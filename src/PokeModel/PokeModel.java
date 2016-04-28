/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 14, 2016
 * Time: 12:23:16 AM
 *
 * Project: csci205FinalProject
 * Package: PokemonModel
 * File: PokeModel
 * Description:
 *
 * ****************************************
 */
package PokeModel;

import PokemonObjects.EnemyTrainer;
import PokemonObjects.UserTrainer;

/**
 * Class that holds the model of the game such as the enemy trainers and user
 * trainer.
 *
 * @author Benjamin Matase and Eric Marshall
 */
public class PokeModel {

    private UserTrainer user;
    private EnemyTrainer enemy;

    private int curProf = 1;
    private boolean curEnemyIsProf = false;

    public PokeModel() {
    }

    public UserTrainer getUser() {
        return user;
    }

    public void setUser(UserTrainer user) {
        this.user = user;
    }

    public EnemyTrainer getEnemy() {
        return enemy;
    }

    /**
     *
     * @param enemy
     * @param isProf
     *
     * @author Eric Marshall
     */
    public void setEnemy(EnemyTrainer enemy, boolean isProf) {
        this.enemy = enemy;
        curEnemyIsProf = isProf;
    }

    /**
     *
     * @author Eric Marshall
     */
    public void clearEnemy() {
        enemy = null;
        curEnemyIsProf = false;
    }

    public boolean curEnemyIsProf() {
        return curEnemyIsProf;
    }

    /**
     *
     * @author Eric Marshall
     */
    public void incrementProf() {
        if (curProf < 12) {
            curProf += 1;
        }
    }

    public int getCurProf() {
        return curProf;
    }

}
