package concurrency;

import java.util.Random;

public abstract class Philosopher extends Thread {

	protected static final int PH_NO = 10;
	protected static final int PH_DELAY = 5000;

	private static final int CRITICAL_SECTION_DELAY = 500;
	private static final int REMAINDER_SECTION_DELAY = 500;

	protected static volatile boolean critical = false;

	protected Random rnd = new Random();

	protected boolean stopped = false;
	protected int id;

	public Philosopher(int id) {
		super();
		System.out.println("Creating ph " + id + "...");
		this.id = id;
	}

	@Override
	public synchronized void start() {
		System.out.println("Starting ph " + id + "...");
		super.start();
	}

	@Override
	public void run() {
		while (!stopped) {
			remainderSection();
			trySection();
			criticalSection();
			exitSection();
		}
		System.out.println("Ph " + id + " has been stopped...");
	}

	protected void trySection() {
		System.out.println("Ph " + id + " is entering try section...");		
	}

	protected void criticalSection() {
		System.out.println("Ph " + id + " is entering critical section...");
		if (critical)
			throw new IllegalStateException();
		critical = true;
		try {
			Thread.sleep(rnd.nextInt(CRITICAL_SECTION_DELAY));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		critical = false;
	}

	protected void exitSection() {
		System.out.println("Ph " + id + " is entering exit section...");				
	}

	protected void remainderSection() {
		System.out.println("Ph " + id + " entering remainder section...");
		try {
			Thread.sleep(rnd.nextInt(REMAINDER_SECTION_DELAY));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
