package org.afkdeveloper.data;

public class GitCommandExecutor {
    public static void commitAndPush(String message) {
        try {
            // TODO: 실제 git add/commit/push 실행 (ProcessBuilder 사용)
            System.out.println("📝 (스텁) Git 커밋/푸시: " + message);
        } catch (Exception e) {
            System.out.println("⚠️ Git 연동 실패: " + e.getMessage());
        }
    }
}