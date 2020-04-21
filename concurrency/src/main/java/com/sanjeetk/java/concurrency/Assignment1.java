package com.sanjeetk.java.concurrency;

public class Assignment1 {
	public static void main(String[] args) {
		DataObject dataObject = new DataObject();
		for (int i = 1; i <= 10; i++) {
			new Producer(dataObject, i).start();
			new Consumer(dataObject, i).start();
		}		
	}
}

class DataObject {
	private static final int SIZE = 10;
	private int getCounter = 0;
	private int putCounter = 0;
	
	private int[] dataContent = new int[SIZE];
	private boolean[] available = new boolean[SIZE];
		
	public synchronized int get() {
		int value;
		while (available[getCounter] == false) { 
			try {
				System.out.println("GET:: thread: " + Thread.currentThread().getName() + " blocked");
				wait();
			} catch (InterruptedException e) {
			}
		}

		System.out.println("GET FROM: " + getCounter + "::value: " + dataContent[getCounter]);
		available[getCounter] = false;
		value = dataContent[getCounter];
		getCounter++;
		if (getCounter == SIZE) {
			getCounter = 0;			
		}
		notifyAll();
		return value;
	}

	public synchronized void put(int value) {
		while (available[putCounter] == true) { 
			try {
				System.out.println("PUT:: thread: " + Thread.currentThread().getName() + " blocked");
				wait();
			} catch (InterruptedException e) {
			}
		}

		System.out.println("PUT AT: " + putCounter + "::value: " + value);
		dataContent[putCounter] = value;
		available[putCounter] = true;
		putCounter++;
		if (putCounter == SIZE) {
			putCounter = 0;			
		}
		notifyAll();
	}
}

class Consumer extends Thread {
	private DataObject dataObject;
	private int consumerNumber;
	
	public Consumer(DataObject dataObject, int number) {
		super("thread-" + number);
		this.dataObject = dataObject;
		this.consumerNumber = number;		
	}

	public void run() {
		int value = 0;
		for (int i = 1; i <= 10; i++) {
			value = dataObject.get();
			System.out.println("Consumer #" + this.consumerNumber + " got: " + value);
			try {
				int sleepTime = (int) (Math.random() * 200);
				if (sleepTime >= 5000) {
					sleepTime = 4999;
				}
				System.out.println("Consumer #" + this.consumerNumber + " sleeping for : " + sleepTime);
				sleep(sleepTime);
			} catch (InterruptedException e) {
			}
		}
	}
}

class Producer extends Thread {
	private DataObject dataObject;
	private int producerNumber;
		
	public Producer(DataObject dataObject, int number) {
		super("thread-" + number);
		this.dataObject = dataObject;
		this.producerNumber = number;
	}

	public void run() {
		for (int value = 1; value <= 10; value++) {			
			dataObject.put(value);
			System.out.println("Producer #" + this.producerNumber + " put: " + value);
			try {
				int sleepTime = (int) (Math.random() * 100);
				if (sleepTime >= 5000) {
					sleepTime = 4999;
				}
				System.out.println("Producer #" + this.producerNumber + " sleeping for : " + sleepTime);
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}