import java.awt.Point;
import java.awt.Rectangle;

import processing.core.PApplet;

public class ClimbOrDie extends PApplet {

	public final static boolean DEBUG = true;
	
	private static final long serialVersionUID = 1L;
	private SkeletonController sController;
	//private KinectController kController;
	private Renderer renderer;
	private Scene scene;

	public static void main(String args[]) {
		PApplet.main(new String[] { /* "--present", */"ClimbOrDie" });
	}

	public void setup() {
		size(720, 700);
		background(0);
		scene = new SceneImpl(this);
		Skeleton skeleton = scene.initSkeleton();
		sController = new SkeletonController(scene);
		//kController = new KinectController(this, true); // TODO set to false
														// later
		renderer = new Renderer(this);
	}

	public void draw() {
		//kController.update();

//		sController.updateSkeleton(kController.getLeftElbowPosition(),
//				kController.getRightElbowPosition(),
//				kController.getLeftShoulderPosition(),
//				kController.getRightShoulderPosition(), kController.getLeftHandPosition(),
//				kController.getRightHandPosition(),
//				kController.isGrabLeft(), kController.isGrabRight());
		sController.updateSkeleton(null, null, null, null, new Point(-60, -50), new Point(60, -50), grab, grab2); 		// TODO: TO TEST, REMOVE ME!!
		renderer.drawScene(scene, new Rectangle(30, 30, 101, 250));

//		if (kController.isDebug())
//			kController.debug();
	}

	/////////////////////////////////////////////
	// TEST STUFF

	boolean grab = true;
	boolean grab2 = true;

	public void mousePressed() 
	{
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