// This series of classes builds off of lessons learned from 
// Object-Oriented Data Structures Using Java by Dale, Joyce, and Weems.

package sortingrelearned;

import java.util.*;
import java.text.DecimalFormat;

public class Sorts 
{
	static int SIZE;
	static int comparisonCount = 0;
	static int[] values;
	
	static void initValues(int yourSize)
	// Initializes the values array with random integers from 0 to 99.
	{
		SIZE = yourSize;
		values = new int[SIZE];
		Random rand = new Random();
		for (int index = 0; index < SIZE; index++)
			values[index] = Math.abs(rand.nextInt()) % 100;
	}
	
	static public boolean isSorted()
	// Returns true if the array values are sorted, and false otherwise.
	{
		boolean sorted = true;
		for (int index = 0; index < (SIZE - 1); index++)
			if (values[index] > values[index + 1])
				sorted = false;
		return sorted;
	}
	
	static public void swap(int index1, int index2)
	// Precondition: index1 and index2 are >= 0 and < SIZE.
	//
	// Swaps the integers at locations index1 and index2 of the values array.
	{
		int temp = values[index1];
		values[index1] = values[index2];
		values[index2] = temp;
	}
	
	static public void printValues()
	// Prints all the values integers.
	{
		int value;
		DecimalFormat fmt = new DecimalFormat("00");
		System.out.println("The values array is:");
		for (int index = 0; index < SIZE; index++)
		{
			value = values[index];
			if (((index + 1) % 10) == 0)
				System.out.println(fmt.format(value));
			else
				System.out.print(fmt.format(value) + " ");
		}
		System.out.println();
	}
	
	// Selection Sort
	static int minIndex(int startIndex, int endIndex)
	// Returns the index of the smallest value in 
	// values[startIndex] to values[endIndex].
	{
		int indexOfMin = startIndex;
		for (int index = startIndex + 1; index <= endIndex; index++){
			comparisonCount++; // NOT FROM BOOK
			if (values[index] < values[indexOfMin])
				indexOfMin = index;
		}
		return indexOfMin;
	}
	
	static void selectionSort()
	// Sorts the values array using the selection sort algorithm.
	{
		int endIndex = SIZE - 1;
		for (int current = 0; current < endIndex; current++)
			swap(current,minIndex(current, endIndex));
	}
	
	//----------------------------------------------------------//
	
	// Bubble Sort
	static void bubbleSort()
	{
		int current = 0;
		while (current  < (SIZE -1)){
			bubbleUp(current, SIZE - 1);
			current++;
		}
	}
	
	static void bubbleUp(int startIndex, int endIndex)
	{
		for (int index = endIndex; index > startIndex; index--){
			comparisonCount++; // NOT FROM BOOK
			if (values[index] < values[index - 1])
				swap(index, index-1);
		}
	}
	
	//----------------------------------------------------------//
	
	// Insertion Sort
	static void insertionSort()
	{
		for (int count = 1; count < SIZE; count++)
			insertElement(0, count);
	}
	
	static void insertElement(int startIndex, int endIndex)
	{
		boolean finished = false;
		int current = endIndex;
		boolean moreToSearch = true;
		while (moreToSearch && !finished){
			comparisonCount++;
			if (values[current] < values[current - 1]){
				swap(current, current - 1);
				current--;
				moreToSearch = (current != startIndex);
			}
			else
				finished = true;
		}
	}
	
	//----------------------------------------------------------//
	
	// Merge Sort
	static void mergeSort(int first, int last)
	{
		if (first < last){
			int middle = (first + last) / 2;
			mergeSort(first, middle);
			mergeSort(middle + 1, last);
			merge(first, middle, middle + 1, last);
		}
	}
	
	static void merge (int leftFirst, int leftLast, int rightFirst, int rightLast)
	// Preconditions: values[leftFirst] to values[leftLast] are sorted.
	//				  values[rightFirst] to values[rightLast] are sorted.
	{
		int[] tempArray = new int[SIZE];
		int index = leftFirst;
		int saveFirst = leftFirst; // to remember where to copy back
		
		while ((leftFirst <= leftLast) && (rightFirst <= rightLast)){
			comparisonCount++;
			if (values[leftFirst] < values[rightFirst]){
				tempArray[index] = values[leftFirst];
				leftFirst++;
			}
			else{
				tempArray[index] = values[rightFirst];
				rightFirst++;	
			}
			index++;
		}
		
		while (leftFirst <= leftLast){ // Copy remaining elements from left half.
			tempArray[index] = values[leftFirst];
			leftFirst++;
			index++;
		}
		
		while (rightFirst <= rightLast){ // Copy remaining elements from right half.
			tempArray[index] = values[rightFirst];
			rightFirst++;
			index++;
		}
		
		for (index = saveFirst; index <= rightLast; index++)
			values[index] = tempArray[index];
	}
	
	//-----------------------------------------------------------//
	
