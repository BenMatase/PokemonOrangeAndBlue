/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates;

import BattleUtility.Event;
import PokeModel.PokeModel;
import PokemonController.BattleControl;
import guiComponents.Animation;
import guiComponents.InfoPanel;
import guiComponents.MenuButton;
import guiComponents.MenuLayoutManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Eric
 */
public class BattleState implements GameState {

    private int ID;
    private Image bgdImage;
    private BattleMenuState state;
    private PokeModel model;
    private BattleControl control;

    // Drawing Pokemon
    private Image playerImage;
    private Image enemyImage;

    // Buttons
    private MenuLayoutManager mainMenuButtons;
    private MenuLayoutManager fightMenuButtons;
    private MenuLayoutManager pokemonMenuButtons;
    private MenuLayoutManager hpBarViewManager;
    private MenuLayoutManager fightMenuCancelButton;

    // Textviews
    private MenuLayoutManager mainMenuTextDisplay;

    // Text Display and animation
    private LinkedBlockingQueue<String> textDisplayQueue;
    private ArrayList<Animation> animationList;

    // Whether or not the view is animating
    // Constants for drawing
    private static final float X_PADDING = 5f;
    private static final float Y_PADDING = 5f;

    public BattleState(int BATTLE, PokeModel model) {
        this(BATTLE);
        this.model = model;
    }

    public BattleState(int BATTLE) {
        ID = BATTLE;
    }

    @Override
    public int getID() {
        return ID;
    }

    //==========================================
    // Mark: - Data Initialization & Destruction
    //==========================================
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        state = BattleMenuState.MAIN;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        if (model.getEnemy() == null || model.getUser() == null) {
            throw new SlickException("Characters don't exist");
        } else {
            model.getUser().getCurPokemon().setID(2);
            playerImage = new Image("./res/Images/Sprites/back/" + model.getUser().getCurPokemon().getID() + ".png");
            model.getEnemy().getCurPokemon().setID(2);
            enemyImage = new Image("./res/Images/Sprites/front/" + model.getEnemy().getCurPokemon().getID() + ".png");
        }
        // Load the battle background image
        bgdImage = new Image("res/Images/Battle/BattleGrass.png");

        RoundedRectangle rightSideDrawRect = new RoundedRectangle(container.getWidth() / 2 + 0.5f * X_PADDING,
                                                                  bgdImage.getHeight() + Y_PADDING,
                                                                  (container.getWidth() * 1 / 2 - 1.5f * X_PADDING),
                                                                  container.getHeight() - bgdImage.getHeight() - 2 * Y_PADDING,
                                                                  5);
        RoundedRectangle leftSideDrawRect = new RoundedRectangle(X_PADDING,
                                                                 rightSideDrawRect.getY(),
                                                                 rightSideDrawRect.getWidth(),
                                                                 rightSideDrawRect.getHeight(),
                                                                 5);
        RoundedRectangle leftWideDrawRect = new RoundedRectangle(X_PADDING,
                                                                 rightSideDrawRect.getY(),
                                                                 rightSideDrawRect.getWidth() * 3f / 2,
                                                                 rightSideDrawRect.getHeight(),
                                                                 5);
        RoundedRectangle rightNarrowDrawRect = new RoundedRectangle(leftWideDrawRect.getX() + leftWideDrawRect.getWidth() + X_PADDING,
                                                                    rightSideDrawRect.getY(),
                                                                    container.getWidth() - leftWideDrawRect.getWidth() - 3 * X_PADDING,
                                                                    rightSideDrawRect.getHeight(),
                                                                    5);

        // MAIN MENU
        // Right side buttons
        mainMenuButtons = new MenuLayoutManager(rightSideDrawRect, 2, 2);
        mainMenuButtons.set(0, 0, new MenuButton("Fight"));
        mainMenuButtons.set(1, 0, new MenuButton("Bag"));
        mainMenuButtons.set(1, 1, new MenuButton("Run"));
        mainMenuButtons.set(0, 1, new MenuButton("Pokemon"));
        // Left Side text view
        mainMenuTextDisplay = new MenuLayoutManager(leftSideDrawRect, 1, 1);
        MenuButton b = new MenuButton("What will you do?", Color.white);
        b.setEnabled(false);
        mainMenuTextDisplay.set(0, 0, b);

