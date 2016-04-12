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
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import PokemonObjects.Pokemon;
import pokemonObjects.Move;
import pokemonObjects.Move.attackType;

/**
 *
 * @author Murph
 */
public class BattleCalculator {

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

    public BattleCalculator(Pokemon AtkPoke, Pokemon DefPoke, Move move) {
        this.AtkPoke = AtkPoke;
        this.DefPoke = DefPoke;
        this.move = move;
        this.criticalModifier = getCriticalModifier();
        this.accuracyModifier = getAccuracyModifier();
    }

    public double damageCalculator() {
        AttackType attackType = move.getAttackType();
        double damage;
        if (attackType.ordinal() == attackType.SPECIAL.ordinal()) {
            damage = specialDamageCalculator();
        } else {
            damage = physicalDamageCalculator();
        }

        return damage;
    }

    private double physicalDamageCalculator() {
        double modifier = getModifier1();
        double modifier2;
        if (DefPoke.getPokemonType2() != null) {
            modifier2 = getModifier2();
            modifier = modifier * modifier2;
        }
        double stab = getStab();
        double attack = AtkPoke.getAttack();
        double defense = DefPoke.getDefense();
        double moveDmg = move.getDamage();

        double damage = ((attack / defense) * (moveDmg) * modifier
                         * stab * criticalModifier);

        return damage;
    }

    private double specialDamageCalculator() {
        double modifier = getModifier1();
        double modifier2;
        if (DefPoke.getPokemonType2() != null) {
            modifier2 = getModifier2();
            modifier = modifier * modifier2;
        }
        double stab = getStab();
        double attack = AtkPoke.getSpcAttack();
        double defense = DefPoke.getSpcDefense();
        double moveDmg = move.getDamage();

        double damage = ((attack / defense) * (moveDmg) * modifier
                         * stab * criticalModifier * accuracyModifier);

        return damage;
    }

    public String getOutcome() {
        String response = null;
        double modifier = getModifier1();
        double modifier2;
        if (DefPoke.getPokemonType2() != null) {
            modifier2 = getModifier2();
            modifier = modifier * modifier2;
        }

        if (modifier >= 2.0) {
            response += "It's super effective! \n";
        } else if (modifier <= 0.5 && modifier >= 0.0) {
            response += "It's not very effective... \n";
        } else if (accuracyModifier == 0.0) {
            response += "But it missed!";
        } else {
            response += String.format("It doesn't affect %s...",
                                      DefPoke.getName());
        }

        if (criticalModifier == 1.5 && modifier != 0.0) {
            response += "It's a critical hit!";
        }

        return response;
    }

    private double getModifier1() {
        PokemonType moveType = move.getDamageType();
        PokemonType defType = DefPoke.getPokemonType1();
        double modifier = TypeChart[moveType.ordinal()][defType.ordinal()];
        return modifier;
    }

    private double getModifier2() {
        PokemonType moveType = move.getDamageType();
        PokemonType defType = DefPoke.getPokemonType2();
        double modifier = TypeChart[moveType.ordinal()][defType.ordinal()];
        return modifier;
    }

    private double getStab() {
        double stab = 1.0;
        if (AtkPoke.getPokemonType1() == move.getDamageType()
            || AtkPoke.getPokemonType2() == move.getDamageType()) {
            stab = 1.5;
        }
        return stab;
    }

    private double getCriticalModifier() {
        double random = Math.random();
        double critMod = 1.0;
        if (random <= 0.0625) {
            critMod = 1.5;
        }
        return critMod;
    }

    private double getAccuracyModifier() {
        double random = Math.random();
        double accMod = 1.0;
        if ((move.getAccuracy() / 100.0) <= random) {
            accMod = 0.0;
        }
        return accMod;
    }
}
