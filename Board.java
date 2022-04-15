import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Board extends JPanel implements ActionListener, MouseListener {
    private int rowCount;
    private int columnCount;
    private int height;
    private int weight;
    private  int nrOfMines;
    private Cell[][] board;
    private JButton[][] buttons;
    private JButton button;
    private Minesweeper mine;
    private int numCellUnFlagged;
    private int numCellRevealed;
    private boolean[][] hiddenMines;
    private JFrame screen = null;
    private JPanel composite = new JPanel();
    private  JPanel topPanel = new JPanel();
    private JButton smiley = new JButton();
    private JPanel tommy = null;


    ImageIcon initialSmiley = getScaledImages("C:\\Users\\adewa\\IdeaProjects\\minesweeper Git\\first smiley.png");
    ImageIcon winSmiley = null;
    ImageIcon loseSmiley = null;


    public Board(int rowCount, int columnCount, int nrOfMines) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.nrOfMines = nrOfMines;
        board = new Cell[rowCount][columnCount];
        hiddenMines = new boolean[rowCount][columnCount];
        buttons = new JButton[rowCount][columnCount];
        numCellRevealed = 0;
        numCellUnFlagged = nrOfMines;
        initializeAgain();
        screen = new JFrame("Minesweeper");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);
        screen.setResizable(false);
        screen.setJMenuBar(makeMenuBar());

        composite.setLayout(new BorderLayout());
        smiley.setPreferredSize(new Dimension(20,20));
        smiley.setIcon(initialSmiley);
        topPanel.add(smiley);
        composite.add(topPanel,BorderLayout.NORTH);
        smiley.addActionListener(this);
        smiley.addMouseListener(this);
        screen.add(composite);
        screen.pack();
    }

    public void loadImages() {
        initialSmiley = getScaledImages("C:\\Users\\adewa\\IdeaProjects\\minesweeper Git\\first smiley.png");
        winSmiley = getScaledImages("C:\\Users\\adewa\\IdeaProjects\\minesweeper Git\\smiley.png");
        loseSmiley = getScaledImages("C:\\Users\\adewa\\IdeaProjects\\minesweeper Git\\lost.png");
    }

    public ImageIcon getScaledImages(String imageString) {
        ImageIcon imageIcon = new ImageIcon(imageString);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }

    public JMenuBar makeMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu gam = new JMenu("Game");
        JMenu help = new JMenu("Help");

        JMenuItem mineNew = new JMenuItem(("New"));
        JMenuItem mineBeg = new JMenuItem("Beginner");
        JMenuItem mineInt = new JMenuItem("Intermediate");
        JMenuItem mineExp = new JMenuItem("Expert");
        JMenuItem mineEnd = new JMenuItem("Exit");

        gam.add(mineNew);
        gam.add(mineBeg);
        gam.add(mineInt);
        gam.add(mineExp);
        gam.add(mineEnd);
        bar.add(gam);
        bar.add(help);
        return bar;
    }

    public Board() {
    }

    public int getRowCount() {
        return rowCount;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }


    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getNrOfMines() {
        return nrOfMines;
    }

    public void setNrOfMines(int nrOfMines) {
        this.nrOfMines = nrOfMines;
    }

    public Cell[][] createBoard(int difficulty) {
        if(difficulty == 1) { //1 is Beginner
            rowCount = 8;
            columnCount = 8;
            nrOfMines = 10;
            board = new Cell[rowCount][columnCount];
        } else if (difficulty == 2) { //2 is Intermediate
            rowCount = 16;
            columnCount = 16;
            nrOfMines = 40;
            board = new Cell[rowCount][columnCount];
        } else if(difficulty == 3) { //3 is Expert
            rowCount = 16;
            columnCount = 30;
            nrOfMines = 99;
            board = new Cell[rowCount][columnCount];
        } else {
            //don't allow any other boards or mines to be built
            throw new IndexOutOfBoundsException("That board is not standard, we don't support custom yet");
        }
        return board;
    }

    public int getNumCellUnFlagged() {
        numCellUnFlagged = nrOfMines;
        for(int i = 0;i<rowCount;i++) {
            for(int j =0;j<columnCount;j++) {
                if(board[i][j].isFlagged()) {
                    numCellUnFlagged--;
                }
            }
        }
        return numCellUnFlagged;
    }

    public ArrayList<Integer> getRandomNos(int number) {
        int numRandom = nrOfMines;
        ArrayList<Integer> randoms = new ArrayList<>(numRandom);

        Random random = new Random();
        for(int i=0;i<numRandom;i++) {
            randoms.add(random.nextInt(number));
        }
        return randoms;
    }

    public void initializeAgain() {
        ArrayList<Integer> row_randoms = getRandomNos(rowCount);
        ArrayList<Integer> column_randoms = getRandomNos(columnCount);
        Collections.shuffle(row_randoms);
        //Collections.shuffle(column_randoms);

        for(int i=0;i<nrOfMines;i++) {
            int x = row_randoms.get(i);
            int y = column_randoms.get(i);
            board[x][y] = new mineCell(0,0,-1);
        }

        for(int i=0;i<rowCount;i++) {
            for(int j=0;j<columnCount;j++) {
                if(board[i][j] == null) {
                    board[i][j] = new numberCell(0,0,0);
                }
            }
        }
    }

    public void getType() {
        for(int i=0;i<rowCount;i++) {
            for(int j=0;j<columnCount;j++) {
                System.out.println(board[i][j].getClass());
            }
        }
    }

    public void countAdjacent() {
        for(int i=0; i<rowCount;i++) {
            for(int j =0; j<columnCount;j++) {
                int count = 0;
                if((i>0 && j>0) && board[i-1][j-1].getClass().getName().equals("mineCell")) {
                    count++;
                }
                if(j>0 && board[i][j-1].getClass().getName().equals("mineCell")) {
                    count++;
                }
                if((i<rowCount-1 && j>0) && board[i+1][j-1].getClass().getName().equals("mineCell")) {
                    count++;
                }
                if(i>0 && board[i-1][j].getClass().getName().equals("mineCell")) {
                    count++;
                }
                if(i<rowCount-1 && board[i+1][j].getClass().getName().equals("mineCell")) {
                    count++;
                }
                if((i>0&&j<columnCount-1) && board[i-1][j+1].getClass().getName().equals("mineCell")) {
                    count++;
                }
                if(j>columnCount-1 && board[i][j+1].getClass().getName().equals("mineCell")) {
                    count++;
                }
                if((i<rowCount-1 && j<columnCount-1) && board[i+1][j+1].getClass().getName().equals("mineCell")) {
                    count++;
                }
                if(board[i][j].getClass().getName().equals("mineCell")) {
                    count = -9;
                    board[i][j].setCellID(count);
                } else {
                    board[i][j].setCellID(count);
                }
                if(board[i][j].getCellID()==0) {
                    board[i][j].reveal();
                }
            }
        }
        System.out.println(Arrays.deepToString(board));
    }

    public boolean minePresent(int x, int y) {
        if(board[x][y].getClass().getName().equals("mineCell")) {
            return true;
        } else {
            return false;
        }
    }

    public int revealValueAtLocation(int x, int y) {
        int value = 0;
        for (int i = 0; i<rowCount;i++) {
            for(int j = 0; j<columnCount;j++) {
                i = x;
                j = y;
                board[x][y].reveal();
                board[x][y].setRevealed(true);
            }
        }
        return board[x][y].getCellID();
    }

    public Cell revealTypeAtLocation(int x, int y) {
        board[x][y].setRevealed(true);
        return board[x][y];
    }

    public boolean[][] getHiddenMines() {
        return hiddenMines;
    }

    public void flagOnCell(int x, int y) {
        board[x][y].flag(board[x][y].getCellButton());
    }

    public void unflagAtLocation(int x, int y) {
        board[x][y].unFlag(board[x][y].getCellButton());
    }


    public void minePosition() {
        for(int i=0; i<rowCount;i++) {
            for(int j=0; j<columnCount;j++) {
                if(board[i][j].getClass().getName().equals("mineCell")) {
                    hiddenMines[i][j] = true;
                } else {
                    hiddenMines[i][j] = false;
                }
            }
        }
    }

    public void relocateMine(int x, int y) {
        for(int i=0;i<rowCount;i++) {
            for(int j= 0; j<columnCount;j++) {
                if(board[i][j].getClass().getName().equals("mineCell")) {
                    board[i][j] = new numberCell(0,0,0);
                    i = x;
                    j = y;
                    board[x][y] = new mineCell(0,0,0);
                    break;
                }
            }
        }
    }

    public void blankOpen(int x, int y) {
        board[x][y].setCellID(0);
        board[x][y].reveal();
        for(int i=0;i<rowCount;i++) {
            for(int j=0;j<columnCount;j++) {
                if((i>0 && j>0) && board[i-1][j-1].getCellID()==0) {
                    board[i][j].reveal();
                }
                if(j>0 && board[i][j-1].getCellID()==0) {
                    board[i][j].reveal();
                }
                if((i<rowCount-1 && j>0) && board[i+1][j-1].getCellID()==0) {
                    board[i][j].reveal();
                }
                if(i>0 && board[i-1][j].getCellID()==0) {
                    board[i][j].reveal();
                }
                if(i<rowCount-1 && board[i+1][j].getCellID()==0) {
                    board[i][j].reveal();
                }
                if((i>0&&j<columnCount-1) && board[i-1][j+1].getCellID()==0) {
                    board[i][j].reveal();
                }
                if(j>columnCount-1 && board[i][j+1].getCellID()==0) {
                    board[i][j].reveal();
                }
                if((i<rowCount-1 && j<columnCount-1) && board[i+1][j+1].getCellID()==0) {
                    board[i][j].reveal();
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //mine.reset();
        mine.refresh();

    }

    @Override
    public void mouseClicked(MouseEvent click) {
        Object pressed = click.getSource();
        int row = 0;
        int col = 0;
        for(int i=0;i<rowCount;i++) {
            for(int j=0;j<columnCount;j++) {
                if(pressed.equals(board[i][j].getCellButton())) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        boolean firstClick = true;
        for(int i=0;i<rowCount;i++) {
            for(int j=0;j<columnCount;j++) {
                if(board[i][j].isRevealed()) {
                    firstClick = false;
                }
            }
        }
        if(firstClick) {
            while(!board[row][col].getClass().getName().equals("numberCell") && board[row][col].getCellID()!=0) {
                countAdjacent();
                blankOpen(row,col);
            }
        }
        Cell check = board[row][col];
        if(SwingUtilities.isLeftMouseButton(click)) {
            check.leftClick(board[row][col].getCellButton());
            if(check.getCellID()==0) {
                blankOpen(row, col);
            }
        } else if(SwingUtilities.isRightMouseButton(click)) {
            if(check.isFlagged() && numCellUnFlagged < nrOfMines) {
                check.rightClick(board[row][col].getCellButton());
                numCellUnFlagged++;

            } else if(!check.isFlagged() && numCellUnFlagged > 0) {
                check.rightClick(board[row][col].getCellButton());
                numCellUnFlagged--;
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
