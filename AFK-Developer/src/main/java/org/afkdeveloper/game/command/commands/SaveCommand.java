package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.data.SaveManager;
import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.model.Player;

public class SaveCommand implements Command {
    private final Player p;
    public SaveCommand(Player p){ this.p = p; }

    @Override public void execute(String[] args) {
        SaveManager.save(p);
    }

    @Override public String help(){ return "/save : 저장"; }
}