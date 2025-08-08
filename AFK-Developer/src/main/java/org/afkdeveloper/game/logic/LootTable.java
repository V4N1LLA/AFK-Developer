package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Equipment;
import org.afkdeveloper.game.model.Rarity;

import java.util.concurrent.ThreadLocalRandom;

public class LootTable {

    /**
     * 일반 사냥 드랍:
     *  - 드랍 시 무기/방어구 50:50
     *  - 희귀도 분포: COMMON 60, UNCOMMON 25, RARE 12, EPIC 3, LEG 0.5% (레벨↑ 시 소폭 상향)
     */
    public static Equipment rollHuntDrop(int level){
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        boolean weapon = rnd.nextBoolean();
        Rarity r = rollRarity(false, level, rnd);
        return weapon ? EquipmentFactory.rollWeapon(level, r) : EquipmentFactory.rollArmor(level, r);
    }

    /**
     * 보스 드랍:
     *  - 드랍 보장 (무기/방어구 랜덤)
     *  - 희귀도 분포 상향: COMMON 30, UNCOMMON 35, RARE 25, EPIC 8, LEG 2%
     */
    public static Equipment rollBossDrop(int level){
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        boolean weapon = rnd.nextBoolean();
        Rarity r = rollRarity(true, level, rnd);
        return weapon ? EquipmentFactory.rollWeapon(level, r) : EquipmentFactory.rollArmor(level, r);
    }

    private static Rarity rollRarity(boolean boss, int level, ThreadLocalRandom rnd){
        double p = rnd.nextDouble() * 100;
        // 레벨이 높을수록 약간 희귀도 상향(최대 +5%)
        double bonus = Math.min(5.0, level * 0.15);
        if (boss) {
            p -= bonus;
            if (p < 2) return Rarity.LEGENDARY;
            if (p < 10) return Rarity.EPIC;
            if (p < 35) return Rarity.RARE;
            if (p < 70) return Rarity.UNCOMMON;
            return Rarity.COMMON;
        } else {
            p -= (bonus * 0.6);
            if (p < 0.5) return Rarity.LEGENDARY;
            if (p < 3.5) return Rarity.EPIC;
            if (p < 15.5) return Rarity.RARE;
            if (p < 40.5) return Rarity.UNCOMMON;
            return Rarity.COMMON;
        }
    }
}