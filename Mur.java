import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Mur extends Case {
    public Mur(int l, int c) {
        super(l, c);
    }

    public boolean estTraversable() {
        return false;
    }

    public Color getColor() {
        return Color.BLACK;
    }

    public void entre(Joueur j) {
        // Appeler la méthode playSound avec le chemin du fichier audio
        playSound("Musique mur.wav");
    }

    public static void playSound(String soundFilePath) {
        try {
            // Charger le fichier audio depuis le chemin spécifié
            File soundFile = new File(soundFilePath);

            // Créer une URL à partir du fichier audio
            URL soundUrl = soundFile.toURI().toURL();

            // Ouvrir le flux audio
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundUrl);

            // Obtenir le format audio
            AudioFormat format = audioIn.getFormat();

            // Créer une source de données pour le flux audio
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);

            // Ouvrir le clip et charger les données audio depuis le flux audio
            clip.open(audioIn);

            // Jouer le son
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
