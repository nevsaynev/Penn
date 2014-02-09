package calculator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame {

	// Declare number buttons array and a dot button.
	private NumberButton jbtNum[] = new NumberButton[10];
	private NumberButton jbtDot;

	// Declare unary and binary operators.
	private OperationButton jbtSignChange;
	private OperationButton jbtAdd;
	private OperationButton jbtSubtract;
	private OperationButton jbtMultiply;
	private OperationButton jbtDivide;
	private OperationButton jbtSquRoot;
	private OperationButton jbtPer;
	private OperationButton jbtMulInv;

	// Declare equal button
	private EqualButton jbtEqual;

	// Clear
	private ClearButton jbtClear;

	// Memory operators
	private MemoryButton jbtMC;
	private MemoryButton jbtMR;
	private MemoryButton jbtMadd;
	private MemoryButton jbtMminus;

	// Text
	private JTextField jtfResult;
	private JLabel slash; // Line between text field and buttons pad.

	// Container
	Container container;

	// Calculator chip for performing calculations.
	CalculatorChip calBrain;

	// Calculator temporary variables
	boolean addVal = false; // true when add button is pressed.
	boolean subVal = false;// true when subtract button is pressed.
	boolean mulVal = false;// true when multiply button is pressed.
	boolean divVal = false; // true when divide button is pressed.
	int count = 0; // count the no. of operands for binary operators.
	int numEqual = 0; // Count no. of times equal button is pressed
						// contiguously.
	double eqVal; //
	boolean equalOperated = false; // Whenever equal button is pressed, this
									// becomes true.
	boolean isSquareRoot = false; // True when square root is taken.
	boolean signChange = false; // If we press the sign change button and follow
								// with some operation, it ensures that the
								// operator has the updated operand

	boolean followOp = false; // Whenever an operator is called, clear text
								// field.

	public static void main(String[] args) {
		Calculator myCal = new Calculator();
		myCal.createGUI(); // call the createGUI method of calculator.
		myCal.calBrain = new CalculatorChip(); // instantiate the calculatorchip
												// object.
	}

	/**
	 * Packs all the components of GUI and create one.
	 */
	void createGUI() {
		setTitle("Calculator");
		// Set the frame not Resizable.
		setResizable(false);
		createMemAndNumPad();
		// Border layout for frame.
		setLayout(new BorderLayout(3, 3));
		add(createMemAndNumPad(), BorderLayout.WEST);
		add(createOperationPanel(), BorderLayout.EAST);

		add(createTextArea(), BorderLayout.NORTH);

		pack();
		implementActionListeners();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Creates the text field.
	 * 
	 * @return
	 */
	Component createTextArea() {
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout(6, 6));
		p3.add(jbtClear = new ClearButton("Clear"), BorderLayout.EAST);
		p3.add(slash = new JLabel(
				"--------------------------------------------------------"),
				BorderLayout.WEST);

		p3.add(jtfResult = new JTextField(), BorderLayout.NORTH);
		jtfResult.setPreferredSize(new Dimension(60, 60));

		jtfResult.setFont(new Font("Times New Roman", Font.BOLD, 28));

		jtfResult.setHorizontalAlignment(JTextField.RIGHT);
		jtfResult.setEditable(false);
		jtfResult.setBackground(Color.WHITE);
		jtfResult.setText("0");
		return p3;
	}

	/**
	 * Create memory pad and number pad.
	 * 
	 * @return
	 */
	Component createMemAndNumPad() {
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(5, 3, 6, 6));
		// 5 is the gap between each button
		p1.add(jbtMC = new MemoryButton("MC"));
		p1.add(jbtMR = new MemoryButton("MR"));
		p1.add(jbtMadd = new MemoryButton("M+"));
		int x[] = new int[] { 7, 4, 1 };
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				NumberButton num = new NumberButton(calBrain);
				num.setText(Integer.toString(i + x[j]));
				p1.add(jbtNum[i + x[j]] = num);
			}
		}
		jbtNum[0] = new NumberButton(calBrain);
		jbtNum[0].setText(Integer.toString(0));
		p1.add(jbtNum[0]);
		p1.add(jbtDot = new NumberButton("."));
		p1.add(jbtSignChange = new OperationButton("+/-"));

		return p1;
	}

	/**
	 * Creates operation panel on the right side of GUI.
	 * 
	 * @return
	 */
	Component createOperationPanel() {
		JPanel p21 = new JPanel();
		p21.setLayout(new GridLayout(3, 2, 6, 6));
		p21.add(jbtMminus = new MemoryButton("M-"));
		p21.add(jbtSquRoot = new OperationButton("\u221A"));
		p21.add(jbtDivide = new OperationButton("\u00F7"));
		p21.add(jbtPer = new OperationButton("%"));
		p21.add(jbtMultiply = new OperationButton("x"));
		p21.add(jbtMulInv = new OperationButton("1/x"));
		p21.setPreferredSize(new Dimension(123, 123));

		JPanel p22 = new JPanel();
		p22.setLayout(new GridLayout(2, 1, 6, 6));
		p22.add(jbtSubtract = new OperationButton("-"));
		p22.add(jbtAdd = new OperationButton("+"));

		JPanel p23 = new JPanel();
		p23.setLayout(new GridLayout(1, 2, 6, 6));
		p23.add(p22);
		p23.add(jbtEqual = new EqualButton("="));

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));

		p2.add(p21);
		p2.add(Box.createRigidArea(new Dimension(0, 6)));
		p2.add(p23);
		return p2;
	}

	/**
	 * Interface between GUI and action listeners.
	 */
	void implementActionListeners() {

		jbtAdd.addActionListener(new ListenToOperationButton());
		jbtDivide.addActionListener(new ListenToOperationButton());
		jbtMultiply.addActionListener(new ListenToOperationButton());
		jbtSubtract.addActionListener(new ListenToOperationButton());

		jbtEqual.addActionListener(new ListenToEqual());
		for (int i = 0; i < 10; i++) {
			jbtNum[i].addActionListener(new ListenToNumberButton());
		}

		jbtDot.addActionListener(new ListenToNumberButton());

		jbtSquRoot.addActionListener(new ListenToUnaryOperations());
		jbtMulInv.addActionListener(new ListenToUnaryOperations());
		jbtPer.addActionListener(new ListenToUnaryOperations());
		jbtSignChange.addActionListener(new ListenToUnaryOperations());
		jbtClear.addActionListener(new ListenToClear());

		jbtMC.addActionListener(new ListenToMemoryButton());
		jbtMR.addActionListener(new ListenToMemoryButton());
		jbtMadd.addActionListener(new ListenToMemoryButton());
		jbtMminus.addActionListener(new ListenToMemoryButton());
	}

	/**
	 * Pass action to onclick method in Calculator button class.
	 * 
	 * @param e
	 */
	void performOnClickColorChange(ActionEvent e) {
		jbtAdd.onClick(e);
		jbtSubtract.onClick(e);
		jbtMultiply.onClick(e);
		jbtDivide.onClick(e);
	}

	/**
	 * Resets the binary operators boolean values.
	 */
	void initialState() {
		// When after equal, some operation pressed.
		count = 0;
		addVal = false;
		subVal = false;
		mulVal = false;
		divVal = false;

	}

	/**
	 * Check if the display contains error before any action is performed except
	 * pressing a number.
	 * 
	 * @return
	 */
	boolean checkDisplay() {
		try {
			Double.parseDouble(jtfResult.getText());
			return true;
		} catch (Exception exp) {
			jtfResult.setText("Error");
			return false;
		}
	}

	// General settings.
	// 1. equalOperated becomes false if any listener is called except Listen to
	// Equal.
	// 2 . numEqual becomes 0 and it is reflected in calculatorchip
	// simultaneously if we press anything except equal.
	// 3. isSquare root is set false if we press anything except square root
	// button.
	// 4. signChange becomes false if we press anything except +/-.

	class ListenToNumberButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// If equal button pressed immediately before number, initiate a new
			// calculation next time.
			if (equalOperated) {
				initialState();
				equalOperated = false;
			}
			isSquareRoot = false;

			// Whenever a number button is clicked, set "Clear" to C/AC button.
			jbtClear.setText("Clear");

			// Extract the displayed value.
			String display;
			String addToDisplay = "";
			display = jtfResult.getText();

			// If display is 0 or if the number is pressed following some event
			// on other types of button, clear the display.
			if (display.equals("0") || followOp) {
				display = "";
				followOp = false;
			}
			addToDisplay = checkSource(e, display);

			// Set the value to be displayed.
			jtfResult.setText(display + addToDisplay);

			// Enable all the binary operator buttons.
			jbtAdd.setEnabled(true);
			jbtSubtract.setEnabled(true);
			jbtMultiply.setEnabled(true);
			jbtDivide.setEnabled(true);

			// Change color if they were pressed earlier to this function call.
			performOnClickColorChange(e);
		}

		/**
		 * Checks the source of event and call the appropriate method in
		 * calculator chip.
		 * 
		 * @param e
		 * @param display
		 * @return the string to be displayed.
		 */
		String checkSource(ActionEvent e, String display) {
			String addToDisplay = "";
			for (int i = 0; i < 10; i++) {
				if (e.getSource() == jbtNum[i]) {
					addToDisplay = calBrain.digit(i);
					return addToDisplay;
				}
			}
			// If we press dot and display already does not contain one, adds
			// dot to the display.
			if (!display.contains(".")) {
				if (display.isEmpty())
					addToDisplay = "0" + calBrain.decimalPoint();
				else
					addToDisplay = calBrain.decimalPoint();
			}

			return addToDisplay;
		}
	}

	class ListenToOperationButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Check if display contains error.
			if (!checkDisplay()) {
				return;
			}

			// Check if sign changed immediately before operation.
			if (isSignChange(e)) {
				return;
			}

			// Check if equal button pressed immediately before operation
			// button. If yes, set calculator to initial state as if a new
			// computation is to be performed.
			if (equalOperated) {
				initialState();
			}

			isSquareRoot = false;
			numEqual = 0;
			calBrain.noOfEquals = numEqual;

			// Check if any of the 4 binary operators is already pressed.
			if (checkButton()) {
				followOp = true;
				checkNumOfOperandAndPerform();
			}
			// Enable/Disable the button as per the selection and set the
			// booleans.
			setBooleanAndButton(e);

			equalOperated = false;
		}

		/**
		 * If signChange performed before operation, retrun true, else return
		 * false.
		 * 
		 * @param e
		 * @return
		 */
		boolean isSignChange(ActionEvent e) {
			if (signChange) {

				String display;
				display = jtfResult.getText();

				// If sign changed of second operand, compute the result and
				// display it.
				if (count == 1) {
					calBrain.temp2 = Double.parseDouble(display);
					performOp();
					count = 0;
				} else {
					// If sign change of first operand, store updated value in
					// temporary variable.
					calBrain.temp = Double.parseDouble(display);
					count = 1;
				}
				signChange = false;
				setBooleanAndButton(e);
				return true;
			} else
				return false;
		}

		/**
		 * Checks number of operands and perform the computation.
		 */
		void checkNumOfOperandAndPerform() {

			String display;
			display = jtfResult.getText();

			// If one operand, wait for the second one.
			if (count == 0) {

				// If we press many numbers and operations randomly (in some
				// sequence), compute the result everytime without waiting for
				// user to explicitly enter the second operand.
				if (checkOp()) {
					calBrain.temp = calBrain.temp2;
					count++;
				} else {
					calBrain.temp = Double.parseDouble(display);
					count = 2;
				}
			}

			// If two operands, perform computation.
			if (count == 1) {
				calBrain.temp2 = Double.parseDouble(display);

				eqVal = calBrain.temp2;
				performOp();
				count = 0;
			}
			if (count == 2)
				count = 1;
		}

		/**
		 * Handles enabling / disabling of operation buttons.
		 * 
		 * @param e
		 */
		void setBooleanAndButton(ActionEvent e) {

			// Disable the button pressed and set its boolean true.
			if (e.getSource() == jbtAdd) {
				addVal = true;
				subVal = false;
				divVal = false;
				mulVal = false;
				jbtAdd.setEnabled(false);
				jbtSubtract.setEnabled(true);
				jbtMultiply.setEnabled(true);
				jbtDivide.setEnabled(true);
			} else if (e.getSource() == jbtSubtract) {
				addVal = false;
				subVal = true;
				divVal = false;
				mulVal = false;
				jbtAdd.setEnabled(true);
				jbtSubtract.setEnabled(false);

				jbtMultiply.setEnabled(true);
				jbtDivide.setEnabled(true);
			} else if (e.getSource() == jbtDivide) {
				addVal = false;
				subVal = false;
				divVal = true;
				mulVal = false;
				jbtAdd.setEnabled(true);
				jbtSubtract.setEnabled(true);
				jbtMultiply.setEnabled(true);
				jbtDivide.setEnabled(false);

			} else if (e.getSource() == jbtMultiply) {
				addVal = false;
				subVal = false;
				divVal = false;
				mulVal = true;

				jbtAdd.setEnabled(true);
				jbtSubtract.setEnabled(true);
				jbtMultiply.setEnabled(false);
				jbtDivide.setEnabled(true);

			}

			// Performs color change on click.
			performOnClickColorChange(e);
		}

		/**
		 * If all four buttons are enabled, return true, else return false.
		 * 
		 * @return
		 */
		boolean checkButton() {
			if (jbtAdd.isEnabled() && jbtSubtract.isEnabled()
					&& jbtMultiply.isEnabled() && jbtDivide.isEnabled())
				return true;
			else
				return false;
		}

		/**
		 * If any operation is active, return true.
		 * 
		 * @return
		 */
		boolean checkOp() {
			if (addVal || subVal || mulVal || divVal)
				return true;
			else
				return false;
		}

		/**
		 * Perform the operation corresponding to button pressed.
		 */
		void performOp() {
			String addToDisplay = "";
			if (addVal) {
				calBrain.chooseOperator = 1;
				addToDisplay = calBrain.add();
				calBrain.temp2 = Double.parseDouble(addToDisplay);
			} else if (subVal) {
				calBrain.chooseOperator = 2;
				addToDisplay = calBrain.subtract();
				calBrain.temp2 = Double.parseDouble(addToDisplay);
			} else if (mulVal) {
				calBrain.chooseOperator = 3;
				addToDisplay = calBrain.multiply();
				calBrain.temp2 = Double.parseDouble(addToDisplay);
			} else if (divVal) {
				calBrain.chooseOperator = 4;
				addToDisplay = calBrain.divide();

				// Catch the error if divide gives one.
				try {
					calBrain.temp2 = Double.parseDouble(addToDisplay);
				} catch (Exception exp) {
				}
			} else {

				return;
			}
			jtfResult.setText(addToDisplay);
		}
	}

	class ListenToEqual implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Check if display contains error.
			if (!checkDisplay()) {
				return;
			}

			// Set corresponding booleans.
			equalOperated = true;
			followOp = true;
			numEqual++;
			calBrain.noOfEquals = numEqual;

			// When first time, equal is pressed, compute using methods in
			// operations listener.
			if (numEqual == 1) {
				ListenToOperationButton operate = new ListenToOperationButton();
				// When there is a sign change immediately before equal, do
				// nothing.
				if (!signChange) {
					operate.checkNumOfOperandAndPerform();
				} else {
					operate.isSignChange(e);
				}

				// If equal pressed after square root, compute the square root.
				if (isSquareRoot) {
					numEqual++;
				} else {
					// Transfer control to temp4 and temp5 for contiguous equal
					// presses.
					calBrain.temp5 = eqVal;
				}
				// Reset the operands.
				calBrain.temp = 0;
				calBrain.temp2 = 0;
			}

			// Press equal contiguously more than once.
			if (numEqual > 1) {
				String display;
				String addToDisplay = "";
				display = jtfResult.getText();
				calBrain.temp4 = Double.parseDouble(display);
				if (addVal) {
					calBrain.chooseOperator = 1;
					//addToDisplay = calBrain.add();
				} else if (subVal) {
					calBrain.chooseOperator = 2;
					//addToDisplay = calBrain.subtract();
				} else if (mulVal) {
					calBrain.chooseOperator = 3;
					//addToDisplay = calBrain.multiply();
				} else if (divVal) {
					calBrain.chooseOperator = 4;
					//addToDisplay = calBrain.divide();
					try {
						calBrain.temp2 = Double.parseDouble(addToDisplay);
					} catch (Exception exp) {
					}
				} else if (isSquareRoot) {
					calBrain.temp6 = calBrain.temp4;
					numEqual--;
					//addToDisplay = calBrain.sqrt();
					calBrain.chooseOperator = 5;
				} else {
					addToDisplay = display;
					jtfResult.setText(addToDisplay);
					return;
				}
				addToDisplay = calBrain.equals();
				jtfResult.setText(addToDisplay);
			}

		}
	}

	class ListenToUnaryOperations implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Check if display contains error.
			if (!checkDisplay()) {
				return;
			}

			// Set corresponding values.
			jbtClear.setText("Clear");
			isSquareRoot = false;
			numEqual = 0;
			calBrain.noOfEquals = numEqual;
			equalOperated = false;
			// Get the display.
			String display;
			display = jtfResult.getText();
			calBrain.temp6 = Double.parseDouble(display);

			// Check the source and perform the operation.
			String addToDisplay = checkSource(e);

			// Display the result.
			jtfResult.setText(addToDisplay);
			followOp = true;

		}

		/**
		 * Check the source and perform the computation.
		 * 
		 * @param e
		 * @return
		 */
		String checkSource(ActionEvent e) {
			String addToDisplay = "";
			if (e.getSource() == jbtSquRoot) {
				isSquareRoot = true;
				addVal = false;
				subVal = false;
				mulVal = false;
				divVal = false;
				calBrain.chooseOperator = 5;
				addToDisplay = calBrain.sqrt();
			} else if (e.getSource() == jbtPer) {
				addToDisplay = calBrain.percent();
			} else if (e.getSource() == jbtMulInv) {
				addToDisplay = calBrain.invert();
			} else if (e.getSource() == jbtSignChange) {
				signChange = true;
				addToDisplay = calBrain.changeSign();
			}
			return addToDisplay;
		}
	}

	class ListenToClear implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Reset everything once All Clear pressed except memory.
			if (jbtClear.getText().equals("All Clear") || equalOperated) {
				addVal = false;
				subVal = false;
				mulVal = false;
				divVal = false;
				isSquareRoot = false;
				calBrain.temp = 0;
				calBrain.temp2 = 0;
				calBrain.temp4 = 0;
				calBrain.temp5 = 0;
				calBrain.temp6 = 0;
				numEqual = 0;
				calBrain.noOfEquals = numEqual;
				jbtAdd.setEnabled(true);
				jbtSubtract.setEnabled(true);
				jbtMultiply.setEnabled(true);
				jbtDivide.setEnabled(true);
			}

			// If set being displayed is clear when button is clicked, change it
			// to "All Clear".
			jbtClear.setText("All Clear");
			String addToDisplay = "";
			addToDisplay = calBrain.clear();
			equalOperated = false;
			// Perform color changes to binary operators.
			performOnClickColorChange(e);

			// Display the result ("0").
			jtfResult.setText(addToDisplay);
		}
	}

	class ListenToMemoryButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Check if display contains error.
			if (!checkDisplay()) {
				return;
			}

			// Extract the display.
			String display;
			String addToDisplay = "";
			display = jtfResult.getText();
			calBrain.temp3 = Double.parseDouble(display);

			// See the button pressed and perform appropriate operation.
			if (e.getSource() == jbtMR) {
				addToDisplay = calBrain.memRead();
				jtfResult.setText(addToDisplay);

			} else {
				if (e.getSource() == jbtMC) {
					jbtMR.setBackground(Color.ORANGE);
					addToDisplay = calBrain.memClear();
				} else if (e.getSource() == jbtMadd) {
					jbtMR.setBackground(Color.RED);
					addToDisplay = calBrain.memPlus();
				} else if (e.getSource() == jbtMminus) {
					jbtMR.setBackground(Color.RED);
					addToDisplay = calBrain.memMinus();
				}
				jtfResult.setText(display + addToDisplay);

			}

			followOp = true;
		}
	}

}
