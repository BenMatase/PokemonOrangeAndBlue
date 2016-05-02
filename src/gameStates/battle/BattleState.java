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
package gameStates.battle;

import util.battleUtility.EnemyDefeatEvent;
import util.battleUtility.Event;
import util.battleUtility.PokemonFaintEvent;
import util.battleUtility.SwitchPokemonEvent;
import util.battleUtility.TextOutputEvent;
import util.battleUtility.UpdateHealthBarEvent;
import util.battleUtility.UserDefeatEvent;
import controller.BattleControl;
import gameStates.guiComponents.InfoPanel;
import gameStates.guiComponents.MenuButton;
import gameStates.guiComponents.MenuLayoutManager;
import gameStates.guiComponents.PokemonImage;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import model.PokeModel;
import model.PokemonObjects.Move;
import model.PokemonObjects.Pokemon;
import model.PokemonObjects.TrainerType;
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
import util.SoundUtil;

/**
 * A game state that simulates a Pokemon battle
 *
 * @author Eric
 */
public class BattleState implements GameState {

    // Control and model components
    private int ID;
    private StateBasedGame game;
    private BattleStateMenuType state;
    private PokeModel model;
    private BattleControl control;

    // Images
    private Image bgdImage;
    private PokemonImage playerImage;
    private PokemonImage enemyImage;

    // Buttons and menus
    private MenuLayoutManager<MenuButton> mainMenuMgr;
    private MenuLayoutManager<MenuButton> fightMenuMgr;
    private MenuLayoutManager<MenuButton> fightMenuCancelMgr;
    private MenuLayoutManager<MenuButton> textDisplayViewMgr;
    private MenuLayoutManager<MenuButton> mainMenuTextDisplay;
    private MenuLayoutManager<MenuButton> pokemonMenuTextDisplay;
    private MenuLayoutManager<MenuButton> pokemonMenuCancelMgr;
    private MenuLayoutManager<InfoPanel> hpBarViewMgr;
    private MenuLayoutManager<InfoPanel> pokemonMenuMgr;

    // Constants for centers of images
    private int etlx = 316;
    private int etly = 0;
    private int ebrx = etlx + 200;
    private int ebry = etly + 93;
    private int ptlx = 0;
    private int ptly = 126;
    private int pbrx = ptlx + 210;
    private int pbry = ptly + 98;

    // Text Display and animation handling
    private LinkedBlockingQueue<Event> eventQueue;
    private int delay = 0;

    // Action Relevant
    private boolean pokemonFainted;
    private boolean exitOnEmptyQueue;

    // Constants for drawing
    private static final float X_PADDING = 5f;
    private static final float Y_PADDING = 5f;

    /**
     * The constructor for a new battle state
     *
     * @author Eric
     * @param BATTLE The integer ID of the battle state
     * @param model The PokeModel to use in the battle state
     */
    public BattleState(int BATTLE, PokeModel model) {
        ID = BATTLE;
        this.model = model;
    }

    @Override
    public int getID() {
        return ID;
    }

