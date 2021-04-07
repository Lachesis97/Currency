package pl.streamsoft.cache_service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LruCacheService<String, Currency> {
	private int size;
	private ConcurrentLinkedQueue<String> linkedQueue;
	private ConcurrentHashMap<String, Currency> hashMap;

	public LruCacheService(int size) {
		this.size = size;
		if (size > 1024) {
			size = 1024;
		}
		this.linkedQueue = new ConcurrentLinkedQueue<String>();
		this.hashMap = new ConcurrentHashMap<String, Currency>(size);
	}

	public Currency get(String key) {
		Currency value = hashMap.get(key);
		if (value != null) {
			linkedQueue.remove(key);
			linkedQueue.add(key);
		}
		return value;
	}

	public synchronized void put(final String key, final Currency value) {
		if (hashMap.containsKey(key)) {
			linkedQueue.remove(key);
		}

		while (linkedQueue.size() >= size) {
			String oldestKey = linkedQueue.poll();
			if (oldestKey != null) {
				hashMap.remove(oldestKey);
			}

		}
		linkedQueue.add(key);
		hashMap.put(key, value);
	}

	public synchronized void clear() {
		this.linkedQueue = new ConcurrentLinkedQueue<String>();
		this.hashMap = new ConcurrentHashMap<String, Currency>(size);
	}

}