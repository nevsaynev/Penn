package frogger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;

/**
 * this class acts as model
 * observable class, notify observer when change occurs
 * observer(View: View) and observable(Model: Cast) are connected in controller(Frogger)
 * 
 * @author Dong Yan, Yucong Li
 */
public class Cast extends Observable {
	// constant setting
	private final int frogs = 5;
	private final int frameSizeX = 600;
	private final int carSizeX = 74;
	private final int lane0AxisY = 42;
	private final int lane1AxisY = 101;

	// variables setting
	private int interval = 44; // determine the car density, larger number less
								// dense
	private int speed = 6; // step value for one timer period
	private int level = 1; // level
	
	// declare frog
	Frog frog = new Frog();

	// declare list of sprite
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	// car random generation
	String[] lane0Img = { "red-car-left.png", "yellow-car-left.png",
			"aqua-car-left.png", "blue-car-left.png", "green-car-left.png",
			"white-car-left.png" };
	String[] lane1Img = { "red-car-right.png", "yellow-car-right.png",
			"aqua-car-right.png", "blue-car-right.png", "green-car-right.png",
			"white-car-right.png" };
	boolean carMayOverlap0 = false; // flag to indicate if a new car can be
									// generated without overlapping to previous
									// car, 0 stands for lane 0 (the upper lane)
	boolean carMayOverlap1 = false;
	Random random = new Random(); // random seed
	int randCarType; // random car type
	int randForInterval; // random interval set

	// declare frogs that are still alive
	int frogWaiting = frogs;

	/**
	 * update model
	 */
	public void makeOneStep() {
		// judge and generate new cars
		collisionAndBound();
		carGeneration();

		// make cars moving in the ArrayList by iterating
		Iterator<Sprite> iter = this.sprites.iterator();
		while (iter.hasNext()) {
			Sprite tmp = iter.next();
			tmp.update(); // car moves
		}

		// update the number of frogs waiting(alive)
		frogWaiting = frogs - frog.getDeath();

		// adjust the difficulty due to the crossed frog number
		switch (frog.getCross()) {
		case 0: {
			interval = 44;
			level = 1;
			break;
		}
		case 2: {
			interval = 35;
			level = 2;
			break;
		}
		case 4: {
			interval = 27;
			level = 3;
			speed = 8;
			break;
		}
		case 6: {
			interval = 20;
			level = 4;
			break;
		}
		case 8: {
			interval = 14;
			level = 5;
			break;
		}
		case 10: {
			interval = 9;
			level = 6;
			speed = 10;
			break;
		}
		case 14: {
			interval = 5;
			level = 7;
			break;
		}
		}

		// notify the observer
		setChanged();
		notifyObservers();
	}

	/**
	 * deal with collision between frog and cars, overlapping of cars and set
	 * limits to frog movement
	 */
	private void collisionAndBound() {
		// initialize parameters
		Iterator<Sprite> iter = this.sprites.iterator();
		carMayOverlap0 = false;
		carMayOverlap1 = false;

		// determine whether the frog is killed by any car in the ArrayList
		while (iter.hasNext()) {
			Sprite tmp = iter.next();
			if (frog.x > tmp.x - frog.xSize && frog.x < tmp.x + tmp.xSize) {
				if (frog.y > tmp.y - frog.ySize && frog.y < tmp.y + tmp.ySize) {
					frog.kill();
				}
			}

			// if out of canvas, remove the car
			if (tmp.getX() < -carSizeX || tmp.getX() > frameSizeX) {
				iter.remove();
			}

			// determine whether this car is overlapped with the other car
			if (tmp.getX() > (frameSizeX - carSizeX)
					&& tmp.getY() == lane0AxisY) {
				carMayOverlap0 = true;
			}
			if (tmp.getX() < 0 && tmp.getY() == lane1AxisY) {
				carMayOverlap1 = true;
			}
		}
	}

	/**
	 * generate cars randomly
	 */
	private void carGeneration() {
		// generate cars in the upper lane(going left)
		if (!carMayOverlap0) {// avoid cars overlapping each other at the
								// beginning point
			// generate random number with interval, larger interval indicates
			// less possibility getting a 0
			randForInterval = random.nextInt(interval);
			if (randForInterval == 0) {// if the random number is 0, generate a
										// car
				// car type is randomly selected from array lane0Img
				randCarType = random.nextInt(lane0Img.length);
				// add this car to ArraryList
				this.add(new Car(lane0Img[randCarType], 0, speed));
			}
		}
		// generate cars in the upper lane(going right)
		if (!carMayOverlap1) {
			randForInterval = random.nextInt(interval);
			if (randForInterval == 0) {
				randCarType = random.nextInt(lane1Img.length);
				this.add(new Car(lane1Img[randCarType], 1, speed));
			}
		}
	}

	public Frog getFrog() {
		return frog;
	}

	public int getFrogWaiting() {
		return frogWaiting;
	}

	public ArrayList<Sprite> get() {
		return sprites;
	}

	public void setList(ArrayList<Sprite> s) {
		sprites = s;
	}
	
	/**
	 * add sprite to the ArrayList
	 * @param s
	 */
	public void add(Sprite s) {
		sprites.add(s);
	}

	/**
	 * remove sprite from the ArrayList
	 * @param s
	 */
	public void remove(Sprite s) {
		sprites.remove(s);
	}
	
	public int getLevel() {
		return level;
	}
}