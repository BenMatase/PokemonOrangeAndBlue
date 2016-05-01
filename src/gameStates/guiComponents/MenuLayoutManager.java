/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates.guiComponents;

import java.lang.reflect.Array;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * A class for performing layout and drawing of menus. Handles correct
 * highlighting and display of the items within it
 *
 * @author Eric
 * @param <T> The type of item that is held by the manager. Must extend
 * MenuButton
 */
public class MenuLayoutManager<T extends MenuButton> {

    // Drawing Variables
    private RoundedRectangle drawArea;
    private float[] buttonRectSize;
    private boolean drawBackground;
    private boolean enabled = true;
    private Color bgdColor = Color.white;

    // Constants for drawing
    private static final float PADDING = 8;

    // Item Holders and tracking
    private T[][] itemMatrix;
    private T[] itemArray;
    private int[] selected = new int[]{-1, -1};
    private int size = 0;

    // Type of item to hold
    private Class<T> type;

    //====================
    // Mark - Constructors
    //====================
    /**
     * Constructor for an empty MenuLayoutManager. Automatically draws the
     * background
     *
     * @author Eric
     * @param drawArea The Rounded Rectangle to draw the Manager's items in
     * @param x The number of slots to put items in horizontally
     * @param y The number of slots to put items in vertically
     * @param empty The class that will be put into the MenuLayoutManager
     */
    public MenuLayoutManager(RoundedRectangle drawArea, int x, int y, Class<T> empty) {
        this(drawArea, x, y, true, empty);
    }

    /**
     * Constructor for an empty MenuLayoutManager. Gives the option to not draw
     * the background
     *
     * @author Eric
     * @param drawArea The Rounded Rectangle to draw the Manager's items in
     * @param x The number of slots to put items in horizontally
     * @param y The number of slots to put items in vertically
     * @param drawBackground Whether or not the background should be drawn
     * @param empty The class that will be put into the MenuLayoutManager
     */
    public MenuLayoutManager(RoundedRectangle drawArea, int x, int y, boolean drawBackground, Class<T> empty) {
        this.type = empty;
        itemMatrix = (T[][]) Array.newInstance(empty, x, y);
        this.drawArea = drawArea;
        this.drawBackground = drawBackground;
        calculateButtonRectSize();
    }

    //=================
    // Mark - Rendering
    //=================
    /**
     * Draw the MenuLayoutManager
     *
     * @author Eric
     * @param container The container to do the drawing in
     * @param g The Graphics context used for drawing
     */
    public void render(GameContainer container, Graphics g) {
        if (drawBackground) {
            g.setColor(bgdColor);
            g.fill(drawArea);
        }
        if (itemArray != null) {
            for (T b : itemArray) {
                if (selected[0] != -1 && selected[1] != -1 && itemMatrix[selected[0]][selected[1]] == b && enabled) {
                    b.setHighlighted(enabled);
                } else {
                    b.setHighlighted(false);
                }
                b.render(container, g);
            }
        }
    }

    //===============
    // Mark - Getters
    //===============
    /**
     * Gets the Rounded Rectangle representing the area the MenuLayoutManager
     * covers
     *
     * @author Eric
     * @return The Rounded Rectangle draw area of the MenuLayoutManager
     */
    public RoundedRectangle getDrawArea() {
        return drawArea;
    }

    /**
     * Gets the item to the left of the currently selected one
     *
     * @author Eric
     * @return The item to the left
     */
    public T getLeft() {
        if (selected[0] != 0 && itemMatrix[selected[0] - 1][selected[1]] != null) {
            return itemMatrix[selected[0] - 1][selected[1]];
        } else {
            return itemMatrix[selected[0]][selected[1]];
        }
    }

    /**
     * Gets the item to the right of the currently selected one
     *
     * @author Eric
     * @return The item to the right
     */
    public T getRight() {
        if (selected[0] < itemMatrix.length - 1 && itemMatrix[selected[0] + 1][selected[1]] != null) {
            return itemMatrix[selected[0] + 1][selected[1]];
        } else {
            return itemMatrix[selected[0]][selected[1]];
        }
    }

