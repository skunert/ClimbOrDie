import java.awt.*;
import processing.core.*;


public class Renderer {

	private PApplet parent;
	private ImageData imageData;
	private int waterPos = 0;
	private boolean waterDirection = false;
	private int crocoPos = 0;
	private boolean crocoDirection = false;

	public Renderer(PApplet parent) {
		this.parent = parent;
		this.imageData = new ImageData(parent);
	}

	public void drawScene(Scene scene, Rectangle area) {
		drawBackground(scene, area);
		drawHandles(scene, area);
		drawSkeleton(scene, area);		
		// done in drawWater drawCrocodile(scene, area);
		drawWater(scene, area);
	}

	private void drawHandles(Scene scene, Rectangle area) {
		this.parent.imageMode(this.parent.CENTER);
		for(Handle h : scene.getHandles()) {
			this.parent.pushMatrix();
			this.parent.translate(h.x, h.y);
			this.parent.scale(0.2f);
			// TODO: Handles move with background?
			if (h.y > imageData.getHandlesMinHeight()) {
				this.parent.image(imageData.getHandleByIndex(h.type), 0, 0);
				if (h.isHighlight()) {
					this.parent.image(imageData.getHandleHighlight(), 0, 0);
				}
			}
			this.parent.popMatrix();
		}
		this.parent.imageMode(this.parent.CORNER);
	}

	private void drawSkeleton(Scene scene, Rectangle area) {
		Skeleton s = scene.getSkeleton();
		if(s == null){
			return;
		}

		this.parent.ellipseMode(this.parent.CENTER);
		Point c = s.getCenter();

		// TODO draw real body part images here

		//only for debugging:
		//parent.tint(255, 126);

		// Arms
		if (s.isLeftHandGrab()) {	
			drawExtremity(imageData.getArmLowerClosedLeft(), s.getLeftHand(), s.getLeftElbow(), imageData.getArmLowerLeftPoint1(), imageData.getArmLowerLeftPoint2(), c);
		} else {
			drawExtremity(imageData.getArmLowerOpenLeft(), s.getLeftHand(), s.getLeftElbow(), imageData.getArmLowerLeftPoint1(), imageData.getArmLowerLeftPoint2(), c);
		}
		if (s.isRightHandGrab()) {
			drawExtremity(imageData.getArmLowerClosedRight(), s.getRightHand(), s.getRightElbow(), imageData.getArmLowerRightPoint1(), imageData.getArmLowerRightPoint2(), c);
		} else {
			drawExtremity(imageData.getArmLowerOpenRight(), s.getRightHand(), s.getRightElbow(), imageData.getArmLowerRightPoint1(), imageData.getArmLowerRightPoint2(), c);
		}
		drawExtremity(imageData.getArmUpperLeft(), s.getLeftElbow(), s.getLeftShoulder(), imageData.getArmUpperLeftPoint1(), imageData.getArmUpperLeftPoint2(), c);
		drawExtremity(imageData.getArmUpperRight(), s.getRightElbow(), s.getRightShoulder(), imageData.getArmUpperRightPoint1(), imageData.getArmUpperRightPoint2(), c);

		// Head
		if (s.isLeftHandGrab() && s.isRightHandGrab()) {
			drawHead(imageData.getHeadBack(), s.getHead(), imageData.getHeadBackPoint1(), 0.2f, c);
		} else if (s.isLeftHandGrab() && ! s.isRightHandGrab()) {
			drawHead(imageData.getHeadRight(), s.getHead(), imageData.getHeadRightPoint1(), 0.2f, c);
		} else if (! s.isLeftHandGrab() && s.isRightHandGrab()) {
			drawHead(imageData.getHeadLeft(), s.getHead(), imageData.getHeadLeftPoint1(), 0.2f, c);
		} else if (! s.isLeftHandGrab() && ! s.isRightHandGrab()) {
			// scream face image? ;-)
			drawHead(imageData.getHeadBack(), s.getHead(), imageData.getHeadBackPoint1(), 0.2f, c);
		}

		// Legs
		float torsoScalefactorX = PApplet.dist(s.getLeftShoulder().x, s.getLeftShoulder().y, s.getRightShoulder().x, s.getRightShoulder().y) / PApplet.dist(imageData.getTorsoPoint2().x, imageData.getTorsoPoint2().y, imageData.getTorsoPoint3().x, imageData.getTorsoPoint3().y);
		drawExtremity(imageData.getLegLowerLeft(), s.getLeftKnee(), s.getLeftFoot(), imageData.getLegLowerLeftPoint1(), imageData.getLegLowerLeftPoint2(), c);
		drawExtremity(imageData.getLegLowerRight(), s.getRightKnee(), s.getRightFoot(), imageData.getLegLowerRightPoint1(), imageData.getLegLowerRightPoint2(), c);
		drawExtremity(imageData.getLegUpperLeft(), new Point(Math.round((horizontalPointTranslate(imageData.getTorsoPointHip(), imageData.getTorsoPoint4()) - s.getHip().x) * torsoScalefactorX), s.getHip().y), s.getLeftKnee(), imageData.getLegUpperLeftPoint1(), imageData.getLegUpperLeftPoint2(), c);
		drawExtremity(imageData.getLegUpperRight(), new Point(Math.round((horizontalPointTranslate(imageData.getTorsoPointHip(), imageData.getTorsoPoint5()) - s.getHip().x) * torsoScalefactorX), s.getHip().y), s.getRightKnee(), imageData.getLegUpperRightPoint1(), imageData.getLegUpperRightPoint2(), c);
		
		// Torso
		drawTorso(imageData.getTorso(), s.getHead(), s.getLeftShoulder(), s.getRightShoulder(), s.getHip(), imageData.getTorsoPoint1(), imageData.getTorsoPoint2(), imageData.getTorsoPoint3(), imageData.getTorsoPointHip(), c);


	}

