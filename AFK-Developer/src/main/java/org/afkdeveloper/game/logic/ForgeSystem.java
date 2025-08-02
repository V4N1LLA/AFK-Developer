package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Equipment;
import org.afkdeveloper.game.model.Player;

import java.util.concurrent.ThreadLocalRandom;

public class ForgeSystem {
    private final Player p;
    public ForgeSystem(Player p){ this.p = p; }

    public void enhanceWeapon(){
        Equipment w = p.getInventory().equipped(Equipment.Type.WEAPON);
        if(w==null){ System.out.println("무기가 장착되어 있지 않습니다."); return; }

        int next = w.getEnhance()+1;
        long cost = 50L * next;
        if(p.getGold() < cost){ System.out.println("골드 부족. 필요: " + cost); return; }

        double base = 0.95 - (next-1)*0.07;   // 점감
        double chance = Math.max(0.15, base); // 최소 15%
        boolean success = ThreadLocalRandom.current().nextDouble() < chance;

        p.gainGold(-cost);
        if(success){
            w.enhanceSuccess();
            System.out.println("✅ 강화 성공! " + w);
        }else{
            System.out.println("❌ 강화 실패... (골드만 소모)");
        }
    }
}