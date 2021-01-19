package warcraftTD.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Outil pour la gestion des sons
 */
public class Sound {
  /**
   * Fichier du son
   */
  private final File file;
  /**
   * Indique si le son doit jouer en boucle
   */
  private final boolean isLoop;
  /**
   * Clip du son
   */
  private Clip c;

  /**
   * Création d'un nouveau son
   *
   * @param filepath Chemin d'accès vers le son
   * @param isLoop   Indique si le son doit jouer en boucle
   * @throws LineUnavailableException      Erreur relative au chargement du son
   * @throws IOException                   Erreur relative au chargement du son
   * @throws UnsupportedAudioFileException Erreur relative au chargement du son
   */
  public Sound(String filepath, boolean isLoop) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
    this.file = new File(filepath);
    this.isLoop = isLoop;
  }

  /**
   * Joue le son
   *
   * @param gain Le gain du son
   * @throws IOException                   Erreur relative à la lecture du son
   * @throws LineUnavailableException      Erreur relative à la lecture du son
   * @throws UnsupportedAudioFileException Erreur relative à la lecture du son
   */
  public void play(double gain) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.file);
    this.c = AudioSystem.getClip();
    this.c.open(audioStream);
    FloatControl gainControl = (FloatControl) this.c.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(dB);
    if (this.isLoop) {
      this.c.loop(Clip.LOOP_CONTINUOUSLY);
    }
    this.c.start();
  }

  /**
   * Joue le son avec un gain de 1
   *
   * @throws IOException                   Erreur relative à la lecture du son
   * @throws LineUnavailableException      Erreur relative à la lecture du son
   * @throws UnsupportedAudioFileException Erreur relative à la lecture du son
   */
  public void play() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    this.play(1.0);
  }

  /**
   * Arrêt du son
   */
  public void stop() {
    this.c.stop();
    this.c = null;
  }

}
