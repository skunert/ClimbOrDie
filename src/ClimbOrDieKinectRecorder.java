import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import processing.core.PApplet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

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
	public ArrayList<Point[]> recList = new ArrayList<Point[]>();

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
					if (ClimbOrDieKinectRecorder.this.gameStarted) {
						ClimbOrDieKinectRecorder.this.dataReady = kController.update();
						synchronized (recList) {
							Point[] points = { kController.getLeftElbowPosition(),
									kController.getRightElbowPosition(),
									kController.getLeftShoulderPosition(),
									kController.getRightShoulderPosition(),
									kController.getLeftHandPosition(),
									kController.getRightHandPosition() };
							recList.add( points );	
						}
					} else
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
		
		textSize(32);
		fill(0, 102, 153);
		text("writing to file " + RECORDING_FILENUM, 20, 20);

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

	///////////////////////////////////////////////////
	
	public void mousePressed() {
		XStream xStream = new XStream();
		synchronized (recList) {
			try {
				writeToFile("recording" + RECORDING_FILENUM + ".xml", xStream.toXML(recList));
				recList.clear();
				RECORDING_FILENUM++;
			} catch (XStreamException | IOException e) {
			}
		}
	}
	
	public static void writeToFile(String filename, String content) throws IOException {
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(filename, false));
		writer.write(content);
		writer.flush();
		writer.close();
	}
}