    /**
     * Gets the item to the up of the currently selected one
     *
     * @author Eric
     * @return The item to the up
     */
    public T getUp() {
        if (selected[1] != 0 && itemMatrix[selected[0]][selected[1] - 1] != null) {
            return itemMatrix[selected[0]][selected[1] - 1];
        } else {
            return itemMatrix[selected[0]][selected[1]];
        }
    }

    /**
     * Gets the item to the down of the currently selected one
     *
     * @author Eric
     * @return The item to the down
     */
    public T getDown() {
        if (selected[1] < itemMatrix[0].length - 1 && itemMatrix[selected[0]][selected[1] + 1] != null) {
            return itemMatrix[selected[0]][selected[1] + 1];
        } else {
            return itemMatrix[selected[0]][selected[1]];
        }
    }

    /**
     * Gets the currently selected item
     *
     * @author Eric
     * @return The currently selected item
     */
    public T getSelected() {
        return getItem(selected[0], selected[1]);
    }

    /**
     * Gets the item at the given index in the matrix
     *
     * @author Eric
     * @param x The x-index
     * @param y The y-index
     * @return The item at the given index in the matrix, null if it's empty
     */
    public T getItem(int x, int y) {
        return itemMatrix[x][y] != null ? (T) itemMatrix[x][y] : null;
    }

    /**
     * Gets whether or not the MenuLayoutManager is showing the highlights on
     * the buttons
     *
     * @author Eric
     * @return True if the highlights are being shown, false otherwise
     */
    public boolean isShowingHighlight() {
        return enabled;
    }

    /**
     * Gets whether or not the background should be drawn
     *
     * @author Eric
     * @return True if the background should be drawn, false otherwise
     */
    public boolean getDrawBackground() {
        return drawBackground;
    }

    /**
     * Gets an array of the items in the MenuLayoutManager
     *
     * @author Eric
     * @return An array of all of the items in the MenuLayoutManager
     */
    public T[] getItems() {
        return itemArray;
    }

    //===============
    // Mark - Setters
    //===============
    /**
     * Inserts an item at the given index in the matrix
     *
     * @author Eric
     * @param x The x-index to insert at
     * @param y The y-index to insert at
     * @param item The item to insert
     */
    public void set(int x, int y, T item) {
        if (item != null) {
            item.setEnabled(enabled);
            item.setHighlighted(false);
            int[] coords = getItemDrawCoords(x, y);
            item.setPosition(coords[0], coords[1]);
            item.setSize(buttonRectSize[0], buttonRectSize[1]);
            item.setHighlighted(false);
            setInMatrix(x, y, item);
        } else {
            setInMatrix(x, y, null);
        }
    }

    /**
     * Set the selected item in the MenuLayoutManager
     *
     * @author Eric
     * @param item The item to set as selected
     */
    public void setSelected(T item) {
        if (item != null) {
            int[] tmp = find(item);
            if (tmp != null && itemMatrix[selected[0]][selected[1]] != item) {
                if (selected[0] != -1 && selected[1] != -1) {
                    itemMatrix[selected[0]][selected[1]].setHighlighted(enabled);
                }
                selected = tmp;
                itemMatrix[selected[0]][selected[1]].setHighlighted(enabled);
            }
        } else {
            selected[0] = -1;
            selected[1] = -1;

        }
    }

    /**
     * Set whether the buttons should show the highlights
     *
     * @author Eric
     * @param show True if the buttons should show highlights when hovered over
     */
    public void shouldShowHighlight(boolean show) {
        enabled = show;
    }

    /**
     * Sets the background color for the MenuLayoutManager
     *
     * @author Eric
     * @param newColor The new background color
     */
    public void setBackgroundColor(Color newColor) {
        bgdColor = newColor;
    }

