/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 14, 2016
 * Time: 12:23:16 AM
 *
 * Project: csci205FinalProject
 * Package: PokemonModel
 * File: PokeModel
 * Description:
 *
 * ****************************************
 */
package PokeModel;

import DatabaseLoaderUtilities.PokemonLoaderUtility;
import PokemonObjects.EnemyTrainer;
import PokemonObjects.Trainer;
import PokemonObjects.UserTrainer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin Matase
 */
public class PokeModel {
    private UserTrainer user;
    private EnemyTrainer enemy;

    private List<Trainer> trainers;

    public PokeModel() {
        user = new UserTrainer("Ben");
        enemy = new EnemyTrainer("Brian King", "Hey.", "Bye.");

        List<String> userMoves = new ArrayList<String>();
        userMoves.add("Pound");

        List<String> enemyMoves = new ArrayList<String>();
        userMoves.add("Pound");

        user.addPokemon(PokemonLoaderUtility.createPokemon("JIGGLYPUFF",
                                                           "Jiggy Wiggy",
                                                           userMoves));
        enemy.addPokemon(PokemonLoaderUtility.createPokemon("JIGGLYPUFF",
                                                            "Dancy",
                                                            enemyMoves));
    }

    public UserTrainer getUser() {
        return user;
    }

    public void setUser(UserTrainer user) {
        this.user = user;
    }

    public EnemyTrainer getEnemy() {
        return enemy;
    }

    public void setEnemy(EnemyTrainer enemy) {
        this.enemy = enemy;
    }

}
