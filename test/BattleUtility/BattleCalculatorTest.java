/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 19, 2016
 * Time: 6:11:39 AM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: BattleCalculatorTest
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import DatabaseLoaderUtilities.PokemonLoaderUtility;
import PokemonObjects.Move;
import PokemonObjects.Pokemon;
import java.util.ArrayList;
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
public class BattleCalculatorTest {

    Pokemon bulbasaur;

    Pokemon gengar;

    public BattleCalculatorTest() {
    }

    @Before
    public void setUp() {
        List<String> moves1 = new ArrayList<>();
        moves1.add("Vine Whip");
        moves1.add("Tackle");
        bulbasaur = PokemonLoaderUtility.createPokemon("BULBASAUR", "Bulby",
                                                       moves1);

        List<String> moves2 = new ArrayList<>();
        moves2.add("Shadow Punch");
        moves2.add("Payback");
        gengar = PokemonLoaderUtility.createPokemon("GENGAR", "Bobby",
                                                    moves2);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calculateDamage method, of class BattleCalculator.
     */
    @Test
    public void testDamageCalculator() {
        System.out.println("damageCalculator");

//        //get a base damage calculation based on a move with no type adv
//        BattleCalculator batCalc = new BattleCalculator(gengar, bulbasaur,
//                                                        gengar.getMoves()[0]);
//
//        double baseDmg = batCalc.calculateDamage();
        //bulbasaur attacking gengar with tackle
        testDamage(bulbasaur, gengar, bulbasaur.getMoves()[1], 0.0f);

        //
    }

    private void testDamage(Pokemon attPkmn, Pokemon defPkmn, Move move,
                            float expDmg) {
        BattleCalculator batCalc = new BattleCalculator(attPkmn, defPkmn,
                                                        move);

        double dmg = batCalc.calculateDamage();

        assertEquals(expDmg, dmg, 0.00001);
    }

    private void testUneffective() {
        //bulbasaur attacking gengar with tackle
        BattleCalculator batCalc = new BattleCalculator(bulbasaur, gengar,
                                                        bulbasaur.getMoves()[1]);

        double dmg = batCalc.calculateDamage();

        //expect 0 because normal doesn't affect ghost types
        double expDmg = 0.0;
        assertEquals(expDmg, dmg, 0.0);
    }

    /**
     * Test of getOutcome method, of class BattleCalculator.
     */
    @Test
    public void testGetOutcome() {
        System.out.println("getOutcome");
        BattleCalculator instance = null;
        String expResult = "";
        String result = instance.getOutcome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of AIMoveAdvantage method, of class BattleCalculator.
     */
    @Test
    public void testAIMoveAdvantage() {
        System.out.println("AIMoveAdvantage");
        BattleCalculator instance = null;
        double expResult = 0.0;
        double result = instance.AIMoveAdvantage();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
