package com.example.hangmangame;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


import java.util.ArrayList;

public class EditSetsController extends FormsController {
    private final int selectedCategory = Main.getSelectedCategory();
    private final ArrayList<String> wordsDb = db.getWords(selectedCategory);
    private final ArrayList<String> newWords = new ArrayList<>();
    private final String getCategoryColor = db.getCategoryColor(selectedCategory);
    @Override
    public void initialize() {
        setupEditMode();
        showWords();
        setCategoryColor();
    }

    private void setupEditMode() {
        createButton.setText("Update");
        createButton.setDisable(true);

        createButton.setOnAction(event -> {
            soundEffects.clickEffect();
            addWord();
            if(!colorHex.equals(getCategoryColor)) {
                db.updateCategoryColor(colorHex,selectedCategory);

            }
        });

        categoryName.setText(db.getCategoryName(selectedCategory));
        categoryName.setEditable(false);

        addWordButton.setOnAction(event -> {
            soundEffects.clickEffect();
            createNewCard();
            createButton.setDisable(false);
            updateDeleteButtonState();
        });
        backButton.setOnAction(event -> {
            soundEffects.clickEffect();
            Main.loadUserCategoryScene();
        });
        deleteCards.setOnAction(event -> {
            soundEffects.clickEffect();
            deleteAllCards();
        });
    }

    public void showWords() {
        wordsDb.forEach(this::createNewCard);
    }

    @Override
    protected void processWords() {
        for (HBox card : cards) {
            VBox textFieldBox = (VBox) card.getChildren().get(1);
            TextField wordField = (TextField) textFieldBox.getChildren().getFirst();
            String word = wordField.getText().trim();
            newWords.add(word);
        }

    }
    @Override
    protected void setCategoryColor() {
        categoryColor.setValue(Color.web(getCategoryColor));
        categoryColor.setStyle("-fx-background-color: #24283b;");
        colorHex = getCategoryColor;

        categoryColor.valueProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(false);
            colorHex = String.format("#%02X%02X%02X",
                    (int) (newValue.getRed() * 255),
                    (int) (newValue.getGreen() * 255),
                    (int) (newValue.getBlue() * 255));
        });
    }

    @Override
    protected void showSuccessMessage() {
        showAlert(Alert.AlertType.INFORMATION, "Changes applied successfully!");
        Main.loadUserCategoryScene();
    }

    @Override
    protected boolean validateInput() {
        if (cards.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No words available. Please add some words.");
            return false;
        }
        return isValidWord();
    }
    @Override
    protected void addWord() {
        if (!validateInput()) {
            return;
        }
        processWords();
        if(!wordsDb.containsAll(newWords) || !(wordsDb.size() == newWords.size()))
            db.updateCategorySet(selectedCategory, newWords);

        showSuccessMessage();
    }

}