import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class KinectController {

	private SimpleOpenNI context;
	private PApplet pApplet;

	private PVector rightHandPosition = new PVector();
	private PVector leftHandPosition = new PVector();

	private PVector rightElbowPosition = new PVector();
	private PVector leftElbowPosition = new PVector();
	private PVector rightShoulderPosition = new PVector();
	private PVector leftShoulderPosition = new PVector();

	private boolean grabLeftAverage[] = new boolean[2];
	private int grabLeftAverageIndex = 0;
	private boolean grabLeft;

	private boolean grabRightAverage[] = new boolean[2];
	private int grabRightAverageIndex = 0;
	private boolean grabRight;

	private boolean debug = false;

	private int userId = 0;

	private static int MIN_HAND_Z_DEPTH_DIFF = 300;
	private static int REFERENCE_TORSO = 1100;

	private PImage bufferedImage;

	public KinectController(ClimbOrDie pApplet, boolean debug) {
		this.context = new SimpleOpenNI(pApplet,
				SimpleOpenNI.RUN_MODE_MULTI_THREADED);
		this.pApplet = pApplet;
		this.debug = debug;
		if (context.isInit() == false) {
			PApplet.println("Can't init SimpleOpenNI, maybe the camera is not connected!");
			pApplet.exit();
			return;
		} else {
			PApplet.println("SimpleOpenNI initialized");
		}

		// enable mirroring
		context.setMirror(true);

		// enable depthMap generation
		context.enableDepth();

		// enable skeleton generation for all joints
		context.enableUser();

		context.enableHand();
		context.startGesture(SimpleOpenNI.GESTURE_HAND_RAISE);
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean update() {
		// update the cam
		context.update();

		// determine user
		int[] userList = context.getUsers();
		if (userList.length == 0)
			return false;

		// refresh user id
		if (userList[0] != userId) {
			userId = userList[0];
			context.startTrackingSkeleton(userId);
		}

		// skeleton not recognized yet
		if (!context.isTrackingSkeleton(userId))
			return false;
		
		synchronized (this) {
			this.bufferedImage = context.userImage();
		}

		// determine body
		PVector realPos = new PVector();
		context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_TORSO,
				realPos);
		PVector torso = new PVector();
		context.convertRealWorldToProjective(realPos, torso);

		// determine its hands
		// right hand
		context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND,
				realPos);
		context.convertRealWorldToProjective(realPos, rightHandPosition);
		grabRight = updateGrab(rightHandPosition, true, torso);

		// left hand
		context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,
				realPos);
		context.convertRealWorldToProjective(realPos, leftHandPosition);
		grabLeft = updateGrab(leftHandPosition, false, torso);

		// the rest

		// right elbow
		context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW,
				realPos);
		context.convertRealWorldToProjective(realPos, rightElbowPosition);

		// left elbow
		context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_ELBOW,
				realPos);
		context.convertRealWorldToProjective(realPos, leftElbowPosition);

		// right shoulder
		context.getJointPositionSkeleton(userId,
				SimpleOpenNI.SKEL_RIGHT_SHOULDER, realPos);
		context.convertRealWorldToProjective(realPos, rightShoulderPosition);

		// left elbow
		context.getJointPositionSkeleton(userId,
				SimpleOpenNI.SKEL_LEFT_SHOULDER, realPos);
		context.convertRealWorldToProjective(realPos, leftShoulderPosition);

		return true;

	}

	private boolean updateGrab(PVector handPos, boolean rightHand, PVector torso) {
		PVector[] depthMap = context.depthMapRealWorld();

		int indexHand = Math.round(handPos.x) + Math.round(handPos.y)
				* context.depthWidth();

		int indexTorso = Math.round(torso.x) + Math.round(torso.y)
				* context.depthWidth();

		// get average
		int avg = 0;
		if (rightHand) {
			for (int i = 0; i < grabRightAverage.length; ++i) {
				if (grabRightAverage[i]) {
					avg++;
				}
			}
			avg = (int) Math.round(((double) avg) / grabRightAverage.length);
		} else {
			for (int i = 0; i < grabLeftAverage.length; ++i) {
				if (grabLeftAverage[i]) {
					avg++;
				}
			}
			avg = (int) Math.round(((double) avg) / grabLeftAverage.length);
		}

		// use last state
		if (indexHand < depthMap.length && indexHand > 0
				&& indexTorso < depthMap.length && indexTorso > 0) {

			double handZ = depthMap[indexHand].z;
			double torsoZ = depthMap[indexTorso].z;

			double distScale = REFERENCE_TORSO / torsoZ;

			if ((torsoZ - handZ) > (MIN_HAND_Z_DEPTH_DIFF * distScale)) {
				if (rightHand) {
					grabRightAverage[grabRightAverageIndex] = true;
				} else {
					grabLeftAverage[grabLeftAverageIndex] = true;
				}
			} else {
				if (rightHand) {
					grabRightAverage[grabRightAverageIndex] = false;
				} else {
					grabLeftAverage[grabLeftAverageIndex] = false;
				}
			}

			if (rightHand) {
				grabRightAverageIndex++;
				if (grabRightAverageIndex == grabRightAverage.length)
					grabRightAverageIndex = 0;
			} else {
				grabLeftAverageIndex++;
				if (grabLeftAverageIndex == grabLeftAverage.length)
					grabLeftAverageIndex = 0;
			}
		}

		if (debug) {
			if (avg == 1)
				PApplet.println(((rightHand) ? "RIGHT" : "LEFT")
						+ " HAND GRAB!");
			else
				PApplet.println(((rightHand) ? "RIGHT" : "LEFT")
						+ " HAND RELEASE!");
		}

		return avg == 1;
	}

	public Point getRightHandPosition() {
		return new Point((int) rightHandPosition.x, (int) rightHandPosition.y);
	}

	public Point getLeftHandPosition() {
		return new Point((int) leftHandPosition.x, (int) leftHandPosition.y);
	}

	public Point getLeftElbowPosition() {
		return new Point((int) leftElbowPosition.x, (int) leftElbowPosition.y);
	}

	public Point getLeftShoulderPosition() {
		return new Point((int) leftShoulderPosition.x,
				(int) leftShoulderPosition.y);
	}

	public Point getRightElbowPosition() {
		return new Point((int) rightElbowPosition.x, (int) rightElbowPosition.y);
	}

	public Point getRightShoulderPosition() {
		return new Point((int) rightShoulderPosition.x,
				(int) rightShoulderPosition.y);
	}

	public boolean isGrabRight() {
		return grabRight;
	}

	public boolean isGrabLeft() {
		return grabLeft;
	}

	public void debug() {
		pApplet.tint(255, 100);
		pApplet.image(context.userImage(), 0, 0);
		pApplet.noTint();
		debugDrawSkeleton(userId);
		// debugDrawHand(leftHandPosition);
		// debugDrawHand(rightHandPosition);
	}

	// private void debugDrawHand(PVector hand) {
	// if (hand.x != 0 && hand.y != 0) {
	// pApplet.fill(pApplet.color(255, 204, 0));
	// pApplet.ellipse(hand.x - 5, hand.y - 5, 10, 10);
	// pApplet.fill(pApplet.color(255, 255, 255));
	// }
	// }

	private void debugDrawSkeleton(int userId) {

		context.drawLimb(userId, SimpleOpenNI.SKEL_HEAD, SimpleOpenNI.SKEL_NECK);

		context.drawLimb(userId, SimpleOpenNI.SKEL_NECK,
				SimpleOpenNI.SKEL_LEFT_SHOULDER);
		context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER,
				SimpleOpenNI.SKEL_LEFT_ELBOW);
		context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_ELBOW,
				SimpleOpenNI.SKEL_LEFT_HAND);

		context.drawLimb(userId, SimpleOpenNI.SKEL_NECK,
				SimpleOpenNI.SKEL_RIGHT_SHOULDER);
		context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER,
				SimpleOpenNI.SKEL_RIGHT_ELBOW);
		context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW,
				SimpleOpenNI.SKEL_RIGHT_HAND);

		context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER,
				SimpleOpenNI.SKEL_TORSO);
		context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER,
				SimpleOpenNI.SKEL_TORSO);

		// context.drawLimb(userId, SimpleOpenNI.SKEL_TORSO,
		// SimpleOpenNI.SKEL_LEFT_HIP);
		// context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_HIP,
		// SimpleOpenNI.SKEL_LEFT_KNEE);
		// context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_KNEE,
		// SimpleOpenNI.SKEL_LEFT_FOOT);
		//
		// context.drawLimb(userId, SimpleOpenNI.SKEL_TORSO,
		// SimpleOpenNI.SKEL_RIGHT_HIP);
		// context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP,
		// SimpleOpenNI.SKEL_RIGHT_KNEE);
		// context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_KNEE,
		// SimpleOpenNI.SKEL_RIGHT_FOOT);
	}

	public PImage getBufferedImage() {
		synchronized (this) {
			return bufferedImage;
		}
	}

	public boolean updateCalibration(Scene scene) {
		// update the cam
		context.update();

		synchronized (this) {
			this.bufferedImage = context.userImage();
		}

		int[] userList = context.getUsers();

		// no user found yet
		if (userList.length != 0) {
			// refresh user id
			if (userList[0] != userId) {
				userId = userList[0];
				context.startTrackingSkeleton(userId);
			}
			scene.setPersonFound(true);
		}

		if (context.isTrackingSkeleton(userId)) {
			scene.setSkelFound(true);
			return true;
		}
		return false;
	}

}
