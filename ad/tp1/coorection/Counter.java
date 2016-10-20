public class Counter {
    
    static class Amount {
	private long amount = 0;
	
	public void setAmount(long amount) {
	    this.amount = amount;
	}
	
	public long getAmount() {
	    return amount;
	}
    }
    
    static class Incrementor extends Thread {
	Amount amount;
	long times;
	int increment;
	
	public Incrementor(Amount amount, long times, int increment) {
	    this.amount = amount;
	    this.times = times;
	    this.increment = increment;
	}
	
	public void run() {
	    for (long i = 0; i < times; i++) {
		
		synchronized( amount ) {
		    
		    long a = amount.getAmount();
		    try {
			Thread.sleep(5);
		    } catch (Exception _) {
		    }
		    amount.setAmount(a + increment);
		}
	    }
	}
    };
    
    static final long TIMES = 100;
    static final int INCREMENT = 2;
    
    static final int NTHREADS = 10;
    
    public static final void main(String args[]) {
	Thread threads[] = new Thread[NTHREADS];
	Amount amount = new Amount();
	
	for (int i = 0; i < threads.length; i++) {
	    threads[i] = new Incrementor(amount, TIMES, INCREMENT);
	}
	
	for (Thread thread : threads) {
	    thread.start();
	}
	
	for (Thread thread : threads) {
	    try {
		thread.join();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	
	if (amount.getAmount() == (NTHREADS * TIMES * INCREMENT)) {
	    System.out.println("OK (" + amount.getAmount() + ")");
	} else {
	    System.out.println("ERREUR (" + amount.getAmount() + ")");
	}
	
    }
}

