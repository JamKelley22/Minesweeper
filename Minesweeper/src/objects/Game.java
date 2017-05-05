package objects;

import java.util.Random;

import other.Config;

public class Game {
	
	private Cell[][] cells;
	private int sizeX;
	private int sizeY;
	private boolean wrap;//determines if edge cells wrap to other side
	private int dificulity;
	
	private Random r = Config.RANDOM;
	
	public Game(int sizeX, int sizeY, boolean wrap, int difficulty) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.wrap = wrap;
		this.dificulity = difficulty;//0-99 99 being full mines
		fillCells();
	}
	
	public Game() {//for a preset Game from a file (eg load game)
		//TODO after regular works
	}
	
	public void reset() {
		//TODO
	}
	
	public Cell[][] getCells() {
		return this.cells;
	}
	
	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public boolean isWrap() {
		return wrap;
	}

	private void fillCells() {
		for(int i = 0; i < this.sizeX; i++) {
			for(int j = 0; j < this.sizeY; j++) {
				if(r.nextInt(100) < this.dificulity) {//Construct Cell with mine in it
					this.cells[i][j] = new Cell(i,j,true);
				}
				else {
					this.cells[i][j] = new Cell(i,j,false);//Construct Cell with no mine
				}
			}
		}
	}
	
}
