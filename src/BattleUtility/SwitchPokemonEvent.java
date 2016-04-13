/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 5:09:51 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: SwitchPokemonEvent
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import PokemonObjects.Pokemon;

/**
 *
 * @author Murph
 */
public class SwitchPokemonEvent extends Event {

    Pokemon switchPokemon = null;

    public SwitchPokemonEvent(Pokemon PokeSwitch) {
        this.switchPokemon = PokeSwitch;
    }

}
