package DatabaseLoaderUtilities;

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

import BattleUtility.PokemonType;
import DatabaseLoaderUtilities.PokemonLoaderUtility;
import PokemonObjects.Move;
import PokemonObjects.Move.AttackType;
import PokemonObjects.Pokemon;
import PokemonObjects.TrainerType;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test class to test all of the public methods in PokemonLoaderUtility
 *
 * @author Benjamin Matase
 */
public class PokemonLoaderUtilityTest {
    /**
     * Test of getPokemonNames method, of class PokemonLoaderUtility.
     *
     * @author Benjamin Matase
     */
    @Test
    public void testGetPokemonNames() {
        System.out.println("getPokemonNames");

        //kind of ridiculous to check if this long list is the same, so
        //I compare the number of Pokemon extracted with expected value
        //I see 493 different Pokemon in xml from the id's
        int expResult = 493;

        //get real size of resulting list
        int result = PokemonLoaderUtility.getPokemonNames().size();

        //compare real and expected sizes
        assertEquals(expResult, result);
    }

    /**
     * Test of getMovesForPokemon method, of class PokemonLoaderUtility.
     *
     * @author Benjamin Matase
     */
    @Test
    public void testGetMovesForPokemon() {
        System.out.println("getMovesForPokemon");
        //again testing number of moves instead of them all being correct

        //get moves for a bulbasaur
        String pokemonName = "BULBASAUR";

        //I counted 54 from the xml
        int expResult = 54;

        //get the size of the returned list
        int result = PokemonLoaderUtility.getMovesForPokemon(
                pokemonName).size();

        //compare real and expected result
        assertEquals(expResult, result);
    }

    /**
     * Test of createMove method, of class PokemonLoaderUtility.
     *
     * @author Benjamin Matase
     */
    @Test
    public void testCreateMove() {
        System.out.println("createMove");

        //decided to use first move in list - absorb
        String moveName = "Absorb";

        //actually create move based on name
        Move move = PokemonLoaderUtility.createMove(moveName);

        //get real values from move
        String name = move.getName();
        PokemonType type = move.getDamageType();
        int dmg = move.getDamage();
        AttackType attType = move.getType();
        float acc = move.getAccuracy();

        //set expected values
        String expName = "Absorb";
        PokemonType expType = PokemonType.GRASS;
        int expDmg = 20;
        AttackType expAttType = AttackType.SPECIAL;
        float expAcc = (float) 1.00;

        //compare real and expected
        assertEquals(expName, name);
        assertEquals(expType, type);
        assertEquals(expDmg, dmg);
        assertEquals(expAttType, attType);
        assertEquals(expAcc, acc, 0.000001);
    }

    /**
     * Test of createPokemon method, of class PokemonLoaderUtility.
     *
     * Based on reading information for a Bulbasaur named Bulby.
     *
     * @author Benjamin Matase
     */
    @Test
    public void testCreatePokemon() {
        System.out.println("createPokemon");

        //chosen a bulbasaur for test with nickname of Bulby
        String expPokemonName = "BULBASAUR";
        String expNickname = "Bulby";

        List<String> moveNames = new ArrayList<String>();

        //add some arbitrary moves
        String move1 = "Vine Whip";
        String move2 = "Sludge Bomb";
        String move3 = "Leaf Storm";
        moveNames.add(move1);
        moveNames.add(move2);
        moveNames.add(move3);

        //use method to create Pokemon
        Pokemon pokemon = PokemonLoaderUtility.createPokemon(expPokemonName,
                                                             expNickname,
                                                             moveNames);

        //set all of the expected values
        int expHp = 45;
        int expAtt = 49;
        int expDef = 49;
        int expSpatt = 65;
        int expSpdef = 65;
        int expSpd = 45;

        PokemonType expType1 = PokemonType.GRASS;
        PokemonType expType2 = PokemonType.POISON;

        int expCurrAtt = expHp;
        TrainerType expTrainerType = null;

        //get real values for all important attributes
        String pokemonName = pokemon.getName();
        String nickname = pokemon.getNickname();

        int hp = pokemon.getHealth();
        int att = pokemon.getAttack();
        int def = pokemon.getDefense();
        int spatt = pokemon.getSpcAttack();
        int spdef = pokemon.getSpcDefense();
        int spd = pokemon.getSpeed();

        PokemonType type1 = pokemon.getPokemonType1();
        PokemonType type2 = pokemon.getPokemonType2();

        int currHP = pokemon.getCurHealth();
        TrainerType trainerType = pokemon.getTrainer();

        Move[] moves = pokemon.getMoves();

        //compare real and expected values
        assertEquals(expPokemonName, pokemonName);
        assertEquals(expNickname, nickname);
        assertEquals(expHp, hp);
        assertEquals(expAtt, att);
        assertEquals(expDef, def);
        assertEquals(expSpatt, spatt);
        assertEquals(expSpdef, spdef);
        assertEquals(expSpd, spd);
        assertEquals(expType1.ordinal(), type1.ordinal());
        assertEquals(expType2.ordinal(), type2.ordinal());
        assertEquals(expCurrAtt, currHP);
        assertEquals(expTrainerType, trainerType);
        assertEquals(moves[0].getName(), move1);
        assertEquals(moves[1].getName(), move2);
        assertEquals(moves[2].getName(), move3);
    }

}
