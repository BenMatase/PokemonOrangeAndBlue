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
package model.PokemonObjects;

import BattleUtility.PokemonType;

/**
 * Class representation of a move that pokemon will hold
 *
 * @author Jason
 */
public class Move {
    public enum AttackType {
        SPECIAL, PHYSICAL, OTHER
    };

    private final AttackType type; //determines which pokemon stat determines total damage delt
    private final PokemonType damageType; // determines which pokemon type determines total damage delt
    private final int damage;
    private final float accuracy;
    private final String name;

    /**
     * Constructor for a Move
     *
     * @param type
     * @param damageType
     * @param damage
     * @param accuracy
     * @param name
     *
     * @author Jason Corriveau
     */
    public Move(AttackType type, PokemonType damageType, int damage,
                float accuracy, String name) {
        this.type = type;
        this.damageType = damageType;
        this.damage = damage;
        this.accuracy = accuracy;
        this.name = name;
    }

    public AttackType getType() {
        return type;
    }

    public PokemonType getDamageType() {
        return damageType;
    }

    public int getDamage() {
        return damage;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public String getName() {
        return name;
    }

}
