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
	private Vector acc = new Vector(0, 0); 
	private Vector dir = new Vector(0,0); 
	private double currentRadius = 0; 
	private double finalRadius;
	private double radiusChange;
	private double degree; 
	private double degreeChange;
	private double killRate = -1.4;
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
	private int ghostIndex = -1;
	private int pathIndex;
	private int safeDiskIndex;
	private int redirectIndex;
	private boolean sineMotion = false;
	private double amplitude;
	private double period;

	/**
	 * this is the constructor for the disk class 
	 * @param spawn the frame the circle spawns on the screen
	 * @param coord its coordinates
	 * @param vec its vector
	 * @param acc its acceleration
	 * @param finalRadius the radius it should finish off with
	 * @param radiusChange changes the radius of the circle
	 * @param circularMotion this will say if its going to move in a circle
	 * @param degree the degree on the circular path it will be on
	 * @param degreeChange change the degree each frame by this amount
	 * @param towardsCenter change the radius of the circular path
	 * @param circularPath this is the circular path, it will travel on the parameter of the circular
	 * @param delay this is the amount of frames the warning will last before the real circle spawns
	 * @param lifeSpan the amount of frames the circle will last on the screen
	 * @param safe if this is a safe zone
	 * @param redirect if the circle will be redirected
	 */
	public Disk(int spawn, Vector coord, Vector vec, Vector acc, double finalRadius, double radiusChange,  
			boolean circularMotion, double degree, double degreeChange, double towardsCenter, Circle circularPath, Delay delay, Delay lifeSpan, boolean safe, boolean redirect) {
		this.setSpawn(spawn);
		this.setCoord(coord);
		this.setVec(vec);
		this.setAcc(acc);
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
	}

	/** 
	 * this is the constructor for the warning for each circle
	 * @param coord
	 * @param finalRadius
	 */
	public Disk(Vector coord, double finalRadius) {
		this.setCoord(coord);
		this.setCurrentRadius(finalRadius);
		for(int i = 0; i < Map.safeDiskSize(); i++) {
			Map.safeDisks()[i].safeZone().circle(this, true);
		}
	}

	/** 
	 * this is used for when a circle is changing its path
	 * @param vec
	 * @param radiusChange
	 * @param degreeChange
	 * @param finalRadius
	 * @param lifeSpan
	 * @param redirect
	 */
	public Disk(Vector vec, double radiusChange, double degreeChange, double finalRadius, Delay lifeSpan, boolean redirect) {
		this.setVec(vec);
		this.setRadiusChange(radiusChange);
		this.setDegreeChange(degreeChange);
		this.setFinalRadius(finalRadius);
		this.setLifeSpan(lifeSpan);
		this.setRedirect(redirect);
	}

	/**
	 * this will act according to how many frames the disk was on the screen for
	 */
	public void delayCheck() {
		if(this.delay().delayCheck() && !this.delay().done()) { //this will check for when the warning must disappear 
			this.delay().setDone(true);
			if(this.delay().dur() != 0 && !this.safe()) {
				this.removeGhost(); // this will remove the warning
			}
		}

		if(this.delay().done()) { // once the warning is done
			this.changeRadius();
			if(!Objects.isNull(this.ghostIndex())) {
				if(this.currentRadius() == this.finalRadius() && this.radiusChange() != 0) { // once the circle has reached its max radius
					if(this.lifeSpan().dur() == 0 && this.vec().x() == 0 && this.vec().y() == 0) { // if it has nothing else to do
						this.setRadiusChange(this.killRate()); // this will make the circle decrease
					}
					else { // if the circle still has actions it must complete
						this.setRadiusChange(0);
					}
				}
			}
			if(this.radiusChange() == 0 && this.currentRadius() != this.finalRadius()){ // this will make circles instantly have their current radius become their max radius
				this.setCurrentRadius(this.finalRadius());
			}

			if(this.circularMotion()) { // if the path of the circle is a circle
				if(this.towardsCenter() != 0) { //if the circular path is decreasing in radius
					double compare = this.towardsCenter(); // this will help later to make sure that the acceleration doesn't make the towards center inverse
					this.setTowardsCenter(this.towardsCenter() + this.acc().y()); // this will accelerate the how fast the radius of the circular motion changes
					if(this.towardsCenter() <= 0 && compare > 0) { // to check if the towards center inverted on itself
						this.acc().setY(0); // no more acceleration 
					}
					this.circularPath().setRadius(this.circularPath().getRadius() - this.towardsCenter()); // this will set the new circular path radius
					if(this.circularPath().getRadius() <= 0) { // if the radius of the circular path is less than 0
						this.circularPath().setRadius(0); // make the radius of the circular path 0
						this.setTowardsCenter(0); // this will make the decrease 0, therefore no longer running the this code
						this.acc().setY(0); // no more acceleration
					}
				}
				this.moveInCircle(); // this will call the function to move in a circle
			}
			else if(this.sineMotion()){ // if the path of the circle is a sine motion
				this.sineMove(); // this will call the function to move in a sine motion
			}
			else { // if the path of the circle is linear
				this.move(); // this will call the function to move in a linear motion
			}

			if(this.lifeSpan().dur() != 0) { // if it doesn't die by collision
				if((this.currentRadius() == this.finalRadius()) || this.safe()) { // if its the maximum radius or a safe zone
					if(this.lifeSpan().delayCheck()) { // this will check if the frames passed is equal to its life span
						if(this.redirect() || this.safe())  { // if its being redirected or if its a safe circle
							this.death().setDone(true); // remove it from the screen
						}
						else {
							this.setRadiusChange(this.killRate()); // this will decrease it until it becomes 0
						}
					}
				}	
			}
		}
		if(this.death().done()) { // if its time to remove it
			this.remove(); // remove the disk from the game
		}
	}

	/**
	 *  this will change the radius according to the radius change of the disk
	 */
	public void changeRadius() {
		if(this.currentRadius() < this.finalRadius() && this.radiusChange() > 0) { // if the current radius isn't its final radius
			this.setCurrentRadius(this.currentRadius() + this.radiusChange());  // this will change the current radius
			if(this.currentRadius() > this.finalRadius()) { // if i happen to get a bigger current radius than the final radius
				this.setCurrentRadius(this.finalRadius()); // set the current radius to be the final
			}
		}
		else if(this.currentRadius() > 0 && this.radiusChange() < 0) { // if the circle is decreasing in size
			this.setCurrentRadius(this.currentRadius() + this.radiusChange()); // change the current radius
			if(this.currentRadius() <= 0) { // if the current radius is less than or equal to 0 
				this.setCurrentRadius(0); // set its radius to be zero
				this.death().setDone(true); // this will make sure the circle is removed

			}
		}
	}

	/**
	 *  the circle moves at a constant rate 
	 */
	public void move() {

		Collision.diskToWall(this); // this will check for collision

		this.coord().setX(this.coord().x() + this.vec().x()); // move in x direction
		this.coord().setY(this.coord().y() + this.vec().y()); // move in y direction

		if((this.dir().x() < 0 && this.vec().x() < 0) || (this.dir().x() > 0 && this.vec().x() > 0)) { // this will make sure that after decelerating, it wont change directions
			this.vec().setX(this.vec().x() + (this.dir().x()) * this.acc().x()); // this will make the speed of the x vector increase or decrease. 
		}
		else { // if the speed makes it go in the opposite direction
			this.vec().setX(0);  // set the speed to 0
		}
		if((this.dir().y() < 0 && this.vec().y() < 0) || (this.dir().y() > 0 && this.vec().y() > 0)) { // this will make sure that after decelerating, it wont change directions
			this.vec().setY(this.vec().y() + (this.dir().y()) * this.acc().y()); // this will make the speed of the y vector increase or decrease. 
		}
		else { // if the speed makes it go in the opposite direction
			this.vec().setY(0); // set the speed to 0
		}


	}

	/** 
	 * this will move the circle in a sine motion 
	 */
	public void sineMove() {
		this.coord().setX(this.coord().x() + this.vec().x()); // this will set the x coordinate
		this.coord().setY((this.amplitude() * Math.sin(this.period() * Math.toRadians(this.coord().x())) + this.coord().y())); // this will set the y coordinate, based on some complex math
	}

	/**
	 * this will move the circle in a circular path
	 */
	public void moveInCircle() {
		this.setDegreeChange(this.degreeChange() + this.acc().x()); // this will change the amount of degrees the circle changes each frame
		this.setDegree((this.degree() + this.degreeChange()) % 360); // this is the degrees on the circular path 
		this.coord().setX(this.circularPath().getCenterX() + (this.circularPath().getRadius() * Math.cos(Math.toRadians(this.degree())))); // this is the math for finding out the x coordinate based on the degree in the circle
		this.coord().setY(this.circularPath().getCenterY() - (this.circularPath().getRadius() * Math.sin(Math.toRadians(this.degree())))); // this is the math for finding out the y coordinate based on the degree in the circle
	}

	/**
	 * this function will remove both the safe circle or the regular circle from the screen
	 */
	public void remove() {
		if(!this.safe()) { // if its a regular circle
			Map.setDiskSize(Map.diskSize() - 1); // O(1) removal for the laser, this is done by replacing the first disk with the last one. Then setting the last one to be null
			Map.disks()[Map.diskSize()].setDiskIndex(this.diskIndex()); 
			Map.disks()[this.diskIndex()] = Map.disks()[Map.diskSize()];
			Map.disks()[Map.diskSize()] = null;
		}
		else { // if its a safe circle
			Map.setSafeDiskSize(Map.safeDiskSize() - 1); // O(1) removal for the laser, this is done by replacing the first safe disk with the last one. Then setting the last one to be null
			Map.safeDisks()[Map.safeDiskSize()].setSafeDiskIndex(this.safeDiskIndex()); 
			Map.safeDisks()[this.safeDiskIndex()] = Map.safeDisks()[Map.safeDiskSize()];
			Map.safeDisks()[Map.safeDiskSize()] = null;
		}

		if(this.redirect()) { //this will check if the disk is being redirected
			// this lump of code is used to create the new redirected circle
			Disk disk = new Disk(0, this.coord(), Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].vec(),
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].acc(), Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].finalRadius(), 
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].radiusChange(),
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].circularMotion(), 
					this.degree(), Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].degreeChange(),
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].towardsCenter(),
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].circularPath(), new Delay(0),
					Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].lifeSpan(),
					this.safe(), Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].redirect());
			disk.setDir(Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].dir());
			disk.setKillRate(Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].killRate());
			
			if(disk.redirect()) { //if the redirected circle will once again be redirected
				disk.setRedirectIndex(Map.levels()[Map.playLevel()].redirect()[this.redirectIndex()].redirectIndex()); // give it an index within the redirected array
			}
			disk.setCurrentRadius(this.currentRadius()); // this will make sure that the redirected disk will have the same radius as the previous one


			if(disk.circularMotion()) { // if the redirected disk moves in a circular motion
				
				// the next few lines are used to find the degrees on the circular path and set its x and y coordinates
				double radius = Math.sqrt(Math.pow(disk.circularPath().getCenterX() - disk.coord().x(), 2) + Math.pow(disk.circularPath().getCenterY() - disk.coord().y(), 2));
				double degrees = 0; 
				if(disk.circularPath().getCenterY() - disk.coord().y() == 0){
					if(disk.circularPath().getCenterX() - disk.coord().x() < 0){
						degrees = 0;
					}
					else{
						degrees = 180;
					}
				}
				else if(disk.circularPath().getCenterX() - disk.coord().x() == 0){
					if(disk.circularPath().getCenterY() - disk.coord().y() < 0){
						degrees = 270;
					}
					else{
						degrees = 90;
					}
				}
				else if(disk.circularPath().getCenterX() - disk.coord().x() < 0 && disk.circularPath().getCenterY() - disk.coord().y() < 0){
					degrees = 360 - Math.toDegrees(Math.atan(Math.abs((disk.circularPath().getCenterY() - disk.coord().y())/(disk.circularPath().getCenterX() - disk.coord().x()))));
				}
				else if(disk.circularPath().getCenterX() - disk.coord().x() > 0 && disk.circularPath().getCenterY() - disk.coord().y() < 0){
					degrees = 180 + Math.toDegrees(Math.atan(Math.abs(disk.circularPath().getCenterY() - disk.coord().y())/(disk.circularPath().getCenterX() - disk.coord().x())));

				}
				else if(disk.circularPath().getCenterX() - disk.coord().x() > 0 && disk.circularPath().getCenterY() - disk.coord().y() > 0){
					degrees = 180 - Math.toDegrees(Math.atan(Math.abs((disk.circularPath().getCenterY() - disk.coord().y())/(disk.circularPath().getCenterX() - disk.coord().x()))));
				}
				else{
					degrees = Math.toDegrees(Math.atan(Math.abs((disk.circularPath().getCenterY() - disk.coord().y())/(disk.circularPath().getCenterX() - disk.coord().x()))));
				}
				disk.circularPath().setRadius(radius);
				disk.setDegree(degrees);
				if(degrees > 90 && degrees < 270){
					disk.coord().setX(disk.circularPath().getCenterX() + (radius * Math.cos(Math.toRadians(disk.degree()))));
					disk.coord().setY(disk.circularPath().getCenterY() - (radius * Math.sin(Math.toRadians(disk.degree()))));
				}
				else{
					disk.coord().setX(disk.circularPath().getCenterX() + (radius * Math.cos(Math.toRadians(disk.degree()))));
					disk.coord().setY(disk.circularPath().getCenterY() + (radius * Math.sin(Math.toRadians(disk.degree()))));
				}
			}
			
			if(disk.safe()) { // if the redirected circle is a safe zone
				disk.setSafeZone(new SafeZone(disk)); // this will make the safe zone circle
				Map.safeDisks()[Map.safeDiskSize()] = disk; // the next two lines will add it to the array for safe circles
				Map.setSafeDiskSize(Map.safeDiskSize() + 1);
			}
			else { // if the redirected circle is a regular circle
				disk.setDiskIndex(Map.diskSize()); // give it an index in the array
				Map.disks()[Map.diskSize()] = disk; // the next two lines will add it to the array for circles
				Map.setDiskSize(Map.diskSize() + 1);
			}
		}
	}

	/**
	 * this will remove the warning circles
	 */
	public void removeGhost() {
		int place = 0;
		int highest = 0;

		for(int i = 0; i < Map.diskSize(); i++) { // this will let find the circle that has the last index for the array of warnings 
			if(Map.disks()[i].ghostIndex() > highest) {
				place = i;
				highest = Map.disks()[i].ghostIndex();
			}
		}

		Map.setGhostDiskSize(Map.ghostDiskSize() - 1); // O(1) removal for the laser, this is done by replacing the first warning disk with the last one. Then setting the last one to be null
		Map.ghostDisks()[this.ghostIndex()] = Map.ghostDisks()[Map.disks()[place].ghostIndex()];
		Map.ghostDisks()[Map.disks()[place].ghostIndex()] = null;
		Map.disks()[place].setGhostIndex(this.ghostIndex());

		for(int i = 0; i < Map.safeDiskSize(); i++) { // remove them from being rendered inside a safe circle
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

	public Vector acc() {
		return acc;
	}

	public void setAcc(Vector acc) {
		this.acc = acc;
	}

	public double killRate() {
		return killRate;
	}

	public void setKillRate(double killRate) {
		this.killRate = killRate;
	}

	public boolean sineMotion() {
		return sineMotion;
	}

	public void setSineMotion(boolean sineMotion) {
		this.sineMotion = sineMotion;
	}

	public double amplitude() {
		return amplitude;
	}

	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}

	public double period() {
		return period;
	}

	public void setPeriod(double period) {
		this.period = period;
	}
}
