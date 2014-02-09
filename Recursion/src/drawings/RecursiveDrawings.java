package drawings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.lang.Math;

/**
 * Program to do recursive drawings.
 * 
 * @author David Matuszek
 * @author Dong Yan
 * @version 2.0
 */
public class RecursiveDrawings extends JFrame implements ActionListener {
	private JPanel drawingPanel = new JPanel();
	private JPanel controlPanel = new JPanel();
	private JRadioButton[] depthButtons = new JRadioButton[6];
	private ButtonGroup group = new ButtonGroup();
	private JButton[] drawingButtons = new JButton[6];

	private int depth; // maximum depth of the recursion
	private int drawingNumber; // which drawing to make

	/**
	 * Main method for this application.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		new RecursiveDrawings().run();
	}

	/**
	 * Runs this RecursiveDrawings application.
	 */
	public void run() {
		createWidgets();
		layOutGui();
		setSize(600, 600);
		attachListeners();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Creates all the components needed by the application.
	 */
	private void createWidgets() {
		for (int i = 0; i < 6; i++) {
			depthButtons[i] = new JRadioButton(" " + (i + 1));
			group.add(depthButtons[i]);
		}
		depthButtons[0].setSelected(true);
		for (int i = 0; i < 6; i++) {
			drawingButtons[i] = new JButton("Drawing " + (i + 1));
		}
		// TODO
		drawingButtons[0].setText("Squares");
		drawingButtons[1].setText("Cross");
		drawingButtons[2].setText("RandomOvals");
		drawingButtons[3].setText("RepeatOvals");
		drawingButtons[4].setText("Tree");
		drawingButtons[5].setText("SnowFlake");
	}

	/**
	 * Arranges the components for this application.
	 */
	private void layOutGui() {
		add(drawingPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		controlPanel.setLayout(new GridLayout(3, 4));
		for (int i = 0; i < 3; i++) {
			controlPanel.add(depthButtons[i]);
			controlPanel.add(depthButtons[i + 3]);
			controlPanel.add(drawingButtons[i]);
			controlPanel.add(drawingButtons[i + 3]);
		}
		setSize(400, 300);
	}

	/**
	 * Attaches this RecursiveDrawings object as a listener for all the drawing
	 * buttons. The depth radio buttons don't get listeners.
	 */
	private void attachListeners() {
		for (int i = 0; i < 6; i++) {
			drawingButtons[i].addActionListener(this);
		}
	}

	/**
	 * Responds to a button press by setting the global variables
	 * <code>depth</code> and <code>drawingNumber</code>, then requesting that
	 * the painting be done.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 6; i++) {
			if (depthButtons[i].isSelected())
				depth = i + 1;
			if (e.getSource() == drawingButtons[i])
				drawingNumber = i + 1;
		}
		repaint();
	}

	/**
	 * Paints one of the drawings, based on the global variables
	 * <code>depth</code> and <code>drawingNumber</code>.
	 * 
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int width = drawingPanel.getWidth();
		int height = drawingPanel.getHeight();

		// clear panel
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);

		g.setClip(0, 0, width, height);
		int x = drawingPanel.getWidth() / 2;
		int y = drawingPanel.getHeight() / 2 + 15;
		int length = Math.min(x, y - 20);
		switch (drawingNumber) {
		case 1:
			squares(g, x, y, length, depth);
			break;
		case 2:
			// enlarge the pattern by multiplying its length by 2
			cross(g, x, y, length * 2, depth);
			break;
		case 3:
			randomOvals(g, x, y, length, length / 2, depth);
			break;
		case 4:
			repeatOvals(g, x, y, length, depth);
			break;
		case 5:
			// adjust pattern position and size
			// multiply depth by 2 to see more recursions
			tree(g, x, y + length, length / 2, 90, (depth) * 2);
			break;
		case 6:
			// draw the 3 edges of basic equilateral triangle by calculating
			// three points
			snowFlake(g, x + length / 2,
					(int) (y + Math.sqrt(3) * (length) / 6), x,
					(int) (y - Math.sqrt(3) * (length) / 3), depth - 1);
			snowFlake(g, x - length / 2,
					(int) (y + Math.sqrt(3) * (length) / 6), x + length / 2,
					(int) (y + Math.sqrt(3) * (length) / 6), depth - 1);
			snowFlake(g, x, (int) (y - Math.sqrt(3) * (length) / 3), x - length
					/ 2, (int) (y + Math.sqrt(3) * (length) / 6), depth - 1);
			break;
		}
	}

	/**
	 * draw square pattern by recursion
	 * 
	 * @param g
	 *            graphics
	 * @param x
	 *            coordinate x of the center of each square
	 * @param y
	 *            coordinate x of the center of each square
	 * @param length
	 *            length of each edge of the square
	 * @param depth
	 *            represents the depth of recursion
	 */
	private void squares(Graphics g, int x, int y, int length, int depth) {
		// return when recursion ends
		if (depth == 0)
			return;

		// 2 ratio looks good
		int ratio = 2;
		// Base pattern
		g.setColor(Color.BLUE);
		g.drawRect(x - length / 2, y - length / 2, length, length);

		// recursively draw 4 smaller squares of depth-1
		// length is shrink to half for every time it recurs
		squares(g, x - length / ratio, y - length / ratio, length / ratio,
				depth - 1); // upper left
		squares(g, x - length / ratio, y + length / ratio, length / ratio,
				depth - 1); // lower left
		squares(g, x + length / ratio, y - length / ratio, length / ratio,
				depth - 1); // upper right
		squares(g, x + length / ratio, y + length / ratio, length / ratio,
				depth - 1); // lower right

	}

	/**
	 * draw oval pattern by recursion, color changes randomly
	 * 
	 * @param g
	 *            graphics
	 * @param x
	 *            coordinate x of center of each oval
	 * @param y
	 *            coordinate x of center of each oval
	 * @param width
	 *            width of the oval
	 * @param height
	 *            height of the oval
	 * @param depth
	 *            represent the depth of recursion
	 */
	private void randomOvals(Graphics g, int x, int y, int width, int height,
			int depth) {
		// return when recursion ends
		if (depth == 0)
			return;

		// random color choose between green and yellow randomly each time
		// Color[] colors = { Color.GREEN, Color.YELLOW };
		// Random random = new Random();
		// int i = random.nextInt(colors.length);
		// g.setColor(colors[i]);

		// base pattern
		g.setColor(Color.BLUE);
		g.drawOval(x - width / 2, y - height / 2, width, height);
		// recursively draw 5 ovals rotated 90 degree from the previous oval
		// and occupy it
		// if depth number is odd, width is larger than height
		if (depth % 2 == 1) {
			randomOvals(g, x, y - height / 4, width, height, depth - 1);
			randomOvals(g, x, y + height / 4, width, height, depth - 1);
			randomOvals(g, x, y - height / 2, width, height, depth - 1);
			randomOvals(g, x, y + height / 2, width, height, depth - 1);
			randomOvals(g, x, y, width, height, depth - 1);

			// and if depth number is even, height is larger than width
		} else {
			randomOvals(g, x - width / 4, y, height, width, depth - 1);
			randomOvals(g, x + width / 4, y, height, width, depth - 1);
			randomOvals(g, x - width / 2, y, height, width, depth - 1);
			randomOvals(g, x + width / 2, y, height, width, depth - 1);
			randomOvals(g, x, y, height, width, depth - 1);

		}

	}

	/**
	 * draw cross pattern (occupied 9 squares and filled 4 of them) by recursion
	 * 
	 * @param g
	 *            graphics
	 * @param x
	 *            coordinate x of center of each square
	 * @param y
	 *            coordinate x of center of each square
	 * @param length
	 *            length of each edge of the square
	 * @param depth
	 *            represent the depth of recursion
	 */
	private void cross(Graphics g, int x, int y, int length, int depth) {
		// return when recursion ends
		if (depth == 0)
			return;
		int ratio = 3;
		// Base pattern
		g.setColor(Color.BLACK);
		g.fillRect(x - length / 6, y - length / 2, length / ratio, length
				/ ratio); // up
		g.fillRect(x - length / 6, y + length / 6, length / ratio, length
				/ ratio); // down
		g.setColor(Color.RED);
		g.fillRect(x - length / 2, y - length / 6, length / ratio, length
				/ ratio); // left
		g.fillRect(x + length / 6, y - length / 6, length / ratio, length
				/ ratio); // right

		// recursively draw 5 smaller crosses of depth-1 in the blanks of the
		// previous cross
		// length is shrink to 1/3 for every time it recurs
		cross(g, x - length / ratio, y - length / ratio, length / ratio,
				depth - 1); // upper left
		cross(g, x - length / ratio, y + length / ratio, length / ratio,
				depth - 1); // lower left
		cross(g, x + length / ratio, y - length / ratio, length / ratio,
				depth - 1); // upper right
		cross(g, x + length / ratio, y + length / ratio, length / ratio,
				depth - 1); // lower right
		cross(g, x, y, length / ratio, depth - 1); // center
	}

	/**
	 * draw oval pattern by recursion, color changes repeatedly
	 * 
	 * @param g
	 *            graphics
	 * @param x
	 *            coordinate x of center of each oval
	 * @param y
	 *            coordinate x of center of each oval
	 * @param length
	 *            diameter of each edge of the oval
	 * @param depth
	 *            represent the depth of recursion
	 */
	private void repeatOvals(Graphics g, int x, int y, int length, int depth) {
		// return when recursion ends
		if (depth == 0)
			return;

		// 2 ratio looks good
		int ratio = 2;

		// change the pattern color by its depth
		// if the depth is odd number, the color is black
		// and light gray for even number
		if (depth % 2 == 1) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
		// base pattern
		g.fillOval(x - length / 4, y - length / 4, length / 2, length / 2);

		// recursively draw 4 smaller ovals of order depth
		// length is shrink to half for every time it recurs
		repeatOvals(g, x - length / ratio, y - length / ratio, length / ratio,
				depth - 1); // upper left
		repeatOvals(g, x - length / ratio, y + length / ratio, length / ratio,
				depth - 1); // lower left
		repeatOvals(g, x + length / ratio, y - length / ratio, length / ratio,
				depth - 1); // upper right
		repeatOvals(g, x + length / ratio, y + length / ratio, length / ratio,
				depth - 1); // lower right

	}

	/**
	 * draw a tree by recursion
	 * 
	 * @param g
	 *            Graphics
	 * @param x0
	 *            coordinate x of start point of the trunk(parent branch)
	 * @param y0
	 *            coordinate y of start point of the trunk(parent branch)
	 * @param length
	 *            length of each branch
	 * @param angle
	 *            angle rotated every time recurs
	 * @param depth
	 *            depth of recursion
	 */
	private void tree(Graphics g, int x0, int y0, double length, int angle,
			int depth) {
		// return when recursion ends
		if (depth == 0)
			return;

		double PI = 3.1415;
		// calculate the starting point(which the ending point of the current
		// line) for the next two lines
		int x1 = (int) (x0 + length * Math.cos(angle * PI / 180));
		int y1 = (int) (y0 - length * Math.sin(angle * PI / 180));
		// Base pattern, display as the trunk
		g.drawLine(x0, y0, x1, y1);
		// recursively draw two branches of depth-1
		g.setColor(Color.GREEN);
		// There are two kinds of branches
		// one is 30 degree anti-clockwise relative to its parent branch
		// one is 50 degree clockwise relative to its parent branch
		// length is decreased proportionally for every time it recurs
		tree(g, x1, y1, length * 0.66, angle - 50, depth - 1);
		tree(g, x1, y1, length * 0.75, angle + 30, depth - 1);
	}

	/**
	 * 
	 * @param g
	 *            Graphics
	 * @param x1
	 *            coordinate x of starting point of the sawtooth edge
	 * @param y1
	 *            coordinate y of starting point of the sawtooth edge
	 * @param x5
	 *            coordinate x of ending point of the sawtooth edge
	 * @param y5
	 *            coordinate x of ending point of the sawtooth edge
	 * @param depth
	 *            depth of recursion
	 */
	private void snowFlake(Graphics g, int x1, int y1, int x5, int y5, int depth) {
		int deltaX, deltaY, x2, y2, x3, y3, x4, y4;
		// Base pattern
		// return when recursion ends
		if (depth == 0) {
			g.setColor(Color.BLUE);
			// Base pattern
			// One edge of the triangle
			g.drawLine(x1, y1, x5, y5);
			return;
		}
		// calculate the coordinates of three points of the triangle sit on this
		// edge
		deltaX = x5 - x1;
		deltaY = y5 - y1;
		x2 = x1 + deltaX / 3;
		y2 = y1 + deltaY / 3;
		x3 = (int) (0.5 * (x1 + x5) + Math.sqrt(3) * (y1 - y5) / 6);
		y3 = (int) (0.5 * (y1 + y5) + Math.sqrt(3) * (x5 - x1) / 6);
		x4 = x1 + 2 * deltaX / 3;
		y4 = y1 + 2 * deltaY / 3;

		// recursively draw the 4 edges for the outline in depth-1
		snowFlake(g, x1, y1, x2, y2, depth - 1);
		snowFlake(g, x2, y2, x3, y3, depth - 1);
		snowFlake(g, x3, y3, x4, y4, depth - 1);
		snowFlake(g, x4, y4, x5, y5, depth - 1);
	}
}
