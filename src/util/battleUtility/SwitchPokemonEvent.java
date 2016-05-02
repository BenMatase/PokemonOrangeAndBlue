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
 * Description: An event to be passed the GUI.  Updates things to show an
 * animation based off the fact that a pokemon has fainted.
 *
 * ****************************************
 */
package util.battleUtility;

import model.PokemonObjects.Pokemon;

/**
 * Event for when a pokemon is switched out.
 *
 * @author Murph
 */
public class SwitchPokemonEvent extends Event {

    Pokemon switchPokemon = null;

    /**
     * Constructor for the abstract of switching a pokemon.
     *
     * @param PokeSwitch
     */
    public SwitchPokemonEvent(Pokemon PokeSwitch) {
        this.switchPokemon = PokeSwitch;
    }

    /**
     * Gets the pokemon that will be sent out for the switch.
     *
     * @return
     */
    public Pokemon getSwitchPokemon() {
        return switchPokemon;
    }

}
