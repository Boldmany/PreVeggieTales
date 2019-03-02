package MapObjects;

import java.util.Objects;

import Main.Collision;
import Main.Delay;
import Main.Map;
import Main.SafeZone;
import Main.Vector;
import javafx.scene.shape.Circle;

public class Disk {

	private int spawn;
	private Vector coord;
	private Vector vec;
	private Vector maxSpeed;
	private Vector dir = new Vector(1,1);
	private double currentRadius = 0;
	private double finalRadius;
	private double radiusChange;
	private double degree;
	private double degreeChange;
	private double towardsCenter;
	private Circle circularPath;
	private Delay delay;
	private Delay lifeSpan;
	private Delay death = new Delay(0);
	private boolean circularMotion;
	private boolean safe;
	private boolean redirect;
	private SafeZone safeZone;
	private int diskIndex;
	private int ghostIndex;
	private int pathIndex;
	private int safeDiskIndex;
	private int redirectIndex;

	public Disk(int spawn, Vector coord, Vector vec, Vector maxSpeed, double finalRadius, double radiusChange,  
			boolean circularMotion, double degree, double degreeChange, double towardsCenter, Circle circularPath, Delay delay, Delay lifeSpan, boolean safe, boolean redirect) {
		this.setSpawn(spawn);
		this.setCoord(coord);
		this.setVec(vec);
		this.setMaxSpeed(maxSpeed);
		this.setFinalRadius(finalRadius);
		this.setRadiusChange(radiusChange); 
		this.setDegree(degree);
		this.setDegreeChange(degreeChange);
		this.setTowardsCenter(towardsCenter);
		this.setCircularPath(circularPath);
		this.setDelay(delay);
		this.setLifeSpan(lifeSpan);
		this.setCircularMotion(circularMotion);
		this.setSafe(safe);
		this.setRedirect(redirect);
		
		if(this.vec().x() < 0) {
			this.dir().setX(-1);
		}
		if (this.vec().y() < 0) {
			this.dir().setY(-1);
		}
	}

	public Disk(Vector coord, double finalRadius) {
		this.setCoord(coord);
		this.setCurrentRadius(finalRadius);
		for(int i = 0; i < Map.safeDiskSize(); i++) {
			Map.safeDisks()[i].safeZone().circle(this, true);
		}
	}

	public Disk(Vector vec, Vector maxSpeed, double radiusChange, double degreeChange, Delay lifeSpan, boolean redirect) {
		this.setVec(vec);
		this.setMaxSpeed(maxSpeed);
		this.setRadiusChange(radiusChange);
		this.setDegreeChange(degreeChange);
		this.setLifeSpan(lifeSpan);
		this.setRedirect(redirect);
	}

