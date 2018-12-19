package Main;

import Character.Pineapple;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class GameLoop implements EventHandler<ActionEvent> {
	
	private static Color pink = Color.rgb(252,31,109);
	private static Color warning = Color.rgb(93,35,71); 
	public Pineapple man = new Pineapple(new Vector(500,300));
	public Level level1 = new Level(1);
	
	
	public void handle(ActionEvent ev) {
		level1.delayCheck();
		Main.gc().clearRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		
		if(MapItems.safeDiskSize() == 0) {
			Main.gc().setFill(Color.BLACK);
			Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		}
		else {
			if(MapItems.safeDisks()[0].delay().done()) {
				Main.gc().setFill(pink);
			}
			else {
				Main.gc().setFill(warning);
			}
			Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
			
			Main.gc().setFill(Color.BLACK);
			for(int i = 0; i < MapItems.safeDiskSize(); i++) {
				MapItems.safeDisks()[i].delayCheck();
				Main.gc().fillOval(MapItems.safeDisks()[i].coord().x() - MapItems.safeDisks()[i].currentRadius(), MapItems.safeDisks()[i].coord().y() - MapItems.safeDisks()[i].currentRadius(),
						MapItems.safeDisks()[i].currentRadius() * 2, MapItems.safeDisks()[i].currentRadius() * 2);
			}
		}
		
		Main.gc().setFill(warning);
		if(MapItems.safeDiskSize() == 0) {
			for(int i = 0; i < MapItems.ghostLaserSize(); i++) {
				Main.gc().fillRect(MapItems.ghostLasers()[i].coord().x(), MapItems.ghostLasers()[i].coord().y(),
						MapItems.ghostLasers()[i].width(), MapItems.ghostLasers()[i].height());
			}
			for(int i = 0; i < MapItems.ghostDiskSize(); i++) {
				Main.gc().fillOval(MapItems.ghostDisks()[i].coord().x() - MapItems.ghostDisks()[i].currentRadius(), MapItems.ghostDisks()[i].coord().y() - MapItems.ghostDisks()[i].currentRadius(),
						MapItems.ghostDisks()[i].currentRadius() * 2, MapItems.ghostDisks()[i].currentRadius() * 2);
			}
		}
		
		Main.gc().setFill(pink);
		for(int i = 0; i < MapItems.laserSize(); i++) {
			int startSize = MapItems.laserSize();
			
			MapItems.lasers()[i].delayCheck();
			
			if(MapItems.laserSize() == startSize) {
				if(MapItems.lasers()[i].delay().done()) {
					Main.gc().fillRect(MapItems.lasers()[i].coord().x(), MapItems.lasers()[i].coord().y(),
							MapItems.lasers()[i].width(), MapItems.lasers()[i].height());
				}
			}
			if(MapItems.laserSize() < startSize) {
				i -= 1;
			}
		}
		for(int i = 0; i < MapItems.diskSize(); i++) {
			int startSize = MapItems.diskSize();
			
			MapItems.disks()[i].delayCheck();
			
			if(MapItems.diskSize() == startSize) {
				if(MapItems.disks()[i].delay().done()) {
					Main.gc().fillOval(MapItems.disks()[i].coord().x() - MapItems.disks()[i].currentRadius(), MapItems.disks()[i].coord().y() - MapItems.disks()[i].currentRadius(),
							MapItems.disks()[i].currentRadius() * 2, MapItems.disks()[i].currentRadius() * 2);
				}
			}
			if(MapItems.diskSize() < startSize) {
				i -= 1;
			}
		}
		
		for(int i = 0; i < MapItems.playerSize(); i++) {
			MapItems.players()[i].move();
			Main.gc().save();
			Main.gc().transform(MapItems.players()[i].rotate().getMxx(), MapItems.players()[i].rotate().getMyx(), MapItems.players()[i].rotate().getMxy(), 
					MapItems.players()[i].rotate().getMyy(), MapItems.players()[i].rotate().getTx(), MapItems.players()[i].rotate().getTy());
			Main.gc().drawImage(MapItems.players()[i].img(), MapItems.players()[i].coord().x() - 13, MapItems.players()[i].coord().y() - 25);
			Main.gc().restore();
		}
		SafeZone.update();
	}
}
