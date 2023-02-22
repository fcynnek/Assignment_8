package com.fcynnek.assignment8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import com.coderscampus.assignment.Assignment8;

public class MultiThreadApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		Assignment8 baseCode = new Assignment8();
		ExecutorService cachedPool = Executors.newCachedThreadPool();
//		ExecutorService cpuPool = Executors.newFixedThreadPool(6);
		List<Integer> listOfNumbers = new ArrayList<Integer>();
		Map<Integer, Integer> countOfNumbers = new HashMap<>();
		List<CompletableFuture<Void>> listOfFutures = new ArrayList<>();
		
		
		for (int i=0; i<1000; i++) {
			
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> listOfNumbers.addAll(baseCode.getNumbers()), cachedPool);
			listOfFutures.add(future);
			
		}
		
		for (int i=0; i<1000; i++) {
			listOfFutures.get(i).get();
		}
		
		listOfNumbers.forEach(number -> {
			if (countOfNumbers.containsKey(number)) {
				countOfNumbers.put(number, countOfNumbers.get(number) + 1);
			} else {
				countOfNumbers.put(number, 1);
			}
		});

		countOfNumbers.forEach((k, v) -> System.out.println(k + ": " + v));

	}
}
