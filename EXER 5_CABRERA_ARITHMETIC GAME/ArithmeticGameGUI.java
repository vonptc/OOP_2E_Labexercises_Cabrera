import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;


public class ArithmeticGameGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

/* ------------------- LOGIN FRAME ------------------- */
class LoginFrame extends JFrame implements ActionListener {
    private final JTextField usernameField = new JTextField(15);
    private final JButton loginButton = new JButton("Login");

    public LoginFrame() {
        super("Arithmetic Challenge - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Welcome to Arithmetic Challenge!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.add(new JLabel("Enter Username: "));
        inputPanel.add(usernameField);

        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setBackground(new Color(75, 175, 230));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);

        panel.add(title);
        panel.add(inputPanel);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose(); // close login window
        new GameFrame(username);
    }
}

/* ------------------- GAME FRAME ------------------- */
class GameFrame extends JFrame implements ActionListener {
    private final Random random = new Random();
    private int operand1, operand2, correctAnswer, correctCount = 0, incorrectCount = 0, totalGames = 0;
    private char operator;
    private final String username;

    // File for saving user scores
    private static final String SCORE_FILE = "scores.txt";

    // Colors & Fonts
    private static final Color PRIMARY = new Color(75, 175, 230);
    private static final Color GREEN = new Color(0, 150, 0);
    private static final Color RED = new Color(200, 0, 0);
    private static final Color BG = new Color(245, 245, 245);
    private static final Font BIG_FONT = new Font("SansSerif", Font.BOLD, 48);
    private static final Font SCORE_FONT = new Font("Monospaced", Font.BOLD, 36);

    // UI Components
    private final JLabel num1 = new JLabel("?", SwingConstants.CENTER);
    private final JLabel op = new JLabel("+", SwingConstants.CENTER);
    private final JLabel num2 = new JLabel("?", SwingConstants.CENTER);
    private final JTextField answer = new JTextField(5);
    private final JLabel feedback = new JLabel("Enter your answer above.", SwingConstants.CENTER);
    private final JLabel correctLbl = new JLabel("0", SwingConstants.CENTER);
    private final JLabel incorrectLbl = new JLabel("0", SwingConstants.CENTER);
    private final JLabel totalLbl = new JLabel("0", SwingConstants.CENTER);
    private final JLabel userLbl;
    private final JButton submitBtn = new JButton("Submit");
    private final JButton resetBtn = new JButton("New Game / Reset");
    private final JButton logoutBtn = new JButton("Logout");

    private final JRadioButton[] levels = {
        new JRadioButton("Level 1 (1–100)", true),
        new JRadioButton("Level 2 (101–500)"),
        new JRadioButton("Level 3 (501–1000)")
    };
    private final JRadioButton[] ops = {
        new JRadioButton("Mixed", true),
        new JRadioButton("Addition"),
        new JRadioButton("Subtraction"),
        new JRadioButton("Multiplication"),
        new JRadioButton("Division")
    };

