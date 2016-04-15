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
 * Description:
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
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Murph
 */
public class BattleControl {

    UserTrainer user;
    EnemyTrainer enemy;

    public BattleControl(PokeModel myModel) {
        this.user = myModel.getUser();
        this.enemy = myModel.getEnemy();
    }

    public ArrayList<Event> getInitialMessage() {
        ArrayList<Event> events = new ArrayList<>();
        TextOutputEvent event1 = new TextOutputEvent(String.format(
                "%s has challenged you to battle!", enemy.getName()));
        TextOutputEvent event2 = new TextOutputEvent(String.format(
                "%s sent out %s!", enemy.getName(), enemy.getCurPokemon()));
        SwitchPokemonEvent event3 = new SwitchPokemonEvent(enemy.getCurPokemon());
        TextOutputEvent event4 = new TextOutputEvent(String.format(
                "Go! %s!", user.getCurPokemon()));
        SwitchPokemonEvent event5 = new SwitchPokemonEvent(user.getCurPokemon());
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        return events;
    }

    public ArrayList<Event> chooseSwitchPokemon(Pokemon newCurrPokemon) throws IOException {
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

    public ArrayList<Event> chooseAttack(Move chosenMove) throws IOException {
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

    public ArrayList<Event> chooseRun() {
        TextOutputEvent event1 = new TextOutputEvent(
                "You cannot run from a Trainer Battle!");
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        return events;
    }

    public ArrayList<Event> chooseItem() {
        TextOutputEvent event1 = new TextOutputEvent(
                "You're a college student! You don't have shit!");
        ArrayList<Event> events = new ArrayList<>();
        events.add(event1);
        return events;
    }

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
