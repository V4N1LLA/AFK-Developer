package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.logic.BossSystem;

public class BossCommand implements Command {
    private final BossSystem boss;
    public BossCommand(BossSystem boss){ this.boss = boss; }

    @Override public void execute(String[] args) {
        boss.start();
    }

    @Override public String help(){ return "/boss : 보스전 시작(제한시간)"; }
}