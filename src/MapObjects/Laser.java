package MapObjects;
import Main.*;

public class Laser {
	
	private int spawn;
	private Vector coord;
	private Vector vec;
	private double width;
	private double height;
	private Delay delay;
	private Delay lifeSpan;
	private Delay death = new Delay(5);
	private int dir; 
	private int laserIndex;
	private int ghostIndex;
	private int pathIndex;
	
	
	public Laser(int spawn, Vector coord, Vector vec, double width, double height, Delay delay, Delay lifeSpan, int dir) {
		this.setSpawn(spawn);
		this.setCoord(coord);
		this.setVec(vec);
		this.setWidth(width);
		this.setHeight(height);
		this.setDelay(delay);
		this.setLifeSpan(lifeSpan);
		this.setDir(dir);
		
		//this.setLaserIndex(MapItems.laserSize());
		//MapItems.lasers()[this.laserIndex()] = this;
		//MapItems.setLaserSize(MapItems.laserSize() + 1);
		if(this.delay().dur() != 0) {
			this.setGhostIndex(MapItems.ghostLaserSize());
			//Laser warning = new Laser(new Vector(coord.x(),coord.y()), width, height, dir);
			//MapItems.ghostLasers()[this.ghostIndex()] = warning;
			//MapItems.setGhostLaserSize(MapItems.ghostLaserSize() + 1);
		}
	}
	
	public Laser(Vector coord, double width, double height, int dir) {
		this.setCoord(coord);
		this.setWidth(width);
		this.setHeight(height);
		this.setDir(dir);
		
		if(dir == 1) {
			this.setHeight(Main.canvas().getHeight());
		}
		else if(dir == 3) {
			this.coord().setY(0);
			this.setHeight(Main.canvas().getHeight());
		}
		else if(dir == 2) {
			this.coord().setX(0);
			this.setWidth(Main.canvas().getWidth());
		}
		else {
			this.setWidth(Main.canvas().getWidth());
		}
		
		for(int i = 0; i < MapItems.safeDiskSize(); i++) {
			MapItems.safeDisks()[i].safeZone().rectangle(this, true);
		}
		
		MapItems.ghostLasers()[MapItems.ghostLaserSize()] = this;
	}
	
	public void delayCheck() {
		
		if(this.delay().done()) {
			this.move();
		}
		else {
			if(this.delay().delayCheck()) {
				this.delay().setDone(true);
				this.removeGhost();
			}
		}
		
		if(this.delay().done() && this.lifeSpan().dur() != 0) {
			if(this.lifeSpan().delayCheck()) {
				this.remove();
			}
		}
		
		if(this.death().done()) {
			if(this.death().delayCheck()) {
				this.remove();
			}
		}
	}
	
	public void removeGhost() {
		MapItems.setGhostLaserSize(MapItems.ghostLaserSize() - 1);
		MapItems.lasers()[MapItems.laserSize() - 1].setGhostIndex(this.ghostIndex());
		MapItems.ghostLasers()[this.ghostIndex()] = MapItems.ghostLasers()[MapItems.ghostLaserSize()];
		MapItems.ghostLasers()[MapItems.ghostLaserSize()] = null;
		
		for(int i = 0; i < MapItems.safeDiskSize(); i++) {
			MapItems.safeDisks()[i].safeZone().setPathSize(MapItems.safeDisks()[i].safeZone().pathSize() - 1);
			MapItems.safeDisks()[i].safeZone().paths()[this.pathIndex()] = MapItems.safeDisks()[i].safeZone().paths()[MapItems.safeDisks()[i].safeZone().pathSize()];
			MapItems.safeDisks()[i].safeZone().paths()[MapItems.safeDisks()[i].safeZone().pathSize()] = null;
		}
	}
	
	public void remove() {
		MapItems.setLaserSize(MapItems.laserSize() - 1);
		MapItems.lasers()[MapItems.laserSize()].setLaserIndex(this.laserIndex()); 
		MapItems.lasers()[this.laserIndex()] = MapItems.lasers()[MapItems.laserSize()];
		MapItems.lasers()[MapItems.laserSize()] = null;
	}
	
	public void move() {
		
		Collision.laserToWall(this);
		
		if(this.dir() == 1) {
			this.setHeight(this.height() + this.vec().y());
			this.coord().setX(this.coord().x() + this.vec().x());
		}
		else if(this.dir() == 2) {
			this.coord().setX(this.coord().x() - this.vec().x());
			this.setWidth(this.width() + this.vec().x());
			this.coord().setY(this.coord().y() + this.vec().y());
		}
		else if(this.dir() == 3) {
			this.coord().setY(this.coord().y() - this.vec().y());
			this.setHeight(this.height() + this.vec().y());
			this.coord().setX(this.coord().x() + this.vec().x());
		}
		else {
			this.setWidth(this.width() + this.vec().x());
			this.coord().setY(this.coord().y() + this.vec().y());
		}
	}
	
	
	public Vector coord() {
		return coord;
	}

	public void setCoord(Vector coord) {
		this.coord = coord;
	}

	public double height() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double width() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public int dir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
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

	public int ghostIndex() {
		return ghostIndex;
	}

	public void setGhostIndex(int ghostIndex) {
		this.ghostIndex = ghostIndex;
	}
	
	public Vector vec() {
		return vec;
	}

	public void setVec(Vector vec) {
		this.vec = vec;
	}

	public Delay death() {
		return death;
	}

	public void setDeath(Delay death) {
		this.death = death;
	}

	public int laserIndex() {
		return laserIndex;
	}

	public void setLaserIndex(int laserIndex) {
		this.laserIndex = laserIndex;
	}

	public int pathIndex() {
		return pathIndex;
	}

	public void setPathIndex(int pathIndex) {
		this.pathIndex = pathIndex;
	}

	public int spawn() {
		return spawn;
	}

	public void setSpawn(int spawn) {
		this.spawn = spawn;
	}
}
