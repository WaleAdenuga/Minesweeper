import java.awt.*;
import java.util.*;
import javax.swing.*;

public class numberCell extends Cell {
    private int adjacentCount;
    ImageIcon imageOne = null;
    ImageIcon imageTwo = null;
    ImageIcon imageThree = null;
    ImageIcon imageFour = null;
    ImageIcon imageFive = null;

    public numberCell(int height, int width) {
        super(height, width);
        isMine = false;
    }

    @Override
    public int getCellID() {
        return cellID;
    }

    @Override
    public void setCellID(int cellID) {
        this.cellID = cellID;
    }

    @Override
    public void leftClick(JButton b) {
        reveal();
        setCellButton(b);
        if(!isFlagged) {
            setRevealed(true);
            draw(b);
        } else{
            setRevealed(false);
        }
    }

    @Override
    public void draw(JButton m) {
        m.setText(" "+ getCellID());
        m.setMargin(new Insets(0,0,0,0));

    }


    public numberCell(int height, int width, int cellID) {
        super(height, width, cellID);
    }

    @Override
    public String toString() {
        return " " +cellID ;
    }
}
