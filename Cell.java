import javax.swing.*;
import java.awt.*;
import java.util.*;

public abstract class Cell {
    protected int height;
    protected int width;
    protected int cellID;
    protected JButton cellButton;
    protected boolean isFlagged;
    protected boolean isRevealed;
    protected int rowPosition;
    protected int columnPosition;
    protected boolean isMine;
    protected Minesweeper mine;


    public Cell(int height, int width, int cellID) {
        this.height = height;
        this.width = width;
        this.cellID = cellID;
        cellButton = new JButton();
        isRevealed = false;
        isFlagged = false;
        rowPosition = 0;
        columnPosition= 0;
    }

    public JButton getCellButton() {
        return cellButton;
    }

    public void setCellButton(JButton cellButton) {
        this.cellButton = cellButton;
    }

    public Cell(int height, int width) {
        this.height = height;
        this.width = width;
        isRevealed = false;
        isFlagged = false;
    }

    public Cell() {
        isFlagged = false;
        isRevealed = false;
    }

    public abstract int getCellID();
    public abstract void setCellID(int cellID);

    public void flag(JButton b) {
        cellButton = b;
        isFlagged = true;
    }

    public void unFlag(JButton m) {
        cellButton = m;
        isFlagged = false;
    }

    public void reveal() {
        getCellID();
        isRevealed = true;
    }


    public abstract void leftClick(JButton m);
    public abstract void draw(JButton m);

    public void rightClick(JButton m) {
        setCellButton(m);
        if(isRevealed) {

        } else {
            if(isFlagged) {
                unFlag(m);
            } else {
                flag(m);
            }
        }
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
