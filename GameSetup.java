import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;


public class GameSetup {

    private Square[][] board;
    private final int numBombs;
    private int flags;
    private final int width;
    private final int height;
    private boolean gameOver;

    public GameSetup (int width, int height, int numBombs) {
        this.width = width;
        this.height = height;
        board = new Square[width][height];
        flags = 10;
        gameOver = false;
        this.numBombs = numBombs;
    }

    public void insertBombs() {
        int counter = 0;
        while (counter < numBombs) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            if (!board[x][y].isBomb()) {
                counter++;
                board[x][y].setBomb();
            }
        }
    }

    public void gameCreate() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = new Square(false, false, false);
            }
        }
        insertBombs();
    }

    public Square[][] getBoard() {
        return board;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int getFlags() {
        return flags;
    }

    public Square getSquare(int x, int y) {
        return board[x][y];
    }

    public int countAdjacentBombs(int x, int y) {
        int counter = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && j >= 0 && i < width && j < height && board[i][j].isBomb()) {
                    if (i == x && j == y) {
                        continue;
                    }
                    counter++;
                }
            }
        }
        return counter;
    }

    public void uncover(int x, int y) {

        if (gameOver) {
            return;
        }

        Square temp = board[x][y];

        if (temp.isBomb()) {
            gameOver = true;
            temp.setCover(true);
            return;
        }

        if (!temp.isCover() || temp.checkFlag()) {
            return;
        }

        recursive(x, y);

    }

    public void recursive(int x, int y) {
        Square currentSquare = board[x][y];

        if (!currentSquare.isCover()) {
            return;
        }

        int adjacentBombs = countAdjacentBombs(x, y);

        board[x][y].setValue(adjacentBombs);

        if (board[x][y].isBomb()) {
            return;
        }

        if (adjacentBombs == 0) {
            currentSquare.setCover(true);
            if (currentSquare.checkFlag()) {
                currentSquare.setFlag();
                flags++;
            }

            for (int i = x-1; i <= x+1; i++) {
                for (int j = y-1; j <= y+1; j++) {
                    if (i >= 0 && i < width && j >= 0 && j < height) {
                        recursive(i, j);
                    }
                }
            }
        }
        else {
            board[x][y].setCover(true);
        }
    }

    public void switchFlag(int x, int y) {
        Square temp = board[x][y];
        if (!temp.isCover()) {
            return;
        }
        if (temp.checkFlag()) {
            temp.setFlag();
            flags++;
        }
        else {
            temp.setFlag();
            flags--;
        }

    }

    public boolean checkWin() {
        int count = 0;
        int numOpenSquares = width * height - numBombs;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!board[i][j].isCover() && !board[i][j].isBomb()) {
                    count++;
                }
            }
        }

        if (count == numOpenSquares) {
            gameOver = true;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Square recieveSquare(int x, int y) {
        return board[x][y];
    }

    public void reset() {
        board = new Square[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = new Square(false, false, false);
            }
        }
        insertBombs();
        flags = numBombs;
        gameOver = false;
    }

    //IO
    public void loadFile() {
        Path file = Paths.get("src/Tester.txt");
        try {

            int lineStarter = 1;
            List<String> storage = Files.readAllLines(file);
            String[] initv = storage.getFirst().split(" ");

            int newwidth = Integer.parseInt(initv[0]);
            int newheight = Integer.parseInt(initv[1]);

            for (int i = 0; i < newwidth; i++) {
                for (int j = 0; j < newheight; j++) {
                    String[] cellData = storage.get(lineStarter).split(" ");

                    boolean hasBomb = false;
                    boolean isCovered = true;
                    boolean isFlagged = false;
                    if (cellData[0].equals("1")) {
                        hasBomb = true;
                    } else if (cellData[1].equals("1")) {
                        isCovered = false;
                    } else if (cellData[2].equals("1")) {
                        isFlagged = true;
                    }

                    Square sq = new Square(hasBomb, isFlagged, !isCovered);

                    board[i][j] = sq;
                    lineStarter++;
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't load game " + e.getMessage());
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Couldn't parse saved game " + e.getMessage());
        }
    }

    public void saveToFile() {
        List<String> storage = new ArrayList<>();
        storage.add(width + " " + height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int bombBool = 0;
                int coveredBool = 0;
                int flaggedBool = 0;
                Square sq = board[i][j];
                if (sq.isBomb()) {
                    bombBool = 1;
                }
                if (!sq.isCover()) {
                    coveredBool = 1;
                }
                if (sq.checkFlag()) {
                    flaggedBool = 1;
                }

                storage.add(bombBool + " "  + coveredBool + " " + flaggedBool);
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Tester.txt"));
            for (String s : storage) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Couldn't save the game: " + e.getMessage());
        }
    }

}