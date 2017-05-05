package other;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import objects.Game;

public class Control implements MouseListener{

	private Game game;
	
	public Control(Game game) {
		this.game = game;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		boolean leftClick = SwingUtilities.isLeftMouseButton(arg0);
		boolean rightClick = SwingUtilities.isRightMouseButton(arg0);
	}

}