    //===========================
    // Mark: - Setup and Teardown
    //===========================
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        state = BattleStateMenuType.MAIN;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {

        loadImages();

        RoundedRectangle fullwidthDrawRect = new RoundedRectangle(X_PADDING,
                                                                  bgdImage.getHeight() + Y_PADDING,
                                                                  container.getWidth() - 2 * Y_PADDING,
                                                                  container.getHeight() - bgdImage.getHeight() - Y_PADDING * 2,
                                                                  5);
        RoundedRectangle right12DrawRect = new RoundedRectangle((container.getWidth() + X_PADDING) * 0.5f,
                                                                bgdImage.getHeight() + Y_PADDING,
                                                                (container.getWidth() - 3 * X_PADDING) * 0.5f,
                                                                container.getHeight() - bgdImage.getHeight() - 2 * Y_PADDING,
                                                                5);
        RoundedRectangle left12DrawRect = new RoundedRectangle(X_PADDING,
                                                               bgdImage.getHeight() + Y_PADDING,
                                                               (container.getWidth() - 3 * X_PADDING) * 0.5f,
                                                               container.getHeight() - bgdImage.getHeight() - 2 * Y_PADDING,
                                                               5);
        RoundedRectangle left34DrawRect = new RoundedRectangle(X_PADDING,
                                                               bgdImage.getHeight() + Y_PADDING,
                                                               (container.getWidth() - 3 * X_PADDING) * 0.75f,
                                                               container.getHeight() - bgdImage.getHeight() - 2 * Y_PADDING,
                                                               5);
        RoundedRectangle right14DrawRect = new RoundedRectangle(X_PADDING + (container.getWidth() - 3 * X_PADDING) * 0.75f + X_PADDING,
                                                                bgdImage.getHeight() + Y_PADDING,
                                                                (container.getWidth() - 3 * X_PADDING) * 0.25f,
                                                                container.getHeight() - bgdImage.getHeight() - 2 * Y_PADDING,
                                                                5);

        // MAIN MENU
        setupMainMenu(right12DrawRect, left12DrawRect);

        // FIGHT MENU
        setupFightMenu(left34DrawRect, right14DrawRect);

        // HP BARS
        setupHPBars(container);

        // TEXT DISPLAY
        textDisplayViewMgr = new MenuLayoutManager<>(fullwidthDrawRect, 1, 1, MenuButton.class);
        textDisplayViewMgr.set(0, 0, new MenuButton(""));
        textDisplayViewMgr.disable();

        // POKEMON MENU
        setupPokemonMenu(container, left34DrawRect, right14DrawRect);

        // Init queues for animation and displaying text
        eventQueue = new LinkedBlockingQueue<>();

        // Battle Controller
        control = new BattleControl(model);
        handleNewEvents(control.getInitialMessage());
        this.state = BattleStateMenuType.HANDLING_EVENTS;
    }

    /**
     * Loads the necessary images for the state
     *
     * @author Eric
     * @throws SlickException
     */
    public void loadImages() throws SlickException {
        bgdImage = new Image("res/Images/Battle/BattleGrass.png");
        System.out.println(bgdImage.getHeight());
        playerImage = new PokemonImage(ptlx, ptly, pbrx, pbry, PokemonImage.FillType.CROP, model.getImgIndex(TrainerType.USER), TrainerType.USER);
//        playerImage = new PokemonImage(px, py, bgdImage.getHeight(), tmp, TrainerType.USER);
        enemyImage = new PokemonImage(etlx, etly, ebrx, ebry, PokemonImage.FillType.SCALE, model.getImgIndex(TrainerType.NPC), TrainerType.NPC);
    }

    /**
     * Sets up the buttons to go in the main menu
     *
     * @author Eric
     * @param buttonRect The rectangle to draw the buttons in
     * @param textDisplayRect The rectangle to draw the text display in
     */
    public void setupMainMenu(RoundedRectangle buttonRect, RoundedRectangle textDisplayRect) {
        // Right side buttons
        mainMenuMgr = new MenuLayoutManager<>(buttonRect, 2, 2, MenuButton.class);
        mainMenuMgr.set(0, 0, new MenuButton("Fight"));
        mainMenuMgr.set(1, 0, new MenuButton("Bag"));
        mainMenuMgr.set(1, 1, new MenuButton("Run"));
        mainMenuMgr.set(0, 1, new MenuButton("Pokemon"));
        // Left Side text view
        mainMenuTextDisplay = new MenuLayoutManager<>(textDisplayRect, 1, 1, MenuButton.class);
        MenuButton b = new MenuButton("What will " + model.getUser().getCurPokemon().getNickname() + " do?", Color.white);
        b.setEnabled(false);
        mainMenuTextDisplay.set(0, 0, b);
    }

    /**
     * Sets up the fight menu display
     *
     * @author Eric
     * @param left The left rectangle, for displaying the attack options
     * @param right The right rectangle, for displaying the cancel button
     */
    public void setupFightMenu(RoundedRectangle left, RoundedRectangle right) {
        fightMenuMgr = new MenuLayoutManager<>(left, 2, 2, MenuButton.class);
        updateFightMenuOptions();
        fightMenuCancelMgr = new MenuLayoutManager<>(right, 1, 1, MenuButton.class);
        fightMenuCancelMgr.set(0, 0, new MenuButton("Cancel"));
        fightMenuCancelMgr.shouldShowHighlight(false);
    }

