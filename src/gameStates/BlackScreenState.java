/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

/**
 * An empty screen class that allows the game to fade into the splash screen
 *
 * @author Eric
 */
public class BlackScreenState extends BasicGameState {

    private int ID;

    public BlackScreenState(int BLACKSCREEN) {
        ID = BLACKSCREEN;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) {
        gc.setShowFPS(false);
        sbg.enterState(1, new EmptyTransition(), new FadeInTransition());
    }

    public void render(GameContainer gc, Graphics g, StateBasedGame sbg) {
        // Do nothing
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        // Do nothing
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // Do nothing
    }
}
