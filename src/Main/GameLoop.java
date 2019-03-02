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

	private static Color pink = Color.rgb(252,31,109);
	private static Color warning = Color.rgb(93,35,71); 
	public Player pineapple = new Player(new Vector(500,300) , new Image("file:resources/pineapple.png"), new Image("file:resources/damaged.png"));
	

	public void handle(ActionEvent ev) {
		Main.gc().clearRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		if(!Main.playing()) {
			Main.gc().setFont(new Font(20));
			Main.gc().fillText("First player use WASD to move around and space bar to dash. \n \n"
					+ "Click the shift button to play 2 players. \n"
					+ (Map.playerSize() == 1 ? "\n" : "Second player use the arrow keys to move around and shift key to dash. \n \n")
					+ "You have 7 lives. \n"
					+ "The next Level will autimatically start once you win. \n"
					+ "Dodge all the pink objects and get to the end of the song to win. \n"
					+ "There will be checkpoints throught the level.", 10, 30);
			Main.gc().setFont(new Font(40));
			Main.gc().fillText("Click the \"\\\" to start", 10, 630);
		}
		else {
			Map.levels()[Map.playLevel()].delayCheck();
	
			
	
			if(Map.safeDiskSize() == 0) {
				Main.gc().setFill(Color.BLACK);
				Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
			}
			else {
				if(Map.safeDisks()[0].delay().done()) {
					Main.gc().setFill(pink);
				}
				else {
					Main.gc().setFill(warning);
				}
				Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
	
				Main.gc().setFill(Color.BLACK);
				for(int i = 0; i < Map.safeDiskSize(); i++) {
					Disk startDisk = Map.safeDisks()[i];
					Map.safeDisks()[i].delayCheck();
					if(Map.safeDisks()[i] != startDisk) {
						i--;
					}
					else if(Map.safeDiskSize() != 0) {
						Main.gc().fillOval(Map.safeDisks()[i].coord().x() - Map.safeDisks()[i].currentRadius() + Map.levels()[Map.playLevel()].shake().degree().x(), 
								Map.safeDisks()[i].coord().y() - Map.safeDisks()[i].currentRadius() + Map.levels()[Map.playLevel()].shake().degree().y(),
								Map.safeDisks()[i].currentRadius() * 2, Map.safeDisks()[i].currentRadius() * 2);
					}
					
					if(Map.safeDiskSize() == 0) {
						Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
					}
				}
			}
	
			Main.gc().setFill(warning);
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
	
			Main.gc().setFill(pink);
			for(int i = 0; i < Map.laserSize(); i++) {
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
		for(int i = 0; i < Map.playerSize(); i++) {
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
		SafeZone.update();
	}
}