        // FIGHT MENU
        // Right side buttons
        fightMenuButtons = new MenuLayoutManager(leftWideDrawRect, 2, 2);
        // Currently placeholder strings
        fightMenuButtons.set(0, 0, new MenuButton("Mud Slap"));
        fightMenuButtons.set(0, 1, new MenuButton("Tackle"));
        fightMenuButtons.set(1, 0, new MenuButton("Quick Attack"));
        fightMenuButtons.set(1, 1, new MenuButton("Splash"));

        // Left Side text view
        fightMenuCancelButton = new MenuLayoutManager(rightNarrowDrawRect, 1, 1);
        fightMenuCancelButton.set(0, 0, new MenuButton("Cancel", Color.blue));

        // HP BARS
        hpBarViewManager = new MenuLayoutManager(new RoundedRectangle(0, 0, container.getWidth(), bgdImage.getHeight(), 0), 2, 3, false);
        hpBarViewManager.set(0, 0, new InfoPanel(19, 100, "Testname1, plz ignore", 2));
        hpBarViewManager.set(1, 2, new InfoPanel(80, 100, "Testname2, plz ignore", 17));
        hpBarViewManager.disable();

        // Init queues for animation and displaying text
        animationList = new ArrayList<>();

        // Battle Controller
        control = new BattleControl(model);
        handleAnimations(control.getInitialMessage());
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        // Clean up menu buttons
        mainMenuButtons = null;
        fightMenuButtons = null;
        pokemonMenuButtons = null;
        hpBarViewManager = null;
        fightMenuCancelButton = null;
        // Clean up text views
        mainMenuTextDisplay = null;
        // Clean up images
        bgdImage = null;
        // Clean up controller
//        control = null;

        textDisplayQueue = null;
        animationList = null;
    }

    //=========================
    // Mark: - Rendering (View)
    //=========================
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        switch (state) {
            case MAIN:
                drawBattleScene(container, g);
                g.setColor(Color.white);
                mainMenuTextDisplay.render(container, g);
                mainMenuButtons.render(container, g);
                break;
            case FIGHT:
                drawBattleScene(container, g);
                fightMenuButtons.render(container, g);
                fightMenuCancelButton.render(container, g);
                break;
            case RUN:
                drawBattleScene(container, g);
                break;
            case POKEMON:
                drawPokemon(container, g);
                break;
            case BAG:
                drawBag(container, g);
                break;
        }
    }

    private void drawBattleScene(GameContainer container, Graphics g) {
        g.setBackground(Color.darkGray);
        g.drawImage(bgdImage, 0, 0, container.getWidth(), bgdImage.getHeight(), 0, 0, bgdImage.getWidth(), bgdImage.getHeight());
        float scale = 2.0f;
        g.drawImage(playerImage, playerImage.getWidth() / 4f, bgdImage.getHeight() - playerImage.getHeight() * scale, playerImage.getWidth() / 4f + playerImage.getWidth() * scale, bgdImage.getHeight(),
                    0, 0, playerImage.getWidth(), playerImage.getHeight());

        g.drawImage(enemyImage, container.getWidth() - enemyImage.getWidth() * 1.25f, 0, container.getWidth() - enemyImage.getWidth() * 0.25f, enemyImage.getHeight(),
                    0, 0, enemyImage.getWidth(), enemyImage.getHeight());

        hpBarViewManager.render(container, g);
    }

    private void drawBag(GameContainer container, Graphics g) {
        g.setBackground(Color.magenta);
    }

    private void drawPokemon(GameContainer container, Graphics g) {
        g.setBackground(Color.green);
    }

    //=====================================
    // Mark: - Logical Updates (Controller)
    //=====================================
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }

    public void handleAnimations(List<Event> events) {
    }

    public void handleMainMenuSelection() {
        switch (mainMenuButtons.getSelected().getText()) {
            case "Fight":
                state = BattleMenuState.FIGHT;
                break;
            case "Pokemon":
                state = BattleMenuState.POKEMON;
                break;
            case "Bag":
                state = BattleMenuState.BAG;
                break;
            case "Run":
                state = BattleMenuState.RUN;
                break;
        }
    }

    public void handleFightMenuSelection() {
        switch (fightMenuButtons.getSelected().getText()) {

        }
    }

    public void handleFightCancelSelection() {
        state = BattleMenuState.MAIN;
    }

