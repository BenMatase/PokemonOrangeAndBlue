/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 9, 2016
 * Time: 7:16:03 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: Trainer
 * Description:
 *
 * ****************************************
 */
package PokemonObjects;

/**
 * Class that will be base parent for both user and ai pokemon trainers
 *
 * @author Jason
 */
public abstract class Trainer {

    private String name;
    private Pokemon[] pokemon;
    private int numPokemon;

    public Trainer(String name) {
        this.name = name;
        this.pokemon = new Pokemon[6];
        numPokemon = 0;
    }

    public abstract void addPokemon(Pokemon pkmn);

    public Pokemon getPokmeon(int number) {
        return pokemon[number];
    }

    /**
     * Helper method used to determine if trainer has any living pokemon left
     *
     * @return
     */
    public boolean pokemonLiving() {
        for (Pokemon poke : pokemon) {
            if (poke.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Pokemon[] getPokemon() {
        return pokemon;
    }

}
