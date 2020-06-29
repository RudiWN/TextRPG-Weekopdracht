package com.textrpg;

import java.util.Random;
import java.util.Scanner;

public class Game {
    boolean isRunning = false;
    boolean exceptionOccurred = false;
    Scanner scan = new Scanner(System.in);

    int readInt(String prompt, int userChoices){
        int input;

        do{
            System.out.println(prompt);
            try{
                input = Integer.parseInt(scan.next());
            }catch(Exception e){
                input = -1;
                System.out.println("Please enter an integer.");
            }
        }while(input < 1 || input > userChoices);
        return input;
    }

    void printLines(int n){
        for(int i = 0; i < n; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    void printHeader(String header){
        printLines(30);
        System.out.println(header);
        printLines(30);
    }

    void typeToContinue(){
        if(!isRunning) {
            System.out.println("Enter anything to start the game!");
            isRunning = true;
        }else if(exceptionOccurred){
            System.out.println("Enter anything to Restart the game.");
        }else{
            System.out.println("Enter anything to continue.");
        }
        scan.next();
    }

    void startGame() {
        //Re-assign isRunning and exceptionOccurred to false in case of an Exception that forces a Restart
        isRunning = false;
        exceptionOccurred = false;
        printHeader("TEST TITLE OF THE GAME");
        System.out.println("When you start the game, you will be met with random encounters.");
        printLines(30);
        typeToContinue();

        //In case isRunning is still false because of the Restart, we now give it true to enter the Game Loop.
        isRunning = true;

        //Because of InvalidLevelException, we put both the making of the Player and the Enemy a little later, in a try-catch
        try{
            Player player = new Player("Player");
            Encounter encounter = new Encounter();

            while(isRunning) {
                Enemy e = encounter.generateRandomEnemy(player);
                printLines(30);
                try{
                    encounter.encounterMessage(e);
                }catch(NullPointerException ex){
                    System.out.println(ex);
                    exceptionOccurred = true;
                    printLines(30);
                    //Ask and let the player Restart the game
                    typeToContinue();
                    startGame();
                }

                printLines(30);
                typeToContinue();
                battle(player, e, encounter);
            }
        }catch(InvalidLevelException e){
            System.out.println(e);
            printLines(30);
            System.out.println("Player's level is invalid (can't be 0)\nPlease Restart the game.");
            exceptionOccurred = true;
            printLines(30);
            //Ask and let the player Restart the game
            typeToContinue();
            startGame();
        }
    }

    public void battle(Player p, Enemy e, Encounter enc) {

        int playerDmgDone;
        int playerDmgTaken;
        boolean canCastMagic = p.getSpellSlots() != 0;
        boolean canTakePotion = p.getConsumableList().size() > 0;
        String dmgDoneMessage = "";
        String dmgTakenMessage;

        while(true){
            printHeader(e.getName() + "\nLevel: " + e.getLevel() + "\nHP: " + e.getHp() + "/" + e.getMaxHp());
            printHeader(p.getName() + "\nLevel: " + p.getLevel() + "\nHP: " + p.getHp() + "/" + p.getMaxHp());

            playerDmgDone = 0;
            //do-while loop to control the flow of combat when Player takes an action he could not take
            //for example with trying to use magic, when he has no resources to use that magic left.
            do{
                printLines(20);
                System.out.println("Choose an action:");
                printLines(20);
                System.out.println("(1) Attack\n(2) Magic Attack (" + p.getSpellSlots() + ")" +  "\n(3) Healing Potion (" + p.getConsumableList().size() + ")");
                printLines(30);

                int input = readInt("Enter your Action ", 3);

                if(input == 1){
                    playerDmgDone = p.attack() - e.getDefenseScore();
                    dmgDoneMessage = "You swung your sword and dealt " + playerDmgDone + " damage to the " + e.getName() + "!";
                    break;
                }else if(input == 2){
                    //re-evaluate if Player can cast magic or not
                    if(canCastMagic = p.getSpellSlots() != 0){
                        //if player can cast magic, we calculate the magic damage and substract a spell slot
                        //next time the re-evaluation happens, and there are no spell slots left, we can't use magic anymore
                        playerDmgDone = p.magicAttack() - e.getDefenseScore();
                        p.setSpellSlots(p.getSpellSlots() - 1);
                        dmgDoneMessage = "You cast your Fireball spell and did " + playerDmgDone + " damage to the " + e.getName() + "!";
                        break;
                    }else{
                        printLines(20);
                        System.out.println("Your are out of spell slots! You can't cast magic!");
                    }
                }else if(input == 3){
                    //re-evaluate if Player has Potions
                    if(canTakePotion = p.getConsumableList().size() > 0){
                        //If there's Potions in our Player's Consumable List, we get one and use the consume method
                        Consumable potion = p.getConsumableList().get(0);
                        int healed = potion.consume(p.getConsumableList(), p);
                        dmgDoneMessage = "Your " + potion.getName() + " healed you for " + healed + " HP!";
                        break;
                    }else{
                        printLines(20);
                        System.out.println("You have no healing potions left!");
                    }
                }
                //In case the Player tries to use a Magic Attack or Potion when they have none, we loop back to the top of this Player Action do-while
            }while(!canCastMagic || !canTakePotion);


            //In this version of the game, every Enemy will just use the Interface Fighter's attack method.
            if(p.dodge(e)){
                playerDmgTaken = 0;
                dmgTakenMessage = "The " + e.getName() + " tried punching you in the face, but you dodged it!";
            }else{
                playerDmgTaken = e.attack() - p.getDefenseScore();

                //set damage done to 0 if previous calculations end in negative numbers
                if(playerDmgDone < 0) playerDmgDone = 0;
                if(playerDmgTaken < 0) playerDmgTaken = 0;

                dmgTakenMessage = "The " + e.getName() + " punched you in the face! You took " + playerDmgTaken + " damage!";
            }

            //Subtract the damage numbers from the Player's and Enemy's health points (hp)
            e.setHp(e.getHp() - playerDmgDone);
            p.setHp(p.getHp() - playerDmgTaken);
            //Print battle info
            printLines(30);
            System.out.println(dmgDoneMessage);
            System.out.println(dmgTakenMessage);
            printLines(30);
            typeToContinue();

            //Check for Player death: means game over and end of the game through playerDied method
            //Check for Enemy death: break out of this battle
            if(p.getHp() <= 0){
                playerDied(p);
                break;
            }else if(e.getHp() <= 0){

                //Reward the Player with Experience Points and check for Level Ups
                int xpGained = enc.rewardExperience(e);
                p.setXp(p.getXp() + xpGained);
                printHeader("You have defeated the " + e.getName() + "!\nYou are awarded " + xpGained + " experience!");
                p.levelUp();

                //Reward the Player with a small heal when they earn 4 or more Experience
                if(xpGained >= 4){
                    int healReward = enc.rewardHeal();
                    p.setHp(p.getHp() + healReward);
                    System.out.println("For earning 3 or more experience you've healed " + healReward + " HP!");
                }

                System.out.println("You have gathered " + p.getXp() + "/" + p.getXpToLevel() + " experience to reach the next level!");
                printLines(30);

                //The Player has a chance to get rewarded a Healing Potion
                if(new Random().nextInt(9) + 1 > 6){
                    Consumable con = enc.rewardConsumable();
                    p.addConsumable(con);

                    System.out.println("You've been rewarded with a "  + con.getName() + "!");
                    printLines(30);
                }

                //A moment of rest in which the Player may be able to prepare for the next battle.
                boolean resting = true;
                do{
                    printHeader("The battle is over, anything you'd like to try before next battle?");
                    System.out.println("(1) Rest (" + p.getAmountOfRest() + ")\n(2) Try to restore magical resources" +  "\n(3) Brew a Greater Healing Potion");
                    printLines(30);

                    int input = readInt("Enter your Action ", 3);

                    if(input == 1){
                        //make sure we have Rests
                        if(p.getAmountOfRest() > 0){
                            int restHeal = p.restHeal();

                            //Healing
                            if(p.getHp() + restHeal > p.getMaxHp()){
                                p.setHp(p.getMaxHp());
                            }else{
                                p.setHp(p.getHp() + restHeal);
                            }
                               //Subtract a Rest
                            p.setAmountOfRest(p.getAmountOfRest() - 1);
                            printHeader("You've rested and healed " + restHeal + " HP!");
                        }else{
                            printLines(30);
                            System.out.println("You have no time to rest!");
                        }
                        break;
                    }else if(input == 2){
                        try{
                            int recoverSpellSlot = p.restoreSpellSlot(p.getSpellSlots());
                            if(recoverSpellSlot == 1){
                                p.setSpellSlots(p.getSpellSlots() + 1);
                                printHeader("You have recovered a spell slot!");
                            }else{
                                printHeader("You failed to recover a spell slot!");
                            }
                            break;
                        }catch(IllegalArgumentException exc) {
                            printLines(30);
                            System.out.println("Must be out of Spell Slots for a chance to recover.");
                        }
                    }else if(input == 3){
                        Consumable GreaterHealingPotion = p.combinePotions(p.getConsumableList(), p);
                        if(GreaterHealingPotion != null){
                            p.addConsumable(GreaterHealingPotion);
                            printLines(30);
                            System.out.println("You've brewed a Greater Healing Potion!");
                        }else{
                            printLines(30);
                            System.out.println("You've failed brewing a Greater Healing Potion!");
                        }
                        printLines(30);
                        break;
                    }
                }while(resting);
                //On Player's command, the battle is ended
                printHeader("Rest moment is over, a new challenger is approaching...");
                typeToContinue();
                break;
            }
        }
    }

    public void playerDied(Player p){
        printHeader("You have died...");
        printHeader("You've reached Level " + p.getLevel() + "\nAnd accumulated " + p.getXp() + " Experience Points!");
        printHeader("Game over!\nTry to improve your score next time!");
        isRunning = false;
    }
}
