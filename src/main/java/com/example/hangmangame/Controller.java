package com.example.hangmangame;

import java.util.concurrent.CompletableFuture;
import com.example.hangmangame.utils.HangmanAnimation;
import com.example.hangmangame.utils.UISoundEffects;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;

import java.util.Arrays;
import java.util.Optional;

public class Controller {
    private final Db db = Db.getInstance();
    private HangmanAnimation hangmanAnimation;
    private final UISoundEffects soundEffects = new UISoundEffects();

    @FXML
    public TextFlow categoryFlow;
    @FXML
    private Pane animationPane;
    @FXML
    private HBox wordDisplay;
    @FXML
    private Button resetButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button menuButton;
    @FXML
    private Label mistakesCounter;
    @FXML
    private Label scoreCounter;
    @FXML
    private Label bestScoreCounter;
    @FXML
    private FlowPane letterGrid;
    @FXML
    private ImageView soundIcon;
    @FXML
    private ToggleButton soundToggle;


    private String wordToGuess;
    private char[] guessedWord;
    private final int maxMistakes = 10;
    private int score = Main.getSelectedScore();
    private final int bestScore = db.getBestScore();
    private int mistakes;
    private final int category = Main.getSelectedCategory();
    private boolean isMuted = false;



    public void initialize() {
        resetButton.setOnAction(event -> {soundEffects.clickEffect(); Main.setSelectedScore(0);Main.loadCategoryScene();});
        exitButton.setOnAction(event -> {soundEffects.clickEffect(); Platform.exit();});
        menuButton.setOnAction(event -> {soundEffects.clickEffect(); Main.setSelectedScore(0); Main.switchToMenuScene();});
        setCategoryFlow();
        setSoundToggle();
        getRandomWord(category);
        setupLetterButtons();
        this.hangmanAnimation = new HangmanAnimation(animationPane,soundEffects);
        scoreCounter.setText(String.valueOf(score));
        bestScoreCounter.setText(String.valueOf(bestScore));
    }

    private void setSoundToggle() {
        soundToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            soundEffects.clickEffect();
            isMuted = newValue;
            soundEffects.setMuted(isMuted);

            String imagePath = isMuted ? "Images/speaker-off.png" : "Images/speaker-on.png";
            Image newImage = new Image(getClass().getResource(imagePath).toExternalForm());
            soundIcon.setImage(newImage);
            if (!isMuted) {
                soundEffects.clickEffect();
            }
        });
    }
    private void setCategoryFlow(){
        Label text = (Label) categoryFlow.getChildren().getFirst();
        text.setText("Category: " + db.getCategoryName(category));
    }




    private void getRandomWord(int category) {
        CompletableFuture.supplyAsync(() -> db.getRandomWord(category))
                .thenAcceptAsync(word -> {
                    if (word != null) {
                        wordToGuess = word.toUpperCase();
                        guessedWord = new char[wordToGuess.length()];
                        Arrays.fill(guessedWord, '_');
                        Platform.runLater(this::displayWordPlaceholders);
                    } else {
                        showAlert();
                        Main.switchToMenuScene();
                    }
                });
    }

    private void resetGame(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message + "Would you like to restart the entire game?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            Main.loadCategoryScene();
        } else {
            Main.switchToMenuScene();
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

    private void showFullWord() {
        wordDisplay.getChildren().clear();
        for (char letter : wordToGuess.toCharArray()) {
            Label label = new Label(String.valueOf(letter));
            label.setStyle("-fx-font-size: 24px; -fx-padding: 5;");
            wordDisplay.getChildren().add(label);
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "No word found in the specified category.", ButtonType.OK);
        alert.showAndWait();
    }

    private void setupLetterButtons() {
        for (javafx.scene.Node node : letterGrid.getChildren()) {
            if (node instanceof Button letterButton) {
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
            soundEffects.clickEffect();
            if (isWordFullyGuessed()) {
                scoreCounter.setText(String.valueOf(++this.score));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Correct guess!", ButtonType.OK);
                alert.showAndWait();
                if(score > bestScore)
                    db.updateBestScore(score);
                Main.setSelectedScore(score);
                Main.setSelectedCategory(category);
                Main.switchToGameScene();
            }
        } else {
            mistakesCounter.setText(String.valueOf(++this.mistakes));
            if (!hangmanAnimation.isComplete()) hangmanAnimation.nextStep();
            if (mistakes >= maxMistakes) {
                showFullWord();
                score = 0;
                scoreCounter.setText(String.valueOf(score));
                resetGame("Game Over! You've made too many mistakes.");
            }
        }
    }

    private boolean isWordFullyGuessed() {
        for (char letter : guessedWord) {
            if (letter == '_') return false;
        }
        return true;
    }
}
