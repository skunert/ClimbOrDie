import java.awt.Point;


public class Skeleton {
	private Point leftHandPos;
	private Point rightHandPos;
	private Point leftEllbow;
	private Point rightEllbow;
	private Point leftShoulder;
	private Point rightShoulder;
	private Point Hip;
	private Point Head;
	private Point leftKnee;
	private Point rightKnee;
	private Point leftFoot;
	private Point rightFoot;
	
	public Point getLeftEllbow() {
		return leftEllbow;
	}
	public void setLeftEllbow(Point leftEllbow) {
		this.leftEllbow = leftEllbow;
	}
	public Point getRightEllbow() {
		return rightEllbow;
	}
	public void setRightEllbow(Point rightEllbow) {
		this.rightEllbow = rightEllbow;
	}
	public Point getLeftShoulder() {
		return leftShoulder;
	}
	public void setLeftShoulder(Point leftShoulder) {
		this.leftShoulder = leftShoulder;
	}
	public Point getRightShoulder() {
		return rightShoulder;
	}
	public void setRightShoulder(Point rightShoulder) {
		this.rightShoulder = rightShoulder;
	}
	public Point getHip() {
		return Hip;
	}
	public void setHip(Point hip) {
		Hip = hip;
	}
	public Point getHead() {
		return Head;
	}
	public void setHead(Point head) {
		Head = head;
	}
	public Point getLeftKnee() {
		return leftKnee;
	}
	public void setLeftKnee(Point leftKnee) {
		this.leftKnee = leftKnee;
	}
	public Point getRightKnee() {
		return rightKnee;
	}
	public void setRightKnee(Point rightKnee) {
		this.rightKnee = rightKnee;
	}
	public Point getLeftFoot() {
		return leftFoot;
	}
	public void setLeftFoot(Point leftFoot) {
		this.leftFoot = leftFoot;
	}
	public Point getRightFoot() {
		return rightFoot;
	}
	public void setRightFoot(Point rightFoot) {
		this.rightFoot = rightFoot;
	}
	private boolean leftHandGrab;
	private boolean rightHandGrab;
	
	public Point getLeftHandPos() {
		return leftHandPos;
	}
	public void setLeftHandPos(Point leftHandPos) {
		this.leftHandPos = leftHandPos;
	}
	public Point getRightHandPos() {
		return rightHandPos;
	}
	public void setRightHandPos(Point rightHandPos) {
		this.rightHandPos = rightHandPos;
	}
	public boolean isLeftHandGrab() {
		return leftHandGrab;
	}
	public void setLeftHandGrab(boolean leftHandGrab) {
		this.leftHandGrab = leftHandGrab;
	}
	public boolean isRightHandGrab() {
		return rightHandGrab;
	}
	public void setRightHandGrab(boolean rightHandGrab) {
		this.rightHandGrab = rightHandGrab;
	}
	
	
}
