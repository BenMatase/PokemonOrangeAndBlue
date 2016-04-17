/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiComponents;

import java.awt.Font;
import java.io.InputStream;
import org.apache.commons.lang3.text.WordUtils;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Eric
 */
public class FontManager {

    public static TrueTypeFont stdPxFont;

    public FontManager() throws SlickException {
        throw new SlickException("Can't instantiate FontManager Object");
    }

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

    public static TrueTypeFont getStdPixelFont() throws SlickException {
        if (stdPxFont == null) {
            stdPxFont = getPixelFontWithSize(32f);
        }
        return stdPxFont;
    }

    public static String[] wrapString(String s, float pxWidth) throws SlickException {
        return WordUtils.wrap(s, (int) (pxWidth / getStdPixelFont().getWidth("W"))).split("\n");
    }
}
