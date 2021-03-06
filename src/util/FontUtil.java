/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 10, 2016
 * Time: 5:20:47 PM
 *
 * Project: csci205FinalProject
 * Package: guiComponents
 * File: FontUtil
 * Description:
 *
 * ****************************************
 */
package util;

import java.awt.Font;
import java.io.InputStream;
import org.apache.commons.lang3.text.WordUtils;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
 * A utility class for getting and working with fonts. Follows the singleton
 * pattern
 *
 * @author Eric
 */
public class FontUtil {

    public static TrueTypeFont stdPxFont;

    private FontUtil() {
    }

    /**
     * Get the pixel font with the given size as a TTF
     *
     * @author Eric
     * @param size The size of the font in pixels
     * @return A TrueTypeFont
     * @throws SlickException
     */
    public static TrueTypeFont getPixelFontWithSize(float size) throws SlickException {
        TrueTypeFont pixelFont = null;
        try {
            InputStream stream = ResourceLoader.getResourceAsStream("res/Fonts/PixelFont.ttf");
            Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, stream);
            tmpFont = tmpFont.deriveFont(size);
            pixelFont = new TrueTypeFont(tmpFont, false);
        } catch (Exception e) {
            System.out.println("Unable to load font");
        }
        return pixelFont;
    }

    /**
     * Gets the pixel font in the "standard" size, used for almost all writing
     * on the screen
     *
     * @author Eric
     * @return A TrueTypeFont
     * @throws SlickException
     */
    public static TrueTypeFont getStdPixelFont() throws SlickException {
        if (stdPxFont == null) {
            stdPxFont = getPixelFontWithSize(32f);
        }
        return stdPxFont;
    }

    /**
     * Puts a string into an array representing the wrapped lines such that the
     * string will fit into a given width with the standard font
     *
     * @author Eric
     * @param s The string to wrap
     * @param pxWidth The width of the area to wrap the string into
     * @return An array of strings representing the wrapped lines
     * @throws SlickException
     */
    public static String[] wrapString(String s, float pxWidth) throws SlickException {
        return WordUtils.wrap(s, (int) (pxWidth / getStdPixelFont().getWidth("W"))).split(System.getProperty("line.separator"));
    }
}
