package Main;

public class Delay {
	private double dur;
	private int framesPassed = 0;
	private boolean done = false;
	
	public Delay(double dur) {
		this.setDur(dur);
	}
	
	public void increase() {
		this.setFramesPassed(this.framesPassed() + 1);
	}
	
	public boolean delayCheck() {
		boolean done = false;
		
		if(this.framesPassed() < this.dur()) {
			this.increase();
		}
		else if(this.framesPassed() == this.dur()) {
			done = true;
		}
		
		return done;
	}
	
	public double dur() {
		return dur;
	}

	public void setDur(double durr) {
		this.dur = durr;
	}

	public boolean done() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public int framesPassed() {
		return framesPassed;
	}

	public void setFramesPassed(int framesPassed) {
		this.framesPassed = framesPassed;
	}
}
