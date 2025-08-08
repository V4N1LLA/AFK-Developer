package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;
import org.afkdeveloper.game.logic.SkillManager;
import org.afkdeveloper.game.model.PassiveSkill;
import org.afkdeveloper.game.model.Player;

public class SkillCommand implements Command {
    private final Player p;
    public SkillCommand(Player p){ this.p = p; }

    @Override public void execute(String[] args) {
        SkillManager sm = p.getSkills();
        if(args.length == 0 || args[0].equalsIgnoreCase("list")){
            System.out.println("🎓 스킬 목록 (id:이름 / 레벨 / 설명)");
            for (var s : PassiveSkill.values()){
                String cap = s.maxLevel()>=0 ? " (max "+s.maxLevel()+")" : "";
                System.out.printf("  %d: %s  | Lv.%d%s%n",
                        s.id(), s.label(), sm.getLevel(s), cap);
            }
            System.out.println("남은 스킬포인트: " + sm.getSkillPoints());
            System.out.println("사용법: /skill up <id> <n>");
            return;
        }

        if(args[0].equalsIgnoreCase("up")){
            if(args.length<3){ System.out.println("사용법: /skill up <id> <n>"); return; }
            try{
                int id = Integer.parseInt(args[1]);
                int n  = Integer.parseInt(args[2]);
                var s = PassiveSkill.byId(id);
                if(s==null){ System.out.println("잘못된 id"); return; }
                boolean ok = sm.levelUp(s, n);
                if(ok) System.out.println("업그레이드 완료! "+s.label()+" Lv."+sm.getLevel(s)+", 남은 포인트: "+sm.getSkillPoints());
                else System.out.println("업그레이드 실패(포인트 부족 또는 최대 레벨 초과).");
            }catch (Exception e){
                System.out.println("정수를 입력하세요.");
            }
            return;
        }

        System.out.println("사용법: /skill list | /skill up <id> <n>");
    }

    @Override public String help(){ return "/skill list | /skill up <id> <n> : 패시브 스킬 관리"; }
}