package MapObjects;

import java.util.Arrays;

import Main.*;

public class Disk {
	
	private Vector coord;
	private double currentRadius = 0;
	private double finalRadius;
	private double radiusChange = 0;
	private Vector vec = new Vector(-3,3);
	private Vector maxSpeed = new Vector(7,7);
	private Vector dir = new Vector(1,1);
	private Delay delay;
	private Delay lifeSpan = new Delay(0);
	private Delay death = new Delay(0);
	private boolean safe;
	private SafeZone safeZone;
	private int diskIndex;
	private int ghostIndex;
	private int pathIndex;
	
	public Disk(Vector coord, double finalRadius, Delay delay, double radiusChange, boolean safe) {
		this.setCoord(coord);
		this.setFinalRadius(finalRadius);
		this.setDelay(delay);
		this.setRadiusChange(radiusChange);
		this.setSafe(safe);
		
		if(this.vec().x() < 0) {
			this.dir().setX(-1);
		}
		if (this.vec().y() < 0) {
			this.dir().setY(-1);
		}
		
		if(!safe) {
			this.setDiskIndex(MapItems.diskSize());
			MapItems.disks()[this.diskIndex()] = this;
			MapItems.setDiskSize(MapItems.diskSize() + 1);
			if(this.delay().dur() != 0) {
				this.setGhostIndex(MapItems.ghostDiskSize());
				Disk warning = new Disk(new Vector(coord.x(),coord.y()), finalRadius);
				MapItems.ghostDisks()[this.ghostIndex()] = warning;
				MapItems.setGhostDiskSize(MapItems.ghostDiskSize() + 1);
			}
		}
		else {
			this.setCurrentRadius(finalRadius);
			this.setSafeZone(new SafeZone(this));
			MapItems.safeDisks()[MapItems.safeDiskSize()] = this;
			MapItems.setSafeDiskSize(MapItems.safeDiskSize() + 1);
			for(int i = 0; i < MapItems.ghostDiskSize(); i++) {
				this.safeZone().circle(MapItems.ghostDisks()[i]);
			}
			for(int i = 0; i < MapItems.ghostLaserSize(); i++) {
				this.safeZone().rectangle(MapItems.ghostLasers()[i]);
			}
		}
	}
	
	public Disk(Vector coord, double finalRadius) {
		this.setCoord(coord);
		this.setCurrentRadius(finalRadius);
		for(int i = 0; i < MapItems.safeDiskSize(); i++) {
			MapItems.safeDisks()[i].safeZone().circle(this);
		}
	}
	
	public void delayCheck() {
		
		if(this.delay().done()) {
			this.changeRadius();
			if(this.delay.dur() != 0 && !this.safe()) {
				if(this.currentRadius() == this.finalRadius() && this.radiusChange() != 0) {
					if(this.lifeSpan().dur() == 0 && this.vec().x() == 0 && this.vec().y() == 0) {
						this.setRadiusChange(this.radiusChange() * -2);
					}
					else {
						this.setRadiusChange(0);
					}
					
					this.removeGhost();
				}
			}
			else if(this.radiusChange() == 0 && this.currentRadius() != this.finalRadius()){
				this.setCurrentRadius(this.finalRadius());
			}
			
			if(this.currentRadius() == this.finalRadius()) {
				this.move();
			}
		}
		
		else {
			if(this.delay().delayCheck()) {
				this.delay().setDone(true);
			}
		}
		
		if(this.delay().done() && this.lifeSpan().dur() != 0) {
			if(this.currentRadius() == this.finalRadius()) {
				if(this.lifeSpan().delayCheck()) {
					//make math
					this.setRadiusChange(-1.4);
				}	
			}
		}
		if(this.death().done()) {
			this.remove();
		}
	}
	
	public void changeRadius() {
		if(this.currentRadius() < this.finalRadius() && this.radiusChange() > 0) {
			this.setCurrentRadius(this.currentRadius() + this.radiusChange());
			if(this.currentRadius() > this.finalRadius()) {
				this.setCurrentRadius(this.finalRadius());
			}
		}
		
		else if(this.currentRadius() > 0 && this.radiusChange() < 0) {
			this.setCurrentRadius(this.currentRadius() + this.radiusChange());
			if(this.currentRadius() < 0) {
				this.setCurrentRadius(0);
				this.death().setDone(true);
			}
		}
	}
	
