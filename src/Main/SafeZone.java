package Main;

import MapObjects.Disk;
import MapObjects.Laser;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

public class SafeZone {
	
	private static Group group = new Group();
	private Path[] paths = new Path[3000];
	private int pathSize = 0;
	private Circle safeCircle;
	private boolean rendering = false;
	
	public SafeZone(Disk disk) {
		this.setSafeCircle(new Circle(disk.coord().x(), disk.coord().y(), disk.currentRadius()));
	}
	
	public void rectangle(Laser warning) {
		Path path = new Path();
		Rectangle rect = new Rectangle(warning.coord().x(), warning.coord().y(), warning.width(), warning.height());
		path = (Path) Path.intersect(this.safeCircle(), rect);
		path.setFill(Color.rgb(93,35,71)); 
		
		this.paths()[pathSize()] = path;
		warning.setPathIndex(pathSize());
		//Main.group().getChildren().add(path);
		group.getChildren().add(path);
		this.setPathSize(pathSize() + 1);
		
	}
	
	public void circle(Disk warning) {
		Path path = new Path();
		Circle circle = new Circle(warning.coord().x(), warning.coord().y(), warning.currentRadius());
		path = (Path) Path.intersect(this.safeCircle(), circle);
		path.setFill(Color.rgb(93,35,71)); 
		
		this.paths()[pathSize()] = path;
		warning.setPathIndex(pathSize());
		//Main.group().getChildren().add(path);
		group.getChildren().add(path);
		this.setPathSize(pathSize() + 1);
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

	public boolean rendering() {
		return rendering;
	}

	public void setRendering(boolean rendering) {
		this.rendering = rendering;
	}

	public static Group group() {
		return group;
	}

	public static void setGroup(Group group) {
		SafeZone.group = group;
	}
}
