package minesweeper_tutorial;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Game {
	private Board layout;
	private JButton[][] buttons = new JButton[Y][X];
	
	//SET UP
	public static final int WIDTH = 20;  // LARGURA DO TILE
	public static final int HEIGHT = 20; // ALTURA DO TILE
	public static final int X = 5;      // TILES POR LINHA
	public static final int Y = 5;
	// TILES POR COLUNA
	protected JFrame window;
	
	//ATRIBUTOS DO JOGO
	public static final int BOMB_COUNT = 10; // QUANTIDADE DE BOMBAS
	private int score_box_width = 100;
	private boolean end = false;
	private boolean win = false;
	private int flags;
	
	public Game() {
		window = new JFrame(); // INICIAR JANELa
		layout = new Board();
		
		// Criar lugar para colocar objetos
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Y, X));
		
		flags = BOMB_COUNT;
		
		// Colocar botões para cada tile
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				
				final int PosY = y;
				final int PosX = x;
				
				buttons[y][x] = new JButton();
				panel.add(buttons[y][x]);
				
				buttons[y][x].addActionListener(e -> {
					Space space = layout.getSpace(PosY, PosX);

				    if (space.bomb) {
				        System.out.println("BOOM!");
				    } else {
				        System.out.println("Número: " + space.bombNearby);
				    }
				});
			}
		}
		
		// Adicionar o lugar
		window.add(panel);
		window.setTitle("Campo Estragado"); // Nome da Janela
		window.setSize(500, 500); // Tamanho da janela
		window.setVisible(true); // Visibilidade da tela
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configurar forma de fechar
		window.setResizable(false);
		
	}
}