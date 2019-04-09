package concurrency;

public class LamportPh extends Philosopher {

	private int[] number;
	private int[] choosing;

	public LamportPh(int id, int[] level, int[] turn) {
		super(id);
		this.number = level;
		this.choosing = turn;
	}

	@Override
	public void trySection() {
		super.trySection();

		choosing[id] = 1;
		for (int j = 0; j < PH_NO; j++) {
			number[id] = Math.max(number[id], number[j] + 1);
		}
		choosing[id] = 0;
		for (int j = 0; j < PH_NO; j++) {
			if (j != id) {
				boolean allowed = false;
				while (!allowed) {
					allowed = true;
					if (choosing[j] == 1) {
						allowed = false;
						continue;
					}
					if (number[j] != 0 && (number[j] < number[id] || (number[j] == number[id] && j < id))) {
						allowed = false;
						continue;
					}
				}
			}
		}
	}

	@Override
	public void exitSection() {
		super.exitSection();

		number[id] = 0;
	}

	public static void main(String[] args) {
		int[] number = new int[PH_NO];
		int[] choosing = new int[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			number[i] = 0;
			choosing[i] = 0;
		}

		Philosopher[] phs = new Philosopher[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			phs[i] = new LamportPh(i, number, choosing);
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
