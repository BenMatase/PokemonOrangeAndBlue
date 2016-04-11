/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 8, 2016
 * Time: 5:34:31 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: Move
 * Description:
 *
 * ****************************************
 */
package pokemonObjects;

import BattleUtility.PokemonType;

/**
 *
 * @author Jason
 */
public class Move {

    public enum attackType {
        SPECIAL, PHYSICAL
    };

    private final attackType type;
    private final PokemonType damageType;
    private final int damage;
    private final int accuracy;
    private final String name;

    public Move(attackType type, PokemonType damageType, int damage,
                int accuracy, String name) {
        this.type = type;
        this.damageType = damageType;
        this.damage = damage;
        this.accuracy = accuracy;
        this.name = name;
    }

    public attackType getType() {
        return type;
    }

    public PokemonType getDamageType() {
        return damageType;
    }

    public int getDamage() {
        return damage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getName() {
        return name;
    }

}
