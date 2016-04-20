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
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * A subclass of button that displays the name, current health, maximum health,
 * and optionally an image of the Pokemon. InfoPanels are not enabled by default
 *
 * @author Eric
 */
public class InfoPanel extends MenuButton {

    // Drawing data
    private Image img;

    // HP Levels
    private int maxHP;
    private int displayHP;
    private int curHP;

    // Draw location values
    private int HPH = 5;
    private int HPB = 3;
    private int HPX;
    private int HPY;
    private int HPW;
    private float hpStep = 0;

    // Drawing constants
    private int X_PADDING = 5;
    private int Y_PADDING = 5;

    //=====================
    // Mark: - Constructors
    //=====================
    /**
     * Constructor for an info panel with only the name and health bar in it.
     * This buttons size must be manually set, or managed by a
     * MenuLayoutManager.
     *
     * @param curHP The current HP of the Pokemon
     * @param maxHP The maximum HP of the Pokemon
     * @param name The name of the Pokemon
     */
    public InfoPanel(int curHP, int maxHP, String name) {
        this(curHP, maxHP, name, null);
    }

    /**
     * Constructor for an info panel with a name, image, and health bar in it.
     * This buttons size must be manually set, or managed by a
     * MenuLayoutManager.
     *
     * @param curHP The current HP of the Pokemon
     * @param maxHP The maximum HP of the Pokemon
     * @param name The name of the Pokemon
     * @param image The image for the Pokemon
     */
    public InfoPanel(int curHP, int maxHP, String name, Image image) {
        this(0, 0, curHP, maxHP, name, image);
    }

    /**
     * Constructor for an info panel with a name, image, and health bar in it.
     * The button will be created at (x, y) in the given context
     *
     * @param x The x location of the info panel in pixels
     * @param y The y location of the info panel in pixels
     * @param curHP The current HP of the Pokemon
     * @param maxHP The maximum HP of the Pokemon
     * @param name The name of the Pokemon
     * @param image The image for the Pokemon
     */
    public InfoPanel(float x, float y, int curHP, int maxHP, String name, Image image) {
        super(name);
        this.text = new String[]{name};
        this.img = image;
        this.maxHP = maxHP;
        this.displayHP = curHP;
        this.curHP = curHP;
        this.enabled = false;
        this.isHighlighted = false;

        drawArea = new RoundedRectangle(x, y, 200, 60, 5);

        recalculate();

        try {
            font = FontUtils.getPixelFontWithSize(24f);
        } catch (SlickException ex) {
        }
    }

    //==================
    // Mark: - Rendering
    //==================
    @Override
    public void render(GameContainer container, Graphics g) {
        g.setColor(Color.gray);
        if (isHighlighted && enabled) {
            g.setColor(highlightColor);
        }
        g.fillRoundRect(drawArea.getX() - 2, drawArea.getY() - 2, drawArea.getWidth() + 4, drawArea.getHeight() + 4, 8);
        // Draw background
        g.setColor(new Color(244, 244, 244));
        g.fill(drawArea);
        g.setColor(Color.black);

        // Draw the Health bar outline
        g.fillRect(HPX - HPB, HPY, HPW + 2 * HPB, HPH);
        g.fillRect(HPX, HPY - HPB, HPW, HPH + 2 * HPB);

        // Draw HP level string
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString((int) displayHP + "/" + maxHP, (int) (HPX - font.getWidth((int) displayHP + "/" + maxHP) - HPB * 2), (int) HPY - 8);
        // Draw the name
        if (img != null) {
            g.drawImage(img, drawArea.getX() + HPB, drawArea.getY() + HPB,
                        drawArea.getX() + drawArea.getHeight() - 2 * HPB, drawArea.getY() + drawArea.getHeight() - 2 * HPB,
                        0, 0, img.getWidth(), img.getHeight());
            g.drawString(text[0], (int) (drawArea.getX() + drawArea.getHeight()), (int) (drawArea.getY() + HPB * 2));
        } else {
            g.drawString(text[0], (int) (drawArea.getX() + HPB * 2), (int) (drawArea.getY() + HPB * 2));
        }

        // Fill the health bar background
        g.setColor(Color.lightGray);
        g.fillRect(HPX, HPY, HPW, HPH);

        // Fill the health bar
        if (displayHP * 1.0 / maxHP > 0.6) {
            g.setColor(Color.green);
        } else if (displayHP * 1.0 / maxHP < 0.2) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.yellow);
        }
        g.fillRect(HPX, HPY, HPW * displayHP / maxHP, HPH);

    }

    public void update(float delta) {
        if (((int) displayHP > curHP && hpStep < 0) || ((int) displayHP < curHP && hpStep > 0)) {
            displayHP += hpStep * delta;
        } else {
            displayHP = curHP;
        }
    }

    //================
    // Mark: - Setters
    //================
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

    /**
     * Sets the HP displayed on the health bar
     *
     * @param hp The new HP value
     */
    public void setHP(int hp) {
        this.hpStep = (hp - curHP) / 2000f;
        this.curHP = hp;
    }

    public InfoPanel getCopy(boolean withImage) {

        if (img != null && withImage) {
            return new InfoPanel(curHP, maxHP, getText(), img);
        }
        return new InfoPanel(curHP, maxHP, getText());
    }

    /**
     * Recalculates the constants for drawing the InfoPanel, used when the panel
     * is moved or resized
     */
    private void recalculate() {
        HPX = (int) (drawArea.getX() + drawArea.getWidth() / 2 - 5);
        HPY = (int) (drawArea.getY() + drawArea.getHeight() - HPH * 3);
        HPW = (int) (drawArea.getWidth() / 2 - 2 * HPB);
    }

}
