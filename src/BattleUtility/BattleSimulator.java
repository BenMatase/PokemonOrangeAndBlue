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
 * Description: Simulates an interaction between two pokemon for a given
 * round in a battle.
 *
 * ****************************************
 */
package BattleUtility;

import model.PokemonObjects.Move;
import model.PokemonObjects.Pokemon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Abstract of a round in a pokemon battle.
 *
 * @author Murph
 */
public class BattleSimulator {

    private Pokemon firstPokemon;
    private Pokemon secondPokemon;
    private Move pokeMove1;
    private Move pokeMove2;

    /**
     * Instantiates the Simulation object. Sets the parameters in an order based
     * off the pokemon's speed, since differing speeds determine which pokemon
     * attacks first. In the case of a switch, input pokemon that is being
     * switched in, but place a null for its move. If both switch, place two
     * nulls.
     *
     * @param poke1 Pokemon
     * @param poke2 Pokemon
     * @param move1 Move
     * @param move2 Move
     * @author Murph
     */
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
        } else if (poke1.getSpeed() < poke2.getSpeed()) {
            this.firstPokemon = poke2;
            this.pokeMove1 = move2;
            this.secondPokemon = poke1;
            this.pokeMove2 = move1;
        } else {
            double random = Math.random();
            if (random > 0.5) {
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

    }

    /**
     * Called on BattleSimulator to gather all necessary events in an event
     * list. Takes into account both attacking, one attacking and one switching,
     * and both switching.
     *
     * @return battle Events ArrayList<Events>
     * @author Murph
     */
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

    /**
     * Private method to calculate the situation were both pokemon switch out.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
    private ArrayList<Event> simulateBothSwitch() {
        ArrayList<Event> battleEvents = new ArrayList<>();

        SwitchPokemonEvent event1 = new SwitchPokemonEvent(firstPokemon);
        TextOutputEvent event2 = new TextOutputEvent(String.format(
                "Go! %s",
                firstPokemon.getName()));
        SwitchPokemonEvent event3 = new SwitchPokemonEvent(secondPokemon);
        TextOutputEvent event4 = new TextOutputEvent(String.format(
                "Go! %s",
                secondPokemon.getName()));

        battleEvents.add(event1);
        battleEvents.add(event2);
        battleEvents.add(event3);
        battleEvents.add(event4);

        return battleEvents;
    }

    /**
     * Private method to calculate the situation were one pokemon switches out
     * and the other attacks.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
    private ArrayList<Event> simulateOneSwitch() {
        ArrayList<Event> battleEvents = new ArrayList<>();

        SwitchPokemonEvent event1 = new SwitchPokemonEvent(secondPokemon);
        TextOutputEvent event2 = new TextOutputEvent(String.format(
                "Go! %s",
                secondPokemon.getName()));

        battleEvents.add(event1);
        battleEvents.add(event2);

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

    /**
     * Private method to calculate the situation were both pokemon attack.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
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

    /**
     * Private method to calculate the sequence of events for the first battle,
     * where the first is determined by the speed of the pokemon.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
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

        secondPokemon.reduceHealth((int) firstBattle.calculateDamage());

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

    /**
     * Private method to calculate the sequence of events for the second battle,
     * where the first is determined by the speed of the pokemon.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
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

        firstPokemon.reduceHealth((int) secondBattle.calculateDamage());

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
