package com.textrpg;

import java.util.Random;

public class Zombie extends Enemy implements Fighter {

    Zombie(String name, int level) throws InvalidLevelException {
        super(name, level);
    }

    @Override
    public int attack(){
        Random random = new Random();
        return random.nextInt(5) + 1;
    }
}
