import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private static Clip bgmclip; // BGM Clip in which the BackGroundMusic plays on
    private static float bgmVolume = 1.0f;
    private static Clip sfxclip; // SFX Clip in which all the SFX plays on
    private static float sfxVolume = 1.0f;

    public static Clip playBGM(String soundPath) {
        try {
            File bgmFile = new File(soundPath);
            AudioInputStream bgmStream = AudioSystem.getAudioInputStream(bgmFile);
            bgmclip = AudioSystem.getClip();
            bgmclip.open(bgmStream);
            bgmclip.loop(Clip.LOOP_CONTINUOUSLY);

            setBGMVolume(bgmVolume);

            bgmclip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return bgmclip;
    }

    public static void setBGMVolume(float vol) {
        bgmVolume = vol;
        if (bgmclip != null) {
            FloatControl volControl = (FloatControl) bgmclip.getControl(FloatControl.Type.MASTER_GAIN);
            float db = (float) (Math.log(bgmVolume) / Math.log(10.0) * 20.0);
            volControl.setValue(db);
        }
    }

    public static Clip playSFX(String soundPath) {
        try {
            File sfxFile = new File(soundPath);
            AudioInputStream sfxStream = AudioSystem.getAudioInputStream(sfxFile);
            sfxclip = AudioSystem.getClip();
            sfxclip.open(sfxStream);

            setSFXVolume(sfxVolume);

            sfxclip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return sfxclip;
    }

    public static void setSFXVolume(float vol) {
        sfxVolume = vol;
        if (sfxclip != null) {
            FloatControl volControl = (FloatControl) sfxclip.getControl(FloatControl.Type.MASTER_GAIN);
            float db = (float) (Math.log(sfxVolume) / Math.log(10.0) * 20.0);
            volControl.setValue(db);
        }
    }

    public static void stopSound(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    // BGM presets
    public static void bgmGame() {
        if (bgmclip == null || !bgmclip.isRunning()) {
            playBGM("src/aud/game.wav");
        }
    }
    
    public static void bgmStop() {
        if (bgmclip != null) {
            stopSound(bgmclip);
            bgmclip = null;
        }
    }

    // SFX presets
    public static void clicksound() {
        playSFX("src/aud/click.wav");
    }

    public static void shootsound() {
        playSFX("src/aud/shoot.wav");
    }

    public static void collision() {
        playSFX("src/aud/collision.wav");
    }
}
