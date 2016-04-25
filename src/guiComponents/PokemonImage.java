/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 17, 2016
 * Time: 6:27:30 PM
 *
 * Project: csci205FinalProject
 * Package: guiComponents
 * File: ImageAnimation
 * Description:
 *
 * ****************************************
 */
package guiComponents;

import PokemonObjects.TrainerType;
import java.util.concurrent.LinkedBlockingQueue;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Eric
 */
public class PokemonImage {

    private enum AnimationAction {

        ATTACK, DEFEND, DISAPPEAR, APPEAR, SWAP;
    }

    // Images for drawing and swapping
    private Image image;
    private Image tmpImage;

    // Animation data
    private LinkedBlockingQueue<AnimationAction> actions;
    private int currMS = 0;

    // Constants for drawing
    private float attackXOffset = 30f;
    private float defendXOffset = 5f;
    private static final float fightActionDurationMS = 200f;
    private static final float appearanceDurationMS = 300;
    private static final float defendDelay = fightActionDurationMS * 2;

    // Variable constraints and positions
    private boolean freeYMax = false;
    private float offsetX = 0;
    private float offsetY = 0;
    private float centerX = 0;
    private float centerY = 0;
    private int x;
    private int y;
    private int ymax;
    private float deltaXAttack = attackXOffset / fightActionDurationMS;
    private float deltaXDefend = defendXOffset / fightActionDurationMS;
    private float deltaYAppear = 0;

    //=====================
    // Mark: - Constructors
    //=====================
    /**
     * Constructor without a constrained maximum y
     *
     * @param x0 The location of the horizontal center of the image
     * @param y0 The location of the vertical center of the image
     * @param image The image to draw
     * @param trainerType The type of trainer
     */
    public PokemonImage(int x0, int y0, Image image, TrainerType trainerType) {
        this(x0, y0, y0 + image.getHeight() / 2, image, trainerType);
    }

    /**
     * Constructor without a constrained maximum y
     *
     * @param x0 The location of the horizontal center of the image
     * @param y0 The location of the vertical center of the image
     * @param ymax The maximum y value of the image
     * @param image The image to draw
     * @param trainerType The type of trainer
     */
    public PokemonImage(int x0, int y0, int ymax, Image image, TrainerType trainerType) {
        actions = new LinkedBlockingQueue<>();
        centerX = x0;
        centerY = y0;
        x = (int) (x0 - image.getWidth() / 2f);
        y = (int) (y0 - image.getHeight() / 2f);
        this.ymax = ymax;
        this.offsetY = ymax - y;
        this.image = image;
        deltaYAppear = (ymax - y) / appearanceDurationMS;

        // Flips for NPC
        if (trainerType == TrainerType.NPC) {
            deltaXAttack *= -1;
            deltaXDefend *= -1;
        }
    }

    //==================
    // Mark: - Rendering
    //==================
    /**
     * Render the image
     *
     * @param container The container to be drawn in
     * @param g The Graphics context to draw with
     */
    public void render(GUIContext container, Graphics g) {
        g.drawImage(image,
                    x + (int) offsetX,
                    y + (int) offsetY,
                    x + (int) offsetX + image.getWidth(),
                    ymax,
                    0,
                    0,
                    image.getWidth(),
                    ymax - (y + offsetY));
    }

    //=================
    // Mark: - Updating
    //=================
    /**
     * Logically updates the image when it is animating
     *
     * @param delta The number of milliseconds since the last update
     */
    public void update(float delta) {
        if (!actions.isEmpty()) {
            currMS += delta;
            switch (actions.peek()) {
                case ATTACK:
                    if (currMS < fightActionDurationMS / 2) {
                        System.out.println(offsetX);
                        offsetX += deltaXAttack * delta;
                    } else if (currMS < fightActionDurationMS) {
                        offsetX -= deltaXAttack * delta;
                    } else {
                        offsetX = 0;
                        currMS = 0;
                        actions.poll();
                    }
                    break;
                case DEFEND:
                    if (currMS < defendDelay) {
                    } else if (currMS < defendDelay + fightActionDurationMS * 0.25f || (currMS < defendDelay + fightActionDurationMS && currMS > defendDelay + fightActionDurationMS * 0.75f)) {
                        offsetX += deltaXAttack * delta;
                    } else if (currMS < defendDelay + fightActionDurationMS * 0.75f) {
                        offsetX -= deltaXAttack * delta;
                    } else {
                        offsetX = 0;
                        currMS = 0;
                        actions.poll();
                    }
                    break;
                case SWAP:
                    if (currMS > 500) {
                        image = tmpImage;
                        x = (int) (centerX - image.getWidth() / 2);
                        y = (int) (centerY - image.getHeight() / 2);
                        if (freeYMax) {
                            ymax = (int) (centerY + image.getHeight() / 2);
                        }
                        offsetY = ymax - y;
                        tmpImage = null;
                        currMS = 0;
                        deltaYAppear = (ymax - y) / appearanceDurationMS;
                        actions.poll();
                    }
                    break;
                case APPEAR:
                    if (currMS < appearanceDurationMS && offsetY > 0) {
                        offsetY -= deltaYAppear * delta;
                    } else {
                        offsetY = 0;
                        currMS = 0;
                        actions.poll();
                    }
                    break;
                case DISAPPEAR:
                    if (currMS < appearanceDurationMS && y + offsetY < ymax) {
                        offsetY += deltaYAppear * delta;
                    } else {
                        offsetY = ymax - y;
                        currMS = 0;
                        actions.poll();
                    }
                    break;
            }
        }

    }

    //===================
    // Mark: - Animations
    //===================
    /**
     * Animate the button for an attack action
     */
    public void attack() {
        actions.add(AnimationAction.ATTACK);
    }

    /**
     * Animate the button for a defend action
     */
    public void defend() {
        actions.add(AnimationAction.DEFEND);
    }

    /**
     * Animate the button for a disappear action
     */
    public void disappear() {
        actions.add(AnimationAction.DISAPPEAR);
    }

    /**
     * Animate the button for an appear action
     */
    public void appear() {
        actions.add(AnimationAction.APPEAR);
    }

    /**
     * Swap the image with a new one, animating the switch by disappearing and
     * reappearing
     *
     * @param image The new image
     * @param freeYMax Whether the ymax should be recalculated when the switch
     * is made
     */
    public void swap(Image image, boolean freeYMax) {
        tmpImage = image;

        actions.add(AnimationAction.DISAPPEAR);
        actions.add(AnimationAction.SWAP);
        actions.add(AnimationAction.APPEAR);
    }

}
