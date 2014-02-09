/**
 * 
 */
package sortingalgorithms;

/**
 * Timing four common sort methods:Bubblesort, insertionsort, selectionsort and
 * quicksort
 * 
 * @author Dong Yan
 * 
 */
public class Sorts {
	/**
	 * main method of the class just print the result
	 */
	public static void main(String[] args) {
		long result[] = calculateTime();
		// print the results in a tablet
		System.out.println("\t\tN\t\t1/2N\t\t1/4N\t");
		System.out.println("bubbleSort\t" + result[0] + "\t\t" + result[1]
				+ "\t\t" + result[2]);
		System.out.println("insertionSort\t" + result[3] + "\t\t" + result[4]
				+ "\t\t" + result[5]);
		System.out.println("selectionSort\t" + result[6] + "\t\t" + result[7]
				+ "\t\t" + result[8]);
		System.out.println("quickSort\t" + result[9] + "\t\t" + result[10]
				+ "\t\t" + result[11]);
		// System.out.println("done");

	}

	/**
	 * help method calculate time
	 * 
	 * @return array of time calculated(long int)
	 */
	public static long[] calculateTime() {
		// initialize the three sample arrays
		// the size of each is half of its previous
		int[] caseN = new int[1000];
		for (int i = 0; i < 1000; i++) {
			caseN[i] = i;
		}
		int[] caseN2 = new int[500];
		for (int i = 0; i < 500; i++) {
			caseN2[i] = i;
		}
		int[] caseN4 = new int[250];
		for (int i = 0; i < 250; i++) {
			caseN4[i] = i;
		}

		long[] result = new long[12];
		// calculate running time for bubblesort of the three sample arrays
		result[0] = (outterTimer(0, caseN) - calRandom(caseN)) / 1000;
		result[1] = (outterTimer(0, caseN2) - calRandom(caseN2)) / 1000;
		result[2] = (outterTimer(0, caseN4) - calRandom(caseN4)) / 1000;
		// calculate running time for insertionsort of the three sample arrays
		result[3] = (outterTimer(1, caseN) - calRandom(caseN)) / 1000;
		result[4] = (outterTimer(1, caseN2) - calRandom(caseN2)) / 1000;
		result[5] = (outterTimer(1, caseN4) - calRandom(caseN4)) / 1000;
		// calculate running time for selectionsort of the three sample arrays
		result[6] = (outterTimer(2, caseN) - calRandom(caseN)) / 1000;
		result[7] = (outterTimer(2, caseN2) - calRandom(caseN2)) / 1000;
		result[8] = (outterTimer(2, caseN4) - calRandom(caseN4)) / 1000;
		// calculate running time for quicksort of the three sample arrays
		result[9] = (outterTimer(3, caseN) - calRandom(caseN)) / 1000;
		result[10] = (outterTimer(3, caseN2) - calRandom(caseN2)) / 1000;
		result[11] = (outterTimer(3, caseN4) - calRandom(caseN4)) / 1000;

		return result;
	}

	/**
	 * Randomizes an array in place.
	 * 
	 * @param array
	 *            The array to be randomized (shuffled).
	 */
	public static void randomize(int[] array) {
		java.util.Random rand = new java.util.Random();
		for (int i = array.length; i > 1; i--) {
			int choice = rand.nextInt(i);
			int temp = array[choice];
			array[choice] = array[i - 1];
			array[i - 1] = temp;
		}
	}

	/**
	 * Sorting an array of integer using bubble sort
	 * 
	 * @author David Matuszek
	 * @param a
	 *            : array of integers
	 */
	public static void bubbleSort(int[] a) {
		int outer, inner = 0;
		// counting down
		for (outer = a.length - 1; outer > 0; outer--) {
			// bubbling up
			for (inner = 0; inner < outer; inner++) {
				if (a[inner] > a[inner + 1]) {
					// if out of order, swamp the two int
					int temp = a[inner];
					a[inner] = a[inner + 1];
					a[inner + 1] = temp;
				}
			}
		}
	}

