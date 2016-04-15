/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 10, 2016
 * Time: 3:50:49 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: UserTrainer
 * Description:
 *
 * ****************************************
 */
package PokemonObjects;

/**
 * Class that will represent the UserTrainer and hold its pokemon
 *
 * @author Jason
 */
public class UserTrainer extends Trainer {

//    private String name;
//    private Pokemon[] pokemon;
//    private int numPokemon;
//    private Pokemon curPokemon;
    public UserTrainer(String name) {
        super(name);
    }

    /**
     * adds pokemon to the user
     *
     * @param pkmn The pokemon to add
     */
    @Override
    public void addPokemon(Pokemon pkmn) {
        pkmn.setTrainerType(TrainerType.USER);
        if (numPokemon < 6) {
            pokemon[numPokemon] = pkmn;
            numPokemon++;
        }
        if (this.curPokemon == null) {
            this.setCurPokemon(pokemon[0]);
        }
    }
}
