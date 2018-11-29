package MapObjects;

import Main.Vector;

public class Disk {
	private Vector coord;
	private double rad;
	private Vector vec = new Vector(0,0);
	private Delay delay;
	private Delay lifeSpan = new Delay(0);
	private Delay death = new Delay(5);
	private int diskIndex;
	private int ghostIndex;
	
	public Disk() {
		
	}
	
	public Vector coord() {
		return coord;
	}
	public void setCoord(Vector coord) {
		this.coord = coord;
	}
	public double rad() {
		return rad;
	}
	public void setRad(double rad) {
		this.rad = rad;
	}
	public Vector vec() {
		return vec;
	}
	public void setVec(Vector vec) {
		this.vec = vec;
	}
	public Delay delay() {
		return delay;
	}
	public void setDelay(Delay delay) {
		this.delay = delay;
	}
	public Delay lifeSpan() {
		return lifeSpan;
	}
	public void setLifeSpan(Delay lifeSpan) {
		this.lifeSpan = lifeSpan;
	}
	public Delay death() {
		return death;
	}
	public void setDeath(Delay death) {
		this.death = death;
	}
	public int diskIndex() {
		return diskIndex;
	}
	public void setDiskIndex(int diskIndex) {
		this.diskIndex = diskIndex;
	}
	public int ghostIndex() {
		return ghostIndex;
	}
	public void setGhostIndex(int ghostIndex) {
		this.ghostIndex = ghostIndex;
	}
}
