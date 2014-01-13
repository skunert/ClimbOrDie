import java.awt.Point;

public class SkeletonController {
	private Skeleton skeleton;

	public SkeletonController(Skeleton skeleton) {
		this.skeleton = skeleton;
	}

	public void updateSkeleton(Point leftElbow, Point rightElbow,
			Point leftShoulder, Point rightShoulder, Point leftHand,
			Point rightHand, boolean leftHandGrab, boolean rightHandGrab) {
		
		skeleton.setLeftElbow(leftElbow);
		skeleton.setRightElbow(rightElbow);
		skeleton.setLeftShoulder(leftShoulder);
		skeleton.setRightShoulder(rightShoulder);
		skeleton.setLeftHand(leftHand);
		skeleton.setRightHand(rightHand);
		skeleton.setLeftHandGrab(leftHandGrab);
		skeleton.setRightHandGrab(rightHandGrab);		
		
		// Scale kinect sizes to image sizes
		//double kSizeLeftUpperArm = leftElbow.distance(leftShoulder);
		//double iSizeLeftUpperArm = -1 /* Ich brauche die Größe der Extremitäten zum skalieren! */;
		
		// TODO ICH (TIMO) MACH HIER BALD WEITER.
		
		

	}
}
