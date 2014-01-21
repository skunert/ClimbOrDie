import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;


public abstract class Scene {

	protected List<Handle> handles;
	protected Skeleton skeleton;
	protected float waterHeight;
	protected float wallHeight;
	protected PApplet parent;
	
	protected boolean gameWon = false;
	protected boolean gameLost = false;
	protected boolean gameStarted = false;
	protected boolean personFound = false;
	protected boolean skelFound = false;
	
	public int winGameHeight;
    public int looseGameHeight; 
	
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
	
	
	public int getWinGameHeight() {
		return winGameHeight;
	}

	public int getLooseGameHeight() {
		return looseGameHeight;
	}
	
	/**
	 * @return the gameWon
	 */
	public boolean isGameWon() {
		return gameWon;
	}

	/**
	 * @return the gameLost
	 */
	public boolean isGameLost() {
		return gameLost;
	}

	/**
	 * @param gameWon the gameWon to set
	 */
	public void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}

	/**
	 * @param gameLost the gameLost to set
	 */
	public void setGameLost(boolean gameLost) {
		this.gameLost = gameLost;
	}

	/**
	 * @return the gameStarted
	 */
	public boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * @param gameStarted the gameStarted to set
	 */
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * @return the personFound
	 */
	public boolean isPersonFound() {
		return personFound;
	}

	/**
	 * @param personFound the personFound to set
	 */
	public void setPersonFound(boolean personFound) {
		this.personFound = personFound;
	}

	/**
	 * @return the skelFound
	 */
	public boolean isSkelFound() {
		return skelFound;
	}

	/**
	 * @param skelFound the skelFound to set
	 */
	public void setSkelFound(boolean skelFound) {
		this.skelFound = skelFound;
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
