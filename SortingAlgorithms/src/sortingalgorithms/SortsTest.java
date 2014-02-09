package sortingalgorithms;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class SortsTest {
	// initialize four test cases
	private final int[] caseOne = new int[] {};
	private final int[] caseTwo = new int[] { 1 };
	private final int[] caseThree = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };

	private int[] caseFour = new int[] { 2, 54, 32, 13, 67, 88, 990, 53, 23,
			56, 89, 23, 12 };

	// The four test methods below are in the same structure
	// and have four test cases for each

	@Test
	public void testBubbleSort() {
		// case when array is empty
		Sorts.bubbleSort(caseOne);
		assertTrue(Arrays.equals(new int[] {}, caseOne));
		// case when array's length is 1
		Sorts.bubbleSort(caseTwo);
		assertTrue(Arrays.equals(new int[] { 1 }, caseTwo));
		// general case involved with random method
		Sorts.randomize(caseThree);
		Sorts.bubbleSort(caseThree);
		int[] testThree = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertTrue(Arrays.equals(testThree, caseThree));
		// general case without random method
		Sorts.bubbleSort(caseFour);
		int[] testFour = { 2, 12, 13, 23, 23, 32, 53, 54, 56, 67, 88, 89, 990 };
		assertTrue(Arrays.equals(testFour, caseFour));

	}

	@Test
	public void testInsertionSort() {
		Sorts.insertionSort(caseOne);
		assertTrue(Arrays.equals(new int[] {}, caseOne));

		Sorts.insertionSort(caseTwo);
		assertTrue(Arrays.equals(new int[] { 1 }, caseTwo));

		Sorts.randomize(caseThree);
		Sorts.insertionSort(caseThree);
		int[] testThree = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertTrue(Arrays.equals(testThree, caseThree));

		Sorts.insertionSort(caseFour);
		int[] testFour = { 2, 12, 13, 23, 23, 32, 53, 54, 56, 67, 88, 89, 990 };
		assertTrue(Arrays.equals(testFour, caseFour));
	}

	@Test
	public void testSelectionSort() {
		Sorts.selectionSort(caseOne);
		assertTrue(Arrays.equals(new int[] {}, caseOne));

		Sorts.selectionSort(caseTwo);
		assertTrue(Arrays.equals(new int[] { 1 }, caseTwo));

		Sorts.randomize(caseThree);
		Sorts.selectionSort(caseThree);
		int[] testThree = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertTrue(Arrays.equals(testThree, caseThree));

		Sorts.selectionSort(caseFour);
		int[] testFour = { 2, 12, 13, 23, 23, 32, 53, 54, 56, 67, 88, 89, 990 };
		assertTrue(Arrays.equals(testFour, caseFour));
	}

	@Test
	public void testQuicksort() {
		Sorts.quicksort(caseOne);
		assertTrue(Arrays.equals(new int[] {}, caseOne));

		Sorts.quicksort(caseTwo);
		assertTrue(Arrays.equals(new int[] { 1 }, caseTwo));

		Sorts.randomize(caseThree);
		Sorts.quicksort(caseThree);
		int[] testThree = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertTrue(Arrays.equals(testThree, caseThree));

		Sorts.quicksort(caseFour);
		int[] testFour = { 2, 12, 13, 23, 23, 32, 53, 54, 56, 67, 88, 89, 990 };
		assertTrue(Arrays.equals(testFour, caseFour));
	}

	@Test
	public void testRandomize() {
		Sorts.randomize(caseThree);
		int[] testThree = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertFalse(Arrays.equals(testThree, caseThree));
	}

	@Test
	public void testInnertimmer() {
		Sorts.innerTimer(0, caseThree);
		int[] testThree = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertTrue(Arrays.equals(testThree, caseThree));
		Sorts.innerTimer(1, caseThree);
		assertTrue(Arrays.equals(testThree, caseThree));
		Sorts.innerTimer(2, caseThree);
		assertTrue(Arrays.equals(testThree, caseThree));
		Sorts.innerTimer(3, caseThree);
		assertTrue(Arrays.equals(testThree, caseThree));
	}

	@Test
	public void testOuttertimer() {

		int[] testThree = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Sorts.outterTimer(0, caseThree);
		assertTrue(Arrays.equals(testThree, caseThree));
		Sorts.outterTimer(1, caseThree);
		assertTrue(Arrays.equals(testThree, caseThree));
		Sorts.outterTimer(2, caseThree);
		assertTrue(Arrays.equals(testThree, caseThree));
		Sorts.outterTimer(3, caseThree);
		assertTrue(Arrays.equals(testThree, caseThree));
	}

	@Test
	public void testCalrandom() {
		Sorts.calRandom(caseThree);
		int[] testThree = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertFalse(Arrays.equals(testThree, caseThree));
	}

	@Test
	public void testCalculatetime() {
		assertTrue(Sorts.calculateTime().getClass().isArray());
		long[] result = Sorts.calculateTime();
		for (int i = 0; i < 4; i++) {
			assertTrue(result[3 * i] >= result[3 * i + 1]);
			assertTrue(result[3 * i + 1] >= result[3 * i + 2]);
		}
	}

}
