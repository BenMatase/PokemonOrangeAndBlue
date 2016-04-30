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
package gameStates.guiComponents;

import model.PokemonObjects.TrainerType;
import java.util.concurrent.LinkedBlockingQueue;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 * A class for drawing and animating images of Pokemon. PokemonImages are
 * capable of appearing, disappearing, attacking, defending, and swapping their
 * image out.
 *
 * @author Eric
 */
public class PokemonImage {

    /**
     * An enum telling whether the image should be cropped or scaled. Can only
     * be set at initialization
     *
     * @author Eric
     */
    public enum FillType {

        CROP, SCALE;
    }

    /**
     * An enum describing the animations that can be performed by the
     * PokemonImage.
     *
     * @author Eric
     */
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
    private float defendXOffset = 10f;
    private static final float fightActionDurationMS = 200f;
    private static final float appearanceDurationMS = 300;
    private static final float defendDelay = fightActionDurationMS * 2;

    // Variable constraints and positions
    private TrainerType type;
    private Rectangle drawRect;
    private FillType fillType;
    private float offsetX = 0;
    private float offsetY = 0;
    private int baseX;
    private int baseY;
    private int maxY;

    // Motion variables
    private float deltaXAttack = attackXOffset / fightActionDurationMS;
    private float deltaXDefend = defendXOffset / fightActionDurationMS;
    private float deltaYAppear = 0;

    //=====================
    // Mark: - Constructors
    //=====================
    /**
     * Constructor that constructs the image from the Pokemon id and Trainer
     * type
     *
     * @author Eric
     * @param tlx Top left X of the draw area
     * @param tly Top left Y of the draw area
     * @param brx Bottom right X of the draw area
     * @param bry Bottom right Y of the draw area
     * @param fillType Whether the image should be cropped or scaled
     * @param id The National Dex ID of the Pokemon
     * @param trainerType Whether the owning trainer is an enemy or the player
     * @throws SlickException
     */
    public PokemonImage(int tlx, int tly, int brx, int bry, FillType fillType, int id, TrainerType trainerType) throws SlickException {
        this(tlx, tly, brx, bry, fillType, "./res/Images/Sprites/" + (trainerType == TrainerType.NPC ? "front/" : "back/") + id + ".png", trainerType);
    }

    /**
     * Constructor that constructs the image from the Pokemon id and Trainer
     * type
     *
     * @author Eric
     * @param tlx Top left X of the draw area
     * @param tly Top left Y of the draw area
     * @param brx Bottom right X of the draw area
     * @param bry Bottom right Y of the draw area
     * @param fillType Whether the image should be cropped or scaled
     * @param ref The String reference to the image
     * @param trainerType Whether the owning trainer is an enemy or the player
     * @throws SlickException
     */
    public PokemonImage(int tlx, int tly, int brx, int bry, FillType fillType, String ref, TrainerType trainerType) throws SlickException {
        this(tlx, tly, brx, bry, fillType, new Image(ref), trainerType);
    }

