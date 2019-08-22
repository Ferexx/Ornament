package Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    private AudioInputStream audioInputStream;
    public Clip clip;
    public double gain;

    public SoundPlayer(File audioFile) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        assert clip != null;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gain = 0.01;
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

    public void setVolume(double newVolume) {
        assert clip != null;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);;
        float dB = (float) (Math.log(newVolume) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}
