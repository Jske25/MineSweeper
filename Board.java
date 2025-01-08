import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JPanel {

    public static final int BWIDTH = 540;
    public static final int BHEIGHT = 540;
    public static final int SIZE = 60;
    private GameSetup gamesetup;
    private JLabel input;


    public Board(JLabel init) {

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2), // Outer border
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Inner padding
        ));
        setBackground(Color.LIGHT_GRAY); // Changed background color for distinction
        setFocusable(true);
        int size = BWIDTH / SIZE;
        this.gamesetup = new GameSetup(size, size, 10);
        this.input = init;
        gamesetup.gameCreate();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() * 9 / BWIDTH;
                int y = e.getY() * 9 / BHEIGHT;

                if (SwingUtilities.isRightMouseButton(e)) {
                    gamesetup.switchFlag(x, y);
                } else if (SwingUtilities.isLeftMouseButton(e) && gamesetup.recieveSquare(x, y).isCover()) {
                    gamesetup.uncover(x, y);
                }
                changeDisplay();
                repaint();
            }
        });
    }

    public GameSetup getGamesetup() {
        return gamesetup;
    }

    public void gameReset() {
        gamesetup.reset();
        input.setText("Uncover all Squares without a BOMB " + "Flag Count: " + gamesetup.getFlags());
        repaint();
        requestFocusInWindow();
    }

    private void changeDisplay() {
        if (gamesetup.isGameOver()) {
            input.setText("YOU LOST, GAME OVER");
        } else if (gamesetup.checkWin()) {
            input.setText("YOU WIN!!!!! CONGRATS");
        }
        else {
            input.setText("Uncover all Squares without a BOMB " + "Flag Count: " + gamesetup.getFlags());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BWIDTH, BHEIGHT);
    }

    @Override
    public void paintComponent(Graphics gpx) {
        super.paintComponent(gpx);

        gpx.setFont(new Font("SansSerif", Font.BOLD, 20));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Square temp = gamesetup.recieveSquare(i, j);
                int placed = gamesetup.countAdjacentBombs(i, j);
                if (!temp.isCover()) {
                    if (placed > 0) {
                        gpx.setColor(Color.BLACK);
                        gpx.drawString(Integer.toString(placed), i * SIZE + 26, j * SIZE + 38);
                    }
                }
                if (temp.checkFlag()) {
                    gpx.setColor(Color.RED);
                    gpx.fillRect(i * SIZE, j * SIZE, SIZE, SIZE);
                } else if (!temp.isCover()) {
                    if (temp.isBomb()) {
                        gpx.setColor(Color.BLACK);
                        gpx.fillRect(i * SIZE, j * SIZE, SIZE, SIZE);
                    }
                } else {
                    gpx.setColor(Color.GRAY);
                    gpx.fillRect(i * SIZE, j * SIZE, SIZE, SIZE);
                }
            }
        }
        for (int i = 0; i <= 9; i++) {
            int x = i * SIZE; gpx.setColor(Color.BLACK); gpx.drawLine(x, 0, x, BHEIGHT);
        }
        for (int j = 0; j <= 9; j++) {
            int y = j * SIZE; gpx.setColor(Color.BLACK); gpx.drawLine(0, y, BWIDTH, y);
        }
    }

}