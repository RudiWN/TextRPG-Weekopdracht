package com.textrpg;

abstract class Character {

    private String name;
    private int maxHp;
    private int hp;
    private int attackScore;
    private int defenseScore;
    private int level;
    private int xp;

    Character (String name, int level) throws InvalidLevelException{
            if (level < 1){
                throw new InvalidLevelException("A character can't be of lower level than 1");
            }else {
                this.level = level;
                this.name = name;
                maxHp = level * 5;
                hp = maxHp;
                attackScore = level;
                defenseScore = level/2;
            }
    }

    public String getName(){
        return name;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public void setMaxHp(int hp){
        this.maxHp = hp;
    }

    public int getAttackScore(){
        return attackScore;
    }

    public void setAttackScore(int attackScore){
        this.attackScore = attackScore;
    }

    public int getDefenseScore(){
        return defenseScore;
    }

    public void setDefenseScore(int defenseScore){
        this.defenseScore = defenseScore;
    }

    public int getLevel(){
        return level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp){
        this.xp = xp;
    }

    public void setLevel(int level){
        this.level = level;
    }
}
