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
    private int x;
    private int y;
    private int ymax;
    private Image image;
    private Image tmpImage;
    private LinkedBlockingQueue<AnimationAction> actions;
    private int currMS = 0;

    private static final float durationMS = 200f;
    private static final float attackXOffset = 30f;
    private static final float defendXOffset = 5f;
    private static final float defendDelay = durationMS * 2;
    private static final float deltaXAttack = attackXOffset / durationMS;
    private static final float deltaXDefend = defendXOffset / durationMS;

    public PokemonImage(int x0, int y0, Image image) {
        this(x0, y0, y0 + image.getHeight(), image);
    }

    public PokemonImage(int x0, int y0, int ymax, Image image) {
        actions = new LinkedBlockingQueue<>();
        x = x0;
        y = y0;
        this.ymax = ymax;
        this.image = image;
    }

    public void render(GUIContext container, Graphics g) {
        g.drawImage(image,
                    x + (int) offsetX,
                    y + (int) offsetY,
                    x + (int) offsetX + image.getWidth(),
                    y + (int) offsetY + image.getHeight() > ymax ? ymax : y + image.getHeight(),
                    0,
                    0,
                    image.getWidth(),
                    y + image.getHeight() > ymax ? ymax - y : image.getHeight());
    }

    public void update(float delta) {
        if (!actions.isEmpty()) {
            currMS += delta;
            switch (actions.peek()) {
                case ATTACK:
                    if (currMS < durationMS / 2) {
                        System.out.println(offsetX);
                        offsetX += deltaXAttack * delta;
                    } else if (currMS < durationMS) {
                        offsetX -= deltaXAttack * delta;
                    } else {
                        offsetX = 0;
                        currMS = 0;
                        actions.poll();
                    }
                    break;
                case DEFEND:
                    if (currMS < defendDelay) {

                    } else if (currMS < defendDelay + durationMS * 0.25f || (currMS < defendDelay + durationMS && currMS > defendDelay + durationMS * 0.75f)) {
                        offsetX += deltaXAttack * delta;
                    } else if (currMS < defendDelay + durationMS * 0.75f) {
                        offsetX -= deltaXAttack * delta;
                    } else {
                        offsetX = 0;
                        currMS = 0;
                        actions.poll();
                    }
                    break;
                case SWAP:
                    break;
                case APPEAR:
                    break;
                case DISAPPEAR:
                    break;
            }
        }

    }

    public void attack() {
        System.out.println("Attacking");
        actions.add(AnimationAction.ATTACK);
    }

    public void defend() {
        System.out.println("Defending");
        actions.add(AnimationAction.DEFEND);
    }

    public void disappear() {
        actions.add(AnimationAction.DISAPPEAR);
    }

    public void appear() {
        actions.add(AnimationAction.APPEAR);
    }

    public void swap() {
        actions.add(AnimationAction.DISAPPEAR);
        actions.add(AnimationAction.SWAP);
        actions.add(AnimationAction.APPEAR);
    }

}
