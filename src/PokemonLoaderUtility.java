
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
 * TODO: account for errors in nomenclature of moves
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

    public static Move createMove(String moveName) {
        Element moveNode = getMoveNode(moveName);

        PokemonType type = PokemonType.valueOf(
                moveNode.getChildText("type").toUpperCase());

        AttackType attType = AttackType.valueOf(
                moveNode.getChildText("class").toUpperCase());

        int damage = Integer.parseInt(moveNode.getChildText("power"));

        float accuracy = Float.parseFloat(moveNode.getChildText("accuracy"));

        //TODO: this is not finished, need to figure out battletype and how to deal with accuracy
        Move move = new Move(attType, type, damage, (int) accuracy, moveName);

        return move;
    }

    /**
     * TODO: possible refactor
     *
     * @param moveName
     * @return
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

    public static Pokemon createPokemon(String pokemonName, String nickname,
                                        List<String> moveNames) {
        Element pokemonNode = getPokemonNode(pokemonName);

        //fill Pokemon nation dex number TODO: add this to Pokemon
        int natDexNum = Integer.parseInt(
                pokemonNode.getAttribute("id").getValue());

        //fill in Pokemon's stats
        Element stats = pokemonNode.getChild("stats");

        int hp = Integer.parseInt(stats.getChildText("HP"));
        int att = Integer.parseInt(stats.getChildText("ATK"));
        int def = Integer.parseInt(stats.getChildText("DEF"));
        int speed = Integer.parseInt(stats.getChildText("SPD"));
        int spatt = Integer.parseInt(stats.getChildText("SAT"));
        int spdef = Integer.parseInt(stats.getChildText("SDF"));

        List<Element> typesNodes = pokemonNode.getChildren("type");
        //TODO: should be refactored to have list off types in Pokemon

        //every pokemon has at least one type
        PokemonType type1 = PokemonType.valueOf(
                typesNodes.get(0).getText().toUpperCase());

        //optional second type
        PokemonType type2 = typesNodes.size() == 2
                            ? PokemonType.valueOf(
                        typesNodes.get(1).getText().toUpperCase())
                            : null;

        Move[] moves = new Move[4];

        //populates moves array with only moves specified in moveNames
        for (int i = 0; i < moveNames.size(); i++) {
            moves[i] = createMove(moveNames.get(i));
        }

        //TODO: figure out why that isn't a valid constructor
        Pokemon pokemon = new Pokemon(hp, att, spatt, def, spdef, speed,
                                      pokemonName, nickname, moves, type1,
                                      type2);

        return pokemon;

    }
}
