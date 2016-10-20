package projetAD;


// Java imports
import java.util.Random;

// Visidia imports
import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.Door;

public class Naimi extends Algorithm {
    
    /**
	 * 
	 */
	// All nodes data
    int procId;
    int owner;
    int next = -1;
    // Higher speed means lower simulation speed
    int speed = 4;

    // Token 
    boolean token = false;

    // To display the state
    boolean waitForCritical = false;
    boolean inCritical = false;

    // Critical section thread
    ReceptionRules rr = null;
    // State display frame
    DisplayFrame df;

    public String getDescription() {

	return ("Lelann Algorithm for Mutual Exclusion");
    }

    @Override
    public Object clone() {
	return new Naimi();
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
	
	initial();
	
	// Display initial state + give time to place frames
	df = new DisplayFrame( procId );
	displayState();
	try { Thread.sleep( 1500 ); } catch( InterruptedException ie ) {}

	while( true ) {
	    
	    // Wait for some time
	    int time = ( 3 + rand.nextInt(10)) * speed * 100;
	    System.out.println("Process " + procId + " wait for " + time);
	    try {
		Thread.sleep( time );
	    } catch( InterruptedException ie ) {}
	    
	    // Try to access critical section
	    askForCritical();

	    displayState();

	    // Simulate critical resource use
	    time = (1 + rand.nextInt(3)) * 1000;
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

    //Rule 1 : initialize Pi
    
    synchronized void initial(){
    	if (this.procId == 0) {
    		this.token=true;
    		this.owner=-1;
    	}else{
        	this.owner=0;	
    	}
    }
    
    
    // Rule 2 : ask for critical section
    synchronized void askForCritical() {
	    waitForCritical = true;
    	if(this.owner!=-1){
    		TokenMessage tm = new TokenMessage(MsgType.REQ);
    		boolean sent = sendTo( owner, tm );
    		this.owner=-1;
			while( !token ) { 
		
			    displayState();
			    try { this.wait(); } catch( InterruptedException ie) {}
			}
    	}
    	
    }
    
    //Rule 3 : receive REQ(k) de j
    synchronized void receiveREQ(int d){
    	
    	if(this.owner==-1){
    	if ( waitForCritical == true ) {
        	next = ( d == 0 ? 1 : 0 );

    	} else {
    		this.token=false;
    	    // Forward token to successor
    	    TokenMessage tm = new TokenMessage(MsgType.TOKEN);
    	    boolean sent = sendTo( next, tm );
    	}
    	}else{
    		TokenMessage tm = new TokenMessage(MsgType.REQ);
    		boolean sent = sendTo( owner, tm );
    	}
    }

    // Rule 4 : receive TOKEN
    synchronized void receiveTOKEN(){
    	
	    token = true;
	    displayState();
    }

    // Rule 5 :
    void endCriticalUse() {


	if(next!=-1){
	TokenMessage tm = new TokenMessage(MsgType.TOKEN);
	boolean sent = sendTo( next, tm );
	token = false;
	next=-1;
	}
	displayState();
    }

    // Access to receive function
    public TokenMessage recoit ( Door d ) {

	TokenMessage sm = (TokenMessage)receive( d );
	return sm;
    }

    // Display state
    void displayState() {

	String state = new String("\n");
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
