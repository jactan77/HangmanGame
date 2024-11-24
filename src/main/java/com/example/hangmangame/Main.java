package com.example.hangmangame;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

public class Main extends Application {

    private static Stage primaryStage;
    private final Db db = new Db();
    private static int selectedCategory;
    private static String selectedStyle;
    public static int getSelectedCategory() {
        return selectedCategory;
    }

    public static void setSelectedCategory(int category) {
        selectedCategory = category;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.setResizable(true);
        loadMenuScene();
        db.getConnection();

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {resizeCurrentScene();});
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {resizeCurrentScene();});
    }

    private void loadMenuScene() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene menuScene = new Scene(fxmlLoader.load(), primaryStage.getWidth(),  primaryStage.getHeight());
        menuScene.getStylesheets().add(Main.class.getResource("menuStyles.css").toExternalForm());
        Platform.runLater(() -> {
            primaryStage.setScene(menuScene);
            primaryStage.show();
        });
    }
    public static void loadCategoryScene(){
        Platform.runLater(() -> {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("categoryView.fxml"));
            Scene categoryScene = new Scene(fxmlLoader.load(), primaryStage.getWidth(),  primaryStage.getHeight());
            primaryStage.setScene(categoryScene);
            primaryStage.show();}
            catch(Exception e){  e.printStackTrace();}
    });

    }
    public static void loadUserCategoryScene(){
        Platform.runLater(() -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("setsView.fxml"));
                Scene categoryScene = new Scene(fxmlLoader.load(), primaryStage.getWidth(),  primaryStage.getHeight());
                primaryStage.setScene(categoryScene);
                primaryStage.show();}
            catch(Exception e){  e.printStackTrace();}
        });

    }

    public static void switchToGameScene() {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
                Scene gameScene = new Scene(fxmlLoader.load(), primaryStage.getWidth(),  primaryStage.getHeight());
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
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
                Scene menuScene = new Scene(fxmlLoader.load(), primaryStage.getWidth(),  primaryStage.getHeight());
                menuScene.getStylesheets().add(Main.class.getResource("menuStyles.css").toExternalForm());
                primaryStage.setScene(menuScene);
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public static void switchToFormsScene() {
        Platform.runLater(() -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("formsView.fxml"));
                Scene formScene = new Scene(fxmlLoader.load(), primaryStage.getWidth(),  primaryStage.getHeight());
                primaryStage.setScene(formScene);
                primaryStage.show();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }



    public static void resizeCurrentScene(){
        Scene currentScene = primaryStage.getScene();
        if(currentScene != null){
            currentScene.setRoot(currentScene.getRoot());
        }
    }


    public static void main(String[] args) {
        launch();
    }
}