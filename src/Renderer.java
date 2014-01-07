import java.awt.Rectangle;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;


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
		
	}
	
	private void drawSkeleton(Scene scene, Rectangle area) {
		
	}
	
	private void drawBackground(Scene scene, Rectangle area) {
		
	}
	
	private void drawWater(Scene scene, Rectangle area) {
		
	}
	
	private void drawCrocodile(Scene scene, Rectangle area) {
		
	}
}
