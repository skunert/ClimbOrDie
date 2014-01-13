import java.awt.Point;

public class SkeletonController {
	private Scene scene;
	private Skeleton skeleton;

	public SkeletonController(Scene scene) {
		this.scene = scene;
		this.skeleton = scene.getSkeleton();
	}

	public void updateSkeleton(Point leftElbow, Point rightElbow,
			Point leftShoulder, Point rightShoulder, Point leftHand,
			Point rightHand, boolean leftHandGrab, boolean rightHandGrab) {		
		
		// Scale kinect sizes to image sizes
//		double kSizeLeftLowerArm = leftHand.distance(leftShoulder);
//		double kSizeLeftUpperArm = leftElbow.distance(leftShoulder);
//		
//		double kSizeRightLowerArm = rightHand.distance(rightShoulder);
//		double kSizeRightUpperArm = leftElbow.distance(rightShoulder);
		
		double iSizeLowerArm = Skeleton.lowerArmLength;
		double iSizeUpperArm = Skeleton.upperArmLength;
		double iSizeShoulderDist = Skeleton.shoulderDist;
		
		// TODO ICH (TIMO) MACH HIER BALD WEITER.
		

		/////////////////////////////////////////////////////////////////
		// TODO Stefan
		skeleton.setLeftHandGrab(leftHandGrab);
		skeleton.setRightHandGrab(rightHandGrab);
		
		if (leftHandGrab) {
			Handle leftHandHandle;
			if ((leftHandHandle = getHandleUnderHand(translate(leftHand, skeleton.getCenter()))) != null) {
				skeleton.setLeftHand(translate(leftHandHandle, negativePoint(skeleton.getCenter())));
				skeleton.setFalling(false);
				if (ClimbOrDie.DEBUG) System.out.println("left hand attached to handle");
			} else {
				skeleton.setLeftHand(leftHand);
			}
		}
		if (rightHandGrab) {
			Handle rightHandHandle;
			if ((rightHandHandle = getHandleUnderHand(translate(rightHand, skeleton.getCenter()))) != null) {
				skeleton.setRightHand(translate(rightHandHandle, negativePoint(skeleton.getCenter())));
				skeleton.setFalling(false);
				if (ClimbOrDie.DEBUG) System.out.println("right hand attached to handle");
			} else {
				skeleton.setRightHand(rightHand);
			}
		}
		if (! rightHandGrab && ! leftHandGrab) {
			skeleton.setFalling(true);
		}
		if (skeleton.isFalling()) {
			Point oldCenter = skeleton.getCenter();
			if (oldCenter.y > scene.getLooseGameHeight()) {
				skeleton.setFalling(false);
				// TODO: Set Game Lost to scene!
			} else {
				// TODO some physics here??
				skeleton.setCenter(new Point(oldCenter.x, oldCenter.y + 2));
			}
		}
	}

	public Handle getHandleUnderHand(Point hand) {
		if (hand != null) {
			for (Handle handle : scene.getHandles()) {
				if (hand.distance(handle) <= Skeleton.minHandleGrabDist) {
					return handle;
				}
			}
		}
		return null;
	}
	
	private Point translate(Point p, Point delta) {
		return new Point(p.x + delta.x, p.y + delta.y);
	}
	
	private Point negativePoint(Point p) {
		return new Point(-p.x, -p.y);
	}
}
