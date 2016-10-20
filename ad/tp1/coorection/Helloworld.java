class HelloWorld {
    
    static class SayHello implements Runnable {
	
	public void run() {
	    System.out.println("Hello world from " + 
			       Thread.currentThread().getId() );
	}
	
    }
    
    public static final int NTHREADS = 10;
    
    public static void main(String[] args) {
	
	Thread threads[] = new Thread[NTHREADS];
	
	for (int i = 0; i < threads.length; i++) {
	    SayHello sh = new SayHello();
	    threads[i] = new Thread( sh );
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
    }
}