	public void delayCheck() {
		if(this.delay().delayCheck() && !this.delay().done()) {
			this.delay().setDone(true);
			if(this.delay().dur() != 0 && !this.safe()) {
				this.removeGhost();
			}
		}

		if(this.delay().done()) {
			this.changeRadius();
			if(!Objects.isNull(this.ghostIndex())) {
				if(this.currentRadius() == this.finalRadius() && this.radiusChange() != 0) {
					if(this.lifeSpan().dur() == 0 && this.vec().x() == 0 && this.vec().y() == 0) {
						this.setRadiusChange(this.radiusChange() * -2);
					}
					else {
						this.setRadiusChange(0);
					}
				}
			}
			if(this.radiusChange() == 0 && this.currentRadius() != this.finalRadius()){
				this.setCurrentRadius(this.finalRadius());
			}

			if(this.currentRadius() == this.finalRadius()) {
				if(this.circularMotion()) {
					if(this.towardsCenter() != 0) {
						this.circularPath().setRadius(this.circularPath().getRadius() - this.towardsCenter());
						if(this.circularPath().getRadius() < 0) {
							this.circularPath().setRadius(0);
						}
					}
					this.moveInCircle();
				}
				else {
					this.move();
				}
			}
		}

		if(this.delay().done() && this.lifeSpan().dur() != 0) {
			if((this.currentRadius() == this.finalRadius()) || this.safe()) {
				if(this.lifeSpan().delayCheck()) {
					if(this.redirect() || this.safe()) {
						this.death().setDone(true);
					}
					else {
						this.setRadiusChange(-1.4);
					}
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

	public void moveInCircle() {
		this.setDegree((this.degree() + this.degreeChange()) % 360);
		this.coord().setX(this.circularPath().getCenterX() + (this.circularPath().getRadius() * Math.cos(Math.toRadians(this.degree()))));
		this.coord().setY(this.circularPath().getCenterY() + (this.circularPath().getRadius() * Math.sin(Math.toRadians(this.degree()))));
	}

	public void remove() {
		if(!this.safe()) {
			Map.setDiskSize(Map.diskSize() - 1);
			Map.disks()[Map.diskSize()].setDiskIndex(this.diskIndex()); 
			Map.disks()[this.diskIndex()] = Map.disks()[Map.diskSize()];
			Map.disks()[Map.diskSize()] = null;
		}
		else {
			Map.setSafeDiskSize(Map.safeDiskSize() - 1);
			Map.safeDisks()[Map.safeDiskSize()].setSafeDiskIndex(this.safeDiskIndex()); 
			Map.safeDisks()[this.safeDiskIndex()] = Map.safeDisks()[Map.safeDiskSize()];
			Map.safeDisks()[Map.safeDiskSize()] = null;
		}

		if(this.redirect()) {
			Disk disk = new Disk(0, this.coord(), Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].vec(), 
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].maxSpeed(), this.finalRadius(), 
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].radiusChange(),
					this.circularMotion(), this.degree(), Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].degreeChange(),
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].towardsCenter(),
					this.circularPath(), new Delay(0),
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].lifeSpan(),
					this.safe(), Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].redirect());
			if(disk.redirect()) {
				disk.setRedirectIndex(Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].redirectIndex());
			}
			disk.setCurrentRadius(this.currentRadius());


			if(disk.circularMotion()) {
				disk.coord().setX(disk.circularPath().getCenterX() + (disk.circularPath().getRadius() * Math.cos(Math.toRadians(disk.degree()))));
				disk.coord().setY(disk.circularPath().getCenterY() + (disk.circularPath().getRadius() * Math.sin(Math.toRadians(disk.degree()))));
			}
			if(disk.safe()) {
				disk.setSafeZone(new SafeZone(disk));
				Map.safeDisks()[Map.safeDiskSize()] = disk;
				Map.setSafeDiskSize(Map.safeDiskSize() + 1);
			}
			else {
				disk.setDiskIndex(Map.diskSize());
				Map.disks()[Map.diskSize()] = disk;
				Map.setDiskSize(Map.diskSize() + 1);
			}
		}
	}

	public void removeGhost() {
		Map.setGhostDiskSize(Map.ghostDiskSize() - 1);
		Map.disks()[Map.diskSize() - 1].setGhostIndex(this.ghostIndex());
		Map.ghostDisks()[this.ghostIndex()] = Map.ghostDisks()[Map.ghostDiskSize()];
		Map.ghostDisks()[Map.ghostDiskSize()] = null;

		for(int i = 0; i < Map.safeDiskSize(); i++) {
			Map.safeDisks()[i].safeZone().paths()[this.pathIndex()] = Map.safeDisks()[i].safeZone().paths()[Map.safeDisks()[i].safeZone().pathSize()];
			Map.safeDisks()[i].safeZone().paths()[Map.safeDisks()[i].safeZone().pathSize()] = null;
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

	public int spawn() {
		return spawn;
	}

	public void setSpawn(int spawn) {
		this.spawn = spawn;
	}

	public boolean redirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public int safeDiskIndex() {
		return safeDiskIndex;
	}

	public void setSafeDiskIndex(int safeDiskIndex) {
		this.safeDiskIndex = safeDiskIndex;
	}

	public boolean circularMotion() {
		return circularMotion;
	}

	public void setCircularMotion(boolean circularMotion) {
		this.circularMotion = circularMotion;
	}

	public Circle circularPath() {
		return circularPath;
	}

	public void setCircularPath(Circle circularPath) {
		this.circularPath = circularPath;
	}

	public double degree() {
		return degree;
	}

	public void setDegree(double degree) {
		this.degree = degree;
	}

	public double degreeChange() {
		return degreeChange;
	}

	public void setDegreeChange(double degreeChange) {
		this.degreeChange = degreeChange;
	}

	public int redirectIndex() {
		return redirectIndex;
	}

	public void setRedirectIndex(int redirectIndex) {
		this.redirectIndex = redirectIndex;
	}

	public double towardsCenter() {
		return towardsCenter;
	}

	public void setTowardsCenter(double towardsCenter) {
		this.towardsCenter = towardsCenter;
	}
}
