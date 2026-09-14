package minesweeper_tutorial;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Game {
	private javax.swing.Timer bombaAnimacao;
    private Board layout;
    private BufferedImage spritesheet;

    // SET UP
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    private int X = 0;
    private int Y = 0;
    public static final int TILE_SIZE = 32;
    protected JFrame window;
    private JButton[][] buttons;

    // ATRIBUTOS DO JOGO
    private boolean bombaFrame = false;
    private int BOMB_COUNT = 0;
    public boolean end = false;
    public boolean win = false;
    public int CASAS_REVELADAS = 0;
    private int flags;
    private int segundos = 0;
    private javax.swing.Timer cronometro;
    private JLabel flags_text;
    JLabel mensagem = new JLabel("");
    
    public Game(int x, int y, int bombs) {

    	this.X = x;
    	this.Y = y;
    	this.BOMB_COUNT = bombs;
    	
        buttons = new JButton[X][Y];
    			
        // CARREGAR SPRITESHEET
        try {
            spritesheet = ImageIO.read(
                getClass().getResource("/Sprites/TilesCampoEstragado.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        window = new JFrame();

        ImageIcon icone = new ImageIcon(
            getClass().getResource("/Sprites/icon.png")
        );

        window.setIconImage(icone.getImage());
        layout = new Board(X, Y, BOMB_COUNT);
        
        // painel que contem tudo
        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // Painel Lateral
        JPanel painelLateral = new JPanel();
        painelLateral.setPreferredSize(new Dimension(200, 0));
        
        JButton reiniciar = new JButton("REINICIAR");
        JButton menu = new JButton("MENU");
        
        reiniciar.addActionListener(e -> {
            window.dispose();
            MusicPlayer.stop();
            new Game(x, y, bombs);
        });
        
        flags = BOMB_COUNT;
        
        JLabel titulo = new JLabel("CAMPO ESTRAGADO");
        JLabel bombas = new JLabel("BOMBAS: " + BOMB_COUNT);
        flags_text = new JLabel("BANDEIRAS: " + flags);
        JLabel tempo = new JLabel("Tempo: 00:00");
        cronometro = new javax.swing.Timer(1000, e -> {
            segundos++;

            int minutos = segundos / 60;
            int segundosRestantes = segundos % 60;

            tempo.setText(String.format(
                "Tempo: %02d:%02d",
                minutos,
                segundosRestantes
            ));
        });

        cronometro.start();
        
        titulo.setFont(new java.awt.Font(
        	    "Arial",
        	    java.awt.Font.BOLD,
        	    18
        	));
        bombas.setFont(new java.awt.Font(
        	    "Arial",
        	    java.awt.Font.PLAIN,
        	    18
        	));
        tempo.setFont(new java.awt.Font(
        	    "Arial",
        	    java.awt.Font.PLAIN,
        	    18
        	));
        flags_text.setFont(new java.awt.Font(
        	    "Arial",
        	    java.awt.Font.PLAIN,
        	    18
        	));
        mensagem.setFont(new java.awt.Font(
        	    "Arial",
        	    java.awt.Font.BOLD,
        	    25
        	));
        
        // Alinhamento dos objetos laterais
        titulo.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        bombas.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        flags_text.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        tempo.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        reiniciar.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);
        mensagem.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);
        // a
        painelLateral.add(titulo);
        painelLateral.add(flags_text);
        painelLateral.add(bombas);
        painelLateral.add(tempo);
        painelLateral.add(reiniciar);
        painelLateral.add(menu);
        painelLateral.add(mensagem);
        
        menu.addActionListener(e -> {
            window.dispose();
            new MainMenu();
        });
        
        painelLateral.setLayout(
        	new javax.swing.BoxLayout(
        		painelLateral,
        		javax.swing.BoxLayout.Y_AXIS
        	)
        );
        
        painelLateral.setBackground(new java.awt.Color(251, 149, 83));
        	
        
        // Painel Tabuleiro
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(X, Y));


        // Configura os botões
        for (int y1 = 0; y1 < Y; y1++) {
            for (int x1 = 0; x1 < X; x1++) {

                final int PosY = y1;
                final int PosX = x1;
                
                buttons[x1][y1] = new JButton();
                
                SetarSprite(x1, y1, 0, 2);
                
                // Configuração tamanho do botão
                buttons[x1][y1].setPreferredSize(
                    new java.awt.Dimension(TILE_SIZE, TILE_SIZE)
                );

                // Tirando informaçoes do botão
                buttons[x1][y1].setBorderPainted(false);
                buttons[x1][y1].setContentAreaFilled(false);
                buttons[x1][y1].setFocusPainted(false);

                panel.add(buttons[x1][y1]);

                // Quando o botão é apertado
                buttons[PosX][PosY].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {

                        if (e.getButton() == MouseEvent.BUTTON3) {
                        	ToggleFlag(PosX, PosY);
                        } else if (e.getButton() == MouseEvent.BUTTON1) {
                        	Reveal(PosX, PosY, "CLIQUE");
                        }
                    }
                });
            }
        }
        
        painelPrincipal.add(panel, BorderLayout.CENTER);
        painelPrincipal.add(painelLateral, BorderLayout.EAST);
        
        window.add(painelPrincipal);
        window.setTitle("Campo Estragado");
        window.setSize(
        	40 * TILE_SIZE,
        	40 * TILE_SIZE + 40
        );
        window.pack();
        window.setLocationRelativeTo(null); 
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
    }

    // PEGAR UM TILE DO SPRITESHEET
    private BufferedImage getSprite(int x, int y) {
        return spritesheet.getSubimage(
            x * WIDTH,
            y * HEIGHT,
            WIDTH,
            HEIGHT
        );
    }
    
    // REVELAR O TILE QUE FOI CLICADO
    private  void Reveal(int x, int y, String mode) {
    	// Não deixa revelar se perdeu/ganhou o jogo
        if (end || win) {
        	return;
        }
    	
    	Space space = layout.getSpace(x, y);
        
        if (space.revelado) {
        	return;
        }
        
        space.revelado = true;
        CASAS_REVELADAS += 1;
        
        if (space.bomb && mode.equals("CLIQUE")) {
            this.end = true;
            for (int i_y = 0; i_y < this.Y; i_y++) {
            	for (int i_x = 0; i_x < this.X; i_x++) {
                	Space Checkspace = layout.getSpace(i_x, i_y);
                	if (Checkspace.bomb) {
                		SetarSprite(i_x, i_y, 2, 2);
                	}
            	}
            }
            MusicPlayer.play("GAME_OVER.wav");
            cronometro.stop();
            mensagem.setText("GAME OVER");
            
            bombaAnimacao = new javax.swing.Timer(300, e -> {
                if (bombaFrame) {
                    SetarSprite(x, y, 2, 2);
                } else {
                    SetarSprite(x, y, 3, 2);
                }

                bombaFrame = !bombaFrame;
            });

            bombaAnimacao.start();

            return;
        }
    	
    	// Caso onde você ganha
    	if (CASAS_REVELADAS == ((X * Y)-BOMB_COUNT)){
    		win = true;
            MusicPlayer.play("WIN_THEME.wav");
    		cronometro.stop();
    		mensagem.setText("PARABENS! ACABOU");
    	}
    	
    	//Sistema de Revelar Vazios
    	if (space.bombNearby == 0) {
	    	for (int i_y = y - 1; i_y < y + 2; i_y++) {
	    		for(int i_x = x -1; i_x < x + 2; i_x++) {
	    			// Cordenada fora do tabuleiro
	    			if ((i_y < 0 || i_y >= Y) || (i_x < 0 || i_x >= X)) {
	    				continue;
	    			}
	    	    	Reveal(i_x, i_y, "BUSCA");
	    		}
	    	}
    	}
    	
    	if (space.bombNearby >= 0 && space.bombNearby <= 8) {
    		SetarSprite(x, y, space.bombNearby, 3);
    	}
    }
    
    // Muda o sprite do tile
    private void SetarSprite(int x, int y, int image_X, int image_Y) {
        BufferedImage sprite = getSprite(image_X, image_Y);
        Image scaled = sprite.getScaledInstance(
            TILE_SIZE,
            TILE_SIZE,
            Image.SCALE_FAST
        );
        buttons[x][y].setIcon(new javax.swing.ImageIcon(scaled));
    }

    private void ToggleFlag(int x, int y) {
        Space space = layout.getSpace(x, y);

        if (space.revelado) {
            return;
        }

        if (space.flag) {
            space.flag = false;
            SetarSprite(x, y, 0, 2); // SPRITE TILE SEM BANDEIRA
            flags++;
            flags_text.setText("BANDEIRAS: " + flags);
            
        } else {
            if (flags <= 0) {
                return;
            }

            space.flag = true;
            SetarSprite(x, y, 1, 2); // SPRITE TILE COM BANDEIRA
            flags--;
            flags_text.setText("BANDEIRAS: " + flags);

        }
    }
    
}