package org.afkdeveloper.game.model;

public class Stat {
    private int str;      // 힘: 데미지
    private int acc;      // 명중: 명중/치명
    private double aspd;  // 공격속도(초당 공격 횟수 기준)
    private int statPoints;

    public Stat() {
        this.str = 5;
        this.acc = 5;
        this.aspd = 1.0; // 1 hit/sec
        this.statPoints = 0;
    }

    public void addStr(int n){ if(statPoints>=n){ str+=n; statPoints-=n; } }
    public void addAcc(int n){ if(statPoints>=n){ acc+=n; statPoints-=n; } }
    public void addAspd(int n){ if(statPoints>=n){ aspd += 0.05 * n; statPoints-=n; } }

    public int getStr(){ return str; }
    public int getAcc(){ return acc; }
    public double getAspd(){ return aspd; }
    public int getStatPoints(){ return statPoints; }
    public void gainStatPoints(int n){ statPoints += n; }

    @Override public String toString(){
        return String.format("STR:%d | ACC:%d | ASPD:%.2f | SP:%d", str, acc, aspd, statPoints);
    }
}