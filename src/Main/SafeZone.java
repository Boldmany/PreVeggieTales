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
		Rectangle rect = new Rectangle(warning.coord().x(), warning.coord().y(), warning.width(), warning.height());
		if(addToIndication) {
			path = (Path) Path.intersect(this.safeCircle(), rect);
			path.setFill(Color.rgb(93,35,71)); 
			this.paths()[pathSize()] = path;
			warning.setPathIndex(pathSize());
			this.setPathSize(pathSize() + 1);
			indication.getChildren().add(path);
		}
		else {
			path.setFill(Color.rgb(252,31,109));
			onTop.getChildren().add(path);
		}
	}
	
	public void circle(Disk warning, boolean addToIndication) {
		Path path = new Path();
		Circle circle = new Circle(warning.coord().x(), warning.coord().y(), warning.currentRadius());
		path = (Path) Path.intersect(this.safeCircle(), circle);
		if(addToIndication) {
			path.setFill(Color.rgb(93,35,71)); 
			this.paths()[pathSize()] = path;
			warning.setPathIndex(pathSize());
			this.setPathSize(pathSize() + 1);
			indication.getChildren().add(path);
		}
		else {
			path.setFill(Color.rgb(252,31,109));
			onTop.getChildren().add(path);
		}
	}

	public static void update() {
		Main.group().getChildren().remove(indication);
		Main.group().getChildren().remove(onTop);
		indication.getChildren().clear();
		onTop.getChildren().clear();
		for(int i = 0; i < MapItems.safeDiskSize(); i++) {
			MapItems.safeDisks()[i].safeZone().setPathSize(0);
			Arrays.fill(MapItems.safeDisks()[i].safeZone().paths(), null);
			MapItems.safeDisks()[i].safeZone().setSafeCircle(new Circle(MapItems.safeDisks()[i].coord().x(), MapItems.safeDisks()[i].coord().y(), MapItems.safeDisks()[i].currentRadius()));
			for(int p = 0; p < MapItems.ghostLaserSize(); p++) {
				MapItems.safeDisks()[i].safeZone().rectangle(MapItems.ghostLasers()[p], true);
			}
			for(int p = 0; p < MapItems.ghostDiskSize(); p++) {
				MapItems.safeDisks()[i].safeZone().circle(MapItems.ghostDisks()[p], true);
			}
			for(int p = 0; p < MapItems.laserSize(); p++) {
				if(MapItems.lasers()[p].delay().done()) {
					MapItems.safeDisks()[i].safeZone().rectangle(MapItems.lasers()[p], false);
				}
			}
			for(int p = 0; p < MapItems.diskSize(); p++) {
				if(MapItems.disks()[p].delay().done()) {
					MapItems.safeDisks()[i].safeZone().circle(MapItems.disks()[p], false);
				}
			}
		}
		Main.group().getChildren().add(indication);
		Main.group().getChildren().add(onTop);
		for(int i = 0; i < MapItems.playerSize(); i++) {
			ImageView pineapple = new ImageView(MapItems.players()[i].img());
			pineapple.setX(MapItems.players()[i].coord().x() - 13);
			pineapple.setY(MapItems.players()[i].coord().y() - 25);
			pineapple.getTransforms().add(MapItems.players()[i].rotate());
			onTop.getChildren().add(pineapple);
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