	private void drawBackground(Scene scene, Rectangle area) {
		Skeleton s = scene.getSkeleton();
		if(s == null){
			return;
		}
		PImage backgroundImage = imageData.getBackground();
		parent.pushMatrix();
		// TODO MOVE IT!
		parent.scale((float)parent.width / (float)backgroundImage.width, (float)parent.height / (float)backgroundImage.height);
		//parent.translate(,);
		parent.image(backgroundImage, 0, 0);
		parent.popMatrix();
	}

	private void drawWater(Scene scene, Rectangle area) {
		Skeleton s = scene.getSkeleton();
		if(s == null){
			return;
		}
		PImage waterRightImage = imageData.getWaterRight();
		PImage waterLeftImage = imageData.getWaterLeft();
		parent.pushMatrix();
		if (parent.frameCount % 120 == 0) {
			waterDirection = false;
		} else if (parent.frameCount % 60 == 0) {
			waterDirection = true;
		}
		if (parent.frameCount % 2 == 0) {
			if (waterDirection) {
				waterPos++;
			} else {
				waterPos--;
			}
		}
		parent.translate(waterPos - 20, parent.height - 150);
		parent.scale(0.6f);
		parent.image(waterLeftImage, 0, 0);
		drawCrocodile(scene, area);
		parent.translate(-waterPos - 20, 100);
		parent.image(waterRightImage, 0, 0);
		parent.popMatrix();
	}

	private void drawCrocodile(Scene scene, Rectangle area) {
		Skeleton s = scene.getSkeleton();
		if(s == null){
			return;
		}
		parent.pushMatrix();
		if (parent.frameCount % 36 == 0) {
			crocoDirection = false;
		} else if (parent.frameCount % 18 == 0) {
			crocoDirection = true;
		}
		if (parent.frameCount % 2 == 0) {
			if (crocoDirection) {
				crocoPos++;
			} else {
				crocoPos--;
			}
		}
		parent.resetMatrix();
		// TODO static position???
		parent.translate(parent.width - 300 + crocoPos,  parent.height - 220 + crocoPos);
		parent.scale(0.3f);
		parent.image(imageData.getCrocodile(), 0, 0);
		parent.popMatrix();
	}

