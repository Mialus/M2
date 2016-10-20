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

	while( true ) {

	    TokenMessage m = (TokenMessage) algo.recoit(d);
	    int door = d.getNum();

	    switch (m.getMsgType()) {
		
	    case TOKEN :
		algo.receiveTOKEN();
		break;
		
	    case REQ :
			algo.receiveREQ( door );
		break;

	    default:
		System.out.println("Error message type");
	    }
	}
    }
}

