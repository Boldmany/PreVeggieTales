package Main;

import Character.Pineapple;
import MapObjects.Disk;
import MapObjects.Laser;

public class Map {
	
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
	
	private static Disk[] safeDisks = new Disk[3000];
	private static int safeDiskSize = 0;
	
	private static Level[] levels = {new Level(1), new Level(2), new Level(3), new Level(4), new Level(5),};
	private static int playLevel = 0;
			
	public static int ghostLaserSize() {
		return ghostLaserSize;
	}

	public static void setGhostLaserSize(int ghostLaserSize) {
		Map.ghostLaserSize = ghostLaserSize;
	}

	public static int laserSize() {
		return laserSize;
	}

	public static void setLaserSize(int laserSize) {
		Map.laserSize = laserSize;
	}

	public static Laser[] lasers() {
		return lasers;
	}

	public static void setLasers(Laser[] lasers) {
		Map.lasers = lasers;
	}

	public static Laser[] ghostLasers() {
		return ghostLasers;
	}

	public static void setGhostLasers(Laser[] ghostLasers) {
		Map.ghostLasers = ghostLasers;
	}

	public static Disk[] disks() {
		return disks;
	}

	public static void setDisks(Disk[] disks) {
		Map.disks = disks;
	}

	public static Disk[] ghostDisks() {
		return ghostDisks;
	}

	public static void setGhostDisks(Disk[] ghostDisks) {
		Map.ghostDisks = ghostDisks;
	}

	public static int diskSize() {
		return diskSize;
	}

	public static void setDiskSize(int diskSize) {
		Map.diskSize = diskSize;
	}

	public static int ghostDiskSize() {
		return ghostDiskSize;
	}

	public static void setGhostDiskSize(int ghostDiskSize) {
		Map.ghostDiskSize = ghostDiskSize;
	}

	public static Pineapple[] players() {
		return players;
	}

	public static void setPlayers(Pineapple[] players) {
		Map.players = players;
	}

	public static int playerSize() {
		return playerSize;
	}

	public static void setPlayerSize(int playerSize) {
		Map.playerSize = playerSize;
	}

	public static Disk[] safeDisks() {
		return safeDisks;
	}

	public static void setSafeDisks(Disk[] safeDisks) {
		Map.safeDisks = safeDisks;
	}

	public static int safeDiskSize() {
		return safeDiskSize;
	}

	public static void setSafeDiskSize(int safeDiskSize) {
		Map.safeDiskSize = safeDiskSize;
	}

	public static Level[] levels() {
		return levels;
	}

	public static void setLevels(Level[] levels) {
		Map.levels = levels;
	}

	public static int playLevel() {
		return playLevel;
	}

	public static void setPlayLevel(int playLevel) {
		Map.playLevel = playLevel;
	}
}
