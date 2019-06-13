package Main;

public class Vector {
	private double x;
	private double y;
	
	/**
	 * this a x and y component vector
	 * @param x x component of the vector
	 * @param y y component of the vector
	 */
	public Vector(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public double x() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double y() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
