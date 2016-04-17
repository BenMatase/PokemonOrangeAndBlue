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

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Eric
 */
public class ImageAnimation extends Animation {

    private int curXoffset = 0;
    private int curYoffset = 0;
    private int x0;
    private int y0;

    public ImageAnimation(int x0, int y0, int[] deltaXPaths, int[] deltaYPaths, int durationMS) {
        super(durationMS);
        assert deltaXPaths.length == deltaYPaths.length;
        int totXDist = 0;
        int totYDist = 0;
        for (int i = 0; i < deltaXPaths.length; i++) {
            totXDist += deltaXPaths[i] > 0 ? deltaXPaths[i] : -deltaXPaths[i];
            totYDist += deltaYPaths[i] > 0 ? deltaYPaths[i] : -deltaYPaths[i];
        }
        totXDist /= deltaXPaths.length;
        totYDist /= deltaYPaths.length;
    }

    @Override
    public void render(GUIContext container, Graphics g) {

    }

    @Override
    public void update(float delta) {
        currMS += delta;
    }

}
