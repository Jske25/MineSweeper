import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {

    @Test
    void testGameSetupInitialization() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.gameCreate();
        int bombCount = 0;

        for (int i = 0; i < setup.width(); i++) {
            for (int j = 0; j < setup.height(); j++) {
                if (setup.getSquare(i, j).isBomb()) {
                    bombCount++;
                }
            }
        }

        assertEquals(10, bombCount, "The number of bombs should match the initialized count.");
    }

    @Test
    void testUncoverNonBombSquare() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.reset();

        setup.getSquare(0, 0).setBomb(); // Place a bomb in a different location
        setup.uncover(1, 1); // Uncover a non-bomb square

        assertFalse(setup.getSquare(1, 1).isCover(), "The square should be uncovered.");
        assertFalse(setup.isGameOver(), "The game should not be over after uncovering a non-bomb square.");
    }

    @Test
    void testUncoverBombSquare() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.reset();

        setup.getSquare(0, 0).setBomb(); // Place a bomb
        setup.uncover(0, 0); // Uncover the bomb

        assertTrue(setup.isGameOver(), "The game should end after uncovering a bomb.");
    }

    @Test
    void testFlaggingAndUnflagging() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.gameCreate();

        setup.switchFlag(0, 0);
        assertTrue(setup.getSquare(0, 0).checkFlag(), "The square should be flagged.");

        setup.switchFlag(0, 0);
        assertFalse(setup.getSquare(0, 0).checkFlag(), "The square should no longer be flagged.");
    }

    @Test
    void testWinCondition() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.reset();

        // Simulate all non-bomb squares being uncovered
        for (int i = 0; i < setup.width(); i++) {
            for (int j = 0; j < setup.height(); j++) {
                if (!setup.getSquare(i, j).isBomb()) {
                    setup.uncover(i, j);
                }
            }
        }

        assertTrue(setup.checkWin(), "The game should be won when all non-bomb squares are uncovered.");
    }

    @Test
    void testSaveAndLoadGame() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.gameCreate();
        setup.switchFlag(0, 0);
        setup.getSquare(1, 1).setBomb();
        setup.saveToFile();

        GameSetup loadedSetup = new GameSetup(9, 9, 10);
        loadedSetup.loadFile();

        for (int i = 0; i < setup.width(); i++) {
            for (int j = 0; j < setup.height(); j++) {
                Square original = setup.getSquare(i, j);
                Square loaded = loadedSetup.getSquare(i, j);

                assertEquals(original.isBomb(), loaded.isBomb(), "Bomb states should " +
                        "match after loading.");
                assertEquals(original.isCover(), loaded.isCover(), "Covered states should " +
                        "match after loading.");
                assertEquals(original.checkFlag(), loaded.checkFlag(), "Flagged states should " +
                        "match after loading.");
            }
        }
    }

    @Test
    void testResetGame() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.gameCreate();
        setup.uncover(1, 1);
        setup.reset();

        assertTrue(setup.getSquare(1, 1).isCover(), "All squares should be covered " +
                "after resetting the game.");
        assertFalse(setup.isGameOver(), "Game should not be over after resetting.");
    }

    @Test
    void testSetup() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.gameCreate();

        int bombCount = 0;
        for (int i = 0; i < setup.width(); i++) {
            for (int j = 0; j < setup.height(); j++) {
                if (setup.getSquare(i, j).isBomb()) {
                    bombCount++;
                }
            }
        }

        assertEquals(10, bombCount, "The number of bombs on the board " +
                "should match the initial setup.");
        for (int i = 0; i < setup.width(); i++) {
            for (int j = 0; j < setup.height(); j++) {
                assertTrue(setup.getSquare(i, j).isCover(), "All squares should be covered " +
                        "at the start of the game.");
            }
        }
    }

    @Test
    void testUncoveringSquare() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.reset();

        setup.getSquare(1, 1).setBomb();
        setup.uncover(0, 0);

        assertFalse(setup.getSquare(0, 0).isCover(), "The square at (0,0) should be uncovered.");
        assertFalse(setup.isGameOver(), "The game should not end after uncovering a non-bomb square.");
    }

    @Test
    void testFlagFunctionality() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.reset();

        setup.switchFlag(0, 0);
        assertTrue(setup.getSquare(0, 0).checkFlag(), "The square at (0,0) should be flagged.");
        assertEquals(9, setup.getFlags(), "The number of remaining flags " +
                "should decrease after flagging.");

        setup.switchFlag(0, 0);
        assertFalse(setup.getSquare(0, 0).checkFlag(), "The square at (0,0) " +
                "should no longer be flagged.");
        assertEquals(10, setup.getFlags(), "The number of remaining flags should " +
                "increase after unflagging.");
    }

    @Test
    void testGameOverWhenClickingBomb() {
        GameSetup setup = new GameSetup(9, 9, 10);
        setup.reset();

        setup.getSquare(0, 0).setBomb();
        setup.uncover(0, 0);

        assertTrue(setup.isGameOver(), "The game should be over after clicking a bomb.");
        assertFalse(setup.getSquare(0, 0).isCover(), "The bomb square should be " +
                "uncovered after clicking.");
    }




}
