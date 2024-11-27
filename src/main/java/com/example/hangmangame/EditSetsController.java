package com.example.hangmangame;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class EditSetsController extends FormsController {
    private final ArrayList<String> wordsDb = db.getWords(Main.getSelectedCategory());
    @Override
    public void initialize() {

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


        createButton.setText("Update");
        createButton.setDisable(true);
        createButton.setOnAction(event -> {
            soundEffects.clickEffect();
            addWord();
        });


        categoryName.setText(db.getCategoryName(Main.getSelectedCategory()));
        categoryName.setEditable(false);
        showWords();
    }

    public void showWords() {
        wordsDb.forEach(this::createNewCard);
    }


    public void createNewCard(String word) {
        currentCard++;

        HBox cardBox = new HBox();
        cardBox.setSpacing(10);
        cardBox.getStyleClass().add("card-box");
        cardBox.setStyle("-fx-background-color: #2E2E3E; " +
                "-fx-padding: 10; " +
                "-fx-border-width: 1; " +
                "-fx-border-color: " + (currentCard == 1 ? "#FFD700" : "#525252") + ";");

        Label numberLabel = new Label(String.valueOf(currentCard));
        numberLabel.setStyle("-fx-text-fill: white;");

        VBox textFieldBox = new VBox();
        textFieldBox.setSpacing(5);
        HBox.setHgrow(textFieldBox, Priority.ALWAYS);

        TextField termField = new TextField();
        termField.setText(word);
        termField.setStyle("-fx-background-color: #1E1E2E; -fx-text-fill: white;");

        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setStyle("-fx-background-color: #FF4444; -fx-text-fill: white;");

        deleteButton.setOnAction(event -> {
            createButton.setDisable(false);
            cardContainer.getChildren().remove(cardBox);
            cards.remove(cardBox);
            renumberCards();
            soundEffects.clickEffect();
        });;

        VBox buttonBox = new VBox();
        buttonBox.getChildren().add(deleteButton);

        textFieldBox.getChildren().add(termField);
        cardBox.getChildren().addAll(numberLabel, textFieldBox, buttonBox);


        cardContainer.getChildren().add(cardBox);
        cards.add(cardBox);
        updateDeleteButtonState();
    }
    @Override
    public void addWord() {
        String category = categoryName.getText().trim();

        if (cards.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No words available. Please add some words.");
            return;
        }

        if(!isValidWord()){
            return;
        }
        ArrayList<String> newWords = new ArrayList<>();
        for (HBox card : cards) {

            VBox textFieldBox = (VBox) card.getChildren().get(1);
            TextField wordField = (TextField) textFieldBox.getChildren().getFirst();
            String word = wordField.getText().trim();
            newWords.add(word);

        }
        db.updateCategorySet(Main.getSelectedCategory(), newWords);

        showAlert(Alert.AlertType.INFORMATION, " Changes applied successfully!");
        Main.loadUserCategoryScene();
    }
    private void updateDeleteButtonState() {
        boolean disableDelete = cards.size() <= 1;

        for (int i = 0; i < cards.size(); i++) {
            HBox card = cards.get(i);
            VBox buttonBox = (VBox) card.getChildren().get(2);
            Button deleteButton = (Button) buttonBox.getChildren().getFirst();
            deleteButton.setDisable(i == 0 ? cards.size() <= 1 : disableDelete);
        }
    }
    @Override
    public void renumberCards() {
        for (int i = 0; i < cards.size(); i++) {
            HBox card = cards.get(i);
            Label numberLabel = (Label) card.getChildren().get(0);
            numberLabel.setText(String.valueOf(i + 1));
            card.setStyle("-fx-background-color: #2E2E3E; " +
                    "-fx-padding: 10; " +
                    "-fx-border-width: 1; " +
                    "-fx-border-color: " + (i == 0 ? "#FFD700" : "#525252") + ";");
        }
        currentCard = cards.size();
        updateDeleteButtonState();
    }


}