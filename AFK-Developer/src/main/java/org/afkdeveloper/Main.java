package org.afkdeveloper;

import org.afkdeveloper.game.command.CommandHandler;
import org.afkdeveloper.game.logic.ConsoleRenderer;
import org.afkdeveloper.game.model.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));

        Player player = new Player("Warrior");
        CommandHandler handler = new CommandHandler(player);

        System.out.println("AFK-Developer 시작! (/help 로 명령어 보기, /exit 종료)");
        // HUD 전용 라인 확보
        ConsoleRenderer.init();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            while (true) {
                ConsoleRenderer.showPrompt();
                String line = br.readLine();
                if (line == null) {
                    System.out.println("\n입력 스트림이 닫혔습니다. 종료합니다.");
                    break;
                }
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.equalsIgnoreCase("/exit")) { System.out.println("👋 종료"); break; }

                handler.handle(line);
            }
        }
    }
}