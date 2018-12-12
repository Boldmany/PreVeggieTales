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
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage window) throws Exception {
		try {
		
		Group group = new Group();
		group.getChildren().add(canvas);
		
		Scene scene = new Scene(group);
		scene.setOnKeyPressed(new OnKeyPressed());
		scene.setOnKeyReleased(new OnKeyReleased());
		
		window.setScene(scene);
		window.setTitle("preVeggieTales");
		window.show();
		
		Timeline gameLoop = new Timeline();
	    gameLoop.setCycleCount(Timeline.INDEFINITE);
	    double interval = (double) (Math.round(((double) 1 / 50) * 1000000)) / 1000;
	    KeyFrame keyframe = new KeyFrame(Duration.millis(interval), new GameLoop());
	    gameLoop.getKeyFrames().add(keyframe);
	    gameLoop.play();
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
}
