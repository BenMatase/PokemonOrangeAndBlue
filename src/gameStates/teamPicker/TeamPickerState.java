/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 23, 2016
 * Time: 3:50:35 PM
 *
 * Project: csci205FinalProject
 * Package: gameStates
 * File: TeamPickerState
 * Description:
 *
 * ****************************************
 */
package gameStates.teamPicker;

import model.PokeModel;
import model.PokemonObjects.Pokemon;
import model.PokemonObjects.UserTrainer;
import util.TrainerCreator.RandomCreatorUtility;
import util.TrainerCreator.TeamCreatorUtility;
import gameStates.guiComponents.InfoPanel;
import gameStates.guiComponents.MenuButton;
import gameStates.guiComponents.MenuLayoutManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import util.ColorUtil;

/**
 * A team picking menu state for the game
 *
 * @author Eric
 */
public class TeamPickerState implements GameState {

    // State info
    private int ID;
    private PokeModel model;
    private StateBasedGame game;

    // Menu Managers
    private MenuLayoutManager<InfoPanel> teamMgr;
    private MenuLayoutManager<MenuButton> textDisplayMgr;
    private MenuLayoutManager<MenuButton> doneButtonMgr;

    // Pokemon
    private Pokemon[] pkmns;

    // Drawing constants
    private int PADDING = 5;

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
     * @param TEAMPICKERSTATE The integer representation of the state
     * @param model The PokeModel for the game
     */
    public TeamPickerState(int TEAMPICKERSTATE, PokeModel model) {
        this.ID = TEAMPICKERSTATE;
        this.model = model;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        pkmns = new Pokemon[6];
        // Set up pokemon choosing options
        teamMgr = new MenuLayoutManager<>(new RoundedRectangle(PADDING, PADDING, container.getWidth() - 2 * PADDING, (container.getHeight() - 3 * PADDING) * 0.66f, 5), 2, 3, false, InfoPanel.class);
        teamMgr.set(0, 0, new InfoPanel(0, 0, "CLICK TO ADD"));
        teamMgr.set(1, 0, new InfoPanel(0, 0, "CLICK TO ADD"));
        teamMgr.set(0, 1, new InfoPanel(0, 0, "CLICK TO ADD"));
        teamMgr.set(1, 1, new InfoPanel(0, 0, "CLICK TO ADD"));
        teamMgr.set(0, 2, new InfoPanel(0, 0, "CLICK TO ADD"));
        teamMgr.set(1, 2, new InfoPanel(0, 0, "CLICK TO ADD"));
        teamMgr.enable();

        // Set up prompt display
        textDisplayMgr = new MenuLayoutManager<>(new RoundedRectangle(PADDING, PADDING * 2 + teamMgr.getDrawArea().getHeight(), (container.getWidth() - 3 * PADDING) * 0.75f, (container.getHeight() - 3 * PADDING) * 0.34f, 5), 1, 1, false, MenuButton.class);
        textDisplayMgr.set(0, 0, new MenuButton("Choose your Pokemon team (Press Enter when done)"));
        textDisplayMgr.disable();

        // Set up completion button
        doneButtonMgr = new MenuLayoutManager<>(new RoundedRectangle(PADDING * 2 + textDisplayMgr.getDrawArea().getWidth(), PADDING * 2 + teamMgr.getDrawArea().getHeight(), (container.getWidth() - 3 * PADDING) * 0.25f, (container.getHeight() - 3 * PADDING) * 0.34f, 5), 1, 2, false, MenuButton.class);
        doneButtonMgr.set(0, 1, new MenuButton("Done"));
        doneButtonMgr.set(0, 0, new MenuButton("Random"));
        doneButtonMgr.shouldShowHighlight(false);

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        game = null;
        doneButtonMgr = null;
        textDisplayMgr = null;
        teamMgr = null;
    }

