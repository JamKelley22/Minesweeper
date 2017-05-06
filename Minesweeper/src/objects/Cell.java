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
	private boolean open;
	
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
		this.color = Config.CELL_COLOR_CLOSED;
		open = false;
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

	/**
	 * will only set flag to true if not open
	 * @param flag
	 */
	public void setFlag(boolean flag) {
		if(flag == true && this.open == false)
			this.flag = true;
		else if(flag == false)
			this.flag = false;
	}

	//Mine Getter
	public boolean isMine() {
		return mine;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void fillCell(Graphics graphics) {
		graphics.setColor(this.color);
		int newX = this.posX * Config.CELL_DISTANCE;
		int newY = this.posY * Config.CELL_DISTANCE;
		graphics.fill3DRect(newX, 
				newY, Config.CELL_DISTANCE, 
				Config.CELL_DISTANCE, true);
		if(this.isFlag()) {
			graphics.setColor(Config.FLAG_COLOR);
			graphics.fillRect(newX + Config.FLAG_DIFF, newY + Config.FLAG_DIFF,
					Config.FLAG_SIZE, Config.FLAG_SIZE);
		}
		
	}
	
	public String toString() {
		return "Cell[ " + this.posX + "," + this.posY + "]\n" 
	+ "Flag: " + this.flag + " | Mine: " + this.mine;
	}

}
