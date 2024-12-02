package com.example.hangmangame;

import com.example.hangmangame.utils.Transition;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.util.Objects;

public class Main extends Application {

    private static Stage primaryStage;
    private final Db db = new Db();
    private static int selectedCategory;
    private static int selectedScore;

    public static int getSelectedCategory() {
        return selectedCategory;
    }

    public static void setSelectedCategory(int category) {
        selectedCategory = category;
    }

    public static void setSelectedScore(int score) { selectedScore = score;}

    public static int getSelectedScore() { return selectedScore;}


    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.setResizable(true);
        loadMenuScene();
        db.getConnection();
        if(db.userExists()) System.out.println("User exists");
        else{
            System.out.println("User doesn't exist");
            db.createUser();
        }

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            resizeCurrentScene();
        });
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            resizeCurrentScene();
        });
    }

    private void loadMenuScene() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menuView.fxml"));
        Scene menuScene = new Scene(fxmlLoader.load(), primaryStage.getWidth(), primaryStage.getHeight());
        menuScene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("menuStyles.css")).toExternalForm());
        Platform.runLater(() -> {
            primaryStage.setScene(menuScene);
            primaryStage.show();
        });
    }

    public static void loadCategoryScene() {
        applyFadeTransitionAndLoad("categoryView.fxml");
    }

    public static void loadUserCategoryScene() {
        applyFadeTransitionAndLoad("setsView.fxml");
    }

    public static void switchToGameScene() {
        applyFadeTransitionAndLoad("view.fxml");
    }

    public static void switchToMenuScene() {
        applyFadeTransitionAndLoad("menuView.fxml");
    }

    public static void switchToFormsScene() {
        applyFadeTransitionAndLoad("formsView.fxml");
    }
    public static void switchToEditSettingsScene() {
        applyFadeTransitionAndLoad("editSetsView.fxml");
    }

    private static void applyFadeTransitionAndLoad(String fxmlFile) {
        Platform.runLater(() -> {
            Scene currentScene = primaryStage.getScene();
            if (currentScene != null) {
                Parent currentRoot = currentScene.getRoot();
                FadeTransition fadeOut = Transition.fadeOut(currentRoot, e -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
                        Parent newRoot = fxmlLoader.load();
                        Scene newScene = new Scene(newRoot, primaryStage.getWidth(), primaryStage.getHeight());

                        if (fxmlFile.equals("menuView.fxml")) {
                            newScene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("menuStyles.css")).toExternalForm());
                        }


                        primaryStage.setScene(newScene);
                        Transition.fadeIn(newRoot).play();
                        primaryStage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                fadeOut.play();
            } else {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
                    Parent newRoot = fxmlLoader.load();
                    Scene newScene = new Scene(newRoot, primaryStage.getWidth(), primaryStage.getHeight());

                    if (fxmlFile.equals("menuView.fxml")) {
                        newScene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("menuStyles.css")).toExternalForm());
                    }

                    primaryStage.setScene(newScene);
                    primaryStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void resizeCurrentScene() {
        Scene currentScene = primaryStage.getScene();
        if (currentScene != null) {
            currentScene.setRoot(currentScene.getRoot());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
