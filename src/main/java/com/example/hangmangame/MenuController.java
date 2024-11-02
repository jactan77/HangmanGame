package com.example.hangmangame;
import com.example.hangmangame.utils.UISoundEffects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;



public class MenuController {
    private final UISoundEffects soundEffects = new UISoundEffects();
    @FXML
    private Button startButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;

    public void initialize() {
        startButton.setOnAction(event -> {soundEffects.clickEffect();Main.switchToGameScene();});
        settingsButton.setOnAction(event -> {soundEffects.clickEffect();}); // in process...
        exitButton.setOnAction(event -> {soundEffects.clickEffect();Platform.exit();System.exit(0);});
    }
    }
