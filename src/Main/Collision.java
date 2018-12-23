package Main;

import MapObjects.*;

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
}
