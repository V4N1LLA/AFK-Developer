package org.afkdeveloper.game.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Equipment> items = new ArrayList<>();
    private final Equipment[] equipped = new Equipment[3]; // 0 W, 1 A, 2 R

    public void add(Equipment e){ items.add(e); }
    public List<Equipment> items(){ return items; }

    public boolean equip(int index){
        if(index<0 || index>=items.size()) return false;
        Equipment e = items.get(index);
        int slot = switch (e.getType()){
            case WEAPON -> 0; case ARMOR -> 1; case RING -> 2;
        };
        equipped[slot] = e;
        return true;
    }

    public Equipment equipped(Equipment.Type t){
        return switch (t){
            case WEAPON -> equipped[0];
            case ARMOR  -> equipped[1];
            case RING   -> equipped[2];
        };
    }

    public String equippedSummary(){
        return """
                🔪 무기 : %s
                🛡️ 방어 : %s
                💍 반지 : %s
                """.formatted(
                equipped[0]==null?"(없음)":equipped[0],
                equipped[1]==null?"(없음)":equipped[1],
                equipped[2]==null?"(없음)":equipped[2]
        );
    }
}