/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 8, 2016
 * Time: 9:25:58 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: Events
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

/**
 *
 * @author Murph
 */
public abstract class Events {
}

class TextOutputEvent extends Events {

    String message;

    public TextOutputEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class UpdateHealthBarEvent extends Events {

}

class AnimationEvent extends Events {

}
