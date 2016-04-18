/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 11, 2016
 * Time: 10:32:56 AM
 *
 * Project: csci205FinalProject
 * Package: guiComponents
 * File: Animation
 * Description:
 *
 * ****************************************
 */
package guiComponents;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Eric
 */
public abstract class Animation {

    protected float durationMS = 1000f;
    protected float currMS = 0f;

    private boolean animating = false;

    public Animation(int durationMS) {
        this.durationMS = durationMS;
    }

    public abstract void render(GUIContext container, Graphics g);

    public abstract void update(float delta);

    public void start() {
        animating = true;
    }

    public boolean running() {
        return animating;
    }

}
