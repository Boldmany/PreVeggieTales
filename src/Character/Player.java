package Character;

import Main.Collision;
import Main.Delay;
import Main.Main;
import Main.Map;
import Main.Vector;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

public class Player {

	private Vector coord;
	private int radius = 13;
	private Vector vec = new Vector(0,0);
	private Image img;
	private Image normal;
	private Image pain;
	private Rotate rotate = new Rotate(0,0,0);
	private Vector dir = new Vector(0,0);
	private Delay dash = new Delay(4);
	private Delay invincibility = new Delay(19);
	private Delay cooldown = new Delay(20); 
	private Delay notDamaged = new Delay(60);
	private int lives = 15;
	private final static int MAX_LIVES = 15;
	private double speed;
	private boolean alive = true;

	/**
	 * this is the constructor for the character
	 * @param coord its coordinates
	 * @param normal the image displayed when it is not hit
	 * @param pain the image displayed when it is damaged
	 */
	public Player(Vector coord, Image normal, Image pain) {
		this.setImg(normal);
		this.setNormal(normal);
		this.setPain(pain);
		this.setCoord(coord);
		this.coord().setX(this.coord().x());
		this.coord().setY(this.coord().y());
		this.rotate().setPivotX(this.coord().x());
		this.rotate().setPivotY(this.coord().y());
		this.notDamaged().setDone(true);
		this.invincibility().setDone(true);
		this.cooldown().setDone(true);

		Map.players()[Map.playerSize()] = this;
		Map.setPlayerSize(Map.playerSize() + 1);
	}

	/**
	 * this function will be used to check for frame related stuff
	 */
	public void delayCheck() {

		this.move(); // this will move the character

		if(this.dash().done()) { // if the dash is done
			if(this.invincibility().delayCheck()) { // this will check for the
				this.invincibility().setDone(true); // no longer invincible
				this.invincibility().setFramesPassed(0); // reset the invincibility frames
			}
			if(this.cooldown().delayCheck()) { // if you can dash again
				this.cooldown().setDone(true);
				this.cooldown().setFramesPassed(0);
			}
		}
		else {
			if(this.dash().delayCheck()) { // check for if the player is done dashing and reset all the necessary things
				this.dash().setDone(true);
				this.cooldown().setDone(false);
				this.invincibility().setDone(false);
				this.invincibility().setFramesPassed(0);
				this.dash().setFramesPassed(0);
			}
		}
		if(this.notDamaged().delayCheck()) { // check for after taking damage and reset all the necessary things
			this.notDamaged().setDone(true);
			this.setImg(this.normal());
			this.notDamaged().setFramesPassed(0);
		}
	}

	/**
	 * this will check for collision between the player and any pink object
	 */
	public void collision() {
		boolean collision = false;
		for(int i = 0; i < Map.laserSize(); i++) { // between character and laser
			if(Collision.characterToLaser(new Circle(this.coord().x(), this.coord().y(), this.radius() - 7), Map.lasers()[i])) {
				collision = true;
			}
		}
		if(!collision) {
			for(int i = 0; i < Map.diskSize(); i++) { // between character and disk
				if(Collision.characterToDisk(this, Map.disks()[i])) {
					collision = true;
				}
			}
		}
		if(!collision) {
			for(int i = 0; i < Map.safeDiskSize(); i++) { // between the character and the outside of a safe disk
				if(Collision.characterToSafeDisk(this, Map.safeDisks()[i])) {
					collision = true;
				}
			}
		}

		if(collision) { // if you got hit
			this.setLives(this.lives() - 1); // decrease lives

			if(this.lives() == 0) { // if you die
				this.setAlive(false);
				boolean death = true;

				if(Map.playerSize() == 2) { // if 2 player mode is active
					for(int i = 0; i < Map.playerSize(); i++) {
						if(!Map.players()[i].alive()) {
							death = !death;
						}
					}
				}

				if(death) { // if death
					Main.setGameState(3); // die
					Map.levels()[Map.playLevel()].clip().stop();
				}
			} 
			else { // if your are still alive, then set all the things needed for when you take damage
				this.setImg(this.pain());
				this.notDamaged().setDone(false);
				this.notDamaged().setFramesPassed(0);
			}
		}
	}

