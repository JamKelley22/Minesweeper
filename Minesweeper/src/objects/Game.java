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
		cells = new Cell[sizeX][sizeY];
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
	
	/**
	 * 
	 * @param x X position of curser in pixels
	 * @param y Y position of curser in pixels
	 * @return the cell in Game that is closest to this x,y position
	 */
	public Cell getClosestCell(int x, int y) {
		return cells[x / Config.CELL_DISTANCE][y / Config.CELL_DISTANCE];
	}
	
	public int getNeighborMineNum(int posX, int posY) {
		int sumMines = 0;
		boolean topWall = (posY == 0);
		boolean bottomWall = (posY == cells.length - 1);
		boolean leftWall = (posX == 0);
		boolean rightWall = (posX == cells[0].length - 1);
		
		if(!topWall) {
			if(cells[posX][posY-1].isMine()) {//North
				sumMines++;
			}
		}
		if(!bottomWall) {
			if(cells[posX][posY+1].isMine()) {//South
				sumMines++;
			} 
		}
		if(!leftWall) {
			if(cells[posX-1][posY].isMine()) {//West
				sumMines++;
			}
		}
		if(!rightWall) {
			if(cells[posX+1][posY].isMine()) {//East
				sumMines++;
			} 
		}
		
		if(!rightWall && !topWall) {//NE
			if(cells[posX+1][posY-1].isMine()) {
				sumMines++;
			} 
		}
		if(!leftWall && !topWall) {//NW
			if(cells[posX-1][posY-1].isMine()) {
				sumMines++;
			} 
		}
		if(!rightWall && !bottomWall) {//SE
			if(cells[posX+1][posY+1].isMine()) {
				sumMines++;
			} 
		}
		if(!leftWall && !bottomWall) {//SW
			if(cells[posX-1][posY+1].isMine()) {
				sumMines++;
			} 
		}
		
		//TODO
				
		
		return sumMines;
	}
	
}
