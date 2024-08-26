import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class quiz {
    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static ArrayList<Question> questions = new ArrayList<>();
    private static JFrame frame;
    private static JLabel timerLabel;
    private static int timeRemaining;
    private static Timer timer;
    private static JPanel questionPanel;
    private static JPanel timerPanel;

    public static void main(String[] args) {
        questions.add(new Question("What is the capital of India?", new String[]{"Dehradun", "Mumbai", "Delhi", "Chennai"}, "Delhi"));
        questions.add(new Question("Which river is considered the holiest in India?", new String[]{"Brahmaputra", "Yamuna", "Godavari", "Ganges"}, "Ganges"));
        questions.add(new Question("Who was the first Prime Minister of India?", new String[]{"Rajiv Gandhi", "Gandhi", "Jawaharlal Nehru", "Indira Gandhi"}, "Jawaharlal Nehru"));
        questions.add(new Question("What is the national flower of India?", new String[]{"Rose", "Lily", "Lotus", "Jasmine"}, "Lotus"));
        questions.add(new Question("Which Indian city is known as the Silicon Valley of India?", new String[]{"Bangalore", "Hyderabad", "Pune", "Chennai"}, "Bangalore"));
        questions.add(new Question("What is the national sport of India?", new String[]{"Cricket", "Hockey", "Football", "Badminton"}, "Hockey"));
        questions.add(new Question("Which Indian state is known for its tea gardens?", new String[]{"Assam", "West Bengal", "Kerala", "Himachal Pradesh"}, "Assam"));
        questions.add(new Question("Which Indian leader is known for his role in the Indian independence movement and his philosophy of non-violence?", new String[]{"Mahatma Gandhi", "Jawaharlal Nehru", "Subhas Chandra Bose", "Bhagat Singh"}, "Mahatma Gandhi"));
        questions.add(new Question("Which Indian festival is known as the Festival of Lights?", new String[]{"Eid", "Holi", "Diwali", "Navratri"}, "Diwali"));
        questions.add(new Question("Which is the largest state by area in India?", new String[]{"Rajasthan", "Madhya Pradesh", "Uttar Pradesh", "Maharashtra"}, "Rajasthan"));

        frame = new JFrame("India Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);

        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());

        timerPanel = new JPanel();
        timerPanel.setBackground(Color.LIGHT_GRAY);
        timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        timerLabel = new JLabel("Time: 15");
        timerLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        timerLabel.setOpaque(true);
        timerLabel.setBackground(Color.GREEN);

        timerPanel.add(timerLabel);
        frame.add(timerPanel, BorderLayout.NORTH);

        questionPanel = new JPanel();
        questionPanel.setBackground(Color.LIGHT_GRAY);
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        frame.add(questionPanel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        startQuiz();
    }

    public static void startQuiz() {
        displayQuestion(questions.get(currentQuestionIndex));
    }

    public static void displayQuestion(Question q) {
        questionPanel.removeAll();

        JLabel questionLabel = new JLabel(q.getQuestion());
        questionLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.add(questionLabel);

        ButtonGroup group = new ButtonGroup();
        for (String option : q.getOptions()) {
            JRadioButton button = new JRadioButton(option);
            button.setActionCommand(option);
            button.setBackground(Color.LIGHT_GRAY);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            group.add(button);
            questionPanel.add(button);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Times New Roman", Font.BOLD, 28));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {  
            String userAnswer = group.getSelection().getActionCommand();
            submitAnswer(userAnswer, q);
            moveToNextQuestion();
        });
        questionPanel.add(submitButton);

        frame.revalidate();
        frame.repaint();

        startTimer();
    }

    public static void startTimer() {
        timeRemaining = 15;
        updateTimerLabel();
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                timeRemaining--;
                updateTimerLabel();
                if (timeRemaining <= 0) {
                    timer.cancel();
                    moveToNextQuestion();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    private static void updateTimerLabel() {
        timerLabel.setText("Time: " + timeRemaining);
        if (timeRemaining > 10) {
            timerLabel.setBackground(Color.GREEN);
        } else if (timeRemaining > 5) {
            timerLabel.setBackground(Color.YELLOW);
        } else {
            timerLabel.setBackground(Color.RED);
        }
    }

    public static void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion(questions.get(currentQuestionIndex));
        } else {
            showResults();
        }
    }

    public static void submitAnswer(String userAnswer, Question q) {
        if (q.isCorrect(userAnswer)) {
            score++;
            playSound("correct.wav");
            JOptionPane.showMessageDialog(frame, "Great job!", "Correct", JOptionPane.INFORMATION_MESSAGE);
        } else {
            playSound("incorrect.wav");
            JOptionPane.showMessageDialog(frame, "Wrong!", "Incorrect", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showResults() {
        if (timer != null) {
            timer.cancel();
        }
        frame.remove(timerPanel);  

        questionPanel.removeAll();
        questionPanel.setLayout(new GridBagLayout());

        String resultText = "Your final score is: " + score + " out of " + questions.size();
        String feedbackText;

        if (score == 10) {
            feedbackText = "Congratulations! Keep it up!";
        } else if (score >= 7) {
            feedbackText = "Well done! You're almost there!";
        } else {
            feedbackText = "Try harder next time!";
        }

        JLabel resultLabel = new JLabel(resultText);
        resultLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel feedbackLabel = new JLabel(feedbackText);
        feedbackLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        questionPanel.add(resultLabel, gbc);

        gbc.gridy = 1;
        questionPanel.add(feedbackLabel, gbc);

        frame.revalidate();
        frame.repaint();
    }

    public static void playSound(String soundFile) {
        // Implement sound playing logic using javax.sound.sampled package
    }
}

class Question {
    private String questionText;
    private String[] options;
    private String correctAnswer;

    public Question(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equals(answer);
    }
}
