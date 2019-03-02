package Main;

import Character.Player;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OnKeyPressed implements EventHandler<KeyEvent>{

	public void handle(KeyEvent key) {
		
		if(Map.players()[0].alive()) {
			if(key.getCode() == KeyCode.W && Map.players()[0].alive()) {
				Map.players()[0].dir().setY(-1);
			}
			if(key.getCode() == KeyCode.S && Map.players()[0].alive()) {
				Map.players()[0].dir().setY(1);
			}
			if(key.getCode() == KeyCode.D && Map.players()[0].alive()) {
				Map.players()[0].dir().setX(1);
			}
			if(key.getCode() == KeyCode.A && Map.players()[0].alive()) {
				Map.players()[0].dir().setX(-1);
			}
			if(key.getCode() == KeyCode.SPACE && Map.players()[0].cooldown().done() && Map.players()[0].alive()) {
				Map.players()[0].dash().setDone(false);
			}
		}
		
		if(!Main.playing()) {
			if(key.getCode() == KeyCode.BACK_SLASH) {
				Main.setPlaying(true);
				Map.levels()[0].clip().start();
				
			}
			if(key.getCode() == KeyCode.SHIFT && Map.playerSize() == 1) {
				new Player(new Vector(600,300) , new Image("file:resources/apple.png"), new Image("file:resources/damaged.png"));
			}
		}
		
		if(Map.playerSize() == 2) {
			if(Map.players()[1].alive()) {
				if(key.getCode() == KeyCode.UP ) {
					Map.players()[1].dir().setY(-1);
				}
				if(key.getCode() == KeyCode.DOWN) {
					Map.players()[1].dir().setY(1);
				}
				if(key.getCode() == KeyCode.RIGHT) {
					Map.players()[1].dir().setX(1);
				}
				if(key.getCode() == KeyCode.LEFT) {
					Map.players()[1].dir().setX(-1);
				}
				if(key.getCode() == KeyCode.SHIFT && Map.players()[0].cooldown().done()) {
					Map.players()[1].dash().setDone(false);
				}
			}
		}
	}
}
