package com.example.hangmangame.utils;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

public class HangmanAnimation {
    private final AudioClip animationSound = new AudioClip(getClass().getResource("/com/example/hangmangame/sounds/chalkSound.wav").toString());
    private Pane pane;
    private int currentStep = 0;


    private Line base, pole, beam, rope;
    private Circle head;
    private Line body, leftArm, rightArm, leftLeg, rightLeg;

    public HangmanAnimation(Pane pane) {
        this.pane = pane;
        initializeParts();
    }

    private void initializeParts() {

        base = new Line(50, 500, 350, 500);
        pole = new Line(100, 500, 100, 100);
        beam = new Line(100, 100, 300, 100);
        rope = new Line(300, 100, 300, 150);


        head = new Circle(300, 180, 30, Color.TRANSPARENT);
        head.setStroke(Color.BLACK);


        body = new Line(300, 210, 300, 350);
        leftArm = new Line(300, 250, 230, 290);
        rightArm = new Line(300, 250, 370, 290);
        leftLeg = new Line(300, 350, 250, 450);
        rightLeg = new Line(300, 350, 350, 450);
    }

    public void nextStep() {
        switch (currentStep) {
            case 0 -> addWithScale(base);
            case 1 -> addWithScale(pole);
            case 2 -> addWithScale(beam);
            case 3 -> addWithScale(rope);
            case 4 -> addWithScale(head);
            case 5 -> addWithScale(body);
            case 6 -> addWithScale(leftArm);
            case 7 -> addWithScale(rightArm);
            case 8 -> addWithScale(leftLeg);
            case 9 -> addWithScale(rightLeg);
        }
        currentStep++;
    }
    private void addWithScale(Line part) {
        pane.getChildren().add(part);
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.5), part);
        scale.setFromX(0.1);
        scale.setFromY(0.1);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();
        animationSound.play();
    }

    private void addWithScale(Circle part) {
        pane.getChildren().add(part);
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.5), part);
        scale.setFromX(0.1);
        scale.setFromY(0.1);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();
        animationSound.play();
    }

    public boolean isComplete() {
        return currentStep >= 10;
    }

    public void reset() {
        pane.getChildren().clear();
        currentStep = 0;
    }
}