    //==================
    // Mark: - Rendering
    //==================
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setBackground(ColorUtil.getBlue());
        teamMgr.render(container, g);
        textDisplayMgr.render(container, g);
        doneButtonMgr.render(container, g);
    }

    //=================
    // Mark: - Updating
    //=================
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (doneButtonMgr.getItem(0, 0).contains(x, y)) {
            handleRandomTeamSelection();
        } else if (doneButtonMgr.getItem(0, 1).contains(x, y)) {
            handleDoneSelection();
        } else {
            if (teamMgr.getSelected().contains(x, y)) {
                try {
                    handleSelectedTeamSlot(teamMgr.find(teamMgr.getSelected()));
                } catch (SlickException ex) {
                }
            }
        }

    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        MenuButton b = null;
        for (MenuButton btn : doneButtonMgr.getItems()) {
            if (btn.contains(newx, newy)) {
                b = btn;
            }
        }
        if (b != null) {
            doneButtonMgr.shouldShowHighlight(true);
            doneButtonMgr.setSelected(b);
        } else {
            doneButtonMgr.shouldShowHighlight(false);
        }
        for (InfoPanel pnl : teamMgr.getItems()) {
            if (pnl != teamMgr.getSelected() && pnl.contains(newx, newy)) {
                teamMgr.setSelected(pnl);
                return;
            }
        }
    }

    /**
     * Handler for selecting the random team button
     *
     * @author Eric
     */
    private void handleRandomTeamSelection() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                Pokemon pkmn = RandomCreatorUtility.getRandomPokemon();
                try {
                    teamMgr.set(j, i, new InfoPanel(pkmn.getCurHealth(), pkmn.getHealth(), pkmn.getName(), new Image("./res/Images/Sprites/front/" + pkmn.getID() + ".png")));
                    pkmns[j + i * 2] = pkmn;
                } catch (SlickException ex) {
                    Logger.getLogger(TeamPickerState.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Handler for selecting a slot for a given Pokemon in the team view
     *
     * @author Eric
     * @param coords The coordinates of the selected InfoPanel in the matrix
     * @throws SlickException
     */
    private void handleSelectedTeamSlot(int[] coords) throws SlickException {;
        Pokemon pkmn = TeamCreatorUtility.getPokemonGUI();
        if (pkmn != null) {
            int[] pnlLoc = teamMgr.find(teamMgr.getSelected());
            teamMgr.set(coords[0], coords[1], new InfoPanel(pkmn.getCurHealth(), pkmn.getHealth(), pkmn.getName(), new Image("./res/Images/Sprites/front/" + pkmn.getID() + ".png")));
            pkmns[pnlLoc[0] + pnlLoc[1] * 2] = pkmn;
        }
    }

    /**
     * Handler for selecting the "Done" option
     *
     * @author Eric
     */
    private void handleDoneSelection() {
        if (readPkmnIntoUser()) {
            game.enterState(main.Main.GameStateType.MAINMENU.getValue(), new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
        }
    }

    /**
     * Reads all of the current Pokemon into the model
     *
     * @author Eric
     * @return True if there were Pokemon to read into the user, false otherwise
     */
    private boolean readPkmnIntoUser() {
        UserTrainer user = new UserTrainer("Player");
        int count = 0;
        for (Pokemon pkmn : pkmns) {
            if (pkmn != null) {
                count += 1;
                user.addPokemon(pkmn);
            }
        }
        model.setUser(user);
        return count > 0;
    }

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

    //</editor-fold>
    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_LEFT:
                teamMgr.setSelected(teamMgr.getLeft());
                break;
            case Input.KEY_RIGHT:
                teamMgr.setSelected(teamMgr.getRight());
                break;
            case Input.KEY_UP:
                teamMgr.setSelected(teamMgr.getUp());
                break;
            case Input.KEY_DOWN:
                teamMgr.setSelected(teamMgr.getDown());
                break;
            case Input.KEY_ENTER:
                handleDoneSelection();
                break;
            case Input.KEY_SPACE:
                try {
                    handleSelectedTeamSlot(teamMgr.find(teamMgr.getSelected()));
                } catch (SlickException ex) {
                }
                break;
        }
    }

    //<editor-fold>
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
