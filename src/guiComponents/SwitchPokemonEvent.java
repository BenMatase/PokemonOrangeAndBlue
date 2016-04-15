/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 15, 2016
 * Time: 10:30:12 AM
 *
 * Project: csci205FinalProject
 * Package: guiComponents
 * File: SwitchPokemonEvent
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
public class SwitchPokemonEvent extends Animation {

    protected Image img2;
    private int dims = 40;
    private boolean disappeared;

    public SwitchPokemonEvent(Image in, Image out, float durationS) {
        super(in, durationS);
        img2 = out;
    }

    @Override
    public void render(GUIContext container, Graphics g) {

    }

    @Override
    public void update(float delta) {
        if (disappeared) {

        } else {
            if () {

            }
        }
    }

}
