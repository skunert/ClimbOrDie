import java.awt.Point;

import javax.annotation.processing.ProcessingEnvironment;

import com.sun.org.apache.bcel.internal.generic.LoadInstruction;

import processing.core.*;


public class ImageData {
	
	private static int HANDLES_NUM = 8;
	
	private PApplet pApplet;
	
	public PImage armLowerClosedLeft;
	public PImage armLowerOpenLeft;
	public Point armLowerLeftPoint1;
	public Point armLowerLeftPoint2;
	
	public PImage armLowerClosedRight;
	public PImage armLowerOpenRight;
	public Point armLowerRightPoint1;
	public Point armLowerRightPoint2;
	
	public PImage armUpperLeft;
	public Point armUpperLeftPoint1;
	public Point armUpperLeftPoint2;
	
	public PImage armUpperRight;
	public Point armUpperRightPoint1;
	public Point armUpperRightPoint2;
	
	public PImage background;
	
	public PImage banner_lose;
	public PImage banner_move;
	public PImage banner_move_more;
	public PImage banner_start;
	public PImage banner_win;
	
	public PImage crocodile;
	public Point crocodilePoint1;
	
	public PImage[] handles;
	public PImage handleHighlight;
	public int handlesMinHeight;
	
	public PImage headBack;
	public Point headBackPoint1;
	
	public PImage headLeft;
	public Point headLeftPoint1;
	
	public PImage headRight;
	public Point headRightPoint1;
	
	public PImage legLowerLeft;
	public Point legLowerLeftPoint1;
	public Point legLowerLeftPoint2;
	
	public PImage legLowerRight;
	public Point legLowerRightPoint1;
	public Point legLowerRightPoint2;
	
	public PImage legUpperLeft;
	public Point legUpperLeftPoint1;
	public Point legUpperLeftPoint2;
	
	public PImage legUpperRight;
	public Point legUpperRightPoint1;
	public Point legUpperRightPoint2;
	
	public PImage torso;
	public Point torsoPoint1;
	public Point torsoPoint2;
	public Point torsoPoint3;
	public Point torsoPoint4;
	public Point torsoPoint5;
	
	public PImage waterRight;
	public PImage waterLeft;
	
	public ImageData(PApplet pApplet) {
		this.pApplet = pApplet;
		
		this.armLowerClosedLeft = pApplet.loadImage("resources/images/arm_lower_closed_left_48x74_48x320.png");
		this.armLowerOpenLeft = pApplet.loadImage("resources/images/arm_lower_open_left_48x74_48x320.png");
		this.armLowerLeftPoint1 = new Point(48, 74);
		this.armLowerLeftPoint2 = new Point(48, 320);
		
		this.armLowerClosedRight = pApplet.loadImage("resources/images/arm_lower_closed_right_50x74_50x320.png");
		this.armLowerOpenRight = pApplet.loadImage("resources/images/arm_lower_open_right_50x74_50x320.png");
		this.armLowerRightPoint1 = new Point(50, 74);
		this.armLowerRightPoint2 = new Point(50, 320);
		
		this.armUpperLeft = pApplet.loadImage("resources/images/arm_upper_left_48x36_46x204.png");
		this.armUpperLeftPoint1 = new Point(48, 36);
		this.armUpperLeftPoint2 = new Point(46, 204);
		
		this.armUpperRight = pApplet.loadImage("resources/images/arm_upper_right_44x36_46x204.png");
		this.armUpperRightPoint1 = new Point(44, 36);
		this.armUpperRightPoint2 = new Point(46, 204);
		
		this.background = pApplet.loadImage("resources/images/background2.png");
		
		this.banner_lose = pApplet.loadImage("resources/images/banner_lose.png");
		this.banner_move = pApplet.loadImage("resources/images/banner_move.png");
		this.banner_move_more = pApplet.loadImage("resources/images/banner_move_more.png");
		this.banner_start = pApplet.loadImage("resources/images/banner_start.png");
		this.banner_win = pApplet.loadImage("resources/images/banner_win.png");
				
		this.crocodile = pApplet.loadImage("resources/images/crocodile_106x27.png");
		this.crocodilePoint1 = new Point(106, 27);
		
		this.handles = new PImage[8];
		for (int i = 0; i < handles.length; i++) {
			handles[i] = pApplet.loadImage("resources/images/handle" + (i+1) + "_50x50.png");
		}
		this.handleHighlight = pApplet.loadImage("resources/images/handle_highlight.png");
		this.handlesMinHeight = 120;
		
		this.headBack = pApplet.loadImage("resources/images/head_back_116x200.png");
		this.headBackPoint1 = new Point(116, 200);
		
		this.headLeft = pApplet.loadImage("resources/images/head_left__148x240.png");
		this.headLeftPoint1 = new Point(148, 240);
		
		this.headRight = pApplet.loadImage("resources/images/head_right_110x240.png");
		this.headRightPoint1 = new Point(110, 240);
		
		this.legLowerLeft = pApplet.loadImage("resources/images/leg_lower_left_145x38_147x324.png");
		this.legLowerLeftPoint1 = new Point(145, 38);
		this.legLowerLeftPoint2 = new Point(50, 324);
		
		this.legLowerRight = pApplet.loadImage("resources/images/leg_lower_right_52x38_50x324.png");
		this.legLowerRightPoint1 = new Point(52, 38);
		this.legLowerRightPoint2 = new Point(147, 324);
		
		this.legUpperLeft = pApplet.loadImage("resources/images/leg_upper_left_57x48_60x282.png");
		this.legUpperLeftPoint1 = new Point(57, 48);
		this.legUpperLeftPoint2 = new Point(60, 282);
		
		this.legUpperRight = pApplet.loadImage("resources/images/leg_upper_right_55x48_52x282.png");
		this.legUpperRightPoint1 = new Point(55, 48);
		this.legUpperRightPoint2 = new Point(52, 282);
		
		this.torso = pApplet.loadImage("resources/images/torso_164x6_40x85_275x85_60x440_256x440.png");
		this.torsoPoint1 = new Point(164, 6);
		this.torsoPoint2 = new Point(40, 85);
		this.torsoPoint3 = new Point(275, 85);
		this.torsoPoint4 = new Point(80, 460);
		this.torsoPoint5 = new Point(236, 460);
		
		this.waterRight = pApplet.loadImage("resources/images/waterRight.png");
		this.waterLeft = pApplet.loadImage("resources/images/waterLeft.png");
	}