	/**
	 * Sorting an array of integer using insertion sort
	 * 
	 * @author 
	 *         http://www.java2novice.com/java-interview-programs/insertion-sort/
	 * @param array
	 *            : an array of integers
	 */

	public static void insertionSort(int array[]) {
		int n = array.length;
		for (int j = 1; j < n; j++) {
			int key = array[j];
			int i = j - 1;
			while ((i > -1) && (array[i] > key)) {
				array[i + 1] = array[i];
				i--;
			}
			array[i + 1] = key;
		}
	}

	/**
	 * Sorting an array of integer using selection sort
	 * 
	 * @author http://java2novice.com/java-sorting-algorithms/selection-sort/
	 * @param arr
	 *            :an array of integers
	 */
	public static void selectionSort(int[] arr) {

		for (int i = 0; i < arr.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < arr.length; j++)
				if (arr[j] < arr[index])
					index = j;

			int smallerNumber = arr[index];
			arr[index] = arr[i];
			arr[i] = smallerNumber;
		}

	}

	static int[] numbers1;
	static int number;

	/**
	 * Sorting an array of integer by calling quickSort1 add a checker to check
	 * if the array is empty
	 * 
	 * @author Lars Vogel
	 * @param values
	 *            :an array of integers
	 */
	public static void quicksort(int[] values) {

		// check for empty or null array
		if (values == null || values.length == 0) {
			return;
		}
		numbers1 = values;
		number = values.length;
		quickSort1(0, number - 1);
	}

	/**
	 * Sorting an non-empty array of integers using quick sort
	 * 
	 * @param low
	 *            : index of the lower bound of the segment to be sorted
	 * @param high
	 *            : index of the higher bound of the segment to be sorted
	 */
	public static void quickSort1(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		int pivot = numbers1[low + (high - low) / 2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (numbers1[i] < pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (numbers1[j] > pivot) {
				j--;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (low < j)
			quickSort1(low, j);
		if (i < high)
			quickSort1(i, high);
	}

	/**
	 * This is a help method exchange two integers in the array
	 * 
	 * @author anonymous
	 * @param i
	 *            : index of one of the integer
	 * @param j
	 *            : index of the other integer
	 */
	public static void exchange(int i, int j) {
		int temp = numbers1[i];
		numbers1[i] = numbers1[j];
		numbers1[j] = temp;
	}

	/**
	 * This is a help method randomize the array, choose a sort method and then
	 * sort
	 * 
	 * @param method
	 *            : choice of sorting method 0 represents bubblesort; 1
	 *            represents insertionsort; 2 represents selectionsort; 3
	 *            represents quicksort
	 * @param array
	 *            : array to be sorted
	 */
	public static void innerTimer(int method, int[] array) {
		randomize(array);
		switch (method) {
		case 0:
			bubbleSort(array);
			break;
		case 1:
			insertionSort(array);
			break;
		case 2:
			selectionSort(array);
			break;
		case 3:
			quicksort(array);
			break;
		}

	}

	/**
	 * Call innerTimer for a large amount of times and record the running time
	 * 
	 * @param method
	 *            choice of sorting method
	 * @param array
	 *            array to be sorted
	 * @return time elapsed in total
	 */
	public static long outterTimer(int method, int[] array) {
		System.gc();
		long startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			innerTimer(method, array);
		}
		long elapsedTime = System.nanoTime() - startTime;
		return elapsedTime;
	}

	/**
	 * Call randomize for the same amount of times and record the running time
	 * 
	 * @param array
	 *            array to be randomized
	 * @return time elapsed only for randomization
	 */
	public static long calRandom(int[] array) {
		System.gc();
		long startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++) {
			randomize(array);
		}
		long elapsedTime = System.nanoTime() - startTime;
		return elapsedTime;
	}
}
