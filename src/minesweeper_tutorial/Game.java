package minesweeper_tutorial;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Game {

    private Board layout;
    private JButton[][] buttons = new JButton[X][Y];
    private BufferedImage spritesheet;

    // SET UP
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    public static final int X = 5;
    public static final int Y = 5;
    public static final int TILE_SIZE = 32;
    
    protected JFrame window;

    // ATRIBUTOS DO JOGO
    public static final int BOMB_COUNT = 10;
    private int score_box_width = 100;
    private boolean end = false;
    private boolean win = false;
    private int flags;

    public Game() {

        // CARREGAR SPRITESHEET
        try {
            spritesheet = ImageIO.read(
                getClass().getResource("/Sprites/TilesCampoEstragado.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        window = new JFrame();
        layout = new Board();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(X, Y));

        flags = BOMB_COUNT;

        // Configura os botões
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {

                final int PosY = y;
                final int PosX = x;

                buttons[x][y] = new JButton();
                
                SetarSprite(x, y, 0, 2);
                
                // Configuração tamanho do botão
                buttons[x][y].setPreferredSize(
                    new java.awt.Dimension(TILE_SIZE, TILE_SIZE)
                );

                // Tirando informaçoes do botão
                buttons[x][y].setBorderPainted(false);
                buttons[x][y].setContentAreaFilled(false);
                buttons[x][y].setFocusPainted(false);

                panel.add(buttons[x][y]);

                // Quando o botão é apertado
                buttons[x][y].addActionListener(e -> {
                    Reveal(PosX, PosY);
                    }
                );
            }
        }

        window.add(panel);
        window.setTitle("Campo Estragado");
        window.setSize(180, 200);
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
    private void Reveal(int x, int y) {
        Space space = layout.getSpace(x, y);
        
        if (!space.revelado) {
        	space.revelado = true;
        } else {
        }
        
    	if (space.bomb) {
    		System.out.println("VOCÊ PERDEU!!");
    		SetarSprite(x, y, 2, 2);
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
}