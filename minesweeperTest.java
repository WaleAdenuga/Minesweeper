import org.junit.*;

import javax.swing.*;

import static org.junit.Assert.*;
import java.util.*;

public class minesweeperTest {

    @Before
    public void SetUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBoardConstructor() {
        Board m = new Board(8,8,10);
        assertEquals(8, m.getRowCount());
        assertEquals(8,m.getColumnCount());
        assertEquals(10, m.getNrOfMines());
    }

    @Test
    public void testCreateBoard() {
        Board board = new Board(0,0,0);
        assertEquals(0,board.getRowCount());
        board.createBoard(1); // test Beginner to Expert levels
        assertEquals(8,board.getRowCount());
        board.createBoard(2);
        assertEquals(16, board.getRowCount());
        board.createBoard(3);
        assertEquals(30, board.getColumnCount());
        assertEquals(16, board.getRowCount());
    }

    @Test
    public void testRandomGenerator() {
        Board board = new Board(0,0,0);
        assertEquals(0, board.getRowCount());
        board.createBoard(1);
        assertEquals(10, board.getNrOfMines());
        ArrayList<Integer> expected = board.getRandomNos(board.getRowCount());
        assertEquals(10, expected.size());
        board.createBoard(3);
        assertEquals(30, board.getColumnCount());
        ArrayList<Integer> again = board.getRandomNos(board.getColumnCount());
        assertEquals(board.getNrOfMines(), again.size());
    }

    @Test
    public void testInitialization() {
        Board board = new Board(0,0,0);
        assertNotEquals(8, board.getRowCount());
        board.createBoard(1);
        Cell[][] totalCells = board.getBoard();
        int x = 0;
        int y = 5;
        assertNull(totalCells[x][y]);
        board.initializeAgain();
        assertNotNull(board);
        for(int i=0; i<totalCells.length;i++) {
            for(int j = 0; j<totalCells[i].length;j++) {
                assertNotNull(totalCells[i][j]);
                assertTrue(totalCells[i][j].getClass().getName().equals("mineCell") || totalCells[i][j].getClass().getName().equals("numberCell"));
                break;
            }
            break;
        }
    }

    @Test
    public void testCountAdjacent() {
        Board board = new Board(0,0,0);
        assertNotEquals(14, board.getRowCount());
        board.createBoard(1);
        board.initializeAgain();
        assertNotNull(board);
        board.countAdjacent();
        Cell[][] totalCells = board.getBoard();
        for(int i =0; i<totalCells.length;i++) {
            for(int j= 0; j<totalCells[i].length;j++) {
                if(totalCells[i][j].getClass().getName().equals("mineCell")) {
                    assertEquals(-9, totalCells[i][j].getCellID());
                } else if(totalCells[i][j].getClass().getName().equals(("numberCell"))) {
                    int check = totalCells[i][j].getCellID();
                    assertTrue(check != -9);
                    assertTrue(check >=0);
                }
                break;
            }
            break;
        }
    }

//    @Test
//    public void testRevealingValue() {
//        Board board = new Board(0,0,0);
//        assertNotEquals(25, board.getRowCount());
//        board.createBoard(1);
//        assertEquals(10, board.getNrOfMines());
//        board.initializeAgain();
//        board.countAdjacent();
//        Cell[][] totalCells = board.getBoard();
//        for(int i =0; i<totalCells.length;i++) {
//            for(int j=0; j<totalCells[i].length;j++) {
//                Cell cell = board.revealTypeAtLocation(i,j);
//                assertTrue(cell.getClass().getName().equals("mineCell") || cell.getClass().getName().equals("numberCell"));
//                int check = board.revealValueAtLocation(i,j);
//                assertEquals(check, totalCells[i][j].getCellID());
//                break;
//            }
//            break;
//        }
//    }

