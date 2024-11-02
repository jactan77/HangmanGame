package com.example.hangmangame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        loadMenuScene();
    }
    private void loadMenuScene() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene menuScene = new Scene(fxmlLoader.load(), 1600, 900);
        menuScene.getStylesheets().add(Main.class.getResource("menuStyles.css").toExternalForm());
        primaryStage.setTitle("Hangman Game");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }


    public static void switchToGameScene() {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
                Scene gameScene = new Scene(fxmlLoader.load(), 1600, 900);
                gameScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
                primaryStage.setScene(gameScene);
                primaryStage.setTitle("Hangman Game - Play");
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public static void switchToMenuScene() {
        Platform.runLater(() -> {
            try {
                primaryStage.close();
                Main newApp = new Main();
                Stage newStage = new Stage();
                newApp.start(newStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}