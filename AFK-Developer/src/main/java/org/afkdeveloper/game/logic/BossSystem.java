package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Boss;
import org.afkdeveloper.game.model.Equipment;
import org.afkdeveloper.game.model.Player;

import java.util.concurrent.ThreadLocalRandom;

public class BossSystem {
    private final Player p;
    private final BattleSystem battle = new BattleSystem();
    private volatile boolean fighting = false;

    public BossSystem(Player p){ this.p = p; }

    public void start(){
        if(fighting){ System.out.println("이미 보스전 중입니다."); return; }
        fighting = true;
        new Thread(this::run, "BOSS").start();
    }

    private Boss makeBoss(){
        int lv = p.getLevel();
        long hp = Math.round((200 + lv * 80L) * (1.0 + (lv/10)*0.8));
        return new Boss("고대의 감시자", hp);
    }

    private void run(){
        Boss boss = makeBoss();
        long timeLimitMs = 20_000; // 20초 제한
        long start = System.currentTimeMillis();
        System.out.println("👹 보스 등장: " + boss.getName() + "  (제한: 20초)");

        while(fighting){
            long elapsed = System.currentTimeMillis()-start;
            if(elapsed >= timeLimitMs){
                System.out.println("\n⏰ 시간초과! 보스가 도망쳤다...");
                fighting = false; break;
            }

            try{
                long delay = Math.max(100, (long)(1000/Math.max(0.1, p.totalAspd())));
                Thread.sleep(delay);
            }catch (InterruptedException ignored){}

            var hit = battle.calcHit(p, boss.getCurHp());
            if(!hit.hit()){
                render(boss, "(MISS)", start, timeLimitMs);
                continue;
            }
            boss.damage(hit.damage());
            render(boss, hit.crit()?"(CRIT!)":"", start, timeLimitMs);

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
        long exp = Math.max(1, Math.round((40 + lv*10L) * expMul));
        long gold = Math.max(1, Math.round((80 + lv*20L) * curMul));
        long gem  = Math.max(1, Math.round((2 + lv/5.0) * curMul));

        p.gainExp(exp);
        p.gainGold(gold);
        p.gainGem(gem);

        double dropChance = 0.30 + p.getSkills().dropRateBonusPct()/100.0;
        if(ThreadLocalRandom.current().nextDouble() < dropChance){
            var drop = new Equipment("Boss Blade", Equipment.Type.WEAPON, 10 + p.getLevel(), 0);
            p.getInventory().add(drop);
            System.out.println("\n💎 보상: EXP+"+exp+" GOLD+"+gold+" GEM+"+gem+" | 드랍: " + drop);
        }else{
            System.out.println("\n💎 보상: EXP+"+exp+" GOLD+"+gold+" GEM+"+gem);
        }
    }

    private void render(Boss b, String tag, long start, long limit){
        double ratio = (double)b.getCurHp()/b.getMaxHp();
        int width = 24, fill = (int)Math.round(Math.max(0, ratio)*width);
        StringBuilder bar = new StringBuilder("[");
        for(int i=0;i<width;i++) bar.append(i<fill?'■':'□');
        long rest = Math.max(0, (limit - (System.currentTimeMillis()-start))/1000);
        System.out.print("\r👹 HP "+bar+"] "+String.format("%.3f", Math.max(0,ratio)*100)+"% "+tag+" | ⏱ "+rest+"s   ");
        if(b.isDead()) System.out.print("\n");
    }
}