package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import MapObjects.*;
import javafx.scene.shape.Circle;

public class Level {

	private Clip clip;
	private int frames = 000;
	private Laser[] lasers = new Laser[3000];
	private int laserSize = 0;
	private int currentLaser = 0;
	private Disk[] disks = new Disk[3000];
	private int diskSize = 0;
	private int currentDisk = 0;
	private Disk[] redirect = new Disk[3000];
	private int redirectSize = 0;
	private Checkpoint[] checkpoints = new Checkpoint[5];
	private int currentCheckpoint = 0;
	private int checkpointSize = 0;;
	private Shake[] shakes = new Shake[3000];
	private Shake shake = new Shake(0, new Delay(0), new Vector(0, 0));
	private int currentShake = 0;
	private int shakeSize = 0;

	public Level(int level) {
		File soundFile = new File("resources/levels/level" + level + "/levelSong.wav");
		this.levelReader("resources/levels/level" + level + "/levelEditor.txt");
		try {
			this.setClip(AudioSystem.getClip());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			this.clip().open(audioIn);
			//this.clip().setFramePosition(797760);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void levelReader(String filePath) {
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null) {
				if(line.equals("")) {
					continue;
				}
				String[] object = line.split("/");
				if(object[0].equals("laser")) {
					Laser laser = new Laser(Integer.parseInt(object[1]), new Vector(0 ,0), new Vector(0, 0), 0, 0, new Delay(0), new Delay(0), 1);
					while((line = bufferedReader.readLine()) != null) {
						String[] change = line.split("/");
						if(change[0].equals("coord")) {
							laser.setCoord(new Vector(Double.parseDouble(change[1]), Double.parseDouble(change[2])));
						}
						else if(change[0].equals("vec")) {
							laser.setVec(new Vector(Double.parseDouble(change[1]), Double.parseDouble(change[2])));
						}
						else if(change[0].equals("width")) {
							laser.setWidth(Double.parseDouble(change[1]));
						}
						else if(change[0].equals("height")) {
							laser.setHeight(Double.parseDouble(change[1]));
						}
						else if(change[0].equals("delay")) {
							laser.delay().setDur(Integer.parseInt(change[1]));
						}
						else if(change[0].equals("lifeSpan")) {
							laser.lifeSpan().setDur(Integer.parseInt(change[1]));
						}
						else if(change[0].equals("dir")) {
							laser.setDir(Integer.parseInt(change[1]));
						}
						else {
							break;
						}
					}
					this.lasers()[this.laserSize()] = laser;
					this.setLaserSize(this.laserSize() + 1);
				}
				else if(object[0].equals("disk")){
					Disk disk = new Disk(Integer.parseInt(object[1]), new Vector(0, 0), new Vector(0, 0), new Vector(0, 0), 0, 0, false, 0, 0,  new Circle(0, 0, 0), new Delay(0), new Delay(0), false, false);
					while((line = bufferedReader.readLine()) != null) {
						String[] change = line.split("/");
						if(change[0].equals("coord")) {
							disk.setCoord(new Vector(Double.parseDouble(change[1]), Double.parseDouble(change[2])));
						}
						else if(change[0].equals("vec")) {
							disk.setVec(new Vector(Double.parseDouble(change[1]), Double.parseDouble(change[2])));
						}
						else if(change[0].equals("maxSpeed")) {
							disk.setMaxSpeed(new Vector(Double.parseDouble(change[1]), Double.parseDouble(change[2])));
						}
						else if(change[0].equals("finalRadius")) {
							disk.setFinalRadius(Double.parseDouble(change[1]));
							disk.setRadiusChange(Double.parseDouble(change[2]));
						}
						else if(change[0].equals("circularMotion")) {
							disk.setCircularMotion(true);
							disk.setDegree(Double.parseDouble(change[1]));
							disk.setDegreeChange(Double.parseDouble(change[2]));
							disk.setCircularPath(new Circle(Double.parseDouble(change[3]), Double.parseDouble(change[4]), Double.parseDouble(change[5])));

							disk.coord().setX(disk.circularPath().getCenterX() + (disk.circularPath().getRadius() * Math.cos(Math.toRadians(disk.degree()))));
							disk.coord().setY(disk.circularPath().getCenterY() + (disk.circularPath().getRadius() * Math.sin(Math.toRadians(disk.degree()))));
						}
						else if(change[0].equals("delay")) {
							disk.delay().setDur(Integer.parseInt(change[1]));
						}
						else if(change[0].equals("lifeSpan")) {
							disk.lifeSpan().setDur(Integer.parseInt(change[1]));
						}
						else if(change[0].equals("safe")) {
							disk.setSafe(true);
						}
						else if(change[0].equals("showSafe")) {
							Laser laser = new Laser(disk.spawn(), new Vector(0, 0), new Vector(0, -1), 1000, 0, new Delay(disk.delay().dur() - 1), new Delay(1), 1);
							laser.setShowSafe(true);
							this.lasers()[this.laserSize()] = laser;
							this.setLaserSize(this.laserSize() + 1);
						}
						else if(change[0].equals("redirect")) {
							disk.setRedirect(true);
							break;
						}
						else {
							break;
						}
					}
					this.disks()[this.diskSize()] = disk;
					this.setDiskSize(this.diskSize() + 1);
					if(disk.redirect()) {
						disk.setRedirectIndex(this.redirectSize());
						boolean loop = true;
						while (loop) {
							Disk redirected = new Disk(new Vector(0, 0), new Vector(0, 0), 0, 0, new Delay(0), false);
							while((line = bufferedReader.readLine()) != null) {
								String[] change = line.split("/");
								if(change[0].equals("vec")) {
									redirected.setVec(new Vector(Double.parseDouble(change[1]), Double.parseDouble(change[2])));
								}
								else if(change[0].equals("maxSpeed")) {
									redirected.setMaxSpeed(new Vector(Double.parseDouble(change[1]), Double.parseDouble(change[2])));
								}
								else if(change[0].equals("degreeChange")) {
									redirected.setDegreeChange(Double.parseDouble(change[1]));
								}
								else if(change[0].equals("lifeSpan")) {
									redirected.lifeSpan().setDur(Integer.parseInt(change[1]));
								}
								else if(change[0].equals("radiusChange")) {
									redirected.setRadiusChange(Double.parseDouble(change[1]));
								}
								else if(change[0].equals("redirect")) {
									redirected.setRedirect(true);
									break;
								}
								else {
									break;
								}
							}
							loop = redirected.redirect();
							if(loop) {
								redirected.setRedirectIndex(this.redirectSize() + 1);
							}
							this.redirect()[this.redirectSize()] = redirected;
							this.setRedirectSize(this.redirectSize() + 1);
						}
					}
				}
				else if(object[0].equals("checkpoint")) {
					Checkpoint checkpoint = new Checkpoint(Integer.parseInt(object[1]));
					this.checkpoints()[this.checkpointSize()] = checkpoint;
					this.setcheckpointSize(this.checkpointSize() + 1);
				}
				else if(object[0].equals("shake")) {
					Shake shake = new Shake(Integer.parseInt(object[1]), new Delay(Integer.parseInt(object[2])), new Vector(Double.parseDouble(object[3]), Double.parseDouble(object[3])));
					this.shakes()[this.shakeSize()] = shake;
					this.setShakeSize(this.shakeSize() + 1);
				}
			}
			bufferedReader.close();
			//sort
			Arrays.sort(lasers, new Comparator<Laser>() {

				@Override
				public int compare(Laser laser1, Laser laser2) {
					if(!Objects.isNull(laser1) && !Objects.isNull(laser2)) {
						return laser1.spawn() - laser2.spawn();
					}
					else {
						return 0;
					}
				}

			});
			Arrays.sort(disks, new Comparator<Disk>() {

				@Override
				public int compare(Disk disk1, Disk disk2) {
					if(!Objects.isNull(disk1) && !Objects.isNull(disk2)) {
						return disk1.spawn() - disk2.spawn();
					}
					else {
						return 0;
					}
				}
			});
			if(this.frames() != 0 && this.laserSize() != 0 && this.diskSize() != 0) {
				for(int i = 0; i <= this.frames(); i++) {
					if(i == this.lasers()[this.currentLaser()].spawn()) {
						this.setCurrentLaser(this.currentLaser() + 1);
						i--;
					}
					if(i == this.disks()[this.currentDisk()].spawn()) {
						this.setCurrentDisk(this.currentDisk() + 1);
						i--;
					}
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delayCheck() {
		if(this.currentLaser() < this.laserSize()) {
			for(int i = this.currentLaser(); i < this.laserSize(); i++) {
				if(this.frames() == this.lasers()[i].spawn()) {
					this.createLaser(this.lasers()[i]);
					this.setCurrentLaser(this.currentLaser() + 1);
				}
				else {
					break;
				}
			}
		}
		if(this.currentDisk() < this.diskSize()) {
			for(int i = this.currentDisk(); i < this.diskSize(); i++) {
				if(this.frames() == this.disks()[i].spawn()) {
					this.createDisk(this.disks()[i]);
					this.setCurrentDisk(this.currentDisk() + 1);
				}
				else {
					break;
				}
			}
		}
		if(this.checkpointSize() != 0 && this.currentCheckpoint() != this.checkpointSize()) {
			if(this.checkpoints()[this.currentCheckpoint()].frame() - 100 == this.frames()) {
				this.checkpoints()[this.currentCheckpoint()].setMusicFrame(this.clip().getFramePosition());
			}
			if(this.checkpoints()[this.currentCheckpoint()].frame() == this.frames()) {
				this.checkpoints()[this.currentCheckpoint()].setCurrentLaser(this.currentLaser());
				this.checkpoints()[this.currentCheckpoint()].setCurrentDisk(this.currentDisk());
				this.setCurrentCheckpoint(this.currentCheckpoint() + 1);
			}
		}
		if(this.shakes()[this.currentShake()].spawn() == this.frames()) {
			this.setShake(this.shakes[this.currentShake()]);
			if(this.currentShake() + 1 != this.shakeSize()) {
				this.setCurrentShake(this.currentShake() + 1);
			}
		}
		if(!this.shake().dur().done()) {
			this.shake().dur().increase();
			if(this.shake().degree().x() > 0 && this.shake().degree().y() > 0) {
				this.shake().degree().setX(-this.shake().degree().x());
			}
			else if(this.shake().degree().x() < 0 && this.shake().degree().y() > 0) {
				this.shake().degree().setY(-this.shake().degree().y());
			}
			else if(this.shake().degree().x() < 0 && this.shake().degree().y() < 0) {
				this.shake().degree().setX(-this.shake().degree().x());
			}
			else {
				this.shake().degree().setY(-this.shake().degree().y());
			}
		}
		else {
			this.shake().setDegree(new Vector(0, 0));
		}
		this.setFrames(this.frames() + 1);
	}

	public void createLaser(Laser laser) {
		laser.setLaserIndex(Map.laserSize());
		Map.lasers()[Map.laserSize()] = laser;
		Map.setLaserSize(Map.laserSize() + 1);
		if(laser.delay().dur() != 0) {
			laser.setGhostIndex(Map.ghostLaserSize());
			Laser warning = new Laser(new Vector(laser.coord().x(), laser.coord().y()), laser.width(), laser.height(), laser.dir(), laser.showSafe());
			Map.ghostLasers()[Map.ghostLaserSize()] = warning;
			Map.setGhostLaserSize(Map.ghostLaserSize() + 1);
		}
	}

	public void createDisk(Disk disk) {
		if(!disk.safe()) {
			disk.setDiskIndex(Map.diskSize());
			Map.disks()[Map.diskSize()] = disk;
			Map.setDiskSize(Map.diskSize() + 1);
			if(disk.delay().dur() != 0) {
				disk.setGhostIndex(Map.ghostDiskSize());
				Disk warning = new Disk(new Vector(disk.coord().x(), disk.coord().y()), disk.finalRadius());
				Map.ghostDisks()[Map.ghostDiskSize()] = warning;
				Map.setGhostDiskSize(Map.ghostDiskSize() + 1);
			}
		}
		else {
			disk.setCurrentRadius(disk.finalRadius());
			disk.setSafeZone(new SafeZone(disk));
			disk.setSafeDiskIndex(Map.safeDiskSize());
			Map.safeDisks()[Map.safeDiskSize()] = disk;
			Map.setSafeDiskSize(Map.safeDiskSize() + 1);
			for(int i = 0; i < Map.ghostDiskSize(); i++) {
				disk.safeZone().circle(Map.ghostDisks()[i], true);
			}
			for(int i = 0; i < Map.ghostLaserSize(); i++) {
				disk.safeZone().rectangle(Map.ghostLasers()[i], true);
			}
		}
	}


	public Clip clip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public int laserSize() {
		return laserSize;
	}

	public void setLaserSize(int laserSize) {
		this.laserSize = laserSize;
	}

	public int diskSize() {
		return diskSize;
	}

	public void setDiskSize(int diskSize) {
		this.diskSize = diskSize;
	}

	public Laser[] lasers() {
		return lasers;
	}

	public void setLasers(Laser[] lasers) {
		this.lasers = lasers;
	}

	public Disk[] disks() {
		return disks;
	}

	public void setDisks(Disk[] disks) {
		this.disks = disks;
	}

	public int frames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
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

	public Disk[] redirect() {
		return redirect;
	}

	public void setRedirect(Disk[] redirect) {
		this.redirect = redirect;
	}

	public int redirectSize() {
		return redirectSize;
	}

	public void setRedirectSize(int redirectSize) {
		this.redirectSize = redirectSize;
	}

	public Checkpoint[] checkpoints() {
		return checkpoints;
	}

	public void setCheckpoints(Checkpoint[] checkpoints) {
		this.checkpoints = checkpoints;
	}

	public int currentCheckpoint() {
		return currentCheckpoint;
	}

	public void setCurrentCheckpoint(int currentCheckpoint) {
		this.currentCheckpoint = currentCheckpoint;
	}
	
	public int checkpointSize() {
		return checkpointSize;
	}

	public void setcheckpointSize(int checkpointSize) {
		this.checkpointSize = checkpointSize;
	}

	public Shake[] shakes() {
		return shakes;
	}

	public void setShakes(Shake[] shakes) {
		this.shakes = shakes;
	}

	public int shakeSize() {
		return shakeSize;
	}
	
	public void setShakeSize(int shakeSize) {
		this.shakeSize = shakeSize;
	}
	
	public int currentShake() {
		return currentShake;
	}

	public void setCurrentShake(int currentShake) {
		this.currentShake = currentShake;
	}

	public Shake shake() {
		return shake;
	}

	public void setShake(Shake shake) {
		this.shake = shake;
	}
}
