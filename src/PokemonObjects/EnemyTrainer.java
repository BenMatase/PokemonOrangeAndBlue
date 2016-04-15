/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 10, 2016
 * Time: 5:19:54 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: EnemyTrainer
 * Description:
 *
 * ****************************************
 */
package PokemonObjects;

/**
 * Class that is used to represent enemy trainers that a user can battle
 * against.
 *
 * @author Jason
 */
public class EnemyTrainer extends Trainer {

    private String name; //name of EnemyTrainer
    private Pokemon[] pokemon;
    private int numPokemon;
    private String OverworldMessage; //message Enemy says when user talks to the in open world
    private String battleEndMessage; //message Enemy says when they lose in a battle
    private Pokemon curPokemon; //current pokemon selected for battle

    public EnemyTrainer(String name, String OverworldMessage,
                        String battleEndMessage) {
        super(name);
        this.OverworldMessage = OverworldMessage;
        this.battleEndMessage = battleEndMessage;
    }

    /**
     * adds pokemon to the enemy and sets pokmeon type to NPC
     *
     * @param pkmn The pokemon that is addded to the enemy trainer
     */
    @Override
    public void addPokemon(Pokemon pkmn) {
        pkmn.setTrainerType(TrainerType.NPC);
        if (numPokemon < 6) {
            pokemon[numPokemon] = pkmn;
            numPokemon++;
        }
        if (this.curPokemon == null) {
            this.setCurPokemon(pokemon[0]);
        }
    }

    public String getOverworldMessage() {
        return OverworldMessage;
    }

    public String getBattleEndMessage() {
        return battleEndMessage;
    }

}
