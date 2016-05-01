/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 24, 2016
 * Time: 10:43:29 PM
 *
 * Project: csci205FinalProject
 * Package: guiComponents
 * File: SoundUtil
 * Description:
 *
 * ****************************************
 */
package util;

import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Utility class for playing sounds. This makes it so sound doesn't have to be
 * changed when switching between gamestates, instead it can be changed whenever
 * desired
 *
 * @author Eric
 */
public class SoundUtil {

    // Currently playing Music
    private static Music curMusic;

    // Music
    private static Music almaMaterMusic;
    private static Music enterBattleMusic;
    private static Music battleMusic;
    private static Music victoryMusic;

    // Sounds
    private static Sound faintSound;
    private static Sound hitSound;
    private static Sound swapSound;

    private SoundUtil() {
    }

    /**
     * Loads all sounds
     *
     * @author Eric
     * @throws SlickException
     */
    public static void init() throws SlickException {
        // Load Music

        almaMaterMusic = new Music("./res/Sounds/Music/AlmaMater.ogg");
        System.out.print("Loading Sounds");
        enterBattleMusic = new Music("./res/Sounds/Music/BattleIntro.ogg");
        System.out.print(".");
        enterBattleMusic.addListener(new MusicListener() {

            @Override
            public void musicEnded(Music music) {
                curMusic = battleMusic;
                curMusic.loop(1.0f, 0.1f);
            }

            @Override
            public void musicSwapped(Music music, Music newMusic) {
            }
        });
        battleMusic = new Music("./res/Sounds/Music/BattleLoop.ogg");
        System.out.print(".");
        victoryMusic = new Music("./res/Sounds/Music/Victory.ogg");
        System.out.print(".");

        // Load Sounds
        faintSound = new Sound("./res/Sounds/Effects/Faint.ogg");
        System.out.print(".");
        hitSound = new Sound("./res/Sounds/Effects/Hit.ogg");
        System.out.print(".");
        swapSound = new Sound("./res/Sounds/Effects/Swap.ogg");
        System.out.println("Done.");
    }

    //==============
    // Mark: - Music
    //==============
    /**
     * Switches the current music to the Alma Mater
     *
     * @author Eric
     */
    public static void playAlmaMater() {
        swapMusic(almaMaterMusic, true);
    }

    /**
     * Switches the current music to the battle music, including the entry sound
     *
     * @author Eric
     */
    public static void playEnterBattle() {
        swapMusic(enterBattleMusic, false);
    }

    /**
     * Switches the current music to the battle victory music
     *
     * @author Eric
     */
    public static void playVictory() {
        swapMusic(victoryMusic, true);
    }

    /**
     * Switches to a new music, and whether or not to loop it
     *
     * @author Eric
     * @param newMusic The new music to play
     * @param loop Whether or not the music should be looped
     */
    private static void swapMusic(Music newMusic, boolean loop) {
        if (curMusic != newMusic) {
            if (curMusic != null) {
                curMusic.stop();
            }
            curMusic = newMusic;
            if (loop) {
                curMusic.loop(1.0f, 0.1f);
            } else {
                curMusic.play(1.0f, 0.1f);
            }
        }
    }

    //======================
    // Mark: - Sound Effects
    //======================
    /**
     * Play the Pokemon faint sound
     *
     * @author Eric
     */
    public static void playFaint() {
        faintSound.play(1.0f, 0.5f);
    }

    /**
     * Play the Pokemon hit sound
     *
     * @author Eric
     */
    public static void playHit() {
        hitSound.play(1.0f, 0.5f);
    }

    /**
     * Play the Pokemon swap sound
     *
     * @author Eric
     */
    public static void playSwap() {
        swapSound.play(1.0f, 0.5f);
    }
}
