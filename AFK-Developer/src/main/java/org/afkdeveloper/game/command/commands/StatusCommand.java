package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.model.Player;

public class StatusCommand implements Command {
    private final Player player;
    public StatusCommand(Player p){ this.player = p; }

    @Override public void execute(String[] args) {
        System.out.println();
        System.out.println(player.statusLine());
    }

    @Override public String help() { return "/status : 현재 상태 표시"; }
}