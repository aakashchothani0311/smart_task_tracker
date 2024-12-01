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
            parentIdx = (currIdx - 1) / 2;
            currIdx = parentIdx; 
        }
	}

	@Override
	public T remove() {
		return null;
	}
	
	@Override
	public List<T> toList(){
		return heap;
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
