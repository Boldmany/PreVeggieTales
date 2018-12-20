package Main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OnKeyPressed implements EventHandler<KeyEvent>{

	public void handle(KeyEvent key) {
		
		if(key.getCode() == KeyCode.W) {
			Map.players()[0].dir().setY(-1);
		}
		if(key.getCode() == KeyCode.S) {
			Map.players()[0].dir().setY(1);
		}
		if(key.getCode() == KeyCode.D) {
			Map.players()[0].dir().setX(1);
		}
		if(key.getCode() == KeyCode.A) {
			Map.players()[0].dir().setX(-1);
		}
		if(key.getCode() == KeyCode.SPACE && Map.players()[0].cooldown().done()) {
			Map.players()[0].dash().setDone(false);
		}
	}
}
