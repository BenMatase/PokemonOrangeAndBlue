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

import DatabaseLoaderUtilities.PokemonLoaderUtility;
import PokemonObjects.Pokemon;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Creates a JOptionPane with custom panel to get user's choice for Pokemon and
 * that Pokemon's moves.
 *
 * @author Benjamin Matase
 */
public class TeamCreatorUtility {
    /**
     * Gets a Pokemon by bringing up a JOptionPane with multiple combo boxes and
     * having the user choose a Pokemon and between one and four moves.
     *
     * @return The Pokemon the user chose.
     *
     * @author Benjamin Matase
     */
    public static Pokemon getPokemonGUI() {
        //create custom panel
        PokemonCreatorPanel pkmnPnl = new PokemonCreatorPanel();

        //make MessageDialog with custom panel as the panel of the dialog
        JOptionPane.showMessageDialog(null, pkmnPnl, "Create your Pokemon",
                                      JOptionPane.INFORMATION_MESSAGE);

        //create the Pokemon from the selected fields and return it
        return createPokemon(pkmnPnl);
    }

    /**
     * Create a Pokemon from the information in the Pokemon creator custom
     * panel.
     *
     * @param pkmnPnl The panel containing all of the user's choices in the
     * combo boxes
     * @return The Pokemon object specified by the information in the combo
     * boxes. If a Pokemon can't be created, then returns null.
     *
     * @author Benjamin Matase
     */
    private static Pokemon createPokemon(PokemonCreatorPanel pkmnPnl) {
        //check whether can actually create Pokemon from
        if (pkmnPnl.hasChosenPkmn() && pkmnPnl.hasChosenAMove()) {
            //extract information
            String pkmnName = pkmnPnl.getPkmnName();
            List<String> moves = pkmnPnl.getMovesList();

            //create and return Pokemon
            return PokemonLoaderUtility.createPokemon(pkmnName, pkmnName, moves);
        } else {
            return null;
        }
    }
}
