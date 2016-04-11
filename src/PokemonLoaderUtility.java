
import PokemonObjects.Pokemon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import pokemonObjects.Move;

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
 * https://github.com/cathyjf/PokemonLabBot/blob/master/moves.xml
 *
 * @author Benjamin Matase
 */
public class PokemonLoaderUtility {
    private static final String POKEMON_FILE_PATH = "./res/Database/pokemon.xml";
    private static final String MOVES_FILE_PATH = "./res/Database/moves.xml";

    private static Element pokemonNode;
    private static Element moveNode;

    /**
     * http://www.mkyong.com/java/how-to-read-xml-file-in-java-jdom-example/
     */
    private static void loadPokemonNode() {
        pokemonNode = loadNode(POKEMON_FILE_PATH);
    }

    private static void loadMoveNode() {
        moveNode = loadNode(MOVES_FILE_PATH);
    }

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

    public static List<String> getPokemonNames() {
        if (pokemonNode == null) {
            loadPokemonNode();
        }

        List<Element> pokemonNodes = pokemonNode.getChildren("pokemon");
        List<String> pokemonNames = new ArrayList<String>();

        for (Element node : pokemonNodes) {
            pokemonNames.add(node.getText());
        }
        return pokemonNames;

    }

    public static List<String> getMovesForPokemon(String pokemonName) {
        if (pokemonNode == null) {
            loadPokemonNode();
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

    public static Move fillMoveAttr(Move move) {
        String moveName = move.getName();

        Element moveNode = getMoveNode(moveName);

        //TODO: add type to move
        move.setType(moveNode.getChildText("type"));

        //TODO: set power
        //TODO: set accuracy
    }

    private static Element getMoveNode(String moveName) {
        if (moveNode == null) {
            loadMoveNode();
        }

        List<Element> moveNodes = moveNode.getChildren("move");
        for (Element moveNode : moveNodes) {
            if (moveNode.getText() == moveName) {
                return moveNode;
            }
        }
        return null;
    }

    private static Element getPokemonNode(String pokemonName) {
        if (pokemonNode == null) {
            loadPokemonNode();
        }

        List<Element> pokemonNodes = pokemonNode.getChildren("pokemon");
        for (Element pokemonNode : pokemonNodes) {
            if (pokemonNode.getText() == pokemonName) {
                return pokemonNode;
            }
        }
        return null;
    }

    public static void fillPokemonAttr(Pokemon pokemon) {
        //TODO: fix this to fit with Jason's public interface for Pokemon
        String pokemonName = pokemon.getName();

        Element pokemonNode = getPokemonNode(pokemonName);

        //TODO: add types
        //fill Pokemon nation dex number
        int natDexNum = Integer.parseInt(
                pokemonNode.getAttribute("id").getValue());

        //fill in Pokemon's stats
        Element stats = pokemonNode.getChild("stats");

        pokemon.setHealth(Integer.parseInt(stats.getChildText("HP")));
        pokemon.setAttack(Integer.parseInt(stats.getChildText("ATK")));
        pokemon.setDefense(Integer.parseInt(stats.getChildText("DEF")));
        pokemon.setSpeed(Integer.parseInt(stats.getChildText("SPD")));
        pokemon.setSpcAttack(Integer.parseInt(stats.getChildText("SAT")));
        pokemon.setSpcDefense(Integer.parseInt(stats.getChildText("SDF")));

    }
}
