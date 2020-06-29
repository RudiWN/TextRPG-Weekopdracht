package com.textrpg;

import java.util.Random;

public class Goblin extends Enemy implements Fighter {

    Goblin(String name, int level) throws InvalidLevelException {
        super(name, level);
    }

    @Override
    public int attack(){
        Random random = new Random();
        return random.nextInt(3) + getAttackScore();
    }
}
