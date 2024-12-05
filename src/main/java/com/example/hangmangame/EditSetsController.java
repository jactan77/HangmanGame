package com.example.hangmangame;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class EditSetsController extends FormsController {
    private final ArrayList<String> wordsDb = db.getWords(Main.getSelectedCategory());

    @Override
    public void initialize() {
        setupEditMode();
        showWords();
    }

    private void setupEditMode() {
        createButton.setText("Update");
        createButton.setDisable(true);

        createButton.setOnAction(event -> {
            soundEffects.clickEffect();
            addWord();
        });

        categoryName.setText(db.getCategoryName(Main.getSelectedCategory()));
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
        ArrayList<String> newWords = new ArrayList<>();
        for (HBox card : cards) {
            VBox textFieldBox = (VBox) card.getChildren().get(1);
            TextField wordField = (TextField) textFieldBox.getChildren().getFirst();
            String word = wordField.getText().trim();
            newWords.add(word);
        }
        db.updateCategorySet(Main.getSelectedCategory(), newWords);
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
}