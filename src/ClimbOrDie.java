import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import processing.core.*;

public class ClimbOrDie extends PApplet {
	
	private SkeletonController sController;	
	private KinectController kController;
	private Renderer renderer;
	private Scene scene;
	
	public static void main(String args[]) {
		PApplet.main(new String[] { /*"--present",*/ "ClimbOrDie" });
	}
	
	public void setup() {
		size(720, 1000);
		background(0);
		scene = new SceneImpl(this);
		Skeleton skeleton = scene.initSkeleton();
		sController = new SkeletonController(skeleton);
		kController = new KinectController(this, true); // TODO set to false later
		renderer = new Renderer(this);
	}

	public void draw() {
		sController.updateSkeleton(new Point(30, 50), new Point(30, 40), true, false, this);
		renderer.drawScene(scene, new Rectangle(30,30,101, 250));
		
		if (kController.isDebug())
			kController.debug();

		kController.update();
	}
}