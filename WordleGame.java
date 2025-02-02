import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class WordleGame {
    private static final int WORD_LENGTH = 5;
    private static final int MAX_ATTEMPTS = 6;
    private List<String> words;
    private String targetWord;
    private int attempts;
    private JButton[][] grid;
    private JTextField inputField;
    private JLabel attemptsLabel;

    public WordleGame() {
        loadWords();
        startNewGame();
        createUI();
    }

    private void loadWords() {
        try {
            words = Files.readAllLines(Paths.get("words.txt"));
            words.replaceAll(String::toUpperCase);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading words file!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void startNewGame() {
        Random rand = new Random();
        targetWord = words.get(rand.nextInt(words.size()));
        attempts = 0;
        if (grid != null) {
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                for (int j = 0; j < WORD_LENGTH; j++) {
                    grid[i][j].setText("");
                    grid[i][j].setBackground(new Color(60, 60, 60));
                }
            }
        }
        if (attemptsLabel != null) attemptsLabel.setText("Attempts: " + attempts + "/" + MAX_ATTEMPTS);
    }

    private void createUI() {
        JFrame frame = new JFrame("Wordle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        // Game Grid
        JPanel gridPanel = new JPanel(new GridLayout(MAX_ATTEMPTS, WORD_LENGTH, 5, 5));
        gridPanel.setBackground(new Color(30, 30, 30));
        grid = new JButton[MAX_ATTEMPTS][WORD_LENGTH];

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            for (int j = 0; j < WORD_LENGTH; j++) {
                grid[i][j] = new JButton();
                grid[i][j].setFont(new Font("Arial", Font.BOLD, 22));
                grid[i][j].setBackground(new Color(60, 60, 60));
                grid[i][j].setForeground(Color.WHITE);
                grid[i][j].setFocusPainted(false);
                grid[i][j].setEnabled(false);
                grid[i][j].setText("");
                gridPanel.add(grid[i][j]);
            }
        }

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(50, 50, 50));

        inputField = new JTextField(WORD_LENGTH);
        inputField.setFont(new Font("Arial", Font.BOLD, 22));
        inputField.setBackground(new Color(70, 70, 70));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setHorizontalAlignment(JTextField.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBackground(new Color(0, 150, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        submitButton.addActionListener(e -> checkWord());

        inputPanel.add(inputField);
        inputPanel.add(submitButton);

        // Attempts Label
        attemptsLabel = new JLabel("Attempts: 0/" + MAX_ATTEMPTS, JLabel.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        attemptsLabel.setForeground(Color.WHITE);

        frame.add(attemptsLabel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void checkWord() {
        String guess = inputField.getText().trim().toUpperCase();
        if (guess.length() != WORD_LENGTH || !words.contains(guess)) {
            shakeInvalidWord();
            JOptionPane.showMessageDialog(null, "Invalid word! Must be " + WORD_LENGTH + " letters and in the dictionary.");
            return;
        }

        attempts++;
        attemptsLabel.setText("Attempts: " + attempts + "/" + MAX_ATTEMPTS);

        // Update the grid with feedback
        for (int i = 0; i < WORD_LENGTH; i++) {
            char guessedChar = guess.charAt(i);
            JButton button = grid[attempts - 1][i];

            if (guessedChar == targetWord.charAt(i)) {
                button.setBackground(new Color(0, 255, 0)); // Green
                button.setText(String.valueOf(guessedChar));
            } else if (targetWord.contains(String.valueOf(guessedChar))) {
                button.setBackground(new Color(255, 255, 0)); // Yellow
                button.setText(String.valueOf(guessedChar));
            } else {
                button.setBackground(new Color(169, 169, 169)); // Gray
                button.setText(String.valueOf(guessedChar));
            }
        }

        if (guess.equals(targetWord)) {
            JOptionPane.showMessageDialog(null, "🎉 Congratulations! You guessed the word: " + targetWord);
            startNewGame();
        } else if (attempts >= MAX_ATTEMPTS) {
            JOptionPane.showMessageDialog(null, "❌ Game Over! The word was: " + targetWord);
            startNewGame();
        }
        inputField.setText("");
    }

    private void shakeInvalidWord() {
        Timer timer = new Timer(50, new ActionListener() {
            private int counter = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter % 2 == 0) {
                    inputField.setLocation(inputField.getLocation().x + 10, inputField.getLocation().y);
                } else {
                    inputField.setLocation(inputField.getLocation().x - 10, inputField.getLocation().y);
                }
                counter++;
                if (counter == 10) {
                    ((Timer) e.getSource()).stop();
                    inputField.setLocation(100, inputField.getLocation().y); // Reset position
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordleGame::new);
    }
}
