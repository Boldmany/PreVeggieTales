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
	private int frames = 0;
    private Laser[] lasers = new Laser[3000];
    private int laserSize = 0;
    private int currentLaser = 0;
    private Disk[] disks = new Disk[3000];
    private int diskSize = 0;
    private int currentDisk = 0;
    private Disk[] redirect = new Disk[3000];
    private int redirectSize = 0;
    private int currentRedirect = 0;

    public Level(int level) {
    	File soundFile = new File("resources/levels/level" + level + "/levelSong.wav");
    	this.levelReader("resources/levels/level" + level + "/levelEditor.txt");
        try {
        	this.setClip(AudioSystem.getClip());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			this.clip().open(audioIn);
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
					Laser laser = new Laser(Integer.parseInt(object[1]), new Vector(Double.parseDouble(object[2]), Double.parseDouble(object[3])), 
							new Vector(Double.parseDouble(object[4]), Double.parseDouble(object[5])), Double.parseDouble(object[6]), Double.parseDouble(object[7]),
							new Delay(Integer.parseInt(object[8])), new Delay(Integer.parseInt(object[9])), Integer.parseInt(object[10]));
					this.lasers()[this.laserSize()] = laser;
					this.setLaserSize(this.laserSize() + 1);
				}
				else {
					Disk disk = new Disk(Integer.parseInt(object[1]), new Vector(Double.parseDouble(object[2]), Double.parseDouble(object[3])),
							new Vector(Double.parseDouble(object[4]), Double.parseDouble(object[5])),
							new Vector(Double.parseDouble(object[6]), Double.parseDouble(object[7])),
							Double.parseDouble(object[8]), Double.parseDouble(object[9]),
							Boolean.parseBoolean(object[10]), Double.parseDouble(object[11]), Double.parseDouble(object[12]),
							new Circle(Double.parseDouble(object[13]), Double.parseDouble(object[14]), Double.parseDouble(object[15])),
							new Delay(Integer.parseInt(object[16])), new Delay(Integer.parseInt(object[17])),
							Boolean.parseBoolean(object[18]), Boolean.parseBoolean(object[19]));
					if(disk.circularMotion()) {
						disk.coord().setX(disk.circularPath().getCenterX() + (disk.circularPath().getRadius() * Math.cos(Math.toRadians(disk.degree()))));
						disk.coord().setY(disk.circularPath().getCenterY() + (disk.circularPath().getRadius() * Math.sin(Math.toRadians(disk.degree()))));
					}
					this.disks()[this.diskSize()] = disk;
					this.setDiskSize(this.diskSize() + 1);
					if(disk.redirect()) {
						boolean loop = true;
						while (loop && (line = bufferedReader.readLine()) != null) {
							object = line.split("/");
							Disk redirected = new Disk(new Vector(Double.parseDouble(object[0]), Double.parseDouble(object[1])), 
									new Vector(Double.parseDouble(object[2]), Double.parseDouble(object[3])),  Double.parseDouble(object[4]),
									new Delay(Integer.parseInt(object[5])), 
									Boolean.parseBoolean(object[6]));
							this.redirect()[this.redirectSize()] = redirected;
							this.setRedirectSize(this.redirectSize() + 1);
							loop = Boolean.parseBoolean(object[5]);
						}
					}
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
    	this.setFrames(this.frames() + 1);
    }
    
    public void createLaser(Laser laser) {
    	laser.setLaserIndex(Map.laserSize());
    	Map.lasers()[laser.laserIndex()] = laser;
    	Map.setLaserSize(Map.laserSize() + 1);
    	if(laser.delay().dur() != 0) {
    		laser.setGhostIndex(Map.ghostLaserSize());
			Laser warning = new Laser(new Vector(laser.coord().x(), laser.coord().y()), laser.width(), laser.height(), laser.dir());
			Map.ghostLasers()[laser.ghostIndex()] = warning;
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

	public int currentRedirect() {
		return currentRedirect;
	}

	public void setCurrentRedirect(int currentRedirect) {
		this.currentRedirect = currentRedirect;
	}
}
