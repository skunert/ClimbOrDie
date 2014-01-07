import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;


public abstract class Scene {

	protected List<Handle> handles;
	protected Skeleton skeleton;
	protected float waterHeight;
	protected float wallHeight;
	protected PApplet parent;
	
	public Scene(PApplet parent) {
		this.parent = parent;
	}
	
	public List<Handle> getHandles() {
		return handles;
	}

	public Skeleton getSkeleton() {
		return skeleton;
	}

	public float getWaterHeight() {
		return waterHeight;
	}

	public float getWallHeight() {
		return wallHeight;
	}
	/**
	 * initializes the skeleton position
	 */
	public abstract Skeleton initSkeleton();
	/**
	 * generates the Handle Positions
	 * @return The list of generated Handles
	 */
	public abstract List<Handle> generateHandles(); 
	public abstract PImage getHandleImage();
	public abstract PImage getBackgroundImage();	
	public abstract PImage getSkeletonBodyImageBack();
	public abstract PImage getSkeletonBodyImageFront();
	public abstract PImage getSkeletonLeftUpperArmImage();
	public abstract PImage getSkeletonRightUpperArmImage();
	public abstract PImage getSkeletonLeftLowerArmImage();
	public abstract PImage getSkeletonRightLowerArmImage();
	public abstract PImage getSkeletonLeftHandImage();
	public abstract PImage getSkeletonRightHandImage();
	public abstract PImage getSkeletonRightUpperLeg();
	public abstract PImage getSkeletonLeftUpperLeg();
	public abstract PImage getSkeletonRightLowerLeg();
	public abstract PImage getSkeletonLeftLowerLeg();
	public abstract PImage getWaterImage();
	public abstract PImage getCrocodileUpperJaw();
	public abstract PImage getCrocodileLowerJaw();
	public abstract PImage getCrocodileBody();
}
