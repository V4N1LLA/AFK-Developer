package org.afkdeveloper.game.command;

import org.afkdeveloper.game.command.commands.*;
import org.afkdeveloper.game.logic.HuntSystem;
import org.afkdeveloper.game.model.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> map = new HashMap<>();

    public CommandHandler(Player player) {
        HuntSystem hunt = new HuntSystem(player);
        map.put("/status", new StatusCommand(player));
        map.put("/hunt", new HuntCommand(hunt));
        map.put("/help", new HelpCommand(map));
        map.put("/exit", new ExitCommand()); // 편의
        // 추후: /stat, /boss, /forge, /equip, /save ...
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