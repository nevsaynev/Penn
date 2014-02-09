package calculator;

/**
 * This class acts like the model for the mvc development Each method returns a
 * String to be shown on the display of the calculator
 * 
 * @author Shallav and Dong
 * 
 */

public class CalculatorChip {
	// For normal two operands calculation
	double temp = 0;
	double temp2 = 0;
	// For special cases when several operators interfere with each other
	double temp4 = 0;
	double temp5 = 0;
	// For normal one operand calculation
	double temp6 = 0;
	// number of times equals is pressed contiguously
	int noOfEquals;

	// For memory calculation
	double memory = 0;
	double temp3 = 0;
	
	// For equal operation
	int chooseOperator;

	/**
	 * Setters and getters for test
	 * @return
	 */
	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getTemp2() {
		return temp2;
	}

	public void setTemp2(double temp2) {
		this.temp2 = temp2;
	}

	public double getTemp4() {
		return temp4;
	}

	public void setTemp4(double temp4) {
		this.temp4 = temp4;
	}

	public double getTemp5() {
		return temp5;
	}

	public void setTemp5(double temp5) {
		this.temp5 = temp5;
	}

	public double getTemp6() {
		return temp6;
	}

	public void setTemp6(double temp6) {
		this.temp6 = temp6;
	}

	public int getNoOfEquals() {
		return noOfEquals;
	}

	public void setNoOfEquals(int noOfEquals) {
		this.noOfEquals = noOfEquals;
	}

	public double getTemp3() {
		return temp3;
	}

	public void setTemp3(double temp3) {
		this.temp3 = temp3;
	}

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}
	/**
	 * Clear the display for a new set of calculation
	 * 
	 * @return
	 */
	String clear() {
		return "0";
	}

	/**
	 * Clear the memory without changing the display
	 * 
	 * @return
	 */
	String memClear() {
		memory = 0;
		return "";
	}

	/**
	 * Return what is in the memory and display it
	 * 
	 * @return
	 */
	String memRead() {
		return (Double.toString(memory));
	}

	/**
	 * Add what is on the display to memory without changing display
	 * 
	 * @return
	 */
	String memPlus() {
		memory = memory + temp3;
		return "";
	}

	/**
	 * Subtract what is on the display from memory without changing display
	 * 
	 * @return
	 */
	String memMinus() {
		memory = memory - temp3;
		return "";
	}

	/**
	 * return the number of each number button
	 * 
	 * @param digit
	 * @return
	 */
	String digit(int digit) {
		return ("" + digit);
	}

	/**
	 * return dot
	 * 
	 * @return
	 */
	String decimalPoint() {
		return ".";
	}

	/**
	 * Perform addition of two numbers
	 * 
	 * @return
	 */
	String add() {

		double addRes;
		// Normal addition without = calculation
		if (noOfEquals <= 1)
			addRes = temp + temp2;
		// Cases when more than one = is performed
		else
			addRes = temp4 + temp5;
		return (Double.toString(addRes));
	}

	/**
	 * Perform subtraction of two numbers
	 * 
	 * @return
	 */
	String subtract() {

		double addRes;
		if (noOfEquals <= 1)
			addRes = temp - temp2;
		else
			addRes = temp4 - temp5;
		return (Double.toString(addRes));
	}

	/**
	 * Perform multiplication of two numbers
	 * 
	 * @return
	 */
	String multiply() {

		double addRes;
		if (noOfEquals <= 1)
			addRes = temp * temp2;
		else
			addRes = temp4 * temp5;
		return (Double.toString(addRes));
	}

	/**
	 * Perform division of two numbers
	 * 
	 * @return
	 */
	String divide() {

		double addRes;
		if (noOfEquals <= 1) {
			// divisor cannot be 0
			if (temp2 == 0)
				return "Error";
			addRes = temp / temp2;
		} else {
			// divisor cannot be 0
			if (temp5 == 0)
				return "Error";
			addRes = temp4 / temp5;
		}
		return (Double.toString(addRes));

	}

	/**
	 * Do nothing and return nothing
	 * 
	 * @return
	 */
	String equals() {
		String result;
		switch (chooseOperator) {
		case 1 :
			result = add();
			break;
		case 2 :
			result = subtract() ;
			break;
		case 3 :
			result = multiply();
			break;
		case 4 :
			result = divide();
			break;
		case 5 :
			result = sqrt();
			break;
		default :
			result = "";
		}
		return result;
	}
/**
 * Calculate square root of the number on display
 * @return
 */
	String sqrt() {
		//negative number has no square root
		if (temp6 < 0)
			return "Error";
		else
			return (Double.toString(Math.sqrt(temp6)));

	}
/**
 * Change the number on display into percentage 
 * @return
 */
	String percent() {

		return (Double.toString(temp6 / 100));
	}
/**
 * Return the multiply inverse of the number
 * @return
 */
	String invert() {
		if (temp6 == 0)
			return "Error";
		else
			return (Double.toString(1 / temp6));
	}
/**
 * Change sign of the number and display it
 * @return
 */
String changeSign() {
		return Double.toString(temp6 * -1);
	}

}
