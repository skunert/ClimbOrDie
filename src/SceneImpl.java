import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;


public class SceneImpl extends Scene {

	public SceneImpl(PApplet parent) {
		super(parent);
		this.handles = generateHandles();
	}

	@Override
	public PImage getHandleImage() {
		return null;
	}

	@Override
	public PImage getBackgroundImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonBodyImageBack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonBodyImageFront() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonLeftUpperArmImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonRightUpperArmImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonLeftLowerArmImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonRightLowerArmImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonLeftHandImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonRightHandImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonRightUpperLeg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonLeftUpperLeg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonRightLowerLeg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getSkeletonLeftLowerLeg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getWaterImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getCrocodileUpperJaw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getCrocodileLowerJaw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PImage getCrocodileBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Skeleton initSkeleton() {
		return skeleton;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Handle> generateHandles() {
		ArrayList<Handle> handleList = new ArrayList<Handle>();
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			Handle h = new Handle();
			h.setLocation(r.nextFloat()*1280, r.nextFloat()*720); //random locations
			handleList.add(h);
		}
		return handleList;
	}

}
