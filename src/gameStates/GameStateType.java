/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates;

/**
 *
 * @author Eric
 */
public enum GameStateType {

    BLACKSCREEN(0),
    SPLASHSCREEN(1),
    MAINMENU(2),
    OVERWORLD(3),
    BATTLE(4);

    private int val;

    GameStateType(int val) {
        this.val = val;
    }

    public int getValue() {
        return this.val;
    }
}
