package concurrency;

public class DekkerPh extends Philosopher {

	private int[] flag;
	private volatile int turn;

	public DekkerPh(int id, int[] flag, int turn) {
		super(id);
		this.flag = flag;
		this.turn = turn;
	}

	@Override
	public void trySection() {
		super.trySection();

		boolean allowed = false;
		while (!allowed) {
			// Try to obtain turn
			flag[id] = 1;
			while (turn != id) {
				if (flag[turn] == 0) {
					turn = id;
				}
			}
			// Check that no other ph has reached this stage
			flag[id] = 2;
			allowed = true;
			for (int j = 0; j < PH_NO; j++) {
				if (j != id && flag[j] == 2) {
					allowed = false;
					break;
				}
			}
		}
	}

	@Override
	public void exitSection() {
		super.exitSection();
		
		flag[id] = 0;
	}

	public static void main(String[] args) {
		int turn = 0;
		int[] flag = new int[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			flag[i] = 0;
		}

		Philosopher[] phs = new Philosopher[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			phs[i] = new DekkerPh(i, flag, turn);
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
