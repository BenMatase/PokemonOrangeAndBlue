/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 7, 2016
 * Time: 10:27:58 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: BattleCalculator
 * Description: Class used to calculate all damage for a single attack
 * using stats of attacking and defending pokemon.
 *
 * ****************************************
 */
package BattleUtility;

import PokemonObjects.Move;
import PokemonObjects.Move.AttackType;
import PokemonObjects.Pokemon;

/**
 * Abstract of Damage Calculations in Pokemon
 *
 * @author Murph
 */
public class BattleCalculator {

    /**
     * Type chart based off the Pokemon type advantages. Ordered in the same way
     * as our enum to check how two types interact.
     *
     * @see <http://pokemondb.net/type>
     * @author Murph
     */
    static double[][] TypeChart
                      = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0, 1, 1, 0.5},
                         {1, 0.5, 0.5, 1, 2, 2, 1, 1, 1, 1, 1, 2, 0.5, 1, 0.5, 1, 2},
                         {1, 2, 0.5, 1, 0.5, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0.5, 1, 1},
                         {1, 1, 2, 0.5, 0.5, 1, 1, 1, 0, 2, 1, 1, 1, 1, 0.5, 1, 1},
                         {1, 0.5, 2, 1, 0.5, 1, 1, 0.5, 2, 0.5, 1, 0.5, 2, 1, 0.5, 1, 0.5},
                         {1, 0.5, 0.5, 1, 2, 0.5, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 0.5},
                         {2, 1, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 0.5, 0.5, 2, 0, 1, 2, 2},
                         {1, 1, 1, 1, 2, 1, 1, 0.5, 0.5, 1, 1, 1, 0.5, 0.5, 1, 1, 0},
                         {1, 2, 1, 2, 0.5, 1, 1, 2, 1, 0, 1, 0.5, 2, 1, 1, 1, 2},
                         {1, 1, 1, 0.5, 2, 1, 2, 1, 1, 1, 1, 2, 0.5, 1, 1, 1, 0.5},
                         {1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 0.5, 1, 1, 1, 1, 0, 0.5},
                         {1, 0.5, 1, 1, 2, 1, 0.5, 0.5, 1, 0.5, 2, 1, 1, 0.5, 1, 2, 0.5},
                         {1, 2, 1, 1, 1, 2, 0.5, 1, 0.5, 2, 1, 2, 1, 1, 1, 1, 0.5},
                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0.5, 0.5},
                         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0.5},
                         {1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 2, 1, 1, 2, 1, 0.5, 0.5},
                         {1, 0.5, 0.5, 0.5, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 0.5}
            };

    private Pokemon AtkPoke;
    private Pokemon DefPoke;
    private Move move;
    private double criticalModifier;
    private double accuracyModifier;

    /**
     * Takes in Attacking and Defending Pokemon, and the move used and sets them
     * as attributes for calculations later.
     *
     * @param AtkPoke Pokemon
     * @param DefPoke Pokemon
     * @param move Move
     * @author Murph
     */
    public BattleCalculator(Pokemon AtkPoke, Pokemon DefPoke, Move move) {
        this.AtkPoke = AtkPoke;
        this.DefPoke = DefPoke;
        this.move = move;
        this.criticalModifier = getCriticalModifier();
        this.accuracyModifier = getAccuracyModifier(move);
    }

    /**
     * Allows outside sources to set a new move to the same calculator. Added to
     * make things easier for the AI Utility.
     *
     * @param move
     * @author Murph
     */
    public void setMove(Move move) {
        this.move = move;
    }

    /**
     * Called on a damage calculator object to gather the amount of damage.
     *
     * @return damage double
     * @author Murph
     */
    public double calculateDamage() {
        AttackType attackType = move.getType();
        if (attackType.ordinal() == attackType.SPECIAL.ordinal()) {
            return calculateSpecialDamage();
        } else {
            return calculatePhysicalDamage();
        }
    }

    /**
     * Used when calculating a physical damage move.
     *
     * @return damage double
     * @author Murph
     */
    private double calculatePhysicalDamage() {
        //TODO: modifier might need a rename
        //gets the damage multiplier based on pokemon move type and opponent type(s)
        double modifier = getModifier1();
        if (DefPoke.getPokemonType2() != null) {
            double modifier2 = getModifier2();
            modifier = modifier * modifier2;
        }

        //gets the damage muliplier based on pokemon type and move type
        double stab = getStab();

        //gets stats from pokemon
        double attack = AtkPoke.getAttack();
        double defense = DefPoke.getDefense();

        //gets base damage from move
        double moveDmg = move.getDamage();

        //incorporates all mulipliers and factors to find actual damange
        double damage = ((attack / defense) * (moveDmg) * modifier
                         * stab * criticalModifier * accuracyModifier);

        return damage;
    }

    /**
     * Used when calculating a special damage move.
     *
     * @return damage double
     * @author Murph
     */
    private double calculateSpecialDamage() {
        //TODO: modifier might need a rename
        //gets the damage multiplier based on pokemon move type and opponent type(s)
        double modifier = getModifier1();
        if (DefPoke.getPokemonType2() != null) {
            double modifier2 = getModifier2();
            modifier = modifier * modifier2;
        }

        //gets the damage muliplier based on pokemon type and move type
        double stab = getStab();

        //gets stats from pokemon
        double attack = AtkPoke.getSpcAttack();
        double defense = DefPoke.getSpcDefense();

        //gets base damage from move
        double moveDmg = move.getDamage();

        //incorporates all mulipliers and factors to find actual damange
        double damage = ((1.0 / 2.0) * (attack / defense) * (moveDmg) * modifier
                         * stab * criticalModifier * accuracyModifier);

        return damage;
    }

    /**
     * Returns all the strings that the GUI should display, in order.
     *
     * @return String
     * @author Murph
     */
    public String getOutcome() {
        String response = "";
        double modifier = getModifier1();
        double modifier2;
        if (DefPoke.getPokemonType2() != null) {
            modifier2 = getModifier2();
            modifier = modifier * modifier2;
        }

        if (accuracyModifier == 0.0) {
            response += "But it missed!";
        } else if (modifier >= 2.0) {
            response += "It's super effective! \n";
        } else if (modifier < 1.0 && modifier > 0.0) {
            response += "It's not very effective... \n";
        } else if (modifier == 0.0) {
            response += String.format("It doesn't affect %s...",
                                      DefPoke.getName());
        } else if (criticalModifier == 1.5 && accuracyModifier != 0.0 && modifier != 0.0) {
            response += "It's a critical hit!";
        }

        return response;
    }

    /**
     * Used by the AI to quickly find the damage modifiers of a given move.
     *
     * @return modifier double
     * @author Murph
     */
    public double AIMoveAdvantage() {
        double modifier = getModifier1();
        double modifier2;
        if (DefPoke.getPokemonType2() != null) {
            modifier2 = getModifier2();
            modifier = modifier * modifier2;
        }
        return modifier;
    }

    /**
     * Gets the first modifier (i.e looks at primary type of defending Pokemon)
     *
     * @return modifier double
     * @author Murph
     */
    private double getModifier1() {
        PokemonType moveType = move.getDamageType();
        PokemonType defType = DefPoke.getPokemonType1();
        double modifier = TypeChart[moveType.ordinal()][defType.ordinal()];
        return modifier;
    }

    /**
     * Gets the first modifier (i.e looks at secondary type of defending
     * Pokemon)
     *
     * @return modifier double
     * @author Murph
     */
    private double getModifier2() {
        PokemonType moveType = move.getDamageType();
        PokemonType defType = DefPoke.getPokemonType2();
        double modifier = TypeChart[moveType.ordinal()][defType.ordinal()];
        return modifier;
    }

    /**
     * Gets the STAB for an attack (Same Type Attack Bonus)
     *
     * @return modifier double
     * @author Murph
     */
    private double getStab() {
        double stab = 1.0;
        if (AtkPoke.getPokemonType1() == move.getDamageType()
            || AtkPoke.getPokemonType2() == move.getDamageType()) {
            stab = 1.5;
        }
        return stab;
    }

    /**
     * Handles the randomization for critical hits, and returns the relevant
     * modifier.
     *
     * @return modifier double
     * @author Murph
     */
    private double getCriticalModifier() {
        double random = Math.random();
        double critMod = 1.0;
        if (random <= 0.0625) {
            critMod = 1.5;
        }
        return critMod;
    }

    /**
     * Calculates Accuracy statistic. Causes move to deal no damage if
     * randomization causes a miss, otherwise has no effect.
     *
     * @return modifier double
     * @author Murph
     */
    private double getAccuracyModifier(Move move) {
        double random = Math.random();
        double accMod = 1.0;
        if ((move.getAccuracy()) <= random) {
            accMod = 0.0;
        }
        return accMod;
    }
}
