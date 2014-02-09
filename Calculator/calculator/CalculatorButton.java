/**
 * 
 */
package calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

/**
 * @author Dong Yan and Shallav Varma
 * 
 */
public abstract class CalculatorButton extends JButton {

	
	public CalculatorButton(CalculatorChip CalOp) {
		this.setPreferredSize(new Dimension(60, 60));
		this.setFont(new Font("Times New Roman", Font.BOLD, 20));
	}

	/**
	 * Constructor for passing text to be displayed on button and creating the
	 * button.
	 * 
	 * @param string
	 */
	public CalculatorButton(String string) {
		super(string);
	}

}

class NumberButton extends CalculatorButton {

	public NumberButton(CalculatorChip myCalChip) {
		super(myCalChip);

	}

	public NumberButton(String string) {
		super(string);
		this.setOpaque(true);

	}

}

class MemoryButton extends CalculatorButton {

	public MemoryButton(CalculatorChip myCalChip) {
		super(myCalChip);

	}

	public MemoryButton(String string) {
		super(string);
		this.setOpaque(true);
		this.setBackground(Color.ORANGE);

	}

}

class ClearButton extends CalculatorButton {

	public ClearButton(CalculatorChip myCalChip) {
		super(myCalChip);

	}

	public ClearButton(String string) {
		super(string);
		this.setOpaque(true);
		this.setBackground(Color.lightGray);

	}

}

class OperationButton extends CalculatorButton {
	public OperationButton(CalculatorChip myCalChip) {
		super(myCalChip);

	}

	public OperationButton(String string) {
		super(string);
		this.setOpaque(true);
		this.setBackground(Color.GREEN);

	}

	/**
	 * Change background color. 
	 * @param e
	 */
	void onClick(ActionEvent e) {
		if (!this.isEnabled()) {
			this.setBackground(Color.RED);
		} else {
			this.setBackground(Color.GREEN);
		}
	}

}

class EqualButton extends CalculatorButton {
	public EqualButton(CalculatorChip myCalChip) {
		super(myCalChip);
	}

	public EqualButton(String string) {
		super(string);
		this.setOpaque(true);
		this.setBackground(Color.GREEN);
	}
}