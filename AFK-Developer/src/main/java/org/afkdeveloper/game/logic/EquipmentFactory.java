package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Equipment;
import org.afkdeveloper.game.model.Rarity;

import java.util.concurrent.ThreadLocalRandom;

public class EquipmentFactory {

    private static final String[] WEAPON_NAMES_T1 = {
            "Rusty Dagger", "Wooden Sword", "Copper Blade", "Stone Axe"
    };
    private static final String[] WEAPON_NAMES_T2 = {
            "Iron Sword", "Steel Mace", "War Axe", "Spear"
    };
    private static final String[] WEAPON_NAMES_T3 = {
            "Knight Blade", "Runed Saber", "Reaver", "Battle Pike"
    };
    private static final String[] ARMOR_NAMES_T1 = {
            "Cloth Armor", "Padded Vest", "Worn Jacket"
    };
    private static final String[] ARMOR_NAMES_T2 = {
            "Leather Armor", "Scale Vest", "Chain Shirt"
    };
    private static final String[] ARMOR_NAMES_T3 = {
            "Iron Plate", "Knight Mail", "Dragon Scale"
    };

    public static Equipment rollWeapon(int level, Rarity r){
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        String name = pickWeaponName(level, rnd);
        // 기본 공격력: 레벨*2.2 ~ 3.0 사이 랜덤, 희귀도 배수 적용
        double base = level * (2.2 + rnd.nextDouble(0.8));
        int atk = Math.max(1, (int)Math.round(base * r.atkMul()));
        return new Equipment(name, Equipment.Type.WEAPON, r, atk, 0);
    }

    public static Equipment rollArmor(int level, Rarity r){
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        String name = pickArmorName(level, rnd);
        // 기본 방어력: 레벨*1.2 ~ 1.8 사이 랜덤, 희귀도 배수 적용
        double base = level * (1.2 + rnd.nextDouble(0.6));
        int def = Math.max(0, (int)Math.round(base * r.defMul()));
        return new Equipment(name, Equipment.Type.ARMOR, r, 0, def);
    }

    private static String pickWeaponName(int level, ThreadLocalRandom rnd){
        if (level < 6) return WEAPON_NAMES_T1[rnd.nextInt(WEAPON_NAMES_T1.length)];
        if (level < 15) return WEAPON_NAMES_T2[rnd.nextInt(WEAPON_NAMES_T2.length)];
        return WEAPON_NAMES_T3[rnd.nextInt(WEAPON_NAMES_T3.length)];
    }
    private static String pickArmorName(int level, ThreadLocalRandom rnd){
        if (level < 6) return ARMOR_NAMES_T1[rnd.nextInt(ARMOR_NAMES_T1.length)];
        if (level < 15) return ARMOR_NAMES_T2[rnd.nextInt(ARMOR_NAMES_T2.length)];
        return ARMOR_NAMES_T3[rnd.nextInt(ARMOR_NAMES_T3.length)];
    }
}