    /**
     * Sets up the HP bars
     *
     * @author Eric
     * @param container The container to draw in
     */
    public void setupHPBars(GameContainer container) {
        // HP BARS
        hpBarViewMgr = new MenuLayoutManager<>(new RoundedRectangle(0, 0, container.getWidth(), bgdImage.getHeight(), 0), 2, 3, false, InfoPanel.class);
        hpBarViewMgr.disable();
        hpBarViewMgr.set(1, 2, new InfoPanel(model.getUser().getCurPokemon().getCurHealth(), model.getUser().getCurPokemon().getHealth(), model.getUser().getCurPokemon().getNickname()));
        hpBarViewMgr.set(0, 0, new InfoPanel(model.getEnemy().getCurPokemon().getCurHealth(), model.getEnemy().getCurPokemon().getHealth(), model.getEnemy().getCurPokemon().getName()));

    }

    /**
     * Sets up the Pokemon Menu
     *
     * @author Eric
     * @param container The container to draw in
     * @param left The left rectangle, for displaying a prompt
     * @param right The right rectangle, for displaying the cancel button
     * @throws SlickException
     */
    public void setupPokemonMenu(GameContainer container, RoundedRectangle left, RoundedRectangle right) throws SlickException {
        // Pokemon Chooser Menu
        pokemonMenuMgr = new MenuLayoutManager<>(new RoundedRectangle(0, 0, container.getWidth(), bgdImage.getHeight(), 0), 2, 3, false, InfoPanel.class);
        updatePokemonMenuOptions();
        pokemonMenuMgr.enable();

        // Prompt Text Display
        pokemonMenuTextDisplay = new MenuLayoutManager<>(left, 1, 1, true, MenuButton.class);
        pokemonMenuTextDisplay.set(0, 0, new MenuButton("Select a new Pokemon"));
        pokemonMenuTextDisplay.disable();

        // Cancel Button
        pokemonMenuCancelMgr = new MenuLayoutManager<>(right, 1, 1, true, MenuButton.class);
        pokemonMenuCancelMgr.set(0, 0, new MenuButton("Cancel"));
        pokemonMenuCancelMgr.shouldShowHighlight(false);

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        // Clean up menu buttons
        mainMenuMgr = null;
        fightMenuMgr = null;
        fightMenuCancelMgr = null;
        textDisplayViewMgr = null;
        mainMenuTextDisplay = null;
        pokemonMenuTextDisplay = null;
        pokemonMenuCancelMgr = null;
        hpBarViewMgr = null;
        pokemonMenuMgr = null;
        // Clean up images
        bgdImage = null;
        playerImage = null;
        enemyImage = null;
        // Clean up controller
        control = null;
        // Clean up Animations
        eventQueue = null;

        // Clear enemy
        model.clearEnemy();

        // Clean up event queue
        eventQueue = null;

    }

