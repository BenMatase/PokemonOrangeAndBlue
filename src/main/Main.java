/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gameStates.battle.BattleState;
import gameStates.blackScreen.BlackScreenState;
import gameStates.mainMenu.MainMenuState;
import gameStates.splashScreen.SplashScreenState;
import gameStates.teamPicker.TeamPickerState;
import model.PokeModel;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import util.DatabaseLoaderUtilities.PokemonLoaderUtility;
import util.SoundUtil;

/**
 * Creates and runs a Slick2D StateBasedGame for Pokemon Orange & Blue
 *
 * @author Eric
 */
public class Main extends StateBasedGame {

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

    // Application Properties
    public static final int WIDTH = 512;
    public static final int HEIGHT = 384;
    public static final int FPS = 60;
    public static final double VERSION = 0.1;
    public static PokeModel model;

    // Class Constructor
    public Main(String appName) {
        super(appName);
        model = new PokeModel();
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new BlackScreenState(GameStateType.BLACKSCREEN.getValue()));
        this.addState(new SplashScreenState(GameStateType.SPLASHSCREEN.getValue()));
        this.addState(new MainMenuState(GameStateType.MAINMENU.getValue(), model));
        this.addState(new TeamPickerState(GameStateType.TEAMPICKER.getValue(), model));
        this.addState(new BattleState(GameStateType.BATTLE.getValue(), model));
    }

    // Main Method
    public static void main(String[] args) {
        try {
            //load databases and music
            SoundUtil.init();
            PokemonLoaderUtility.init();

            // Run the app
            AppGameContainer app = new AppGameContainer(new Main("Pokémon Orange and Blue Version"));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
