/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 21, 2016
 * Time: 12:28:13 AM
 *
 * Project: csci205FinalProject
 * Package: TrainerCreator
 * File: TrainerLoaderUtility
 * Description:
 *
 * ****************************************
 */
package DatabaseLoaderUtilities;

import PokemonObjects.EnemyTrainer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Benjamin Matase
 */
public class TrainerLoaderUtility {

    private static final String TRAINERS_FILE_PATH = "./res/Database/trainers.xml";

    private static Element trainerRootNode;

    public static EnemyTrainer loadProfessor(int profNum) {
        if (trainerRootNode == null) {
            loadTrainerRootNode();
        }

        //get all of the professor nodes
        List<Element> professors = trainerRootNode.getChildren("professors");

        //get professor node with specified profNum as id
        Element profNode = null;
        for (Element professor : professors) {
            if (professor.getAttributeValue("id").equals(String.valueOf(profNum))) {
                profNode = professor;
            }
        }

        //get information about professor
        String name = profNode.getChildText("name");
        String introText = profNode.getChildText("introText");
        String outroText = profNode.getChildText("outroText");

        //TODO: rename attributes to EnemyTrainer
        EnemyTrainer enemy = new EnemyTrainer(name, introText, outroText);

        //get all pokemon nodes
        List<Element> pkmnNodes = profNode.getChild("team").getChildren(
                "pokemon");

        //go through each pokemon and add to enemy
        for (Element pkmnNode : pkmnNodes) {
            //get species
            String species = pkmnNode.getChildText("species");

            //get specified moves and add strings to list
            List<String> moves = new ArrayList<>();
            List<Element> moveNodes = pkmnNode.getChild("moves").getChildren();

            for (Element moveNode : moveNodes) {
                moves.add(moveNode.getText());
            }

            //create and add pokemon using PokemonLoaderUtility
            enemy.addPokemon(
                    PokemonLoaderUtility.createPokemon(species, species,
                                                       moves));
        }

        return enemy;

    }

    private static void loadTrainerRootNode() {
        trainerRootNode = loadNode(TRAINERS_FILE_PATH);
    }

    /**
     * Makes an Element which is the root node of the xml file specified at the
     * given file path.
     *
     * Source of method:
     * <a href="http://www.mkyong.com/java/how-to-read-xml-file-in-java-jdom-example/">
     * jDom</a>
     *
     * @param filePath The file path to the xml to be read.
     * @return The root node element of the specified xml.
     *
     * @author Benjamin Matase
     */
    private static Element loadNode(String filePath) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(filePath);

        try {
            Document document = (Document) builder.build(xmlFile);
            return document.getRootElement();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }

        return null;
    }

}
