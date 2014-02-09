package frogger;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author Dong Yan, Yucong Li
 * 
 * this class is an abstract class and is extended by car and frog 
 */
public abstract class Sprite {
	// location 
	int x, y;
	// step 
	int dx, dy;
	// size  
	int xSize, ySize;
	
	public abstract void update();
	
	public abstract void draw(Graphics g, JPanel p);
	
	/**
	 * load images for painting
	 * @param fileName
	 * @return
	 */
	public Image loadImage(String fileName) {
        Image img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException exc) {
            System.out.println("Can't load image.");
            exc.printStackTrace();
        }
        return img;
    }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setDx(int i) {
		dx = i;
	}

	public void setDy(int i) {
		dy = i;
	}
}
