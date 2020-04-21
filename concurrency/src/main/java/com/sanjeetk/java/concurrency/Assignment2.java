package com.sanjeetk.java.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Assignment2 {
	
	public static void main(String args[]) throws InterruptedException {		
		startAndCompleteTasks(10);		
	}

	private static void startAndCompleteTasks(int numberOfThreads) throws InterruptedException  {
		final CountDownLatch startGate = new CountDownLatch(10);
		final CyclicBarrier barrier = new CyclicBarrier(10);
		
		System.out.println("Application Started");
		
		for (int i=0; i < numberOfThreads; i++) {			
			new Thread("Thread " + i) {
				public void run() {					
					System.out.println(Thread.currentThread().getName() + " started");	
						
					try {
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();						
					} catch (BrokenBarrierException e) {						
						e.printStackTrace();
					}							
						
					System.out.println(Thread.currentThread().getName() + " finished");
					startGate.countDown();
					
				}
			}.start();			
		}	
				
		try {
			startGate.await();
		} catch (InterruptedException e) {			
			e.printStackTrace();
			throw e;
		}
		
		
		System.out.println("Application Finished");
	}
	
}
