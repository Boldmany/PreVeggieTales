package Main;

import Character.Player;
import MapObjects.Disk;
import MapObjects.Laser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameLoop implements EventHandler<ActionEvent> {

	private static Color pink = Color.rgb(252,31,109); // this is the color used for the objects that deal damage
	private static Color warning = Color.rgb(93,35,71); // this is the color used for the warnings 
	public Player pineapple = new Player(new Vector(500,300) , new Image("file:resources/pineapple.png"), new Image("file:resources/damagedPineapple.png")); // this instantiates the pineapple
	public Image victory = new Image("file:resources/winscreen.png");
	public Image death = new Image("file:resources/death.png");

	/**
	 * this will run 60 times a second
	 */
	public void handle(ActionEvent ev) {
		
		if(Map.levels()[Map.playLevel()].frames() == 4150) { // if you reach 4150 frames
			Map.setDiskSize(0); // remove all disks
		}
		
		Main.gc().clearRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight()); // clear the canvas

		if(Main.gameState() == 0) { // this is the menu screen
			Main.gc().setFill(Color.BLACK);
			Main.gc().setFont(new Font(20));
			Main.gc().fillText("First player use WASD to move around and space bar to dash. \n \n"
					+ "Click the shift button to play 2 players. \n"
					+ (Map.playerSize() == 1 ? "\n" : "Second player use the arrow keys to move around and shift key to dash. \n \n")
					+ "Dashing gives you invincibility frames during and after the dash \n"
					+ "You have 15 lives. They will be displayed on the top corners of the screen (rgiht side pineapple, left side is apple) \n"
					+ "The next Level will autimatically start once you win. \n"
					+ "Dodge all the pink objects and get to the end of the song to win.", 10, 30);
			Main.gc().setFont(new Font(40));
			Main.gc().fillText("Click the \"\\\" to start", 10, 630);
		}
		else if (Main.gameState() == 1){
			Map.levels()[Map.playLevel()].delayCheck(); // this will run the delay check for the level

			if(Map.levels()[Map.playLevel()].frames() == 0) { // if the frame counter reaches 0
				Map.levels()[Map.playLevel()].clip().setMicrosecondPosition(0); // reset the song, just in case
				Map.levels()[Map.playLevel()].clip().start(); // start playing the song
			}

			if(Map.safeDiskSize() == 0) { // if there are no safe disks
				Main.gc().setFill(Color.BLACK); // make the background 
				Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
			}
			else {
				if(Map.safeDisks()[0].delay().done()) { // if there is a safe disk in action
					Main.gc().setFill(pink);
				}
				else {
					Main.gc().setFill(warning);
				}
				Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight()); // fill the entire screen with that color

				Main.gc().setFill(Color.BLACK);
				for(int i = 0; i < Map.safeDiskSize(); i++) { // for each safe disk
					Disk startDisk = Map.safeDisks()[i]; // for later use
					Map.safeDisks()[i].delayCheck(); // delay check
					if(Map.safeDisks()[i] != startDisk) { // if it was removed after the delay check
						i--;
					}
					else if(Map.safeDiskSize() != 0) {
						// draw the safe disk circle
						Main.gc().fillOval(Map.safeDisks()[i].coord().x() - Map.safeDisks()[i].currentRadius() + Map.levels()[Map.playLevel()].shake().degree().x(), 
								Map.safeDisks()[i].coord().y() - Map.safeDisks()[i].currentRadius() + Map.levels()[Map.playLevel()].shake().degree().y(),
								Map.safeDisks()[i].currentRadius() * 2, Map.safeDisks()[i].currentRadius() * 2);
					}

					if(Map.safeDiskSize() == 0) { // if there are no more safe circles
						Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight()); // fill everything with black
					}
				}
			}

			Main.gc().setFill(warning);

			// the next few lines will draw out both the laser and disk warnings
			if(Map.safeDiskSize() == 0) {
				for(int i = 0; i < Map.ghostLaserSize(); i++) {
					Main.gc().fillRect(Map.ghostLasers()[i].coord().x() + Map.levels()[Map.playLevel()].shake().degree().x(), Map.ghostLasers()[i].coord().y() + Map.levels()[Map.playLevel()].shake().degree().y(),
							Map.ghostLasers()[i].width(), Map.ghostLasers()[i].height());
				}
				for(int i = 0; i < Map.ghostDiskSize(); i++) { 
					Main.gc().fillOval(Map.ghostDisks()[i].coord().x() - Map.ghostDisks()[i].currentRadius() + Map.levels()[Map.playLevel()].shake().degree().x(),
							Map.ghostDisks()[i].coord().y() - Map.ghostDisks()[i].currentRadius() + Map.levels()[Map.playLevel()].shake().degree().y(),
							Map.ghostDisks()[i].currentRadius() * 2, Map.ghostDisks()[i].currentRadius() * 2);
				}
			}

			// the next few lines will draw out both the lasers and disks 
			Main.gc().setFill(pink);
			for(int i = 0; i < Map.laserSize(); i++) {
				// this will make sure that no laser has been removed after the delay check so that it can be drawn onto the screen
				Laser laser = Map.lasers()[i];
				Map.lasers()[i].delayCheck();
				if(laser == Map.lasers()[i]) {
					if(Map.lasers()[i].delay().done()) {
						Main.gc().fillRect(Map.lasers()[i].coord().x() + Map.levels()[Map.playLevel()].shake().degree().x(), Map.lasers()[i].coord().y() + Map.levels()[Map.playLevel()].shake().degree().y(), 
								Map.lasers()[i].width(), Map.lasers()[i].height());

					}
				}
				else {
					i -= 1;
				}
			}
			for(int i = 0; i < Map.diskSize(); i++) {
				// this will make sure that no disk has been removed after the delay check so that it can be drawn onto the screen
				Disk disk = Map.disks()[i];
				Map.disks()[i].delayCheck();
				if(disk == Map.disks()[i]) {
					if(Map.disks()[i].delay().done()) {
						Main.gc().fillOval(Map.disks()[i].coord().x() - Map.disks()[i].currentRadius() + Map.levels()[Map.playLevel()].shake().degree().x(), 
								Map.disks()[i].coord().y() - Map.disks()[i].currentRadius() + Map.levels()[Map.playLevel()].shake().degree().y(),
								Map.disks()[i].currentRadius() * 2, Map.disks()[i].currentRadius() * 2);
					}
				}
				else {
					i --;
				}
			}
		}
		else if(Main.gameState() == 2){
			Main.gc().drawImage(victory, 0, 0); // victory screen
		}
		else {
			Main.gc().drawImage(death, 0, 0); // death screen
		}
		for(int i = 0; i < Map.playerSize(); i++) { // this will draw the characters at an angle based on how they are moving 
			if(Map.players()[i].alive()) {
				Map.players()[i].delayCheck();
				Main.gc().save();
				Main.gc().transform(Map.players()[i].rotate().getMxx(), Map.players()[i].rotate().getMyx(), Map.players()[i].rotate().getMxy(), 
						Map.players()[i].rotate().getMyy(), Map.players()[i].rotate().getTx(), Map.players()[i].rotate().getTy());
				Main.gc().drawImage(Map.players()[i].img(), Map.players()[i].coord().x() - 13 + Map.levels()[Map.playLevel()].shake().degree().x(), 
						Map.players()[i].coord().y() - 25 + Map.levels()[Map.playLevel()].shake().degree().y());
				Main.gc().restore();
			}
		}
		SafeZone.update(); // this will update all items within the safe zone
		
		// the next few lines are for showing the amount of lives
		Main.gc().setFill(Color.WHITE);
		if(Map.players()[0].lives() > 0) {
			Main.gc().fillText(String.valueOf(Map.players()[0].lives()), 950, 50);
		}
		if(Map.playerSize() == 2) {
			if(Map.players()[1].lives() > 0) {
				Main.gc().fillText(String.valueOf(Map.players()[1].lives()), 0, 50);
			}
		}
	}
}
