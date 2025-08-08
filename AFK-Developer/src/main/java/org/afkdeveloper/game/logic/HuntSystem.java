package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Equipment;
import org.afkdeveloper.game.model.Monster;
import org.afkdeveloper.game.model.Player;

import java.util.concurrent.ThreadLocalRandom;

public class HuntSystem {
    private final Player player;
    private final BattleSystem battle = new BattleSystem();
    private volatile boolean hunting = false;
    private Thread loop;

    private Monster current;

    public HuntSystem(Player player){ this.player = player; }

    public boolean isHunting(){ return hunting; }

    public void start(){
        if(hunting) { ConsoleRenderer.event("이미 사냥 중입니다."); return; }
        hunting = true;
        spawnNewMonster();
        loop = new Thread(this::runLoop, "HUNT-LOOP");
        loop.setDaemon(true);
        loop.start();
        ConsoleRenderer.event("🗡️ 사냥을 시작합니다.");
        renderHUD("", 0);
    }

    public void stop(){
        hunting = false;
        ConsoleRenderer.event("⏸️ 사냥을 중단했습니다.");
    }

    private void spawnNewMonster() {
        int lv = Math.max(1, player.getLevel());
        int tier = (lv - 1) / 10;
        String[] names = switch (tier) {
            case 0 -> new String[]{"슬라임", "고블린", "늑대", "쥐", "거머리"};
            case 1 -> new String[]{"스켈레톤", "오크", "도적", "리치의 종복"};
            case 2 -> new String[]{"서큐버스", "미노타우로스", "와이번"};
            default -> new String[]{"카오스 수하", "심연의 형상", "망각의 존재"};
        };
        String name = names[ThreadLocalRandom.current().nextInt(names.length)];

        long baseHp = 26 + (long)(lv * 10L); // 살짝 완화
        long hp = Math.round(baseHp * (1.0 + tier * 0.6));
        current = new Monster(name, lv, hp);
    }

    private long expReward(){ return 5 + player.getLevel(); }        // +1 상향
    private long goldReward(){ return 8 + player.getLevel() * 2L; }  // +2 상향

    private void runLoop(){
        while(hunting){
            try {
                long delay = Math.max(80, (long)(1000 / Math.max(0.1, player.totalAspd())));
                Thread.sleep(delay);

                var hit = battle.calcHit(player, current.getCurHp());
                if(hit.hit()){
                    current.damage(hit.damage());
                    renderHUD(hit.crit() ? "(CRIT!)" : "", hit.damage());
                } else {
                    renderHUD("(MISS)", 0);
                }

                if(current.dead()){
                    double expMul  = player.getSkills().expGainMultiplier();
                    double curMul  = player.getSkills().currencyGainMultiplier();
                    long expGain   = Math.max(1, Math.round(expReward()  * expMul));
                    long goldGain  = Math.max(1, Math.round(goldReward() * curMul));

                    player.gainExp(expGain);
                    player.gainGold(goldGain);

                    ConsoleRenderer.event("✅ 처치! EXP+"+expGain+" GOLD+"+goldGain);

                    // 드랍률 = 기본 5% + 패시브
                    double dropChance = 0.05 + player.getSkills().dropRateBonusPct()/100.0;
                    if(ThreadLocalRandom.current().nextDouble() < dropChance){
                        var drop = LootTable.rollHuntDrop(player.getLevel());
                        player.getInventory().add(drop);
                        ConsoleRenderer.event("🎁 드랍: " + drop);

                        // 더 좋은 무기면 자동 장착
                        if (drop.getType() == Equipment.Type.WEAPON) {
                            var curW = player.getInventory().equipped(Equipment.Type.WEAPON);
                            if (curW == null || drop.getAtk() > curW.getAtk()) {
                                // 인벤토리에서 마지막에 추가된 아이템의 인덱스 = size-1
                                int idx = player.getInventory().items().size() - 1;
                                player.getInventory().equip(idx);
                                ConsoleRenderer.event("🗡️ 자동 장착: " + drop);
                            }
                        }
                    }

                    // 다음 몬스터
                    spawnNewMonster();
                    renderHUD("", 0);
                }
            } catch (InterruptedException ignored){}
        }
    }

    private void renderHUD(String tag, long lastDmg){
        long cur = current.getCurHp();
        long max = current.getMaxHp();
        double ratio = Math.max(0, Math.min(1.0, (double)cur / Math.max(1, max)));
        int width = 20;
        int fill = (int)Math.round(ratio * width);
        StringBuilder bar = new StringBuilder("[");
        for(int i=0;i<width;i++) bar.append(i<fill ? '■' : '□');
        String pct = String.format("%.3f", ratio*100);
        String dmg = lastDmg > 0 ? (" -" + lastDmg) : "";
        String line = String.format("   몬스터 Lv.%d %s  HP %s] %s%%  (%d/%d) %s%s",
                current.getLevel(), current.getName(), bar.toString(), pct, cur, max, dmg, (tag.isEmpty()?"":" "+tag));
        ConsoleRenderer.renderHUD(line);
    }
}