public class Main {
    public static void main(String[] args) {

        Minesweeper mine = new Minesweeper(new Board(8,8,10),levelOfDifficulty.Beginner);
        mine.startGame();
        mine.winGame();
        //b.toString();
        //b.getType();
    }
}
