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

/**
 * Class that will be used to represent a Pokemon
 *
 * @author Jason
 */
public class Pokemon {
    private int health;
    private int curHealth;
    private int attack;
    private int spcAttack;
    private int defense;
    private int spcDefense;
    private int speed;
    private String name;
    private String nickname;

    //INSERT ENUM TYPE HERE
    private PokemonType pokemonType1;
    private PokemonType pokemonType2;
    //INSERT ENUM FOR EACH MOVE HERE & getters and setters

    /**
     * Constructor for when pokemon has only 1 type of pokmeon in it
     *
     * @param health
     * @param attack
     * @param spcAttack
     * @param defense
     * @param spcDefense
     * @param speed
     * @param pokemonType1
     */
    public Pokemon(int health, int attack, int spcAttack, int defense,
                   int spcDefense, int speed, PokemonType pokemonType1,
                   String name) {
        this.health = health;
        this.curHealth = health;
        this.attack = attack;
        this.spcAttack = spcAttack;
        this.defense = defense;
        this.spcDefense = defense;
        this.speed = speed;
        this.pokemonType1 = pokemonType1;
        this.pokemonType2 = null;
        this.name = name;
        this.nickname = null;
    }

    /**
     * Constructor for pokemon of two different types
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
                   int spcDefense, int speed, PokemonType pokemonType1,
                   String name, PokemonType pokemonType2) {
        pokemon(health, attack, spcAttack, defense,
                spcDefense, speed, pokemonType1,
                name)
        this.pokemonType2 = pokemonType2;
    }

    /**
     * Constructor for pokemon of one type and a nickname
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
                   int spcDefense, int speed, PokemonType pokemonType1,
                   String name, String nickname
    ) {
        pokemon(health, attack, spcAttack, defense,
                spcDefense, speed, pokemonType1,
                name);
        this.nickname = nickname;
    }

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
                   int spcDefense, int speed, PokemonType pokemonType1,
                   String name, PokemonType pokemonType2, String nickname) {
        pokemon(health, attack, spcAttack, defense,
                spcDefense, speed, pokemonType1,
                name);
        this.nickname = nickname;
        this.pokemonType2 = pokemonType2;
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

    public boolean isAlive() {
        return this.curHealth > 0;
    }

    public void reduceHealth(int healthLoss) {
        if (this.curHealth < healthLoss) {
            this.curHealth = 0;
        } else {
            this.curHealth -= healthLoss;
        }
    }

}
