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
 * Want to show jFrame. When Create or Cancel is clicked, then this method will
 * extract information and return a Pokemon
 *
 * @author Benjamin Matase
 */
public class TeamCreatorUtility {
    public static Pokemon getPokemonGUI() {
        PokemonChooserFrame pkmnChsrFrame = new PokemonChooserFrame();
        pkmnChsrFrame.setVisible(true);
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                //make view visible
//            }
//        });

        return null;
    }

    public static void main(String[] args) {
        getPokemonGUI();
    }
}
