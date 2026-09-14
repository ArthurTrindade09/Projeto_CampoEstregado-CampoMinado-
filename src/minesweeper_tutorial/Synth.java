package minesweeper_tutorial;

import javax.sound.sampled.*;

public class Synth {

    public static void tocarNota(double frequencia, int duracao) {
        try {
            float sampleRate = 44100;

            AudioFormat formato = new AudioFormat(
                sampleRate,
                8,
                1,
                true,
                false
            );

            SourceDataLine linha = AudioSystem.getSourceDataLine(formato);
            linha.open(formato);
            linha.start();

            byte[] buffer = new byte[1];

            int amostras = (int) (duracao * sampleRate / 1000);

            for (int i = 0; i < amostras; i++) {

                double tempo = i / sampleRate;

                byte valor = (byte) (
                    Math.sin(2 * Math.PI * frequencia * tempo) * 127
                );

                buffer[0] = valor;
                linha.write(buffer, 0, 1);
            }

            linha.drain();
            linha.stop();
            linha.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}