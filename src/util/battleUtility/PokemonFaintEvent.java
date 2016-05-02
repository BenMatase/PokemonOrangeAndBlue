/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 5:09:09 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: PokemonFaintEvent
 * Description: An event to be passed the GUI.  Updates things to show an
 * animation based off the fact that a pokemon has fainted.
 *
 * ****************************************
 */
package util.battleUtility;

import model.PokemonObjects.TrainerType;

/**
 * Abstract of a single pokemon fainting.
 *
 * @author Murph
 */
public class PokemonFaintEvent extends Event {

    TrainerType trainerType;

    /**
     * Constructor for a pokemon faint event.
     *
     * @param type
     */
    public PokemonFaintEvent(TrainerType type) {
        this.trainerType = type;
    }

    /**
     * Gets which pokemon in a fight faints by returning if it was a user or
     * enemy.
     *
     * @return
     */
    public TrainerType getTrainerType() {
        return trainerType;
    }
}
