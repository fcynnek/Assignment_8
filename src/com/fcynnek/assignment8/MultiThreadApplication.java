package com.fcynnek.assignment8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
//		ExecutorService cpuPool = Executors.newFixedThreadPool(5);
		List<Integer> listOfNumbers = new ArrayList<Integer>();
		
		for (int i=0; i<1000; i++) {
//			listOfNumbers.addAll(baseCode.getNumbers());
//			System.out.println(listOfNumbers);
			
			
			CompletableFuture.supplyAsync(() -> new Assignment8(), cachedPool)
//							 .thenApplyAsync(number -> baseCode.getNumbers(), cpuPool)
							 .thenAcceptAsync(number -> baseCode.getNumbers());
//							 .thenAcceptAsync(iterances -> System.out.println());
							 
//							 .thenAcceptAsync((Consumer<? super Assignment8>) baseCode.getNumbers(), cpuPool);
		}
		
	}
	
	//then run instead of then applyasync
}
