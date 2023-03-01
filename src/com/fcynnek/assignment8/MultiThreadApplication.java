package com.fcynnek.assignment8;

import java.util.ArrayList;
import java.util.Collection;
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
		List<Integer> listOfNumbers = new ArrayList<>();
//		List<AtomicInteger> listOfNumbers = new ArrayList<>();
		Map<Integer, AtomicInteger> countOfNumbers = new HashMap<>();
		List<CompletableFuture<Void>> listOfFutures = new ArrayList<>();
		
		
		for (int i=0; i<1000; i++) {
			
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> listOfNumbers.addAll(baseCode.getNumbers()), cachedPool);
			
//			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//				List<Integer> numbers = baseCode.getNumbers();
//				for (int number : numbers) {
//					AtomicInteger atomicNum = new AtomicInteger(number);
//					listOfNumbers.addAll(atomicNum);
//				}
//			}, cachedPool);
			
			listOfFutures.add(future);
			
		}
		
		for (int i=0; i<1000; i++) {
			listOfFutures.get(i).get();				
		}
		CompletableFuture.allOf(listOfFutures.toArray(new CompletableFuture[0])).join();
		
		while (listOfFutures.stream().filter(CompletableFuture :: isDone).count() < 1000) {
//			System.out.println(listOfFutures.stream().filter(CompletableFuture :: isDone).count());
		}
		
//		listOfNumbers.forEach(number -> {
					
//				if (countOfNumbers.containsKey(number)) {
//					countOfNumbers.put(number, countOfNumbers.get(number) + 1);
//				} else {
//					countOfNumbers.put(number, 1);
//				}
//			}
		for (Integer number : listOfNumbers) {
			
			synchronized (countOfNumbers) {
				countOfNumbers.compute(number, (k, v) -> {
					if (v == null) {
						v = new AtomicInteger();
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
