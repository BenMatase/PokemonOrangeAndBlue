/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gameStates.BattleState;
import gameStates.BlackScreenState;
import gameStates.GameStateType;
import gameStates.MainMenuState;
import gameStates.OverworldState;
import gameStates.SplashScreenState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Eric
 */
public class PokemonOB extends StateBasedGame {

    // Application Properties
    public static final int WIDTH = 512;
    public static final int HEIGHT = 384;
    public static final int FPS = 60;
    public static final double VERSION = 0.1;

    // Class Constructor
    public PokemonOB(String appName) {
        super(appName);

    }

    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        // The first state added will be the one that is loaded first, when the application is launched
        this.addState(new BlackScreenState(GameStateType.BLACKSCREEN.getValue()));
        this.addState(new SplashScreenState(GameStateType.SPLASHSCREEN.getValue()));
        this.addState(new MainMenuState(GameStateType.MAINMENU.getValue()));
        this.addState(new OverworldState(GameStateType.OVERWORLD.getValue()));
        this.addState(new BattleState(GameStateType.BATTLE.getValue()));
    }

    // Main Method
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new PokemonOB(
                "Pokémon Orange and Blue v" + VERSION));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