	public PImage getArmLowerClosedLeft() {
		return armLowerClosedLeft;
	}

	public PImage getArmLowerOpenLeft() {
		return armLowerOpenLeft;
	}

	public Point getArmLowerLeftPoint1() {
		return armLowerLeftPoint1;
	}

	public Point getArmLowerLeftPoint2() {
		return armLowerLeftPoint2;
	}

	public PImage getArmLowerClosedRight() {
		return armLowerClosedRight;
	}

	public PImage getArmLowerOpenRight() {
		return armLowerOpenRight;
	}

	public Point getArmLowerRightPoint1() {
		return armLowerRightPoint1;
	}

	public Point getArmLowerRightPoint2() {
		return armLowerRightPoint2;
	}

	public PImage getArmUpperLeft() {
		return armUpperLeft;
	}

	public Point getArmUpperLeftPoint1() {
		return armUpperLeftPoint1;
	}

	public Point getArmUpperLeftPoint2() {
		return armUpperLeftPoint2;
	}

	public PImage getArmUpperRight() {
		return armUpperRight;
	}

	public Point getArmUpperRightPoint1() {
		return armUpperRightPoint1;
	}

	public Point getArmUpperRightPoint2() {
		return armUpperRightPoint2;
	}

	public PImage getBackground() {
		return background;
	}

	public PImage getBanner_lose() {
		return banner_lose;
	}

	public PImage getBanner_move() {
		return banner_move;
	}

	public PImage getBanner_move_more() {
		return banner_move_more;
	}

	public PImage getBanner_start() {
		return banner_start;
	}

	public PImage getBanner_win() {
		return banner_win;
	}

	public PImage getCrocodile() {
		return crocodile;
	}

	public Point getCrocodilePoint1() {
		return crocodilePoint1;
	}

	public PImage[] getHandles() {
		return handles;
	}
	
	public PImage getRandomHandle() {
		return handles[(int) Math.floor((Math.random() * (handles.length - 1)))];
	}
	
	public PImage getHandleByIndex(int index) {
		return handles[index];
	}

	public static int getRandomHandleType() {
		return (int) Math.floor((Math.random() * (HANDLES_NUM - 1)));
	}

	public PImage getHandleHighlight() {
		return handleHighlight;
	}

	public int getHandlesMinHeight() {
		return handlesMinHeight;
	}

	public PImage getHeadBack() {
		return headBack;
	}

	public PImage getHeadLeft() {
		return headLeft;
	}

	public PImage getHeadRight() {
		return headRight;
	}

	public Point getHeadBackPoint1() {
		return headBackPoint1;
	}
	
	public Point getHeadLeftPoint1() {
		return headLeftPoint1;
	}
	
	public Point getHeadRightPoint1() {
		return headRightPoint1;
	}

	public PImage getLegLowerLeft() {
		return legLowerLeft;
	}

	public Point getLegLowerLeftPoint1() {
		return legLowerLeftPoint1;
	}

	public Point getLegLowerLeftPoint2() {
		return legLowerLeftPoint2;
	}

	public PImage getLegLowerRight() {
		return legLowerRight;
	}

	public Point getLegLowerRightPoint1() {
		return legLowerRightPoint1;
	}

	public Point getLegLowerRightPoint2() {
		return legLowerRightPoint2;
	}

	public PImage getLegUpperLeft() {
		return legUpperLeft;
	}

	public Point getLegUpperLeftPoint1() {
		return legUpperLeftPoint1;
	}

	public Point getLegUpperLeftPoint2() {
		return legUpperLeftPoint2;
	}

	public PImage getLegUpperRight() {
		return legUpperRight;
	}

	public Point getLegUpperRightPoint1() {
		return legUpperRightPoint1;
	}

	public Point getLegUpperRightPoint2() {
		return legUpperRightPoint2;
	}

	public PImage getTorso() {
		return torso;
	}

	public Point getTorsoPoint1() {
		return torsoPoint1;
	}

	public Point getTorsoPoint2() {
		return torsoPoint2;
	}

	public Point getTorsoPoint3() {
		return torsoPoint3;
	}

	public Point getTorsoPoint4() {
		return torsoPoint4;
	}

	public Point getTorsoPoint5() {
		return torsoPoint5;
	}
	
	public Point getTorsoPointHip() {
		return new Point(Math.round((torsoPoint4.x + torsoPoint5.x) / 2), torsoPoint4.y);
	}
	
	public int getTorsoPoint4HipWidth() {
		return getTorsoPointHip().x - torsoPoint4.x;
	}
	
	public int getTorsoPoint5HipWidth() {
		return torsoPoint5.x - getTorsoPointHip().x;
	}
	
	public PImage getWaterRight() {
		return waterRight;
	}
	
	public PImage getWaterLeft() {
		return waterLeft;
	}
}
