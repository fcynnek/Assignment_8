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

	public static void main(String[] args) {
		
		Assignment8 baseCode = new Assignment8();
		ExecutorService cachedPool = Executors.newCachedThreadPool();
		ExecutorService cpuPool = Executors.newFixedThreadPool(6);
		List<Integer> listOfNumbers = new ArrayList<Integer>();
		Map<Integer, Integer> countOfNumbers = new HashMap<>();
		
		for (int i=0; i<1000; i++) {
//			listOfNumbers.addAll(baseCode.getNumbers());
//			System.out.println(listOfNumbers);
			
			
//			CompletableFuture.supplyAsync(() -> listOfNumbers.addAll(baseCode.getNumbers()), cachedPool);
			CompletableFuture.supplyAsync(() -> listOfNumbers.addAll(baseCode.getNumbers()), cpuPool);
//							 .thenComposeAsync(listOfNumbers.addAll(baseCode.getNumbers()));
//							 .thenAccept(numbers -> baseCode.getNumbers())
//							 .thenApplyAsync(number -> baseCode.getNumbers(), cpuPool)
//							 .thenAcceptAsync(number -> baseCode.getNumbers());
//							 .thenAcceptAsync(iterances -> System.out.println());
							 
//							 .thenAcceptAsync((Consumer<? super Assignment8>) baseCode.getNumbers(), cpuPool);
		}
		
//		for (Integer number : listOfNumbers) {
//			countOfNumbers.compute(number, (k, v) -> v == null ? 1 : v + 1);
//		}
		for (int i=0; i<1000000; i++) {
			listOfNumbers.forEach(number -> {
				if (countOfNumbers.containsKey(number)) {
					countOfNumbers.put(number, countOfNumbers.get(countOfNumbers) + 1);
				} else {
					countOfNumbers.put(number, 1);
				}
			});
		}

		countOfNumbers.forEach((k, v) -> System.out.println(k + ": " + v));
		
	}
}
