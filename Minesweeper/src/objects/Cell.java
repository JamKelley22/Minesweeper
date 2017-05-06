package objects;

import java.awt.Color;
import java.awt.Graphics;

import other.Config;

public class Cell {
	
	private int posX;
	private int posY;
	private boolean mine;
	private boolean flag;
	private Color color;
	
	/**
	 * Cell Constructor, A Game is made of a number of cells that may or may not contain mines
	 * @param posX The horizontal position in units
	 * @param posY The vertical position in units
	 * @param mine True if Cell has a mine, False otherwise
	 */
	public Cell(int posX, int posY, boolean mine) {
		this.mine = mine;
		this.flag = false;//starts out false
		this.posX = posX;
		this.posY = posY;
	}
	
	//Position Getters
	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}
	
	//Flag Getters & Setters
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	//Mine Getter
	public boolean isMine() {
		return mine;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void fillCell(Graphics graphics) {
		graphics.setColor(Color.BLUE);
		graphics.fill3DRect(this.posX * Config.CELL_DISTANCE, 
				this.posY * Config.CELL_DISTANCE, Config.CELL_DISTANCE, 
				Config.CELL_DISTANCE, true);
		
	}
}
