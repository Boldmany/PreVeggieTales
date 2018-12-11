package Main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OnKeyPressed implements EventHandler<KeyEvent>{

	public void handle(KeyEvent key) {
		
		if(key.getCode() == KeyCode.W) {
			MapItems.players()[0].dir().setY(-1);
		}
		if(key.getCode() == KeyCode.S) {
			MapItems.players()[0].dir().setY(1);
		}
		if(key.getCode() == KeyCode.D) {
			MapItems.players()[0].dir().setX(1);
		}
		if(key.getCode() == KeyCode.A) {
			MapItems.players()[0].dir().setX(-1);
		}
	}
}
