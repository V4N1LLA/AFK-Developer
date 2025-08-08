package org.afkdeveloper.game.logic;

/**
 * 한 줄짜리 HUD(진행바)를 프롬프트 "위" 라인에 고정해서 갱신하는 렌더러.
 * - 입력 줄은 항상 화면 맨 아래.
 * - HUD는 그 바로 위 1줄.
 * - ANSI 시퀀스로 커서 위치를 저장/복구하며, 라인 지움.
 *
 * Windows 10+ PowerShell / Windows Terminal에서 동작.
 */
public class ConsoleRenderer {
    private static final Object LOCK = new Object();
    private static volatile boolean initialized = false;
    private static final boolean ANSI = true;

    /** 최초 1회: 아래에 입력줄을 두기 위해 HUD용 빈 줄을 하나 만들어 둔다. */
    public static void init() {
        synchronized (LOCK) {
            if (initialized) return;
            System.out.println(); // HUD용 빈 줄 확보 (프롬프트는 그 아래)
            System.out.flush();
            initialized = true;
        }
    }

    /** HUD 라인을 갱신한다(프롬프트 줄은 건드리지 않음). */
    public static void renderHUD(String text) {
        if (!initialized) init();
        synchronized (LOCK) {
            if (ANSI) {
                // 커서 위치 저장 → 한 줄 위로 이동 → 라인 지움 → HUD 출력 → 커서 복구
                System.out.print("\033[s");        // save cursor
                System.out.print("\033[1A");       // move up 1 line (to HUD line)
                System.out.print("\033[2K");       // clear entire line
                System.out.print(text);
                System.out.print("\033[u");        // restore cursor
            } else {
                // ANSI 미지원 환경: 그냥 같은 줄에 덮어쓰기(최소 보장)
                System.out.print("\r" + text);
            }
            System.out.flush();
        }
    }

    /** 이벤트성 메시지(드랍, 레벨업 등)는 프롬프트 위에서 출력되며, HUD는 이후 갱신 시 다시 그 자리에 덮인다. */
    public static void event(String msg) {
        if (!initialized) init();
        synchronized (LOCK) {
            // 커서 저장 → 한 줄 위(HUD 자리)에서 개행 출력하여 위로 스크롤 → 커서 복원
            // 1) 먼저 프롬프트 줄 커서 저장
            System.out.print("\033[s");
            // 2) 프롬프트 줄에서 println을 하면 프롬프트 줄이 위로 올라가고, 새 프롬프트는 다음 입력 때 다시 찍힘
            System.out.println();
            // 3) 메시지 출력
            System.out.println(msg);
            // 4) 커서 복구(입력 위치로 돌아감)
            System.out.print("\033[u");
            System.out.flush();
        }
    }

    /** 프롬프트 출력. 입력 전에 매번 호출. */
    public static void showPrompt() {
        if (!initialized) init();
        synchronized (LOCK) {
            // 프롬프트 줄을 깨끗하게
            System.out.print("\033[2K\r");
            System.out.print(">> ");
            System.out.flush();
        }
    }
}