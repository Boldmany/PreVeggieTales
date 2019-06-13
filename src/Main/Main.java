package Main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application{
	
	private static Canvas canvas = new Canvas(1000, 650);
	private static GraphicsContext gc = canvas.getGraphicsContext2D();
	private static Group group = new Group();
	private static boolean playing = false;
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * making the stage / screen
	 */
	public void start(Stage window) throws Exception {
		try {

			group.getChildren().add(canvas);
		
			Scene scene = new Scene(group);
			
			scene.setOnKeyPressed(new OnKeyPressed()); // input once clicked
			scene.setOnKeyReleased(new OnKeyReleased()); // input once relaesed
			
			window.setScene(scene);
			window.setTitle("PreVeggieTales");
			window.show();
			
			
			Timeline gameLoop = new Timeline();
		    gameLoop.setCycleCount(Timeline.INDEFINITE);
		    double interval = (double) (Math.round(((double) 1 / 60) * 1000000)) / 1000; // run 60 times a second
		    KeyFrame keyframe = new KeyFrame(Duration.millis(interval), new GameLoop());
		    gameLoop.getKeyFrames().add(keyframe);
		    gameLoop.play(); // run the game
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static GraphicsContext gc() {
		return gc;
	}

	public static void setGc(GraphicsContext gc) {
		Main.gc = gc;
	}
	
	public static Canvas canvas() {
		return canvas;
	}

	public static void setCanvas(Canvas canvas) {
		Main.canvas = canvas;
	}

	public static Group group() {
		return group;
	}

	public static void setGroup(Group group) {
		Main.group = group;
	}

	public static boolean playing() {
		return playing;
	}

	public static void setPlaying(boolean playing) {
		Main.playing = playing;
	}
}
