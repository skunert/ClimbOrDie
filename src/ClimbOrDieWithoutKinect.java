import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet;

import com.thoughtworks.xstream.XStream;

public class ClimbOrDieWithoutKinect extends PApplet {

	public final static boolean DEBUG = false;
	public final static int WIDTH = 720;
	public final static int HEIGHT = 700;

	private static final long serialVersionUID = 1L;
	private SkeletonController sController;
	private Renderer renderer;
	private Scene scene;

	private boolean gameStarted;
	private boolean dataReady;
	
	private ArrayList<Point[]> winAnimationList;
	private ArrayList<Point[]> loseAnimationList;
	private int animationCounter = 0;

	public static void main(String args[]) {
		PApplet.main(new String[] { /* "--present", */"ClimbOrDieWithoutKinect" });
	}

	public void setup() {
		XStream xStream = new XStream();
		winAnimationList = (ArrayList<Point[]>) xStream.fromXML(new File("resources/animations/win.xml"));
		loseAnimationList = (ArrayList<Point[]>) xStream.fromXML(new File("resources/animations/loose.xml"));
		size(WIDTH, HEIGHT);
		background(0);
		scene = new SceneImpl(this);
		scene.initSkeleton();
		sController = new SkeletonController(scene);
		renderer = new Renderer(this);
		scene.setGameWon(true);
	}

	public void draw() {

//		if (gameStarted) {
//			if (dataReady) {
//				sController.updateSkeleton(kController.getLeftElbowPosition(),
//						kController.getRightElbowPosition(),
//						kController.getLeftShoulderPosition(),
//						kController.getRightShoulderPosition(),
//						kController.getLeftHandPosition(),
//						kController.getRightHandPosition(),
//						kController.isGrabLeft(), kController.isGrabRight());
//			}
//		}

		renderer.drawScene(scene, new Rectangle(30, 30, 101, 250));

		if (scene.gameLost && ! loseAnimationList.isEmpty() && frameCount % 2 == 0) {
			animationCounter++;
			animationCounter = animationCounter % loseAnimationList.size();
			Point[] points = loseAnimationList.get(animationCounter);
			sController.updateSkeleton(points[0],
					points[1],
					points[2],
					points[3],
					points[4],
					points[5],
					false, false);
		} else if (scene.gameWon && ! winAnimationList.isEmpty() && frameCount % 2 == 0) {
			animationCounter++;
			animationCounter = animationCounter % winAnimationList.size();
			Point[] points = winAnimationList.get(animationCounter);
			sController.updateSkeleton(points[0],
					points[1],
					points[2],
					points[3],
					points[4],
					points[5],
					false, false);
		}
		
	}

	// ///////////////////////////////////////////
	// TEST STUFF

	boolean grab = true;
	boolean grab2 = true;

	public void mousePressed() {
		scene.getSkeleton().setCenter(new Point(mouseX, mouseY));
	}

	public void mouseReleased() {
		if (mouseButton == LEFT) {
			grab = false;
		} else if (mouseButton == RIGHT) {
			grab2 = false;
		}
	}
}