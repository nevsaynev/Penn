package calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Shallav and Dong
 * 
 */
public class CalculatorChipTest {
	static CalculatorChip CalTest;
	static double memory;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CalTest = new CalculatorChip();
		memory = CalTest.getMemory();
	}

	@Test
	public void testClear() {
		assertTrue(CalTest.clear().equals("0"));
	}

	@Test
	public void testMemClear() {
		assertTrue(CalTest.memClear().equals(""));
		assertEquals("0.0", Double.toString(memory));

	}

	@Test
	public void testMemRead() {
		assertTrue(CalTest.memRead().equals(
				Double.toString(CalTest.getMemory())));

	}

	@Test
	public void testMemPlus() {
		CalTest.setTemp3(9);
		CalTest.setMemory(0);
		assertTrue(CalTest.memPlus().equals(""));
		assertTrue((Double.toString(CalTest.getMemory())).equals("9.0"));

		CalTest.setTemp3(3.4);
		assertTrue(CalTest.memPlus().equals(""));
		assertTrue((Double.toString(CalTest.getMemory())).equals("12.4"));
	}

	@Test
	public void testMemMinus() {
		CalTest.setTemp3(6.8);
		CalTest.setMemory(0);
		assertTrue(CalTest.memMinus().equals(""));
		assertTrue((Double.toString(CalTest.getMemory())).equals("-6.8"));
	}

	@Test
	public void testDigit() {
		for (int i = 0; i < 10; i++) {
			assertTrue(CalTest.digit(i).equals("" + i));
		}
	}

	@Test
	public void testDecimalPoint() {
		assertTrue(CalTest.decimalPoint().equals("."));
	}

	@Test
	public void testAdd() {
		CalTest.setNoOfEquals(0);
		CalTest.setTemp(7.4);
		CalTest.setTemp2(2.6);
		assertTrue(CalTest.add().equals("10.0"));

		CalTest.setNoOfEquals(1);
		CalTest.setTemp(77.4);
		CalTest.setTemp2(2.6);
		assertTrue(CalTest.add().equals("80.0"));

		CalTest.setNoOfEquals(2);
		CalTest.setTemp4(7.4);
		CalTest.setTemp5(2.6);
		assertTrue(CalTest.add().equals("10.0"));

	}

	@Test
	public void testSubtract() {
		CalTest.setNoOfEquals(0);
		CalTest.setTemp(2.4);
		CalTest.setTemp2(2.6);
		assertTrue(CalTest.subtract().equals("-0.20000000000000018"));

		CalTest.setNoOfEquals(1);
		CalTest.setTemp(2.4);
		CalTest.setTemp2(0.0006);
		assertTrue(CalTest.subtract().equals("2.3994"));

		CalTest.setNoOfEquals(2);
		CalTest.setTemp4(98.0);
		CalTest.setTemp5(6.0);
		assertTrue(CalTest.subtract().equals("92.0"));
	}

	@Test
	public void testMultiply() {
		CalTest.setNoOfEquals(0);
		CalTest.setTemp(2.4);
		CalTest.setTemp2(5);
		assertTrue(CalTest.multiply().equals("12.0"));

		CalTest.setNoOfEquals(1);
		CalTest.setTemp(8.000000);
		CalTest.setTemp2(0.0006);
		assertTrue(CalTest.multiply().equals("0.0048"));

		CalTest.setNoOfEquals(2);
		CalTest.setTemp4(46.0);
		CalTest.setTemp5(2.0);
		assertTrue(CalTest.multiply().equals("92.0"));
	}

	@Test
	public void testDivide() {
		CalTest.setNoOfEquals(0);
		CalTest.setTemp(2.5);
		CalTest.setTemp2(5);
		assertTrue(CalTest.divide().equals("0.5"));
		
		CalTest.setTemp2(0);
		assertTrue(CalTest.divide().equals("Error"));

		CalTest.setNoOfEquals(1);
		CalTest.setTemp(8);
		CalTest.setTemp2(8);
		assertTrue(CalTest.divide().equals("1.0"));

		CalTest.setNoOfEquals(2);
		CalTest.setTemp4(46.0);
		CalTest.setTemp5(2.0);
		assertTrue(CalTest.divide().equals("23.0"));
		CalTest.setTemp5(0);
		assertTrue(CalTest.divide().equals("Error"));
		
		CalTest.setNoOfEquals(0);
		CalTest.setTemp(2.5);
		CalTest.setTemp2(5);
		assertTrue(CalTest.divide().equals("0.5"));

		
	}
	
	

	@Test
	public void testEquals() {
		// If I don't change choose operator. 
		assertTrue(CalTest.equals().equals(""));
		CalTest.chooseOperator = 6;
		assertTrue(CalTest.equals().equals(""));
		
		//Test when operator is divide.
		CalTest.chooseOperator = 4;
		CalTest.setNoOfEquals(0);
		CalTest.setTemp(2.5);
		CalTest.setTemp2(5);
		assertTrue(CalTest.equals().equals("0.5"));
		CalTest.setTemp2(0);
		assertTrue(CalTest.equals().equals("Error"));
		
		// Test when operator is multiply.
		CalTest.chooseOperator = 3;
		CalTest.setNoOfEquals(0);
		CalTest.setTemp(2.4);
		CalTest.setTemp2(5);
		assertTrue(CalTest.equals().equals("12.0"));
		
		//Test when operator is subtract.
		CalTest.chooseOperator = 2;
		CalTest.setNoOfEquals(2);
		CalTest.setTemp4(98.0);
		CalTest.setTemp5(6.0);
		assertTrue(CalTest.equals().equals("92.0"));
		
		//Test when operator is add.
		CalTest.chooseOperator = 1;
		CalTest.setNoOfEquals(2);
		CalTest.setTemp4(7.4);
		CalTest.setTemp5(2.6);
		assertTrue(CalTest.equals().equals("10.0"));
		
		//Test when squareroot.
		CalTest.chooseOperator = 5;
		CalTest.setTemp6(4);
		assertTrue(CalTest.equals().equals("2.0"));
		CalTest.setTemp6(-1);
		assertTrue(CalTest.equals().equals("Error"));
		
	}

	@Test
	public void testSqrt() {
		CalTest.setTemp6(4);
		assertTrue(CalTest.sqrt().equals("2.0"));
		CalTest.setTemp6(0);
		assertTrue(CalTest.sqrt().equals("0.0"));
		CalTest.setTemp6(-1);
		assertTrue(CalTest.sqrt().equals("Error"));
	}

	@Test
	public void testPercent() {
		CalTest.setTemp6(0);
		assertTrue(CalTest.percent().equals("0.0"));
		CalTest.setTemp6(89.8);
		assertTrue(CalTest.percent().equals("0.898"));
		CalTest.setTemp6(-123);
		assertTrue(CalTest.percent().equals("-1.23"));
		
	}

	@Test
	public void testInvert() {
		CalTest.setTemp6(0);
		assertTrue(CalTest.invert().equals("Error"));
		CalTest.setTemp6(8);
		assertTrue(CalTest.invert().equals("0.125"));
		CalTest.setTemp6(-1);
		assertTrue(CalTest.invert().equals("-1.0"));
		
	}

	@Test
	public void testChangeSign() {
		CalTest.setTemp6(0);
		assertTrue(CalTest.changeSign().equals("-0.0"));
		CalTest.setTemp6(-9);
		assertTrue(CalTest.changeSign().equals("9.0"));
	}

}
