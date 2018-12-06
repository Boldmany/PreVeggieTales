package Main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OnKeyPressed implements EventHandler<KeyEvent>{
	public int i = 0;
	@Override
	public void handle(KeyEvent key) {
		
		if(key.getCode() == KeyCode.W) {
			i = (i + 1) % 8;
			MapItems.players()[0].setDir(i);
			MapItems.players()[0].rotation();
		}
		
	}

}
