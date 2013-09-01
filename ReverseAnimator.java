class Calculator extends Thread {
	private volatile long startTime;
	private volatile long endTime;
	private volatile boolean reversed = false;
	private volatile float duration;

	private volatile float forwardDuration;
	private volatile float reverseDuration;
	private volatile float fraction;

	public Calculator(int duration) {
		this.duration = (float) duration;
		forwardDuration = this.duration;
	}

	@Override
	public void run() {
		startTime = System.currentTimeMillis();

		while (true) {

			if (reversed) {
				fraction = (endTime - System.currentTimeMillis()) / duration;

				if (fraction < 0) {
					break;
				}
			} else {
				fraction = (System.currentTimeMillis() - startTime) / duration;

				if (fraction > 1) {
					break;
				}
			}

			System.out.println(fraction);
		}
	}

	public void reverse() {
		if (reversed) {
			startTime = System.currentTimeMillis() - (long) (fraction * duration);
			reversed = false;
		} else {
			endTime = System.currentTimeMillis() + (long) (fraction * duration);
			reversed = true;
		}
	}
}

public class RevertingAnimator {
	public static void main(String[] args) {
		Calculator c = new Calculator(10000);
		c.start();
		try {
			Thread.sleep(5000);
			c.reverse();
			Thread.sleep(2000);
			c.reverse();
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
