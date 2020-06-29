package com.textrpg;

import java.util.List;
import java.util.Random;

public class HealingPotion extends Item implements Consumable {

    HealingPotion(String name){
        super(name);
    }

    HealingPotion(){
        this("Healing Potion");
    }

    @Override
    public int consume(List<Consumable> consumables, Player p){
        int heal = new Random().nextInt(7) + 1 + p.getDefenseScore();
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
