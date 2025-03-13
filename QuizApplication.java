import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class QuizApplication extends JFrame implements ActionListener {
    private String[] questions = {
        "What is the oldest language in the world?", 
        "What type of battery are used in Smart Phones?", 
        "What is 5 + 7?"
    };
    
    private String[][] options = {
        {"Tamil", "hindi", "Sanskrit", "Chinese"},
        {"lithium ion", "alkaline", "Zinc carbon ", "Lithium polymer"},
        {"10", "11", "12", "13"}
    };
    
    private int[] correctAnswers = {0, 0, 2}; // Index of correct answers in options
    private int currentQuestion = 0;
    private int score = 0;
    private int timeLeft = 10; // Timer for each question in seconds

    private JLabel questionLabel, timerLabel, scoreLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;
    private JButton nextButton;
    private Timer timer;

    public QuizApplication() {
        setTitle("Quiz Application");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        timerLabel = new JLabel("Time left: " + timeLeft + "s");
        add(timerLabel);
        
        questionLabel = new JLabel();
        add(questionLabel);
        
        optionButtons = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            add(optionButtons[i]);
        }

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        add(nextButton);

        scoreLabel = new JLabel("Score: 0");
        add(scoreLabel);

        loadQuestion();
        startTimer();

        setVisible(true);
    }

    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText(questions[currentQuestion]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(options[currentQuestion][i]);
            }
            timeLeft = 10; // Reset time for the question
            timerLabel.setText("Time left: " + timeLeft + "s");
        } else {
            showFinalScore();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time left: " + timeLeft + "s");
                } else {
                    checkAnswer();
                }
            }
        }, 1000, 1000);
    }

    private void checkAnswer() {
        timer.cancel(); // Stop the timer

        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected() && i == correctAnswers[currentQuestion]) {
                score += 10;
                break;
            }
        }
        
        currentQuestion++;
        scoreLabel.setText("Score: " + score);
        if (currentQuestion < questions.length) {
            optionGroup.clearSelection();
            loadQuestion();
            startTimer(); // Restart timer for next question
        } else {
            showFinalScore();
        }
    }

    private void showFinalScore() {
        JOptionPane.showMessageDialog(this, "Quiz Over! Your final score is: " + score);
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkAnswer();
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}
