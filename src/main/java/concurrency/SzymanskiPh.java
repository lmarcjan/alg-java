package concurrency;

public class SzymanskiPh extends Philosopher {

	private int[] flag;

	public SzymanskiPh(int id, int[] flag) {
		super(id);
		this.flag = flag;
	}

	@Override
	public void trySection() {
		super.trySection();

		flag[id] = 1; // Standing outside waiting room
		// Wait for open door
		boolean open = false;
		while (!open) {
			open = true;
			for (int j = 0; j < PH_NO; j++) {
				if (j != id && flag[j] > 2) {
					open = false;
					break;
				}
			}
		}
		flag[id] = 3; // Standing in doorway
		// Check if another process is waiting to enter
		boolean waiting = false;
		for (int j = 0; j < PH_NO; j++) {
			if (j != id && flag[j] == 1) {
				waiting = true;
				break;
			}
		}
		if (waiting) {
			flag[id] = 2; // Waiting for other processes to enter
			// Wait for a process to enter and close the door
			boolean entered = false;
			while (!entered) {
				for (int j = 0; j < PH_NO; j++) {
					if (j != id && flag[j] == 4) {
						entered = true;
						break;
					}
				}
			}
		}
		flag[id] = 4; // The door is closed
		// Wait for everyone of lower ID to finish exit protocol
		boolean finished = false;
		while (!finished) {
			finished = true;
			for (int j = 0; j < id; j++) {
				if (j != id && flag[j] > 1) {
					finished = false;
					break;
				}
			}
		}
	}

	@Override
	public void exitSection() {
		super.exitSection();

		// Ensure everyone in the waiting room has
		// realized that the door is supposed to be closed
		boolean realized = false;
		while (!realized) {
			realized = true;
			for (int j = id+1; j < PH_NO; j++) {
				if (j != id && flag[j] > 1 && flag[j] != 4) {
					realized = false;
					break;
				}
			}
		}
		flag[id] = 0; // Leave. Reopen door if nobody is still in the waiting room
	}

	public static void main(String[] args) {
		int[] flag = new int[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			flag[i] = 0;
		}

		Philosopher[] phs = new Philosopher[PH_NO];
		for (int i = 0; i < PH_NO; i++) {
			phs[i] = new SzymanskiPh(i, flag);
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
