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

        // 별칭(슬래시 없이도 되게)
        map.put("status", map.get("/status"));
        map.put("hunt",   map.get("/hunt"));
        map.put("stat",   map.get("/stat"));
        map.put("equip",  map.get("/equip"));
        map.put("forge",  map.get("/forge"));
        map.put("boss",   map.get("/boss"));
        map.put("skill",  map.get("/skill"));
        map.put("save",   map.get("/save"));
        map.put("load",   map.get("/load"));
        map.put("help",   map.get("/help"));

        // 짧은 별칭
        map.put("hs", args -> map.get("/hunt").execute(new String[]{"stop"}));
        map.put("hk", args -> map.get("/hunt").execute(new String[]{"start"}));
    }

    public void handle(String input){
        if(input.isBlank()) return;

        String[] toks = input.trim().split("\\s+");
        String head = toks[0];
        String key = head.startsWith("/") ? head.toLowerCase() : head.toLowerCase();

        String[] args;
        if (toks.length > 1) {
            args = new String[toks.length - 1];
            System.arraycopy(toks, 1, args, 0, toks.length - 1);
        } else {
            args = new String[0];
        }

        Command c = map.get(key);
        if (c == null && !key.startsWith("/")) {
            // "/xxx"로도 재시도
            c = map.get("/" + key);
        }

        if(c==null){
            System.out.println("❓ 알 수 없는 명령어. /help 를 확인하세요.");
            return;
        }
        c.execute(args);
    }
}