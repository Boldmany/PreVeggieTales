package Main;

public class Checkpoint {
	private int frame;
	private int musicFrame;
	private int currentLaser;
	private int currentDisk;
	
	public Checkpoint(int frame) {
		this.setFrame(frame);
	}
	
	public int frame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
	public int musicFrame() {
		return musicFrame;
	}
	public void setMusicFrame(int musicFrame) {
		this.musicFrame = musicFrame;
	}
	public int currentLaser() {
		return currentLaser;
	}
	public void setCurrentLaser(int currentLaser) {
		this.currentLaser = currentLaser;
	}
	public int currentDisk() {
		return currentDisk;
	}
	public void setCurrentDisk(int currentDisk) {
		this.currentDisk = currentDisk;
	}
}
