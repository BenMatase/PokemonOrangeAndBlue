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
package PokemonObjects;

import BattleUtility.PokemonType;

/**
 *
 * @author Jason
 */
public class Move {
    //TODO: added Other and should probably move elsewhere where all classes can see
    public enum AttackType {
        SPECIAL, PHYSICAL, OTHER
    };

    private final AttackType type;
    private final PokemonType damageType;
    private final int damage;
    private final int accuracy;
    private final String name;

    public Move(AttackType type, PokemonType damageType, int damage,
                int accuracy, String name) {
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

    public int getAccuracy() {
        return accuracy;
    }

    public String getName() {
        return name;
    }

}
