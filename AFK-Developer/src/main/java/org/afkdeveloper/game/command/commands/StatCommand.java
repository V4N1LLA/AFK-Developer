package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.model.Player;

public class StatCommand implements Command {
    private final Player p;
    public StatCommand(Player p){ this.p = p; }

    @Override public void execute(String[] args) {
        if(args.length < 2){
            System.out.println("사용법: /stat <str|acc|aspd> <포인트>");
            System.out.println("현재 SP: " + p.getStat().getStatPoints());
            return;
        }
        String which = args[0].toLowerCase();
        int n;
        try{ n = Integer.parseInt(args[1]); } catch (Exception e){ System.out.println("정수를 입력하세요."); return; }
        if(n<=0){ System.out.println("1 이상 입력."); return; }

        switch (which){
            case "str" -> p.getStat().addStr(n);
            case "acc" -> p.getStat().addAcc(n);
            case "aspd"-> p.getStat().addAspd(n);
            default -> { System.out.println("str/acc/aspd 중 선택"); return; }
        }
        System.out.println("분배 완료. SP 남은 수: " + p.getStat().getStatPoints());
    }

    @Override public String help(){ return "/stat <str|acc|aspd> <n> : 스탯 분배"; }
}