package Main;

import Character.Pineapple;
import MapObjects.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class GameLoop implements EventHandler<ActionEvent> {

	private static Color pink = Color.rgb(252,31,109);
	private static Color warning = Color.rgb(93,35,71); 
	public Pineapple man = new Pineapple(new Vector(500,300));

	public void handle(ActionEvent ev) {

		if(!Map.levels()[Map.playLevel()].clip().isRunning()) {
			Map.levels()[Map.playLevel()].clip().start();
		}
		if(Map.levels()[Map.playLevel()].frames() == 1000) {
			System.out.println(Map.levels()[Map.playLevel()].clip().getFramePosition());
		}
		Map.levels()[Map.playLevel()].delayCheck();

		Main.gc().clearRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());

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
					Main.gc().fillOval(Map.safeDisks()[i].coord().x() - Map.safeDisks()[i].currentRadius(), Map.safeDisks()[i].coord().y() - Map.safeDisks()[i].currentRadius(),
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
				Main.gc().fillRect(Map.ghostLasers()[i].coord().x(), Map.ghostLasers()[i].coord().y(),
						Map.ghostLasers()[i].width(), Map.ghostLasers()[i].height());
			}
			for(int i = 0; i < Map.ghostDiskSize(); i++) {
				Main.gc().fillOval(Map.ghostDisks()[i].coord().x() - Map.ghostDisks()[i].currentRadius(), Map.ghostDisks()[i].coord().y() - Map.ghostDisks()[i].currentRadius(),
						Map.ghostDisks()[i].currentRadius() * 2, Map.ghostDisks()[i].currentRadius() * 2);
			}
		}

		Main.gc().setFill(pink);
		for(int i = 0; i < Map.laserSize(); i++) {
			Laser laser = Map.lasers()[i];

			Map.lasers()[i].delayCheck();

			if(laser == Map.lasers()[i]) {
				if(Map.lasers()[i].delay().done()) {
					Main.gc().fillRect(Map.lasers()[i].coord().x(), Map.lasers()[i].coord().y(),
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
					Main.gc().fillOval(Map.disks()[i].coord().x() - Map.disks()[i].currentRadius(), Map.disks()[i].coord().y() - Map.disks()[i].currentRadius(),
							Map.disks()[i].currentRadius() * 2, Map.disks()[i].currentRadius() * 2);
				}
			}
			else {
				i --;
			}
		}

		for(int i = 0; i < Map.playerSize(); i++) {
			Map.players()[i].move();
			Main.gc().save();
			Main.gc().transform(Map.players()[i].rotate().getMxx(), Map.players()[i].rotate().getMyx(), Map.players()[i].rotate().getMxy(), 
					Map.players()[i].rotate().getMyy(), Map.players()[i].rotate().getTx(), Map.players()[i].rotate().getTy());
			Main.gc().drawImage(Map.players()[i].img(), Map.players()[i].coord().x() - 13, Map.players()[i].coord().y() - 25);
			Main.gc().restore();
		}
		SafeZone.update();
	}
}