	static boolean binarySearch(int target, int start, int end)
	/*NOT FROM BOOK*/
	{
		int middle = ((start + end) / 2);
		if(values[middle] == target){
			comparisonCount++; // NOT FROM BOOK
			return true;
		}
		else{
			while((start + end) / 2 != start){
				comparisonCount++;
				if(target > values[middle])
					return binarySearch(target, middle, end);
				if(target < values[middle])
					return binarySearch(target, start, middle);
			}
		}
		return false;
	}
	
	static boolean iterativeSearch(int target, int startIndex, int endIndex)
	/*NOT FROM BOOK*/
	{
		resetCount();
		for(int index = startIndex; index <= endIndex; index++){
			comparisonCount++;
			if(values[index] == target)
				return true;
		}
		return false;
			
	}
	
	static void resetCount()
	/*NOT FROM BOOK*/
	{
		comparisonCount = 0;
	}
	
	static void shuffleValues()
	/*NOT FROM BOOK*/
	{
		Random rand = new Random();
		for(int i = 0; i < SIZE; i++){
			swap(i, Math.abs(rand.nextInt())%SIZE);
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println("~~~Welcome to Sorting Relearned!~~~");
		System.out.println("Here you can use some sorting algorithms to sort an array of integers which you can then search using binary search!");
		System.out.println("Enter an integer indicating how large you'd like the array to be.");
		Scanner scan = new Scanner(System.in);
		try{
			initValues(scan.nextInt());
		} catch (Exception e) {
			System.out.println("You didn't follow the rules... C'mon.");
		}
		System.out.println("Initiating an array called \"Values\" of size " + SIZE + " with random integers 0 to 99...\n");
		printValues();
		System.out.println("Values array initiated!");
		System.out.println("Values is sorted: " + isSorted());
		System.out.println();
		
		boolean endApp = false;
		while(!endApp){
			System.out.print("What do you want to do?: bubbleSort, selectionSort, insertionSort, shuffleValues, binarySearch, mergeSort.\n"
					+ "Enter here: ");
			resetCount();
			String scannedSort = scan.nextLine().trim();
			System.out.println();
			// bubbleSort
			if (scannedSort.equalsIgnoreCase("bubbleSort")){
				bubbleSort();
				System.out.println("bubbleSort called.\n");
				System.out.println("Values is sorted: " + isSorted());
				System.out.println("Number of comparisons from sort: " + comparisonCount + "\n");
			}
			// selectionSort
			else if (scannedSort.equalsIgnoreCase("selectionSort")){
				selectionSort();
				System.out.println("selectionSort called.\n");
				System.out.println("Values is sorted: " + isSorted());
				System.out.println("Number of comparisons from sort: " + comparisonCount + "\n");
			}
			// insertionSort
			else if (scannedSort.equalsIgnoreCase("insertionSort")){
				insertionSort();
				System.out.println("insertionSort called.\n");
				System.out.println("Values is sorted: " + isSorted());
				System.out.println("Number of comparisons from sort: " + comparisonCount + "\n");
			}
			// mergeSort
			else if (scannedSort.equalsIgnoreCase("mergeSort")){
				mergeSort(0, SIZE-1);
				System.out.println("mergeSort called.\n");
				System.out.println("Values is sorted: " + isSorted());
				System.out.println("Number of comparisons from sort: " + comparisonCount + "\n");
			}
			// **UNSUPPORTED** quickSort
			else if (scannedSort.equalsIgnoreCase("quickSort")){
				System.out.println("quickSort is currently unsupported. Try back next time!\n");
			}
			// shuffleValues
			else if (scannedSort.equalsIgnoreCase("shuffleValues")){
				System.out.println("shuffleValues called.\n");
				shuffleValues();
				printValues();
				System.out.println("Values is sorted: " + isSorted() + "\n");
			}
			// binarySearch
			else if (scannedSort.equalsIgnoreCase("binarySearch")){
				System.out.println("binarySearch called.\n");
				if (!isSorted()){
					System.out.println("ERROR: Cannot use binarySearch on an unsorted array!\n");
				}
				else{
					System.out.print("Please search for an integer in the array between 0 and 99.\n"
							+ "Enter here: ");
					int scannedInt = scan.nextInt();
					resetCount();
					if(binarySearch(scannedInt, 0, SIZE-1))
						System.out.println("\""+ scannedInt + "\" was found in the array.");
					else
						System.out.println("\"" + scannedInt + "\" was not found in the array.");
					System.out.println("Number of comparisons on binarySearch: " + comparisonCount);
					iterativeSearch(scannedInt, 0, SIZE-1);
					System.out.println("Number of comparisons on iterativeSearch: " + comparisonCount + "\n");
				}
			}
			// User desires to end application.
			else if(scannedSort.equalsIgnoreCase("END") || scannedSort.equalsIgnoreCase("QUIT")){
				System.out.println("Goodbye!");
				endApp = true;
			}
			else{
				System.out.println("ERROR: Invalid input: " + scannedSort + "\n");
			}
		}
	}

}
