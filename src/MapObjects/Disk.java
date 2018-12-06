package MapObjects;

import Main.*;

public class Disk {
	
	private Vector coord;
	private double currentRadius = 0;
	private double finalRadius;
	private double radiusChange = 0;
	private Vector vec = new Vector(0,0);
	private Delay delay;
	private Delay lifeSpan = new Delay(0);
	private int function;
	private int diskIndex;
	private int ghostIndex;
	
	public Disk(Vector coord, double finalRadius, Delay delay, int function, boolean real) {
		this.setCoord(coord);
		this.setFinalRadius(finalRadius);
		this.setDelay(delay);
		this.setFunction(function);
		
		if(real) {
			this.setDiskIndex(MapItems.diskSize());
			MapItems.disks()[this.diskIndex()] = this;
			MapItems.setDiskSize(MapItems.diskSize() + 1);
			if(this.delay().dur() != 0) {
				this.setGhostIndex(MapItems.ghostDiskSize());
				Disk warning = new Disk(new Vector(coord.x(),coord.y()), finalRadius, new Delay(0), 0, false);
				MapItems.ghostDisks()[this.ghostIndex()] = warning;
				MapItems.setGhostDiskSize(MapItems.ghostDiskSize() + 1);
			}
		}
	}
	
	public void delayCheck() {
		
	}
	
	public void expand() {
		this.setRadiusChange(5);
		if(this.currentRadius() < this.finalRadius()) {
			this.setCurrentRadius(this.currentRadius() + this.radiusChange());
			if(this.currentRadius()  > this.finalRadius()) {
				this.setCurrentRadius(this.finalRadius());
				
			}
		}
	}
	
	public void shrink() {
		this.setRadiusChange(-10);
		if(this.currentRadius() > 0) {
			this.setCurrentRadius(this.currentRadius() + this.radiusChange());
		}
	}
	
	
	public Vector coord() {
		return coord;
	}
	
	public void setCoord(Vector coord) {
		this.coord = coord;
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

	public double currentRadius() {
		return currentRadius;
	}

	public void setCurrentRadius(double currentRadius) {
		this.currentRadius = currentRadius;
	}

	public double finalRadius() {
		return finalRadius;
	}

	public void setFinalRadius(double finalRadius) {
		this.finalRadius = finalRadius;
	}

	public int function() {
		return function;
	}

	public void setFunction(int function) {
		this.function = function;
	}
	public double radiusChange() {
		return radiusChange;
	}

	public void setRadiusChange(double radiusChange) {
		this.radiusChange = radiusChange;
	}
}
