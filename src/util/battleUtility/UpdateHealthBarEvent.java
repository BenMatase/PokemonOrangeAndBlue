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
package util.battleUtility;

import model.PokemonObjects.TrainerType;

/**
 * Abstract of changing the health bar
 *
 * @author Murph
 */
public class UpdateHealthBarEvent extends Event {

    TrainerType trainerType;
    int newCurrHealth;

    /**
     *
     * @param type
     * @param healthLeft
     */
    public UpdateHealthBarEvent(TrainerType type, int healthLeft) {
        this.trainerType = type;
        this.newCurrHealth = healthLeft;
    }

    /**
     * Gets which pokemon that needs to have its health updated.
     *
     * @return
     */
    public TrainerType getTrainerType() {
        return trainerType;
    }

    /**
     * Gets the health to set that pokemon's health visual to.
     *
     * @return
     */
    public int getNewCurrHealth() {
        return newCurrHealth;
    }

}
