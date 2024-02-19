package com.example.demo1111;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private int questionNumber = 0;
    private int max = 5;
    private int correctAnswers = 0;

    String[] question = new String[max];
    String[] answer1 = new String[max];
    String[] answer2 = new String[max];
    String[] answer3 = new String[max];

    Label label = new Label();
    Label userAnswer = new Label();

    RadioButton choice1 = new RadioButton();
    RadioButton choice2 = new RadioButton();
    RadioButton choice3 = new RadioButton();

    ToggleGroup answers = new ToggleGroup();

    Button next = new Button("Next");
    RadioButton userAnswered;

    TilePane buttons = new TilePane();
    Button answer = new Button("Answer");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        question[0] = "What's the capital of Nepal?";
        question[1] = "What's the capital of US?";
        question[2] = "What's the capital of Canada?";
        question[3] = "What's the capital of China?";
        question[4] = "What's the capital of India?";

        label.setFont(new Font("Cambria", 26));
        label.setTranslateY(230);
        label.setTranslateX(230);
        label.setText(question[0]);

        HBox answerArea = new HBox();
        answerArea.setTranslateY(330);
        answerArea.setTranslateX(260);
        answerArea.setSpacing(20);

        answer1[0] = "Kathmandu";       // Capital of Nepal
        answer1[1] = "Washington DC";   // Capital of US
        answer1[2] = "Ottawa";          // Capital of Canada
        answer1[3] = "Beijing";         // Capital of China
        answer1[4] = "New Delhi";       // Capital of India

        answer2[0] = "Monaco";
        answer2[1] = "San Marino";
        answer2[2] = "Kaunas";
        answer2[3] = "Copenhagen";
        answer2[4] = "Tampere";

        answer3[0] = "Tirana";
        answer3[1] = "Zagreb";
        answer3[2] = "Vilnius";
        answer3[3] = "Stockholm";
        answer3[4] = "Helsinki";

        choice1.setText(answer1[0]);
        choice2.setText(answer2[0]);
        choice3.setText(answer3[0]);

        choice1.setToggleGroup(answers);
        choice2.setToggleGroup(answers);
        choice3.setToggleGroup(answers);

        answer.setMaxWidth(Double.MAX_VALUE);
        answer.setOnAction(e -> questionAnswered());

        answers.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                userAnswered = (RadioButton) newValue.getToggleGroup().getSelectedToggle();
            }
        });
        userAnswer.setTranslateY(280);
        userAnswer.setTranslateX(360);
        userAnswer.setFont(new Font("Cambria", 16));

        answerArea.getChildren().addAll(choice1, choice2, choice3);

        next.setOnAction(e -> nextQuestion());
        next.setMaxWidth(Double.MAX_VALUE);

        buttons.setTranslateY(400);
        buttons.setTranslateX(280);
        buttons.setHgap(5);
        buttons.getChildren().addAll(next);

        answer.setTranslateX(550);
        answer.setTranslateY(330);

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #FFFFFF;");
        Scene scene = new Scene(pane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        pane.getChildren().addAll(label, answerArea, buttons, answer, userAnswer);
    }

    public void questionAnswered() {
        if (userAnswered.equals(choice1)) {
            userAnswer.setText("Correct");
            correctAnswers++;
        } else {
            userAnswer.setText("Wrong answer");
        }
        disableAnswering();
    }

    public void nextQuestion() {
        if (questionNumber == max - 1) {
            displayResult();
        } else {
            questionNumber++;
            label.setText(question[questionNumber]);
            choice1.setText(answer1[questionNumber]);
            choice2.setText(answer2[questionNumber]);
            choice3.setText(answer3[questionNumber]);
            userAnswer.setText(" ");
            enableAnswering();
        }
    }

    private void disableAnswering() {
        choice1.setDisable(true);
        choice2.setDisable(true);
        choice3.setDisable(true);
        answer.setDisable(true);
    }

    private void enableAnswering() {
        choice1.setDisable(false);
        choice2.setDisable(false);
        choice3.setDisable(false);
        answer.setDisable(false);
    }

    private void displayResult() {
        Label resultLabel = new Label("You have answered all questions.\nYou answered " + correctAnswers + " questions correctly.\nDo you want to play again or end?");
        Button replayButton = new Button("Play again");
        Button endButton = new Button("End game");

        replayButton.setOnAction(e -> {
            questionNumber = 0;
            correctAnswers = 0;
            label.setText(question[0]);
            choice1.setText(answer1[0]);
            choice2.setText(answer2[0]);
            choice3.setText(answer3[0]);
            userAnswer.setText("");
            enableAnswering();
            Stage stage = (Stage) replayButton.getScene().getWindow();
            stage.close();
            nextQuestion(); // Start the quiz again
        });

        endButton.setOnAction(e -> Platform.exit());

        VBox resultPane = new VBox(10);
        resultPane.getChildren().addAll(resultLabel, replayButton, endButton);
        resultPane.setTranslateX(200);
        resultPane.setTranslateY(150);

        Stage stage = new Stage();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #FFFFFF;");
        Scene scene = new Scene(pane, 600, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        pane.getChildren().addAll(resultPane);
    }
}
