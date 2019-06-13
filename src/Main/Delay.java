package Main;

public class Delay {
	private double dur;
	private int framesPassed = 0;
	private boolean done = false;
	
	/**
	 * this is the constructor that will set the amount of frames for a delay
	 * @param dur duration of the delay
	 */
	public Delay(double dur) {
		this.setDur(dur);
	}
	
	/**
	 * this will check for if the delay is over or not
	 * @return if its done or not
	 */
	public boolean delayCheck() {
		boolean done = false;
		
		// self explanatory
		if(this.framesPassed() < this.dur()) { 
			this.setFramesPassed(this.framesPassed() + 1);
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
