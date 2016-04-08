
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 7, 2016
 * Time: 10:40:44 PM
 *
 * Project: csci205FinalProject
 * Package:
 * File: PokemonLoaderUtility
 * Description:
 *
 * ****************************************
 */
/**
 * https://github.com/r4vi/zipper-demo/blob/master/resources/pokemon.xml
 *
 * @author Benjamin Matase
 */
public class PokemonLoaderUtility {
    private static Element rootNode;

    /**
     * http://www.mkyong.com/java/how-to-read-xml-file-in-java-jdom-example/
     */
    private static void loadTree() {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("./data/pokemon.xml");

        try {
            Document document = (Document) builder.build(xmlFile);
            rootNode = document.getRootElement();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }

    public static List<String> getPokemonNames() {
        if (rootNode == null) {
            loadTree();
        }

        List<Element> pokemonNodes = rootNode.getChildren("pokemon");
        List<String> pokemonNames = new ArrayList<String>();

        for (Element node : pokemonNodes) {
            pokemonNames.add(node.getText());
        }
        return pokemonNames;

    }

    public static List<String> getMovesForPokemon(String pokemonName) {
        if (rootNode == null) {
            loadTree();
        }

        List<String> movesStr = new ArrayList<String>();

        Element pokemon = getPokemonNode(pokemonName);

        Element movesNode = pokemon.getChild("moves");
        //gets all moves Pokemon can learn
        List<Element> movesNodes = movesNode.getChildren();

        //populates string list
        for (Element moveNode : movesNodes) {
            movesStr.add(moveNode.getText());
        }

        return movesStr;
    }

    private static Element getPokemonNode(String pokemonName) {
        List<Element> pokemonNodes = rootNode.getChildren("pokemon");
        for (Element pokemon : pokemonNodes) {
            if (pokemon.getText() == pokemonName) {
                return pokemon;
            }
        }
        return null;
    }

    public static void fillPokemonAttr(Pokemon pokemon) {
        //TODO: fix this to fit with Jason's public interface for Pokemon
        String pokemonName = pokemon.getName();

        Element pokemon = getPokemonNode(pokemonName);

        //use that node to fill in stats, type, national dex num, etc
    }
}
