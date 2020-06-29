package com.textrpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Player extends Character implements Fighter, Wizard, Trainable{

    private int xpToLevel = 5;
    private int spellSlots;
    private int amountOfRest = 2;
    private List<Consumable> consumables = new ArrayList<>();

    Player(String name, int level) throws InvalidLevelException {
        super(name, level);

        setMaxHp(level * hpModifier);
        setHp(getMaxHp());
        spellSlots = level;

        //Give the player a random starting amount of Healing Potions
        for(int i = 0; i < new Random().nextInt(level); i++){
            consumables.add(new HealingPotion("Healing Potion"));
        }
    }

    Player(String name) throws InvalidLevelException {
        this(name, 1);
    }

    public int getXpToLevel(){
        return xpToLevel;
    }

    public void setXpToLevel(){
        this.xpToLevel *= 2;
    }

    public int increaseLevel(){
        return getLevel() + 1;
    }

    public int getSpellSlots() {
        return spellSlots;
    }

    public void setSpellSlots(int spellSlots){
        this.spellSlots = spellSlots;
    }

    public int getAmountOfRest(){
        return amountOfRest;
    }

    public void setAmountOfRest(int amountOfRest){
        this.amountOfRest = amountOfRest;
    }

    public List<Consumable> getConsumableList(){
        return consumables;
    }

    public void addConsumable(Consumable c){
        if(c == null) {
            throw new NullPointerException("No Consumable found.");
        }
        consumables.add(c);
    }

    @Override
    public int attack(){
        return new Random().nextInt(5) + 1 + getAttackScore();
    }

    @Override
    public int magicAttack(){
        Random random = new Random();
        int damage = (random.nextInt(9) + 1) + (random.nextInt(9) + 1) + (getAttackScore() * 2);
        return damage;
    }

    public boolean dodge(Enemy e){
        int dodgeModifier = new Random().nextInt(5) + getDefenseScore();
        if(dodgeModifier > e.getAttackScore() + 4) return true;
        else return false;
    }

    @Override
    public void levelUp(){
        if(getXp() >= getXpToLevel()){
            setLevel(increaseLevel());
            setXpToLevel();
            System.out.println("You've leveled up to Level " + getLevel() + "!");

            setMaxHp(getMaxHp() + hpModifier);
            setAttackScore(getAttackScore() + 1);
            setDefenseScore(getDefenseScore() + 1);
            setSpellSlots(getSpellSlots() + 1);

            //check if maybe there was enough experience to gain another level
            levelUp();
        }
    }

    //Not sure why we would want to throw an Exception, but needed one for the requirements.
    @Override
    public int restoreSpellSlot(int i){
        if(i > 0){
            throw new IllegalArgumentException("Must be out of Spell Slots for a chance to recover.");
        }else if(new Random().nextInt(9) > 4){
            return 1;
        }
        return 0;
    }

    public int restHeal(){
        return new Random().nextInt( 9) + 1;
    }

    public Consumable combinePotions(List<Consumable> consumables, Player p){
        if(consumables.size() >= 2){
            consumables.remove(0);
            consumables.remove(0);
            return new GreaterHealingPotion("Greater Healing Potion");
        }else{
            System.out.println("Not enough potions to combine!");
            return null;
        }
    }
}
