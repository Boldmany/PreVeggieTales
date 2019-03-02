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

	public SafeZone(Disk disk) {
		this.setSafeCircle(new Circle(disk.coord().x(), disk.coord().y(), disk.currentRadius()));
	}

	public void rectangle(Laser warning, boolean addToIndication) {
		Path path = new Path();
		Rectangle rect = new Rectangle(warning.coord().x() + Map.levels()[Map.playLevel()].shake().degree().x(), warning.coord().y() + Map.levels()[Map.playLevel()].shake().degree().y(),
				warning.width(), warning.height());
		if(addToIndication) {
			if(!warning.showSafe()) {
				path = (Path) Path.intersect(this.safeCircle(), rect);
			}
			else {
				Shape paths = Path.subtract(rect, this.safeCircle()); 
				for(int i = 0; i < Map.safeDiskSize(); i++) {
					if(Map.safeDisks()[i].safeZone() != this) {
						paths = Path.intersect(paths, Map.safeDisks()[i].safeZone().safeCircle());
					}
				}
				path = (Path) paths;
			}
			path.setFill(Color.rgb(93,35,71)); 
			this.paths()[pathSize()] = path;
			warning.setPathIndex(pathSize());
			this.setPathSize(pathSize() + 1);
			indication().getChildren().add(path);
		}
		else {
			path.setFill(Color.rgb(252,31,109));
			onTop().getChildren().add(path);
		}
	}

	public void circle(Disk warning, boolean addToIndication) {
		Path path = new Path();
		Circle circle = new Circle(warning.coord().x() + Map.levels()[Map.playLevel()].shake().degree().x(), 
				warning.coord().y() + Map.levels()[Map.playLevel()].shake().degree().y(), warning.currentRadius());
		path = (Path) Path.intersect(this.safeCircle(), circle);
		if(addToIndication) {
			path.setFill(Color.rgb(93,35,71));
			this.paths()[pathSize()] = path;
			warning.setPathIndex(pathSize());
			this.setPathSize(pathSize() + 1);
			indication().getChildren().add(path);
		}
		else {
			path.setFill(Color.rgb(252,31,109));
			onTop().getChildren().add(path);
		}
	}

	public static void update() {
		Main.group().getChildren().remove(indication());
		Main.group().getChildren().remove(onTop());
		indication().getChildren().clear();
		onTop().getChildren().clear();
		for(int i = 0; i < Map.safeDiskSize(); i++) {
			Map.safeDisks()[i].safeZone().setPathSize(0);
			Arrays.fill(Map.safeDisks()[i].safeZone().paths(), null);
			Map.safeDisks()[i].safeZone().setSafeCircle(new Circle(Map.safeDisks()[i].coord().x() + Map.levels()[Map.playLevel()].shake().degree().x(), 
					Map.safeDisks()[i].coord().y() + Map.levels()[Map.playLevel()].shake().degree().y(), Map.safeDisks()[i].currentRadius()));
			for(int p = 0; p < Map.ghostLaserSize(); p++) {
				Map.safeDisks()[i].safeZone().rectangle(Map.ghostLasers()[p], true);
			}
			for(int p = 0; p < Map.ghostDiskSize(); p++) {
				Map.safeDisks()[i].safeZone().circle(Map.ghostDisks()[p], true);
			}
			for(int p = 0; p < Map.laserSize(); p++) {
				if(Map.lasers()[p].delay().done()) {
					Map.safeDisks()[i].safeZone().rectangle(Map.lasers()[p], false);
				}
			}
			for(int p = 0; p < Map.diskSize(); p++) {
				if(Map.disks()[p].delay().done()) {
					Map.safeDisks()[i].safeZone().circle(Map.disks()[p], false);
				}
			}
		}
		Main.group().getChildren().add(indication());
		Main.group().getChildren().add(onTop());
		
		for(int i = 0; i < Map.playerSize(); i++) {
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
