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
 * File: HPBar
 * Description:
 *
 * ****************************************
 */
package guiComponents;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Eric
 */
public class InfoPanel extends MenuButton {

    // Drawing data
    private Image img;

    // HP Levels
    private int maxHP;
    private int curHP;
    private int HPH = 5;
    private int HPB = 3;
    private int HPX;
    private int HPY;
    private int HPW;

    // Drawing constants
    private int X_PADDING = 5;
    private int Y_PADDING = 5;

    public InfoPanel(int curHP, int maxHP, String s) {
        this(0, 0, curHP, maxHP, s);
    }

    public InfoPanel(float x, float y, int curHP, int maxHP, String name) {
        this(x, y, curHP, maxHP, name, null);
    }

    public InfoPanel(float x, float y, int curHP, int maxHP, String name, Image image) {
        super(name);
        this.text = new String[]{name};
        this.img = image;
        this.maxHP = maxHP;
        this.curHP = curHP;
        this.enabled = false;
        this.isHighlighted = false;

        drawArea = new RoundedRectangle(x, y, 200, 60, 5);

        recalculate();

        try {
            font = FontManager.getPixelFontWithSize(24f);
        } catch (SlickException ex) {
        }
    }

    @Override
    public void render(GUIContext container, Graphics g) {
        // Draw background
        g.setColor(new Color(244, 244, 244));
        g.fill(drawArea);
        g.setColor(Color.black);
        g.draw(drawArea);

        // Draw the Health bar outline
        g.fillRect(HPX - HPB, HPY, HPW + 2 * HPB, HPH);
        g.fillRect(HPX, HPY - HPB, HPW, HPH + 2 * HPB);

        // Draw HP level string
        g.setFont(font);
        g.setColor(Color.darkGray);
        g.drawString(curHP + "/" + maxHP, HPX - font.getWidth(curHP + "/" + maxHP) - HPB * 2, HPY - 8);
        // Draw the name
        if (img != null) {
            g.drawImage(img, drawArea.getX() + HPB, drawArea.getY() + HPB,
                        drawArea.getX() + drawArea.getHeight() - 2 * HPB, drawArea.getY() + drawArea.getHeight() - 2 * HPB,
                        0, 0, img.getWidth(), img.getHeight());
            g.drawString(text[0], drawArea.getX() + drawArea.getHeight(), drawArea.getY() + HPB * 2);
        } else {
            g.drawString(text[0], drawArea.getX() + HPB * 2, drawArea.getY() + HPB * 2);
        }

        // Fill the health bar background
        g.setColor(Color.lightGray);
        g.fillRect(HPX, HPY, HPW, HPH);

        // Fill the health bar
        if (curHP * 1.0 / maxHP > 0.6) {
            g.setColor(Color.green);
        } else if (curHP * 1.0 / maxHP < 0.2) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.yellow);
        }
        g.fillRect(HPX, HPY, HPW * curHP / maxHP, HPH);

        if (isHighlighted && enabled) {
            g.setColor(highlightColor);
            g.draw(drawArea);
        }
    }

    @Override
    public void setSize(float width, float height) {
        drawArea.setWidth(width);
        drawArea.setCornerRadius(5);
        drawArea.prune();
        recalculate();
    }

    @Override
    public void setPosition(float x, float y) {
        drawArea.setX(x);
        drawArea.setY(y);
        drawArea.prune();
        recalculate();
    }

    public void setHP(int hp) {
        this.curHP = hp;
    }

    private void recalculate() {
        HPX = (int) (drawArea.getX() + drawArea.getWidth() / 2 - 5);
        HPY = (int) (drawArea.getY() + drawArea.getHeight() - HPH * 3);
        HPW = (int) (drawArea.getWidth() / 2 - 2 * HPB);
    }

}
