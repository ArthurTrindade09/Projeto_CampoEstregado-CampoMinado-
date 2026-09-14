package minesweeper_tutorial;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.util.Random;
import javax.swing.Timer;

public class MainMenu {
    
    protected JFrame window;
    
    public MainMenu() {
        MusicPlayer.play("MAIN_THEME.wav");
        // painel que contem tudo
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel painelCapa = new JPanel(new BorderLayout());
        JPanel painelCapaPrincipal = new JPanel(null);
        
        painelCapa.setLayout(null);
        
        painelCapa.setBounds(0, 0, 500, 500);
        painelPrincipal.setBounds(500, 0, 500, 500);
        
        ImageIcon capa = new ImageIcon(
        	    getClass().getResource("/Sprites/titlescreen.png")
        	);

        	JLabel imagemCapa = new JLabel(capa);
        	imagemCapa.setBounds(0, 0, 500, 500);

        	painelCapa.add(imagemCapa);
        	
        	JButton jogar = new JButton("JOGAR");

        	jogar.setBounds(180, 420, 140, 45);
        	
        	jogar.addActionListener(e -> {

        	    Timer animacao = new Timer(10, null);

        	    animacao.addActionListener(ev -> {
        	        int xCapa = painelCapa.getX();
        	        int xEscolha = painelPrincipal.getX();

        	        if (xCapa <= -500) {
        	            animacao.stop();
        	            return;
        	        }

        	        painelCapa.setLocation(xCapa - 10, 0);
        	        painelPrincipal.setLocation(xEscolha - 10, 0);
        	    });

        	    animacao.start();
        	});

        	painelCapa.add(jogar);
        	
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new javax.swing.BoxLayout(
            conteudo,
            javax.swing.BoxLayout.Y_AXIS
        ));
        
        // Titulo do menu
        javax.swing.JLabel titulo = new javax.swing.JLabel("CAMPO ESTRAGADO");
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
        titulo.setAlignmentX(javax.swing.JLabel.CENTER_ALIGNMENT);

        conteudo.add(titulo);
        
        JLabel tamanho = new JLabel("Tamanho do tabuleiro:");

        JRadioButton tamanho5 = new JRadioButton("5 x 5");
        JRadioButton tamanho10 = new JRadioButton("10 x 10");
        JRadioButton tamanho20 = new JRadioButton("20 x 20");

        ButtonGroup grupoTamanho = new ButtonGroup();
        grupoTamanho.add(tamanho5);
        grupoTamanho.add(tamanho10);
        grupoTamanho.add(tamanho20);

        tamanho10.setSelected(true);
        
        JLabel bombas = new JLabel("Quantidade de bombas:");

        JRadioButton bombas5 = new JRadioButton("5");
        JRadioButton bombas10 = new JRadioButton("10");
        JRadioButton bombas20 = new JRadioButton("20");
        JRadioButton bombasAleatorio = new JRadioButton("Aleatório");

        ButtonGroup grupoBombas = new ButtonGroup();
        grupoBombas.add(bombas5);
        grupoBombas.add(bombas10);
        grupoBombas.add(bombas20);
        grupoBombas.add(bombasAleatorio);

        bombas10.setSelected(true);
        
        
        tamanho.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        tamanho5.setAlignmentX(JRadioButton.CENTER_ALIGNMENT);
        tamanho10.setAlignmentX(JRadioButton.CENTER_ALIGNMENT);
        tamanho20.setAlignmentX(JRadioButton.CENTER_ALIGNMENT);

        bombas.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        bombas5.setAlignmentX(JRadioButton.CENTER_ALIGNMENT);
        bombas10.setAlignmentX(JRadioButton.CENTER_ALIGNMENT);
        bombas20.setAlignmentX(JRadioButton.CENTER_ALIGNMENT);
        bombasAleatorio.setAlignmentX(JRadioButton.CENTER_ALIGNMENT);
        
        conteudo.add(tamanho);
        conteudo.add(tamanho5);
        conteudo.add(tamanho10);
        conteudo.add(tamanho20);
        conteudo.add(bombas);
        conteudo.add(bombas5);
        conteudo.add(bombas10);
        conteudo.add(bombas20);
        conteudo.add(bombasAleatorio);
        
        JButton jogar1 = new JButton("JOGAR");
        jogar1.setAlignmentX(JButton.CENTER_ALIGNMENT);
        
        jogar1.addActionListener(e -> {

        	Random gerador = new Random();
        	
            int tamanho_a = 10;

            if (tamanho5.isSelected()) {
                tamanho_a = 5;
            } else if (tamanho20.isSelected()) {
                tamanho_a = 20;
            }

            System.out.println("Tamanho: " + tamanho_a);
            
            int bombas_a = 10;

            if (bombas5.isSelected()) {
                bombas_a = 5;
            } else if (bombas20.isSelected()) {
                bombas_a = 20;
            } else if (bombasAleatorio.isSelected()) {
            	bombas_a = gerador.nextInt(tamanho_a * 2);
            }
            MusicPlayer.stop();
            Game game = new Game(tamanho_a, tamanho_a, bombas_a);
            window.dispose();
        });

        conteudo.add(jogar1);
        
        painelPrincipal.add(conteudo, BorderLayout.CENTER);
        
        painelCapaPrincipal.add(painelCapa);
        painelCapaPrincipal.add(painelPrincipal);
        window = new JFrame();

        ImageIcon icone = new ImageIcon(
            getClass().getResource("/Sprites/icon.png")
        );

        window.setIconImage(icone.getImage());
        
        
        window.add(painelCapaPrincipal);
        window.setTitle("Campo Estragado - Main Menu");
        window.setSize(500,500);
        window.setLocationRelativeTo(null); 
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
    }
}
