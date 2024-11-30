package com.application.util;

import java.util.ArrayList;

public class CustomHeap<T> implements CustomHeapInterface<T> {
	
	private ArrayList<T> heap;
	
	CustomHeap(){
		heap = new ArrayList<>();
	}
 
	@Override
	public void insert(T val) {
		
	}

	@Override
	public T remove() {
		
		return null;
	}

	@Override
	public boolean isEmpty() {
		return heap.isEmpty();
	}
	
	private void swap(int i, int j) {
		
	}
}
