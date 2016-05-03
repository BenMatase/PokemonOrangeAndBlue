package gameStates;

/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: May 2, 2016
 * Time: 9:37:42 PM
 *
 * Project: csci205FinalProject
 * Package:
 * File: GameStateType
 * Description:
 *
 * ****************************************
 */

/**
 * Enum for each type of game state in the game, represented with an integer
 *
 * @author Eric
 */
public enum GameStateType {

    BLACKSCREEN(0),
    SPLASHSCREEN(1),
    MAINMENU(2),
    TEAMPICKER(3),
    BATTLE(4);

    private int val;

    GameStateType(int val) {
        this.val = val;
    }

    public int getValue() {
        return this.val;
    }
}
