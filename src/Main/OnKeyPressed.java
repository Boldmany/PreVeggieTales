package Main;

import Character.Player;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OnKeyPressed implements EventHandler<KeyEvent>{

	/**
	 * when you click a key
	 */
	public void handle(KeyEvent key) {
		
		if(Map.players()[0].alive()) { // this is for player 1 / pineapple
			if(key.getCode() == KeyCode.W) { // if w key is pressed
				Map.players()[0].dir().setY(-1); // moving up
			}
			if(key.getCode() == KeyCode.S) { // if s key is pressed
				Map.players()[0].dir().setY(1); // moving down
			}
			if(key.getCode() == KeyCode.D) { // if d key is pressed
				Map.players()[0].dir().setX(1); // moving right
			}
			if(key.getCode() == KeyCode.A) { // if a key is pressed
				Map.players()[0].dir().setX(-1); // moving left
			}
			if(key.getCode() == KeyCode.SPACE && Map.players()[0].cooldown().done()) { // if key bar is pressed and cool down for dash is done
				Map.players()[0].dash().setDone(false); // dash
			}
		}
		
		if(Main.gameState() == 0 || Main.gameState() == 3) {
			if(key.getCode() == KeyCode.BACK_SLASH) { // if back slash is clicked
				if(Main.gameState() == 3) {
					Player.death();
				}
				Main.setGameState(1); // start the game
			}
			if(key.getCode() == KeyCode.SHIFT && Map.playerSize() == 1 && Main.gameState() == 0) { // if shift key is clicked
				new Player(new Vector(600,300) , new Image("file:resources/apple.png"), new Image("file:resources/damagedApple.png")); // add player 2
			}
		}
		
		//just like player one, except using arrow keys
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
				if(key.getCode() == KeyCode.SHIFT && Map.players()[1].cooldown().done()) {
					Map.players()[1].dash().setDone(false);
				}
			}
		}
	}
}