    /**
     * Constructor that constructs the image from the Pokemon id and Trainer
     * type
     *
     * @author Eric
     * @param tlx Top left X of the draw area
     * @param tly Top left Y of the draw area
     * @param brx Bottom right X of the draw area
     * @param bry Bottom right Y of the draw area
     * @param fillType Whether the image should be cropped or scaled
     * @param image The image to draw
     * @param trainerType Whether the owning trainer is an enemy or the player
     */
    public PokemonImage(int tlx, int tly, int brx, int bry, FillType fillType, Image image, TrainerType trainerType) {
        // Set drawing constants and do setup
        drawRect = new Rectangle(tlx, tly, brx - tlx, bry - tly);
        this.fillType = fillType;
        maxY = (int) drawRect.getMaxY();
        this.offsetY = drawRect.getHeight();
        setImage(image);
        this.type = trainerType;
        this.actions = new LinkedBlockingQueue<>();
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
     * @author Eric
     * @param container The container to be drawn in
     * @param g The Graphics context to draw with
     */
    public void render(GUIContext container, Graphics g) {
        g.drawImage(image,
                    baseX + offsetX, baseY + offsetY,
                    baseX + offsetX + image.getWidth(), maxY,
                    0, 0,
                    image.getWidth(), maxY - (baseY + offsetY));
    }

    //=================
    // Mark: - Updating
    //=================
    /**
     * Logically updates the image when it is animating
     *
     * @author Eric
     * @param delta The number of milliseconds since the last update
     */
    public void update(float delta) {
        if (!actions.isEmpty()) {
            currMS += delta;
            switch (actions.peek()) {
                case ATTACK:
                    attackUpdate(delta);
                    break;
                case DEFEND:
                    defendUpdate(delta);
                    break;
                case SWAP:
                    swapUpdate(delta);
                    break;
                case APPEAR:
                    appearUpdate(delta);
                    break;
                case DISAPPEAR:
                    disappearUpdate(delta);
                    break;
            }
        }
    }

    /**
     * Updates the draw location for defend animations
     *
     * @author Eric
     * @param delta The delta in ms
     */
    private void defendUpdate(float delta) {
        if (currMS < defendDelay) {
        } else if (currMS < defendDelay + fightActionDurationMS * 0.25f || (currMS < defendDelay + fightActionDurationMS && currMS > defendDelay + fightActionDurationMS * 0.75f)) {
            offsetX += deltaXDefend * delta;
        } else if (currMS < defendDelay + fightActionDurationMS * 0.75f) {
            offsetX -= deltaXDefend * delta;
        } else {
            offsetX = 0;
            currMS = 0;
            actions.poll();
        }
    }

    /**
     * Updates the draw location for attack animations
     *
     * @author Eric
     * @param delta The delta in ms
     */
    private void attackUpdate(float delta) {
        if (currMS < fightActionDurationMS / 2) {
            offsetX += deltaXAttack * delta;
        } else if (currMS < fightActionDurationMS) {
            offsetX -= deltaXAttack * delta;
        } else {
            offsetX = 0;
            currMS = 0;
            actions.poll();
        }
    }

    /**
     * Updates the draw location for swap animations
     *
     * @author Eric
     * @param delta The delta in ms
     */
    private void swapUpdate(float delta) {
        if (currMS > 500) {
            setImage(tmpImage);
            tmpImage = null;
            currMS = 0;
            actions.poll();
        }
    }

    /**
     * Updates the draw location for appear animations
     *
     * @author Eric
     * @param delta The delta in ms
     */
    private void appearUpdate(float delta) {
        if (currMS < appearanceDurationMS && offsetY > 0) {
            offsetY -= deltaYAppear * delta;
        } else {
            offsetY = 0;
            currMS = 0;
            actions.poll();
        }
    }

    /**
     * Updates the draw location for disappear animations
     *
     * @author Eric
     * @param delta The delta in ms
     */
    private void disappearUpdate(float delta) {
        if (currMS < appearanceDurationMS && baseY + offsetY < maxY) {
            offsetY += deltaYAppear * delta;
        } else {
            offsetY = maxY - baseY;
            currMS = 0;
            actions.poll();
        }
    }

    //===================
    // Mark: - Animations
    //===================
    /**
     * Animate the button for an attack action
     *
     * @author Eric
     */
    public void attack() {
        actions.add(AnimationAction.ATTACK);
    }

    /**
     * Animate the button for a defend action
     *
     * @author Eric
     */
    public void defend() {
        actions.add(AnimationAction.DEFEND);
    }

    /**
     * Animate the button for a disappear action
     *
     * @author Eric
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
     * Swaps the current image with the image of the Pokemon with the given ID
     *
     * @author Eric & Jason
     * @param id The National Dex ID of the Pokemon
     */
    public void swap(int id) {
        try {
            tmpImage = new Image("./res/Images/Sprites/" + (type == TrainerType.NPC ? "front/" : "back/") + id + ".png");
        } catch (SlickException ex) {
            tmpImage = image;
        }
        actions.add(AnimationAction.DISAPPEAR);
        actions.add(AnimationAction.SWAP);
        actions.add(AnimationAction.APPEAR);
    }

    /**
     * Updates the drawing variables for the image when it is swapped
     *
     * @author Eric & Jason
     */
    private void updateImageConstants() {
        switch (fillType) {
            case CROP:
                if (drawRect.getHeight() > image.getHeight()) {
                    this.deltaYAppear = drawRect.getHeight() / appearanceDurationMS;
                    baseY = (int) (drawRect.getMaxY() - image.getHeight() * 0.75f);
                } else {
                    this.deltaYAppear = image.getHeight() / appearanceDurationMS;
                    baseY = (int) drawRect.getY();
                }
                break;
            case SCALE:
                baseY = (int) drawRect.getMaxY() - image.getHeight();
                deltaYAppear = image.getHeight() / appearanceDurationMS;
                break;
        }
        offsetY = drawRect.getMaxY() - baseY;
        baseX = (int) (drawRect.getCenterX() - image.getWidth() / 2);

    }

    /**
     * Calls the necessary functions to update the image and the necessary
     * constraints
     *
     * @author Eric & Jason
     * @param img The new image to be shown
     */
    private void setImage(Image img) {
        if (fillType == FillType.SCALE && img.getHeight() > drawRect.getHeight()) {
            image = img.getScaledCopy(0.9f * drawRect.getHeight() / img.getHeight());
        } else {
            image = img;
        }
        updateImageConstants();
    }
}
