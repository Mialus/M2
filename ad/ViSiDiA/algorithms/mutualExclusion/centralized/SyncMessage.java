
import visidia.simulation.process.messages.Message;
import visidia.simulation.process.messages.MessageType;

public class SyncMessage extends Message {
    
    MsgType type;

    public SyncMessage() {
	MessageType mt = new MessageType("Msg");
	mt.setColor(java.awt.Color.black);
	this.setType(mt);
    }
    
    public SyncMessage( MsgType t ) {
	
	System.out.println(t);
	type = t;
	MessageType mt = new MessageType("Msg");

	switch (type) {
		    
	case ACK :
	    mt.setColor(java.awt.Color.cyan);
	    this.setType(mt);
	    break;	

	case REQ :
	    mt.setColor(java.awt.Color.magenta);
	    this.setType(mt);
	    break;
	    
	case REL :
	    mt.setColor(java.awt.Color.red);
	    this.setType(mt);
	    break;
	}
    }
    
    public MsgType getMsgType() {

	return type;
    }
    
    @Override
    public Message clone() {
	return new SyncMessage();
    }
    
    @Override 
    public String toString() {

	return type.toString();
    }

    @Override 
    public String getData() {

	return type.toString();
    }

}
