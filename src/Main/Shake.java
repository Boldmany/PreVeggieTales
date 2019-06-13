package Main;

public class Shake {
	
	private int spawn;
	private Delay dur;
	private Vector degree;
	
	/**
	 * this will set a shake in the game
	 * @param spawn the frame it will spawn at
	 * @param dur how long it will last
	 * @param degree and how much the screen will shake
	 */
	public Shake (int spawn, Delay dur, Vector degree) {
		this.setSpawn(spawn);
		this.setDur(dur);
		this.setDegree(degree);
	}
	
	
	public int spawn() {
		return spawn;
	}
	public void setSpawn(int spawn) {
		this.spawn = spawn;
	}
	public Delay dur() {
		return dur;
	}
	public void setDur(Delay dur) {
		this.dur = dur;
	}


	public Vector degree() {
		return degree;
	}


	public void setDegree(Vector degree) {
		this.degree = degree;
	}
}
