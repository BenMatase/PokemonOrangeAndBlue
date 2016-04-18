/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiComponents;

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
public class MenuButton {

    // Data for rendering
    protected String[] text;
    protected RoundedRectangle drawArea;
    protected TrueTypeFont font;
    protected boolean isHighlighted = false;
    protected Color highlightColor;
    protected boolean enabled = true;

    public MenuButton(String text) {
        this(text, Color.red);
    }

    public MenuButton(String text, Color highlightColor) {
        this(0, 0, 0, 0, text, highlightColor);
    }

    public MenuButton(float x, float y, float width, float height, String text, Color highlightColor) {
        this.highlightColor = highlightColor;
        drawArea = new RoundedRectangle(x, y, width, height, 5);
        try {
            font = FontManager.getStdPixelFont();
        } catch (SlickException ex) {
        }
        this.text = new String[]{text};
        updateWrapSize();
    }

    public void render(GUIContext container, Graphics g) {
        if (isHighlighted && enabled) {
            g.setColor(highlightColor);
            g.draw(drawArea);
        }

        g.setFont(font);
        g.setColor(Color.black);
        int i = 0;
        for (String s : text) {
            g.drawString(s, centerStringX(s), stringLocY(s, i));
            i++;
        }

    }

    public void setSize(float width, float height) {
        drawArea.setWidth(width);
        drawArea.setHeight(height);
        drawArea.setCornerRadius(5);
        updateWrapSize();
        drawArea.prune();
    }

    public void setPosition(float x, float y) {
        drawArea.setX(x);
        drawArea.setY(y);
        drawArea.prune();
    }

    public float getX() {
        return drawArea.getWidth();
    }

    public float getY() {
        return drawArea.getY();
    }

    public float getWidth() {
        return drawArea.getWidth();
    }

    public float getHeight() {
        return drawArea.getHeight();
    }

    public boolean getHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public float[] getCenter() {
        return drawArea.getCenter();
    }

    public String getText() {
        return String.join(" ", text);
    }

    public void setText(String text) {
        this.text = new String[]{text};
        updateWrapSize();
    }

    public boolean contains(float x, float y) {
        return drawArea.contains(x, y);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void updateWrapSize() {
        try {
            this.text = FontManager.wrapString(String.join(" ", text), this.drawArea.getWidth() - 10);
        } catch (Exception ex) {
        }
    }

    //================
    // Mark: - Helpers
    //================
    private float centerStringX(String s) {
        return drawArea.getX() + drawArea.getWidth() / 2 - font.getWidth(s) / 2;
    }

    private float stringLocY(String s, int num) {
        return (int) (drawArea.getY() + (drawArea.getHeight() * (num + 1) * 1.0f / (text.length + 1)) - (font.getHeight(s) / 2));
    }

}
