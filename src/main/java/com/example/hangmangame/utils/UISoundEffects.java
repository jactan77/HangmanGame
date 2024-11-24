package com.example.hangmangame.utils;
import java.util.concurrent.CompletableFuture;
import javafx.scene.media.AudioClip;
public class UISoundEffects {
    private final AudioClip animationSound = new AudioClip(getClass().getResource("/com/example/hangmangame/sounds/chalkSound.wav").toString());
    private final AudioClip clickSound = new AudioClip(getClass().getResource("/com/example/hangmangame/sounds/sound.wav").toString());

    public void clickEffect() {
        clickSound.play();
    }
    public void animationSound() {
        CompletableFuture.runAsync(animationSound::play);
    }
    /// More effects coming soon....
}
