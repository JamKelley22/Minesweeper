package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.image.VolatileImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clock.Updatable;
import objects.Cell;
import objects.Game;
import other.Config;
import other.Control;

public class View extends JFrame implements Updatable{
	
	private static final long serialVersionUID = 1L;
	
	private Graphics graphics;
	private VolatileImage image;
	
	private Game game;
	private Control control;
	
	private int lastXPos;
	private int lastYPos;
	
	public View(int width, int height, Game game) {
		this.setUndecorated(true);
		this.game = game;
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(width, height));
		this.add(jp);
		Control control = new Control(game, this, this);
		this.control = control;
		jp.addMouseListener(control);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		image = this.createVolatileImage(width, height);
		graphics = jp.getGraphics();
	}

	@Override
	public void update() {
		Graphics2D g2D = image.createGraphics();
		
		if(Config.ISMOVING) {//Moving the window
			int holdXPos = MouseInfo.getPointerInfo().getLocation().x;
			int holdYPos = MouseInfo.getPointerInfo().getLocation().y;
			
			System.out.println("(" + holdXPos + "," + holdYPos + ")");
			
			int xDiff = holdXPos - this.lastXPos;
			int yDiff = holdYPos - this.lastYPos;
			
			this.setLocation(this.getX() + xDiff, this.getY() + yDiff);
			
			this.lastXPos = holdXPos;
			this.lastYPos = holdYPos;
		}
			
		Cell[][] cells = game.getCells();
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				cells[i][j].fillCell(g2D, game);
			}
 		}
		
		//ToolBar
		int gamePixels = Config.CELL_DISTANCE * Config.GAME_SIZE;
		int xSizeOffset = 8;
		//Exit Button
		g2D.setColor(Color.RED);
		g2D.fill3DRect(gamePixels - Config.BUTTON_WIDTH, 0, Config.BUTTON_WIDTH, Config.Y_OFFSET, true);
		g2D.setColor(Color.GRAY.darker().darker());
		g2D.setFont(Config.TOOLBAR_FONT);
		g2D.drawString("x", gamePixels - (Config.BUTTON_WIDTH / 2) - xSizeOffset, Config.Y_OFFSET / 2  + xSizeOffset );
		
		//Minimize Button
		//frame.setState(Frame.ICONIFIED);
		g2D.setColor(Color.YELLOW);
		g2D.fill3DRect(gamePixels - (Config.BUTTON_WIDTH * 2), 0, Config.BUTTON_WIDTH, Config.Y_OFFSET, true);
		g2D.setColor(Color.GRAY.darker().darker());
		g2D.setFont(Config.TOOLBAR_FONT);
		g2D.drawString("-", gamePixels - (Config.BUTTON_WIDTH / 2) - (Config.BUTTON_WIDTH) - xSizeOffset, Config.Y_OFFSET / 2  + xSizeOffset );
		
		//Maximize Button
		g2D.setColor(Color.GREEN);
		g2D.fill3DRect(gamePixels - (Config.BUTTON_WIDTH * 3), 0, Config.BUTTON_WIDTH, Config.Y_OFFSET, true);
		g2D.setColor(Color.GRAY.darker().darker());
		g2D.setFont(Config.TOOLBAR_FONT);
		g2D.drawString("+", gamePixels - (Config.BUTTON_WIDTH / 2) - (Config.BUTTON_WIDTH * 2) - xSizeOffset, Config.Y_OFFSET / 2  + xSizeOffset );
		
		
		
		if(Config.GAME_END) {//End of game
			g2D.setColor(Color.BLACK);
			g2D.setFont(Config.GAME_OVER_FONT);
			int gameCenter = (int)((Config.GAME_SIZE * Config.CELL_DISTANCE) / 2.0);
			g2D.drawString("Game Over",gameCenter - (int)(Config.GAME_OVER_FONT.getSize() * 2.5), gameCenter + Config.Y_OFFSET);//Change numbers
		}
		graphics.drawImage(image, 0, 0, null);
		
		
	}

	public void setLastXPos(int lastXPos) {
		this.lastXPos = lastXPos;
	}

	public void setLastYPos(int lastYPos) {
		this.lastYPos = lastYPos;
	}
	
}
