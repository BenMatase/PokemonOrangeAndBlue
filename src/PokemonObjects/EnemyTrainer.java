/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 10, 2016
 * Time: 5:19:54 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: EnemyTrainer
 * Description:
 *
 * ****************************************
 */
package PokemonObjects;

/**
 *
 * @author Jason
 */
public class EnemyTrainer extends Trainer {

    private String name;
    private Pokemon[] pokemon;
    private int numPokemon;
    private String message;

    public EnemyTrainer(String name, String text) {
        super(name);
        this.message = text;

    }

    /**
     * adds pokemon to the user
     *
     * @param pkmn
     */
    @Override
    public void addPokemon(Pokemon pkmn) {
        pkmn.setTrainerType(TrainerType.NPC);
        if (numPokemon < 6) {
            pokemon[numPokemon] = pkmn;
            numPokemon++;
        }
    }

}
