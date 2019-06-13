package Main;

import Character.Player;
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
	
	/**
	 * this will check for collision between a wall and a disk
	 * @param disk
	 */
	public static void diskToWall(Disk disk) {
		if(!disk.redirect()) { // if the disk is not going to be used
			// the next few if statements will check for if the disk is coming onto the screen (do nothing) or leaving it (remove it from the game)
			if(disk.dir().y() == 1) {
				if(disk.coord().y() - disk.currentRadius() > Main.canvas().getHeight()) {
					disk.death().setDone(true);
				}
			}
			else if(disk.dir().y() == -1){
				if(disk.coord().y() + disk.currentRadius() < 0) {
					disk.death().setDone(true);
				}
			}
			if(disk.dir().x() == 1) {
				if(disk.coord().x() - disk.currentRadius() > Main.canvas().getWidth()) {
					disk.death().setDone(true);
				}
			}
			else if(disk.dir().x() == -1){
				if(disk.coord().x() + disk.currentRadius() < 0) {
					disk.death().setDone(true);
				}
			}
		}
	}
	
	/**
	 * checking to make sure the character doesn't go off screen
	 * @param character
	 */
	public static void characterToWall(Player character) {
		if(character.coord().x() - character.radius() <= 0) {
			character.coord().setX(character.radius());
		}
		if(character.coord().x() + character.radius() >= 1000) {
			character.coord().setX(1000 - character.radius());
		}
		if(character.coord().y() - character.radius() <= 0) {
			character.coord().setY(character.radius());
		}
		if(character.coord().y() + character.radius() >= 650) {
			character.coord().setY(650 - character.radius());
		}
	} 
	
	/**
	 * this will check for collision between the character and a laser
	 * @param character
	 * @param laser
	 * @return if the character collided or not
	 */
	public static boolean characterToLaser(Circle character, Laser laser) {
		return (character.intersects(laser.coord().x(), laser.coord().y(), laser.width(), laser.height()) && laser.delay().done());
	}
	
	/**
	 * this will check collision between the character and a disk
	 * @param character
	 * @param disk
	 * @return if the character collided or not
	 */
	public static boolean characterToDisk(Player character, Disk disk) {
		Line line = new Line();
		// the next few lines are the math to find do the collision detection
		line.setStartX(Math.min(character.coord().x(), disk.coord().x()));
		line.setStartY(Math.min(character.coord().y(), disk.coord().y()));
		line.setEndX(Math.max(character.coord().x(), disk.coord().x()));
		line.setEndY(Math.max(character.coord().y(), disk.coord().y()));
		double length = Math.sqrt(Math.pow((line.getEndX() - line.getStartX()), 2) + Math.pow((line.getEndY() - line.getStartY()), 2));
		return (length <= character.radius() - 7 + disk.currentRadius() && disk.delay().done());
	}
	
	/**
	 * this will check collision between the character and outside the safe disk
	 * @param character
	 * @param disk
	 * @return
	 */
	public static boolean characterToSafeDisk(Player character, Disk disk) {
		Line line = new Line();
		// the next few lines are the math to find do the collision detection
		line.setStartX(Math.min(character.coord().x(), disk.coord().x()));
		line.setStartY(Math.min(character.coord().y(), disk.coord().y()));
		line.setEndX(Math.max(character.coord().x(), disk.coord().x()));
		line.setEndY(Math.max(character.coord().y(), disk.coord().y()));
		double length = Math.sqrt(Math.pow((line.getEndX() - line.getStartX()), 2) + Math.pow((line.getEndY() - line.getStartY()), 2));
		return (length >= character.radius() - 7 + disk.currentRadius() && disk.delay().done());
	}
}
