package objects;

public class Cell {
	
	private int posX;
	private int posY;
	private boolean mine;
	private boolean flag;
	
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
}
