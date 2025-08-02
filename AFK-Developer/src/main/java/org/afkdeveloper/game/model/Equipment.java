package org.afkdeveloper.game.model;

public class Equipment {
    public enum Type { WEAPON, ARMOR, RING }

    private final String name;
    private final Type type;
    private int atk;
    private int def;
    private int enhance; // +n

    public Equipment(String name, Type type, int atk, int def) {
        this.name = name;
        this.type = type;
        this.atk = atk;
        this.def = def;
        this.enhance = 0;
    }

    public void enhanceSuccess(){
        enhance++;
        atk += 2;
        def += 1;
    }

    public Type getType(){ return type; }
    public int getAtk(){ return atk; }
    public int getDef(){ return def; }
    public int getEnhance(){ return enhance; }
    public String getName(){ return name; }

    @Override public String toString(){
        return "%s +%d (ATK:%d DEF:%d)".formatted(name, enhance, atk, def);
    }
}