/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 5:36:29 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: PokemonTest
 * Description:
 *
 * ****************************************
 */
package PokemonObjects;

import BattleUtility.PokemonType;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jason
 */
public class PokemonTest {

    private Pokemon pkmn;

    public PokemonTest() {
    }

    @Before
    public void setUp() {
        pkmn = new Pokemon(100, 50, 50, 50,
                           50, 50, "Squirtle", null, null,
                           PokemonType.WATER, null);
    }

    @After
    public void tearDown() {
        pkmn = null;
    }

    /**
     * Test of isAlive method, of class Pokemon.
     */
    @Test
    public void testIsAlive() {
        System.out.println("isAlive");
        boolean expResult = true;
        boolean result = pkmn.isAlive();
        assertEquals(expResult, result);
    }

    /**
     * Test of reduceHealth method, of class Pokemon.
     */
    @Test
    public void testReduceHealth() {
        System.out.println("reduceHealth");
        int healthLoss = 150;
        pkmn.reduceHealth(healthLoss);
        int expResult = 0;
        int result = pkmn.getCurHealth();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMove method, of class Pokemon.
     */
    @Test
    public void testSetMove() {
        System.out.println("setMove");
        Move newMove = new Move(Move.AttackType.PHYSICAL, PokemonType.WATER, 95,
                                100, "Surf");
        int moveIndex = 0;
        pkmn.setMove(newMove, moveIndex);
        String expResult = newMove.getName();
        String result = pkmn.getMoves()[0].getName();
        assertEquals(expResult, result);
    }

}
