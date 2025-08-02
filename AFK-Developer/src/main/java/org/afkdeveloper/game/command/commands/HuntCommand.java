package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.logic.HuntSystem;

public class HuntCommand implements Command {
    private final HuntSystem hunt;
    public HuntCommand(HuntSystem hunt){ this.hunt = hunt; }

    @Override public void execute(String[] args) {
        if(args.length==0){
            System.out.println("사용법: /hunt start | /hunt stop");
            return;
        }
        switch (args[0].toLowerCase()){
            case "start" -> hunt.start();
            case "stop"  -> hunt.stop();
            default -> System.out.println("사용법: /hunt start | /hunt stop");
        }
    }

    @Override public String help(){ return "/hunt start|stop : 자동 사냥 시작/중단"; }
}