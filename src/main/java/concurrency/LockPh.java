package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockPh extends Philosopher {

	private static Lock lock = new ReentrantLock();
	
	public LockPh(int id) {
		super(id);
	}

	@Override
	public void trySection() {
		super.trySection();

		lock.lock();
	}

	@Override
	public void exitSection() {
		super.exitSection();

		lock.unlock();
	}

	public static void main(String[] args) {
		Philosopher[] phs = new Philosopher[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			phs[i] = new LockPh(i);
			phs[i].start();
		}
		try {
			Thread.sleep(PH_DELAY);
			for (int i = 0; i < PH_NO; i++) {
				phs[i].stopped = true;
				phs[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
