
// Visidia imports
import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.*;

// Reception thread
public class ReceptionRules extends Thread {
    
    Lelann algo;
    
    public ReceptionRules( Lelann a ) {
	
	algo = a;
    }
    
    public void run() {
	
	Door d = new Door();

	while( true ) {

	    SyncMessage m = (SyncMessage) algo.recoit(d);
	    int door = d.getNum();

	    switch (m.getMsgType()) {
		
	    case JETON :
		algo.receiveJETON();
		break;
		
	    default:
		System.out.println("Error message type");
	    }
	}
    }
}

