/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 16, 2016
 * Time: 5:23:55 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: AIUtilityTest
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import DatabaseLoaderUtilities.PokemonLoaderUtility;
import PokemonObjects.EnemyTrainer;
import PokemonObjects.Move;
import PokemonObjects.Pokemon;
import PokemonObjects.UserTrainer;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jason
 */
public class AIUtilityTest {

    private UserTrainer userTrainer;
    private Pokemon pkmn;
    private EnemyTrainer enemyTrainer;
    private Pokemon pkmn2;

    public AIUtilityTest() {
    }

    @Before
    public void setUp() {
        enemyTrainer = new EnemyTrainer("Enemy", "Hello", "Goodbye");
        List<String> moves = new ArrayList<>();
        moves.add("Tackle");
        moves.add("Bubble");
        pkmn = PokemonLoaderUtility.createPokemon("SQUIRTLE", "Squirty", moves);
        enemyTrainer.addPokemon(pkmn);
        enemyTrainer.setCurPokemon(pkmn);

        userTrainer = new UserTrainer("User");
        List<String> moves2 = new ArrayList<>();
        moves2.add("Tackle");
        moves2.add("Vine Whip");
        moves2.add("Cut");
        pkmn = PokemonLoaderUtility.createPokemon("BULBASAUR", "Bulb", moves2);
        enemyTrainer.addPokemon(pkmn);
        enemyTrainer.setCurPokemon(pkmn);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMove method, of class AIUtility.
     */
    @Test
    public void testGetMove() {
        System.out.println("getMove");
        Move result = AIUtility.getMove(userTrainer, enemyTrainer);
        if (result != enemyTrainer.getCurPokemon().getMoves()[0]
            && result != enemyTrainer.getCurPokemon().getMoves()[1]
            && result != enemyTrainer.getCurPokemon().getMoves()[2]) {
            throw new ArrayIndexOutOfBoundsException("Error occured");
        }
        System.out.println("Move selected: " + result.getName());
    }
}
