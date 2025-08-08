package org.afkdeveloper.game.command;

public interface Command {
    void execute(String[] args);
    default String help(){ return ""; }
}