
// Visidia imports
import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.Door;

// Reception thread
public class ReceptionRules extends Thread {
    
    LamportMutualExclusion algo;
    
    public ReceptionRules( LamportMutualExclusion a ) {
	
	algo = a;
    }
    
    public void run() {
	
	Door d = new Door();

	while( true ) {

	    SyncMessage m = (SyncMessage) algo.recoit(d);
	    int door = d.getNum();

	    switch (m.getMsgType()) {
		
	    case REQ :
		algo.receiveREQ( m.getMsgClock(), m.getMsgProc(), door );
		break;

	    case ACK :
		algo.receiveACK( m.getMsgClock(), m.getMsgProc(), door );
		break;
		
	    case REL :
		algo.receiveREL( m.getMsgClock(), m.getMsgProc(), door );
		break;
		
	    default:
		System.out.println("Error message type");
	    }
	}
    }
}

