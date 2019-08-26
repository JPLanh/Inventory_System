package Components;

import java.util.*;

public class Sorter<T extends Comparable<T>> {

	public ArrayList<T> mergeSort(ArrayList<T> unsortedList) {
//		System.out.println("Unsorted");
//		for (T item : unsortedList) {
//			if (item instanceof Item) {
//				System.out.println(item.toString());
//			}
//		}
		
		mergeSortHelper(unsortedList);

//		System.out.println("sorted");
//		for (T item : unsortedList) {
//			if (item instanceof Item) {
//				System.out.println(item.toString());
//			}
//		}
		return null;
	}
	
	public ArrayList<T> mergeSortHelper(ArrayList<T> halfList){
		if (halfList.size() == 1) return halfList;
		else if (halfList.size() == 2) {

//			System.out.println("unsorted");
//			for (T item : halfList) {
//				if (item instanceof Item) {
//					System.out.println(item.toString());
//				}
//			}
			
			Collections.sort(halfList);
			
//			System.out.println("sorted");
//			for (T item : halfList) {
//				if (item instanceof Item) {
//					System.out.println(item.toString());
//				}
//			}
			return halfList;
		}
		int mid = halfList.size()/2;
		ArrayList<T> Left = mergeSortHelper(new ArrayList<T>(halfList.subList(0, mid+1)));
		ArrayList<T> Right = mergeSortHelper(new ArrayList<T>(halfList.subList(mid+1, halfList.size())));
		System.out.println("Unsorted");
		for (T item : halfList) {
			if (item instanceof Item) {
				System.out.println(item.toString());
			}
		}
		Left.addAll(Right);
		Collections.sort(Left);
		return Left;
	}
}
