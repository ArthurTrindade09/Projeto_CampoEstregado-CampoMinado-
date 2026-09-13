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
    private JButton[][] buttons = new JButton[Y][X];
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
        panel.setLayout(new GridLayout(Y, X));

        flags = BOMB_COUNT;

        // Configura os botões
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {

                final int PosY = y;
                final int PosX = x;

                buttons[y][x] = new JButton();
                
                SetarSprite(y, x, 2, 0);
                
                // Configuração tamanho do botão
                buttons[y][x].setPreferredSize(
                    new java.awt.Dimension(TILE_SIZE, TILE_SIZE)
                );

                // Tirando informaçoes do botão
                buttons[y][x].setBorderPainted(false);
                buttons[y][x].setContentAreaFilled(false);
                buttons[y][x].setFocusPainted(false);

                panel.add(buttons[y][x]);

                // Quando o botão é apertado
                buttons[y][x].addActionListener(e -> {
                    Reveal(PosY, PosX);
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
    private void Reveal(int y, int x) {
        Space space = layout.getSpace(y, x);
        
        if (!space.revelado) {
        	space.revelado = true;
        } else {
        }
        
    	if (space.bomb) {
    		System.out.println("VOCÊ PERDEU!!");
    	}
    }
    private void SetarSprite(int y, int x, int image_X, int image_Y) {
        BufferedImage sprite = getSprite(image_Y, image_X);
        Image scaled = sprite.getScaledInstance(
            TILE_SIZE,
            TILE_SIZE,
            Image.SCALE_FAST
        );
        buttons[y][x].setIcon(new javax.swing.ImageIcon(scaled));
    }
}