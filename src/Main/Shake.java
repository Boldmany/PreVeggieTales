package Main;

public class Shake {
	
	private int spawn;
	private Delay dur;
	private Vector degree;
	
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
