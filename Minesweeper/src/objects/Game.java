package objects;

import java.util.ArrayList;
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
		createEmptyCells();
	}
	
	public Game() {//for a preset Game from a file (eg load game)
		//TODO after regular works
	}
	
	public void reset() {
		//TODO
		closeAllCells();
	}
	
	public void newGame() {
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

	private void createEmptyCells() {
		for(int i = 0; i < this.sizeX; i++) {
			for(int j = 0; j < this.sizeY; j++) {
				this.cells[i][j] = new Cell(i,j,false);
//				if(r.nextInt(100) < this.dificulity) {//Construct Cell with mine in it
//					this.cells[i][j] = new Cell(i,j,true);
//				}
//				else {
//					this.cells[i][j] = new Cell(i,j,false);//Construct Cell with no mine
//				}
			}
		}
	}
	
	/**
	 * 
	 * @param x X position of curser in pixels
	 * @param y Y position of curser in pixels
	 * @return the cell in Game that is closest to this x,y position if y > 0, else null
	 */
	public Cell getClosestCell(int x, int y) {
		if(y > 0) {
			return cells[x / Config.CELL_DISTANCE][y / Config.CELL_DISTANCE];
		}
		else {
			return null;
		}
	}
	
	public Cell[] getNeighbors(int posX, int posY) {
		ArrayList<Cell> cellList = new ArrayList<Cell>();
		
		boolean topWall = (posY == 0);
		boolean bottomWall = (posY == cells.length - 1);
		boolean leftWall = (posX == 0);
		boolean rightWall = (posX == cells[0].length - 1);
		
		if(!topWall) {
			cellList.add(cells[posX][posY-1]);
		}
		if(!bottomWall) {
			cellList.add(cells[posX][posY+1]); 
		}
		if(!leftWall) {
			cellList.add(cells[posX-1][posY]);
		}
		if(!rightWall) {
			cellList.add(cells[posX+1][posY]);
		}
		
		if(!rightWall && !topWall) {//NE
			cellList.add(cells[posX+1][posY-1]); 
		}
		if(!leftWall && !topWall) {//NW
			cellList.add(cells[posX-1][posY-1]); 
		}
		if(!rightWall && !bottomWall) {//SE
			cellList.add(cells[posX+1][posY+1]);
		}
		if(!leftWall && !bottomWall) {//SW
			cellList.add(cells[posX-1][posY+1]);
		}
		return cellList.toArray(new Cell[cellList.size()]);
	}
	
	public Cell[] getCardnalNeighbors(int posX, int posY) {
		ArrayList<Cell> cellList = new ArrayList<Cell>();
		
		boolean topWall = (posY == 0);
		boolean bottomWall = (posY == cells.length - 1);
		boolean leftWall = (posX == 0);
		boolean rightWall = (posX == cells[0].length - 1);
		
		if(!topWall) {
			cellList.add(cells[posX][posY-1]);
		}
		if(!bottomWall) {
			cellList.add(cells[posX][posY+1]); 
		}
		if(!leftWall) {
			cellList.add(cells[posX-1][posY]);
		}
		if(!rightWall) {
			cellList.add(cells[posX+1][posY]);
		}
		return cellList.toArray(new Cell[cellList.size()]);
	}
	
	public int getNeighborMineNum(int posX, int posY) {
		int sumMines = 0;
		
		Cell[] cellNeighbors = this.getNeighbors(posX, posY);
		for(Cell c : cellNeighbors) {
			if(c.isMine()) {
				sumMines++;
			}
		}
		return sumMines;
	}

	public void recursiveOpen(Cell c, int prob) {
		if(!c.isMine()) {
			
			Cell[] cellNeighbors = this.getCardnalNeighbors(c.getPosX(), c.getPosY());
			
			for(int i = 0; i < cellNeighbors.length; i++) {
				if(r.nextInt(100) < prob) {
					c.openCell();
					if(!Config.FIRST_CLICK_RECURSIVE) {
						this.recursiveOpen(cellNeighbors[i], prob - Config.RECURSIVE_PROB_DIFF);
					}
					else {
						this.recursiveOpen(cellNeighbors[i], prob - Config.FIRST_RECURSIVE_PROB_DIFF);
					}
					
				}
			}
			
		}
		
		
	}
	
	private void closeAllCells() {//For Reset()
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				cells[i][j].setOpen(false);
			}
		}
	}

	public void lose() {
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				if(cells[i][j].isMine()) {
					cells[i][j].openCell();
					//this.clock.stop();
					Config.GAME_END = true;
				}
			}
		}
	}
	
	public void win() {//TODO
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				if(cells[i][j].isMine()) {
					cells[i][j].openCell();
					//this.clock.stop();
					Config.GAME_END = true;
					Config.WON_GAME = true;
				}
			}
		}
	}

	public void fillClosedCells() {
		for(int i = 0; i < this.sizeX; i++) {
			for(int j = 0; j < this.sizeY; j++) {
				if(!cells[i][j].isOpen()) {
					if(r.nextInt(100) < this.dificulity) {//Construct Cell with mine in it
						this.cells[i][j].setMine(true);
					}
					else {
						//this.cells[i][j].setMine(false);//Construct Cell with no mine
					}
				}
			}
		}
	}
	
}
