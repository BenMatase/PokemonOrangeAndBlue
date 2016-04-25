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
 * Want to show jFrame. When Create or Cancel is clicked, then this method will
 * extract information and return a Pokemon
 *
 * @author Benjamin Matase
 */
public class TeamCreatorUtility {
    public static Pokemon getPokemonGUI() {
        PokemonCreatorPanel pkmnPnl = new PokemonCreatorPanel();
        JOptionPane.showMessageDialog(null, pkmnPnl, "Create your Pokemon",
                                      JOptionPane.INFORMATION_MESSAGE);
        return createPokemon(pkmnPnl);
    }

    private static Pokemon createPokemon(PokemonCreatorPanel pkmnPnl) {
        if (pkmnPnl.hasChosenPkmn() && pkmnPnl.hasChosenAMove()) {
            String pkmnName = pkmnPnl.getPkmnName();
            List<String> moves = pkmnPnl.getMovesList();
            return PokemonLoaderUtility.createPokemon(pkmnName, pkmnName, moves);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        getPokemonGUI();
    }
}
