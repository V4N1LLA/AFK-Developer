package org.afkdeveloper.game.model;

public enum Rarity {
    COMMON("흰색", 1.00, 1.00),
    UNCOMMON("초록", 1.10, 1.05),
    RARE("파랑", 1.25, 1.10),
    EPIC("보라", 1.45, 1.15),
    LEGENDARY("금빛", 1.70, 1.20);

    private final String label;
    private final double atkMul;
    private final double defMul;

    Rarity(String label, double atkMul, double defMul) {
        this.label = label;
        this.atkMul = atkMul;
        this.defMul = defMul;
    }

    public String label() { return label; }
    public double atkMul() { return atkMul; }
    public double defMul() { return defMul; }
}