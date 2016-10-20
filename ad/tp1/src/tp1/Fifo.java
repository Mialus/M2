package tp1;
import java.util.LinkedList;
import java.util.Queue;

public class Fifo {

	static class Container {

		Queue<Object> queue = new LinkedList<Object>();

		public void push(Object o) {
			queue.add(o);
		}

		public Object pop() {
			Object o = queue.poll();
			return o;
		}

		public int size() {
			return queue.size();
		}
	}

	static class Producer extends Thread {
		Container fifo;
		long times;
		Object o;
		public Producer(Container fifo, long times) {
			o = new Object();
			this.fifo = fifo;
			this.times = times;
		}

		public void run() {

			for(int i =0; i<50;i++){
				synchronized(fifo){
					fifo.push(o);
					System.out.println("push" + i);
					fifo.notifyAll();
				}
			}
			
		}
	}

	static class Consumer extends Thread {
		Container fifo;
		long times;
		public Consumer(Container fifo, long times) {
			this.fifo = fifo;
			this.times = times;
		}

		public void run() {
			for(int i =0; i<50;i++){
				synchronized (fifo){
						while(fifo.size()==0){
							try{
							fifo.wait();
							} catch (InterruptedException e) {}
						}
						fifo.pop();
						System.out.println("pop" + i);
			}
		}
	}
	}
	final static int NTHREADS = 10;
	final static long TIMES = 1000000;

	public static final void main(String args[]) {
		Thread threads[] = new Thread[NTHREADS];
		Container fifo = new Container();

		for (int i = 0; i < threads.length; i++) {
			if ((i % 2) == 0)
				threads[i] = new Producer(fifo, TIMES);
			else
				threads[i] = new Consumer(fifo, TIMES);
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (fifo.size() == 0) {
			System.out.println("OK");
		} else {
			System.out.println("ERREUR");
		}
	}

}