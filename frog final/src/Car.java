package frogger;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author Dong Yan, Yucong Li
 * act as a "sub-model" of Cast(model)
 */
public class Car extends Sprite{
	// set value of background size, frog initial location and car size
	private final int frameSizeX = 600;
	private final int carSizeX = 74;
	private final int carSizeY = 37;
	private final int lane0AxisY = 42;
	private final int lane1AxisY = 101;
	
	// car image and lane declare
	Image carImg;
	int lane;
	
	/**
	 * constructor
	 * @param fileName: the name of image
	 * @param lane: 0 stands for the north lane, 1 south
	 * @param speed: the step value of each update
	 */
	Car(String fileName, int lane, int speed) {
		this.lane = lane;
		carImg = loadImage(fileName);
		dx = speed;
		dy = 0;
		xSize = carSizeX;
		ySize = carSizeY;
		
		// the initial location of every new car
		switch (this.lane) {
		case 0: {
			x = frameSizeX;
			y = lane0AxisY;
			break;
		}
		case 1: {
			x = -carSizeX;
			y = lane1AxisY;
			break;
		}
		default: {x = 0; y = 0;} // error when this occurs
		}
	}
	
	/**
	 * make movement when called
	 */
	@Override
	public void update() {
		switch (lane) {
		case 0: {
			x = x - dx;
			break;
		}
		case 1: {
			x = x + dx;
			break;
		}
		}
	}

	/**
	 * draw the car to the panel
	 */
	@Override
	public void draw(Graphics g, JPanel p) {
		g.drawImage(carImg, x, y, p);
	}
	
	Image getImage() {
		return carImg;
	}
}