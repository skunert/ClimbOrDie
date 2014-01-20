import java.awt.Point;

public class SkeletonController {
	private Scene scene;
	private Skeleton skeleton;
	private Handle leftHandHandle;
	private Handle rightHandHandle;

	public SkeletonController(Scene scene) {
		this.scene = scene;
		this.skeleton = scene.getSkeleton();
	}

	public void updateSkeleton(Point leftElbow, Point rightElbow,
			Point leftShoulder, Point rightShoulder, Point leftHand,
			Point rightHand, boolean leftHandGrab, boolean rightHandGrab) {

		double kSizeLeftLowerArm = leftHand.distance(leftElbow);
		double kSizeLeftUpperArm = leftElbow.distance(leftShoulder);
		double kSizeRightLowerArm = rightHand.distance(rightElbow);
		double kSizeRightUpperArm = rightElbow.distance(rightShoulder);

		double iSizeLowerArm = Skeleton.lowerArmLength;
		double iSizeUpperArm = Skeleton.upperArmLength;

		double armLeftLowerArmScale = iSizeLowerArm / kSizeLeftLowerArm;
		double armRightLowerArmScale = iSizeLowerArm / kSizeRightLowerArm;
		double armLeftUpperArmScale = iSizeUpperArm / kSizeLeftUpperArm;
		double armRightUpperArmScale = iSizeUpperArm / kSizeRightUpperArm;

		skeleton.setLeftElbow(new Point(
				(int) (skeleton.getLeftShoulder().x - ((leftShoulder.x - leftElbow.x) * armLeftUpperArmScale)),
				(int) (skeleton.getLeftShoulder().y - ((leftShoulder.y - leftElbow.y) * armLeftUpperArmScale))));

		skeleton.setLeftHand(new Point(
				(int) (skeleton.getLeftElbow().x - ((leftElbow.x - leftHand.x) * armLeftLowerArmScale)),
				(int) (skeleton.getLeftElbow().y - ((leftElbow.y - leftHand.y) * armLeftLowerArmScale))));

		skeleton.setRightElbow(new Point(
				(int) (skeleton.getRightShoulder().x + ((rightElbow.x - rightShoulder.x) * armRightUpperArmScale)),
				(int) (skeleton.getRightShoulder().y + ((rightElbow.y - rightShoulder.y) * armRightUpperArmScale))));

		skeleton.setRightHand(new Point(
				(int) (skeleton.getRightElbow().x + ((rightHand.x - rightElbow.x) * armRightLowerArmScale)),
				(int) (skeleton.getRightElbow().y + ((rightHand.y - rightElbow.y) * armRightLowerArmScale))));
		
//		if(true)
//			return;

		
		// ///////////////////////////////////////////////////////////////
		// TODO Stefan
		skeleton.setLeftHandGrab(leftHandGrab);
		skeleton.setRightHandGrab(rightHandGrab);
		
		if (leftHandHandle != null) {
			leftHandHandle.setHighlight(false);
		}
		if (rightHandHandle != null) {
			rightHandHandle.setHighlight(false);
		}

		leftHandHandle = getHandleUnderHand(translate(skeleton.getLeftHand(), skeleton.getCenter()));
		rightHandHandle = getHandleUnderHand(translate(skeleton.getRightHand(), skeleton.getCenter()));
		if (leftHandGrab) {
			if (leftHandHandle != null) {
				skeleton.setLeftHand(translate(leftHandHandle,
						negativePoint(skeleton.getCenter())));
				skeleton.setFalling(false);
				leftHandHandle.setHighlight(true);
				if (ClimbOrDie.DEBUG)
					System.out.println("left hand attached to handle");
			} 
		}
		if (rightHandGrab) {
			if (rightHandHandle != null) {
				skeleton.setRightHand(translate(rightHandHandle,
						negativePoint(skeleton.getCenter())));
				skeleton.setFalling(false);
				rightHandHandle.setHighlight(true);
				if (ClimbOrDie.DEBUG)
					System.out.println("right hand attached to handle");
			} 
		}
		if (!rightHandGrab && !leftHandGrab && scene.isGameStarted() && !scene.isGameWon()) {
			skeleton.setFalling(true);
		}
		if (skeleton.isFalling()) {
			Point oldCenter = skeleton.getCenter();
			if (oldCenter.y > scene.getLooseGameHeight()) {
				skeleton.setFalling(false);
				scene.setGameLost(true);
			} else {
				// TODO some physics here??
				skeleton.setCenter(new Point(oldCenter.x, oldCenter.y + 2));
			}
		}
		if (skeleton.getCenter().y < scene.getWinGameHeight()) {
			skeleton.setFalling(false);
			scene.setGameWon(true);
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
