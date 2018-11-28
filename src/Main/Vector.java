package Main;

public class Vector {
	private double x;
	private double y;
	
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
