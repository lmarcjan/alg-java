package concurrency;

public class PetersonPh extends Philosopher {

	private int[] level;
	private int[] turn;

	public PetersonPh(int id, int[] level, int[] turn) {
		super(id);
		this.level = level;
		this.turn = turn;
	}

	@Override
	public void trySection() {
		super.trySection();

		for (int k = 0; k < PH_NO - 1; k++) {
			level[id] = k;
			turn[k] = id;
			boolean allowed = false;
			while (!allowed) {
				allowed = true;
				if (turn[k] != id) {
					continue;
				}
				for (int j = 0; j < PH_NO; j++) {
					if (j != id && level[j] >= k) {
						allowed = false;
						break;
					}
				}
			}
		}
	}

	@Override
	public void exitSection() {
		super.exitSection();

		level[id] = 0;
	}

	public static void main(String[] args) {
		int[] level = new int[PH_NO];
		int[] turn = new int[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			level[i] = 0;
			turn[i] = 0;
		}

		Philosopher[] phs = new Philosopher[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			phs[i] = new PetersonPh(i, level, turn);
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