	public void remove() {
		MapItems.setDiskSize(MapItems.diskSize() - 1);
		MapItems.disks()[MapItems.diskSize()].setDiskIndex(this.diskIndex()); 
		MapItems.disks()[this.diskIndex()] = MapItems.disks()[MapItems.diskSize()];
		MapItems.disks()[MapItems.diskSize()] = null;
	}
	
	public void removeGhost() {
		MapItems.setGhostDiskSize(MapItems.ghostDiskSize() - 1);
		MapItems.disks()[MapItems.diskSize() - 1].setGhostIndex(this.ghostIndex());
		MapItems.ghostDisks()[this.ghostIndex()] = MapItems.ghostDisks()[MapItems.ghostDiskSize()];
		MapItems.ghostDisks()[MapItems.ghostDiskSize()] = null;
		
		for(int i = 0; i < MapItems.safeDiskSize(); i++) {
			MapItems.safeDisks()[i].safeZone().setPathSize(MapItems.safeDisks()[i].safeZone().pathSize() - 1);
			System.out.println(MapItems.safeDisks()[i].safeZone().pathSize());
			MapItems.safeDisks()[i].safeZone().paths()[this.pathIndex()] = MapItems.safeDisks()[i].safeZone().paths()[MapItems.safeDisks()[i].safeZone().pathSize()];
			MapItems.safeDisks()[i].safeZone().paths()[MapItems.safeDisks()[i].safeZone().pathSize()] = null;
		}
	}
	
	public void removeSafe() {
		MapItems.setSafeDiskSize(MapItems.safeDiskSize() - 1);
		MapItems.safeDisks()[Arrays.asList(MapItems.safeDisks()).indexOf(this)] = MapItems.safeDisks()[MapItems.safeDiskSize()];
		MapItems.safeDisks()[MapItems.safeDiskSize()] = null;
	}
	
	
	public void move() {
		double change = 0.5;
		
		Collision.diskToWall(this);
		
		this.coord().setX(this.coord().x() + this.vec().x());
		this.coord().setY(this.coord().y() + this.vec().y());
		
		if(this.maxSpeed().x() != 0) {
			this.vec().setX(this.vec().x() + (this.dir().x() * change));
			if(Math.abs(this.vec().x()) >= this.maxSpeed().x()) {
				this.dir().setX(-this.dir().x());
			}
		}
		if(this.maxSpeed().y() != 0) {
			this.vec().setY(this.vec().y() + (this.dir().y() * change));
			if(Math.abs(this.vec().y()) >= this.maxSpeed().y()) {
				this.dir().setY(-this.dir().y());
			}
		}
	}
	
	
	public Vector coord() {
		return coord;
	}
	
	public void setCoord(Vector coord) {
		this.coord = coord;
	}
	
	public Vector vec() {
		return vec;
	}
	
	public void setVec(Vector vec) {
		this.vec = vec;
	}
	
	public Delay delay() {
		return delay;
	}
	
	public void setDelay(Delay delay) {
		this.delay = delay;
	}
	
	public Delay lifeSpan() {
		return lifeSpan;
	}
	
	public void setLifeSpan(Delay lifeSpan) {
		this.lifeSpan = lifeSpan;
	}
	
	public int diskIndex() {
		return diskIndex;
	}
	
	public void setDiskIndex(int diskIndex) {
		this.diskIndex = diskIndex;
	}
	
	public int ghostIndex() {
		return ghostIndex;
	}
	
	public void setGhostIndex(int ghostIndex) {
		this.ghostIndex = ghostIndex;
	}

	public double currentRadius() {
		return currentRadius;
	}

	public void setCurrentRadius(double currentRadius) {
		this.currentRadius = currentRadius;
	}

	public double finalRadius() {
		return finalRadius;
	}

	public void setFinalRadius(double finalRadius) {
		this.finalRadius = finalRadius;
	}

	public double radiusChange() {
		return radiusChange;
	}

	public void setRadiusChange(double radiusChange) {
		this.radiusChange = radiusChange;
	}
	public Vector dir() {
		return dir;
	}

	public void setDir(Vector dir) {
		this.dir = dir;
	}

	public Vector maxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Vector maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Delay death() {
		return death;
	}

	public void setDeath(Delay death) {
		this.death = death;
	}
	
	public boolean safe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	public int pathIndex() {
		return pathIndex;
	}

	public void setPathIndex(int pathIndex) {
		this.pathIndex = pathIndex;
	}

	public SafeZone safeZone() {
		return safeZone;
	}

	public void setSafeZone(SafeZone safeZone) {
		this.safeZone = safeZone;
	}
}
