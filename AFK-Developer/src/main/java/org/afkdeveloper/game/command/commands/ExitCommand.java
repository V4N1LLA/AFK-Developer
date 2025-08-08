package org.afkdeveloper.game.command.commands;

import org.afkdeveloper.game.command.Command;

public class ExitCommand implements Command {
    @Override public void execute(String[] args) {
        System.out.println("힌트: 실제 종료는 콘솔에서 /exit 입력으로 처리됩니다.");
    }
}