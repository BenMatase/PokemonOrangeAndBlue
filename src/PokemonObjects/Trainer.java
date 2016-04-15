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
 * Class that will be base abstraction for both user and ai pokemon trainers
 *
 * @author Jason
 */
public abstract class Trainer {

    protected String name;
    protected Pokemon[] pokemon;
    protected int numPokemon;
    protected Pokemon curPokemon; //current pokemon selected (using in battle)

    public Trainer(String name) {
        this.name = name;
        this.pokemon = new Pokemon[6];
        this.curPokemon = null; //won't select until first pokemon added
        this.numPokemon = 0;
    }

    public abstract void addPokemon(Pokemon pkmn);

    public Pokemon getPokmeon(int number) {
        return pokemon[number];
    }

    /**
     * Helper method used to determine if trainer has any living pokemon left
     *
     * @return boolean: true if trainer has any pokemon that are living
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

    public Pokemon getCurPokemon() {
        return curPokemon;
    }

    public void setCurPokemon(Pokemon curPokemon) {
        this.curPokemon = curPokemon;
    }

    public int getNumPokemon() {
        return numPokemon;
    }

}
