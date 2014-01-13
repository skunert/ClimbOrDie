import java.awt.Point;

import processing.core.PApplet;
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

	private boolean grabRight;
	private boolean grabLeft;

	private boolean debug = false;

	private int userId = 0;

	private static int MAX_HAND_DEPTH_DOWN_DIFF = 16;
	private static int MAX_HAND_DEPTH_UP_DIFF = 8;
	private static int ERROR_THRESHOLD = 10;
	private static int GRAB_THRESHOLD = 60;
	private static int RELEASE_THRESHOLD = 100;

	private static int REFERENCE_DIST = 1070;

	private boolean grabLeftAverage[] = new boolean[1];
	private int grabLeftAverageIndex = 0;
	private float lastLeftHandSize;

	private boolean grabRightAverage[] = new boolean[1];
	private int grabRightAverageIndex = 0;
	private float lastRightHandSize;

	public KinectController(PApplet pApplet, boolean debug) {
		this.context = new SimpleOpenNI(pApplet);
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
			PApplet.println("USER FOUND. WAITING FOR SKELETON...");
		}

		// skeleton not recognized yet
		if (!context.isTrackingSkeleton(userId))
			return false;

		// determine its hands
		// right hand
		PVector realPos = new PVector();
		context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND,
				realPos);
		context.convertRealWorldToProjective(realPos, rightHandPosition);
		grabRight = updateGrab(rightHandPosition, true);

		// left hand
		context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,
				realPos);
		context.convertRealWorldToProjective(realPos, leftHandPosition);
		grabLeft = updateGrab(leftHandPosition, false);

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

	private boolean updateGrab(PVector handPos, boolean rightHand) {
		PVector[] depthMap = context.depthMapRealWorld();

		int index = Math.round(handPos.x) + Math.round(handPos.y)
				* context.depthWidth();

		// use last state
		if (index > depthMap.length || index < 0) {
			if (rightHand) {
				return grabRightAverage[grabRightAverage.length - 1];
			} else {
				return grabLeftAverage[grabLeftAverage.length - 1];
			}
		}

		double handZ = depthMap[index].z;

		double distScale = REFERENCE_DIST / handZ;

		// go upwards
		int off = 0;
		for (; off < context.depthHeight(); off++) {
			index = Math.round(handPos.x) + (Math.round(handPos.y) - off)
					* context.depthWidth();

			if (index > 0
					&& ((int) depthMap[index].z) - ((int) handZ) > MAX_HAND_DEPTH_UP_DIFF) {
				break;
			}
		}
		float handTopY = handPos.y - off;

		// go downwards
		for (off = 0; off < context.depthHeight(); off++) {
			index = Math.round(handPos.x) + (Math.round(handPos.y) + off)
					* context.depthWidth();

			if (index > 0
					&& index < depthMap.length
					&& ((int) depthMap[index].z) - ((int) handZ) > MAX_HAND_DEPTH_DOWN_DIFF) {
				break;
			}
		}
		float handBottomY = handPos.y + off;

		// show the detected hand
		if (debug) {
			pApplet.stroke(pApplet.color(0, 255, 0));
			pApplet.strokeWeight(4);
			pApplet.line(handPos.x, handTopY, handPos.x, handBottomY);
			pApplet.strokeWeight(1);
			pApplet.stroke(pApplet.color(0, 0, 0));
		}

		float handSize = handBottomY - handTopY;

		int avg = 0;

		// detect grab
		if (rightHand) {
			// get average
			for (int i = 0; i < grabRightAverage.length; ++i) {
				if (grabRightAverage[i]) {
					avg++;
				}
			}
			avg = (int) Math.round(((double) avg) / grabRightAverage.length);

			if (lastRightHandSize > 0) {
				// TOP and BOTTOM are one point! (error)
				if (handSize < ERROR_THRESHOLD*distScale) {
					// do not change grabAverage
					grabRightAverageIndex--;
					if (grabRightAverageIndex < -1)
						grabRightAverageIndex = -1;
				} else if (handSize < GRAB_THRESHOLD*distScale) {
					grabRightAverage[grabRightAverageIndex] = true;
				} else if (handSize > RELEASE_THRESHOLD*distScale) {
					grabRightAverage[grabRightAverageIndex] = false;
				} else {
					grabRightAverage[grabRightAverageIndex] = (avg == 1);
				}
				grabRightAverageIndex++;
				if (grabRightAverageIndex == grabRightAverage.length)
					grabRightAverageIndex = 0;
			}
			lastRightHandSize = handSize;

		} else {
			// get average
			for (int i = 0; i < grabLeftAverage.length; ++i) {
				if (grabLeftAverage[i]) {
					avg++;
				}
			}
			avg = (int) Math.round(((double) avg) / grabLeftAverage.length);

			if (lastLeftHandSize > 0) {
				// TOP and BOTTOM are one point! (error)
				if (handSize < ERROR_THRESHOLD) {
					// do not change grabAverage
					grabLeftAverageIndex--;
					if (grabLeftAverageIndex < -1)
						grabLeftAverageIndex = -1;
				} else if (handSize < GRAB_THRESHOLD) {
					grabLeftAverage[grabLeftAverageIndex] = true;
				} else if (handSize > RELEASE_THRESHOLD) {
					grabLeftAverage[grabLeftAverageIndex] = false;
				} else {
					grabLeftAverage[grabLeftAverageIndex] = (avg == 1);
				}
				grabLeftAverageIndex++;
				if (grabLeftAverageIndex == grabLeftAverage.length)
					grabLeftAverageIndex = 0;
			}
			lastLeftHandSize = handSize;
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

}
