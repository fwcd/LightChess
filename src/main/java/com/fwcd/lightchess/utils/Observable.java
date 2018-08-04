package com.fwcd.lightchess.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Observable<T> {
	private T value;
	private final List<Consumer<T>> listeners = new ArrayList<>();
	
	public Observable(T value) {
		this.value = Objects.requireNonNull(value);
	}
	
	public void set(T value) {
		this.value = Objects.requireNonNull(value);
		fire();
	}
	
	public T get() {
		return value;
	}
	
	public void use(Consumer<T> user) {
		user.accept(value);
		fire();
	}
	
	private void fire() {
		for (Consumer<T> listener : listeners) {
			listener.accept(value);
		}
	}
	
	public void listen(Consumer<T> listener) {
		listeners.add(listener);
	}
	
	public void unlisten(Consumer<T> listener) {
		listeners.remove(listener);
	}
}
