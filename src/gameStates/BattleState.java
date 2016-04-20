/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates;

import BattleUtility.AnimationEvent;
import BattleUtility.EnemyDefeatEvent;
import BattleUtility.Event;
import BattleUtility.PokemonFaintEvent;
import BattleUtility.SwitchPokemonEvent;
import BattleUtility.TextOutputEvent;
import BattleUtility.UpdateHealthBarEvent;
import BattleUtility.UserDefeatEvent;
import PokeModel.PokeModel;
import PokemonController.BattleControl;
import PokemonObjects.Move;
import PokemonObjects.Pokemon;
import PokemonObjects.TrainerType;
import guiComponents.InfoPanel;
import guiComponents.MenuButton;
import guiComponents.MenuLayoutManager;
import guiComponents.PokemonImage;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * A game state that simulates a Pokemon battle
 *
 * @author Eric
 */
public class BattleState implements GameState {

    // Control and model components
    private int ID;
    private StateBasedGame game;
    private BattleMenuState state;
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
    private int ex = 418;
    private int ey = 55;
    private int px = 73;
    private int py = 193;

    // Text Display and animation handling
    private LinkedBlockingQueue<Event> eventQueue;
    private int delay = 0;

    // Music
    private Music introMusic;
    private Music bgdMusic;

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
        state = BattleMenuState.MAIN;
        bgdMusic = new Music("./res/Sounds/BattleThemeLoop.ogg");
        introMusic = new Music("./res/Sounds/BattleIntro.ogg");
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {

        beginMusic();

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
        RoundedRectangle right14DrawRect = new RoundedRectangle(container.getWidth() * 0.75f + X_PADDING * 0.5f,
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
        this.state = BattleMenuState.HANDLING_EVENTS;
    }

    public void beginMusic() {
        introMusic.addListener(new MusicListener() {

            @Override
            public void musicEnded(Music music) {
                bgdMusic.loop(1.0f, 0.25f);
                introMusic.removeListener(this);
            }

            @Override
            public void musicSwapped(Music music, Music newMusic) {

            }
        });
        introMusic.play();
        introMusic.play(1.0f, 0.25f);
    }

    public void loadImages() throws SlickException {
        bgdImage = new Image("res/Images/Battle/BattleGrass.png");
        Image tmp = new Image("./res/Images/Sprites/back/" + model.getUser().getCurPokemon().getID() + ".png");
        playerImage = new PokemonImage(px, py, bgdImage.getHeight(), tmp, TrainerType.USER);
        tmp = new Image("./res/Images/Sprites/front/" + model.getEnemy().getCurPokemon().getID() + ".png");
        enemyImage = new PokemonImage(ex, ey, tmp, TrainerType.NPC);
        tmp = null;
    }

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

    public void setupFightMenu(RoundedRectangle left, RoundedRectangle right) {
        fightMenuMgr = new MenuLayoutManager<>(left, 2, 2, MenuButton.class);
        updateFightMenuOptions();
        fightMenuCancelMgr = new MenuLayoutManager<>(right, 1, 1, MenuButton.class);
        fightMenuCancelMgr.set(0, 0, new MenuButton("Cancel"));
        fightMenuCancelMgr.shouldShowHighlight(false);
    }

    public void setupHPBars(GameContainer container) {
        // HP BARS
        hpBarViewMgr = new MenuLayoutManager<>(new RoundedRectangle(0, 0, container.getWidth(), bgdImage.getHeight(), 0), 2, 3, false, InfoPanel.class);
        hpBarViewMgr.set(1, 2, new InfoPanel(model.getUser().getCurPokemon().getCurHealth(), model.getUser().getCurPokemon().getHealth(), model.getUser().getCurPokemon().getNickname()));
        hpBarViewMgr.set(0, 0, new InfoPanel(model.getEnemy().getCurPokemon().getCurHealth(), model.getEnemy().getCurPokemon().getHealth(), model.getEnemy().getCurPokemon().getName()));
        hpBarViewMgr.disable();
    }

    public void setupPokemonMenu(GameContainer container, RoundedRectangle left, RoundedRectangle right) throws SlickException {
        // Pokemon Chooser Menu
        pokemonMenuMgr = new MenuLayoutManager<>(new RoundedRectangle(0, 0, container.getWidth(), bgdImage.getHeight(), 0), 2, 3, true, InfoPanel.class);
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
        model.setEnemy(
            null);

        // Clean up music
        if (bgdMusic.playing()) {
            bgdMusic.fade(500, 0, true);
        }
        eventQueue = null;
    }

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
                g.setBackground(new Color(100, 0, 100));
                pokemonMenuMgr.render(container, g);
                pokemonMenuTextDisplay.render(container, g);
                pokemonMenuCancelMgr.render(container, g);
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
        g.setBackground(Color.darkGray);
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
        g.setBackground(Color.green);
    }

