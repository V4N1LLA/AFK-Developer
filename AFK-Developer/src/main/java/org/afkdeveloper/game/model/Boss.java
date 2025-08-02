package org.afkdeveloper.game.model;

public class Boss {
    private final String name;
    private final long maxHp;
    private long curHp;

    public Boss(String name, long hp){
        this.name = name;
        this.maxHp = hp;
        this.curHp = hp;
    }

    public String getName(){ return name; }
    public long getMaxHp(){ return maxHp; }
    public long getCurHp(){ return curHp; }
    public void damage(long d){ curHp = Math.max(0, curHp - d); }
    public boolean isDead(){ return curHp<=0; }
}