	/**
	 * this will move the character
	 */
	public void move() {

		boolean angled = false;
		int degree = 0;

		// based on the direction of movement, it will find what angle to the character should be rendered on
		if(this.dir().x() == 1 && this.dir().y() == -1) {
			degree = 45;
			angled = true;
		}
		else if(this.dir().x() == 1 && this.dir().y() == 0) {
			degree = 90;
		}
		else if(this.dir().x() == 1 && this.dir().y() == 1) {
			degree = 135;
			angled = true;
		}
		else if(this.dir().x() == 0 && this.dir().y() == 1) {
			degree = 180;
		}
		else if(this.dir().x() == -1 && this.dir().y() == 1) {
			degree = 225;
			angled = true;
		}
		else if(this.dir().x() == -1 && this.dir().y() == 0) {
			degree = 270;
		}
		else if(this.dir().x() == -1 && this.dir().y() == -1){
			degree = 315;
			angled = true;
		}

		if(!this.dash().done()) { // if the character is currently dashing
			this.setSpeed(30);
		}
		else { // if the character is normally "walking"
			this.setSpeed(4);
		}
		if(angled) { // if at an angle
			this.setSpeed(this.speed() * Math.cos(Math.toRadians(45))); // this will make sure the character moves slower
		}

		this.coord().setY(this.coord().y() + (this.speed() * this.dir().y())); // this will change its x coordinate
		this.coord().setX(this.coord().x() + (this.speed() * this.dir().x())); // this will change its y coordinate
		this.rotate().setPivotX(this.coord().x()); // this is used for rotating the image
		this.rotate().setPivotY(this.coord().y()); // this is used for rotating the image
		this.rotate().setAngle(degree); // this is the angle for rotation

		Collision.characterToWall(this); // check for collision with wall

		if(this.dash().done() && this.invincibility().done() && this.notDamaged().done()) { // if not dashing and not invincible and not damaged
			this.collision(); // check for collision
		}
	}

	/**
	 * this is for when the player dies
	 */
	public static void death() {
		//the next few lines will reset all the character stats
		for(int i = 0; i < Map.playerSize(); i++) {
			Map.players()[i].setAlive(true);
			Map.players()[i].setLives(Player.MAX_LIVES());
			Map.players()[i].setImg(Map.players()[i].normal());
			Map.players()[i].notDamaged().setDone(true);
			Map.players()[i].dash().setDone(true);
			Map.players()[i].setDir(new Vector(0,0));

			if(i == 0) {
				Map.players()[i].setCoord(new Vector(500,300));
			}
			else {
				Map.players()[i].setCoord(new Vector(600,300));
			}
		}

		Map.levels()[Map.playLevel()].clear();
		
		// the next few lines will reset the level content and stop the music
		Map.levels()[Map.playLevel()].setShakeSize(0);
		Map.levels()[Map.playLevel()].setDiskSize(0);
		Map.levels()[Map.playLevel()].setLaserSize(0);
		Map.levels()[Map.playLevel()].setDoneLevel(false);
		Map.levels()[Map.playLevel()].setFrames(-100);
		Map.levels()[Map.playLevel()].setCurrentDisk(0);
		Map.levels()[Map.playLevel()].setCurrentLaser(0);
		Map.levels()[Map.playLevel()].setCurrentShake(0);


		Map.levels()[Map.playLevel()].levelReader("resources/levels/level" + (Map.playLevel() + 1) + "/levelEditor.txt");
	}

	public double radius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Vector vec() {
		return vec;
	}

	public void setVec(Vector vec) {
		this.vec = vec;
	}

	public Image img() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public Rotate rotate() {
		return rotate;
	}

	public void setRotate(Rotate rotate) {
		this.rotate = rotate;
	}

	public Vector coord() {
		return coord;
	}

	public void setCoord(Vector coord) {
		this.coord = coord;
	}

	public Vector dir() {
		return dir;
	}

	public void setDir(Vector dir) {
		this.dir = dir;
	}

	public double speed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Delay dash() {
		return dash;
	}

	public void setDash(Delay dash) {
		this.dash = dash;
	}

	public Delay cooldown() {
		return cooldown;
	}

	public void setCooldown(Delay cooldown) {
		this.cooldown = cooldown;
	}

	public int lives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Delay invincibility() {
		return invincibility;
	}

	public void setInvincibility(Delay invincibility) {
		this.invincibility = invincibility;
	}

	public Image pain() {
		return pain;
	}

	public void setPain(Image pain) {
		this.pain = pain;
	}

	public Image normal() {
		return normal;
	}

	public void setNormal(Image normal) {
		this.normal = normal;
	}

	public Delay notDamaged() {
		return notDamaged;
	}

	public void setNotDamaged(Delay notDamaged) {
		this.notDamaged = notDamaged;
	}

	public boolean alive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public static int MAX_LIVES() {
		return MAX_LIVES;
	}
}
