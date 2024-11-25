package com.example.hangmangame.utils;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Transition {


    public static FadeTransition fadeOut(Node node) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.3);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        return fadeOut;
    }

    public static FadeTransition fadeIn(Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), node);
        fadeIn.setFromValue(0.3);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        return fadeIn;
    }

    // Fade Out transition with callback after the transition finishes
    public static FadeTransition fadeOut(Node node, EventHandler<ActionEvent> onFinished) {
        FadeTransition fadeOut = fadeOut(node);
        fadeOut.setOnFinished(onFinished); // Execute the callback after the fade-out
        return fadeOut;
    }

    public static FadeTransition fadeIn(Node node, EventHandler<ActionEvent> onFinished) {
        FadeTransition fadeIn = fadeIn(node);
        fadeIn.setOnFinished(onFinished);
        return fadeIn;
    }
}
