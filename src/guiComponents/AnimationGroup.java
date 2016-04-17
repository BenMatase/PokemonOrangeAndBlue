/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 16, 2016
 * Time: 10:18:24 AM
 *
 * Project: csci205FinalProject
 * Package: guiComponents
 * File: AnimationGroup
 * Description:
 *
 * ****************************************
 */
package guiComponents;

import java.util.List;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Eric
 */
public class AnimationGroup {

    private List<Animation> anims;

    public AnimationGroup(List<Animation> anims) {
        this.anims = anims;
    }

    public void render(GUIContext container, Graphics g) {
        for (Animation a : anims) {
            a.render(container, g);
        }
    }

    public void update(float delta) {
        for (Animation a : anims) {
            if (a.running()) {
                a.update(delta);
            } else {
                anims.remove(a);
            }
        }
    }

    public void start() {
        for (Animation a : anims) {
            a.start();
        }
    }

}