//    public void setModel(PokeModel model) {
//        this.model = model;
//    }
//    private void handleNewEvents(List<Event> newEvents) {
//        while (!newEvents.isEmpty()) {
//
//        }
//    }
    //========================
    // Mark: - Mouse Listeners
    //========================
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        switch (state) {
            case MAIN:
                if (mainMenuButtons.getSelected().contains(x, y)) {
                    handleMainMenuSelection();
                }
                break;
            case FIGHT:
                if (fightMenuButtons.getSelected().contains(x, y)) {
                    handleFightMenuSelection();
                } else if (fightMenuCancelButton.getSelected().contains(x, y)) {
                    handleFightCancelSelection();
                    break;
                }
            case BAG:
                break;
            case POKEMON:
                break;
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        switch (state) {
            case MAIN:
                for (MenuButton btn : mainMenuButtons.getButtons()) {
                    if (btn.contains(newx, newy)) {
                        mainMenuButtons.setSelected(btn);
                    }
                }
                break;
            case FIGHT:
                for (MenuButton btn : fightMenuButtons.getButtons()) {
                    if (btn.contains(newx, newy)) {
                        fightMenuButtons.setSelected(btn);
                    }
                }
                break;
        }
    }

    // Unused listeners
    //<editor-fold>
    @Override
    public void mouseWheelMoved(int change) {

    }

    @Override
    public void mousePressed(int button, int x, int y) {

    }

    @Override
    public void mouseReleased(int button, int x, int y) {

    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {

    }
    // </editor-fold>

    //====================================
    // Mark: - Key handling and listening
    //====================================
    @Override
    public void keyPressed(int key, char c) {
        switch (state) {
            case MAIN:
                handleMainMenuKeyPress(key, c);
                break;
            case FIGHT:
                handleFightMenuKeyPress(key, c);
        }
    }

    private void handleFightMenuKeyPress(int key, char c) {
        switch (key) {
            case Input.KEY_LEFT:
                fightMenuButtons.setSelected(fightMenuButtons.getLeft());
                break;
            case Input.KEY_RIGHT:
                fightMenuButtons.setSelected(fightMenuButtons.getRight());
                break;
            case Input.KEY_DOWN:
                fightMenuButtons.setSelected(fightMenuButtons.getDown());
                break;
            case Input.KEY_UP:
                fightMenuButtons.setSelected(fightMenuButtons.getUp());
                break;
            case Input.KEY_SPACE:
            case Input.KEY_ENTER:
                handleFightMenuSelection();
                break;
            case Input.KEY_BACK:
            case Input.KEY_ESCAPE:
                state = BattleMenuState.MAIN;
                break;
        }
    }

    private void handleMainMenuKeyPress(int key, char c) {
        switch (key) {
            case Input.KEY_LEFT:
                mainMenuButtons.setSelected(mainMenuButtons.getLeft());
                break;
            case Input.KEY_RIGHT:
                mainMenuButtons.setSelected(mainMenuButtons.getRight());
                break;
            case Input.KEY_DOWN:
                mainMenuButtons.setSelected(mainMenuButtons.getDown());
                break;
            case Input.KEY_UP:
                mainMenuButtons.setSelected(mainMenuButtons.getUp());
                break;
            case Input.KEY_SPACE:
            case Input.KEY_ENTER:
                handleMainMenuSelection();
                break;
        }
    }

    //============================================
    // MARK: - Everything past this line is unused
    //============================================
    //<editor-fold>
    //====================================
    // Mark: - Accepting and Setting Input
    //====================================
    //<editor-fold>
    @Override
    public void keyReleased(int key, char c) {
    }
    // </editor-fold>

    //====================================
    // Mark: - Accepting and Setting Input
    //====================================
    //<editor-fold>
    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
    //</editor-fold>

    //======================================
    // Mark: - Unused Functions for Gamepads
    //======================================
    //<editor-fold defaultstate="collapsed">
    @Override
    public void controllerLeftPressed(int controller) {

    }

    @Override
    public void controllerLeftReleased(int controller) {

    }

    @Override
    public void controllerRightPressed(int controller) {

    }

    @Override
    public void controllerRightReleased(int controller) {

    }

    @Override
    public void controllerUpPressed(int controller) {

    }

    @Override
    public void controllerUpReleased(int controller) {

    }

    @Override
    public void controllerDownPressed(int controller) {

    }

    @Override
    public void controllerDownReleased(int controller) {

    }

    @Override
    public void controllerButtonPressed(int controller, int button) {

    }

    @Override
    public void controllerButtonReleased(int controller, int button) {

    }
    // </editor-fold>
    // </editor-fold>
}
