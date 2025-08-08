package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Boss;
import org.afkdeveloper.game.model.Equipment;
import org.afkdeveloper.game.model.Player;

public class BossSystem {
    private final Player p;
    private final BattleSystem battle = new BattleSystem();
    private volatile boolean fighting = false;

    public BossSystem(Player p){ this.p = p; }

    public void start(){
        if(fighting){ ConsoleRenderer.event("이미 보스전 중입니다."); return; }
        fighting = true;
        new Thread(this::run, "BOSS").start();
    }

    private Boss makeBoss(){
        int lv = p.getLevel();
        long hp = Math.round((220 + lv * 85L) * (1.0 + (lv/10)*0.8));
        return new Boss("고대의 감시자", hp);
    }

    private void run(){
        Boss boss = makeBoss();
        long timeLimitMs = 20_000;
        long start = System.currentTimeMillis();
        ConsoleRenderer.event("👹 보스 등장: " + boss.getName() + "  (제한: 20초)");
        render(boss, "", start, timeLimitMs, 0);

        while(fighting){
            long elapsed = System.currentTimeMillis()-start;
            if(elapsed >= timeLimitMs){
                ConsoleRenderer.event("⏰ 시간초과! 보스가 도망쳤다...");
                fighting = false; break;
            }

            try{
                long delay = Math.max(100, (long)(1000/Math.max(0.1, p.totalAspd())));
                Thread.sleep(delay);
            }catch (InterruptedException ignored){}

            var hit = battle.calcHit(p, boss.getCurHp());
            if(hit.hit()){
                boss.damage(hit.damage());
                render(boss, hit.crit()?"(CRIT!)":"", start, timeLimitMs, hit.damage());
            } else {
                render(boss, "(MISS)", start, timeLimitMs, 0);
            }

            if(boss.isDead()){
                reward();
                fighting = false;
                break;
            }
        }
    }

    private void reward(){
        double expMul = p.getSkills().expGainMultiplier();
        double curMul = p.getSkills().currencyGainMultiplier();

        int lv = p.getLevel();
        long exp = Math.max(1, Math.round((55 + lv*12L) * expMul));  // 상향
        long gold = Math.max(1, Math.round((120 + lv*28L) * curMul)); // 상향
        long gem  = Math.max(1, Math.round((3 + lv/4.0) * curMul));   // 상향

        p.gainExp(exp);
        p.gainGold(gold);
        p.gainGem(gem);

        // 보스는 장비 드랍 보장(희귀도 상향)
        var drop = LootTable.rollBossDrop(p.getLevel());
        p.getInventory().add(drop);
        // 더 좋은 무기면 자동 장착
        if (drop.getType() == Equipment.Type.WEAPON) {
            var curW = p.getInventory().equipped(Equipment.Type.WEAPON);
            if (curW == null || drop.getAtk() > curW.getAtk()) {
                int idx = p.getInventory().items().size() - 1;
                p.getInventory().equip(idx);
                ConsoleRenderer.event("🗡️ 자동 장착: " + drop);
            }
        }

        ConsoleRenderer.event("🏆 보상: EXP+"+exp+" GOLD+"+gold+" GEM+"+gem+" | 드랍: " + drop);
    }

    private void render(Boss b, String tag, long start, long limit, long lastDmg){
        double ratio = (double)b.getCurHp()/b.getMaxHp();
        int width = 24, fill = (int)Math.round(Math.max(0, ratio)*width);
        StringBuilder bar = new StringBuilder("[");
        for(int i=0;i<width;i++) bar.append(i<fill?'■':'□');
        long rest = Math.max(0, (limit - (System.currentTimeMillis()-start))/1000);
        String pct = String.format("%.3f", Math.max(0,ratio)*100);
        String dmg = lastDmg > 0 ? (" -" + lastDmg) : "";
        String line = "👹 Boss HP " + bar + "] " + pct + "% (" + b.getCurHp() + "/" + b.getMaxHp() + ")"
                + dmg + (tag.isEmpty() ? "" : " " + tag) + " | ⏱ " + rest + "s";
        ConsoleRenderer.renderHUD(line);
    }
}