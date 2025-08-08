package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Player;

import java.util.concurrent.ThreadLocalRandom;

public class BattleSystem {
    public record HitResult(boolean hit, boolean crit, long damage){}

    public HitResult calcHit(Player p, long monsterHp){
        var sk = p.getSkills();

        // 명중률 (상한 97%)
        double baseHit = 0.62 + p.getStat().getAcc() * 0.010; // +0.02
        double hitChance  = Math.min(0.97, baseHit + sk.accuracyBonusPct()/100.0);

        // 치명타 확률 (상한 50%)
        double baseCrit = 0.06 + p.getStat().getAcc() * 0.005; // +0.01
        double critChance = Math.min(0.50, baseCrit + sk.critChanceBonusPct()/100.0);

        var r = ThreadLocalRandom.current();
        if(r.nextDouble() >= hitChance) return new HitResult(false, false, 0);

        // 데미지 (1.2 → 1.5 배수로 상향 + 최종 데미지% + 치뎀%)
        long base = Math.max(1, Math.round(p.totalAtk() * 1.5));
        boolean crit = r.nextDouble() < critChance;

        double dmg = base * sk.damageMultiplier();
        if(crit) dmg *= (1.5 * sk.critDamageMultiplier());
        // 난수 가감
        dmg += r.nextInt(0, Math.max(1, (int)(base*0.12)));

        return new HitResult(true, crit, Math.max(1, Math.round(dmg)));
    }
}