package com.example.hangmangame;

import com.example.hangmangame.utils.UISoundEffects;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class FormsController {
    @FXML protected Button deleteCards;
    @FXML protected Button backButton;
    @FXML protected Button addWordButton;
    @FXML protected VBox cardContainer;
    @FXML protected Button createButton;
    @FXML protected TextField categoryName;
    @FXML protected ColorPicker  categoryColor;

    protected final Db db = new Db();
    protected final UISoundEffects soundEffects = new UISoundEffects();
    protected int currentCard = 0;
    protected final List<HBox> cards = new ArrayList<>();
    protected String colorHex;
    @FXML
    public void initialize() {
        setupInitialCard();
        setupButtonHandlers();
        setCategoryColor();
    }

    protected void setupInitialCard() {
        createNewCard();
        createButton.setDisable(true);
    }

    protected void setupButtonHandlers() {
        addWordButton.setOnAction(event -> {
            soundEffects.clickEffect();
            createNewCard();
        });

        backButton.setOnAction(event -> {
            soundEffects.clickEffect();
            Main.loadUserCategoryScene();
        });

        createButton.setOnAction(event -> {
            soundEffects.clickEffect();
            addWord();
        });

        deleteCards.setOnAction(event -> {
            soundEffects.clickEffect();
            deleteAllCards();
        });
    }

    protected void createNewCard(String initialText) {
        currentCard++;

        HBox cardBox = new HBox();
        cardBox.setSpacing(10);
        cardBox.getStyleClass().add("card-box");
        cardBox.setStyle(getCardStyle(currentCard));

        Label numberLabel = new Label(String.valueOf(currentCard));
        numberLabel.setStyle("-fx-text-fill: white;");

        VBox textFieldBox = createTextFieldBox(initialText);
        Button deleteButton = createDeleteButton(cardBox);
        VBox buttonBox = new VBox(deleteButton);

        cardBox.getChildren().addAll(numberLabel, textFieldBox, buttonBox);
        cardContainer.getChildren().add(cardBox);
        cards.add(cardBox);

        updateDeleteButtonState();
    }

    protected void createNewCard() {
        createNewCard(null);
    }

    protected VBox createTextFieldBox(String initialText) {
        VBox textFieldBox = new VBox();
        textFieldBox.setSpacing(5);
        HBox.setHgrow(textFieldBox, Priority.ALWAYS);

        TextField termField = new TextField();
        termField.setPromptText("TERM");
        if (initialText != null) {
            termField.setText(initialText);
        }
        termField.setStyle("-fx-background-color: #1E1E2E; -fx-text-fill: white;");
        termField.textProperty().addListener((observable, oldValue, newValue) ->
                createButton.setDisable(false));

        textFieldBox.getChildren().add(termField);
        return textFieldBox;
    }

    protected Button createDeleteButton(HBox cardBox) {
        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setStyle("-fx-background-color: #FF4444; -fx-text-fill: white;");

        deleteButton.setOnAction(event -> {
            soundEffects.clickEffect();
            createButton.setDisable(false);
            cardContainer.getChildren().remove(cardBox);
            cards.remove(cardBox);
            renumberCards();
        });

        return deleteButton;
    }

    protected void updateDeleteButtonState() {
        boolean disableDelete = cards.size() <= 1;

        for (int i = 0; i < cards.size(); i++) {
            HBox card = cards.get(i);
            VBox buttonBox = (VBox) card.getChildren().get(2);
            Button deleteButton = (Button) buttonBox.getChildren().getFirst();
            deleteButton.setDisable(i == 0 ? cards.size() <= 1 : disableDelete);
        }
    }

    protected void renumberCards() {
        for (int i = 0; i < cards.size(); i++) {
            HBox card = cards.get(i);
            Label numberLabel = (Label) card.getChildren().get(0);
            numberLabel.setText(String.valueOf(i + 1));
            card.setStyle(getCardStyle(i + 1));
        }
        currentCard = cards.size();
        updateDeleteButtonState();
    }

    protected String getCardStyle(int cardNumber) {
        return "-fx-background-color: #2E2E3E; " +
                "-fx-padding: 10; " +
                "-fx-border-width: 1; " +
                "-fx-border-color: " + (cardNumber == 1 ? "#FFD700" : "#525252") + ";";
    }

    protected void addWord() {
        if (!validateInput()) {
            return;
        }
        processWords();
        showSuccessMessage();
    }

    protected boolean validateInput() {
        String category = categoryName.getText().trim();

        if (cards.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No words available. Please add some words.");
            return false;
        }

        if (category.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Category name cannot be empty.");
            return false;
        }

        return isValidWord();
    }

    protected void processWords() {
        String category = categoryName.getText().trim();
        db.addCategory(category,colorHex);

        for (HBox card : cards) {
            VBox textFieldBox = (VBox) card.getChildren().get(1);
            TextField wordField = (TextField) textFieldBox.getChildren().getFirst();
            String word = wordField.getText().trim();
            db.addWord(word, db.getCategoryId(category.toLowerCase()));
        }
    }

    protected void showSuccessMessage() {
        showAlert(Alert.AlertType.INFORMATION, "Category and words added successfully!");
        Main.loadUserCategoryScene();
    }

    protected void deleteAllCards() {
        cards.clear();
        cardContainer.getChildren().clear();
        currentCard = 0;
        createButton.setDisable(true);
    }

    protected boolean isValidWord() {
        for (int i = 0; i < cards.size(); i++) {
            HBox card = cards.get(i);
            VBox textFieldBox = (VBox) card.getChildren().get(1);
            TextField wordField = (TextField) textFieldBox.getChildren().getFirst();
            String word = wordField.getText().trim();

            if (!validateWordContent(word)) {
                return false;
            }

            if (!validateWordUniqueness(word, i)) {
                return false;
            }
        }
        return true;
    }

    protected boolean validateWordContent(String word) {
        if (word.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Word cannot be empty. Please provide input.");
            return false;
        }
        if (word.matches(".*\\d.*")) {
            showAlert(Alert.AlertType.ERROR, "Word contains digits. Please provide valid input.");
            return false;
        }
        return true;
    }

    protected boolean validateWordUniqueness(String word, int currentIndex) {
        for (int j = currentIndex + 1; j < cards.size(); j++) {
            HBox card2 = cards.get(j);
            VBox textFieldBox2 = (VBox) card2.getChildren().get(1);
            TextField wordField2 = (TextField) textFieldBox2.getChildren().getFirst();
            String word2 = wordField2.getText().trim();

            if (word.equalsIgnoreCase(word2)) {
                showAlert(Alert.AlertType.ERROR, "Word: \"" + word + "\" already exists.");
                return false;
            }
        }
        return true;
    }
    protected void setCategoryColor() {
        categoryColor.setValue(Color.web("#7aa2f7"));
        categoryColor.setStyle("-fx-background-color: #24283b;");
        colorHex = "#7aa2f7";

        categoryColor.valueProperty().addListener((observable, oldValue, newValue) -> {
            colorHex = String.format("#%02X%02X%02X",
                    (int) (newValue.getRed() * 255),
                    (int) (newValue.getGreen() * 255),
                    (int) (newValue.getBlue() * 255));
        });
    }

    protected void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }
}