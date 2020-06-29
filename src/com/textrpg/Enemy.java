package com.textrpg;

public class Enemy extends Character implements Fighter {
    Enemy(String name, int level) throws InvalidLevelException {
        super(name, level);
        setXp(level * 2);
    }

    @Override
    public int attack() {
        return 0;
    }
}
