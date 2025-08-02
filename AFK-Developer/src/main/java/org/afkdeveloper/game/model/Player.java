package org.afkdeveloper.game.model;

public class Player {
    private final String name;
    private int level;
    private long exp;
    private long gold;
    private long gem;
    private final Stat stat;

    public Player(String name) {
        this.name = name;
        this.level = 1;
        this.exp = 0;
        this.gold = 0;
        this.gem  = 0;
        this.stat = new Stat();
    }

    public String getName(){ return name; }
    public int getLevel(){ return level; }
    public long getExp(){ return exp; }
    public long getGold(){ return gold; }
    public long getGem(){ return gem; }
    public Stat getStat(){ return stat; }

    // 10레벨 단위로 요구치 상승(완만→점진)
    public long expToNext() {
        int tier = Math.max(0, (level-1)/10);        // 0,1,2,...
        long base = 30L + (level-1) * 8L;             // 선형 기반
        return Math.round(base * (1.0 + tier * 0.5)); // 티어마다 +50%
    }

    public void gainGold(long g){ gold += g; }
    public void gainGem(long g){ gem  += g; }

    public void gainExp(long e){
        exp += e;
        while (exp >= expToNext()) {
            exp -= expToNext();
            level++;
            stat.gainStatPoints(2); // 레벨업마다 스탯 2포인트
            System.out.println("🎉 레벨업! Lv." + level + " (스탯 포인트 +2)");
        }
    }

    public String bar(long cur, long max, int width){
        if(max<=0) max=1;
        double ratio = (double)cur/max;
        int fill = (int)Math.round(ratio * width);
        StringBuilder sb = new StringBuilder("[");
        for(int i=0;i<width;i++) sb.append(i<fill ? '■' : '□');
        sb.append("] ").append(String.format("%.3f", ratio*100)).append("%");
        return sb.toString();
    }

    public String statusLine(){
        return String.format(
            """
            🧑‍💻 전사 %s | Lv.%d
            📈 EXP: %s
            💰 GOLD: %,d | 💎 GEM: %,d
            📊 %s
            """,
            name, level, bar(exp, expToNext(), 20), gold, gem, stat.toString()
        );
    }
}