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

import DatabaseLoaderUtilities.PokemonLoaderUtility;
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
public class TrainerTest {

    private Trainer trainer;
    private Pokemon pkmn;

    public TrainerTest() {
    }

    @Before
    public void setUp() {
        trainer = new EnemyTrainer("Enemy", "Hello", "Goodbye");
        List<String> moves = new ArrayList<>();
        moves.add("Tackle");
        moves.add("Tail Whip");
        moves.add("Bubble");

        pkmn = PokemonLoaderUtility.createPokemon("SQUIRTLE", "Squirty", moves);
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
