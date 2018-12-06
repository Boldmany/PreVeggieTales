package Main;

import Character.Pineapple;
import MapObjects.Disk;
import MapObjects.Laser;

public class MapItems {
	
	private static Pineapple[] players = new Pineapple[2];
	private static int playerSize = 0;
	
	private static Laser[] lasers = new Laser[3000];
	private static Laser[] ghostLasers = new Laser[3000] ;
	private static int laserSize = 0;
	private static int ghostLaserSize = 0;
	
	private static Disk[] disks = new Disk[3000];
	private static Disk[] ghostDisks = new Disk[3000] ;
	private static int diskSize = 0;
	private static int ghostDiskSize = 0;
	
	
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

	public static Disk[] disks() {
		return disks;
	}

	public static void setDisks(Disk[] disks) {
		MapItems.disks = disks;
	}

	public static Disk[] ghostDisks() {
		return ghostDisks;
	}

	public static void setGhostDisks(Disk[] ghostDisks) {
		MapItems.ghostDisks = ghostDisks;
	}

	public static int diskSize() {
		return diskSize;
	}

	public static void setDiskSize(int diskSize) {
		MapItems.diskSize = diskSize;
	}

	public static int ghostDiskSize() {
		return ghostDiskSize;
	}

	public static void setGhostDiskSize(int ghostDiskSize) {
		MapItems.ghostDiskSize = ghostDiskSize;
	}

	public static Pineapple[] players() {
		return players;
	}

	public static void setPlayers(Pineapple[] players) {
		MapItems.players = players;
	}

	public static int playerSize() {
		return playerSize;
	}

	public static void setPlayerSize(int playerSize) {
		MapItems.playerSize = playerSize;
	}
}
