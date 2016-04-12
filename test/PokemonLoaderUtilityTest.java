/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 8, 2016
 * Time: 9:09:51 PM
 *
 * Project: csci205FinalProject
 * Package:
 * File: PokemonLoaderUtilityTest
 * Description:
 *
 * ****************************************
 */

import PokemonObjects.Move;
import PokemonObjects.Pokemon;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Benjamin Matase
 */
public class PokemonLoaderUtilityTest {

    public PokemonLoaderUtilityTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPokemonNames method, of class PokemonLoaderUtility.
     */
    @Test
    public void testGetPokemonNames() {
        System.out.println("getPokemonNames");

        //kind of ridiculous to check if this long list is the same, so
        //I compare the number of Pokemon extracted with expected value
        int expResult = 493;
        int result = PokemonLoaderUtility.getPokemonNames().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMovesForPokemon method, of class PokemonLoaderUtility.
     */
    @Test
    public void testGetMovesForPokemon() {
        System.out.println("getMovesForPokemon");
        //again testing number of moves vs. exact correct moves
        String pokemonName = "BULBASAUR";
        int expResult = 54;
        int result = PokemonLoaderUtility.getMovesForPokemon(
                pokemonName).size();
        assertEquals(expResult, result);
    }

    /**
     * Test of createMove method, of class PokemonLoaderUtility.
     */
    @Test
    public void testCreateMove() {
        System.out.println("createMove");
        String moveName = "";
        Move expResult = null;
        Move result = PokemonLoaderUtility.createMove(moveName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPokemon method, of class PokemonLoaderUtility.
     */
    @Test
    public void testCreatePokemon() {
        System.out.println("createPokemon");

        //chosen attributes for a bulbasaur
        String pokemonName = "BULBASAUR";
        String nickname = "Bulby";
        List<String> moveNames = new List<String>();
        moveNames.add("Vine Whip");
        moveNames.add("Sludge Bomb");
        moveNames.add("Leaf Storm");

        Pokemon expResult = null;
        Pokemon result = PokemonLoaderUtility.createPokemon(pokemonName,
                                                            nickname, moveNames);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
