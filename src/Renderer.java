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
		drawHandle(scene);
		drawSkeleton(scene);		
		drawCrocodile(scene);
		drawWater(scene);
	}
	
	private void drawHandle(Scene scene) {
		
	}
	
	private void drawSkeleton(Scene scene) {
		
	}
	
	private void drawBackground(Scene scene, Rectangle area) {
		
	}
	
	private void drawWater(Scene scene) {
		
	}
	
	private void drawCrocodile(Scene scene) {
		
	}
}
