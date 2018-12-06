package Main;

import Character.Pineapple;
import MapObjects.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameLoop implements EventHandler<ActionEvent> {
	
	private static Color pink = Color.rgb(252,31,109);
	private static Color warning = Color.rgb(93,35,71); 
	public Laser thing1 = new Laser(new Vector(550, 0), 50, 0, 1, new Delay(50), true);
	public Laser thing2 = new Laser(new Vector(1000, 500), 0, 50, 2, new Delay(150), true);
	public Laser thing3 = new Laser(new Vector(100, 650), 50, 0, 3, new Delay(150), true);
	public Laser thing4 = new Laser(new Vector(0, 100), 0,50, 4, new Delay(100), true);
	public Disk thing5 = new Disk(new Vector(200, 300), 50, new Delay(10), 0, true);
	public Image img = new Image("file:resources/pineapple.png");
	public Pineapple man = new Pineapple(new Vector(500,300));
	
	
	public void handle(ActionEvent ev) {
		thing1.vec().setY(5);
		thing2.vec().setX(9);
		thing3.vec().setY(9);
		thing4.vec().setX(9);
		Main.gc().clearRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		Main.gc().setFill(Color.BLACK);
		Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		
		Main.gc().setFill(warning);
		for(int i = 0; i < MapItems.ghostLaserSize(); i++) {
			Main.gc().fillRect(MapItems.ghostLasers()[i].coord().x(), MapItems.ghostLasers()[i].coord().y(),
					MapItems.ghostLasers()[i].width(), MapItems.ghostLasers()[i].height());
		}
		for(int i = 0; i < MapItems.ghostDiskSize(); i++) {
			Main.gc().fillOval(MapItems.ghostDisks()[i].coord().x() - MapItems.ghostDisks()[i].finalRadius(), MapItems.ghostDisks()[i].coord().y() - MapItems.ghostDisks()[i].finalRadius(),
					MapItems.ghostDisks()[i].finalRadius() * 2, MapItems.ghostDisks()[i].finalRadius() * 2);
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
		
		for(int i = 0; i < MapItems.playerSize(); i++) {
			Main.gc().save();
			Main.gc().transform(MapItems.players()[i].rotate().getMxx(), MapItems.players()[i].rotate().getMyx(), MapItems.players()[i].rotate().getMxy(), 
					MapItems.players()[i].rotate().getMyy(), MapItems.players()[i].rotate().getTx(), MapItems.players()[i].rotate().getTy());
			Main.gc().drawImage(MapItems.players()[i].img(), MapItems.players()[i].coord().x() - 13, MapItems.players()[i].coord().y() - 25);
			Main.gc().restore();
		}
	}
}
