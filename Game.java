import java.awt.*;
import javax.swing.*;

public class Game implements Runnable {

    public void run() {

        JFrame frame = new JFrame("MineSweeper");
        frame.setLocation(300, 300);

        final JPanel top = new JPanel();
        frame.add(top, BorderLayout.NORTH);
        final JButton reset = new JButton("Reset Game");
        top.add(reset);

        final JPanel lower = new JPanel();
        frame.add(lower, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Starting Game...");
        lower.add(status);

        final Board board = new Board(status);
        frame.add(board, BorderLayout.CENTER);

        final JButton instructionsButton = new JButton("Help");
        instructionsButton.addActionListener(e -> showInstructions(frame));
        top.add(instructionsButton);

        reset.addActionListener(e -> board.gameReset());

        JButton loadButton = new JButton("Load Game");
        loadButton.addActionListener(e ->
                board.getGamesetup().loadFile()
        );
        top.add(loadButton);

        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(e -> board.getGamesetup().saveToFile());
        top.add(saveButton);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void showInstructions(JFrame parent) {
        String instructionsText = "<html><body style='width: 250px;'>" +
                "<h1>How to Play Minesweeper</h1>" +
                "<p>Welcome to Minesweeper! Your mission is to uncover all the safe squares " +
                "without setting off any hidden mines.</p>" +
                "<h2>Gameplay Instructions:</h2>" +
                "<ul>" +
                "<li><b>Left Click:</b> Open a square. Be careful – it might contain a mine!</li>" +
                "<li><b>Right Click:</b> Place or remove a flag to mark a suspected mine.</li>" +
                "</ul>" +
                "<h2>Winning the Game:</h2>" +
                "<p>Successfully flag all the mines and uncover the remaining squares to win. Use the numbers on revealed squares " +
                "to determine how many mines are nearby.</p>" +
                "<h2>Tips:</h2>" +
                "<ul>" +
                "<li>Start with corners or edges to uncover more clues.</li>" +
                "<li>Use flags to avoid forgetting mine locations.</li>" +
                "<li>If unsure, trust your instincts and click away!</li>" +
                "</ul>" +
                "</body></html>";
        JOptionPane.showMessageDialog(
                parent, instructionsText, "Minesweeper Help", JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