	private Point translate(Point p, Point delta) {
		return new Point(p.x + delta.x, p.y + delta.y);
	}

	private Point negativePoint(Point p) {
		return new Point(-p.x, -p.y);
	}

	private Point scalePoint(Point p, float scaleFactor) {
		return new Point(parent.round(p.x * scaleFactor), parent.round(p.y * scaleFactor));
	}
	
	private int horizontalPointTranslate(Point point1, Point point2) {
		return point2.x - point1.x;
	}

	// Helper functions to drawing primitives
	// ======================================
	// (may be deleted after implementing drawSkeleton() with the real images)

	private void circle(Point center, int size) {
		circle(center, new Point(0, 0), size);
	}

	private void circle(Point center, Point translate, int size) {
		center = translate(center, translate);
		this.parent.ellipse(center.x, center.y, size, size);
	}

	private void line(Point a, Point b, Point translate) {
		a = translate(a, translate);
		b = translate(b, translate);
		this.parent.line(a.x, a.y, b.x, b.y);
	}

	private void drawExtremity(PImage pImage, Point skelPoint1, Point skelPoint2, Point imagePoint1, Point imagePoint2, Point translate) {
		float scaleFactor = PApplet.dist(skelPoint1.x, skelPoint1.y, skelPoint2.x, skelPoint2.y) / PApplet.dist(imagePoint1.x, imagePoint1.y, imagePoint2.x, imagePoint2.y);
		Point negativeImagePoint1 = negativePoint(imagePoint1);
		Point startPoint = translate(skelPoint1, translate);
		float angle = (float) Math.PI - (float) Math.atan2(skelPoint1.x - skelPoint2.x, skelPoint1.y - skelPoint2.y);
		parent.pushMatrix();
		parent.translate(startPoint.x, startPoint.y);
		parent.scale(scaleFactor);
		parent.rotate(angle);
		parent.image(pImage, negativeImagePoint1.x, negativeImagePoint1.y);
		parent.popMatrix();
	}

	private float drawTorso(PImage torsoImage, Point skelPointHead, Point skelPointLeftShoulder, Point skelPointRightShoulder, Point skelPointHip, Point imagePoint1, Point imagePoint2, Point imagePoint3, Point imagePointHip, Point translate) {
		float scaleFactorX = PApplet.dist(skelPointLeftShoulder.x, skelPointLeftShoulder.y, skelPointRightShoulder.x, skelPointRightShoulder.y) / PApplet.dist(imagePoint2.x, imagePoint2.y, imagePoint3.x, imagePoint3.y);
		float scaleFactorY = PApplet.dist(skelPointHead.x, skelPointHead.y, skelPointHip.x, skelPointHip.y) / PApplet.dist(imagePoint1.x, imagePoint1.y, imagePointHip.x, imagePointHip.y);
		Point negativeImagePoint1 = negativePoint(imagePoint1);
		Point startPoint = translate(skelPointHead, translate);
		parent.pushMatrix();
		parent.translate(startPoint.x, startPoint.y);
		parent.scale(scaleFactorX, scaleFactorY);
		parent.image(torsoImage, negativeImagePoint1.x, negativeImagePoint1.y);
		parent.popMatrix();
		return scaleFactorX;
	}

	private void drawHead(PImage headImage, Point skelPointHead, Point imagePoint1, float scaleFactor, Point translate) {
		Point negativeImagePoint1 = negativePoint(imagePoint1);
		Point startPoint = translate(skelPointHead, translate);
		parent.pushMatrix();
		parent.translate(startPoint.x, startPoint.y);
		parent.scale(scaleFactor);
		parent.image(headImage, negativeImagePoint1.x, negativeImagePoint1.y);
		parent.popMatrix();
	}
}
