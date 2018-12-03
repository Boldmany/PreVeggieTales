package Main;

import MapObjects.Laser;
import MapObjects.MapItems;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class GameLoop implements EventHandler<ActionEvent> {
	
	private static Color pink = Color.rgb(252,31,109);
	private static Color warning = Color.rgb(93,35,71); 
	public Laser thing1 = new Laser(new Vector(0, 100), 0, 50, 1, new Delay(50), true);
	public Laser thing2 = new Laser(new Vector(100, 650), 50, 0, 2, new Delay(150), true);
	public Laser thing3 = new Laser(new Vector(1000, 500), 0, 50, 3, new Delay(150), true);
	public Laser thing4 = new Laser(new Vector(550, 0), 50, 0, 4, new Delay(100), true);
	public Image img = new Image("file:resources/pineapple.png");
	public Rotate rotate = new Rotate(-45,100,200);
	
	
	public void handle(ActionEvent ev) {
		thing1.vec().setX(5);
		thing2.vec().setY(9);
		thing3.vec().setX(9);
		thing4.vec().setY(9);
		Main.gc().clearRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		Main.gc().setFill(Color.BLACK);
		Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		
		Main.gc().setFill(warning);
		for(int i = 0; i < MapItems.ghostLaserSize(); i++) {
			Main.gc().fillRect(MapItems.ghostLasers()[i].coord().x(), MapItems.ghostLasers()[i].coord().y(),
					MapItems.ghostLasers()[i].width(), MapItems.ghostLasers()[i].height());
		}
		
		Main.gc().setFill(pink);
		for(int i = 0; i < MapItems.laserSize(); i++) {
			int startSize = MapItems.laserSize();
			
			MapItems.lasers()[i].delayCheck();
			
			if(MapItems.laserSize() == startSize) {
				if(MapItems.lasers()[i].delay().done()) {
					Main.gc().fillRect(MapItems.lasers()[i].coord().x(), MapItems.lasers()[i].coord().y(),
							MapItems.lasers()[i].width(), MapItems.lasers()[i].height());
					MapItems.lasers()[i].Move();
				}
			}
			if(MapItems.laserSize() < startSize) {
				i -= 1;
			}
		}
		
		Main.gc().drawImage(img, 100, 200);
		
		Main.gc().drawImage(img, 100, 200);
	}
}
