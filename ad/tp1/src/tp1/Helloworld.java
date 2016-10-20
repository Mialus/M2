package tp1;

class HelloWorld {

	static class SayHello implements Runnable {

		public void run() {
			System.out.println("Hello World from " + Thread.currentThread().getId());
		}

	}

	public static final int NTHREADS = 10;

	public static void main(String[] args) {
		SayHello s = new SayHello();
		Thread[] thread = new Thread[NTHREADS];
		
		for(int i = 0; i < NTHREADS; i++){
			thread[i] = new Thread(s); 
			thread[i].start();
		}
	}

}