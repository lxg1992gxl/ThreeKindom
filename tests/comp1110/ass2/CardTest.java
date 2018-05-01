package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static comp1110.ass2.TestUtility.randomCard;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static comp1110.ass2.WarringStatesGame.isValidCard;

public class CardTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testGood() {
        Random r = new Random();
        String card = randomCard(r);
        assertTrue(isValidCard(card));

    }

    //            if (chunks[0] >= 'a' & chunks[0] <= 'g') {
    //                //check for second character
    //                if ((chunks[1] + chunks[0] <= 152) & (chunks[1] >= 48)) {
    //                    //check for third character
    //assuming a0-9, through to g0

    @Test
    public void testBad() {
        Random r = new Random();
        //from testUtility generate card FIXME so not copy pasted?
        char k = (char) ('h' + r.nextInt(19));
        char c = (char) ('0' + r.nextInt(8));

        //bad number

        String card = ""+k+c;

        assertTrue("Invalid kingdom code: expected a-g, but got "+k,!isValidCard(card));
        //letter out of bounds

    }


    @Test
    public void testLength(){
        Random r = new Random();
        String card = randomCard(r);
        assertFalse("Card representation strings must be 2 characters long", card.length()!=2);

    }

    @Test
    public void testKingdom(){

        Random r = new Random();
        char k = (char) ('a' + r.nextInt(7));
        int n = 8;// - (k - 'a');
        char c = (char) ('0' + r.nextInt(n));
        String card = ""+k+c;

        //number out of bounds for kingdom in question
        assertTrue(card, !isValidCard(card));

    }
}
