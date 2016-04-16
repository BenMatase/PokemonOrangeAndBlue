/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 8, 2016
 * Time: 12:24:31 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: BattleSimulator
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import PokemonObjects.Move;
import PokemonObjects.Pokemon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author Murph
 */
public class BattleSimulator {

    private Pokemon firstPokemon;
    private Pokemon secondPokemon;
    private Move pokeMove1;
    private Move pokeMove2;

    public BattleSimulator(Pokemon poke1, Pokemon poke2, Move move1, Move move2) {
        if (move1 == null) {
            this.firstPokemon = poke2;
            this.pokeMove1 = move2;
            this.secondPokemon = poke1;
            this.pokeMove2 = move1;
        } else if (move2 == null) {
            this.firstPokemon = poke1;
            this.pokeMove1 = move1;
            this.secondPokemon = poke2;
            this.pokeMove2 = move2;
        } else if (poke1.getSpeed() > poke2.getSpeed()) {
            this.firstPokemon = poke1;
            this.pokeMove1 = move1;
            this.secondPokemon = poke2;
            this.pokeMove2 = move2;
        } else {
            this.firstPokemon = poke2;
            this.pokeMove1 = move2;
            this.secondPokemon = poke1;
            this.pokeMove2 = move1;
        }

    }

    public ArrayList<Event> simulate() {
        ArrayList<Event> battleEvents = new ArrayList<>();

        if (pokeMove1 == null && pokeMove2 == null) {
            battleEvents = simulateBothSwitch();
        } else if (pokeMove1 == null || pokeMove2 == null) {
            battleEvents = simulateOneSwitch();
        } else {
            battleEvents = simulateNoSwitch();
        }
        return battleEvents;
    }

    private ArrayList<Event> simulateBothSwitch() {
        ArrayList<Event> battleEvents = new ArrayList<>();

        SwitchPokemonEvent event1 = new SwitchPokemonEvent(firstPokemon,
                                                           String.format(
                                                                   "Go! %s",
                                                                   firstPokemon.getName()));
        SwitchPokemonEvent event3 = new SwitchPokemonEvent(secondPokemon,
                                                           String.format(
                                                                   "Go! %s",
                                                                   secondPokemon.getName()));

        battleEvents.add(event1);
        battleEvents.add(event3);

        return battleEvents;
    }

    private ArrayList<Event> simulateOneSwitch() {
        ArrayList<Event> battleEvents = new ArrayList<>();

        SwitchPokemonEvent event1 = new SwitchPokemonEvent(secondPokemon,
                                                           String.format(
                                                                   "Go! %s",
                                                                   secondPokemon.getName()));

        battleEvents.add(event1);

        ArrayList<Event> eventArray1 = getFirstBattle();

        battleEvents.addAll(eventArray1);

        if (!secondPokemon.isAlive()) {
            String deadString = String.format("%s fainted!",
                                              secondPokemon.getName());
            TextOutputEvent event3 = new TextOutputEvent(deadString);
            PokemonFaintEvent event4 = new PokemonFaintEvent(
                    secondPokemon.getTrainer());
            battleEvents.add(event3);
            battleEvents.add(event4);
        }

        return battleEvents;
    }

    private ArrayList<Event> simulateNoSwitch() {

        ArrayList<Event> battleEvents = new ArrayList<>();

        ArrayList<Event> eventArray1 = getFirstBattle();

        battleEvents.addAll(eventArray1);

        if (!secondPokemon.isAlive()) {
            String deadString = String.format("%s fainted!",
                                              secondPokemon.getName());
            TextOutputEvent event3 = new TextOutputEvent(deadString);
            PokemonFaintEvent event4 = new PokemonFaintEvent(
                    secondPokemon.getTrainer());
            battleEvents.add(event3);
            battleEvents.add(event4);
            return battleEvents;
        }

        ArrayList<Event> eventArray2 = getSecondBattle();

        battleEvents.addAll(eventArray2);

        if (!firstPokemon.isAlive()) {
            String deadString = String.format("%s fainted!",
                                              firstPokemon.getName());
            TextOutputEvent event3 = new TextOutputEvent(deadString);
            PokemonFaintEvent event4 = new PokemonFaintEvent(
                    firstPokemon.getTrainer());
            battleEvents.add(event3);
            battleEvents.add(event4);
        }

        return battleEvents;
    }

    private ArrayList<Event> getFirstBattle() {

        ArrayList<Event> battleEvents = new ArrayList<>();
        BattleCalculator firstBattle
                         = new BattleCalculator(firstPokemon, secondPokemon,
                                                pokeMove1);
        String openingText = String.format("%s used %s! \n",
                                           firstPokemon.getName(),
                                           pokeMove1.getName());

        TextOutputEvent event1 = new TextOutputEvent(openingText);

        battleEvents.add(event1);

        secondPokemon.reduceHealth((int) firstBattle.damageCalculator());

        UpdateHealthBarEvent event2
                             = new UpdateHealthBarEvent(
                        secondPokemon.getTrainer(), secondPokemon.getCurHealth());
        battleEvents.add(event2);

        String battleText = firstBattle.getOutcome();

        try {
            BufferedReader bufReader = new BufferedReader(new StringReader(
                    battleText));

            String line = null;
            while ((line = bufReader.readLine()) != null) {
                TextOutputEvent textEvent = new TextOutputEvent(line);
                battleEvents.add(textEvent);
            }
        } catch (IOException e) {
        }

        return battleEvents;
    }

    private ArrayList<Event> getSecondBattle() {

        ArrayList<Event> battleEvents = new ArrayList<>();
        BattleCalculator secondBattle
                         = new BattleCalculator(secondPokemon, firstPokemon,
                                                pokeMove2);
        String openingText = String.format("%s used %s! \n",
                                           secondPokemon.getName(),
                                           pokeMove2.getName());

        TextOutputEvent event1 = new TextOutputEvent(openingText);

        battleEvents.add(event1);

        firstPokemon.reduceHealth((int) secondBattle.damageCalculator());

        UpdateHealthBarEvent event2 = new UpdateHealthBarEvent(
                firstPokemon.getTrainer(), firstPokemon.getCurHealth());
        battleEvents.add(event2);

        String battleText = secondBattle.getOutcome();
        try {
            BufferedReader bufReader = new BufferedReader(new StringReader(
                    battleText));

            String line = null;
            while ((line = bufReader.readLine()) != null) {
                TextOutputEvent textEvent = new TextOutputEvent(line);
                battleEvents.add(textEvent);
            }
        } catch (IOException e) {
        }

        return battleEvents;
    }

}
