package Character;

import Main.MapItems;
import Main.Vector;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Pineapple {
	
	private Vector coord;
	private int radius = 13;
	private Vector vec = new Vector(0,0);
	private Image img = new Image("file:resources/pineapple.png");
	private Rotate rotate = new Rotate(0,0,0);
	private int dir = 0;
	
	public Pineapple(Vector coord) {
		this.setCoord(coord);
		this.coord().setX(this.coord().x());
		this.coord().setY(this.coord().y());
		rotate.setPivotX(this.coord().x());
		rotate.setPivotY(this.coord().y());
		MapItems.players()[MapItems.playerSize()] = this;
		MapItems.setPlayerSize(MapItems.playerSize() + 1);
	}
	
	public void rotation() {
		rotate.setPivotX(this.coord().x());
		rotate.setPivotY(this.coord().y());
		if(this.dir() == 1) {
			rotate.setAngle(0);
		}
		else if(this.dir() == 2) {
			rotate.setAngle(45);
		}
		else if(this.dir() == 3) {
			rotate.setAngle(90);
		}
		else if(this.dir() == 4) {
			rotate.setAngle(135);
		}
		else if(this.dir() == 5) {
			rotate.setAngle(180);
		}
		else if(this.dir() == 6) {
			rotate.setAngle(-135);
		}
		else if(this.dir() == 7) {
			rotate.setAngle(-90);
		}
		else {
			rotate.setAngle(-45);
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

	public int dir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
	
	public Vector coord() {
		return coord;
	}

	public void setCoord(Vector coord) {
		this.coord = coord;
	}
}
