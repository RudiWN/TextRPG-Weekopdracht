package com.textrpg;

public interface Wizard {

    //A wizard interacts with the world with magical spells
    //we return damage dealt in an int
    int magicAttack();
    int restoreSpellSlot(int i);
}
