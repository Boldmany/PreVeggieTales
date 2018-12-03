package Character;

import Main.Vector;
import javafx.scene.image.Image;

public class Pineapple {
	private Vector coord;
	private Vector bodyCoord;
	private double radius;
	private Vector vec = new Vector(0,0);
	private Image img = new Image("file:resources/pineapple.pmg");
	
	public void rotate(double degree) {
	}

	public Vector coord() {
		return coord;
	}

	public void setCoord(Vector coord) {
		this.coord = coord;
	}

	public Vector bodyCoord() {
		return bodyCoord;
	}

	public void setBodyCoord(Vector bodyCoord) {
		this.bodyCoord = bodyCoord;
	}

	public double radius() {
		return radius;
	}

	public void setRadius(double radius) {
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
}
