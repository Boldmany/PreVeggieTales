package Main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OnKeyReleased implements EventHandler<KeyEvent>{

	@Override
	public void handle(KeyEvent key) {
		
		if(key.getCode() == KeyCode.W && Map.players()[0].dir().y() == -1) {
			Map.players()[0].dir().setY(0);
		}
		if(key.getCode() == KeyCode.S && Map.players()[0].dir().y() == 1) {
			Map.players()[0].dir().setY(0);
		}
		if(key.getCode() == KeyCode.D && Map.players()[0].dir().x() == 1) {
			Map.players()[0].dir().setX(0);
		}
		if(key.getCode() == KeyCode.A && Map.players()[0].dir().x() == -1) {
			Map.players()[0].dir().setX(0);
		}
	}
}
