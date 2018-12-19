package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import MapObjects.*;

public class Level {
    
	private Clip clip;
	private int frames = 0;
    private Laser[] lasers = new Laser[3000];
    private int laserSize = 0;
    private int currentLaser = 0;
    private Disk[] disks = new Disk[3000];
    private int diskSize = 0;
    private int currentDisk = 0;

    public Level(int level) {
    	File soundFile = new File("resources/levels/level" + level + "/autism.wav");
    	this.levelReader("resources/levels/level" + level + "/autism.txt");
        try {
        	this.setClip(AudioSystem.getClip());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			this.clip().open(audioIn);
			this.clip().start();
		}
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
    }
    
    public void levelReader(String filePath) {
    	
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			boolean redirect = false;
			
			String line;
			while((line = bufferedReader.readLine()) != null) {
				String[] object = line.split("/");
				System.out.println(object[0]);
				System.out.println(object[1]);
				System.out.println(object[2]);
				System.out.println(object[3]);
				System.out.println(object[4]);
				System.out.println(object[5]);
				System.out.println(object[6]);
				System.out.println(object[7]);
				System.out.println(object[8]);
				System.out.println(object[9]);
				System.out.println(object[10]);
				if(object[0].equals("laser")) {
					Laser laser = new Laser(Integer.parseInt(object[1]), new Vector(Double.parseDouble(object[2]), Double.parseDouble(object[3])), 
							new Vector(Double.parseDouble(object[4]), Double.parseDouble(object[5])), Double.parseDouble(object[6]), Double.parseDouble(object[7]),
							new Delay(Integer.parseInt(object[8])), new Delay(Integer.parseInt(object[9])), Integer.parseInt(object[10]));
					this.lasers()[this.laserSize()] = laser;
					this.setLaserSize(this.laserSize() + 1);
				}
				else {
					if(redirect == false) {
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
			if(this.frames() == this.lasers()[this.currentLaser()].spawn()) {
				this.createLaser(this.lasers()[this.currentLaser()]);
				this.setCurrentLaser(this.currentLaser() + 1);
			}
			else {
				this.setFrames(this.frames() + 1);
			}
    	}
    }
    
    public void createLaser(Laser laser) {
    	laser.setLaserIndex(MapItems.laserSize());
    	MapItems.lasers()[laser.laserIndex()] = laser;
    	MapItems.setLaserSize(MapItems.laserSize() + 1);
    	if(laser.delay().dur() != 0) {
    		laser.setGhostIndex(MapItems.ghostLaserSize());
			Laser warning = new Laser(new Vector(laser.coord().x(), laser.coord().y()), laser.width(), laser.height(), laser.dir());
			MapItems.ghostLasers()[laser.ghostIndex()] = warning;
			MapItems.setGhostLaserSize(MapItems.ghostLaserSize() + 1);
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
}
