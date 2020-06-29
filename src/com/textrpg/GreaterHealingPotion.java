package com.textrpg;

import java.util.List;
import java.util.Random;

public class GreaterHealingPotion extends Item implements Consumable {

    GreaterHealingPotion(String name){
        super(name);
    }

    GreaterHealingPotion(){
        this("Healing Potion");
    }

    @Override
    public int consume(List<Consumable> consumables, Player p){
        Random random = new Random();
        int heal = (random.nextInt(7) + 1) + (random.nextInt(7) + 1) + p.getDefenseScore();
        int playerHp = p.getHp();
        int playerMaxHp = p.getMaxHp();

        if(playerHp + heal < playerMaxHp){
            p.setHp(playerHp + heal);
        }else if(playerHp + heal > playerMaxHp) {
            p.setHp(playerMaxHp);
        }
        consumables.remove(0);
        return heal;
    }

    public String getName(){
        return name;
    }
}
