package Main;

import java.util.Arrays;

import MapObjects.Disk;
import MapObjects.Laser;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SafeZone {
	
	private static Group indication = new Group();
	private static Group onTop = new Group();
	private Path[] paths = new Path[3000];
	private int pathSize = 0;
	private Circle safeCircle;

	/**
	 * this will be used to draw all the material inside a safe circle
	 * @param disk
	 */
	public SafeZone(Disk disk) {
		this.setSafeCircle(new Circle(disk.coord().x(), disk.coord().y(), disk.currentRadius()));
	}

	/**
	 * this will draw the laser warning so it doesn't overlap the pink on the outside of the safe circle
	 * @param warning its a laser
	 * @param addToIndication if you want to add it or not to the screen
	 */
	public void rectangle(Laser warning, boolean addToIndication) {
		Path path = new Path(); // this will be used to draw the warning perfectly onto the screen
		Rectangle rect = new Rectangle(warning.coord().x() + Map.levels()[Map.playLevel()].shake().degree().x(), warning.coord().y() + Map.levels()[Map.playLevel()].shake().degree().y(),
				warning.width(), warning.height());
		if(addToIndication) {
			if(!warning.showSafe()) {
				path = (Path) Path.intersect(this.safeCircle(), rect); // where they intersect, draw the warning
			}
			else { // if the game is showing that the circle will zoom in
				Shape paths = Path.subtract(rect, this.safeCircle()); 
				for(int i = 0; i < Map.safeDiskSize(); i++) { // it will draw it inside the new safe circle
					if(Map.safeDisks()[i].safeZone() != this) {
						paths = Path.intersect(paths, Map.safeDisks()[i].safeZone().safeCircle());
					}
				}
				path = (Path) paths;
			}
			path.setFill(Color.rgb(93,35,71)); // this will add the warning laser as the intended color
			this.paths()[pathSize()] = path;
			warning.setPathIndex(pathSize());
			this.setPathSize(pathSize() + 1);
			indication().getChildren().add(path);
		}
		else { // if there is no laser warning
			path.setFill(Color.rgb(252,31,109));
			onTop().getChildren().add(path);
		}
	}
	
	/**
	 * this will draw the disk warning so it doesn't overlap the pink on the outside of the safe circle
	 * @param warning
	 * @param addToIndication
	 */
	public void circle(Disk warning, boolean addToIndication) {
		Path path = new Path(); // this will be used to draw the warning perfectly onto the screen
		Circle circle = new Circle(warning.coord().x() + Map.levels()[Map.playLevel()].shake().degree().x(), 
				warning.coord().y() + Map.levels()[Map.playLevel()].shake().degree().y(), warning.currentRadius());
		path = (Path) Path.intersect(this.safeCircle(), circle); // find where they intersect
		if(addToIndication) { // add it to the screen
			path.setFill(Color.rgb(93,35,71));
			this.paths()[pathSize()] = path;
			warning.setPathIndex(pathSize());
			this.setPathSize(pathSize() + 1);
			indication().getChildren().add(path);
		}
		else { // if there is no disk warning
			path.setFill(Color.rgb(252,31,109));
			onTop().getChildren().add(path);
		}
	}

	/**
	 * this will run through all the warning disks and lasers
	 */
	public static void update() {
		Main.group().getChildren().remove(indication());
		Main.group().getChildren().remove(onTop());
		indication().getChildren().clear();
		onTop().getChildren().clear();
		for(int i = 0; i < Map.safeDiskSize(); i++) { // for each safe disk circle
			Map.safeDisks()[i].safeZone().setPathSize(0);
			Arrays.fill(Map.safeDisks()[i].safeZone().paths(), null);
			Map.safeDisks()[i].safeZone().setSafeCircle(new Circle(Map.safeDisks()[i].coord().x() + Map.levels()[Map.playLevel()].shake().degree().x(), 
					Map.safeDisks()[i].coord().y() + Map.levels()[Map.playLevel()].shake().degree().y(), Map.safeDisks()[i].currentRadius()));
			for(int p = 0; p < Map.ghostLaserSize(); p++) { // run for all laser warnings
				Map.safeDisks()[i].safeZone().rectangle(Map.ghostLasers()[p], true);
			}
			for(int p = 0; p < Map.ghostDiskSize(); p++) {  // run for all disk warnings
				Map.safeDisks()[i].safeZone().circle(Map.ghostDisks()[p], true);
			}
			for(int p = 0; p < Map.laserSize(); p++) {  // run for all lasers
				if(Map.lasers()[p].delay().done()) {
					Map.safeDisks()[i].safeZone().rectangle(Map.lasers()[p], false);
				}
			}
			for(int p = 0; p < Map.diskSize(); p++) {  // run for all disk
				if(Map.disks()[p].delay().done()) {
					Map.safeDisks()[i].safeZone().circle(Map.disks()[p], false);
				}
			}
		}
		Main.group().getChildren().add(indication()); // add it to the screen
		Main.group().getChildren().add(onTop()); // add it to the screen
		
		for(int i = 0; i < Map.playerSize(); i++) { // draw the characters on top of the outside of the safe zone
			if(Map.players()[i].alive()) {
				ImageView player = new ImageView(Map.players()[i].img());
				player.setX(Map.players()[i].coord().x() - 13 + Map.levels()[Map.playLevel()].shake().degree().x());
				player.setY(Map.players()[i].coord().y() - 25 + Map.levels()[Map.playLevel()].shake().degree().y());
				player.getTransforms().add(Map.players()[i].rotate());
				onTop().getChildren().add(player);
			}
		}
	}

	public Path[] paths() {
		return paths;
	}

	public void setPaths(Path[] paths) {
		this.paths = paths;
	}

	public int pathSize() {
		return pathSize;
	}

	public void setPathSize(int pathSize) {
		this.pathSize = pathSize;
	}

	public Circle safeCircle() {
		return safeCircle;
	}

	public void setSafeCircle(Circle safeCircle) {
		this.safeCircle = safeCircle;
	} 

	public static Group onTop() {
		return onTop;
	}

	public static void setOnTop(Group onTop) {
		SafeZone.onTop = onTop;
	}

	public static Group indication() {
		return indication;
	}

	public static void setIndication(Group indication) {
		SafeZone.indication = indication;
	}
}
