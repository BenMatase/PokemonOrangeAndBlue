/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 10:23:57 AM
 *
 * Project: csci205FinalProject
 * Package: PokemonController
 * File: BattleControl
 * Description: Abstract of entire battle.  Considers trainers and all
 * possible options each can take.  Uses AIUtility and Battle Simulator.
 *
 * ****************************************
 */
package PokemonController;

import BattleUtility.AIUtility;
import BattleUtility.BattleSimulator;
import BattleUtility.EnemyDefeatEvent;
import BattleUtility.Event;
import BattleUtility.SwitchPokemonEvent;
import BattleUtility.TextOutputEvent;
import BattleUtility.UserDefeatEvent;
import PokeModel.PokeModel;
import PokemonObjects.EnemyTrainer;
import PokemonObjects.Move;
import PokemonObjects.Pokemon;
import PokemonObjects.UserTrainer;
import java.util.ArrayList;

/**
 * Controller for a given Pokemon battle abstract. Takes in a pokemon model.
 *
 * @author Murph
 */
public class BattleControl {

    UserTrainer user;
    EnemyTrainer enemy;

    /**
     * Constructor takes in a pokemon model and extracts the Trainers.
     *
     * @param myModel
     * @author Murph
     */
    public BattleControl(PokeModel myModel) {
        this.user = myModel.getUser();
        this.enemy = myModel.getEnemy();
    }

    /**
     * Called on the battle simulator to get the opening events for the battle.
     *
     * @return events ArrayList<Event>
     * @author Murph
     */
    public ArrayList<Event> getInitialMessage() {
        ArrayList<Event> events = new ArrayList<>();
        TextOutputEvent event1 = new TextOutputEvent(String.format(
                "%s has challenged you to battle!", enemy.getName()));
        SwitchPokemonEvent event3 = new SwitchPokemonEvent(enemy.getCurPokemon());
        TextOutputEvent event4 = new TextOutputEvent(
                String.format(
                        "%s sent out %s!",
                        enemy.getName(),
                        enemy.getCurPokemon().getName()));
        SwitchPokemonEvent event5 = new SwitchPokemonEvent(user.getCurPokemon());
        TextOutputEvent event6 = new TextOutputEvent(String.format("Go! %s!",
                                                                   user.getCurPokemon().getNickname()));

        events.add(event1);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        return events;
    }

    /**
     * Called on the controller to get events for when user chooses to switch
     * their pokemon.
     *
     * @param newCurrPokemon
     * @return battle events ArrayList<Event>
     * @author Murph
     */
    public ArrayList<Event> chooseSwitchPokemon(Pokemon newCurrPokemon) {
        if (newCurrPokemon == user.getCurPokemon()) {
            ArrayList<Event> events = new ArrayList<>();
            TextOutputEvent event1 = new TextOutputEvent(String.format(
                    "% is already in battle", user.getCurPokemon().getName()));
            events.add(event1);
            return events;
        }
        user.setCurPokemon(newCurrPokemon);
        BattleSimulator switchBattle = new BattleSimulator(
                user.getCurPokemon(), enemy.getCurPokemon(),
                null, AIUtility.getMove(user, enemy));

        return switchBattle.simulate();
    }

    /**
     * Called on the controller to get events for when user chooses to fight.
     *
     * @param chosenMove
     * @return battle events ArrayList<Event>
     * @author Murph
     */
    public ArrayList<Event> chooseAttack(Move chosenMove) {
        BattleSimulator switchBattle = new BattleSimulator(
                user.getCurPokemon(), enemy.getCurPokemon(),
                chosenMove,
                AIUtility.getMove(user, enemy));
        ArrayList<Event> events = switchBattle.simulate();
        if (!enemy.pokemonLiving()) {
            ArrayList<Event> victoryEvents = makeEnemyDefeatEvents();
            events.addAll(victoryEvents);
        } else if (!user.pokemonLiving()) {
            ArrayList<Event> defeatEvents = makeUserDefeatEvents();
            events.addAll(defeatEvents);
        }
        return events;
    }

    /**
     * Called on the controller to get the events for when user chooses to run.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
    public ArrayList<Event> chooseRun() {
        TextOutputEvent event1 = new TextOutputEvent(
                "You cannot run from a Trainer Battle!");
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        return events;
    }

    public ArrayList<Event> switchNewPokemon(Pokemon mon) {
        TextOutputEvent event1 = new TextOutputEvent(String.format(
                "You sent out %s", mon.getNickname()));
        SwitchPokemonEvent event2 = new SwitchPokemonEvent(mon);
        user.setCurPokemon(mon);
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        return events;
    }

    /**
     * Called on the controller to get the events for when user choose to get an
     * item.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
    public ArrayList<Event> chooseItem() {
        TextOutputEvent event1 = new TextOutputEvent(
                "You're a college student! You don't have shit!");
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        return events;
    }

    /**
     * Private method to gather the events for when a user is defeated.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
    private ArrayList<Event> makeUserDefeatEvents() {
        TextOutputEvent event1 = new TextOutputEvent(String.format(
                "%s is out of usable pokemon!", user.getName()));
        TextOutputEvent event2 = new TextOutputEvent(String.format(
                "%s fainted!", user.getName()));
        UserDefeatEvent event3 = new UserDefeatEvent();
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        return events;
    }

    /**
     * Private method to gather the events for when an enemy is defeated.
     *
     * @return battle events ArrayList<Event>
     * @author Murph
     */
    private ArrayList<Event> makeEnemyDefeatEvents() {
        TextOutputEvent event1 = new TextOutputEvent(String.format(
                "You have defeated %s!", enemy.getName()));
        EnemyDefeatEvent event2 = new EnemyDefeatEvent();
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        return events;
    }
}
