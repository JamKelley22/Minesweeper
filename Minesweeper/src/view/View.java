package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
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
	
	public View(int width, int height, Game game) {
		this.setUndecorated(true);
		this.game = game;
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(width, height));
		this.add(jp);
		Control control = new Control(game, this);
		jp.addMouseListener(control);
		
		//this.addMouseListener(control);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		image = this.createVolatileImage(width, height);
		graphics = jp.getGraphics();
	}

	@Override
	public void update() {
		Graphics2D g2D = image.createGraphics();
		g2D.setColor(Color.WHITE);
		g2D.drawRect(0, 0, image.getWidth(), image.getHeight());
		
		
		g2D.setColor(Color.RED);
		g2D.fillRect(450, 0, 500, 25);
		
		
		g2D.setColor(Color.BLACK);
		for(int i = 1; i < 30; i++) {
			g2D.drawLine(i * Config.CELL_DISTANCE, 0, i * Config.CELL_DISTANCE, 525);
		}
		
		//game.getCurrLvl().update();
		//Room[][] rooms = game.getCurrLvl().getRooms();
//		for (int i = 0; i < rooms.length; i++) {
//			for(int j = 0; j < rooms[i].length; j++) {
//				Color c = rooms[i][j].getColor();//gen.createColor();//room.getColor();
//				g2D.setColor(c);
//				g2D.fill(rooms[i][j].getPolygon());
//				graphics.drawImage(image, 0, 0, null);
//				
//				Point p = getCenter(rooms[i][j].getPolygon());
//				g2D.setColor(Color.BLACK);
//			}
//		}
		//graphics.drawImage(image, 0, 0, null);
		Cell[][] cells = game.getCells();
		for(int i = 0; i < cells.length; i++) {
			for(int j = 0; j < cells[i].length; j++) {
				cells[i][j].fillCell(graphics);
			}
 		}
 		//TODO
		
		
	}
	
	private Point getCenter(Polygon p) {
		int x = 0;
		for (int i : p.xpoints) {
			x += i;
		}
		x /= p.xpoints.length;
		int y = 0;
		for (int i : p.ypoints) {
			y += i;
		}
		y /= p.ypoints.length;
		return new Point(x, y);
	}
}
