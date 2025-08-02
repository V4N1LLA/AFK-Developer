package org.afkdeveloper.game.model;

public class Achievement {
    private final String code;
    private boolean unlocked;

    public Achievement(String code){
        this.code = code;
        this.unlocked = false;
    }
    public String code(){ return code; }
    public boolean unlocked(){ return unlocked; }
    public void unlock(){ this.unlocked = true; }
}