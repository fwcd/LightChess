package fwcd.lightchess.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import fwcd.fructose.Observable;

public class ObservableList<T> implements Iterable<T> {
	private final Observable<List<T>> list = new Observable<>(new ArrayList<>());
	
	public void add(T element) {
		list.get().add(element);
		list.fire();
	}
	
	public void remove(T element) {
		list.get().remove(element);
		list.fire();
	}
	
	public int size() {
		return list.get().size();
	}
	
	public T get(int index) {
		return list.get().get(index);
	}
	
	public void listen(Consumer<List<T>> listener) {
		list.listen(listener);
	}
	
	public void unlisten(Consumer<List<T>> listener) {
		list.unlisten(listener);
	}
	
	public Stream<T> stream() {
		return list.get().stream();
	}
	
	@Override
	public Iterator<T> iterator() {
		return list.get().iterator();
	}
}
