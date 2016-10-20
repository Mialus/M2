// Java imports
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

// Visidia imports
import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.Door;


public class CentralizedMutualExclusion extends Algorithm {

    // All nodes data
    int procId;
    int nbNeighbors;

    int clock = 0;
    int[] F_H;
    MsgType[] F_M;

    // To display the state
    boolean waitForCritical = false;
    boolean inCritical = false;

    // State display frame
    DisplayFrame df;
    // Master's Data
    boolean util = false;
    Queue<Integer> attente = new LinkedList<Integer>();

    public String getDescription() {

	return ("Centralized Algorithm for Mutual Exclusion");
    }

    @Override
    public Object clone() {
	return new CentralizedMutualExclusion ();
    }

    //
    // Nodes' code
    //
    @Override
    public void init() {

	procId = getId();
	Random rand = new Random( procId );

	// Start reception thread
	Thread thread = new Thread( this );

	while( true ) {
	    
	    if ( procId == 0 ) {

		System.out.println("Je suis le master");
		
		Door d = new Door();
		SyncMessage m = (SyncMessage) receive(d);
		int neighbor = d.getNum();
		
		switch (m.getMsgType()) {
		    
		case REQ :
		    recvReq( neighbor );
		    break;
		    
		case REL :
		    recvRel( neighbor );
		    break;
		    
		default: 
		    System.out.println("Error message type");
		}

	    } else {

		// Wait for some time
		int time = ( 3 + rand.nextInt(10)) * 1000;
		System.out.println("Process " + procId + " wait for " + time);
		try {
		    Thread.sleep( time );
		} catch( InterruptedException ie ) {}
		
		// Try to access critical section
		askForCritical();
		
		// Simulate critical resource use
		time = (1 + rand.nextInt(2)) * 1000;
		System.out.println("Process " + procId + " wait for " + time);
		try {
		    Thread.sleep( time );
		} catch( InterruptedException ie ) {}

		// Release critical use
		endCriticalUse();
	    }
	}
    }

    //
    // Rules
    //
    void askForCritical() {

	SyncMessage sm = new SyncMessage(MsgType.REQ);
	boolean sent = sendTo( 0, sm );
	sm = (SyncMessage)receiveFrom( 0 );
    }

    void endCriticalUse() {
	
	SyncMessage sm = new SyncMessage(MsgType.REL);
	boolean sent = sendTo( 0, sm );
    }

    synchronized void recvReq( int neighbor ) {
	
	if ( !util ) {
	    
	    util = true;
	    SyncMessage sm = new SyncMessage(MsgType.ACK);
	    boolean sent = sendTo( neighbor, sm );
	    
	} else {
	    
	    try {
		attente.add( neighbor );
	    } catch(IllegalStateException ise) {
		System.out.println("Queue is already full");
	    }
	}
	
    }

    synchronized void recvRel( int neighbor ) {
	
	Integer next = attente.remove();
	if ( next == null ) {
	    
	    util = false;
	    
	} else {
	    
	    SyncMessage sm = new SyncMessage(MsgType.ACK);
	    boolean sent = sendTo( next, sm );
	}
    }

    // Display state
    void displayState() {

	String state = new String("Clock = " + clock + "\n");
	state = state + "--------------------------------------\n";
	for ( int i = 0 ; i < (nbNeighbors+1) ; i++ ) {
	    state = state + "F_H[" + i + "] = " + F_H[i] + "\t";
	    state = state + "F_M[" + i + "] = " + F_M[i] + "\n";
	}
	state = state + "--------------------------------------\n";
	if ( inCritical ) 
	    state = state + "** ACCESS CRITICAL **";
	else if ( waitForCritical )
	    state = state + "* WAIT FOR *";
	else
	    state = state + "-- SLEEPING --";

	df.display( state );
    }
}
    
