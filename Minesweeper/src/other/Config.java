package other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.Random;

public class Config {
	
	public static final Random RANDOM = new Random();
	
	
	public static final int CELL_DISTANCE = 20;//In pixels
	
	public static final int DIFFICULITY = 25;
	
	public static final int GAME_SIZE = 20;
	
	public static final float FPS = 60.0f;
	
	
	public static final int Y_OFFSET = 20;
	
	public static final int FLAG_DIFF = 5;
	
	public static final int FLAG_SIZE = CELL_DISTANCE - FLAG_DIFF * 2;//In pixels
	
	
	public static final Color CELL_COLOR_CLOSED = Color.GRAY.brighter();

	public static final Color CELL_COLOR_OPENED = Color.GRAY;

	public static final Color FLAG_COLOR = Color.BLUE;

	public static final Color MINE_NUM_COLOR = Color.BLACK;
	
	public static final Color CELL_COLOR_MINE = Color.RED.brighter();
	
	public static final Color TOOLBAR_COLOR = Color.GRAY.brighter();
	

	public static final int BUTTON_WIDTH = (int)(Config.CELL_DISTANCE * 2);
	
	public static final int FONT_SIZE = 20;
	
	
	public static final Font GAME_OVER_FONT = new Font("TimesRoman", Font.BOLD, (int)(Config.GAME_SIZE * 3)); 

	public static final Font TOOLBAR_FONT = new Font("Dialog", Font.PLAIN, 20);
	
	public static final Font TIME_FONT = new Font("Dialog", Font.PLAIN, 20);
	
	
	public static final int FIRST_OPEN_NEIGHBORS_PROB = 100;
	
	public static final int OPEN_NEIGHBORS_PROB = 50;

	public static final int RECURSIVE_PROB_DIFF = 15;//Ammount the pobibility of opening more spaces goes down each cell opened

	public static final int FIRST_RECURSIVE_PROB_DIFF = 25;//More spaces will open with first click


	public static boolean FIRST_CLICK = false;

	

	public static boolean FIRST_CLICK_RECURSIVE = true;
	
	public static boolean FIRST_RECURSIVE = true;
	
	public static boolean IS_MAX_FRAME = false;
	
	public static boolean GAME_END = false;

	public static boolean ISMOVING = false;


	public static boolean WON_GAME = false;


	public static Color EXIT_BUTTON_COLOR = Color.RED;
	
	public static Color MIN_BUTTON_COLOR = Color.YELLOW;
	
	public static Color MAX_BUTTON_COLOR = Color.GREEN;
	
	public static Color RESET_BUTTON_COLOR = Color.GRAY.brighter();
	
	
	public static Point subtract(Point p1, Point p2) {
	    return new Point(p1.x - p2.x, p1.y - p2.y);//-25 to adjust for close screen offset
	}
	
	public static void resetVars() {
		Config.FIRST_CLICK_RECURSIVE = true;
		Config.FIRST_RECURSIVE = true;
		Config.IS_MAX_FRAME = false;
		Config.GAME_END = false;
		Config.WON_GAME = false;
	}
}
