package com.textrpg;

public class Main {
    public static void main(String[] args) {
        Game rpg = new Game();
        rpg.startGame();
    }
}



/* Playing around with Polymorphism

        Enemy monster = new Goblin("Goblin", 10);
        System.out.println(monster.attack());

        Fighter npc = new Main().makeEnemyFighter("Goblin", 10);
        System.out.println(npc.attack());

        Goblin goblin = new Goblin("John", 10);
        System.out.println(goblin.attack());

        Fighter enemy = monster;
        */

        /* Learning that objects can be of Type of an Interface
        Fighter player = new Player("John", 8);
        player.attack();

        public Fighter makeEnemyFighter(String name, int maxHp){
            return new Goblin(name, maxHp);
        }
         */