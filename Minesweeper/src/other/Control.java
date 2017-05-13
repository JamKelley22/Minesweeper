package other;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import objects.Cell;
import objects.Game;
import view.View;

public class Control implements MouseListener, MouseMotionListener{

	private Game game;
	private JFrame frame;
	private View view;
	
	public Control(Game game, JFrame frame, View view) {
		this.game = game;
		this.frame = frame;
		this.view = view;
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
		this.resetToolbarColors();
		
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
				Config.FIRST_CLICK = true;
			}
			else if((c != null) & leftClick && c.isFlag() == false) {//Open Cell
				Config.FIRST_CLICK = true;
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
				this.checkToolBar(clickXPos, true);
			}
			
			
			
			if(c != null) {//Debug
				System.out.println(c);
				
				System.out.println(game.getNeighborMineNum(c.getPosX(), c.getPosY()));
			}
		}
		else if(Config.GAME_END && !Config.ISMOVING) {
			if(leftClick && clickYPos < 0 && !Config.ISMOVING) {//TOOLBAR(WHEN GAME IS ENDED)
				this.checkToolBar(clickXPos, true);
			}
		}
		
		
		Config.ISMOVING = false;
	}

	private void checkToolBar(int clickXPos, boolean clicked) {
		if(clickXPos > (Config.CELL_DISTANCE * Config.GAME_SIZE) - Config.BUTTON_WIDTH) {//Exit button
			if(clicked) {
				System.exit(0);
			}
			else {
				view.setExitButtonColor(Config.EXIT_BUTTON_COLOR.darker());
				//Reset others
				view.setMinButtonColor(Config.MIN_BUTTON_COLOR);
				view.setMaxButtonColor(Config.MAX_BUTTON_COLOR);
				view.setResetButtonColor(Config.RESET_BUTTON_COLOR);
			}
		}
		else if(clickXPos > (Config.CELL_DISTANCE * Config.GAME_SIZE) - (Config.BUTTON_WIDTH * 2)) {//Minimize
			if(clicked) {
				frame.setState(Frame.ICONIFIED);
			}
			else {
				view.setMinButtonColor(Config.MIN_BUTTON_COLOR.darker());
				//Reset others
				view.setExitButtonColor(Config.EXIT_BUTTON_COLOR);
				view.setMaxButtonColor(Config.MAX_BUTTON_COLOR);
				view.setResetButtonColor(Config.RESET_BUTTON_COLOR);
			}
		}
		else if(clickXPos > (Config.CELL_DISTANCE * Config.GAME_SIZE) - (Config.BUTTON_WIDTH * 3)) {//Maximize
			if(clicked) {
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
			else {
				view.setMaxButtonColor(Config.MAX_BUTTON_COLOR.darker());
				//Reset others
				view.setExitButtonColor(Config.EXIT_BUTTON_COLOR);
				view.setMinButtonColor(Config.MIN_BUTTON_COLOR);
				view.setResetButtonColor(Config.RESET_BUTTON_COLOR);
			}
		}
		else if(clickXPos > (Config.CELL_DISTANCE * Config.GAME_SIZE) - (Config.BUTTON_WIDTH * 4)) {//RESET GAME
			if(clicked) {
				game.reset();
				System.out.println("RESET");
			}
			else {
				view.setResetButtonColor(Config.RESET_BUTTON_COLOR.darker());
				//Reset others
				view.setExitButtonColor(Config.EXIT_BUTTON_COLOR);
				view.setMinButtonColor(Config.MIN_BUTTON_COLOR);
				view.setMaxButtonColor(Config.MAX_BUTTON_COLOR);
			}
		}
		else {
			this.resetToolbarColors();//Left of toolbar buttons
			
		}
		//TODO add game size select
	}
	
	private void resetToolbarColors() {
		view.setExitButtonColor(Config.EXIT_BUTTON_COLOR);
		view.setMinButtonColor(Config.MIN_BUTTON_COLOR);
		view.setMaxButtonColor(Config.MAX_BUTTON_COLOR);
		view.setResetButtonColor(Config.RESET_BUTTON_COLOR);
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int hoverYPos = Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).y - Config.Y_OFFSET;
		int hoverXPos = 0;
		if(hoverYPos < 0) {
			hoverXPos = Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).x;
			this.checkToolBar(hoverXPos,false);
		}
		else {
			this.resetToolbarColors();
		}
		System.out.println("XPos: " + hoverXPos + "  |  YPos: " + hoverYPos);
		
		
	}

}
