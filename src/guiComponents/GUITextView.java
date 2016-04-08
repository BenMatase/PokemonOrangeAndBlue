/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 8, 2016
 * Time: 2:46:42 PM
 *
 * Project: csci205FinalProject
 * Package: guiComponents
 * File: GUITextView
 * Description:
 *
 * ****************************************
 */
package guiComponents;

import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Eric
 */
public class GUITextView {

    private RoundedRectangle drawArea;
    public String[] text = new String[0];
    private int numStrings;
    private TrueTypeFont font;

    // Drawing constants
    private static final float X_PADDING = 5;
    private static final float Y_PADDING = 5;

    public GUITextView(RoundedRectangle rect, String text) throws SlickException {
        drawArea = rect;
        font = FontManager.getStdPixelFont();
        setText(text);
    }

    public void render(GUIContext container, Graphics g) {
        g.setColor(Color.white);
        g.fill(drawArea);
        int y = 0;
        for (int i = 0; i < text.length; i++) {
            font.drawString(X_PADDING, drawArea.getY() + Y_PADDING * (i + 1) + y, text[i], Color.black);
            y += font.getHeight(text[i]);
        }
    }

    private void setText(String string) throws SlickException {
        ArrayList<String> textStrings = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                end = i;
            }
            if (font.getWidth(string.substring(start, i)) > drawArea.getWidth() - 2 * Y_PADDING) {
                textStrings.add("'" + string.substring(start, end) + "'");
                start = 0 + end;
            }
        }
        if (start < end) {
            textStrings.add("'" + string.substring(start, end) + "'");
        }
        System.out.println(Arrays.toString(textStrings.toArray()));
    }
}
