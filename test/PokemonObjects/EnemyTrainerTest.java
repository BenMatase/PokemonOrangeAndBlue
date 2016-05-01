/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 5:36:27 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: EnemyTrainerTest
 * Description:
 *
 * ****************************************
 */
package PokemonObjects;

import model.PokemonObjects.EnemyTrainer;
import model.PokemonObjects.Pokemon;
import model.PokemonObjects.TrainerType;
import util.DatabaseLoaderUtilities.PokemonLoaderUtility;
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
public class EnemyTrainerTest {

    public EnemyTrainerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addPokemon method, of class EnemyTrainer.
     */
    @Test
    public void testAddPokemon() {
        System.out.println("addPokemon");
        List<String> moves = new ArrayList<>();
        moves.add("Tackle");
        moves.add("Tail Whip");
        moves.add("Bubble");

        Pokemon pkmn = PokemonLoaderUtility.createPokemon("SQUIRTLE", "Squirty",
                                                          moves);

        pkmn.setTrainerType(TrainerType.NPC);
        EnemyTrainer instance = new EnemyTrainer(null, null, null);
        instance.addPokemon(pkmn);
        assertEquals(pkmn.getTrainer().ordinal(),
                     instance.getCurPokemon().getTrainer().ordinal());
    }

}
