package concurrency;

public class UnsynchronizedPh extends Philosopher {

	public UnsynchronizedPh(int id) {
		super(id);
	}

	@Override
	public void trySection() {
		super.trySection();
		
		// Do nothing
	}

	@Override
	public void exitSection() {
		super.exitSection();

		// Do nothing
	}

	public static void main(String[] args) {
		Philosopher[] phs = new Philosopher[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			phs[i] = new UnsynchronizedPh(i);
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
