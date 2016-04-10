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
        List<String> expResult = null;
        List<String> result = PokemonLoaderUtility.getPokemonNames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMovesForPokemon method, of class PokemonLoaderUtility.
     */
    @Test
    public void testGetMovesForPokemon() {
        System.out.println("getMovesForPokemon");
        String pokemonName = "";
        List<String> expResult = null;
        List<String> result = PokemonLoaderUtility.getMovesForPokemon(
                pokemonName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
