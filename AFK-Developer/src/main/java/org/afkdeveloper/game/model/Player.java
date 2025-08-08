package org.afkdeveloper.game.model;

import org.afkdeveloper.game.logic.SkillManager;

public class Player {
    private final String name;
    private int level;
    private long exp;
    private long gold;
    private long gem;

    private final Stat stat;
    private final Inventory inv = new Inventory();
    private final SkillManager skills = new SkillManager();

    public Player(String name) {
        this.name = name;
        this.level = 1;
        this.exp = 0;
        this.gold = 0;
        this.gem  = 0;
        this.stat = new Stat();

        // 스타터 장비(살짝 상향)
        inv.add(new Equipment("Wooden Sword", Equipment.Type.WEAPON, Rarity.UNCOMMON, 7, 0));
        inv.add(new Equipment("Cloth Armor",  Equipment.Type.ARMOR,  Rarity.COMMON,   0, 3));
        inv.equip(0); // 기본 무기 장착
    }

    public String getName(){ return name; }
    public int getLevel(){ return level; }
    public long getExp(){ return exp; }
    public long getGold(){ return gold; }
    public long getGem(){ return gem; }
    public Stat getStat(){ return stat; }
    public Inventory getInventory(){ return inv; }
    public SkillManager getSkills(){ return skills; }

    public long expToNext() {
        int tier = Math.max(0, (level-1)/10);
        long base = 30L + (level-1) * 8L;
        return Math.round(base * (1.0 + tier * 0.5));
    }

    public void gainGold(long g){ gold += g; }
    public void gainGem(long g){ gem  += g; }

    public void gainExp(long e){
        exp += e;
        while (exp >= expToNext()) {
            exp -= expToNext();
            level++;
            stat.gainStatPoints(2);
            skills.addSkillPoints(1); // 레벨업마다 스킬포인트 +1
            System.out.println("🎉 레벨업! Lv." + level + " (스탯 +2, 스킬포인트 +1)");
        }
    }

    public int totalAtk(){
        int base = Math.max(1, stat.getStr());
        Equipment w = inv.equipped(Equipment.Type.WEAPON);
        return base + (w!=null ? w.getAtk() : 0);
    }
    public int totalDef(){
        int base = Math.max(0, stat.getAcc()/5);
        Equipment a = inv.equipped(Equipment.Type.ARMOR);
        return base + (a!=null ? a.getDef() : 0);
    }

    public double totalAspd(){
        return Math.max(0.1, stat.getAspd() + skills.attackSpeedFlat());
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
            ⚔️ ATK:%d | 🛡️ DEF:%d
            📊 %s
            %s
            """,
            name, level, bar(exp, expToNext(), 20), gold, gem,
            totalAtk(), totalDef(), stat.toString(), inv.equippedSummary()
        );
    }
}