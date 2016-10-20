import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.edgestate.MarkedState;
import visidia.simulation.process.messages.IntegerMessage;
import visidia.simulation.process.messages.StringMessage;
import visidia.simulation.process.messages.Message;
import visidia.simulation.process.messages.Door;

class SyncMessage extends Message {

	String type;
	int    clock;

    @Override
    public Message clone() {
	return new SyncMessage("",0);
    }

    @Override 
    public String toString() {

	String display = new String(type + " " + String.valueOf(clock));

	return (display);
    }

    @Override 
    public String getData() {

	return (this.toString());
    }

    public SyncMessage( String s, int c) {
	    
	    type = s;
	    clock = c;

	}
    }

public class Premier extends Algorithm {

    @Override
    public Object clone() {
	return new Premier();
    }

    @Override
    public void init() {

	int nbNeighbors = getArity();

	for ( int i = 0 ; i < nbNeighbors; i++) {

	    SyncMessage sm = new SyncMessage("ACK", getId());
	    sendTo(i, sm);
	}

	Door d = new Door();
	SyncMessage m = (SyncMessage) receive(d);
	System.out.println("Node " + getId() + " received message " + m.toString() + " from " + d.getNum());
	
    }
}
