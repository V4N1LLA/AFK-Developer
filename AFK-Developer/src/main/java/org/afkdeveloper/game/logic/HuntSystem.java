package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Player;

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
        // 플레이어 레벨 기반 던전 자동 상향
        int lv = player.getLevel();
        long hp = 30 + (long)(lv * 12L);
        int tier = Math.max(0, (lv-1)/10);
        return Math.round(hp * (1.0 + tier*0.6));
    }

    private long expReward(){
        int lv = player.getLevel();
        return 4 + lv; // 소폭 증가
    }
    private long goldReward(){
        int lv = player.getLevel();
        return 6 + lv * 2L;
    }

    private void runLoop(){
        long hp = monsterMaxHp();
        long cur = hp;

        while(hunting){
            try {
                // 공격 주기 = 1000ms / ASPD
                double aspd = player.getStat().getAspd();
                long delay = Math.max(100, (long)(1000 / Math.max(0.1, aspd)));
                Thread.sleep(delay);

                var hit = battle.calcHit(player, cur);
                if(!hit.hit()){
                    render(cur, hp, "(MISS)");
                    continue;
                }
                cur = Math.max(0, cur - hit.damage());
                render(cur, hp, hit.crit() ? "(CRIT!)" : "");

                if(cur <= 0){
                    // 처치 보상
                    player.gainExp(expReward());
                    player.gainGold(goldReward());
                    // 다음 몬스터 리스폰
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

        System.out.print("\r"); // 같은 줄 갱신
        System.out.print("몬스터 HP " + bar + " " + tag + "    ");
        if(cur == max) System.out.print("\n"); // 새 몬스터 스폰 시 줄개행
    }
}