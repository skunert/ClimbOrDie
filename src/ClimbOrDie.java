import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet;

import com.thoughtworks.xstream.XStream;

public class ClimbOrDie extends PApplet {

	public final static boolean DEBUG = false;
	public static int WIDTH = 720;
	public static int HEIGHT = 700;

	private static final long serialVersionUID = 1L;
	private SkeletonController sController;
	private KinectController kController;
	private Renderer renderer;
	private Scene scene;

	private boolean gameStarted;
	private boolean dataReady;
	
	private ArrayList<Point[]> winAnimationList;
	private ArrayList<Point[]> loseAnimationList;
	private int animationCounter = 0;

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "ClimbOrDie" });
	}

	@SuppressWarnings("unchecked")
	public void setup() {
		XStream xStream = new XStream();
		winAnimationList = (ArrayList<Point[]>) xStream.fromXML(new File("resources/animations/win.xml"));
		loseAnimationList = (ArrayList<Point[]>) xStream.fromXML(new File("resources/animations/loose.xml"));
        WIDTH = displayWidth;
        HEIGHT = displayHeight;
        ImageData.HANDLE_MIN_HEIGHT = (int)(HEIGHT*0.11*ImageData.BACKGROUND_ASPECT_FACTOR);
        size(WIDTH, HEIGHT);
		background(0);
		scene = new SceneImpl(this);
		scene.initSkeleton();
		kController = new KinectController(this, DEBUG);
		sController = new SkeletonController(scene);
		renderer = new Renderer(this);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// do nothing
					}
					if (ClimbOrDie.this.gameStarted)
						ClimbOrDie.this.dataReady = kController.update();
					else
						ClimbOrDie.this.gameStarted = kController
								.updateCalibration(scene);
				}
			}
		}).start();
	}

	public void draw() {
		if (gameStarted) {
			if (dataReady && (! scene.gameLost && ! scene.gameWon)) {
				sController.updateSkeleton(kController.getLeftElbowPosition(),
						kController.getRightElbowPosition(),
						kController.getLeftShoulderPosition(),
						kController.getRightShoulderPosition(),
						kController.getLeftHandPosition(),
						kController.getRightHandPosition(),
						kController.isGrabLeft(), kController.isGrabRight());
			} else if (scene.gameLost && ! loseAnimationList.isEmpty()) {
				if (millis() % 50 == 0)
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
			} else if (scene.gameWon && ! winAnimationList.isEmpty()) {
				if (millis() % 50 == 0)
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

		renderer.drawScene(scene, new Rectangle(30, 30, 101, 250));

		synchronized (kController) {
			if (!gameStarted) {
				imageMode(PApplet.CENTER);
				pushMatrix();
				image(kController.getBufferedImage(), getWidth() / 2,
						getHeight() / 3,
						kController.getBufferedImage().width / 2,
						kController.getBufferedImage().height / 2);
				popMatrix();
				imageMode(PApplet.CORNER);
			}
			else {
				imageMode(PApplet.CENTER);
				pushMatrix();
				image(kController.getBufferedImage(), WIDTH - 64 - 10,
						48 +10,
						128, 96);
				popMatrix();
				imageMode(PApplet.CORNER);
			}
		}

		if (kController.isDebug())
			kController.debug();
	}

	// ///////////////////////////////////////////
	// TEST STUFF

	boolean grab = true;
	boolean grab2 = true;

	public void mousePressed() {
		System.out.println(mouseY);
		if (mouseButton == LEFT) {
			grab = true;
		} else if (mouseButton == RIGHT) {
			grab2 = true;
		}
	}

	public void mouseReleased() {
		if (mouseButton == LEFT) {
			grab = false;
		} else if (mouseButton == RIGHT) {
			grab2 = false;
		}
	}
}