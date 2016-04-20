/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 20, 2016
 * Time: 5:05:33 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: MoveTest
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
 * Testing for Move class
 *
 * @author Jason
 */
public class MoveTest {

    private Move move1;
    private Move move2;

    public MoveTest() {
    }

    @Before
    public void setUp() {
        move1 = new Move(Move.AttackType.PHYSICAL, PokemonType.WATER, 50, 95,
                         "Bubble");
        move2 = new Move(Move.AttackType.SPECIAL, PokemonType.NORMAL, 0, 100,
                         "Screech");

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getType method, of class Move.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Move.AttackType expResult = Move.AttackType.PHYSICAL;
        Move.AttackType result = move1.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDamageType method, of class Move.
     */
    @Test
    public void testGetDamageType() {
        System.out.println("getDamageType");
        PokemonType expResult = PokemonType.NORMAL;
        PokemonType result = move2.getDamageType();
        assertEquals(expResult, result);
    }

}
