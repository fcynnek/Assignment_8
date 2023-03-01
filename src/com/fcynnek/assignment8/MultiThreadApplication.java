package com.fcynnek.assignment8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.coderscampus.assignment.Assignment8;

public class MultiThreadApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		
		Assignment8 baseCode = new Assignment8();
		ExecutorService cachedPool = Executors.newCachedThreadPool();
//		ExecutorService cpuPool = Executors.newFixedThreadPool(6);
//		List<Integer> listOfNumbers = new ArrayList<>();
//		List<AtomicInteger> listOfNumbers = new ArrayList<>();
		Collection<Integer> listOfNumbers = Collections.synchronizedCollection(new ArrayList<>());
		Map<Integer, AtomicInteger> countOfNumbers = new HashMap<>();
		List<CompletableFuture<Void>> listOfFutures = new ArrayList<>();
		
		
		for (int i=0; i<1000; i++) {
			
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> listOfNumbers.addAll(baseCode.getNumbers()), cachedPool);
			listOfFutures.add(future);
		}
		
		
		for (int i=0; i<1000; i++) {
			listOfFutures.get(i).get();				
		}
		
		
		CompletableFuture.allOf(listOfFutures.toArray(new CompletableFuture[0])).join();
		
		
		while (listOfFutures.stream().filter(CompletableFuture :: isDone).count() < 1000) {
//			System.out.println(listOfFutures.stream().filter(CompletableFuture :: isDone).count());
		}
		
		
		for (Integer number : listOfNumbers) {
			
			synchronized (countOfNumbers) {
				countOfNumbers.compute(number, (k, v) -> {
					if (v == null) {
						v = new AtomicInteger(1);
					} else {
						synchronized (v) {
							v.incrementAndGet();
						}
					}
					return v;
				});
			}
		};
				
		
		countOfNumbers.forEach((k, v) -> System.out.println(k + ": " + v.get()));

	}
}
