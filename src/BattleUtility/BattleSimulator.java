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
        if (poke1.getSpeed() > poke2.getSpeed) {
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

    public ArrayList<Events> simulate() throws IOException {

        ArrayList<Events> battleEvents = new ArrayList<>();
        ;

        ArrayList<Events> eventArray1 = getFirstBattle();

        battleEvents.addAll(eventArray1);

        if (secondPokemon.isFainted()) {
            String deadString = String.format("%s fainted!",
                                              secondPokemon.getName());
            TextOutputEvent event3 = new TextOutputEvent(deadString);
            battleEvents.add(event3);
            return battleEvents;
        }

        ArrayList<Events> eventArray2 = getSecondBattle();

        battleEvents.addAll(eventArray2);

        if (firstPokemon.isFainted()) {
            String deadString = String.format("%s fainted!",
                                              firstPokemon.getName());
            TextOutputEvent event3 = new TextOutputEvent(deadString);
            battleEvents.add(event3);
        }

        return battleEvents;
    }

    private ArrayList<Events> getFirstBattle() throws IOException {

        ArrayList<Events> battleEvents = new ArrayList<>();
        BattleCalculator firstBattle
                         = new BattleCalculator(firstPokemon, secondPokemon,
                                                pokeMove1);
        String openingText = String.format("%s used %s! \n",
                                           firstPokemon.getName(),
                                           pokeMove1.getName());

        TextOutputEvent event1 = new TextOutputEvent(openingText);

        battleEvents.add(event1);

        secondPokemon.reduceHealth(firstBattle.damageCalculator());

        UpdateHealthBarEvent event2 = new UpdateHealthBarEvent();
        battleEvents.add(event2);

        String battleText = firstBattle.getOutcome();

        BufferedReader bufReader = new BufferedReader(new StringReader(
                battleText));

        String line = null;
        while ((line = bufReader.readLine()) != null) {
            TextOutputEvent textEvent = new TextOutputEvent(line);
            battleEvents.add(textEvent);
        }

        return battleEvents;
    }

    private ArrayList<Events> getSecondBattle() throws IOException {

        ArrayList<Events> battleEvents = new ArrayList<>();
        BattleCalculator secondBattle
                         = new BattleCalculator(secondPokemon, firstPokemon,
                                                pokeMove2);
        String openingText = String.format("%s used %s! \n",
                                           secondPokemon.getName(),
                                           pokeMove2.getName());

        TextOutputEvent event1 = new TextOutputEvent(openingText);

        battleEvents.add(event1);

        firstPokemon.reduceHealth(secondBattle.damageCalculator());

        UpdateHealthBarEvent event2 = new UpdateHealthBarEvent();
        battleEvents.add(event2);

        String battleText = secondBattle.getOutcome();

        BufferedReader bufReader = new BufferedReader(new StringReader(
                battleText));

        String line = null;
        while ((line = bufReader.readLine()) != null) {
            TextOutputEvent textEvent = new TextOutputEvent(line);
            battleEvents.add(textEvent);
        }

        return battleEvents;
    }

}
