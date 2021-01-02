package warcraftTD.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
  private final File file;
  private final boolean isLoop;


  public Sound(String filepath, boolean isLoop) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
    this.file = new File(filepath);
    this.isLoop = isLoop;
  }

  public void play(double gain) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.file);
    Clip clip = AudioSystem.getClip();
    clip.open(audioStream);
    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(dB);
    if (this.isLoop) {
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    clip.start();
  }

  public void play() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    this.play(1.0);
  }


}
