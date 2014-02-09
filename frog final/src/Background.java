package frogger;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author Dong Yan, Yucong Li
 * this class basically reads background image and draws it
 */
public class Background {
	Image backImg;
	
	/**
	 * constructor and load the image
	 */
	Background(String filename) {
		backImg = loadImage(filename);
	}
	
	/**
	 * draw the background to p 
	 * @param g
	 * @param p
	 */
	void draw(Graphics g, JPanel p) {
		g.drawImage(backImg, 0, 0, p);
	}

	/**
	 * load image
	 * @param fileName
	 * @return
	 */
	private Image loadImage(String fileName) {
        Image img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException exc) {
            System.out.println("Can't load image.");
        }
        return img;
    }
}