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
		double kSizeLeftLowerArm = leftHand.distance(leftShoulder);
		double kSizeLeftUpperArm = leftElbow.distance(leftShoulder);
		
		double kSizeRightLowerArm = rightHand.distance(rightShoulder);
		double kSizeRightUpperArm = leftElbow.distance(rightShoulder);
		
		double iSizeLowerArm = Skeleton.lowerArmLength;
		double iSizeUpperArm = Skeleton.upperArmLength;
		double iSizeShoulderDist = Skeleton.shoulderDist;
		
		// TODO ICH (TIMO) MACH HIER BALD WEITER.
		
		

	}
}
