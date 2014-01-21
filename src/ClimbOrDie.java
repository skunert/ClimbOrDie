import java.awt.Rectangle;

import processing.core.PApplet;

public class ClimbOrDie extends PApplet {

	public final static boolean DEBUG = true;
	public final static int WIDTH = 720;
	public final static int HEIGHT = 700;

	private static final long serialVersionUID = 1L;
	private SkeletonController sController;
	private KinectController kController;
	private Renderer renderer;
	private Scene scene;

	public static void main(String args[]) {
		PApplet.main(new String[] { /*"--present", */ "ClimbOrDie" });
	}

	public void setup() {
		size(WIDTH, HEIGHT);
		background(0);
		scene = new SceneImpl(this);
		scene.initSkeleton();
		sController = new SkeletonController(scene);
		// TODO set to false later
		kController = new KinectController(this, ClimbOrDie.DEBUG);
		renderer = new Renderer(this);
	}

	public void draw() {
		boolean dataReady = kController.update();

		if(dataReady)
		sController.updateSkeleton(kController.getLeftElbowPosition(),
				kController.getRightElbowPosition(),
				kController.getLeftShoulderPosition(),
				kController.getRightShoulderPosition(),
				kController.getLeftHandPosition(),
				kController.getRightHandPosition(), kController.isGrabLeft(),
				kController.isGrabRight());
		// TODO: change by kController??
		scene.setPersonFound(true);
		scene.setSkelFound(true);
		//
		// sController.updateSkeleton(null, null, null, null, new Point(-60,
		// -50),
		// new Point(60, -50), grab, grab2); // TODO: TO TEST, REMOVE ME!!
		renderer.drawScene(scene, new Rectangle(30, 30, 101, 250));

		if (kController.isDebug())
			kController.debug();
	}

	// ///////////////////////////////////////////
	// TEST STUFF

	boolean grab = true;
	boolean grab2 = true;

	public void mousePressed() {
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