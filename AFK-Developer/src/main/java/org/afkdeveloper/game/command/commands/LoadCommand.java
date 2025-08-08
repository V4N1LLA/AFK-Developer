package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.data.SaveManager;
import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.model.Player;

public class LoadCommand implements Command {
    private final Player p;
    public LoadCommand(Player p){ this.p = p; }

    @Override public void execute(String[] args) {
        SaveManager.load(p);
    }

    @Override public String help(){ return "/load : 불러오기"; }
}