package minesweeper_tutorial;

import javax.sound.sampled.*;

public class Synth {

    public static void tocarNota(double frequencia, int duracao) {
    	
    	
        float sampleRate = 44100;
        int samples = (int) (duracao * sampleRate / 1000);

        AudioFormat formato = new AudioFormat(
            sampleRate,
            8,
            1,
            true,
            false
        );

        try {
            SourceDataLine linha = AudioSystem.getSourceDataLine(formato);
            linha.open(formato);
            linha.start();

            byte[] dados = new byte[samples];

            for (int i = 0; i < samples; i++) {

                double tempo = i / sampleRate;

                double onda = Math.sin(
                    2 * Math.PI * frequencia * tempo
                );

                dados[i] = (byte) (onda * 127);
            }

            linha.write(dados, 0, dados.length);

            linha.drain();
            linha.stop();
            linha.close();

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}