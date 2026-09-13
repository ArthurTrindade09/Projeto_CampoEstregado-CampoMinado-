package minesweeper_tutorial;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Space {
	// Informarções do espaço quadrado
	public boolean bomb;
	public boolean flag;
	public boolean revelado;
	public int bombNearby = 0;
	public BufferedImage image;
	
	public Space(boolean bomb) {
		this.bomb = bomb;
	}
}
