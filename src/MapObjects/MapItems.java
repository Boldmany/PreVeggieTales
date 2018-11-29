package MapObjects;

public class MapItems {
	private static Laser[] lasers = new Laser[3000];
	private static Laser[] ghostLasers = new Laser[3000] ;
	private static int laserSize = 0;
	private static int ghostLaserSize = 0;

	
	public static int ghostLaserSize() {
		return ghostLaserSize;
	}

	public static void setGhostLaserSize(int ghostLaserSize) {
		MapItems.ghostLaserSize = ghostLaserSize;
	}

	public static int laserSize() {
		return laserSize;
	}

	public static void setLaserSize(int laserSize) {
		MapItems.laserSize = laserSize;
	}

	public static Laser[] lasers() {
		return lasers;
	}

	public static void setLasers(Laser[] lasers) {
		MapItems.lasers = lasers;
	}

	public static Laser[] ghostLasers() {
		return ghostLasers;
	}

	public static void setGhostLasers(Laser[] ghostLasers) {
		MapItems.ghostLasers = ghostLasers;
	}
}
