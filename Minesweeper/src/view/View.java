package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	
	private int frameCounter = 0;
	private int timeSec1 = 0;
	private int timeSec10 = 0;
	private int timeSec100 = 0;
	
	
	private Color exitButtonColor = Config.EXIT_BUTTON_COLOR;
	private Color minButtonColor = Config.MIN_BUTTON_COLOR;
	private Color maxButtonColor = Config.MAX_BUTTON_COLOR;
	private Color resetButtonColor = Config.RESET_BUTTON_COLOR;
	
	public View(int width, int height, Game game) {
		this.setUndecorated(true);
		this.game = game;
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(width, height));
		this.add(jp);
		Control control = new Control(game, this, this);
		this.control = control;
		jp.addMouseListener(control);
		jp.addMouseMotionListener(control);
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
		
		if(!Config.GAME_END) {
			this.drawTime(g2D);
		}
		
		this.drawButton(0, "x", exitButtonColor, g2D);//Exit Button
		this.drawButton(1, "-", minButtonColor, g2D);//Minimize Button
		this.drawButton(2, "+", maxButtonColor, g2D);//Maximize Button
		this.drawButton(3, "r", resetButtonColor, g2D);//RESET BUTTON
		
		
		if(Config.GAME_END) {//End of game
			g2D.setColor(Color.BLACK);
			g2D.setFont(Config.GAME_OVER_FONT);
			int gameCenter = (int)((Config.GAME_SIZE * Config.CELL_DISTANCE) / 2.0);
			
//			g2D.fill3DRect(gameCenter - (int)(Config.GAME_OVER_FONT.getSize() * 2.5), gameCenter + Config.Y_OFFSET - (int)(Config.GAME_SIZE * 3) + 5, (int)(Config.GAME_SIZE * 15), (int)(Config.GAME_SIZE * 3), true);
//			String endMessage;
//			if(Config.WON_GAME) {
//				g2D.setColor(Color.GREEN);
//				endMessage = "YOU WIN!";
//			}
//			else {
//				g2D.setColor(Color.RED);
//				endMessage = "Game Over";
//			}
			
			
//			g2D.drawString(endMessage,gameCenter - (int)(Config.GAME_OVER_FONT.getSize() * 2.5), gameCenter + Config.Y_OFFSET);//Change numbers
		}
		
		
		graphics.drawImage(image, 0, 0, null);
		
		
		frameCounter++;
	}

	public void setLastXPos(int lastXPos) {
		this.lastXPos = lastXPos;
	}

	public void setLastYPos(int lastYPos) {
		this.lastYPos = lastYPos;
	}
	
	private void drawButton(int buttonNum, String buttonSymbol, Color buttonColor, Graphics g2D) {
		buttonNum++;
		int gamePixels = Config.CELL_DISTANCE * Config.GAME_SIZE;
		int xSizeOffset = 5;
		
		g2D.setColor(buttonColor);
		g2D.fill3DRect(gamePixels - (Config.BUTTON_WIDTH * buttonNum), 0, Config.BUTTON_WIDTH, Config.Y_OFFSET,true);
		g2D.setColor(Color.GRAY.darker().darker());
		g2D.setFont(Config.TOOLBAR_FONT);
		g2D.drawString(buttonSymbol, 
				gamePixels - (Config.BUTTON_WIDTH / 2)  - (Config.BUTTON_WIDTH * (buttonNum - 1)) - xSizeOffset, 
				Config.Y_OFFSET / 2  + xSizeOffset );
	}

	private void drawTime(Graphics g2D) {
		int yCenteringText = 18;
		int xShift = Config.CELL_DISTANCE * 8;
		int xDrawText = (int)(Config.CELL_DISTANCE) + 4;
		
		g2D.setColor(Config.TOOLBAR_COLOR);
		g2D.fillRect(0, 0, this.getWidth(), Config.Y_OFFSET);//Color fill
		
		g2D.setColor(Color.DARK_GRAY.darker());
		for(int i = 0; i < 3; i++) {
			g2D.fillRect(i * Config.CELL_DISTANCE + xShift, 0, Config.CELL_DISTANCE, Config.Y_OFFSET);
		}
		
		g2D.setColor(Color.WHITE);
		
		g2D.drawLine(Config.CELL_DISTANCE + xShift, 0, Config.CELL_DISTANCE + xShift, Config.Y_OFFSET);
		g2D.drawLine((Config.CELL_DISTANCE * 2) + xShift, 0, (Config.CELL_DISTANCE * 2) + xShift, Config.Y_OFFSET);
		
		if(this.frameCounter >= Config.FPS) {
			this.frameCounter = 0;
			if(!(timeSec1 == 9 && timeSec10 == 9 && timeSec100 == 9) && Config.FIRST_CLICK) {
				this.timeSec1++;
				if(timeSec1 >= 10) {
					timeSec1 = 0;
					timeSec10++;
					if(timeSec10 >= 10) {
						timeSec10 = 0;
						if(timeSec100 < 9) {
							timeSec100++;
						}
						
					}
				}
			}
		}
		g2D.setFont(Config.TIME_FONT);
		g2D.drawString(Integer.toString(this.timeSec100), xDrawText - Config.CELL_DISTANCE + xShift, yCenteringText);
		g2D.drawString(Integer.toString(this.timeSec10), xDrawText + xShift, yCenteringText);
		g2D.drawString(Integer.toString(this.timeSec1), xDrawText + Config.CELL_DISTANCE + xShift, yCenteringText);
	}
	
	public void setExitButtonColor(Color exitButtonColor) {
		this.exitButtonColor = exitButtonColor;
	}

	public void setMinButtonColor(Color minButtonColor) {
		this.minButtonColor = minButtonColor;
	}

	public void setMaxButtonColor(Color maxButtonColor) {
		this.maxButtonColor = maxButtonColor;
	}

	public void setResetButtonColor(Color resetButtonColor) {
		this.resetButtonColor = resetButtonColor;
	}
	
	public void resetClock() {
		this.timeSec1 = 0;
		this.timeSec10 = 0;
		this.timeSec100 = 0;
	}
	
}
