
// Java imports
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

// Visidia imports
import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.Door;

public class LamportMutualExclusion extends Algorithm {
    
    // All nodes data
    int procId;
    int nbNeighbors;

    int clock = 0;
    int[] F_H;
    MsgType[] F_M;

    // To display the state
    boolean waitForCritical = false;
    boolean inCritical = false;

    // Reception thread
    ReceptionRules rr = null;
    // State display frame
    DisplayFrame df;

    public String getDescription() {

	return ("Lamport Algorithm for Mutual Exclusion");
    }

    @Override
    public Object clone() {
	return new LamportMutualExclusion();
    }

    //
    // Nodes' code
    //
    @Override
    public void init() {

	procId = getId();
	Random rand = new Random( procId );

	rr = new ReceptionRules( this );
	rr.start();

	nbNeighbors = getArity();
	System.out.println("Process " + procId + " as " + nbNeighbors + " neighbors");	
	F_H = new int[nbNeighbors+1];
	for ( int i = 0 ; i < (nbNeighbors +1) ; i++ ) F_H[i] = 0;
	F_M = new MsgType[nbNeighbors+1];

	// Display initial state
	df = new DisplayFrame( procId );
	displayState();
	try { Thread.sleep( 15000 ); } catch( InterruptedException ie ) {}

	while( true ) {
	    
	    // Wait for some time before simulation
	    int time = 0;
	    time = ((procId + time) * 20000) + 1000;
	    System.out.println("Process " + procId + " wait for " + time);
	    try {
		Thread.sleep( time );
	    } catch( InterruptedException ie ) {}
	    
	    // Try to access critical section
	    waitForCritical = true;
	    askForCritical();
	    waitForCritical = false;
	    inCritical = true;
	    displayState();

	    // Simulate critical resource use
	    time = (1 + rand.nextInt(2)) * 1000;
	    System.out.println("Process " + procId + " enter SC " + time);
	    try {
		Thread.sleep( time );
	    } catch( InterruptedException ie ) {}
	    System.out.println("Process " + procId + " exit SC ");

	    // Release critical use
	    inCritical = false;
	    endCriticalUse();
	}
    }

    //--------------------
    // Rules
    //-------------------

    // Rule 1 : ask for critical section
    synchronized void askForCritical() {

	clock = clock + 1;
	F_H[ procId ] = clock;
	F_M[ procId ] = MsgType.REQ;

	SyncMessage sm = new SyncMessage(MsgType.REQ, clock, procId);

	for ( int i = 0; i < nbNeighbors ; i++ ) {

	    System.out.println("Process " + procId + " send REQ to " + i);
	    boolean sent = sendTo( i, sm );
	}

	boolean meTurn = false;
	while( !meTurn ) { 

	    displayState();

	    // Test my turn
	    meTurn = true;
	    for(int i = 0; i < nbNeighbors +1 ; i++ ) {

		if ( (F_H[i] < F_H[procId]) 
		     || ((F_H[i] == F_H[procId]) && (i < procId)) )
		    meTurn = false;
	    }
	    if ( !meTurn ) {
		try { this.wait(); } catch( InterruptedException ie) {}
	    }
	}
    }

    // Rule 2 : receive REQ
    synchronized void receiveREQ( int H, int p, int d){

	System.out.println("Process " + procId + " reveiced REQ from " + p);
	if ( clock < H ) clock = H;
	clock = clock + 1;

	F_H[p] = H;
	F_M[p] = MsgType.REQ;
	
	displayState();

	SyncMessage sm = new SyncMessage(MsgType.ACK, clock, procId);
	boolean sent = sendTo( d, sm );
    }

    // Rule 3 : receive ACK
    synchronized void receiveACK( int H, int p, int d ){

	System.out.println("Process " + procId + " reveiced ACK from " + p);
	if ( clock < H ) clock = H;
	clock = clock + 1;

	if ( F_M[p] != MsgType.REQ ) {
	    
	    F_H[p] = H;
	    F_M[p] = MsgType.ACK;
	}
	displayState();

	this.notify();
    }

    // Rule 4 :
    void endCriticalUse() {

	clock++;

	SyncMessage sm = new SyncMessage(MsgType.REL, clock, procId);
	for ( int i = 0 ; i < nbNeighbors ; i ++ ) {

	    boolean sent = sendTo( i, sm );
	}

	F_H[ procId ] = clock;
	F_M[ procId ] = MsgType.REL;

	displayState();
    }

    // Rule 5 : receive REL
    synchronized void receiveREL( int H, int p , int d ){

	System.out.println("Process " + procId + " reveiced REL from " + p);
	if ( clock < H ) clock = H;
	clock = clock + 1;

	F_H[ p ] = H;
	F_M[ p ] = MsgType.REL;

	displayState();

	this.notify();
    }

    // Access to receive function
    public SyncMessage recoit ( Door d ) {

	SyncMessage sm = (SyncMessage)receive( d );
	return sm;
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
