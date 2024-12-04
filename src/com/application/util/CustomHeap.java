package com.application.util;

import java.util.ArrayList;
import java.util.List;

public class CustomHeap<T extends Comparable<T>> implements CustomHeapInterface<T> {
	
	private List<T> heap;
	
	public CustomHeap(){
		@SuppressWarnings("unchecked")
		List<T> tempHeap = (List<T>) new ArrayList<Object>();
		heap = tempHeap;
	}
 
	@Override
	public void insert(T val) {
		
		heap.add(val); 

		int currIdx = heap.size() - 1; 
		int parentIdx = (currIdx - 1) / 2;
		
        while (currIdx > 0 && heap.get(currIdx).compareTo(heap.get(parentIdx)) < 0) {
            swap(currIdx, parentIdx); 
            currIdx = parentIdx;
            parentIdx = (currIdx - 1) / 2;
        }
	}

	@Override
	public T remove() {
        T min = heap.get(0); 

        T lastElement = heap.remove(heap.size() - 1); 

        if (!heap.isEmpty()) {
            heap.set(0, lastElement); 

            int currentIndex = 0;
            while (true) {
                int left = 2 * currentIndex + 1;
                int right = 2 * currentIndex + 2;

                int smallest = currentIndex;

                if (left < heap.size() && heap.get(left).compareTo(heap.get(smallest)) < 0)
                    smallest = left;
                
                if (right < heap.size() && heap.get(right).compareTo(heap.get(smallest)) < 0)
                    smallest = right;
                
                if (smallest == currentIndex)
                    break; 

                swap(currentIndex, smallest); 
                currentIndex = smallest; 
            }
        }

        return min;
	}
	
	@Override
	public List<T> toSortedList(){
		List<T> tempHeap = heap;
		
		ArrayList<T> retList = new ArrayList<>();
		
		while(!tempHeap.isEmpty())
			retList.add(remove());
		
		return retList;
	}

	@Override
	public boolean isEmpty() {
		return heap.isEmpty();
	}
	
	private void swap(int i, int j) {
		T temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}
}
