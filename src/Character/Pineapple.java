package Character;

import Main.*;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Pineapple {
	
	private Vector coord;
	private int radius = 13;
	private Vector vec = new Vector(0,0);
	private Image img = new Image("file:resources/pineapple.png");
	private Rotate rotate = new Rotate(0,0,0);
	private Vector dir = new Vector(0,0);
	private Delay dash = new Delay(3);
	private Delay cooldown = new Delay(20);
	private double speed;
	
	public Pineapple(Vector coord) {
		this.setCoord(coord);
		this.coord().setX(this.coord().x());
		this.coord().setY(this.coord().y());
		rotate.setPivotX(this.coord().x());
		rotate.setPivotY(this.coord().y());
		Map.players()[Map.playerSize()] = this;
		Map.setPlayerSize(Map.playerSize() + 1);
	}
	
	public void move() {
		
		this.delayCheck();
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
		rotate.setPivotX(this.coord().x());
		rotate.setPivotY(this.coord().y());
		rotate.setAngle(degree);
	}

	public void delayCheck() {
		if(this.dash().done()) {
			this.setSpeed(4);
			this.dash().setFramesPassed(0);
			
			if(!this.cooldown().done()) {
				if(this.cooldown().framesPassed() >= this.cooldown().dur()) {
					this.cooldown().setDone(true);
					this.cooldown().setFramesPassed(0);
				}
				else {
					this.cooldown().increase();
				}
			}
		}
		else {
			if(this.dash().framesPassed() >= this.dash().dur()) {
				this.dash().setDone(true);
				this.cooldown().setDone(false);
			}
			else {
				this.dash().increase();
			}
		}
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
}
