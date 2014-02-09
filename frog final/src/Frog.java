package frogger;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author Dong Yan, Yucong Li
 * act as a "sub-model" of Cast(model)
 */
public class Frog extends Sprite {
	// set value of background size, frog initial location and frog size
	final private int backgroundSizeX = 600;
	final private int backgroundSizeY = 180;
	final private int frogInitX = 285;
	final private int frogInitY = 150;
	final private int frogSizeX = 30;
	final private int frogSizeY = 30;

	// declare images
	Image frogImg, frogDeadImg;
	Image frogUpImg, frogDownImg, frogLeftImg, frogRightImg;
	Image frogUp2Img, frogDown2Img, frogLeft2Img, frogRight2Img;

	// declare flags
	boolean alive = true;
	boolean upReleasedFlag = true;
	int cross = 0;
	int death = 0;
	// use integer to indicate orientation
	// 0 indicates UP, 1 indicates RIGHT, 2 indicates DOWN, 3 indicates LEFT
	int orientationInt = 0;
	
	/**
	 * load images and initialize frog
	 */
	Frog() {
		frogUpImg = loadImage("frog-up.png");
		frogDownImg = loadImage("frog-down.png");
		frogLeftImg = loadImage("frog-left.png");
		frogRightImg = loadImage("frog-right.png");

		frogUp2Img = loadImage("frog-up-2.png");
		frogDown2Img = loadImage("frog-down-2.png");
		frogLeft2Img = loadImage("frog-left-2.png");
		frogRight2Img = loadImage("frog-right-2.png");

		frogDeadImg = loadImage("splat.gif");

		reborn();
	}

	@Override
	public void update() {
		// do nothing
	}

	/**
	 * draw frog to the panel
	 */
	@Override
	public void draw(Graphics g, JPanel p) {
		g.drawImage(frogImg, x, y, p);
	}

	/**
	 * frog move one step further when up key is pressed
	 */
	void upPressed() {
		if (alive && upReleasedFlag) { // avoid interpret keep pressing as multiple presses
			// up not released
			upReleasedFlag = false;
			// frog can only move when it is alive
			switch (orientationInt) {
			// facing up
			case 0: {
				frogImg = frogUp2Img;
				if (y <= 0) { // frog gets across the street
					cross++;
					reborn();
				} else {
					y -= dy;
				}
				break;
			}
			// facing right
			case 1: {
				frogImg = frogRight2Img;
				if (x + xSize <= backgroundSizeX - frogSizeX) { // frog are limited by the right bound
					x += dx;
				}
				break;
			}
			// facing down
			case 2: {
				frogImg = frogDown2Img;
				if (y + ySize <= backgroundSizeY - frogSizeY) { // frog are limited by the bottom bound
					y += dy;
				}
				break;
			}
			//facing left
			case 3: {
				frogImg = frogLeft2Img;
				if (x >= frogSizeX) { // frog are limited by the left bound
					x -= dx;
				}
				break;
			}
			}
		} else if (!alive && upReleasedFlag) {
			reborn();
		}
	}

	/**
	 * frog turn back when down key is pressed
	 */
	void downPressed() {
		orientationInt += 2;
		outRangeOrientationHandle();
		setOrientationImg();
	}

	/**
	 * frog turn 90 degree counterclockwise when left key is pressed
	 */
	void leftPressed() {
		orientationInt--;
		outRangeOrientationHandle();
		setOrientationImg();
	}

	/**
	 * frog turn 90 degree clockwise when right key is pressed
	 */
	void rightPressed() {
		orientationInt++;
		outRangeOrientationHandle();
		setOrientationImg();
	}

	/**
	 * handle the situation when orientationInt is out of range
	 * the range is 0 to 3
	 */
	void outRangeOrientationHandle() {
		switch (orientationInt) {
		// orientationInt = -2 is equivalent to orientationInt = 2
		case -2: {
			orientationInt = 2;
			break;
		}
		// orientationInt = -1 is equivalent to orientationInt = 3
		case -1: {
			orientationInt = 3;
			break;
		}
		// orientationInt = 4 is equivalent to orientationInt = 0
		case 4: {
			orientationInt = 0;
			break;
		}
		// orientationInt = 5 is equivalent to orientationInt = 2
		case 5: {
			orientationInt = 1;
			break;
		}
		}
	}
	
	/**
	 * assign the correspond image to the current image according to the orientationInt
	 */
	void setOrientationImg() {
		if (alive) {
			switch (orientationInt) {
			case 0: {
				frogImg = frogUpImg;
				break;
			}
			case 1: {
				frogImg = frogRightImg;
				break;
			}
			case 2: {
				frogImg = frogDownImg;
				break;
			}
			case 3: {
				frogImg = frogLeftImg;
				break;
			}
			}
		}
	}

	/**
	 * change the flag when up key is released
	 * change the image back to the normal images
	 */
	void upReleased() {
		upReleasedFlag = true;
		setOrientationImg();
	}

	/**
	 * reset the value location/orientation/image/life state
	 * not reset the cross and death counter
	 */
	public void reborn() {
		x = frogInitX;
		y = frogInitY;
		dx = 30;
		dy = 30;
		xSize = frogSizeX;
		ySize = frogSizeY;
		frogImg = frogUpImg;
		orientationInt = 0;
		alive = true;
	}
	
	/**
	 * kill the frog, change the death counter and life state
	 */
	void kill() {
		if (alive) {
			alive = false;
			frogImg = frogDeadImg;
			death++;
		}
	}

	public int getCross() {
		return cross;
	}

	public int getDeath() {
		return death;
	}

	public void setCross(int i) {
		cross = i;
	}

	public void setDeath(int i) {
		death = i;
	}
	
	public Image getFrogUpImg() {
		return frogUpImg;
	}
}