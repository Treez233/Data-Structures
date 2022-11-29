package tests;

import listClasses.BasicLinkedList;
import listClasses.SortedLinkedList;

public class SampleDriver {

	public static void main(String[] args) {
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();

		basicList.addToEnd("Red").addToFront("Yellow").addToFront("Blue");
		System.out.println("First: " + basicList.getFirst());
		System.out.println("Last: " + basicList.getLast());
		System.out.println("Size: " + basicList.getSize());
		System.out.println("Retrieve First: " + basicList.retrieveFirstElement());
		System.out.println("Retrieve Last: " + basicList.retrieveLastElement());
		System.out.println("Removing Red");
		basicList.remove("Red", String.CASE_INSENSITIVE_ORDER);
		System.out.print("Iteration: ");
		for (String entry : basicList) {
			System.out.print(entry + " ");
		}

		SortedLinkedList<String> sortedList = new SortedLinkedList<String>(String.CASE_INSENSITIVE_ORDER);
		sortedList.add("Yellow").add("Red");
		System.out.print("\n\nIteration (for sorted list): ");
		for (String entry : sortedList) {
			System.out.print(entry + " ");
		}
		sortedList.remove("Red");
		System.out.print("\nAfter remove in sorted list first is: ");
		System.out.println(sortedList.getFirst());
	}
}
