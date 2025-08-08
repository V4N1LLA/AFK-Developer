package org.afkdeveloper.game.logic;

import org.afkdeveloper.data.GitCommandExecutor;

public class GitSyncService {
    public static void onAchievement(String message){
        // 나중에 업적 달성 시 호출 예정
        GitCommandExecutor.commitAndPush(message);
    }
}