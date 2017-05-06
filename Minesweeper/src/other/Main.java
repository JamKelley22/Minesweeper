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
		Game game = new Game();
		Runnable r = new Runnable() {
			  public void run() {
			    View gui = new View(500, 500, game);
			    try {
				      Thread.sleep(50);
				    } catch (InterruptedException e) {
				      e.printStackTrace();
				    }
				    Clock clock = new Clock(20.0f, gui);
				    clock.init();
			  }
			};
			SwingUtilities.invokeLater(r);
	}
}