
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
package DatabaseLoaderUtilities;

import BattleUtility.PokemonType;
import PokemonObjects.Move;
import PokemonObjects.Move.AttackType;
import PokemonObjects.Pokemon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * A utility class that uses a Singleton design pattern to make sure pokemonNode
 * and moveNode are initialized when necessary. Reads information from two xml
 * files for moves and Pokemon so that the game can get accurate information.
 *
 * Source for XML's:
 * <a href="https://github.com/r4vi/zipper-demo/blob/master/resources/pokemon.xml">
 * pokemon.xml</a>
 * <a href="https://github.com/cathyjf/PokemonLabBot/blob/master/moves.xml">
 * moves.xml</a>
 *
 * TODO: account for errors in nomenclature of moves
 *
 * TODO: account for failure of setting either root Element
 *
 * @author Benjamin Matase
 */
public class PokemonLoaderUtility {
    private static final String POKEMON_FILE_PATH = "./res/Database/pokemon.xml";
    private static final String MOVES_FILE_PATH = "./res/Database/moves.xml";

    private static Element pokemonNode;
    private static Element moveNode;

    /**
     * Initializes pokemonNode to the root node of pokemon.xml file found at
     * POKEMON_FILE_PATH.
     *
     * @author Benjamin Matase
     */
    private static void loadPokemonNode() {
        pokemonNode = loadNode(POKEMON_FILE_PATH);
    }

    /**
     * Initializes moveNode to the root node of moves.xml file found at
     * MOVES_FILE_PATH.
     *
     * @author Benjamin Matase
     */
    private static void loadMoveNode() {
        moveNode = loadNode(MOVES_FILE_PATH);
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

    /**
     * Returns a list of all Pokemon species in the program's Pokemon database.
     *
     * @return A list of the names in pokemon.xml exactly as they appear in it.
     *
     * @author Benjamin Matase
     */
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

    /**
     * Gets a list of all moves that a Pokemon can learn according to
     * pokemon.xml.
     *
     * @param pokemonName The name of the Pokemon as it appears in pokemon.xml.
     * @return A list of all moves that the specified Pokemon can have.
     *
     * @author Benjamin Matase
     */
    public static List<String> getMovesForPokemon(String pokemonName) {
        if (pokemonNode == null) {
            loadPokemonNode();
        }

        List<String> movesStr = new ArrayList<String>();

        //get the node for the specified Pokemono
        Element pokemon = getPokemonNode(pokemonName);

        //gets all moves Pokemon can learn
        Element movesNode = pokemon.getChild("moves");
        List<Element> movesNodes = movesNode.getChildren();

        //populates string list
        for (Element moveNode : movesNodes) {
            movesStr.add(moveNode.getText());
        }

        return movesStr;
    }

    /**
     * Create Move object from name of move.
     *
     * @param moveName The name of the move as it appears in moves.xml.
     * @return A move populated with all necessary information from database.
     *
     * @author Benjamin Matase
     */
    public static Move createMove(String moveName) {
        //get node of specified move
        Element moveNode = getMoveNode(moveName);

        //get the type of the move from the xml and make into enum
        PokemonType type = PokemonType.valueOf(
                moveNode.getChildText("type").toUpperCase());

        //get the type of attack from the xml and make into enum
        AttackType attType = AttackType.valueOf(
                moveNode.getChildText("class").toUpperCase());

        //get the damage as an integer
        int damage = Integer.parseInt(moveNode.getChildText("power"));

        //get the accuracy as a float
        float accuracy = Float.parseFloat(moveNode.getChildText("accuracy"));

        //creates the move using the main constructor
        Move move = new Move(attType, type, damage, accuracy, moveName);

        return move;
    }

    /**
     * Goes through all the moves and finds the node that corresponds to the
     * moveName.
     *
     * TODO: possible refactor
     *
     * @param moveName The name of the move that is specified in the xml.
     * @return The node corresponding to the moveName.
     *
     * @author Benjamin Matase
     */
    private static Element getMoveNode(String moveName) {
        if (moveNode == null) {
            loadMoveNode();
        }

        List<Element> moveNodes = moveNode.getChildren("move");
        for (Element moveNode : moveNodes) {
            if (moveNode.getAttribute("name").getValue().equals(moveName)) {
                return moveNode;
            }
        }
        return null;
    }

    /**
     * Goes through all of the Pokemon and finds the node that corresponds to
     * the pokemonName.
     *
     * @param pokemonName The name (species) of the Pokemon as specified in the
     * xml.
     * @return The node corresponding to the pokemonNode.
     *
     * @author Benjamin Matase
     */
    private static Element getPokemonNode(String pokemonName) {
        if (pokemonNode == null) {
            loadPokemonNode();
        }

        List<Element> pokemonNodes = pokemonNode.getChildren("pokemon");
        for (Element pokemonNode : pokemonNodes) {
            if (pokemonNode.getChild("name").getText().equals(pokemonName)) {
                return pokemonNode;
            }
        }
        return null;
    }

    /**
     * Extracts all information about a certain Pokemon with a certain moveset
     * and then create a Pokemon object with specified unique attributes.
     *
     * TODO: limit moveNames to be more than 0 and less than 5
     *
     * @param pokemonName The name (species) of the Pokemon as it appears in
     * pokemon.xml.
     * @param nickname A nickname for the Pokemon.
     * @param moveNames The list of moves as strings to be the Pokemon's
     * moveset.
     * @return A Pokemon object with the unique attributes passed in but also
     * all of the more generic attributes filled from database.
     *
     * @author Benjamin Matase
     */
    public static Pokemon createPokemon(String pokemonName, String nickname,
                                        List<String> moveNames) {
        Element pokemonNode = getPokemonNode(pokemonName);

        //fill Pokemon nation dex number TODO: add this to Pokemon
        int natDexNum = Integer.parseInt(
                pokemonNode.getAttribute("id").getValue());

        //get Pokemon's stats node
        Element stats = pokemonNode.getChild("stats");

        //extract each of the 6 stats
        int hp = Integer.parseInt(stats.getChildText("HP"));
        int att = Integer.parseInt(stats.getChildText("ATK"));
        int def = Integer.parseInt(stats.getChildText("DEF"));
        int speed = Integer.parseInt(stats.getChildText("SPD"));
        int spatt = Integer.parseInt(stats.getChildText("SAT"));
        int spdef = Integer.parseInt(stats.getChildText("SDF"));

        //TODO: should be refactored to have list off types in Pokemon
        List<Element> typesNodes = pokemonNode.getChildren("type");

        //every pokemon has at least one type
        PokemonType type1 = PokemonType.valueOf(
                typesNodes.get(0).getText().toUpperCase());

        //optional second type handled with ternary operator for now
        PokemonType type2 = typesNodes.size() == 2
                            ? PokemonType.valueOf(
                        typesNodes.get(1).getText().toUpperCase())
                            : null;

        Move[] moves = new Move[4];

        //populates moves array with only moves specified in moveNames
        for (int i = 0; i < moveNames.size(); i++) {
            moves[i] = createMove(moveNames.get(i));
        }

        //create Pokemon object from all information gathered
        Pokemon pokemon = new Pokemon(natDexNum, hp, att, spatt, def, spdef,
                                      speed,
                                      pokemonName, nickname, moves, type1,
                                      type2);

        return pokemon;

    }
}
