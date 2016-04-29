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
package util;

import org.newdawn.slick.Color;

/**
 * A utility class for getting default colors. Follows the singleton pattern
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
     * @author Eric
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
     * @author Eric
     * @return A blue color
     */
    public static Color getBlue() {
        if (blue == null) {
            blue = new Color(11, 84, 149);
        }
        return blue;
    }

}
