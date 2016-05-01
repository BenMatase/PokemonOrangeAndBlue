/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 20, 2016
 * Time: 4:50:02 PM
 *
 * Project: csci205FinalProject
 * Package: TrainerCreator
 * File: RandomCreatorUtility
 * Description:
 *
 * ****************************************
 */
package util.TrainerCreator;

import util.DatabaseLoaderUtilities.PokemonLoaderUtility;
import model.PokemonObjects.EnemyTrainer;
import model.PokemonObjects.Pokemon;
import model.PokemonObjects.Trainer;
import model.PokemonObjects.UserTrainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A Utility class to handle the creation of random Pokemon and trainers.
 *
 * @author Benjamin Matase
 */
public class RandomCreatorUtility {

    /**
     * TODO: have arguments to set greeting and goodbye message
     *
     * Gets an EnemyTrainer with name and with numPkmn amount of random Pokemon
     * on its team. Also gives default greeting and goodbye of "Hello!" and
     * "Goodbye!"
     *
     * @param numPkmn An integer between 1 and 6 specifying how many Pokemon.
     * @param name The desired name of the trainer.
     * @return An NPC with random Pokemon with random moves.
     *
     * @author Benjamin Matase
     */
    public static EnemyTrainer getRandomNPC(int numPkmn, String name) {
        //TODO: check if numPkmn is between 1 and 6
        EnemyTrainer enemy = new EnemyTrainer(name, "Hello!", "Goodbye!");
        fillRandomTrainer(numPkmn, enemy);
        return enemy;
    }

    /**
     * Gets a UserTrainer with name and with numPkmn amount of random Pokemon on
     * its team.
     *
     * @param numPkmn An integer between 1 and 6 specifying how many Pokemon.
     * @param name The desired name of the user.
     * @return A UserTrainer with random Pokemon with random moves.
     *
     * @author Benjamin Matase
     */
    public static UserTrainer getRandomUser(int numPkmn, String name) {
        //TODO: check if numPkmn is between 1 and 6
        UserTrainer user = new UserTrainer(name);
        fillRandomTrainer(numPkmn, user);
        return user;
    }

    /**
     * Gets a randomized Pokemon. Randomizes the species and the four moves for
     * that Pokemon.
     *
     * @return A Pokemon with a random species and random four moves.
     *
     * @author Benjamin Matase
     */
    public static Pokemon getRandomPokemon() {
        //TODO: get rid of need to load all Pokemon and moves everytime
        List<String> allPokemon = PokemonLoaderUtility.getPokemonNames();

        Random random = new Random();

        List<String> pkmnMoves = new ArrayList<>();

        //get which pokemon to create
        String pkmnName = allPokemon.get(random.nextInt(allPokemon.size()));

        //get all possible moves
        List<String> allMoves = PokemonLoaderUtility.getMovesForPokemon(
                pkmnName);

        //loop to repeat until get 4 damaging moves
        while (true) {
            //get a random move
            pkmnMoves.add(allMoves.get(random.nextInt(allMoves.size())));

            //once get 4, break out
            if (pkmnMoves.size() == 4) {
                break;
            }
        }

        //create and return Pokemon
        return PokemonLoaderUtility.createPokemon(pkmnName, pkmnName,
                                                  pkmnMoves);
    }

    /**
     * Fill trainer with numPkmn amount of random Pokemon.
     *
     * @param numPkmn The number of Pokemon to add to trainer's team. Must be
     * between 1 and 6.
     * @param trainer The trainer that will have the randomized Pokemon added
     * to.
     *
     * @author Benjamin Matase
     */
    private static void fillRandomTrainer(int numPkmn, Trainer trainer) {
        //get numPkmn randomized and add to trainer
        for (int iPkmn = 0; iPkmn < numPkmn; iPkmn++) {
            trainer.addPokemon(getRandomPokemon());
        }
    }

}
