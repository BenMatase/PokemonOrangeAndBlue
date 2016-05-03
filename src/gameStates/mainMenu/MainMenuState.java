/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 10, 2016
 * Time: 10:43:29 PM
 *
 * Project: csci205FinalProject
 * Package: gameStates
 * File: BattleState
 * Description:
 *
 * ****************************************
 */
package gameStates.mainMenu;

import gameStates.GameStateType;
import gameStates.guiComponents.MenuButton;
import gameStates.guiComponents.MenuLayoutManager;
import model.PokeModel;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import util.DatabaseLoaderUtilities.TrainerLoaderUtility;
import util.SoundUtil;
import util.TrainerCreator.RandomCreatorUtility;

/**
 * A main menu state for the game
 *
 * @author Eric
 */
public class MainMenuState implements GameState {

    private int ID;
    private PokeModel model;
    private StateBasedGame game;

    private MenuLayoutManager<MenuButton> menuMgr;

    @Override
    public int getID() {
        return ID;
    }

    //=========================
    // Mark: - Setup & Teardown
    //=========================
    /**
     * A constructor for a TeamPickerState
     *
     * @author Eric
     * @param MAINMENU The integer representation of the state
     * @param model The PokeModel for the game
     */
    public MainMenuState(int MAINMENU, PokeModel model) {
        ID = MAINMENU;
        this.model = model;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        menuMgr = new MenuLayoutManager<>(new RoundedRectangle(5, container.getHeight() / 4, container.getWidth() - 10, container.getHeight() / 2, 5), 1, 3, MenuButton.class);
        menuMgr.setDrawBackground(false);
        menuMgr.set(0, 0, new MenuButton("Battle Random Trainer"));
        menuMgr.set(0, 1, new MenuButton("Battle " + TrainerLoaderUtility.loadProfessor(model.getCurProf()).getName()));
        menuMgr.set(0, 2, new MenuButton("Restart"));

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = null;
        menuMgr = null;
    }

    //==================
    // Mark: - Rendering
    //==================
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        menuMgr.render(container, g);
    }

    //========================
    // Mark: - Logical Updates
    //========================
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }

    //=========================
    // Mark: - Control Handling
    //=========================
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (menuMgr.getSelected().contains(x, y)) {
            handleSelection();
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        for (MenuButton btn : menuMgr.getItems()) {
            if (btn.contains(newx, newy)) {
                menuMgr.setSelected(btn);
            }
        }
    }

    /**
     * Handler for selecting an item in the menu
     *
     * @author Eric
     */
    private void handleSelection() {
        if ("Battle Random Trainer".equals(menuMgr.getSelected().getText())) {
            model.setEnemy(RandomCreatorUtility.getRandomNPC(6, "Random Trainer"), false);
            SoundUtil.playEnterBattle();
            game.enterState(GameStateType.BATTLE.getValue(), new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
        } else if (menuMgr.getSelected().getText().equals("Battle " + TrainerLoaderUtility.loadProfessor(model.getCurProf()).getName())) {
            model.setEnemy(util.DatabaseLoaderUtilities.TrainerLoaderUtility.loadProfessor(model.getCurProf()), true);
            SoundUtil.playEnterBattle();
            game.enterState(GameStateType.BATTLE.getValue(), new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
        }
        if ("Restart".equals(menuMgr.getSelected().getText())) {
            game.enterState(GameStateType.TEAMPICKER.getValue(), new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
        }
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_UP:
                menuMgr.setSelected(menuMgr.getUp());
                break;
            case Input.KEY_DOWN:
                menuMgr.setSelected(menuMgr.getDown());
                break;
            case Input.KEY_SPACE:
            case Input.KEY_ENTER:
                handleSelection();
        }
    }

    //=========================
    // Mark: - Unused Functions
    //=========================
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

    @Override
    public void setInput(Input input) {

    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }

    @Override
    public void keyReleased(int key, char c) {

    }

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

    //</editor-fold>
}
