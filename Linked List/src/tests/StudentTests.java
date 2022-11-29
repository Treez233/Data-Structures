package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import listClasses.BasicLinkedList;
import listClasses.SortedLinkedList;

/**
 * 
 * You need student tests if you are looking for help during office hours about
 * bugs in your code.
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {

	@Test
	public void testAddToEndFrontAndGetSize() {
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		basicList.addToEnd("Red").addToFront("Yellow").addToFront("Blue");
		String product = "Entries: ";
		for(String entry: basicList) {
			product += entry + " ";
		}
		product = product.trim();
		String correct = "Entries: Blue Yellow Red";
		System.out.println(product + "\n" + correct);
		assertTrue(product.equals(correct) && basicList.getSize() == 3);
	}
	@Test
	public void testgetFirstLast() {
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		basicList.addToEnd("Red").addToFront("Yellow").addToFront("Blue").addToEnd("Green");
		System.out.println("First: " +basicList.getFirst() + " Last: "+ basicList.getLast());
		assertTrue(basicList.getFirst().equals("Blue") && basicList.getLast().equals("Green"));
	}
	@Test
	public void testRetieveFirstLast() {
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		basicList.addToEnd("Red").addToFront("Yellow").addToFront("Blue").addToEnd("Green");
		int listSizeBefore = basicList.getSize();
		String first = basicList.retrieveFirstElement();
		String last = basicList.retrieveLastElement();
		System.out.println("Retrieved first: " +first+ " Retrieved last: "+ last);
		int listSizeAfter = basicList.getSize();
		System.out.println("Size before: " + listSizeBefore + " Size after: " + listSizeAfter);
		assertTrue(first.equals("Blue") &&
				   last.equals("Green") && listSizeAfter == basicList.getSize());
	}
	@Test
	public void testIteratorAndReverseList() {
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		basicList.addToEnd("Red").addToFront("Yellow").addToFront("Blue").addToEnd("Green");
		String list = "";
		for(String entry : basicList) {
			list += entry + " ";
		}
		System.out.println(list.trim());
		String listReversed ="";
		for(String entry: basicList.getReverseList()) {
			listReversed += entry + " ";
		}
		System.out.println(listReversed.trim());
		String result = "Green Red Yellow Blue";
		assertTrue(result.trim().equals(listReversed.trim()));
	}
	@Test
	public void testgetReverseArrayList() {
		BasicLinkedList<String> basicList = new BasicLinkedList<String>();
		basicList.addToEnd("Red").addToFront("Yellow").addToFront("Blue");
		ArrayList<String> original = new ArrayList<String>();
		original.add("Blue");
		original .add("Yellow");
		original .add("Red");
		ArrayList<String> reversed = basicList.getReverseArrayList();
		ArrayList<String> reversedResult = new ArrayList<String>();
		reversedResult.add("Red");
		reversedResult.add("Yellow");
		reversedResult.add("Blue");
		System.out.println(original.toString());
		System.out.println(reversed.toString());
		assertTrue(reversed.equals(reversedResult));
	}
	
	@Test
	public void testSortedAddRemove() {
		SortedLinkedList<String> sortedList = new SortedLinkedList<String>(String.CASE_INSENSITIVE_ORDER);
		sortedList.add("Apple").add("Cucumber").add("Dolphin").add("Banana");
		String list = "";
		for(String entry : sortedList) {
			list += entry + " ";
		}
		System.out.println(list);
		sortedList.remove("Dolphin");
		String afterRemove = "";
		for(String entry : sortedList) {
			afterRemove += entry + " ";
		}
		System.out.println(afterRemove);
		assertTrue(afterRemove.contains("Dolphin") == false);
	}
	


}
