import java.awt.Point;

import processing.core.PApplet;


public class SkeletonController {
	private Skeleton skeleton;
	
	public SkeletonController(Skeleton skeleton) {
		this.skeleton = skeleton;
	}
	
	// TODO remove papplet!
	public void updateSkeleton(Point leftHandPos, Point rightHandPos, boolean leftHandGrab, boolean rightHandGrab, PApplet parent) {
		//TODO implement updateSkeleton
		if (parent.frameCount % 60 == 0) {
			skeleton.setLeftHandGrab(! skeleton.isLeftHandGrab());
		}
		if (parent.frameCount % 120 == 0) {
			skeleton.setRightHandGrab(! skeleton.isRightHandGrab());
		}
	}
}
