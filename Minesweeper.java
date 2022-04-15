import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Minesweeper extends JPanel {
    private Board board;
    private Cell[][] totalCells;
    private boolean gameDone;
    private JButton reset = new JButton(" ");
    private gameState state;
    private levelOfDifficulty difficulty;
    private int difficultyNumber;



    public Minesweeper(Board board, levelOfDifficulty difficulty) {
        setDifficulty(difficulty);
        this.board = board;
        this.difficulty = difficulty;
        totalCells = board.getBoard();
        setState(gameState.notStarted);
        board.initializeAgain();
//        board.countAdjacent();
        //board.createBoard(difficultyNumber);
    }

    public gameState getState() {
        return state;
    }

    public void setState(gameState state) {
        this.state = state;
    }

    public int getDifficultyNumber() {
        return difficultyNumber;
    }

    public void setDifficultyNumber(int difficultyNumber) {
        this.difficultyNumber = difficultyNumber;

    }

    public void reset() {
        board.initializeAgain();
        board.countAdjacent();
    }

    public levelOfDifficulty getDifficulty() {
        return difficulty;
    }

    public void refresh() {
        board.repaint();
    }

    public void startGame() {
        board.createBoard(difficultyNumber);
        board.initializeAgain();
        board.countAdjacent();
        board.repaint();
        setState(gameState.Started);
    }


    public void setDifficulty( levelOfDifficulty difficulty) {
        if(difficulty.equals(levelOfDifficulty.Expert)) {
            difficultyNumber = 3;
        } else if (difficulty.equals(levelOfDifficulty.Intermediate)) {
            difficultyNumber = 2;
        } else if(difficulty.equals(levelOfDifficulty.Beginner)) {
            difficultyNumber = 1;
        }
    }

    public void winGame() {
        gameDone = true;
        setState(gameState.Won);
        for(int i=0;i< board.getRowCount();i++) {
            for(int j=0; j<board.getColumnCount();j++) {
                board.revealValueAtLocation(i,j);

                if(!board.revealTypeAtLocation(i,j).getClass().getName().equals("mineCell")) {
                    board.unflagAtLocation(i,j);
                }
            }
        }
        refresh();
        JOptionPane.showMessageDialog(null, "You have suprisingly won!");
        reset();
    }

    public void lose() {
        setState(gameState.Lost);
        gameDone = true;
        for(int i=0;i<board.getRowCount();i++) {
            for(int j=0;j<board.getColumnCount();j++) {
                if(totalCells[i][j].getClass().getName().equals("mineCell")) {
                    totalCells[i][j].reveal();
                }
                if(totalCells[i][j].isFlagged() && totalCells[i][j].getClass().getName().equals("numberCell")) {
                    totalCells[i][j].unFlag(totalCells[i][j].getCellButton());
                }

            }
        }
        refresh();
        JOptionPane.showMessageDialog(null,"Game over, You have lost!");
        reset();
    }

    public JButton getReset() {
        return reset;
    }

    public void setReset(JButton reset) {
        this.reset = reset;
    }
}
