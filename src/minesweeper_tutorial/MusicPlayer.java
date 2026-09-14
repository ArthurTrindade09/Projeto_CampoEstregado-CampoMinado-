package minesweeper_tutorial;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class MusicPlayer {

    private static Clip musicaAtual;

    public static void play(String arquivo) {

        stop();

        try {
            URL url = MusicPlayer.class.getResource("/MusicFiles/" + arquivo);

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            musicaAtual = AudioSystem.getClip();
            musicaAtual.open(audio);

            musicaAtual.loop(Clip.LOOP_CONTINUOUSLY);
            musicaAtual.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {

        if (musicaAtual != null) {
            musicaAtual.stop();
            musicaAtual.close();
            musicaAtual = null;
        }
    }
}