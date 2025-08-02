package org.afkdeveloper.game.logic;

import org.afkdeveloper.game.model.PassiveSkill;

import java.util.EnumMap;
import java.util.Map;

public class SkillManager {
    private final Map<PassiveSkill, Integer> levels = new EnumMap<>(PassiveSkill.class);
    private int skillPoints = 0;

    public SkillManager(){
        for (var s : PassiveSkill.values()) levels.put(s, 0);
    }

    public int getLevel(PassiveSkill s){ return levels.get(s); }
    public int getSkillPoints(){ return skillPoints; }
    public void addSkillPoints(int n){ skillPoints += n; }
    public void consumeSkillPoints(int n){ skillPoints = Math.max(0, skillPoints - n); }

    /** 레벨업 (제한을 지킴). 성공 시 true */
    public boolean levelUp(PassiveSkill s, int n){
        if(n<=0) return false;
        if(skillPoints < n) return false;
        int cur = levels.get(s);
        int max = s.maxLevel();
        if(max >= 0 && cur + n > max) n = max - cur;
        if(n <= 0) return false;
        levels.put(s, cur + n);
        consumeSkillPoints(n);
        return true;
    }

    // ===== 효과 조회 =====
    public double damageMultiplier(){
        int lv = getLevel(PassiveSkill.DAMAGE_PCT);
        return 1.0 + lv * PassiveSkill.DAMAGE_PCT.perLevel()/100.0;
    }
    public double accuracyBonusPct(){
        int lv = getLevel(PassiveSkill.ACCURACY_PCT);
        return lv * PassiveSkill.ACCURACY_PCT.perLevel();
    }
    public double critChanceBonusPct(){
        int lv = getLevel(PassiveSkill.CRIT_CHANCE_PCT);
        return lv * PassiveSkill.CRIT_CHANCE_PCT.perLevel();
    }
    public double critDamageMultiplier(){
        int lv = getLevel(PassiveSkill.CRIT_DAMAGE_PCT);
        return 1.0 + lv * PassiveSkill.CRIT_DAMAGE_PCT.perLevel()/100.0;
    }
    public double currencyGainMultiplier(){
        int lv = getLevel(PassiveSkill.CURRENCY_GAIN_PCT);
        return 1.0 + lv * PassiveSkill.CURRENCY_GAIN_PCT.perLevel()/100.0;
    }
    public double expGainMultiplier(){
        int lv = getLevel(PassiveSkill.EXP_GAIN_PCT);
        return 1.0 + lv * PassiveSkill.EXP_GAIN_PCT.perLevel()/100.0;
    }
    public double dropRateBonusPct(){
        int lv = getLevel(PassiveSkill.DROP_RATE_PCT);
        return lv * PassiveSkill.DROP_RATE_PCT.perLevel();
    }
    public double attackSpeedFlat(){
        int lv = getLevel(PassiveSkill.ATTACK_SPEED_FLAT);
        return lv * PassiveSkill.ATTACK_SPEED_FLAT.perLevel();
    }
}