package frogger;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * this is the master class and it acts as a controller
 * @author Dong Yan, Yucong Li
 */
public class Frogger extends JFrame {
	// set the frame size
	public static final int frameSizeX = 600;
	private final int frameSizey = 265;
	
	// declare components
	private JPanel buttonPanel = new JPanel();
	private JButton startButton = new JButton("Start");
	private JButton pauseButton = new JButton("Pause");
	private JLabel label = new JLabel("Ready to start the game?");
	KeyL keyListener = new KeyL();
	private JButton introButton = new JButton("HELP");
	private JButton frogPowerButton = new JButton("!!FROG POWER!!(1)");
	private JButton superJumpButton = new JButton("!!SUPER JUMP!!(1)");
	
	
	// declare model and view
	private Cast model = new Cast();
	private View view = new View(model);
	
	// declare timer and other variables
	private Timer timer = new Timer();
	private boolean pause = true; // pause flag, indicates whether the key listener is activated
	
	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		Frogger frogger = new Frogger();
		frogger.run();
	}
	
	/**
	 * call run method to start the game
	 */
	public void run() {
		// initialize components
		layOutComponents();
		attachListenersToComponents();
		
		// Connect model and view
		model.addObserver(view);
	}
	
	/**
	 * layout components
	 */
	private void layOutComponents() {
		// JFrame settings
		pack();
		setTitle("WELCOME TO FROGGER GAME!");
		setSize(frameSizeX, frameSizey);
		this.setResizable(false);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		// button and button panel settings
		this.add(BorderLayout.CENTER, view);
		this.add(BorderLayout.SOUTH, buttonPanel);
		buttonPanel.setLayout(new GridLayout(2, 3));
		buttonPanel.add(label);
		buttonPanel.add(startButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(introButton);
		buttonPanel.add(frogPowerButton);
		buttonPanel.add(superJumpButton);
		pauseButton.setEnabled(false);
	}

	/**
	 * attach listeners to components
	 */
	private void attachListenersToComponents() {
		// add key listener
		//this.addKeyListener(keyListener);
		//buttonPanel.addKeyListener(keyListener);
		startButton.addKeyListener(keyListener);
		pauseButton.addKeyListener(keyListener);
		introButton.addKeyListener(keyListener);
		frogPowerButton.addKeyListener(keyListener);
		superJumpButton.addKeyListener(keyListener);
		view.addKeyListener(keyListener);
		this.setFocusable(true);
		this.requestFocus();

		// add action listener to start button
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// components' text setting
				setTitle("WELCOME TO FROGGER GAME!");
				startButton.setText("Resume");
				pauseButton.setText("Pause");
				
				// set new timer and timer task
				timer = new Timer(true);
				timer.schedule(new Strobe(), 0, 40); // task, delay, duration (milliseconds)
				
				// flags settings
				pause = false;
				startButton.setEnabled(false);
				pauseButton.setEnabled(true);
				introButton.setEnabled(true);
				
				// let foreground not display
				view.setForegroundFlag(false);
			}
		});
		
		// add action listener to pause button
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// component's text setting
				setTitle("WELCOME TO FROGGER GAME! -- Pause");
				
				// cancel timer
				timer.cancel();
				
				// flags settings
				pause = true;
				startButton.setEnabled(true);
				pauseButton.setEnabled(false);
			}
		});
		
		frogPowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				frogPowerButton.setText("!!FROG POWER!!(0)");
				frogPowerButton.setEnabled(false);
				// clear the ArrayList
				model.setList(new ArrayList<Sprite>());
			}
		});
		
		superJumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				superJumpButton.setText("!!SUPER JUMP!!(0)");
				superJumpButton.setEnabled(false);
				// let the frog move faster
				model.getFrog().setDx(75);
				model.getFrog().setDy(75);
			}
		});
		
		introButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// component's text setting
				setTitle("WELCOME TO FROGGER GAME! -- Instruction");
				
				// cancel timer
				timer.cancel();
				
				// flags settings
				pause = true;
				startButton.setEnabled(true);
				pauseButton.setEnabled(false);
				introButton.setEnabled(false);
				view.setForegroundFlag(true);
				
				// repaint the view so that the foreground will display
				repaint();
			}
		});
		
		
	}

	/**
	 * timer task, executed periodically
	 * update label, model, and game end judging
	 */
	private class Strobe extends TimerTask {
		@Override
		public void run() {
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setText("-- Level: " + model.getLevel() + " --");
			model.makeOneStep();
			gameEndJudge();
		}
	}

	/**
	 * an interface implements KeyListener, override 3 methods
	 * control frog movement
	 */
	private class KeyL implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (!pause) { // when the game is not paused
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					model.getFrog().upPressed();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					model.getFrog().downPressed();
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					model.getFrog().leftPressed();
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					model.getFrog().rightPressed();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (true) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					model.getFrog().upReleased();
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	/**
	 * judging whether the game is end
	 * if the game is ended, give out notifications and display/rate the result 
	 */
	private void gameEndJudge() {
		if (model.getFrogWaiting() == 0)
		{	
			// rate and set components' text
			this.setTitle("-- Game Over --");
			if (model.getFrog().getCross() < 4) {
				label.setText(" " + model.getFrog().getCross() + " frogs crossed, try harder!");
			}
			if (model.getFrog().getCross() >= 4 && model.getFrog().getCross() < 10) {
				label.setText(" " + model.getFrog().getCross() + " frogs crossed, not bad!");
			}
			if (model.getFrog().getCross() >= 10 && model.getFrog().getCross() < 16) {
				label.setText(" " + model.getFrog().getCross() + " frogs crossed, good work!");
			}
			if (model.getFrog().getCross() >= 16) {
				label.setText(" " + model.getFrog().getCross() + " frogs crossed, exellent!");
			}
			startButton.setText("Restart");
			pauseButton.setText("End");
			
			// pause the game
			timer.cancel();
			pause = true;			
			startButton.setEnabled(true);
			pauseButton.setEnabled(false);
			frogPowerButton.setText("!!FROG POWER!!(1)");
			frogPowerButton.setEnabled(true);
			superJumpButton.setText("!!SUPER JUMP!!(1)");
			superJumpButton.setEnabled(true);
			
			// clear historical data
			model.getFrog().setCross(0);
			model.getFrog().setDeath(0);
			model.getFrog().reborn();
		}
	}
}