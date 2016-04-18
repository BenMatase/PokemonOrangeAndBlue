/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 5:07:46 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: UpdateHealthBarEvent
 * Description: An event to be passed the GUI.  Updates things to show
 * the current health value of a pokemon.
 *
 * ****************************************
 */
package BattleUtility;

import PokemonObjects.TrainerType;

/**
 * Abstract of changing the health bar
 *
 * @author Murph
 */
public class UpdateHealthBarEvent extends Event {

    TrainerType trainerType;
    int newCurrHealth;

    public UpdateHealthBarEvent(TrainerType type, int healthLeft) {
        this.trainerType = type;
        this.newCurrHealth = healthLeft;
    }

    public TrainerType getTrainerType() {
        return trainerType;
    }

    public int getNewCurrHealth() {
        return newCurrHealth;
    }

}
