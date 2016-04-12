/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 8, 2016
 * Time: 9:25:58 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: Events
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import PokemonObjects.Pokemon;
import PokemonObjects.TrainerType;

/**
 *
 * @author Murph
 */
public abstract class Events {
}

class TextOutputEvent extends Events {

    String message;

    public TextOutputEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class UpdateHealthBarEvent extends Events {

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

class AnimationEvent extends Events {

}

class PokemonFaintEvent extends Events {

    TrainerType trainerType;

    public PokemonFaintEvent(TrainerType type) {
        this.trainerType = type;
    }

    public TrainerType getTrainerType() {
        return trainerType;
    }
}

class SwitchPokemonEvent extends Events {

    Pokemon switchPokemon = null;

    public SwitchPokemonEvent(Pokemon PokeSwitch) {
        this.switchPokemon = PokeSwitch;
    }

}
