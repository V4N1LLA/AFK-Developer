package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Equipment;
import org.afkdeveloper.game.model.Player;

import java.util.concurrent.ThreadLocalRandom;

public class HuntSystem {
    private final Player player;
    private final BattleSystem battle = new BattleSystem();
    private volatile boolean hunting = false;
    private Thread loop;

    public HuntSystem(Player player){ this.player = player; }

    public boolean isHunting(){ return hunting; }

    public void start(){
        if(hunting) {
            System.out.println("이미 사냥 중입니다.");
            return;
        }
        hunting = true;
        loop = new Thread(this::runLoop, "HUNT-LOOP");
        loop.setDaemon(true);
        loop.start();
        System.out.println("🗡️ 사냥을 시작합니다.");
    }

    public void stop(){
        hunting = false;
        System.out.println("⏸️ 사냥을 중단했습니다.");
    }

    private long monsterMaxHp(){
        int lv = player.getLevel();
        long hp = 30 + (long)(lv * 12L);
        int tier = Math.max(0, (lv-1)/10);
        return Math.round(hp * (1.0 + tier*0.6));
    }

    private long expReward(){ return 4 + player.getLevel(); }
    private long goldReward(){ return 6 + player.getLevel() * 2L; }

    private void runLoop(){
        long hp = monsterMaxHp();
        long cur = hp;

        while(hunting){
            try {
                double aspd = player.totalAspd();
                long delay = Math.max(80, (long)(1000 / Math.max(0.1, aspd)));
                Thread.sleep(delay);

                var hit = battle.calcHit(player, cur);
                if(!hit.hit()){
                    render(cur, hp, "(MISS)");
                    continue;
                }
                cur = Math.max(0, cur - hit.damage());
                render(cur, hp, hit.crit() ? "(CRIT!)" : "");

                if(cur <= 0){
                    // 처치 보상 (패시브 배수 적용)
                    double expMul  = player.getSkills().expGainMultiplier();
                    double curMul  = player.getSkills().currencyGainMultiplier();
                    long expGain   = Math.max(1, Math.round(expReward()  * expMul));
                    long goldGain  = Math.max(1, Math.round(goldReward() * curMul));

                    player.gainExp(expGain);
                    player.gainGold(goldGain);

                    // 드랍률 = 기본 2% + 패시브
                    double dropChance = 0.02 + player.getSkills().dropRateBonusPct()/100.0;
                    if(ThreadLocalRandom.current().nextDouble() < dropChance){
                        var drop = new Equipment("Rusty Dagger", Equipment.Type.WEAPON, 3, 0);
                        player.getInventory().add(drop);
                        System.out.print("\n🎁 드랍 획득: " + drop + "\n");
                    }

                    // 다음 몬스터
                    hp = monsterMaxHp();
                    cur = hp;
                }
            } catch (InterruptedException ignored){}
        }
    }

    private void render(long cur, long max, String tag){
        double ratio = Math.max(0, Math.min(1.0, (double)cur/max));
        int width = 20;
        int fill = (int)Math.round(ratio * width);
        StringBuilder bar = new StringBuilder("[");
        for(int i=0;i<width;i++) bar.append(i<fill ? '■' : '□');
        bar.append("] ").append(String.format("%.3f", ratio*100)).append("%");

        System.out.print("\r몬스터 HP " + bar + " " + tag + "    ");
        if(cur == max) System.out.print("\n");
    }
}