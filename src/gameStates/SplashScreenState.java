/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates;

import guiComponents.FontUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Eric
 */
public class SplashScreenState implements GameState {

    //=========================
    // Mark: - Member Variables
    //=========================
    // Stored for game use
    private int ID;
    private StateBasedGame game;
    // Font Rendering
    private TrueTypeFont pixelFont;
    private Color pixelFontColor;
    private float fadeDiff = -0.02f;
    // Music
    private Music splashScreenMusic;
    // Images
    private Image pokemonLogo;
    private Image splashScreenImage;
    private Image versionImage;

    //====================
    // Mark: - Constructor
    //====================
    public SplashScreenState(int SPLASHSCREEN) {
        ID = SPLASHSCREEN;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        this.game = game;

        // Load Music
        splashScreenMusic = new Music("res/Sounds/OverworldTheme.ogg");

        // Load Images
        pokemonLogo = new Image("res/Images/SplashScreen/PokemonLogo.png").getScaledCopy(0.9f);
        splashScreenImage = new Image("res/Images/SplashScreen/Bouffalant.png");
        versionImage = new Image("res/Images/SplashScreen/Version.png").getScaledCopy(0.5f);

        // Load fonts
        try {
            pixelFont = FontUtils.getStdPixelFont();
        } catch (SlickException ex) {
            System.out.println("Error, failed to load font");
        }
        pixelFontColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    //==========================
    // Mark: - Graphics Handling
    //==========================
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        // Draw Background
        g.setBackground(Color.white);
        g.setColor(new Color(1, 84, 150));
        g.fill(new Polygon(new float[]{0.0f, 0.0f, container.getWidth() - 10f, 0.0f, 0.0f, container.getHeight()}));
        g.setColor(new Color(243, 110, 33));
        g.fill(new Polygon(new float[]{container.getWidth(), container.getHeight(), container.getWidth(), 10f, 0.0f, container.getHeight()}));

        // Draw Pokemon Logo
        g.drawImage(pokemonLogo, container.getWidth() / 2 - pokemonLogo.getWidth() / 2, 0,
                    container.getWidth() / 2 + pokemonLogo.getWidth() / 2, pokemonLogo.getHeight(),
                    0, 0, pokemonLogo.getWidth(), pokemonLogo.getHeight());

        // Draw center image
        g.drawImage(splashScreenImage, container.getWidth() / 4, container.getHeight() / 4,
                    3 * container.getWidth() / 4, 3 * container.getHeight() / 4,
                    0, 0, splashScreenImage.getWidth(), splashScreenImage.getHeight());

        // Draw version Image
        g.drawImage(versionImage, container.getWidth() / 2 - versionImage.getWidth() / 2, container.getHeight() * 2 / 3,
                    container.getWidth() / 2 + versionImage.getWidth() / 2, 5 * container.getHeight() / 6,
                    0, 0, versionImage.getWidth(), versionImage.getHeight());

        // Draw prompt String
        String promptString = "Press enter/space to start";
        pixelFont.drawString(container.getWidth() / 2 - pixelFont.getWidth(promptString) / 2, (5 * container.getHeight() / 6), promptString, pixelFontColor);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // Updates the alpha of the text in the prompt
        if (pixelFontColor.a <= 0.02 && fadeDiff < 0) {
            pixelFontColor.a = 0.0f;
            fadeDiff *= -1;
        } else if (pixelFontColor.a >= 0.98 && fadeDiff > 0) {
            pixelFontColor.a = 1.0f;
            fadeDiff *= -1;
        } else {
            pixelFontColor.a += fadeDiff;
        }
    }

    //=========================
    // Mark: - Entering/Exiting
    //=========================
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        splashScreenMusic.loop();
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
    }

    private void goToMenu() {
        if (splashScreenMusic != null && splashScreenMusic.playing()) {
            splashScreenMusic.fade(500, 0.0f, false);
        }
        game.enterState(GameStateType.BATTLE.getValue(), new FadeOutTransition(), new FadeInTransition());
    }

    //=======================
    // Mark: - Input Handlers
    //=======================
    @Override
    public void mouseWheelMoved(int change) {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        goToMenu();
    }

    @Override
    public void mousePressed(int button, int x, int y) {

    }

    @Override
    public void mouseReleased(int button, int x, int y) {

    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {

    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {

    }

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

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_SPACE:
            case Input.KEY_ENTER:
                goToMenu();
                break;
        }
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
}
