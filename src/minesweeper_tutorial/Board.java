package minesweeper_tutorial;

import java.util.Random;

public class Board {
	private Space[][] board = new Space[Game.Y][Game.X]; 
	
	public Space getSpace(int y, int x) {
		return board[y][x];
	}
	
	public Board() {
		Random Gerador = new Random(); // Gerador de números aleatórios
		int BombasColocadas = 0;
		
		// Cria a matriz do tabuleiro Game.Y x Game.X
		for (int i_y = 0; i_y < Game.Y; i_y++) {
			for (int i_x = 0; i_x < Game.X; i_x++) {
				board[i_y][i_x] = new Space(false);
			} // Linhas
		} // Colunas
		
		// Configurar onde vai ter bomba
		while (BombasColocadas < Game.BOMB_COUNT) {
			int Posicao_X = Gerador.nextInt(Game.X);
			int Posicao_Y = Gerador.nextInt(Game.Y);
			
			if (board[Posicao_Y][Posicao_X].bomb == true) {
				// Ja tem bomba, então não pode colocar
				continue;
			} else {
				// Não tem bomba. Pode colocar bomba
				BombasColocadas += 1;
				board[Posicao_Y][Posicao_X].bomb = true;
			}	
		}
		
		// Configura quantas bombas próximas tem de cada tile
		ConfigurarNumero();
		
	} // Metodo Board()
	
	public void MostrarBoard() {
		// Percorre o tabuleiro para mostrar tudo.
		for (int i_y = 0; i_y < Game.Y; i_y++) {
			for (int i_x = 0; i_x < Game.X; i_x++) {
				
				if (board[i_y][i_x].bomb == true) {
					// Se tiver bomba
					System.out.print("[💣]");
				} else {
					// Se não tiver bomba
					System.out.print("["+board[i_y][i_x].bombNearby+"]");
				}
			} // Linhas
			System.out.println();
		} // Colunas
	}

	public void ConfigurarNumero() {
		// Acessar cada tile para verificar
		for (int i_y = 0; i_y < Game.Y; i_y++) {
			for (int i_x = 0; i_x < Game.X; i_x++) {
		
				// Verificar 8 tiles vizinhos
				for (int CheckY = i_y - 1; CheckY < i_y + 2; CheckY++) {
					for (int CheckX = i_x - 1; CheckX < i_x + 2; CheckX++) {
						
						// Caso a posição a checar esteja fora do tabuleiro
						if ((CheckY < 0 || CheckY >= Game.Y) || (CheckX < 0 || CheckX >= Game.X)) {
							continue;
						}
						
						// Ignorar a própria casa
						if (CheckY == i_y && CheckX == i_x) {
							continue;
							
						} else {
							if (board[CheckY][CheckX].bomb == true) {
								// Caso Tenha bomba
								// Bombas proximas do tile que está analisando
								board[i_y][i_x].bombNearby += 1;
							} else {
								// Caso não tenha bomba
								continue;
							}
						}
					}
				}
			}
		}
	}
}
