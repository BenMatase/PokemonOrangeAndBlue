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

/**
 *
 * @author Jason
 */
public class Move {

    public enum attackType {
        SPECIAL, PHYSICAL
    };

    private attackType type;
    private int damage;
    private int accuracy;

    public Move(attackType type, int damage, int accuarcy) {
        this.type = type;
        this.damage = damage;
        this.accuracy = accuracy;
    }
}