    //=====================================
    // Mark: - Logical Updates (Controller)
    //=====================================
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        hpBarViewMgr.getButton(0, 0).update(delta);
        hpBarViewMgr.getButton(1, 2).update(delta);
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
                        game.enterState(GameStateType.SPLASHSCREEN.getValue(), new FadeOutTransition(), new FadeInTransition());
                    } else {
                        this.state = BattleMenuState.MAIN;
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
            System.out.println("Update display text to: '" + ((TextOutputEvent) evt).getMessage() + "'");
            textDisplayViewMgr.getButton(0, 0).setText(((TextOutputEvent) evt).getMessage());
            delay += 2000;
        } else if (evt instanceof AnimationEvent) {
            System.out.println("Animation");
        } else if (evt instanceof UpdateHealthBarEvent) {
            handleUpdateHPEvent((UpdateHealthBarEvent) evt);
            System.out.println("Health Update");
        } else if (evt instanceof PokemonFaintEvent) {
            handleFaintEvent((PokemonFaintEvent) evt);
            System.out.println("Pokemon Faint");
            eventQueue.poll();
        } else if (evt instanceof UserDefeatEvent) {
            UserDefeatEvent ude = (UserDefeatEvent) evt;
            exitOnEmptyQueue = true;
            System.out.println("User Defeat");
        } else if (evt instanceof EnemyDefeatEvent) {
            EnemyDefeatEvent ede = (EnemyDefeatEvent) evt;
            exitOnEmptyQueue = true;
            System.out.println("Enemy Defeat");
        } else if (evt instanceof SwitchPokemonEvent) {
            handleSwitchEvent((SwitchPokemonEvent) evt);
            System.out.println("Switch Pokemon");
        } else if (evt != null) {
            System.out.println("Other: " + evt.getClass().getSimpleName());
        } else {
            System.out.println("Null event");
        }
    }

    private void handleSwitchEvent(SwitchPokemonEvent spe) {
        if (spe.getSwitchPokemon().getTrainer() == TrainerType.NPC) {
            try {
                enemyImage.swap(new Image("./res/Images/Sprites/front/" + model.getEnemy().getCurPokemon().getID() + ".png"));
            } catch (SlickException e) {
            }
        } else {
            try {
                playerImage.swap(new Image("./res/Images/Sprites/back/" + model.getUser().getCurPokemon().getID() + ".png"));
            } catch (SlickException e) {
            }
            mainMenuTextDisplay.getSelected().setText("What will " + model.getUser().getCurPokemon().getNickname() + "do?");
        }
    }

    private void handleFaintEvent(PokemonFaintEvent pfe) throws SlickException {
        switch (pfe.getTrainerType()) {
            case NPC:
                enemyImage.disappear();
                break;
            case USER:
                playerImage.disappear();
                if (model.getUser().pokemonLiving()) {
                    updatePokemonMenuOptions();
                    pokemonFainted = true;
                    state = BattleMenuState.POKEMON;
                }
                break;
        }
        delay += 2000;
    }

    private void handleUpdateHPEvent(UpdateHealthBarEvent uhbe) {
        System.out.println("Trainer type is ==> " + uhbe.getTrainerType());
        switch (uhbe.getTrainerType()) {
            case NPC:
                hpBarViewMgr.getButton(0, 0).setHP(uhbe.getNewCurrHealth());
                playerImage.attack();
                enemyImage.defend();
                break;
            case USER:
                hpBarViewMgr.getButton(1, 2).setHP(uhbe.getNewCurrHealth());
                playerImage.defend();
                enemyImage.attack();
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
                state = BattleMenuState.FIGHT;
                break;
            case "Pokemon":
                updatePokemonMenuOptions();
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

    /**
     * Updates the available moves in the fight menu
     *
     * @author Eric
     */
    private void updateFightMenuOptions() {
        Move[] moves = model.getUser().getCurPokemon().getMoves();
        int[][] slots = new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        for (int i = 0; i < 4; i++) {
            if (i < moves.length) {
                fightMenuMgr.set(slots[i][0],
                                 slots[i][1],
                                 new MenuButton(moves[i].getName()));
            } else {
                fightMenuMgr.set(slots[i][0], slots[i][1], null);
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
        this.state = BattleMenuState.HANDLING_EVENTS;
    }

    /**
     * Updates the pokemon available to
     *
     * @throws SlickException
     */
    private void updatePokemonMenuOptions() throws SlickException {
        int[][] places = new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}, {0, 2}, {1, 2}};
        int i = 0;
        Image img;
        InfoPanel pnl;
        for (Pokemon pkmn : model.getUser().getPokemon()) {
            img = new Image("./res/Images/Sprites/front/" + pkmn.getID() + ".png");
            pnl = new InfoPanel(pkmn.getCurHealth(), pkmn.getHealth(), pkmn.getNickname(), img);
            pnl.setEnabled(true);
            pokemonMenuMgr.set(places[i][0], places[i][1], pnl);
            i += 1;
        }
    }

    private void handlePokemonMenuSelection() throws SlickException {
        for (Pokemon pkmn : model.getUser().getPokemon()) {
            if (pkmn.getNickname().equals(pokemonMenuMgr.getSelected().getText())) {
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
                        handleNewEvents(control.switchNewPokemon(pkmn));
                    }
                    hpBarViewMgr.set(1, 2, pokemonMenuMgr.getSelected().getCopy(false));
                    mainMenuTextDisplay.getButton(0, 0).setText("What will " + hpBarViewMgr.getButton(1, 2).getText() + " do?");
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
        state = BattleMenuState.MAIN;
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
        handleNextEvent();
        this.state = BattleMenuState.HANDLING_EVENTS;
    }

    //========================
    // Mark: - Mouse Listeners
    //========================
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        System.out.print("Clicked in ");
        switch (state) {
            case MAIN:
                System.out.println("Main");
                if (mainMenuMgr.getSelected().contains(x, y)) {
                    try {
                        handleMainMenuSelection();
                    } catch (SlickException e) {
                    }
                }
                break;
            case FIGHT:
                System.out.println("Fight");
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
                System.out.println("Pokemon");
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
                for (MenuButton btn : mainMenuMgr.getButtons()) {
                    if (btn.contains(newx, newy)) {
                        mainMenuMgr.setSelected(btn);
                    }
                }
                break;
            case FIGHT:
                for (MenuButton btn : fightMenuMgr.getButtons()) {
                    if (btn.contains(newx, newy)) {
                        fightMenuMgr.setSelected(btn);
                    }
                }
                break;
            case POKEMON:
                for (MenuButton pnl : pokemonMenuMgr.getButtons()) {
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
            case MAIN: {
                try {
                    handleMainMenuKeyPress(key, c);
                } catch (SlickException e) {
                }
            }
            break;
            case FIGHT: {
                try {
                    handleFightMenuKeyPress(key, c);
                } catch (SlickException e) {
                }
            }
        }
    }

    /**
     * Handler for key presses in the fight menu
     *
     * @author Eric
     */
    private void handleFightMenuKeyPress(int key, char c) throws SlickException {
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
                state = BattleMenuState.MAIN;
                break;
        }
    }

    /**
     * Handler for key presses in the main menu
     *
     * @author Eric
     */
    private void handleMainMenuKeyPress(int key, char c) throws SlickException {
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

    //====================================
    // Mark: - Accepting and Setting Input
    //====================================
    @Override
    public boolean isAcceptingInput() {
        return this.state != BattleMenuState.HANDLING_EVENTS;
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
