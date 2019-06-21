package Main;

import Character.Player;
import MapObjects.Disk;
import MapObjects.Laser;

public class Map {
	
	private static Player[] players = new Player[2]; // array of players on screen
	private static int playerSize = 0;
	
	private static Laser[] lasers = new Laser[3000]; // array of lasers on screen
	private static Laser[] ghostLasers = new Laser[3000]; // array of laser warnings on screen
	private static int laserSize = 0;
	private static int ghostLaserSize = 0;
	
	private static Disk[] disks = new Disk[3000]; // array of disks on screen
	private static Disk[] ghostDisks = new Disk[3000]; // array of disk warnings on screen
	private static int diskSize = 0;
	private static int ghostDiskSize = 0;
	
	private static Disk[] safeDisks = new Disk[3000]; // array of safe disks on screen
	private static int safeDiskSize = 0;
	
	private static Level[] levels = {new Level(1), new Level(2), new Level(3)}; // the amount of levels
	private static int playLevel = 0; // the current level
			 
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

	public static Player[] players() {
		return players;
	}

	public static void setPlayers(Player[] players) {
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
