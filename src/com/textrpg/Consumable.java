package com.textrpg;

import java.util.List;

public interface Consumable {
    int consume(List<Consumable> consumables, Player p);
    String getName();
}
