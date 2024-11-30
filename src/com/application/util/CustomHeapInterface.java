package com.application.util;

public interface CustomHeapInterface<T> {
	public void insert(T val);
	public T remove();
	public boolean isEmpty(); 
}
