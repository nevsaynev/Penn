package frogger;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


/**
 * @author Dong Yan, Yucong Li
 * 
 * this class paints the panel and acts as view
 * observer class, call overridden update() automatically when change notified
 * observer(View: View) and observable(Model: Cast) are connected in controller(Frogger)
 */
@SuppressWarnings("serial")
public class View extends JPanel implements Observer {
	// declare view and model
	Cast model;

	// declare background
	Background background = new Background("background.png");
	Background foreground = new Background("foreground.png");
	
	// declare foregroundFlage which indicates whether draw foreground
	boolean foregroundFlag = false;
	/**
	 * constructor
	 * @param model
	 */
	View(Cast model) {
		this.model = model;
	}
	
	@Override
	/***
	 * this gets called when the model does notifyObservers()
	 */
	public void update(Observable o, Object arg) {
		repaint();
	}

	@Override
	/***
	 * draw background, cars, and frog on the panel
	 */
	public void paint(Graphics g) {
		// draw background
		background.draw(g, this);
		g.setFont(new Font("New Roman", Font.BOLD,20));
		g.drawString("Cross: " + model.getFrog().getCross(), 37, 28);
		// show the frog alive graphically
		int i = model.getFrogWaiting();
		while (i > 0) { 
			g.drawImage(model.getFrog().getFrogUpImg(), 600 - (30 * i), 150, this);
			i--;
		}
		// draw frog
		model.getFrog().draw(g, this);
		// draw cars by iterating all the cars in cast
		Iterator<Sprite> iter = model.sprites.iterator();
		while (iter.hasNext()) {
			//cast sprite to car here for we are sure it is a car
			Sprite tmp = iter.next();
			tmp.draw(g, this);
		}
		// if foreground is true, display it
		if (foregroundFlag) {
			foreground.draw(g, this);
		}
	}
	
	public void setForegroundFlag(boolean b) {
		foregroundFlag = b;
	}
}