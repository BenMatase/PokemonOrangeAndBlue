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

import java.util.ArrayList;

/**
 * Class that will be base abstraction for both user and ai pokemon trainers
 *
 * @author Jason
 */
public abstract class Trainer {

    protected String name;
    protected ArrayList<Pokemon> pokemon;
    protected Pokemon curPokemon; //current pokemon selected (using in battle)

    public Trainer(String name) {
        this.name = name;
        this.pokemon = new ArrayList<>(6);
        this.curPokemon = null; //won't select until first pokemon added
    }

    public abstract void addPokemon(Pokemon pkmn);

    public Pokemon getPokmeon(int number) {
        return this.pokemon.get(number);
    }

    /**
     * Helper method used to determine if trainer has any living pokemon left
     *
     * @return boolean: true if trainer has any pokemon that are living
     */
    public boolean pokemonLiving() {
        for (int x = 0; x < pokemon.size(); x++) {
            if (pokemon.get(x).isAlive()) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Pokemon[] getPokemon() {
        return pokemon.toArray(new Pokemon[pokemon.size()]);
    }

    public Pokemon getCurPokemon() {
        return curPokemon;
    }

    public void setCurPokemon(Pokemon curPokemon) {
        if (this.pokemon.contains(curPokemon)) {
            this.curPokemon = curPokemon;
        }
    }

    public int getNumPokemon() {
        return pokemon.size();
    }

}
