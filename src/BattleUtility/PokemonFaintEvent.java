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
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import PokemonObjects.TrainerType;

/**
 *
 * @author Murph
 */
public class PokemonFaintEvent extends Event {

    TrainerType trainerType;

    public PokemonFaintEvent(TrainerType type) {
        this.trainerType = type;
    }

    public TrainerType getTrainerType() {
        return trainerType;
    }
}