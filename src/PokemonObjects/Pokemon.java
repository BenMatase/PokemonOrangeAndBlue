/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 8, 2016
 * Time: 5:34:25 PM
 *
 * Project: csci205FinalProject
 * Package: PokemonObjects
 * File: Pokemon
 * Description:
 *
 * ****************************************
 */
package PokemonObjects;

import BattleUtility.PokemonType;
import java.util.ArrayList;

/**
 * Class that will be used to represent any Pokemon
 *
 * @author Jason
 */
public class Pokemon {
    private int health;
    private int curHealth; //the current health held by a pokemon
    private int attack;
    private int spcAttack;
    private int defense;
    private int spcDefense;
    private int speed;
    private int ID; //pokedex number
    private String name;
    private String nickname; //nickname for pokemon selected by user
    private TrainerType trainer = null; //Enum used by BattleSimulator to send events to GUI
    private ArrayList<Move> moves;
    private PokemonType pokemonType1;
    private PokemonType pokemonType2;

    /**
     * Constructor for pokemon of two types and a nickname
     *
     * @param health
     * @param attack
     * @param spcAttack
     * @param defense
     * @param spcDefense
     * @param speed
     * @param pokemonType1
     * @param pokemonType2
     */
    public Pokemon(int health, int attack, int spcAttack, int defense,
                   int spcDefense, int speed,
                   String name, String nickname, Move[] moves,
                   PokemonType pokemonType1, PokemonType pokemonType2
    ) {
        this.health = health;
        this.attack = attack;
        this.spcAttack = spcAttack;
        this.defense = defense;
        this.spcDefense = spcDefense;
        this.speed = speed;
        this.name = name;
        this.moves = new ArrayList<>();
        this.setMoves(moves);
        this.pokemonType1 = pokemonType1;
        this.pokemonType2 = pokemonType2;
        this.ID = 0;

        //initialize current health to full health
        this.curHealth = this.health;

        if (nickname == null) {
            this.nickname = name;
        } else {
            this.nickname = nickname;
        }
    }

    public int getCurHealth() {
        return curHealth;
    }

    public void setCurHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSpcAttack() {
        return spcAttack;
    }

    public void setSpcAttack(int spcAttack) {
        this.spcAttack = spcAttack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpcDefense() {
        return spcDefense;
    }

    public void setSpcDefense(int spcDefense) {
        this.spcDefense = spcDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PokemonType getPokemonType1() {
        return pokemonType1;
    }

    public PokemonType getPokemonType2() {
        return pokemonType2;
    }

    /**
     * Method that returns true if pokmeon has health greater than 0
     *
     * @return true if pokemon has current health remaining
     */
    public boolean isAlive() {
        return this.curHealth > 0;
    }

    /**
     * Sets pokemon current health to right amount, never less than 0
     *
     * @param healthLoss The amount of health a pokemon will lose
     */
    public void reduceHealth(int healthLoss) {
        if (this.curHealth < healthLoss) {
            this.curHealth = 0;
        } else {
            this.curHealth -= healthLoss;
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Move[] getMoves() {
        return moves.toArray(new Move[moves.size()]);
    }

    public void setMoves(Move[] moves) {
        for (Move move : moves) {
            setMove(move, this.moves.size());
        }
    }

    public void setMove(Move newMove, int moveIndex) {
        if (this.moves.size() < 4 && !(newMove == null)) {
            this.moves.add(newMove);
        }
    }

    public void setTrainerType(TrainerType type) {
        this.trainer = type;
    }

    public String getName() {
        return name;
    }

    public TrainerType getTrainer() {
        return trainer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNumMoves() {
        return this.moves.size();
    }

}
