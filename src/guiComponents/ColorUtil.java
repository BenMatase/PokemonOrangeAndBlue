/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 23, 2016
 * Time: 5:40:51 PM
 *
 * Project: csci205FinalProject
 * Package: guiComponents
 * File: ColorUtil
 * Description:
 *
 * ****************************************
 */
package guiComponents;

import org.newdawn.slick.Color;

/**
 *
 * @author Eric
 */
public class ColorUtil {

    // Singleton instances
    private static Color orange;
    private static Color blue;

    private ColorUtil() {
    }

    /**
     * Gets the singleton Bucknell Orange
     *
     * @return An orange color
     */
    public static Color getOrange() {
        if (orange == null) {
            orange = new Color(247, 112, 39);
        }
        return orange;
    }

    /**
     * Gets the singleton Bucknell Blue
     *
     * @return A blue color
     */
    public static Color getBlue() {
        if (blue == null) {
            blue = new Color(11, 84, 149);
        }
        return blue;
    }

}
