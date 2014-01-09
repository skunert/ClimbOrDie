import java.awt.*;
import java.util.List;

//import processing.core.PApplet;
//import processing.core.PImage;
import processing.core.*;

import javax.lang.model.element.VariableElement;


public class Renderer {

	private PApplet parent;
	
	public Renderer(PApplet parent) {
		this.parent = parent;
	}
	
	public void drawScene(Scene scene, Rectangle area) {
		drawBackground(scene, area);
		drawHandles(scene, area);
		drawSkeleton(scene, area);		
		drawCrocodile(scene, area);
		drawWater(scene, area);
	}
	
	private void drawHandles(Scene scene, Rectangle area) {
        this.parent.ellipseMode(this.parent.CENTER);
		for(Handle h : scene.getHandles()) {
            // TODO draw handle images here
            circle(h, 10);
		}
	}
	
	private void drawSkeleton(Scene scene, Rectangle area) {
        Skeleton s = scene.getSkeleton();
        if(s == null){
            return;
        }

        this.parent.ellipseMode(this.parent.CENTER);
        Point c = s.getCenter();

        // TODO draw real body part images here
        int headSize = 30;
        int handSize = 20;
        int grabHandSize = 10;
        circle(s.getHead(), c, headSize);
        line(s.getHead(), s.getHip(), c);
        line(s.getLeftShoulder(), s.getRightShoulder(), c);
        line(s.getLeftShoulder(), s.getLeftElbow(), c);
        line(s.getRightShoulder(), s.getRightElbow(), c);
        line(s.getLeftElbow(), s.getLeftHand(), c);
        line(s.getRightElbow(), s.getRightHand(), c);
        line(s.getHip(), s.getLeftKnee(), c);
        line(s.getHip(), s.getRightKnee(), c);
        line(s.getLeftKnee(), s.getLeftFoot(), c);
        line(s.getRightKnee(), s.getRightFoot(), c);
        circle(s.getLeftHand(), c, s.isLeftHandGrab() ? grabHandSize : handSize);
        circle(s.getRightHand(), c, s.isRightHandGrab() ? grabHandSize : handSize);
	}
	
	private void drawBackground(Scene scene, Rectangle area) {
		this.parent.background(255);
	}
	
	private void drawWater(Scene scene, Rectangle area) {
		
	}
	
	private void drawCrocodile(Scene scene, Rectangle area) {
		
	}

    private Point translate(Point p, Point delta) {
        return new Point(p.x + delta.x, p.y + delta.y);
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
}
