package org.afkdeveloper.game.model;

public class Monster {
    private final String name;
    private final int level;
    private final long maxHp;
    private long curHp;

    public Monster(String name, int level, long maxHp) {
        this.name = name;
        this.level = level;
        this.maxHp = Math.max(1, maxHp);
        this.curHp = this.maxHp;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public long getMaxHp() { return maxHp; }
    public long getCurHp() { return curHp; }

    public void damage(long d) { curHp = Math.max(0, curHp - Math.max(0, d)); }
    public boolean dead() { return curHp <= 0; }

    @Override
    public String toString() {
        return "Lv." + level + " " + name + " (" + curHp + "/" + maxHp + ")";
    }
}   