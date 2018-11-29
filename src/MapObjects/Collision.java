package MapObjects;

import Main.Main;

public class Collision {
	
	public static void laserToWall(Laser laser) {
		if(laser.dir() == 1) {
			if(laser.width() >= Main.canvas().getWidth()) {
				if(laser.lifeSpan().dur() == 0) {
					laser.death().setDone(true);
				}
				else {
					MapItems.lasers()[laser.laserIndex()].vec().setX(0);
				}
			}
		}
		else if(laser.dir() == 2) {
			if(laser.coord().y() <= 0) {
				if(laser.lifeSpan().dur() == 0) {
					laser.death().setDone(true);
				}
				else {
					MapItems.lasers()[laser.laserIndex()].vec().setX(0);
				}
			}
		}
		else if(laser.dir() == 3) {
			if(laser.coord().x() <= 0) {
				if(laser.lifeSpan().dur() == 0) {
					laser.death().setDone(true);
				}
				else {
					MapItems.lasers()[laser.laserIndex()].vec().setX(0);
				}
			}
		}
		else if (laser.dir() == 4){
			if(laser.height() >= Main.canvas().getHeight()) {
				if(laser.lifeSpan().dur() == 0) {
					laser.death().setDone(true);
				}
				else {
					MapItems.lasers()[laser.laserIndex()].vec().setX(0);
				}
			}
		}
	}
}
