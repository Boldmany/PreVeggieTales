package Main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OnKeyReleased implements EventHandler<KeyEvent>{

	/**
	 * when you release a key
	 */
	public void handle(KeyEvent key) {
		
		if(key.getCode() == KeyCode.W && Map.players()[0].dir().y() == -1) { // if released w key and moving up
			Map.players()[0].dir().setY(0); // no longer moving up
		}
		if(key.getCode() == KeyCode.S && Map.players()[0].dir().y() == 1) { // if released s key and moving down
			Map.players()[0].dir().setY(0); // no longer moving down
		}
		if(key.getCode() == KeyCode.D && Map.players()[0].dir().x() == 1) { // if released d key and moving right
			Map.players()[0].dir().setX(0); // no longer moving right
		}
		if(key.getCode() == KeyCode.A && Map.players()[0].dir().x() == -1) { // if released a key and moving left 
			Map.players()[0].dir().setX(0); // no longer moving left
		}
		
		// just like player one, except using arrow keys
		if(Map.playerSize() == 2) {
			if(key.getCode() == KeyCode.UP && Map.players()[1].dir().y() == -1) {
				Map.players()[1].dir().setY(0);
			}
			if(key.getCode() == KeyCode.DOWN && Map.players()[1].dir().y() == 1) {
				Map.players()[1].dir().setY(0);
			}
			if(key.getCode() == KeyCode.RIGHT && Map.players()[1].dir().x() == 1) {
				Map.players()[1].dir().setX(0);
			}
			if(key.getCode() == KeyCode.LEFT && Map.players()[1].dir().x() == -1) {
				Map.players()[1].dir().setX(0);
			}
		}
	}
}
