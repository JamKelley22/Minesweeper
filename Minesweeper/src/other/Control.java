package other;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import objects.Cell;
import objects.Game;

public class Control implements MouseListener{

	private Game game;
	private JFrame frame;
	
	public Control(Game game, JFrame frame) {
		this.game = game;
		this.frame = frame;
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
		
		if(rightClick) {
			//System.out.println(Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()));
			if(Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).y < 0) { 
				if(Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).x > 450) {
					System.exit(0);
				}
				
			}
		}
		if(leftClick) {
			//System.out.println(MouseInfo.getPointerInfo().getLocation());
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(!Config.GAME_END) {
			boolean leftClick = SwingUtilities.isLeftMouseButton(arg0);
			boolean rightClick = SwingUtilities.isRightMouseButton(arg0);
			boolean cellIsMine = false;
			
			
			Cell c = game.getClosestCell(Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).x, 
					Config.subtract(MouseInfo.getPointerInfo().getLocation(), frame.getLocationOnScreen()).y);
			cellIsMine = c.isMine();
			if(rightClick)
				c.swapFlagState();
			else if(leftClick && c.isFlag() == false) {//Open Cell
				if(c.openCell() && !c.isMine()) {
					if(Config.FIRST_RECURSIVE) {
						game.recursiveOpen(c, Config.FIRST_OPEN_NEIGHBORS_PROB);
						Config.FIRST_RECURSIVE = false;
					}
					else {
						game.recursiveOpen(c, Config.OPEN_NEIGHBORS_PROB);
					}
				}
				else if(c.isMine()) {
					game.lose();
				}
			}
			
			System.out.println(c);
			
			System.out.println(game.getNeighborMineNum(c.getPosX(), c.getPosY()));
		}
		
	}

}