    /**
     * Set whether the background should be drawn
     *
     * @author Eric
     * @param drawBackground Whether the background should be drawn
     */
    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    //===============
    // Mark - Helpers
    //===============
    /**
     * Recalculates the dimensions of the buttons
     *
     * @author Eric
     */
    private void calculateButtonRectSize() {
        int width = (int) ((drawArea.getWidth() - (itemMatrix.length + 1) * PADDING) / itemMatrix.length);
        int height = (int) ((drawArea.getHeight() - (itemMatrix[0].length + 1) * PADDING) / itemMatrix[0].length);
        buttonRectSize = new float[]{width, height};
    }

    /**
     * Helper for setting buttons into the matrix. This does all of the nasty
     * calculations and handles cases
     *
     * @author Eric
     * @param x The x-index to place at
     * @param y The y-index to place at
     * @param item The item to place
     */
    private void setInMatrix(int x, int y, T item) {
        // Different things for if the item is empty
        if (item != null) {
            // If nothing is selected, select the item
            if (selected[0] == -1 && selected[1] == -1) {
                item.setHighlighted(enabled);
                item.setEnabled(enabled);
                selected[0] = x;
                selected[1] = y;
            }
            if (itemMatrix[x][y] == null) {
                size += 1;
            }
            itemMatrix[x][y] = item;
        } else {
            // Only need to set if not already null
            if (itemMatrix[x][y] != null) {
                size -= 1;
                itemMatrix[x][y] = null;
                refreshArray();
                // If size is not 0 and the button was selected
                if (size > 0 && x == selected[0] && y == selected[1]) {
                    setSelected(itemArray[0]);
                } // If size is 0 and the button was selected
                else if (x == selected[0] && y == selected[1]) {
                    setSelected(null);
                }
            }
        }
        refreshArray();
    }

    /**
     * Gets the coordinates for the item at the given indices
     *
     * @author Eric
     * @param gridX The x-index in the matrix
     * @param gridY The y-index in the matrix
     * @return The x and y coordinates of the buttons to draw
     */
    private int[] getItemDrawCoords(int gridX, int gridY) {
        int x = (int) (drawArea.getX() + (PADDING * (gridX + 1)) + buttonRectSize[0] * gridX);
        int y = (int) (drawArea.getY() + (PADDING * (gridY + 1)) + buttonRectSize[1] * gridY);
        return new int[]{x, y};
    }

    /**
     * Finds a given item in the matrix and gets its indices
     *
     * @author Eric
     * @param b The item to find
     * @return The x and y coordinates of the buttons to draw
     */
    public int[] find(T b) {
        for (int x = 0; x < itemMatrix.length; x++) {
            for (int y = 0; y < itemMatrix[0].length; y++) {
                if (itemMatrix[x][y] == b) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

    /**
     * Disables all of the buttons in the matrix
     *
     * @author Eric
     */
    public void disable() {
        this.enabled = false;
        if (itemArray != null) {
            for (T b : itemArray) {
                b.setEnabled(enabled);
                b.setHighlighted(false);
            }
        }
        setSelected(null);
    }

    /**
     * Disables all of the buttons in the matrix
     *
     * @author Eric
     */
    public void enable() {
        this.enabled = true;
        if (itemArray != null) {
            for (T b : itemArray) {
                b.setEnabled(enabled);
                b.setHighlighted(false);
            }
            setSelected(itemArray[0]);
        }
    }

    /**
     * Refreshes the array of items in the MenuLayoutManager from the matrix
     *
     * @author Eric
     */
    private void refreshArray() {
        itemArray = (T[]) Array.newInstance(type, size);
        int i = 0;
        for (T[] row : itemMatrix) {
            for (T btn : row) {
                if (btn != null) {
                    itemArray[i] = btn;
                    i += 1;
                }
            }
        }
    }

    /**
     * Clears the MenuLayoutManager, resetting it to default
     *
     * @author Eric
     */
    public void clear() {
        selected[0] = -1;
        selected[1] = -1;
        itemMatrix = (T[][]) Array.newInstance(type, itemMatrix.length, itemMatrix[0].length);
        size = 0;
        refreshArray();

    }

}
