/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 5:36:31 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: TrainerTest
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
public class TrainerTest {

    private Trainer trainer;
    private Pokemon pkmn;

    public TrainerTest() {
    }

    @Before
    public void setUp() {
        trainer = new EnemyTrainer("Enemy", "Hello", "Goodbye");
        pkmn = new Pokemon(100, 50, 50, 50,
                           50, 50, "Squirtle", null, null,
                           PokemonType.WATER, null);
        trainer.addPokemon(pkmn);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of pokemonLiving method, of class Trainer.
     */
    @Test
    public void testPokemonLiving() {
        boolean expResult = true;
        boolean result = trainer.pokemonLiving();
        assertEquals(expResult, result);
    }

}
