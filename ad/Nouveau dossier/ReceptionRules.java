package projetAD;

import visidia.simulation.process.messages.Door;

// Reception thread
public class ReceptionRules extends Thread {
    
	Naimi algo;
    
    public ReceptionRules( Naimi naimi ) {
    	algo = naimi;
    }
    
    public void run() {
	    Door d = new Door();
		while(true) {
			SyncMessage m = (SyncMessage) algo.recoit(d);
			int door = d.getNum();
			
		    switch (m.getMsgType()) {
			
		    case TOKEN :
		    	algo.receiveTOKEN(m.getProc(), door);
			break;
			
		    case REQ :
		    	algo.receiveREQ (m.getProc(), door);
		    break;
		    
		    case START :
		    	algo.receiveSTART(door,m.getProc());
		    break;
		    
		    case ELECT :
		    	algo.receiveELECT(m.getProc(),door);
		    break;
		    
		    case LEADER :
		    	algo.receiveLEADER(m.getProc(),door);
		    break;
		    
		    default:
		    	System.out.println("Error message type");
			break;
		    }
		}
    }
}

