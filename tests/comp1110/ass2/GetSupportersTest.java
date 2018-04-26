package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.HashSet;
import java.util.Random;

import static comp1110.ass2.TestUtility.PLACEMENTS;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test objective:
 * <p>
 * Get the list of supporters for the chosen player, given the provided
 * setup and move sequence.
 * The list of supporters is a sequence of two-character card IDs, representing
 * the cards that the chosen player collected by moving Zhang Yi.
 */
public class GetSupportersTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testGood() {
        for (int i = 0; i < PLACEMENTS.length; i++) {
            String setup = TestUtility.shufflePlacement(PLACEMENTS[i]);
            for (int j = 0; j < TestUtility.MOVE_SEQUENCES[i].length; j++) {
                String moveSequence = TestUtility.MOVE_SEQUENCES[i][j];
                for (int p = 0; p < 4; p++) {
                    checkSupporters(TestUtility.SUPPORTERS[i][j][p], setup, moveSequence, p);
                }
            }
        }
    }

    @Test
    public void testDeleted() {
        Random r = new Random();
        for (int i = 0; i < PLACEMENTS.length; i++) {
            String setup = TestUtility.shufflePlacement(PLACEMENTS[i]);
            for (int j = 0; j < TestUtility.MOVE_SEQUENCES[i].length; j++) {
                String moveSequence = TestUtility.MOVE_SEQUENCES[i][j];
                for (int p = 0; p < 4; p++) {
                    String supporters = WarringStatesGame.getSupporters(setup, moveSequence, 4, p);
                    assertNotNull(supporters);
                    int victim = r.nextInt((supporters.length()) / 2 - 1);
                    String victimStr = supporters.substring(victim * 2, (victim + 1) * 2);
                    supporters = supporters.substring(0, victim * 2) + supporters.substring((victim + 1) * 2);
                    assertFalse("Missing supporter " + victimStr + ", expected " + TestUtility.SUPPORTERS[i][j][p], TestUtility.SUPPORTERS[i][j][p].equals(supporters));
                }
            }
        }
        checkSimpleValid();
    }

    @Test
    public void testTransferred() {
        Random r = new Random();
        for (int i = 0; i < PLACEMENTS.length; i++) {
            String setup = TestUtility.shufflePlacement(PLACEMENTS[i]);
            for (int j = 0; j < TestUtility.MOVE_SEQUENCES[i].length; j++) {
                String moveSequence = TestUtility.MOVE_SEQUENCES[i][j];
                for (int p = 0; p < 4; p++) {
                    String supporters = WarringStatesGame.getSupporters(setup, moveSequence, 4, p);
                    String supportersNextPlayer = WarringStatesGame.getSupporters(setup, moveSequence, 4, (p + 1) % 4);
                    assertNotNull(supportersNextPlayer);
                    int victim = r.nextInt((supportersNextPlayer.length()) / 2 - 1);
                    String victimStr = supportersNextPlayer.substring(victim * 2, (victim + 1) * 2);
                    supporters = supporters + victimStr;
                    assertFalse("Unexpected supporter " + victimStr + ", expected only " + TestUtility.SUPPORTERS[i][j][p], TestUtility.SUPPORTERS[i][j][p].equals(supporters));
                }
            }
        }
        checkSimpleValid();
    }

    private void checkSimpleValid() {
        checkSupporters(TestUtility.SUPPORTERS[0][0][0], PLACEMENTS[0], TestUtility.MOVE_SEQUENCES[0][0], 0);
    }

    private void checkSupporters(String expected, String setup, String moveSequence, int playerNum) {
        String actual = WarringStatesGame.getSupporters(setup, moveSequence, 4, playerNum);
        String testDescription = "getSupporters for 4 players setup " + setup + " moveSequence " + moveSequence + " playerNum " + playerNum + ": ";
        assertTrue(testDescription + ": supporter string is null", actual != null);
        assertTrue(testDescription + "supporter string is of length " + actual.length() + ": should be a sequence of two-character card IDs", actual.length() % 2 == 0);
        HashSet<String> s = new HashSet<>();
        for (int i = 0; i < actual.length(); i += 2) {
            String supporter = actual.substring(i, i + 2);
            assertTrue(testDescription + "found duplicated supporter " + supporter, s.add(supporter));
        }
        HashSet<String> e = new HashSet<>();
        for (int i = 0; i < expected.length(); i += 2) {
            e.add(expected.substring(i, i + 2));
        }
        for (String c : e) {
            assertTrue(testDescription + "did not find expected supporter " + c, s.contains(c));
        }
        for (String c : s) {
            assertTrue(testDescription + "found unexpected supporter " + c, e.contains(c));
        }
    }
}