    //=========================
    //   _   _ _
    //  | | | (_)
    //  | | | |_  __ __      __
    //  | | | | |/ _ \ \ /\ / /
    //  \ \_/ / |  __/\ V  V /
    //   \___/|_|\___| \_/\_/
    //=========================
    //=========================
    // Mark: - Rendering (View)
    //=========================
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        switch (state) {
            case MAIN:
                drawBattleScene(container, g);
                mainMenuTextDisplay.render(container, g);
                mainMenuMgr.render(container, g);
                break;
            case FIGHT:
                drawBattleScene(container, g);
                fightMenuMgr.render(container, g);
                fightMenuCancelMgr.render(container, g);
                break;
            case RUN:
                drawBattleScene(container, g);
                break;
            case POKEMON:
                drawPokemon(container, g);
                break;
            case BAG:
                drawBattleScene(container, g);
                drawBag(container, g);
                break;
            case HANDLING_EVENTS:
                drawBattleScene(container, g);
                drawMessage(container, g);
        }
    }

    //==========================
    // Mark: - Rendering Helpers
    //==========================
    /**
     * Renders the battle scene
     *
     * @author Eric
     * @param container The container being drawn in
     * @param g The graphics context used to draw
     */
    private void drawBattleScene(GameContainer container, Graphics g) {
        g.setBackground(ColorUtil.getBlue());
        g.drawImage(bgdImage, 0, 0, container.getWidth(), bgdImage.getHeight(), 0, 0, bgdImage.getWidth(), bgdImage.getHeight());

        // Draw Pokemon
        playerImage.render(container, g);
        enemyImage.render(container, g);
        // Draw HP Bars
        hpBarViewMgr.render(container, g);
    }

    /**
     * Draw the bag interface
     *
     * @author Eric
     * @param container The container being drawn in
     * @param g The graphics context used to draw
     */
    private void drawBag(GameContainer container, Graphics g) {
        g.setBackground(Color.magenta);
    }

    /**
     * Draw the Message display interface
     *
     * @author Eric
     * @param container The container being drawn in
     * @param g The graphics context used to draw
     */
    private void drawMessage(GameContainer container, Graphics g) {
        textDisplayViewMgr.render(container, g);
    }

    /**
     * Draw the Pokemon choosing interface
     *
     * @author Eric
     * @param container The container being drawn in
     * @param g The graphics context used to draw
     */
    private void drawPokemon(GameContainer container, Graphics g) {
        g.setBackground(ColorUtil.getBlue());
        pokemonMenuMgr.render(container, g);
        if (pokemonFainted) {
            textDisplayViewMgr.render(container, g);
        } else {
            pokemonMenuTextDisplay.render(container, g);
            pokemonMenuCancelMgr.render(container, g);
        }
    }

    // ===============================================
    //  _____             _             _ _
    // /  __ \           | |           | | |
    // | /  \/ ___  _ __ | |_ _ __ ___ | | | ___ _ __
    // | |    / _ \| '_ \| __| '__/ _ \| | |/ _ \ '__|
    // | \__/\ (_) | | | | |_| | | (_) | | |  __/ |
    //  \____/\___/|_| |_|\__|_|  \___/|_|_|\___|_|
    // ===============================================
    //=====================================
    // Mark: - Logical Updates (Controller)
    //=====================================
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        hpBarViewMgr.getItem(0, 0).update(delta);
        hpBarViewMgr.getItem(1, 2).update(delta);
        playerImage.update(delta);
        enemyImage.update(delta);
        switch (state) {
            case HANDLING_EVENTS:
                if (!eventQueue.isEmpty()) {
                    delay -= delta;
                    if (delay <= 0) {
                        delay = 0;
                        eventQueue.poll();
                        handleNextEvent();
                    }
                } else {
                    if (exitOnEmptyQueue) {
                        exitOnEmptyQueue = false;
                        model.getUser().healPokemon();
                        model.getUser().setCurPokemon(model.getUser().getPokmeon(0));
                        SoundUtil.playAlmaMater();
                        game.enterState(main.Main.GameStateType.MAINMENU.getValue(), new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
                    } else {
                        this.state = BattleStateMenuType.MAIN;
                    }
                }
        }
    }

    /**
     * If the event Queue is not empty, handle the next one.
     *
     * @author Eric
     * @throws org.newdawn.slick.SlickException
     */
    public void handleNextEvent() throws SlickException {
        Event evt = eventQueue.peek();
        if (evt instanceof TextOutputEvent) {
            textDisplayViewMgr.getItem(0, 0).setText(((TextOutputEvent) evt).getMessage());
            delay += 2000;
        } else if (evt instanceof UpdateHealthBarEvent) {
            handleUpdateHPEvent((UpdateHealthBarEvent) evt);
        } else if (evt instanceof PokemonFaintEvent) {
            handleFaintEvent((PokemonFaintEvent) evt);
        } else if (evt instanceof UserDefeatEvent) {
            exitOnEmptyQueue = true;
        } else if (evt instanceof EnemyDefeatEvent) {
            if (model.curEnemyIsProf()) {
                model.incrementProf();
            }
            exitOnEmptyQueue = true;
        } else if (evt instanceof SwitchPokemonEvent) {
            handleSwitchEvent((SwitchPokemonEvent) evt);
        }
    }

    /**
     * Handles what to do when pokemon are switched in and out
     *
     * @author Eric
     * @param spe The SwitchPokemonEvent describing what is happening
     */
    private void handleSwitchEvent(SwitchPokemonEvent spe) {
        SoundUtil.playSwap();
        if (spe.getSwitchPokemon() != null) {
            if (spe.getSwitchPokemon().getTrainer() == TrainerType.NPC) {
                enemyImage.swap(model.getEnemy().getCurPokemon().getID());
                hpBarViewMgr.set(0, 0, new InfoPanel(model.getEnemy().getCurPokemon().getCurHealth(), model.getEnemy().getCurPokemon().getHealth(), model.getEnemy().getCurPokemon().getName()));
            } else {
                playerImage.swap(model.getUser().getCurPokemon().getID());
                mainMenuTextDisplay.getSelected().setText("What will " + model.getUser().getCurPokemon().getNickname() + " do?");
            }
        } else {
            playerImage.swap(model.getImgIndex(TrainerType.USER));
            enemyImage.swap(model.getImgIndex(TrainerType.NPC));
        }
    }

    /**
     * Handles what to do in the case that a Pokemon faints
     *
     * @author Eric
     * @param pfe The PokemonFaintEvent describing what happened
     * @throws SlickException
     */
    private void handleFaintEvent(PokemonFaintEvent pfe) throws SlickException {
        SoundUtil.playFaint();
        switch (pfe.getTrainerType()) {
            case NPC:
                enemyImage.disappear();
                if (model.getEnemy().pokemonLiving()) {
                    eventQueue.poll();
                    handleNewEvents(control.enemyFaintSwitch());
                } else {
                    SoundUtil.playVictory();
                }
                break;
            case USER:
                if (model.getUser().pokemonLiving()) {
                    updatePokemonMenuOptions();
                    pokemonFainted = true;
                    state = BattleStateMenuType.POKEMON;
                    textDisplayViewMgr.getItem(0, 0).setText("Choose which Pokemon to send out");
                }
                break;
        }
    }

    /**
     * Handles what to do when the HP of a Pokemon is changed
     *
     * @author Eric
     * @param uhbe The UpdateHealthBarEvent that describes what happened
     */
    private void handleUpdateHPEvent(UpdateHealthBarEvent uhbe) {
        switch (uhbe.getTrainerType()) {
            case NPC:
                playerImage.attack();
                if (uhbe.getNewCurrHealth() != hpBarViewMgr.getItem(0, 0).getHP()) {
                    SoundUtil.playHit();
                    enemyImage.defend();
                    hpBarViewMgr.getItem(0, 0).setHP(uhbe.getNewCurrHealth());
                }
                break;
            case USER:
                enemyImage.attack();
                if (uhbe.getNewCurrHealth() != hpBarViewMgr.getItem(1, 2).getHP()) {
                    SoundUtil.playHit();
                    playerImage.defend();
                    hpBarViewMgr.getItem(1, 2).setHP(uhbe.getNewCurrHealth());
                }
                break;
        }
        delay += 2000;
    }

    /**
     * Handler for button selection in the main menu
     *
     * @author Eric
     * @throws org.newdawn.slick.SlickException
     */
    public void handleMainMenuSelection() throws SlickException {
        switch (mainMenuMgr.getSelected().getText()) {
            case "Fight":
                updateFightMenuOptions();
                state = BattleStateMenuType.FIGHT;
                break;
            case "Pokemon":
                updatePokemonMenuOptions();
                state = BattleStateMenuType.POKEMON;
                break;
            case "Bag":
                eventQueue.add(new TextOutputEvent("College students can't afford stuff to put in a bag!"));
                this.state = BattleStateMenuType.HANDLING_EVENTS;
                handleNextEvent();
                break;
            case "Run":
                eventQueue.add(new TextOutputEvent("You can't run from a trainer battle!"));
                this.state = BattleStateMenuType.HANDLING_EVENTS;
                handleNextEvent();
                break;
        }
    }

    /**
     * Updates the available moves in the fight menu
     *
     * @author Eric
     */
    private void updateFightMenuOptions() {
        Move[] moves = model.getUser().getCurPokemon().getMoves();
        int[][] slots = new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        fightMenuMgr.clear();
        for (int i = 0; i < 4; i++) {
            if (i < moves.length) {
                fightMenuMgr.set(slots[i][0], slots[i][1], new MenuButton(moves[i].getName()));
            }
        }
    }

    /**
     * Handler for button selection in the fight menu
     *
     * @author Eric
     * @throws org.newdawn.slick.SlickException
     */
    public void handleFightMenuSelection() throws SlickException {
        // Handle selection of each move
        if (fightMenuMgr.getSelected().getText().equals(model.getUser().getCurPokemon().getMoves()[0].getName())) {
            handleNewEvents(control.chooseAttack(model.getUser().getCurPokemon().getMoves()[0]));
        } else if (fightMenuMgr.getSelected().getText().equals(model.getUser().getCurPokemon().getMoves()[1].getName())) {
            handleNewEvents(control.chooseAttack(model.getUser().getCurPokemon().getMoves()[1]));
        } else if (fightMenuMgr.getSelected().getText().equals(model.getUser().getCurPokemon().getMoves()[2].getName())) {
            handleNewEvents(control.chooseAttack(model.getUser().getCurPokemon().getMoves()[2]));
        } else if (fightMenuMgr.getSelected().getText().equals(model.getUser().getCurPokemon().getMoves()[3].getName())) {
            handleNewEvents(control.chooseAttack(model.getUser().getCurPokemon().getMoves()[3]));
        }
        this.state = BattleStateMenuType.HANDLING_EVENTS;
    }

    /**
     * Updates the pokemon available to
     *
     * @author Eric
     * @throws SlickException
     */
    private void updatePokemonMenuOptions() throws SlickException {
        int[][] places = new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}, {0, 2}, {1, 2}};
        int i = 0;
        Image img;
        InfoPanel pnl;
        pokemonMenuMgr.clear();
        for (Pokemon pkmn : model.getUser().getPokemon()) {
            img = new Image("res/Images/Sprites/front/" + pkmn.getID() + ".png");
            pnl = new InfoPanel(pkmn.getCurHealth(), pkmn.getHealth(), pkmn.getNickname(), img);
            pnl.setEnabled(true);
            pokemonMenuMgr.set(places[i][0], places[i][1], pnl);
            i += 1;
        }
    }

    /**
     * Handler for button selection in the Pokemon menu
     *
     * @author Eric
     * @throws SlickException
     */
    private void handlePokemonMenuSelection() throws SlickException {
        for (Pokemon pkmn : model.getUser().getPokemon()) {
            if (pkmn.getName().equals(pokemonMenuMgr.getSelected().getText()) && pkmn.getCurHealth() == pokemonMenuMgr.getSelected().getHP()) {
                if (pkmn.isAlive()) {
                    // pokemonFainted true if the user's current pokemon fainted (not picking by choice)
                    if (!pokemonFainted) {
                        if (pkmn == model.getUser().getCurPokemon()) {
                            handleSubMenuCancelSelection();
                        } else {
                            handleNewEvents(control.chooseSwitchPokemon(pkmn));
                        }
                    } else {
                        pokemonFainted = false;
                        eventQueue.poll();
                        handleNewEvents(control.userFaintSwitch(pkmn));
                    }
                    hpBarViewMgr.set(1, 2, pokemonMenuMgr.getSelected().getCopy(false));
                    mainMenuTextDisplay.getItem(0, 0).setText("What will " + hpBarViewMgr.getItem(1, 2).getText() + " do?");
                    break;
                }
            }
        }

    }

    /**
     * Handler for selecting the cancel button in the fight menu
     *
     * @author Eric
     */
    public void handleSubMenuCancelSelection() {
        state = BattleStateMenuType.MAIN;
    }

    /**
     * Handles the addition of new events to be shown to the user
     *
     * @author Eric
     */
    private void handleNewEvents(List<Event> newEvents) throws SlickException {
        while (!newEvents.isEmpty()) {
            eventQueue.add(newEvents.get(0));
            newEvents.remove(0);
        }
        this.state = BattleStateMenuType.HANDLING_EVENTS;
        handleNextEvent();
    }

    //========================
    // Mark: - Mouse Listeners
    //========================
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        switch (state) {
            case MAIN:
                if (mainMenuMgr.getSelected().contains(x, y)) {
                    try {
                        handleMainMenuSelection();
                    } catch (SlickException e) {
                    }
                }
                break;
            case FIGHT:
                if (fightMenuMgr.getSelected().contains(x, y)) {
                    try {
                        handleFightMenuSelection();
                    } catch (SlickException e) {
                    }
                } else if (fightMenuCancelMgr.getSelected().contains(x, y)) {
                    handleSubMenuCancelSelection();
                    break;
                }
            case BAG:
                break;
            case POKEMON:
                if (pokemonMenuMgr.getSelected().contains(x, y)) {
                    try {
                        handlePokemonMenuSelection();
                    } catch (SlickException e) {
                    }
                } else if (pokemonMenuCancelMgr.getSelected().contains(x, y)) {
                    handleSubMenuCancelSelection();
                    break;
                }
                break;
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        switch (state) {
            case MAIN:
                for (MenuButton btn : mainMenuMgr.getItems()) {
                    if (btn.contains(newx, newy)) {
                        mainMenuMgr.setSelected(btn);
                    }
                }
                break;
            case FIGHT:
                if (fightMenuCancelMgr.getItem(0, 0).contains(newx, newy)) {
                    fightMenuCancelMgr.shouldShowHighlight(true);
                } else if (fightMenuCancelMgr.isShowingHighlight()) {
                    fightMenuCancelMgr.shouldShowHighlight(false);
                }
                for (MenuButton btn : fightMenuMgr.getItems()) {
                    if (btn.contains(newx, newy)) {
                        fightMenuMgr.setSelected(btn);
                    }
                }
                break;
            case POKEMON:
                if (pokemonMenuCancelMgr.getItem(0, 0).contains(newx, newy)) {
                    pokemonMenuCancelMgr.shouldShowHighlight(true);
                } else if (pokemonMenuCancelMgr.isShowingHighlight()) {
                    pokemonMenuCancelMgr.shouldShowHighlight(false);
                }
                for (InfoPanel pnl : pokemonMenuMgr.getItems()) {
                    if (pnl.contains(newx, newy)) {
                        pokemonMenuMgr.setSelected(pnl);
                    }
                }
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
                try {
                    handleMainMenuKeyPress(key);
                } catch (SlickException e) {
                }
                break;
            case FIGHT:
                try {
                    handleFightMenuKeyPress(key);
                } catch (SlickException e) {
                }
                break;
            case POKEMON:
                try {
                    handlePokemonMenuKeyPress(key);
                } catch (SlickException e) {
                }
                break;
            case HANDLING_EVENTS:
                switch (key) {
                    case Input.KEY_SPACE:
                    case Input.KEY_ENTER:
                        if (delay > 0) {
                            delay = 0;
                        }
                        break;
                }
        }
    }

    /**
     * Handler for key presses in the fight menu
     *
     * @author Eric
     */
    private void handleFightMenuKeyPress(int key) throws SlickException {
        switch (key) {
            case Input.KEY_LEFT:
                fightMenuMgr.setSelected(fightMenuMgr.getLeft());
                break;
            case Input.KEY_RIGHT:
                fightMenuMgr.setSelected(fightMenuMgr.getRight());
                break;
            case Input.KEY_DOWN:
                fightMenuMgr.setSelected(fightMenuMgr.getDown());
                break;
            case Input.KEY_UP:
                fightMenuMgr.setSelected(fightMenuMgr.getUp());
                break;
            case Input.KEY_SPACE:
            case Input.KEY_ENTER:
                handleFightMenuSelection();
                break;
            case Input.KEY_BACK:
            case Input.KEY_ESCAPE:
                handleSubMenuCancelSelection();
                break;
        }
    }

    /**
     * Handler for key presses in the main menu
     *
     * @author Eric
     */
    private void handleMainMenuKeyPress(int key) throws SlickException {
        switch (key) {
            case Input.KEY_LEFT:
                mainMenuMgr.setSelected(mainMenuMgr.getLeft());
                break;
            case Input.KEY_RIGHT:
                mainMenuMgr.setSelected(mainMenuMgr.getRight());
                break;
            case Input.KEY_DOWN:
                mainMenuMgr.setSelected(mainMenuMgr.getDown());
                break;
            case Input.KEY_UP:
                mainMenuMgr.setSelected(mainMenuMgr.getUp());
                break;
            case Input.KEY_SPACE:
            case Input.KEY_ENTER:
                handleMainMenuSelection();
                break;
        }
    }

    /**
     * Handler for key presses in the Pokemon Menu
     *
     * @param key The key pressed
     * @param c The character selected
     * @throws SlickException
     */
    private void handlePokemonMenuKeyPress(int key) throws SlickException {
        switch (key) {
            case Input.KEY_LEFT:
                pokemonMenuMgr.setSelected(pokemonMenuMgr.getLeft());
                break;
            case Input.KEY_RIGHT:
                pokemonMenuMgr.setSelected(pokemonMenuMgr.getRight());
                break;
            case Input.KEY_DOWN:
                pokemonMenuMgr.setSelected(pokemonMenuMgr.getDown());
                break;
            case Input.KEY_UP:
                pokemonMenuMgr.setSelected(pokemonMenuMgr.getUp());
                break;
            case Input.KEY_SPACE:
            case Input.KEY_ENTER:
                handlePokemonMenuSelection();
                break;
            case Input.KEY_DELETE:
            case Input.KEY_ESCAPE:
                if (pokemonFainted) {
                    handleSubMenuCancelSelection();
                }
                break;
        }
    }

    //====================================
    // Mark: - Accepting and Setting Input
    //====================================
    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    //============================================
    // MARK: - Everything past this line is unused
    //============================================
    //<editor-fold>
    //============================
    // Mark: - Unused Keylisteners
    //============================
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
