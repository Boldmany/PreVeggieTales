package Character;

import java.util.Arrays;

import Main.Collision;
import Main.Delay;
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
	private int lives = 7;
	private double speed;
	private boolean alive = true;
	
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
	
	public void move() {
		
		boolean angled = false;
		int degree = 0;
		
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
		
		if(!this.dash().done()) {
			this.setSpeed(30);
		}
		else {
			this.setSpeed(4);
		}
		if(angled) {
			this.setSpeed(this.speed() * Math.cos(Math.toRadians(45)));
		}
		
		this.coord().setY(this.coord().y() + (this.speed() * this.dir().y()));
		this.coord().setX(this.coord().x() + (this.speed() * this.dir().x()));
		this.rotate().setPivotX(this.coord().x());
		this.rotate().setPivotY(this.coord().y());
		this.rotate().setAngle(degree);
		
		Collision.characterToWall(this);
	
		if(this.dash().done() && this.invincibility().done() && this.notDamaged().done()) {
			this.collision();
		}
	}

	public void delayCheck() {
		
		this.move();
		
		if(this.dash().done()) {
			if(!this.invincibility().done()) {
				if(this.invincibility().delayCheck()) {
					this.invincibility().setDone(true);
					this.invincibility().setFramesPassed(0);
				}
			}
			if(!this.cooldown().done()) {
				if(this.cooldown().delayCheck()) {
					this.cooldown().setDone(true);
					this.cooldown().setFramesPassed(0);
				}
			}
		}
		else {
			if(this.dash().delayCheck()) {
				this.dash().setDone(true);
				this.cooldown().setDone(false);
				this.invincibility().setDone(false);
				this.invincibility().setFramesPassed(0);
				this.dash().setFramesPassed(0);
			}
		}
		if(this.notDamaged().delayCheck()) {
			this.notDamaged().setDone(true);
			this.setImg(this.normal());
			this.notDamaged().setFramesPassed(0);
		}
	}
	
	public void collision() {
		boolean collision = false;
		for(int i = 0; i < Map.laserSize(); i++) {
			if(Collision.characterToLaser(new Circle(this.coord().x(), this.coord().y(), this.radius() - 7), Map.lasers()[i])) {
				collision = true;
			}
		}
		if(!collision) {
			for(int i = 0; i < Map.diskSize(); i++) {
				if(Collision.characterToDisk(this, Map.disks()[i])) {
					collision = true;
				}
			}
		}
		if(!collision) {
			for(int i = 0; i < Map.safeDiskSize(); i++) {
				if(Collision.characterToSafeDisk(this, Map.safeDisks()[i])) {
					collision = true;
				}
			}
		}
		
		if(collision) {
			this.setLives(this.lives() - 1);
			
			if(this.lives() == 0) {
				this.setAlive(false);
				boolean death = true;
				
				if(Map.playerSize() == 2) {
					for(int i = 0; i < Map.playerSize(); i++) {
						if(!Map.players()[i].alive()) {
							death = !death;
						}
					}
				}
				
				if(death) {
					death();
				}
			}
			else {
				this.setImg(this.pain());
				this.notDamaged().setDone(false);
				this.notDamaged().setFramesPassed(0);
			}
		}
	}
	
	public void reborn() {
		this.setAlive(true);
		this.setLives(7);
	}
	
	public static void death() {
		for(int i = 0; i < Map.playerSize(); i++) {
			Map.players()[i].reborn();
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
		
		Arrays.fill(Map.disks(), null);
		Arrays.fill(Map.lasers(), null);
		Arrays.fill(Map.ghostDisks(), null);
		Arrays.fill(Map.ghostLasers(), null);
		Arrays.fill(Map.safeDisks(), null);
		Map.setDiskSize(0);
		Map.setLaserSize(0);
		Map.setGhostDiskSize(0);
		Map.setGhostLaserSize(0);
		Map.setSafeDiskSize(0);

		Map.levels()[Map.playLevel()].setDiskSize(0);
		Map.levels()[Map.playLevel()].setLaserSize(0);
		
		if(Map.levels()[Map.playLevel()].currentCheckpoint() != 0) {
			Map.levels()[Map.playLevel()].setFrames(Map.levels()[Map.playLevel()].checkpoints()[Map.levels()[Map.playLevel()].currentCheckpoint() - 1].frame() - 100);
			Map.levels()[Map.playLevel()].setCurrentDisk(Map.levels()[Map.playLevel()].checkpoints()[Map.levels()[Map.playLevel()].currentCheckpoint() - 1].currentDisk());
			Map.levels()[Map.playLevel()].setCurrentLaser(Map.levels()[Map.playLevel()].checkpoints()[Map.levels()[Map.playLevel()].currentCheckpoint() - 1].currentLaser());
			Map.levels()[Map.playLevel()].clip().setFramePosition(Map.levels()[Map.playLevel()].checkpoints()[Map.levels()[Map.playLevel()].currentCheckpoint() - 1].musicFrame());
		}
		else {
			Map.levels()[Map.playLevel()].setFrames(0);
			Map.levels()[Map.playLevel()].setCurrentDisk(0);
			Map.levels()[Map.playLevel()].setCurrentLaser(0);
			Map.levels()[Map.playLevel()].clip().setFramePosition(0);
			
		}
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
}
