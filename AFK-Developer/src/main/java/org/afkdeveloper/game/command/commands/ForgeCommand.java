package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.logic.ForgeSystem;

public class ForgeCommand implements Command {
    private final ForgeSystem forge;
    public ForgeCommand(ForgeSystem forge){ this.forge = forge; }

    @Override public void execute(String[] args) {
        forge.enhanceWeapon();
    }

    @Override public String help(){ return "/forge : 장착 무기 강화"; }
}