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
		    	algo.receiveTOKEN();
			break;
			
		    case REQ :
		    	algo.receiveREQ (m.getProc());
		    break;
		    
		    case LEADER :
		    	algo.receiveLEADER(door,m.getProc());
		    break;
		    
		    case START :
		    	algo.receiveSTART(door,m.getProc());
		    break;
		    
		    case IMLEADER :
		    	algo.receiveIMLEADER(door,m.getProc());
		    break;
		    
		    default:
		    	System.out.println("Error message type");
			break;
		    }
		}
    }
}

