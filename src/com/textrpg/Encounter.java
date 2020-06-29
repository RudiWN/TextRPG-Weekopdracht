package com.textrpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Encounter {

    public Enemy generateRandomEnemy(Player p){
        Random random = new Random();
        List<Enemy> enemies = new ArrayList<>();

        try{
            Goblin goblin = new Goblin("Goblin", random.nextInt(p.getLevel()) + 1);
            Zombie zombie = new Zombie("Zombie", random.nextInt(p.getLevel()) + 2);
            Goblin goblinHigherLevel = new Goblin("Goblin", random.nextInt(p.getLevel()) + 2);
            Zombie zombieHigherLevel = new Zombie("Zombie", random.nextInt(p.getLevel()) + 3);

            enemies.add(goblin);
            enemies.add(zombie);

            if(p.getLevel() > 3) {
                enemies.add(goblinHigherLevel);
                enemies.add(zombieHigherLevel);
            }
            return enemies.get(random.nextInt(enemies.size()));
        }catch(InvalidLevelException e){
            System.out.println(e);
        }
        //Had to return something outside the scope of try-catch, not sure this is a good idea though
        return null;
    }

    public Consumable rewardConsumable(){
        //In this version, we'll probably only have a Healing Potion. Will expand to hand out a random Consumable.
        return new HealingPotion("Healing Potion");
    }

    public int rewardExperience(Enemy e){
        return e.getXp();
    }

    public int rewardHeal(){
        return new Random().nextInt(5) + 1;
    }

    public void encounterMessage(Enemy e) {
        if(e == null){
            throw new NullPointerException("\nSomething went wrong and no enemy has been found.\nPlease Restart the game.");
        }else{
            System.out.println("You've encountered a level " + e.getLevel() + " " + e.getName() + "!");
        }
    }
}
