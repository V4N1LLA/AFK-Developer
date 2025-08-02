package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.model.Equipment;
import org.afkdeveloper.game.model.Player;

import java.util.List;

public class EquipCommand implements Command {
    private final Player p;
    public EquipCommand(Player p){ this.p = p; }

    @Override public void execute(String[] args) {
        var inv = p.getInventory();
        List<Equipment> list = inv.items();

        if(args.length==0){
            if(list.isEmpty()){ System.out.println("인벤토리가 비어있습니다."); return; }
            System.out.println("인벤토리:");
            for(int i=0;i<list.size();i++){
                System.out.printf(" [%d] %s%n", i, list.get(i));
            }
            System.out.println("장착: /equip <index>");
            System.out.println(inv.equippedSummary());
            return;
        }
        try{
            int idx = Integer.parseInt(args[0]);
            if(inv.equip(idx)) System.out.println("장착 완료.");
            else System.out.println("잘못된 인덱스.");
        }catch (Exception e){
            System.out.println("사용법: /equip <index>");
        }
    }

    @Override public String help(){ return "/equip [index] : 인벤토리 보기/장착"; }
}