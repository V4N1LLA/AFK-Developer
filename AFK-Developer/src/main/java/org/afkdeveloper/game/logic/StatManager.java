package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.Stat;

public class StatManager {
    public static boolean allocate(Stat s, String which, int n){
        if(n<=0 || s.getStatPoints()<n) return false;
        switch (which.toLowerCase()){
            case "str" -> s.addStr(n);
            case "acc" -> s.addAcc(n);
            case "aspd"-> s.addAspd(n);
            default -> { return false; }
        }
        return true;
    }
}