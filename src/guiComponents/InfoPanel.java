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
    private static final int PAD = 3;
    // Health Bar
    private int HPH = 5;
    private int HPX;
    private int HPY;
    private int HPW;
    // Image
    private int IX;
    private int IY;
    private int IW;
    private int IH;

    // Frame step size when updating
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
            font = FontUtil.getPixelFontWithSize(24f);
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
        g.fillRect(HPX - PAD, HPY, HPW + 2 * PAD, HPH);
        g.fillRect(HPX, HPY - PAD, HPW, HPH + 2 * PAD);

        // Draw HP level string
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString((int) displayHP + "/" + maxHP, (int) (HPX - font.getWidth((int) displayHP + "/" + maxHP) - PAD * 2), (int) HPY - 8);
        // Draw the name
        if (img != null) {
            // Draw image if needed
            g.drawImage(img, IX, IY, IX + IW, IY + IH, 0, 0, img.getWidth(), img.getHeight());

            g.drawString(text[0], (int) (drawArea.getX() + IW + 2 * PAD), (int) (drawArea.getY() + PAD * 2));
        } else {
            g.drawString(text[0], (int) (drawArea.getX() + PAD * 2), (int) (drawArea.getY() + PAD * 2));
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
        g.fillRect(HPX, HPY, maxHP != 0 ? HPW * displayHP / maxHP : 0, HPH);

    }

    /**
     * Updates the currently drawn HP value, incrementing by 'delta' MS
     *
     * @param delta The number of milliseconds since the last update
     */
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

    //================
    // Mark: - Getters
    //================
    public int getHP() {
        return curHP;
    }

    /**
     * Gets a copy of the InfoPanel, optionally with the image it has.
     *
     * @param withImage Whether or not to include an image
     * @return A copy of the InfoPanel
     */
    public InfoPanel getCopy(boolean withImage) {

        if (img != null && withImage) {
            InfoPanel pnl = new InfoPanel(curHP, maxHP, getText(), img);
            return pnl;
        }
        InfoPanel pnl = new InfoPanel(curHP, maxHP, getText());
        return pnl;
    }

    //================
    // Mark: - Helpers
    //================
    /**
     * Recalculates the constants for drawing the InfoPanel, used when the panel
     * is moved or resized. This should greatly reduce the calculations required
     * for rendering the image at runtime.
     */
    private void recalculate() {
        HPX = (int) (drawArea.getX() + drawArea.getWidth() / 2 - 5);
        HPY = (int) (drawArea.getY() + drawArea.getHeight() - HPH * 3);
        HPW = (int) (drawArea.getWidth() / 2 - 2 * PAD);
        if (img != null) {
            float imgDrawDim = (drawArea.getHeight() - PAD * 2);
            if (img.getWidth() <= img.getHeight()) {
                float imgScale = (imgDrawDim / img.getHeight());
                IW = (int) (img.getWidth() * imgScale);
                IH = (int) (img.getHeight() * imgScale);
                IX = (int) (drawArea.getX() + PAD + (imgDrawDim / 2) - IW / 2);
                IY = (int) (drawArea.getY() + PAD);

            } else {
                float imgScale = (imgDrawDim / img.getWidth());
                IW = (int) (img.getWidth() * imgScale);
                IH = (int) (img.getHeight() * imgScale);
                IX = (int) (drawArea.getX() + PAD);
                IY = (int) (drawArea.getY() + PAD + (imgDrawDim / 2 - IH / 2));
            }
        }
    }

}
