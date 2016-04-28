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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Eric
 */
public class PokemonImage {

    public enum FillType {

        CROP, SCALE;
    }

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
    // The default values for the image, when not offset
    private int baseX;
    private int baseY;
    // Maximum absolute y position
    private int ymax;
    // Motion variables
    private float deltaXAttack = attackXOffset / fightActionDurationMS;
    private float deltaXDefend = defendXOffset / fightActionDurationMS;
    private float deltaYAppear = 0;

    //=====================
    // Mark: - Constructors
    //=====================
//    /**
//     * Constructor without a constrained maximum y
//     *
//     * @param x0 The location of the horizontal center of the image
//     * @param y0 The location of the vertical center of the image
//     * @param image The image to draw
//     * @param trainerType The type of trainer
//     */
    public PokemonImage(int tlx, int tly, int brx, int bry, FillType fillType, int id, TrainerType trainerType) throws SlickException {
        this(tlx, tly, brx, bry, fillType, "./res/Images/Sprites/" + (trainerType == TrainerType.NPC ? "front/" : "back/") + id + ".png", trainerType);
    }

    public PokemonImage(int tlx, int tly, int brx, int bry, FillType fillType, String ref, TrainerType trainerType) throws SlickException {
        this(tlx, tly, brx, bry, fillType, new Image(ref), trainerType);
    }

    public PokemonImage(int tlx, int tly, int brx, int bry, FillType fillType, Image image, TrainerType trainerType) {
        // Set drawing constants and do setup
        drawRect = new Rectangle(tlx, tly, brx - tlx, bry - tly);
        this.fillType = fillType;
        ymax = (int) drawRect.getMaxY();
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
     * @param container The container to be drawn in
     * @param g The Graphics context to draw with
     */
    public void render(GUIContext container, Graphics g) {
        g.drawImage(image,
                    baseX + offsetX, baseY + offsetY,
                    baseX + offsetX + image.getWidth(), ymax,
                    0, 0,
                    image.getWidth(), ymax - (baseY + offsetY));
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
                        offsetX += deltaXDefend * delta;
                    } else if (currMS < defendDelay + fightActionDurationMS * 0.75f) {
                        offsetX -= deltaXDefend * delta;
                    } else {
                        offsetX = 0;
                        currMS = 0;
                        actions.poll();
                    }
                    break;
                case SWAP:
                    if (currMS > 500) {
                        setImage(tmpImage);
                        tmpImage = null;
                        currMS = 0;
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
                    if (currMS < appearanceDurationMS && baseY + offsetY < ymax) {
                        offsetY += deltaYAppear * delta;
                    } else {
                        offsetY = ymax - baseY;
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

    private void setImage(Image img) {
        if (fillType == FillType.SCALE && img.getHeight() > drawRect.getHeight()) {
            image = img.getScaledCopy(0.9f * drawRect.getHeight() / img.getHeight());
        } else {
            image = img;
        }
        updateImageConstants();
    }
}
