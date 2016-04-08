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
public class GUIButton {

    // Data for rendering
    private String text;
    private RoundedRectangle button;
    private TrueTypeFont font;
    private boolean isHighlighted = false;

    public GUIButton(String text) {
        this(1, 1, 1, 1, text);
    }

    public GUIButton(float x, float y, float width, float height, String text) {
        this.text = text;
        button = new RoundedRectangle(x, y, width, height, 5);
        try {
            font = FontManager.getStdPixelFont();
        } catch (SlickException ex) {
        }
    }

    public void render(GUIContext container, Graphics g) {
        if (isHighlighted) {
            g.setColor(Color.red);
            g.draw(button);
        }
        font.drawString(centerStringX(text), centerStringY(text), text, Color.black);
    }

    public void setSize(float width, float height) {
        button.setWidth(width);
        button.setHeight(height);
        button.setCornerRadius(5);
        button.prune();
    }

    public void setPosition(float x, float y) {
        button.setX(x);
        button.setY(y);
        button.prune();
    }

    public float getX() {
        return button.getWidth();
    }

    public float getY() {
        return button.getY();
    }

    public float getWidth() {
        return button.getWidth();
    }

    public float getHeight() {
        return button.getHeight();
    }

    public boolean getHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public float[] getCenter() {
        return button.getCenter();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean contains(float x, float y) {
        return button.contains(x, y);
    }

    //================
    // Mark: - Helpers
    //================
    private float centerStringX(String s) {
        return button.getX() + button.getWidth() / 2 - font.getWidth(text) / 2;
    }

    private float centerStringY(String s) {
        return button.getY() + button.getHeight() / 2 - font.getHeight(text) / 2;
    }

}
