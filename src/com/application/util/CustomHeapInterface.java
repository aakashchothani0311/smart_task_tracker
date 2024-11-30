package com.application.util;

import java.util.List;

public interface CustomHeapInterface<T> {
	public void insert(T val);
	public T remove();
	public List<T> toList();
	public boolean isEmpty(); 
}
