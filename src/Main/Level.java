package Main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Level {
    private Clip clip;
    
    public Level(String song) {
    	File soundFile = new File(song);
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
    
    public void creation() {
    	
    }
    
    public void createLaser() {
    	
    }
    
    public void createDisk() {
    	
    }
    
	public Clip clip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}
}
