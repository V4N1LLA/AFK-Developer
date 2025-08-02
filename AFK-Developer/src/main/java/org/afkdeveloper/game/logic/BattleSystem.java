package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Player;

import java.util.concurrent.ThreadLocalRandom;

public class BattleSystem {

    public record HitResult(boolean hit, boolean crit, long damage){}

    // 데미지 = (STR 기반) * (치명 1.5배)
    public HitResult calcHit(Player p, long monsterHp){
        int str = p.getStat().getStr();
        int acc = p.getStat().getAcc();

        double hitChance = Math.min(0.95, 0.60 + acc * 0.01); // ACC가 높을수록 미스↓
        double critChance = Math.min(0.50, 0.05 + acc * 0.005); // 최대 50%

        ThreadLocalRandom r = ThreadLocalRandom.current();
        boolean hit = r.nextDouble() < hitChance;
        if(!hit) return new HitResult(false, false, 0);

        long base = Math.max(1, Math.round(str * 2.2));
        boolean crit = r.nextDouble() < critChance;
        long dmg = crit ? Math.round(base * 1.5) : base;

        // 간단한 난수 가감
        dmg += r.nextInt(0, Math.max(1, (int)(base*0.15)));
        return new HitResult(true, crit, dmg);
    }
}