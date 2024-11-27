package com.example.hangmangame;

import com.example.hangmangame.utils.RandomColors;
import com.example.hangmangame.utils.UISoundEffects;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FormsController {
    @FXML
    public Button backButton;
    @FXML
    public Button addWordButton;
    @FXML
    public VBox cardContainer; // Add this to your FXML
    @FXML
    public Button createButton;
    @FXML
    public TextField categoryName;
    public   final Db  db = new Db();
    public  final UISoundEffects soundEffects = new UISoundEffects();
    public int currentCard = 0;
    public final List<HBox> cards = new ArrayList<>();


    public void initialize() {

        createNewCard();
        addWordButton.setOnAction(event -> {
            soundEffects.clickEffect();
            createNewCard();});
        backButton.setOnAction(event -> {
            soundEffects.clickEffect();
            Main.loadUserCategoryScene()
            ;});
        createButton.setOnAction(event -> {
            soundEffects.clickEffect();
            addWord();

        });
    }


    public void createNewCard() {
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
        termField.setPromptText("TERM");
        termField.setStyle("-fx-background-color: #1E1E2E; -fx-text-fill: white;");


        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setStyle("-fx-background-color: #FF4444; -fx-text-fill: white;");

        deleteButton.setOnAction(event -> {
            cardContainer.getChildren().remove(cardBox);
            cards.remove(cardBox);
            renumberCards();

        });
        VBox buttonBox = new VBox();
        buttonBox.getChildren().add(deleteButton);


        textFieldBox.getChildren().add(termField);
        cardBox.getChildren().addAll(numberLabel, textFieldBox, buttonBox);

        // Add to container and list
        cardContainer.getChildren().add(cardBox);
        cards.add(cardBox);
    }
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
    }
    public void addWord() {
        String category = categoryName.getText().trim();

        if (cards.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No words available. Please add some words.");
            return;
        }


        if (category.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Category name cannot be empty.");
            return;
        }


        if (db.categoryExists(category)) {
            showAlert(Alert.AlertType.ERROR, "Category already exists.");
            return;
        }
        if(!isValidWord()){
             return;
        }


        db.addCategory(category);


        for (HBox card : cards) {

            VBox textFieldBox = (VBox) card.getChildren().get(1);
            TextField wordField = (TextField) textFieldBox.getChildren().getFirst();
            String word = wordField.getText().trim();
            db.addWord(word, db.getCategoryId(category.toLowerCase()));
        }

        showAlert(Alert.AlertType.INFORMATION, "Category and words added successfully!");
        Main.loadUserCategoryScene();
    }
    public boolean isValidWord() {
        for (int i = 0; i < cards.size(); i++) {
            HBox card = cards.get(i);
            VBox textFieldBox = (VBox) card.getChildren().get(1);
            TextField wordField = (TextField) textFieldBox.getChildren().getFirst();
            String word = wordField.getText().trim();


            if (word.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Word cannot be empty. Please provide input.");
                return false;
            }
            if (word.matches(".*\\d.*")) {
                showAlert(Alert.AlertType.ERROR, "Word contains digits. Please provide valid input.");
                return false;
            }


            for (int j = i + 1; j < cards.size(); j++) {
                HBox card2 = cards.get(j);
                VBox textFieldBox2 = (VBox) card2.getChildren().get(1);
                TextField wordField2 = (TextField) textFieldBox2.getChildren().getFirst();
                String word2 = wordField2.getText().trim();

                if (word.equalsIgnoreCase(word2)) { 
                    showAlert(Alert.AlertType.ERROR, "Word: \"" + word + "\" already exists.");
                    return false;
                }
            }
        }
        return true;
    }


    public void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }


}




