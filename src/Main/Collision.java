package Main;

import Character.Pineapple;
import MapObjects.Disk;
import MapObjects.Laser;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Collision {
	
	public static void laserToWall(Laser laser) {
		if(laser.height() > Main.canvas().getHeight() ||
				laser.coord().x() < 0 ||
				laser.coord().y() < 0 ||
				laser.width() > Main.canvas().getWidth()) {
			if(laser.lifeSpan().dur() == 0) {
				laser.death().setDone(true);
			}
			laser.vec().setY(0);
		}
	}
	
	public static void diskToWall(Disk disk) {
		if(disk.maxSpeed().x() != 0) {
			if(disk.dir().y() == 1) {
				if(disk.coord().y() - disk.currentRadius() > Main.canvas().getHeight()) {
					disk.death().setDone(true);
				}
			}
			else {
				if(disk.coord().y() + disk.currentRadius() < 0) {
					disk.death().setDone(true);
				}
			}
		}
		if(disk.maxSpeed().y() != 0) {
			if(disk.dir().x() == 1) {
				if(disk.coord().x() - disk.currentRadius() > Main.canvas().getWidth()) {
					disk.death().setDone(true);
				}
			}
			else {
				if(disk.coord().x() + disk.currentRadius() < 0) {
					disk.death().setDone(true);
				}
			}
		}
	}
	
	public static boolean characterToLaser(Circle character, Laser laser) {
		return (character.intersects(laser.coord().x(), laser.coord().y(), laser.width(), laser.height()) && laser.delay().done());
	}
	
	public static boolean characterToDisk(Pineapple character, Disk disk) {
		Line line = new Line();
		line.setStartX(Math.min(character.coord().x(), disk.coord().x()));
		line.setStartY(Math.min(character.coord().y(), disk.coord().y()));
		line.setEndX(Math.max(character.coord().x(), disk.coord().x()));
		line.setEndY(Math.max(character.coord().y(), disk.coord().y()));
		double length = Math.sqrt(Math.pow((line.getEndX() - line.getStartX()), 2) + Math.pow((line.getEndY() - line.getStartY()), 2));
		return (length <= character.radius() - 7 + disk.currentRadius() && disk.delay().done());
	}
	
	public static boolean characterToSafeDisk(Pineapple character, Disk disk) {
		Line line = new Line();
		line.setStartX(Math.min(character.coord().x(), disk.coord().x()));
		line.setStartY(Math.min(character.coord().y(), disk.coord().y()));
		line.setEndX(Math.max(character.coord().x(), disk.coord().x()));
		line.setEndY(Math.max(character.coord().y(), disk.coord().y()));
		double length = Math.sqrt(Math.pow((line.getEndX() - line.getStartX()), 2) + Math.pow((line.getEndY() - line.getStartY()), 2));
		return (length >= character.radius() - 7 + disk.currentRadius() && disk.delay().done());
	}
}
