package org.afkdeveloper.game.model;

public enum PassiveSkill {
    DAMAGE_PCT(0, "데미지% 상승", 1.0, -1),
    ACCURACY_PCT(1, "명중% 상승", 1.0, -1),
    CRIT_CHANCE_PCT(2, "치명타 확률% 상승", 1.0, 50),
    CRIT_DAMAGE_PCT(3, "치명타 데미지% 상승", 0.5, -1),
    CURRENCY_GAIN_PCT(4, "재화 획득률% 상승", 1.0, -1),
    EXP_GAIN_PCT(5, "경험치 획득률% 상승", 1.0, -1),
    DROP_RATE_PCT(6, "장비 드랍률% 상승", 0.1, -1),
    ATTACK_SPEED_FLAT(7, "공격속도 상승", 0.05, 100);

    private final int id;
    private final String label;
    private final double perLevel;
    private final int maxLevel;

    PassiveSkill(int id, String label, double perLevel, int maxLevel) {
        this.id = id;
        this.label = label;
        this.perLevel = perLevel;
        this.maxLevel = maxLevel;
    }

    public int id(){ return id; }
    public String label(){ return label; }
    public double perLevel(){ return perLevel; }
    public int maxLevel(){ return maxLevel; }

    public static PassiveSkill byId(int i){
        for (var s : values()) if (s.id == i) return s;
        return null;
    }
}