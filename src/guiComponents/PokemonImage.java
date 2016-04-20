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

    private float offsetX = 0;
    private float offsetY = 0;
    private float centerX = 0;
    private float centerY = 0;
    private int x;
    private int y;
    private int ymax;
    private Image image;
    private Image tmpImage;
    private LinkedBlockingQueue<AnimationAction> actions;
    private int currMS = 0;

    private static final float fightActionDurationMS = 200f;
    private static final float appearanceDurationMS = 300;
    private static final float defendDelay = fightActionDurationMS * 2;

    private float attackXOffset = 30f;
    private float defendXOffset = 3f;

    private float deltaXAttack = attackXOffset / fightActionDurationMS;
    private float deltaXDefend = defendXOffset / fightActionDurationMS;
    private float deltaYAppear = 0;

    public PokemonImage(int x0, int y0, Image image, TrainerType trainerType) {
        this(x0, y0, y0 + image.getHeight() / 2, image, trainerType);
    }

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
                    image = tmpImage;
                    x = (int) (centerX - image.getWidth() / 2);
                    y = (int) (centerY - image.getHeight() / 2);
                    offsetY = ymax - y;
                    tmpImage = null;
                    currMS = 0;
                    actions.poll();
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

    public void attack() {
        actions.add(AnimationAction.ATTACK);
    }

    public void defend() {
        actions.add(AnimationAction.DEFEND);
    }

    public void disappear() {
        actions.add(AnimationAction.DISAPPEAR);
    }

    public void appear() {
        actions.add(AnimationAction.APPEAR);
    }

    public void swap(Image image) {
        tmpImage = image;
        actions.add(AnimationAction.DISAPPEAR);
        actions.add(AnimationAction.SWAP);
        actions.add(AnimationAction.APPEAR);
    }

}
