package Main;

public class Delay {
	private double dur;
	private double secondsPassed = 0;
	private int framesPassed = 0;
	private boolean done = false;
	
	public Delay(double dur) {
		this.setDur(dur);
	}
	
	public void increase() {
		this.setFramesPassed(this.framesPassed() + 1);
		this.setSecondsPassed(this.framesPassed()/60);
	}
	
	public boolean delayCheck() {
		boolean done = false;
		if(this.framesPassed() >= this.dur()) {
			done = true;
		}
		else {
			this.increase();
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



	public double secondsPassed() {
		return secondsPassed;
	}



	public void setSecondsPassed(double secondsPassed) {
		this.secondsPassed = secondsPassed;
	}



	public int framesPassed() {
		return framesPassed;
	}



	public void setFramesPassed(int framesPassed) {
		this.framesPassed = framesPassed;
	}
}
