package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static comp1110.ass2.TestUtility.randomLocation;
import static comp1110.ass2.WarringStatesGame.isValidLocation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testGood() {
        Random r = new Random();
        char choice = randomLocation(r);
//        //check letter codes
//        if(r.nextBoolean()){
//            choice = (char)(r.nextInt(26)+65);
//        }
//        //check number codes
//        else {choice = (char)(r.nextInt(10)+48);}

        assertTrue(isValidLocation(choice));
    }

    @Test
    public void testBad() {
        Random r = new Random();

        //char choice = ((char)r.nextInt(200));
        char choice = 'a'-1;

        //caps wrong
        assertFalse("Board location encoding must use upper case characters", choice >= 'a' && choice <= 'z');
        //out of bounds
        assertTrue("Board character " + choice+ " is out of bounds", (!isValidLocation(choice)));
    }
}
