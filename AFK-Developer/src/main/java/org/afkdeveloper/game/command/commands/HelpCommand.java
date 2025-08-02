package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;

import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, Command> map;
    public HelpCommand(Map<String, Command> map){ this.map = map; }

    @Override public void execute(String[] args) {
        System.out.println("📜 명령어 목록");
        map.forEach((k,v)-> System.out.println("  " + k + "  " + v.help()));
        System.out.println("  /exit  게임 종료");
    }

    @Override public String help(){ return "/help : 명령어 안내"; }
}