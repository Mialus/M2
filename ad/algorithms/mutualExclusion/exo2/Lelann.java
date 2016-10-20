
// Java imports
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

// Visidia imports
import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.*;

public class Lelann extends Algorithm {
	boolean Jeton;
	boolean SC;
    int procId;
    int nbNeighbors;
    
    // Reception thread
    ReceptionRules rr = null;
    
    public String getDescription() {

	return ("Lelann Algorithm");
    }

	
@Override
public Object clone() {
	return new Lelann();
}

@Override
public void init(){
	procId = getId();
	nbNeighbors = getArity();
	
	if(procId==0){
		Jeton=true;
	}else{
		Jeton=false;
	}
	
	rr = new ReceptionRules( this );
	rr.start();
	
	while( true ) {
	    
	    // Wait for some time before simulation
	    System.out.println("Process " + procId);
	    try {
		Thread.sleep( 300 );
	    } catch( InterruptedException ie ) {}
	    
	    // Try to access critical section
	    askForCritical();

	    // Simulate critical resource use
	    System.out.println("Process " + procId);
	    try {
		Thread.sleep( 300 );
	    } catch( InterruptedException ie ) {}
	    System.out.println("Process " + procId + " exit SC ");

	    // Release critical use
	    endCriticalUse();
	}
}


//--------------------
// Rules
//-------------------

// Rule 1 : ask for critical section
synchronized void askForCritical() {

SC=true;

while(!Jeton){
	try{
this.wait();
	}catch( InterruptedException ie ){
		
	}
}
}

// Rule 2 : receive REQ
synchronized void receiveJETON(){
	if(SC){
		Jeton = true;
	}else{
		if (procId==0){
			SyncMessage sm = new SyncMessage(MsgType.JETON, 0, procId);
			sendTo(0, sm);
		}else{
			SyncMessage sm = new SyncMessage(MsgType.JETON, 1, procId);
			sendTo(1, sm);
		}
	}
	this.notifyAll();

}

// Rule 3 : receive ACK
synchronized void endCriticalUse(){
SC = false;
Jeton=false;

if (procId==0){
	SyncMessage sm = new SyncMessage(MsgType.JETON, 0, procId);
	sendTo(0, sm);
}else{
	SyncMessage sm = new SyncMessage(MsgType.JETON, 1, procId);
	sendTo(1, sm);
}

}

// Access to receive function
public SyncMessage recoit ( Door d ) {

SyncMessage sm = (SyncMessage)receive( d );
return sm;
}
}
