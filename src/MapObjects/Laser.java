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
	
	
	public Laser(Vector coord, double width, double height, int dir, Delay delay, boolean real) {
		this.setCoord(coord);
		this.setWidth(width);
		this.setHeight(height);
		this.setDir(dir);
		this.setDelay(delay);
		
		if(real) {
			this.setLaserIndex(MapItems.laserSize());
			MapItems.lasers()[this.laserIndex()] = this;
			MapItems.setLaserSize(MapItems.laserSize() + 1);
			if(this.delay().dur() != 0) {
				this.setGhostIndex(MapItems.ghostLaserSize());
				ghost(new Vector(coord.x(),coord.y()), width, height, dir, new Delay(0));
				MapItems.setGhostLaserSize(MapItems.ghostLaserSize() + 1);
			}
		}
	}

	public void ghost(Vector coord, double width, double height, int dir, Delay delay) {
		Laser warning = new Laser(coord, width, height, dir, delay, false);
		if(dir == 1) {
			warning.setWidth(Main.canvas().getWidth());
		}
		else if(dir == 3) {
			warning.coord().setX(0);
			warning.setWidth(Main.canvas().getWidth());
		}
		else if(dir == 2) {
			warning.coord().setY(0);
			warning.setHeight(Main.canvas().getHeight());
		}
		else {
			warning.setHeight(Main.canvas().getHeight());
		}
		MapItems.ghostLasers()[MapItems.ghostLaserSize()] = warning;
	}
	
	public void delayCheck() {
		boolean removed = this.delay().done();
		if(!this.delay().done()) {
			if(this.delay().framesPassed() >= this.delay().dur()) {
				this.delay().setDone(true);
				MapItems.setGhostLaserSize(MapItems.ghostLaserSize() - 1);
				for(int i = this.ghostIndex(); i < MapItems.ghostLaserSize(); i++) {
					MapItems.ghostLasers()[i] = MapItems.ghostLasers()[i + 1];
					MapItems.lasers()[i + 1].setGhostIndex(MapItems.lasers()[i + 1].ghostIndex() - 1);
				}
				MapItems.ghostLasers()[MapItems.ghostLaserSize()] = null;
			}
			else {
				this.delay().increase();
			}
		}
		
		if(removed && !this.lifeSpan().done()) {
			if(this.lifeSpan().framesPassed() >= this.lifeSpan().dur()) {
				MapItems.setLaserSize(MapItems.laserSize() - 1);
				for(int i = this.laserIndex(); i < MapItems.laserSize(); i++) {
					MapItems.lasers()[i] = MapItems.lasers()[i + 1];
					MapItems.lasers()[i].setLaserIndex(MapItems.lasers()[i].laserIndex() - 1);
				}
				MapItems.lasers()[MapItems.laserSize()] = null;
			}
			else {
				this.lifeSpan().increase();
			}
		}
	}
	
	
	public void Move() {
		Collision.laserToWall(this);
		if(this.dir() == 1) {
			this.setWidth(this.width() + this.vec().x());
			this.coord().setY(this.coord().y() + this.vec().y());
		}
		else if(this.dir() == 2) {
			this.coord().setY(this.coord().y() - this.vec().y());
			this.setHeight(this.height() + this.vec().y());
			this.coord().setX(this.coord().x() + this.vec().x());
		}
		else if(this.dir() == 3) {
			this.coord().setX(this.coord().x() - this.vec().x());
			this.setWidth(this.width() + this.vec().x());
			this.coord().setY(this.coord().y() + this.vec().y());
		}
		else {
			this.setHeight(this.height() + this.vec().y());
			this.coord().setX(this.coord().x() + this.vec().x());
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
