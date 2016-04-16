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
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Eric
 */
public abstract class Animation {

    protected Image img;
    protected float durationS;
    protected float currMS;

    private boolean animating = false;

    public Animation(Image img, float durationS) {
        this.img = img;

        this.durationS = durationS;
    }

    public abstract void render(GUIContext container, Graphics g);

    public abstract void update(float delta);

    public void start() {
        animating = true;
    }

    public void stop() {
        animating = false;
        currMS = 0;
    }

}
