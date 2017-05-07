package other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.Random;

public class Config {
	
	public static final Random RANDOM = new Random();
	
	public static final int CELL_DISTANCE = 20;//In pixels
	
	public static final int FLAG_DIFF = 5;
	
	public static final int FLAG_SIZE = CELL_DISTANCE - FLAG_DIFF * 2;//In pixels
	
	public static final Color CELL_COLOR_CLOSED = Color.GRAY.brighter();

	public static final Color CELL_COLOR_OPENED = Color.GRAY;

	public static final Color FLAG_COLOR = Color.RED;

	public static final Color MINE_NUM_COLOR = Color.BLACK;

	public static final int FONT_SIZE = 20;
	
	public static final Font GAME_OVER_FONT = new Font("TimesRoman", Font.BOLD, 50); 

	public static final Color CELL_COLOR_MINE = Color.RED.brighter();

	public static final int DIFFICULITY = 40;
	
	public static final int FIRST_OPEN_NEIGHBORS_PROB = 25;
	
	public static final int OPEN_NEIGHBORS_PROB = 15;

	public static final int RECURSIVE_PROB_DIFF = 2;//Ammount the pobibility of opening more spaces goes down each cell opened
	
	public static boolean FIRST_RECURSIVE = true;
	
	public static boolean GAME_END = false;
	
	public static Point subtract(Point p1, Point p2) {
	    return new Point(p1.x - p2.x, p1.y - p2.y);//-25 to adjust for close screen offset
	}
}
