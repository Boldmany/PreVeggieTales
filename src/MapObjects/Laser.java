package MapObjects;
import Main.*;

public class Laser {
	
	private Vector coord;
	private Vector vec = new Vector(0,0);
	private double width;
	private double height;
	private int dir; 
	private Delay delay;
	private Delay lifeSpan = new Delay(0);
	private Delay death = new Delay(5);
	private int laserIndex;
	private int ghostIndex;
	
	
	public Laser(Vector coord, double width, double height, int dir, Delay delay) {
		this.setCoord(coord);
		this.setWidth(width);
		this.setHeight(height);
		this.setDir(dir);
		this.setDelay(delay);
		
		this.setLaserIndex(MapItems.laserSize());
		MapItems.lasers()[this.laserIndex()] = this;
		MapItems.setLaserSize(MapItems.laserSize() + 1);
		if(this.delay().dur() != 0) {
			this.setGhostIndex(MapItems.ghostLaserSize());
			ghost(new Vector(coord.x(),coord.y()), width, height, dir);
			MapItems.setGhostLaserSize(MapItems.ghostLaserSize() + 1);
		}
	}
	
	public Laser(Vector coord, double width, double height, int dir) {
		this.setCoord(coord);
		this.setWidth(width);
		this.setHeight(height);
		this.setDir(dir);
	}

	public void ghost(Vector coord, double width, double height, int dir) {
		Laser warning = new Laser(coord, width, height, dir);
		if(dir == 1) {
			warning.setHeight(Main.canvas().getHeight());
		}
		else if(dir == 3) {
			warning.coord().setY(0);
			warning.setHeight(Main.canvas().getHeight());
		}
		else if(dir == 2) {
			warning.coord().setX(0);
			warning.setWidth(Main.canvas().getWidth());
		}
		else {
			warning.setWidth(Main.canvas().getWidth());
		}
		MapItems.ghostLasers()[MapItems.ghostLaserSize()] = warning;
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
}
