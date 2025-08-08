package org.afkdeveloper.data;

import org.afkdeveloper.game.model.Player;

import java.nio.file.Path;

/**
 * 추후 JSON 직렬화/역직렬화 구현 예정.
 */
public class SaveManager {
    private static final Path SAVE = Path.of("save", "player.txt");

    public static void save(Player p) {
        try {
            // 임시: 간단 문자열 저장 (TODO: JSON으로 교체)
            String s = "name="+p.getName()+",level="+p.getLevel()+",gold="+p.getGold()+",gem="+p.getGem();
            FileStorage.writeString(SAVE, s);
            System.out.println("💾 저장 완료.");
        } catch (Exception e) {
            System.out.println("⚠️ 저장 실패: " + e.getMessage());
        }
    }

    public static void load(Player p) {
        try {
            String s = FileStorage.readString(SAVE);
            if (s == null) { System.out.println("불러올 데이터가 없습니다."); return; }
            // TODO: 실제로 파싱하여 p에 반영
            System.out.println("📥 불러오기 (샘플 문자열): " + s);
        } catch (Exception e) {
            System.out.println("⚠️ 불러오기 실패: " + e.getMessage());
        }
    }
}