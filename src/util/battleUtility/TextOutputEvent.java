/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 5:07:05 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: TextOutputEvent
 * Description: An event to be passed the GUI.  Updates things to show a
 * text animation
 *
 * ****************************************
 */
package util.battleUtility;

/**
 * Abstraction for the event of text being displayed on the screen.
 *
 * @author Murph
 */
public class TextOutputEvent extends Event {

    String message;

    /**
     * Constructs the message by taking in a string.
     *
     * @param message
     */
    public TextOutputEvent(String message) {
        this.message = message;
    }

    /**
     * Gets the string message from the event.
     *
     * @return
     */
    public String getMessage() {
        return message;
    }
}
