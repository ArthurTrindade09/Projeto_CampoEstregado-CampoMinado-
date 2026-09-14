package minesweeper_tutorial;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Musica {

    private ArrayList<Nota>[] canais;

    @SuppressWarnings("unchecked")
    public Musica(String caminho) {

        canais = new ArrayList[4];

        for (int i = 0; i < 4; i++) {
            canais[i] = new ArrayList<>();
        }

        try {

        	BufferedReader leitor = new BufferedReader(
        		    new InputStreamReader(
        		        getClass().getResourceAsStream("/MusicFiles/" + caminho)
        		    )
        		);

            String linha;
            int canal = 0;

            while ((linha = leitor.readLine()) != null && canal < 4) {

                String[] partes = linha.trim().split(" ");

                for (String parte : partes) {

                    String[] dados = parte.split(":");

                    String notaCompleta = dados[0];
                    int duracao = Integer.parseInt(dados[1]);

                    if (notaCompleta.equals("REST")) {
                        canais[canal].add(
                            new Nota("REST", 0, duracao)
                        );
                        continue;
                    }

                    String nota;
                    int oitava;

                    if (notaCompleta.length() == 2) {
                        nota = notaCompleta.substring(0, 1);
                        oitava = Integer.parseInt(
                            notaCompleta.substring(1)
                        );

                    } else {
                        nota = notaCompleta.substring(0, 2);
                        oitava = Integer.parseInt(
                            notaCompleta.substring(2)
                        );
                    }

                    canais[canal].add(
                        new Nota(nota, oitava, duracao)
                    );
                }

                canal++;
            }

            leitor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Nota> getCanal(int canal) {
        return canais[canal];
    }
    
    public void tocar() {
        Synth.tocarCanais(canais);
    }
}