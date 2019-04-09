package concurrency;

import java.util.concurrent.atomic.AtomicBoolean;

public class CompareAndSetPh extends Philosopher {

	private static AtomicBoolean lock = new AtomicBoolean(false);
	
	public CompareAndSetPh(int id) {
		super(id);
	}

	@Override
	public void trySection() {
		super.trySection();
		
		while (!lock.compareAndSet(false, true)) {
			// Wait for lock
		}
	}

	@Override
	public void exitSection() {
		super.exitSection();

		lock.set(false); // Release lock
	}

	public static void main(String[] args) {
		Philosopher[] phs = new Philosopher[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			phs[i] = new CompareAndSetPh(i);
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
