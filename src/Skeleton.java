import java.awt.Point;


public class Skeleton {
    private Point center;
	private Point leftHand;
	private Point rightHand;
	private Point leftElbow;
	private Point rightElbow;
	private Point leftShoulder;
	private Point rightShoulder;
	private Point hip;
	private Point head;
	private Point leftKnee;
	private Point rightKnee;
	private Point leftFoot;
	private Point rightFoot;
    private boolean leftHandGrab;
    private boolean rightHandGrab;
    private boolean falling;
    
	private boolean hasLeftHandle = false;
	private boolean hasRightHandle = false;

    public final static int shoulderDist = 40;
    public final static int lowerArmLength = 32;
    public final static int upperArmLength = 32;
    public static final int climpUpLimit = 10;
	public static final int fallingSpeed = 1;
	public static final int handleSnapDist = 50;
    
    public final static int minHandleGrabDist = 10;
    
    // TODO: some physics parameter?
    public final static int skelWeight = 50;

    public Point getCenter() { return center; }
    public void setCenter(Point center) { this.center = center; }
	public Point getLeftElbow() {
		return leftElbow;
	}
	public void setLeftElbow(Point leftElbow) {
		this.leftElbow = leftElbow;
	}
	public Point getRightElbow() {
		return rightElbow;
	}
	public void setRightElbow(Point rightElbow) {
		this.rightElbow = rightElbow;
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
		return hip;
	}
	public void setHip(Point hip) {
		this.hip = hip;
	}
	public Point getHead() {
		return head;
	}
	public void setHead(Point head) {
		this.head = head;
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
	public Point getLeftHand() {
		return leftHand;
	}
	public void setLeftHand(Point leftHand) {
		this.leftHand = leftHand;
	}
	public Point getRightHand() {
		return rightHand;
	}
	public void setRightHand(Point rightHand) {
		this.rightHand = rightHand;
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
	public boolean isFalling() {
		return falling;
	}
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	public boolean isHasLeftHandle() {
		return hasLeftHandle;
	}
	public void setHasLeftHandle(boolean hasLeftHandle) {
		this.hasLeftHandle = hasLeftHandle;
	}
	public boolean isHasRightHandle() {
		return hasRightHandle;
	}
	public void setHasRightHandle(boolean hasRightHandle) {
		this.hasRightHandle = hasRightHandle;
	}
}
