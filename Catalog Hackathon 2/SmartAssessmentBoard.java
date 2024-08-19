import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

class StateCapital {
    private String state;
    private String capital;

    public StateCapital(String state, String capital) {
        this.state = state;
        this.capital = capital;
    }

    public String getState() {
        return state;
    }

    public String getCapital() {
        return capital;
    }
}

class QuizController {
    private List<StateCapital> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public QuizController(List<StateCapital> questions) {
        this.questions = questions;
    }

    public StateCapital getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(getCurrentQuestion().getCapital())) {
            score++;
        }
        currentQuestionIndex++;
    }

    public int getScore() {
        return score;
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }
}

public class SmartAssessmentBoard {
    private JFrame frame;
    private JLabel questionLabel;
    private JComboBox<String> answerComboBox;
    private JButton nextButton;
    private JLabel scoreLabel;

    private QuizController controller;

    public SmartAssessmentBoard(QuizController controller) {
        this.controller = controller;
        frame = new JFrame("Smart Assessment Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        questionLabel = new JLabel();
        answerComboBox = new JComboBox<>();
        nextButton = new JButton("Next");
        scoreLabel = new JLabel("Score: 0");

        JPanel panel = new JPanel();
        panel.add(questionLabel);
        panel.add(answerComboBox);
        panel.add(nextButton);
        panel.add(scoreLabel);

        frame.add(panel);
        frame.setVisible(true);

        loadQuestion();
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                if (controller.hasNextQuestion()) {
                    loadQuestion();
                } else {
                    questionLabel.setText("Quiz Complete!");
                    answerComboBox.setEnabled(false);
                    nextButton.setEnabled(false);
                }
                scoreLabel.setText("Score: " + controller.getScore());
            }
        });
    }

    private void loadQuestion() {
        StateCapital question = controller.getCurrentQuestion();
        questionLabel.setText("What is the capital of " + question.getState() + "?");
        answerComboBox.removeAllItems();
        answerComboBox.addItem("Select Capital");
        answerComboBox.addItem(question.getCapital());
        answerComboBox.addItem("Incorrect Capital 1");
        answerComboBox.addItem("Incorrect Capital 2");
    }

    private void checkAnswer() {
        String selectedAnswer = (String) answerComboBox.getSelectedItem();
        controller.checkAnswer(selectedAnswer);
    }

    public static void main(String[] args) {
        List<StateCapital> quizQuestions = Arrays.asList(
                new StateCapital("Andhra Pradesh", "Amaravati"),
                new StateCapital("Bihar", "Patna"),
                new StateCapital("Karnataka", "Bengaluru"),
                new StateCapital("Maharashtra", "Mumbai"),
                new StateCapital("Tamil Nadu", "Chennai")
        );
        QuizController controller = new QuizController(quizQuestions);
        new SmartAssessmentBoard(controller);
    }
}