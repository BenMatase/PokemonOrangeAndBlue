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

import util.DatabaseLoaderUtilities.PokemonLoaderUtility;
import model.PokemonObjects.EnemyTrainer;
import model.PokemonObjects.Move;
import model.PokemonObjects.Pokemon;
import model.PokemonObjects.UserTrainer;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
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
        moves.add("Vine Whip");
        moves.add("Cut");
        List<String> moves2 = new ArrayList<>();
        moves2.add("Tackle");
        moves2.add("Bubble");
        pkmn = PokemonLoaderUtility.createPokemon("SQUIRTLE", "Squirty", moves2);
        pkmn2 = PokemonLoaderUtility.createPokemon("BULBASAUR", "Bulb", moves);
        enemyTrainer.addPokemon(pkmn2);
        enemyTrainer.addPokemon(pkmn);
        enemyTrainer.setCurPokemon(enemyTrainer.getPokmeon(0));

        userTrainer = new UserTrainer("User");

        userTrainer.addPokemon(pkmn);
        userTrainer.setCurPokemon(pkmn);

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

    /**
     * Test of getPokmeon method, of class AIUtilty
     */
    @Test
    public void testGetPokmeon() {
        System.out.println("getPokemon");
        enemyTrainer.getCurPokemon().setCurHealth(0);
        Pokemon expResult = enemyTrainer.getPokmeon(1);
        Pokemon result = AIUtility.getPokemon(enemyTrainer);
        System.out.println(result.getName());
        assertEquals(result, expResult);
    }
}
