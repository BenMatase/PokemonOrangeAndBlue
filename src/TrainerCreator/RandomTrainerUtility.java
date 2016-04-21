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
 * File: RandomTrainerUtility
 * Description:
 *
 * ****************************************
 */
package TrainerCreator;

import DatabaseLoaderUtilities.PokemonLoaderUtility;
import PokemonObjects.EnemyTrainer;
import PokemonObjects.Pokemon;
import PokemonObjects.Trainer;
import PokemonObjects.UserTrainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Benjamin Matase
 */
public class RandomTrainerUtility {

    /**
     * TODO: have arguments to set greeting and goodbye message
     *
     * @param numPkmn
     * @param name
     * @return
     */
    public static EnemyTrainer getRandomNPC(int numPkmn, String name) {
        EnemyTrainer enemy = new EnemyTrainer(name, "Hello!", "Goodbye!");
        fillRandomTrainer(numPkmn, enemy);
        return enemy;
    }

    public static UserTrainer getRandomUser(int numPkmn, String name) {
        UserTrainer user = new UserTrainer(name);
        fillRandomTrainer(numPkmn, user);
        return user;
    }

    private static void fillRandomTrainer(int numPkmn, Trainer trainer) {
        //TODO: get rid of need to load all Pokemon and moves everytime
        List<String> allPokemon = PokemonLoaderUtility.getPokemonNames();

        Random random = new Random();

        //get each pokemon
        for (int iPkmn = 0; iPkmn < numPkmn; iPkmn++) {
            List<String> pkmnMoves = new ArrayList<>();

            //get which pokemon to create
            String pkmnName = allPokemon.get(random.nextInt(allPokemon.size()));

            //get all possible moves
            List<String> allMoves = PokemonLoaderUtility.getMovesForPokemon(
                    pkmnName);

            //get 4 random moves from all possible moves
            for (int iMove = 0; iMove < 4; iMove++) {
                String moveName = allMoves.get(random.nextInt(allMoves.size()));
                pkmnMoves.add(moveName);
            }

            Pokemon pkmn = PokemonLoaderUtility.createPokemon(pkmnName, pkmnName,
                                                              pkmnMoves);

            trainer.addPokemon(pkmn);
        }
    }

}
