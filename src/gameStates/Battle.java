/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates;

import guiComponents.GUIButton;
import guiComponents.GUIButtonManager;
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
public class Battle implements GameState {

    private int ID;
    private String character1;
    private String character2;
    private Image bgdImage;
    private BattleMenuState state;

    // Buttons
    private GUIButtonManager mainMenuButtons;
    private GUIButtonManager fightMenuButtons;
    private GUIButtonManager pokemonMenuButtons;

    // Textviews
    private GUIButtonManager mainMenuLeftTextView;
    private GUIButtonManager fightMenuCancelView;

    // Constants for drawing
    private static final float X_PADDING = 5f;
    private static final float Y_PADDING = 5f;

    public Battle(int BATTLE) {
        ID = BATTLE;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        state = BattleMenuState.MAIN;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        switch (state) {
            case MAIN:
                drawBattleScene(container, g);
                g.setColor(Color.white);
                mainMenuLeftTextView.render(container, g);
                mainMenuButtons.render(container, g);
                break;
            case FIGHT:
                drawBattleScene(container, g);
                fightMenuButtons.render(container, g);
                fightMenuCancelView.render(container, g);
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

    public void setCharacters(String c1, String c2) {
        this.character1 = c1;
        this.character2 = c2;
    }

    private void drawBattleScene(GameContainer container, Graphics g) {
        g.setBackground(Color.darkGray);
        g.drawImage(bgdImage, 0, 0, container.getWidth(), bgdImage.getHeight(), 0, 0, bgdImage.getWidth(), bgdImage.getHeight());
    }

    private void drawBag(GameContainer container, Graphics g) {
        g.setBackground(Color.magenta);
    }

    private void drawPokemon(GameContainer container, Graphics g) {
        g.setBackground(Color.green);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        if (character1 == null || character2 == null) {

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
        mainMenuButtons = new GUIButtonManager(rightSideDrawRect, 2, 2);
        mainMenuButtons.set(0, 0, new GUIButton("Fight"));
        mainMenuButtons.set(1, 0, new GUIButton("Bag"));
        mainMenuButtons.set(0, 1, new GUIButton("Run"));
        mainMenuButtons.set(1, 1, new GUIButton("Pokemon"));
        // Left Side text view
        mainMenuLeftTextView = new GUIButtonManager(leftSideDrawRect, 1, 1);
        GUIButton b = new GUIButton("What will you do?", Color.white);
        b.setEnabled(false);
        mainMenuLeftTextView.set(0, 0, b);

        // FIGHT MENU
        // Right side buttons
        fightMenuButtons = new GUIButtonManager(leftWideDrawRect, 2, 2);
        fightMenuButtons.set(0, 0, new GUIButton("Whip"));
        fightMenuButtons.set(0, 1, new GUIButton("Tackle"));
        fightMenuButtons.set(1, 0, new GUIButton("Quick Attack"));
        fightMenuButtons.set(1, 1, new GUIButton("Splash"));
        // Left Side text view
        fightMenuCancelView = new GUIButtonManager(rightNarrowDrawRect, 1, 1);
        fightMenuCancelView.set(0, 0, new GUIButton("Cancel", Color.blue));

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {

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

    //========================
    // Mark: - Mouse Listeners
    //========================
    //<editor-fold>
    @Override
    public void mouseWheelMoved(int change) {

    }

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
                } else if (fightMenuCancelView.getSelected().contains(x, y)) {
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
    public void mousePressed(int button, int x, int y) {

    }

    @Override
    public void mouseReleased(int button, int x, int y) {

    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        switch (state) {
            case MAIN:
                for (GUIButton btn : mainMenuButtons.getButtons()) {
                    if (btn.contains(newx, newy)) {
                        mainMenuButtons.setSelected(btn);
                    }
                }
                break;
            case FIGHT:
                for (GUIButton btn : fightMenuButtons.getButtons()) {
                    if (btn.contains(newx, newy)) {
                        fightMenuButtons.setSelected(btn);
                    }
                }
                break;
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {

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

    //=====================
    // Mark: - Key handlers
    //=====================
    //<editor-fold>
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
                handleMainMenuSelection();
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

    @Override
    public void keyReleased(int key, char c) {

    }
    //</editor-fold>

    //======================================================
    // Mark: - Unused Functions for Implementing Controllers
    //======================================================
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

}