    @Test
    public void testMinePosition() {
        Board board = new Board(4,4,10);
        assertEquals(10, board.getNrOfMines());
        board.createBoard(1);
        assertEquals(8, board.getRowCount());
        assertNotEquals(15, board.getColumnCount());
        board.initializeAgain();
        board.countAdjacent();
        Cell[][] totalCells = board.getBoard();
        boolean[][] hiddenMines = board.getHiddenMines();
        for(int i=0;i<hiddenMines.length;i++) {
            for(int j= 0; j<hiddenMines[i].length;j++) {
                if(totalCells[i][j].getClass().getName().equals("mineCell")) {
                    assertTrue(hiddenMines[i][j]);
                } else if(totalCells[i][j].getClass().getName().equals("numberCell")) {
                    assertFalse(hiddenMines[i][j]);
                }
                break;
            }
            break;
        }
    }

    @Test
    public void testReplacingMine() {
        Board board = new Board(0,0,0);
        assertEquals(0,board.getColumnCount());
        board.createBoard(1);
        assertEquals(10, board.getNrOfMines());
        board.initializeAgain();
        board.countAdjacent();
        Cell[][] totalCells = board.getBoard();
        assertEquals(8, totalCells.length);
        int i = 5;
        int j = 6;
        board.relocateMine(i,j);
        assertTrue(totalCells[i][j].getClass().getName().equals("mineCell"));
    }

    @Test
    public void testCell() {
        Cell barry = new mineCell(0,0,-1);
        assertEquals(-1, barry.getCellID());
        assertFalse(barry.isFlagged());
        assertFalse(barry.isRevealed());
        barry.setFlagged(true);
        assertTrue(barry.isFlagged());
        Cell aron = new numberCell(0,0,4);
        assertEquals(4, aron.getCellID());
        aron.setRevealed(true);
        assertTrue(aron.isRevealed());
        JButton m = new JButton();
        aron.setCellButton(m);
        assertEquals(m,aron.getCellButton());
    }

    @Test
    public void testClickingCell() {
        Cell aron = new mineCell(0,0,0);
        JButton p = new JButton();
        aron.rightClick(p);
        assertEquals(p, aron.getCellButton());
        assertTrue(aron.isFlagged());
        aron.rightClick(p);
        assertFalse(aron.isFlagged());
        aron.rightClick(p);
        assertTrue(aron.isFlagged());

        Cell baziel = new numberCell(0,0,0);
        assertEquals(0,baziel.getCellID());
        JButton b = new JButton();
        baziel.setCellButton(b);
        assertEquals(b, baziel.getCellButton());
        assertFalse(baziel.isRevealed());
        baziel.leftClick(b);
        assertTrue(baziel.isRevealed());
        baziel.rightClick(b);
        assertFalse(baziel.isFlagged());

    }

    @Test
    public void testGameLogic() {
        Cell baziel = new numberCell(4,4,0);
        Cell aron = new mineCell(4,4,0);
        Board harry = new Board(0,0,0);
        //harry.createBoard(1);
        JButton chimene = new JButton();
        JButton sara = new JButton();
        JButton reset = new JButton();

        Minesweeper m = new Minesweeper(harry, levelOfDifficulty.Beginner);
        assertEquals(1,m.getDifficultyNumber());
        m.setDifficulty(levelOfDifficulty.Intermediate);
        assertEquals(2, m.getDifficultyNumber());
        m.setDifficulty(levelOfDifficulty.Expert);
        assertEquals(3,m.getDifficultyNumber());
        m.setDifficulty(levelOfDifficulty.Beginner);
        assertEquals(gameState.notStarted, m.getState());
        m.startGame();
        assertEquals(gameState.Started, m.getState());
        m.winGame();
        assertEquals(gameState.Won, m.getState());
        aron.leftClick(sara);
        assertEquals(sara, aron.getCellButton());
        assertEquals(gameState.Lost, m.getState());
        m.reset();
        assertEquals(gameState.Started, m.getState());
        m.winGame();
        assertTrue(baziel.isRevealed());
        m.reset();
        baziel.rightClick(chimene);
        assertTrue(baziel.isFlagged());
        m.winGame();
        assertFalse(baziel.isFlagged());
        m.reset();
        assertEquals(gameState.Started, m.getState());
    }
}
