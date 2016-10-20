import java.util.LinkedList;
import java.util.Queue;

public class FifoSize {
    
    static class Container {

	static final int FIFO_SIZE = 10;
	Queue<Object> queue = new LinkedList<Object>();
	
	public boolean push(Object o) {

	    if (size() == FIFO_SIZE) {
		return false;
	    } else {
		queue.add(o);
		return true;
	    }
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
	Container container;
	long t;
	int producer;
	
	public Producer(Container fifo, long times, int prod) {
	    
	    container = fifo;
	    t = times;
	    producer = prod;
	}
	
	public void run() {
	    
	    for( int i = 0; i < t; i++) {

		synchronized( container ) {

		    Object myObj = new Object();
		    boolean add = container.push( myObj );
		    while ( add == false ) {
			try {
			    // System.out.println("Producer wait");
			    container.wait();
			} catch (Exception ex) {
			    System.out.println("*** Except ***" + ex);
			}
			add = container.push( myObj );
		    } 
		    // System.out.println("Producer " + producer + " add " + container.size() );
		    container.notifyAll();
		}
	    }
	}
    }
    
    static class Consumer extends Thread {
	
	Container container;
	long t;
	int consumer;
	
	public Consumer(Container fifo, long times, int cons) {
	    
	    container = fifo;
	    t = times;
	    consumer = cons;
	}
	
	public void run() {
	    
	    for( int i = 0; i < t ; i++) {
		
		synchronized( container ){

		    Object o = container.pop();
		    // System.out.println("Consumer " + consumer + " pop " + i);
		    while( o == null ) {
		    
			try {
			    // System.out.println("Consumer wait");
			    container.wait();
			    
			} catch(Exception ex) { 
			    System.out.println("*** Except ***" + ex );
			}
			
			
			o = container.pop();
		    }
		    container.notifyAll();
		}
	    }
	}
    }    

    final static int NTHREADS = 10;
    final static long TIMES = 50;
    
    public static final void main(String args[]) {
	Thread threads[] = new Thread[NTHREADS];
	Container fifo = new Container();
	
	for (int i = 0; i < threads.length; i++) {
	    if ((i % 2) == 0)
		threads[i] = new Producer(fifo, TIMES, i);
	    else
		threads[i] = new Consumer(fifo, TIMES, i);
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
	    System.out.println("ERREUR " + fifo.size() );
	}
    }
}

