package pl.streamsoft.services;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExtendedCacheMap<String, Currency> implements Map<String, Currency> {

	private Map<String, Currency> delegate = new ConcurrentHashMap<String, Currency>();
	private Queue<String> keyInsertionOrder = new ConcurrentLinkedQueue<String>();
	private int maxCapacity = 20;

	public ExtendedCacheMap() {

	}

	public ExtendedCacheMap(int maxCapacity) {
		if (maxCapacity < 1) {
			throw new IllegalArgumentException("Capacity must be greater than 0");
		}
		this.maxCapacity = maxCapacity;
	}

	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, Currency>> entrySet() {
		return delegate.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	@Override
	public Currency get(Object key) {
		return delegate.get(key);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return delegate.keySet();
	}

	@Override
	public Currency put(String key, Currency value) {
		Currency previous = delegate.put(key, value);
		keyInsertionOrder.remove(key);
		keyInsertionOrder.add(key);

		if (delegate.size() > maxCapacity) {
			String oldest = keyInsertionOrder.poll();
			delegate.remove(oldest);
		}
		return previous;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Currency> m) {
		for (String key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	@Override
	public Currency remove(Object key) {
		keyInsertionOrder.remove(key);
		return delegate.remove(key);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Collection<Currency> values() {
		return delegate.values();
	}
}