    public GameFrame(String username) {
        super("Arithmetic Challenge");
        this.username = username;
        loadUserScore();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG);
        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));

        userLbl = new JLabel("Player: " + username + "   |   Total Games: " + totalGames, SwingConstants.CENTER);
        userLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        userLbl.setBorder(new EmptyBorder(5, 5, 5, 5));

        add(userLbl, BorderLayout.NORTH);
        add(createLeftPanel(), BorderLayout.WEST);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);
        add(feedback, BorderLayout.SOUTH);

        feedback.setFont(new Font("SansSerif", Font.ITALIC, 16));

        pack();
        setMinimumSize(new Dimension(900, 480));
        setLocationRelativeTo(null);
        setVisible(true);
        newGame();
    }

    /* ----------- UI Panels ----------- */
    private JPanel createLeftPanel() {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(BG);

        left.add(Box.createVerticalStrut(30));
        left.add(makeTitledPanel("Operations", ops));
        left.add(Box.createVerticalStrut(15));
        left.add(makeTitledPanel("Level", levels));
        left.add(Box.createVerticalGlue());
        return left;
    }

    private JPanel makeTitledPanel(String title, JRadioButton[] buttons) {
        JPanel panel = new JPanel(new GridLayout(buttons.length, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(PRIMARY.darker(), 1),
                title, TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14), PRIMARY.darker()));

        ButtonGroup group = new ButtonGroup();
        for (JRadioButton b : buttons) {
            group.add(b);
            b.addActionListener(e -> generateProblem());
            b.setBackground(Color.WHITE);
            panel.add(b);
        }
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 40));
        center.setBackground(BG);

        for (JLabel lbl : new JLabel[]{num1, op, num2}) {
            lbl.setFont(BIG_FONT);
            lbl.setPreferredSize(new Dimension(100, 80));
        }

        answer.setFont(new Font("Monospaced", Font.PLAIN, 40));
        answer.setHorizontalAlignment(JTextField.CENTER);
        answer.setPreferredSize(new Dimension(120, 80));

        center.add(num1);
        center.add(op);
        center.add(num2);
        center.add(new JLabel("="));
        center.add(answer);
        return center;
    }

    private JPanel createRightPanel() {
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(BG);

        setupButton(submitBtn, new Font("SansSerif", Font.BOLD, 22), PRIMARY, Color.WHITE);
        setupButton(resetBtn, new Font("SansSerif", Font.PLAIN, 14), null, null);
        setupButton(logoutBtn, new Font("SansSerif", Font.PLAIN, 14), null, null);

        correctLbl.setFont(SCORE_FONT);
        incorrectLbl.setFont(SCORE_FONT);
        totalLbl.setFont(SCORE_FONT);
        correctLbl.setForeground(GREEN);
        incorrectLbl.setForeground(RED);
        totalLbl.setForeground(PRIMARY.darker());

        JPanel score = new JPanel(new GridLayout(3, 2, 5, 5));
        score.setBackground(BG);
        score.add(new JLabel("Correct:", SwingConstants.CENTER));
        score.add(correctLbl);
        score.add(new JLabel("Incorrect:", SwingConstants.CENTER));
        score.add(incorrectLbl);
        score.add(new JLabel("Total:", SwingConstants.CENTER));
        score.add(totalLbl);

        right.add(submitBtn);
        right.add(Box.createVerticalStrut(20));
        right.add(new JLabel("SCORE:", SwingConstants.CENTER));
        right.add(score);
        right.add(Box.createVerticalStrut(20));
        right.add(resetBtn);
        right.add(Box.createVerticalStrut(10));
        right.add(logoutBtn);
        right.add(Box.createVerticalGlue());
        return right;
    }

    private void setupButton(JButton btn, Font font, Color bg, Color fg) {
        btn.setFont(font);
        if (bg != null) btn.setBackground(bg);
        if (fg != null) btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(this);
    }

    /* ----------- Game Logic ----------- */
    private void newGame() {
        correctCount = incorrectCount = 0;
        updateScore();
        feedback.setText("New Game Started! Solve the problem.");
        feedback.setForeground(Color.BLACK);
        generateProblem();
    }

    private void generateProblem() {
        int[] bounds = getLevelBounds();
        int min = bounds[0], max = bounds[1];
        operator = getOperator();

        switch (operator) {
            case '+':
                operand1 = rand(min, max);
                operand2 = rand(min, max);
                break;
            case '-':
                operand1 = rand(min, max);
                operand2 = rand(min, operand1);
                break;
            case '*':
                operand1 = rand(min, Math.min(max, 50));
                operand2 = rand(min, Math.min(max, 50));
                break;
            case '/':
                operand2 = rand(min, Math.max(2, max / 10));
                operand1 = operand2 * rand(2, 10);
                break;
        }

        switch (operator) {
            case '+': correctAnswer = operand1 + operand2; break;
            case '-': correctAnswer = operand1 - operand2; break;
            case '*': correctAnswer = operand1 * operand2; break;
            case '/': correctAnswer = operand1 / operand2; break;
        }

        num1.setText(String.valueOf(operand1));
        op.setText(String.valueOf(operator));
        num2.setText(String.valueOf(operand2));
        answer.setText("");
        answer.requestFocusInWindow();
    }

    private int[] getLevelBounds() {
        if (levels[1].isSelected()) return new int[]{101, 500};
        if (levels[2].isSelected()) return new int[]{501, 1000};
        return new int[]{1, 100};
    }

    private char getOperator() {
        if (ops[1].isSelected()) return '+';
        if (ops[2].isSelected()) return '-';
        if (ops[3].isSelected()) return '*';
        if (ops[4].isSelected()) return '/';
        char[] mix = {'+', '-', '*', '/'};
        return mix[random.nextInt(mix.length)];
    }

    private void checkAnswer() {
        try {
            int user = Integer.parseInt(answer.getText().trim());
            totalGames++;
            if (user == correctAnswer) {
                correctCount++;
                feedback.setText("✅ Correct! " + operand1 + " " + operator + " " + operand2 + " = " + correctAnswer);
                feedback.setForeground(GREEN);
            } else {
                incorrectCount++;
                feedback.setText("❌ Incorrect. The answer was " + correctAnswer);
                feedback.setForeground(RED);
            }
            updateScore();
            generateProblem();
        } catch (NumberFormatException ex) {
            feedback.setText("Please enter a valid number.");
            feedback.setForeground(RED);
            answer.setText("");
        }
    }

    private void updateScore() {
        correctLbl.setText(String.valueOf(correctCount));
        incorrectLbl.setText(String.valueOf(incorrectCount));
        totalLbl.setText(String.valueOf(totalGames));
        userLbl.setText("Player: " + username + "   |   Total Games: " + totalGames);
        saveUserScore();
    }

    private int rand(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == submitBtn) checkAnswer();
        else if (src == resetBtn) newGame();
        else if (src == logoutBtn) {
            saveUserScore();
            dispose();
            new LoginFrame();
        }
    }

    /* ----------- Score Persistence ----------- */
    private void loadUserScore() {
        File file = new File(SCORE_FILE);
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 4 && parts[0].equalsIgnoreCase(username)) {
                    correctCount = Integer.parseInt(parts[1]);
                    incorrectCount = Integer.parseInt(parts[2]);
                    totalGames = Integer.parseInt(parts[3]);
                    return;
                }
            }
        } catch (Exception ignored) {}
    }

    private void saveUserScore() {
        Map<String, String> scores = new LinkedHashMap<>();
        File file = new File(SCORE_FILE);

        if (file.exists()) {
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        scores.put(parts[0], line);
                    }
                }
            } catch (Exception ignored) {}
        }

        scores.put(username, username + "," + correctCount + "," + incorrectCount + "," + totalGames);

        try (PrintWriter pw = new PrintWriter(file)) {
            for (String line : scores.values()) pw.println(line);
        } catch (IOException e) {
            System.err.println("Error saving scores: " + e.getMessage());
        }
    }
}
