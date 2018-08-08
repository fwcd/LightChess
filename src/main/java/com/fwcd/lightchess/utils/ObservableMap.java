package com.fwcd.lightchess.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.fwcd.fructose.Observable;

public class ObservableMap<K, V> {
	private final Observable<Map<K, V>> map = new Observable<>(new HashMap<>());
	private final List<BiConsumer<K, V>> putListeners = new ArrayList<>();
	private final List<BiConsumer<K, V>> removeListeners = new ArrayList<>();
	
	public void put(K key, V value) {
		map.get().put(key, value);
		putListeners.forEach(it -> it.accept(key, value));
		map.fire();
	}
	
	public int size() {
		return map.get().size();
	}
	
	public V remove(K key) {
		V removed = map.get().remove(key);
		removeListeners.forEach(it -> it.accept(key, removed));
		return removed;
	}
	
	public V get(K key) {
		return map.get().get(key);
	}
	
	public Set<K> keySet() {
		return map.get().keySet();
	}
	
	public Collection<V> values() {
		return map.get().values();
	}
	
	public Set<Map.Entry<K, V>> entrySet() {
		return map.get().entrySet();
	}
	
	public void listen(Consumer<Map<K, V>> listener) {
		map.listen(listener);
	}
	
	public void unlisten(Consumer<Map<K, V>> listener) {
		map.unlisten(listener);
	}
	
	public void listenToPut(BiConsumer<K, V> listener) {
		putListeners.add(listener);
	}
	
	public void unlistenFromPut(BiConsumer<K, V> listener) {
		putListeners.remove(listener);
	}
	
	public void listenToRemove(BiConsumer<K, V> listener) {
		removeListeners.add(listener);
	}
	
	public void unlistenFromRemove(BiConsumer<K, V> listener) {
		removeListeners.remove(listener);
	}
}
