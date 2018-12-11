package Main;

import MapObjects.*;

public class Collision {
	
	public static void laserToWall(Laser laser) {
		if(laser.dir() == 1) {
			if(laser.height() >= Main.canvas().getHeight()) {
				if(laser.lifeSpan().dur() == 0) {
					laser.death().setDone(true);
				}
				laser.vec().setY(0);
			}
			
		}
		else if(laser.dir() == 2) {
			if(laser.coord().x() <= 0) {
				if(laser.lifeSpan().dur() == 0) {
					laser.death().setDone(true);
				}
				laser.vec().setX(0);
			}
		}
		else if(laser.dir() == 3) {
			if(laser.coord().y() <= 0) {
				if(laser.lifeSpan().dur() == 0) {
					laser.death().setDone(true);
				}
				laser.vec().setY(0);
			}
		}
		else if (laser.dir() == 4){
			if(laser.width() >= Main.canvas().getWidth()) {
				if(laser.lifeSpan().dur() == 0) {
					laser.death().setDone(true);
				}
				laser.vec().setX(0);
			}
		}
	}
	
	public static void diskToWall(Disk disk) {
		if(disk.maxSpeed().x() != 0) {
			if(disk.dir().y() == 1) {
				if(disk.coord().y() - disk.currentRadius() >= Main.canvas().getHeight()) {
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
				if(disk.coord().x() - disk.currentRadius() >= Main.canvas().getWidth()) {
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
}
