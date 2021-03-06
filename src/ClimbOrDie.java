import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import processing.core.PApplet;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

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

	private boolean soundPlayed = false;

	private ArrayList<Point[]> winAnimationList;
	private ArrayList<Point[]> loseAnimationList;
	private int animationCounter = 0;

	public static final String WIN_SOUND = "resources/sounds/Attribution 3.0/Ta Da-SoundBible.com-1884170640.wav";
	public static final String LOST_SOUND = "resources/sounds/Sampling Plus 1.0/Psycho Scream-SoundBible.com-1441943673.wav";
	public static final String HANDLE_SOUND = "resources/sounds/Attribution 3.0/Blop-Mark_DiAngelo-79054334.wav";

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "ClimbOrDie" });
	}

	@SuppressWarnings("unchecked")
	public void setup() {
		XStream xStream = new XStream();
		winAnimationList = (ArrayList<Point[]>) xStream.fromXML(new File(
				"resources/animations/win.xml"));
		loseAnimationList = (ArrayList<Point[]>) xStream.fromXML(new File(
				"resources/animations/loose.xml"));
		WIDTH = displayWidth;
		HEIGHT = displayHeight;
		ImageData.HANDLE_MIN_HEIGHT = (int) (HEIGHT * 0.11 * ImageData.BACKGROUND_ASPECT_FACTOR);
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
			if (dataReady && (!scene.gameLost && !scene.gameWon)) {
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

		if (scene.gameLost && !loseAnimationList.isEmpty()
				&& frameCount % 2 == 0) {
			animationCounter++;
			animationCounter = animationCounter % loseAnimationList.size();
			Point[] points = loseAnimationList.get(animationCounter);
			sController.updateSkeleton(points[0], points[1], points[2],
					points[3], points[4], points[5], false, false);
		} else if (scene.gameWon && !winAnimationList.isEmpty()
				&& frameCount % 2 == 0) {
			animationCounter++;
			animationCounter = animationCounter % winAnimationList.size();
			Point[] points = winAnimationList.get(animationCounter);
			sController.updateSkeleton(points[0], points[1], points[2],
					points[3], points[4], points[5], false, false);
		}

		if (scene.gameWon && !soundPlayed) {
			soundPlayed = true;
			ClimbOrDie.playSound(ClimbOrDie.WIN_SOUND);
		} else if (scene.gameLost && !soundPlayed) {
			soundPlayed = true;
			ClimbOrDie.playSound(ClimbOrDie.LOST_SOUND);
		}

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
			} else {
				imageMode(PApplet.CENTER);
				pushMatrix();
				image(kController.getBufferedImage(), WIDTH - 64 - 10, 48 + 10,
						128, 96);
				popMatrix();
				imageMode(PApplet.CORNER);
			}
		}

		if (kController.isDebug())
			kController.debug();
	}

	// UTIILS

	public static synchronized void playSound(final String path) {
				
		new Thread(new Runnable() {

			@Override
			public void run() {
				InputStream in = null;
				try {
					in = new FileInputStream(path);
				} catch (FileNotFoundException e) {
					System.out.println("Media file not found");
					e.printStackTrace();
				}

				AudioStream as = null;
				try {
					as = new AudioStream(in);
				} catch (IOException e) {
					e.printStackTrace();
				}
				AudioPlayer.player.start(as);
				try {
					if (path.equals(ClimbOrDie.LOST_SOUND))
						Thread.sleep(6000);
					else if (path.equals(ClimbOrDie.WIN_SOUND))
						Thread.sleep(5000);
					else if (path.equals(ClimbOrDie.HANDLE_SOUND))
						Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
		
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