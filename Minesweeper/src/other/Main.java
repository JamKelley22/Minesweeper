package other;

import javax.swing.SwingUtilities;

import clock.Clock;
import objects.Game;
import view.View;

public class Main {
	public static void main(String[] args) {
		//IO io = new IO();
		//Player player = new Player("Jameel");
		//Game g = io.makeGame(player);
		Game game = new Game(Config.GAME_SIZE,Config.GAME_SIZE,false,Config.DIFFICULITY);
		Runnable r = new Runnable() {
			  public void run() {
			    View gui = new View(Config.GAME_SIZE * Config.CELL_DISTANCE, 
			    		Config.GAME_SIZE * Config.CELL_DISTANCE + Config.Y_OFFSET, game);
			    try {
				      Thread.sleep(50);
				    } catch (InterruptedException e) {
				      e.printStackTrace();
				    }
				    Clock clock = new Clock(Config.FPS, gui);
				    //game.setClock(clock);
				    clock.init();
			  }
			};
			SwingUtilities.invokeLater(r);
	}
}