/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Benjamin Matase, Jason Corriveau, Eric Marshall, Alexander Murph
 * Date: Apr 9, 2016
 * Time: 6:24:46 PM
 *
 * Project: csci205FinalProject
 * Package: BattleUtility
 * File: BattleSimulatorTest
 * Description:
 *
 * ****************************************
 */
package BattleUtility;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Murph
 */
public class BattleSimulatorTest {

    public BattleSimulatorTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of simulate method, of class BattleSimulator.
     */
    @Test
    public void testSimulate() throws Exception {
        System.out.println("simulate");
        BattleSimulator instance = null;
        ArrayList<Events> expResult = null;
        ArrayList<Events> result = instance.simulate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
