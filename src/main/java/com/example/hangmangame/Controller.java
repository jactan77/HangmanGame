package com.example.hangmangame;

import com.example.hangmangame.utils.HangmanAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.media.AudioClip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Arrays;
import java.util.Optional;

public class Controller {
    private HangmanAnimation hangmanAnimation;
    @FXML
    private Pane animationPane;

    @FXML
    private ImageView firstImage;
    @FXML
    private HBox wordDisplay;
    @FXML
    private Button resetButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label mistakesCounter;


    @FXML
    private FlowPane letterGrid;
    private String wordToGuess;
    private char[] guessedWord;
    private final int maxMistakes = 10;
    private int mistakes;
    private final AudioClip clickSound = new AudioClip(getClass().getResource("/com/example/hangmangame/sounds/sound.wav").toString());
    public void initialize() {
        setResetButton();
        setExitButton();
        promptForWord();
        displayWordPlaceholders();
        setupLetterButtons();
        hangmanAnimation = new HangmanAnimation(animationPane);
    }

    private void resetGame(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message +"Would you like to restart the entire game?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            Main.restart();
        } else {
            Platform.exit();
        }
    }
    public void setResetButton(){
        resetButton.setOnAction(event -> {
            clickSound.play();
            Main.restart();
        });
    }
    public void setExitButton(){
        exitButton.setOnAction(event -> {
            clickSound.play();
            Platform.exit();
        });
    }


    public boolean containsDigits(String input) {
        for(int i = 0; i < input.length(); i++){
            char a = input.charAt(i);
            if(!Character.isLetter(input.charAt(i))){
                return false;
            }
        }
        return true;
    }
    private void promptForWord() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Word");
        dialog.setHeaderText("Set the word to guess");
        dialog.setContentText("Please enter a word:");

        Optional<String> result = dialog.showAndWait();
        if ((result.isPresent() && !result.get().trim().isEmpty()) && containsDigits(result.get())) {
            wordToGuess = result.get().trim().toUpperCase();
            guessedWord = new char[wordToGuess.length()];
            Arrays.fill(guessedWord,'_');
        } else {
            showAlert("Invalid input. Please restart the game and enter a valid word.");
        }
    }


    private void displayWordPlaceholders() {
        wordDisplay.getChildren().clear();

        for (char letter : guessedWord) {
            Label label = new Label(String.valueOf(letter));
            label.setStyle("-fx-font-size: 24px; -fx-padding: 5;");
            wordDisplay.getChildren().add(label);
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }


    private void setupLetterButtons() {

        for (javafx.scene.Node node : letterGrid.getChildren()) {
            if (node instanceof Button) {
                Button letterButton = (Button) node;
                letterButton.setOnAction(event -> {

                    String letter = letterButton.getText();
                    processGuess(letter.charAt(0));
                    letterButton.setDisable(true);

                });
            }
        }
    }




    private void processGuess(char guessedLetter) {
        boolean correctGuess = false;



        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guessedLetter) {
                guessedWord[i] = guessedLetter;
                displayWordPlaceholders();
                correctGuess = true;
            }
        }

        if (correctGuess) {
            clickSound.play();
            if (isWordFullyGuessed()) {
                resetGame("Congratulations, you won the game.");
            }
        } else {

            mistakesCounter.setText(String.valueOf(++this.mistakes));
            if(!hangmanAnimation.isComplete())
                hangmanAnimation.nextStep();
            if(mistakes >= maxMistakes){
                resetGame("Game Over! You've made too many mistakes.");
            }
        }

    }
    private boolean isWordFullyGuessed(){
        for(char letter: guessedWord){
            if(letter == '_') return false;
        }
        return true;
    }

}