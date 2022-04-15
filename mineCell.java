import javax.swing.*;
import java.util.*;

public class mineCell extends Cell {
    ImageIcon bombImage = null;

    public mineCell(int height, int width, int cellID) {
        super(height, width, cellID);
        isMine = true;
    }

    public mineCell(int height, int width) {
        super(height, width);
        isMine = true;
    }

    public mineCell() {
        super();
        isMine = true;
    }

    @Override
    public int getCellID() {
        return cellID;
    }

    @Override
    public void setCellID(int cellID) {
        this.cellID=cellID;
    }

    @Override
    public void leftClick(JButton m) {
        setCellButton(m);
        reveal();
        mine.lose();

    }

    @Override
    public void draw(JButton m) {

    }


    @Override
    public String toString() {
        return " " +cellID ;
    }



}
