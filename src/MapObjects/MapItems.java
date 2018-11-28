package MapObjects;

import java.util.ArrayList;
import java.util.List;

public class MapItems {
	private static List<Laser> lasers = new ArrayList<Laser>();
	private static List<Laser> ghostLasers = new ArrayList<Laser>();
	private static int ghostLaserSize = 0;
		
			
	public static List<Laser> lasers() {
		return lasers;
	}

	public static void setLasers(List<Laser> lasers) {
		MapItems.lasers = lasers;
	}
	
	public static List<Laser> ghostLasers() {
		return ghostLasers;
	}

	public static void setGhostLasers(List<Laser> ghostLasers) {
		MapItems.ghostLasers = ghostLasers;
	}

	public static int ghostLaserSize() {
		return ghostLaserSize;
	}

	public static void setGhostLaserSize(int ghostLaserSize) {
		MapItems.ghostLaserSize = ghostLaserSize;
	}
}
