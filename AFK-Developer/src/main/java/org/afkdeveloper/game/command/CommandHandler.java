package org.afkdeveloper.game.command;

import org.afkdeveloper.game.command.commands.*;
import org.afkdeveloper.game.logic.BossSystem;
import org.afkdeveloper.game.logic.ForgeSystem;
import org.afkdeveloper.game.logic.HuntSystem;
import org.afkdeveloper.game.model.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> map = new HashMap<>();

    public CommandHandler(Player player) {
        HuntSystem hunt = new HuntSystem(player);
        ForgeSystem forge = new ForgeSystem(player);
        BossSystem boss = new BossSystem(player);

        map.put("/status", new StatusCommand(player));
        map.put("/hunt",   new HuntCommand(hunt));
        map.put("/stat",   new StatCommand(player));
        map.put("/equip",  new EquipCommand(player));
        map.put("/forge",  new ForgeCommand(forge));
        map.put("/boss",   new BossCommand(boss));
        map.put("/skill",  new SkillCommand(player));
        map.put("/save",   new SaveCommand(player));
        map.put("/load",   new LoadCommand(player));
        map.put("/help",   new HelpCommand(map));
        map.put("/exit",   new ExitCommand());
    }

    public void handle(String input){
        if(input.isBlank()) return;
        String[] tok = input.split("\\s+");
        String cmd = tok[0].toLowerCase();
        String[] args = tok.length>1 ? input.substring(cmd.length()).trim().split("\\s+") : new String[0];

        Command c = map.get(cmd);
        if(c==null){
            System.out.println("❓ 알 수 없는 명령어. /help 를 확인하세요.");
            return;
        }
        c.execute(args);
    }
}