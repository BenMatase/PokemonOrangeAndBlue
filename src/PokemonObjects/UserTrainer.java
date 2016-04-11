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
 *
 * @author Jason
 */
public class UserTrainer extends Trainer {

    private String name;
    private Pokemon[] pokemon;
    private int numPokemon;

    public UserTrainer(String name) {
        super(name);
    }

    /**
     * adds pokemon to the user
     *
     * @param pkmn
     */
    @Override
    public void addPokemon(Pokemon pkmn) {
        pkmn.setTrainerType(TrainerType.USER);
        if (numPokemon < 6) {
            pokemon[numPokemon] = pkmn;
            numPokemon++;
        }
    }
}
