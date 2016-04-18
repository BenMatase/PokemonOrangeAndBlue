/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 13, 2016
 * Time: 5:12:27 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: AIUtility
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import PokemonObjects.EnemyTrainer;
import PokemonObjects.Move;
import PokemonObjects.UserTrainer;
import java.util.Random;

/**
 *
 * @author Jason
 */
public class AIUtility {

    /**
     * Gets the move that will be selected by an AI trainer based on its current
     * moves and user pokemon
     *
     * @param usr
     * @param npc
     * @return
     */
    public static Move getMove(UserTrainer usr, EnemyTrainer npc) {
        //need to evaluate how much damage for each move
        BattleCalculator battleCalculator = new BattleCalculator(
                usr.getCurPokemon(), npc.getCurPokemon(),
                npc.getCurPokemon().getMoves()[0]);
        //create an array of number of doubles based on number of moves
        double[] moveDamage = new double[npc.getCurPokemon().getNumMoves()];
        for (int x = 0; x < npc.getCurPokemon().getNumMoves(); x++) {
            battleCalculator.setMove(npc.getCurPokemon().getMoves()[x]);
            moveDamage[x] = battleCalculator.AIMoveAdvantage();
        }
        System.out.println("Damage of move 0: " + moveDamage[0]);
        System.out.println("Damage of move 1: " + moveDamage[1]);
        System.out.println("Damage of move 2: " + moveDamage[2]);
        int moveChoice = randomFromArray(moveDamage);
        return npc.getCurPokemon().getMoves()[moveChoice];
    }

    /**
     * Takes in an array of doubles and returns a random number. The random
     * number is chosen by summing all the doubles together and getting a random
     * number between 0 and said number and determining which array it came
     * from.
     *
     * @param doubleArray The array of double values to get a random index
     * choice of
     * @return The index the random value is selected from
     */
    private static int randomFromArray(double[] doubleArray) {
        double totValues = 0;
        for (double dobVal : doubleArray) {
            totValues += dobVal;
        }
        Random randGenerator = new Random();
        double randValue = (totValues) * randGenerator.nextDouble();
        int arrayChoice = 0; //the array index that the random number is from
        double ArrayValue = doubleArray[0]; //the sum of the values in the current index and previous indices
        System.out.println(
                "Damage value :" + randValue);
        while (randValue > ArrayValue) {
            arrayChoice++;
            ArrayValue += doubleArray[arrayChoice];
        }
        return arrayChoice;
    }

}
