import java.awt.*;

import processing.core.PApplet;

public class ClimbOrDieKinectRecorder extends PApplet {

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
	
	public int RECORDING_FILENUM = 1;

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "ClimbOrDieKinectRecorder" });
	}

	public void setup() {
        WIDTH = displayWidth;
        HEIGHT = displayHeight;
        ImageData.HANDLE_MIN_HEIGHT = (int)(HEIGHT*0.11);
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
					if (ClimbOrDieKinectRecorder.this.gameStarted)
						ClimbOrDieKinectRecorder.this.dataReady = kController.update();
					else
						ClimbOrDieKinectRecorder.this.gameStarted = kController
								.updateCalibration(scene);
				}
			}
		}).start();
	}

	public void draw() {

		if (gameStarted) {
			if (dataReady) {
				sController.updateSkeleton(kController.getLeftElbowPosition(),
						kController.getRightElbowPosition(),
						kController.getLeftShoulderPosition(),
						kController.getRightShoulderPosition(),
						kController.getLeftHandPosition(),
						kController.getRightHandPosition(),
						kController.isGrabLeft(), kController.isGrabRight());
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