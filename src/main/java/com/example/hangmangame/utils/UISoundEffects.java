package com.example.hangmangame.utils;
import java.util.concurrent.CompletableFuture;
import javafx.scene.media.AudioClip;
public class UISoundEffects {
    private final AudioClip animationSound = new AudioClip(getClass().getResource("/com/example/hangmangame/sounds/chalkSound.wav").toString());
    private final AudioClip clickSound = new AudioClip(getClass().getResource("/com/example/hangmangame/sounds/sound.wav").toString());
    private boolean isMuted;

    public UISoundEffects(){
        isMuted = false;
    }
    public void clickEffect() {
        if(!isMuted)
            clickSound.play();

    }
    public void animationSound() {
        if(!isMuted)
            CompletableFuture.runAsync(animationSound::play);
    }
    public void setMuted(boolean muted) {
        isMuted = muted;
    }

}
