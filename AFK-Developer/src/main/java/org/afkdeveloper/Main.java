package org.afkdeveloper;

import org.afkdeveloper.game.command.CommandHandler;
import org.afkdeveloper.game.model.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // 콘솔 출력 UTF-8 강제
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));

        Player player = new Player("Warrior");
        CommandHandler handler = new CommandHandler(player);
        Scanner sc = new Scanner(System.in);

        System.out.println("🎮 AFK-Developer 시작! (/help 로 명령어 보기)");
        while (true) {
            System.out.print(">> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("/exit")) {
                System.out.println("👋 게임을 종료합니다.");
                break;
            }
            handler.handle(line);
        }
    }
}