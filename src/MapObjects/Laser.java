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
	private boolean showSafe = false;

	/**
	 * this is the constructor for the lasers
	 * @param spawn the frame at which it is rendered on screen
	 * @param coord its coordinates
	 * @param vec its x and y vector components
	 * @param width
	 * @param height
	 * @param delay the amount of frames the warning should last 
	 * @param lifeSpan the amount of time the laser will last on screen
	 * @param dir the direction at which its heading (1:top to bottom, 2:right to left, 3:bottom to top, 4:left to right)
	 */
	public Laser(int spawn, Vector coord, Vector vec, double width, double height, Delay delay, Delay lifeSpan, int dir) {
		this.setSpawn(spawn);
		this.setCoord(coord);
		this.setVec(vec);
		this.setWidth(width);
		this.setHeight(height);
		this.setDelay(delay);
		this.setLifeSpan(lifeSpan);
		this.setDir(dir);
	}

	/**
	 * this is the constructor used for making a warning laser
	 * @param coord
	 * @param width
	 * @param height
	 * @param dir
	 * @param showSafe
	 */
	public Laser(Vector coord, double width, double height, int dir, boolean showSafe) {
		this.setCoord(coord);
		this.setWidth(width);
		this.setHeight(height);
		this.setDir(dir);
		this.setShowSafe(showSafe);

		//the next few lines will help in making the laser warning work according to how its direction
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

		for(int i = 0; i < Map.safeDiskSize(); i++) {
			Map.safeDisks()[i].safeZone().rectangle(this, true);
		}

		Map.ghostLasers()[Map.ghostLaserSize()] = this;
	}

	/**
	 * this will check each frame and function according to what delay has finished
	 */
	public void delayCheck() {

		// if the warning is over, make the warning disappear
		if(this.delay().delayCheck() && !this.delay().done()) {
			this.delay().setDone(true);
			if(this.delay().dur() != 0) {
				this.removeGhost();
			}
		}

		//have the laser move across the screen
		if(this.delay().done()) {
			this.move();

			//this will check for if its time to remove the laser from the screen
			if(this.lifeSpan().dur() != 0) {
				if(this.lifeSpan().delayCheck()) {
					this.death().setDone(true );;
				}
			}

			//this will remove the laser from the screen
			if(this.death().done()) {
				this.remove();
			}
		}

	}
	
	/**
	 * this will be used to move the laser across the screen
	 */
	public void move() {

		Collision.laserToWall(this);

		if(this.dir() == 1) { // if top to bottom
			this.setHeight(this.height() + this.vec().y()); // change the height to give off the illusion thats its going down
			this.coord().setX(this.coord().x() + this.vec().x()); // change x coordinate based on vector
		}
		else if(this.dir() == 2) { //if right to left
			this.coord().setX(this.coord().x() - this.vec().x()); // change the length and x coordinate to give off the illusion thats its going to the left
			this.setWidth(this.width() + this.vec().x());
			this.coord().setY(this.coord().y() + this.vec().y()); // change y coordinate based on vector
		}
		else if(this.dir() == 3) { // if bottom to top
			this.coord().setY(this.coord().y() - this.vec().y()); // change the height and y coordinate to give off the illusion thats its going up
			this.setHeight(this.height() + this.vec().y());
			this.coord().setX(this.coord().x() + this.vec().x()); // change x coordinate based on vector
		}
		else { // if left to right
			this.setWidth(this.width() + this.vec().x()); // change the length to give off the illusion thats its going to the right
			this.coord().setY(this.coord().y() + this.vec().y()); // change y coordinate based on vector
		}
	}

	/**
	 * this function will remove the warning laser
	 */
	public void removeGhost() {
		Map.setGhostLaserSize(Map.ghostLaserSize() - 1); // O(1) removal for the laser, this is done by replacing the first warning laser with the last one. Then setting the last one to be null
		Map.lasers()[Map.laserSize() - 1].setGhostIndex(this.ghostIndex());
		Map.ghostLasers()[this.ghostIndex()] = Map.ghostLasers()[Map.ghostLaserSize()];
		Map.ghostLasers()[Map.ghostLaserSize()] = null;

		for(int i = 0; i < Map.safeDiskSize(); i++) { // this is to remove it from showing up in the safe disks
			if(!this.showSafe()) {
				Map.safeDisks()[i].safeZone().setPathSize(Map.safeDisks()[i].safeZone().pathSize() - 1);
				Map.safeDisks()[i].safeZone().paths()[this.pathIndex()] = Map.safeDisks()[i].safeZone().paths()[Map.safeDisks()[i].safeZone().pathSize()];
				Map.safeDisks()[i].safeZone().paths()[Map.safeDisks()[i].safeZone().pathSize()] = null;
			}
		}
	}

	/**
	 *  this function will remove the laser
	 */
	public void remove() {
		Map.setLaserSize(Map.laserSize() - 1); // O(1) removal for the laser, this is done by replacing the first laser with the last one. Then setting the last one to be null
		Map.lasers()[Map.laserSize()].setLaserIndex(this.laserIndex()); 
		Map.lasers()[this.laserIndex()] = Map.lasers()[Map.laserSize()];
		Map.lasers()[Map.laserSize()] = null;
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

	public boolean showSafe() {
		return showSafe;
	}

	public void setShowSafe(boolean showSafe) {
		this.showSafe = showSafe;
	}
}
