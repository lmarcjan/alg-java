package concurrency;

import java.util.EmptyStackException;
import java.util.Stack;

public class ProducentConsumer {

	public static class Consumer extends Thread {
		private Stack<Integer> stack;

		public Consumer(Stack<Integer> stack) {
			this.stack = stack;
		}

		public void run() {
			while (true) {
				synchronized (stack) {
					try {
						int x = stack.pop();
						System.out.println("Just popped " + x);
					} catch (EmptyStackException e) {
						try {
							System.out.println("Waiting...");
							stack.wait();
						} catch (InterruptedException e2) {
						}
					}
				}
			}
		}
	}

	public static class Producer extends Thread {
		private Stack<Integer> stack;

		public Producer(Stack<Integer> stack) {
			this.stack = stack;
		}

		public void run() {
			while (true) {
				int random = (int) (Math.random() * 5);
				stack.push(random);
				synchronized (stack) {
					System.out.println("Notifying...");
					stack.notify();
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		Consumer c = new Consumer(stack);
		c.start();
		Producer p = new Producer(stack);
		p.start();
	}
}
