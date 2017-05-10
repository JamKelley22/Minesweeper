package other;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import objects.Cell;
import objects.Game;
import view.View;

public class Control implements MouseListener{

	private Game game;
	private JFrame frame;
	private View view;
	
	public Control(Game game, JFrame frame, View view) {
		this.game = game;
		this.frame = frame;
		this.view = view;
	}
	
	public void setView() {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		boolean leftClick = SwingUtilities.isLeftMouseButton(arg0);
		boolean rightClick = SwingUtilities.isRightMouseButton(arg0);
		
		int clickXPos = Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).x;
		int clickYPos = Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).y - Config.Y_OFFSET;
		
		if(leftClick && clickYPos < 0 && clickXPos <= (Config.CELL_DISTANCE * Config.GAME_SIZE) - (Config.BUTTON_WIDTH * 4) ) {
			Config.ISMOVING = true;
			view.setLastXPos(MouseInfo.getPointerInfo().getLocation().x);
			view.setLastYPos(MouseInfo.getPointerInfo().getLocation().y);
			System.out.println("MOVE");
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		boolean leftClick = SwingUtilities.isLeftMouseButton(arg0);
		boolean rightClick = SwingUtilities.isRightMouseButton(arg0);
		
		int clickXPos = Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).x;
		int clickYPos = Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).y - Config.Y_OFFSET;
		
		
		
		if(!Config.GAME_END && !Config.ISMOVING) {
			
			boolean cellIsMine = false;
			
			
			
			Cell c = game.getClosestCell(clickXPos, clickYPos);
			//cellIsMine = c.isMine();
			
			
			
			if(rightClick && c != null && !c.isOpen()) {//SWAP FLAG
				c.swapFlagState();
			}
			else if((c != null) & leftClick && c.isFlag() == false) {//Open Cell
				if(c.openCell() && !c.isMine()) {
					if(Config.FIRST_RECURSIVE) {
						game.fillClosedCells();//fill cells
						game.recursiveOpen(c, Config.FIRST_OPEN_NEIGHBORS_PROB);
						Config.FIRST_RECURSIVE = false;
						Config.FIRST_CLICK_RECURSIVE = false;
						
					}
					else {
						game.recursiveOpen(c, Config.OPEN_NEIGHBORS_PROB);
					}
				}
				else if(c.isMine()) {
					game.lose();
				}
			}
			
			boolean wonGame = this.checkWin();
			
			if(wonGame) {
				game.win();
			}
			
			if(leftClick && clickYPos < 0 && !Config.ISMOVING) {//TOOLBAR
				this.checkToolBar(clickXPos);
			}
			
			if(c != null) {//Debug
				System.out.println(c);
				
				System.out.println(game.getNeighborMineNum(c.getPosX(), c.getPosY()));
			}
		}
		else if(Config.GAME_END && !Config.ISMOVING) {
			if(leftClick && clickYPos < 0 && !Config.ISMOVING) {//TOOLBAR
				this.checkToolBar(clickXPos);
			}
		}
		
		
		Config.ISMOVING = false;
	}

	private void checkToolBar(int clickXPos) {
		if(clickXPos > (Config.CELL_DISTANCE * Config.GAME_SIZE) - Config.BUTTON_WIDTH) {//Exit button
			System.exit(0);
		}
		else if(clickXPos > (Config.CELL_DISTANCE * Config.GAME_SIZE) - (Config.BUTTON_WIDTH * 2)) {//Minimize
			frame.setState(Frame.ICONIFIED);
		}
		else if(clickXPos > (Config.CELL_DISTANCE * Config.GAME_SIZE) - (Config.BUTTON_WIDTH * 3)) {//Maximize
			if(Config.IS_MAX_FRAME) {
				frame.setExtendedState(JFrame.NORMAL);
				Config.IS_MAX_FRAME = false;
			}
			else {
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				Config.IS_MAX_FRAME = true;
			}
			//System.out.println("MAX");
		}
		else if(clickXPos > (Config.CELL_DISTANCE * Config.GAME_SIZE) - (Config.BUTTON_WIDTH * 4)) {//RESET GAME
			game.reset();
			System.out.println("RESET");
		}
	}
	
	private boolean checkWin() {
		Cell[][] cells = game.getCells();
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				if((!cells[i][j].isOpen() && !cells[i][j].isFlag())) {
					return false;
				}
			}
		}
		return true;
	}

}
