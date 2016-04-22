/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 21, 2016
 * Time: 4:16:26 PM
 *
 * Project: csci205FinalProject
 * Package: TrainerCreator
 * File: TeamCreatorUtility
 * Description:
 *
 * ****************************************
 */
package TrainerCreator;

import PokemonObjects.Pokemon;

/**
 *
 * @author Benjamin Matase
 */
public class TeamCreatorUtility {
    public static Pokemon getPokemonGUI() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //create view
                PokemonChooserFrame pkmnChsrFrame = new PokemonChooserFrame();

                //make view visible
                pkmnChsrFrame.setVisible(true);
            }
        });
        return null;
    }

    public static void main(String[] args) {
        getPokemonGUI();
    }
}
