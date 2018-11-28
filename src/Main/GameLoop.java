package Main;

import MapObjects.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class GameLoop implements EventHandler<ActionEvent> {
	
	private static Color pink = Color.rgb(252,31,109);
	private static Color warning = Color.rgb(93,35,71); 
	public Laser thing1 = new Laser(new Vector(0, 100), 0, 50, 1, new Delay(50), true);
	public Laser thing2 = new Laser(new Vector(100, 650), 50, 0, 2, new Delay(150), true);
	public Laser thing3 = new Laser(new Vector(1000, 500), 0, 50, 3, new Delay(100), true);
	public Laser thing4 = new Laser(new Vector(550, 0), 50, 0, 4, new Delay(100), true);
	
	public void handle(ActionEvent ev) {
		thing1.vec().setX(5);
		thing2.vec().setY(9);
		thing3.vec().setX(9);
		thing4.vec().setY(9);
		Main.gc().clearRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		Main.gc().setFill(Color.BLACK);
		Main.gc().fillRect(0, 0, Main.canvas().getWidth(), Main.canvas().getHeight());
		
		Main.gc().setFill(warning);
		for(int i = 0; i < MapItems.ghostLasers().size(); i++) {
			Main.gc().fillRect(MapItems.ghostLasers().get(i).coord().x(), MapItems.ghostLasers().get(i).coord().y(),
					MapItems.ghostLasers().get(i).width(), MapItems.ghostLasers().get(i).height());
		}
		
		Main.gc().setFill(pink);
		for(int i = 0; i < MapItems.lasers().size(); i++) {
			int startSize = MapItems.lasers().size();
			
			if(!MapItems.lasers().get(i).delay().done() || (!MapItems.lasers().get(i).lifeSpan().done() && MapItems.lasers().get(i).lifeSpan().dur() != 0)) {
				MapItems.lasers().get(i).delayCheck();
			}
			if(MapItems.lasers().get(i).delay().done()) {
				Main.gc().fillRect(MapItems.lasers().get(i).coord().x(), MapItems.lasers().get(i).coord().y(),
						MapItems.lasers().get(i).width(), MapItems.lasers().get(i).height());
				MapItems.lasers().get(i).Move();
			
			}
			if(MapItems.lasers().size() < startSize) {
				i -= 1;
			}
		}
	}
}
