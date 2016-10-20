
import visidia.simulation.process.messages.Message;

public class SyncMessage extends Message {
    
    MsgType type;
    int clock;
    int proc;

    public SyncMessage() {}
    
    public SyncMessage( MsgType t, int c, int p ) {
	
	type = t;
	clock = c;
	proc = p;
    }
    
    public MsgType getMsgType() {

	return type;
    }

    public int getMsgClock() {

	return clock;
    }

    public int getMsgProc() {

	return proc;
    }
    
    @Override
    public Message clone() {
	return new SyncMessage();
    }
    
    @Override 
    public String toString() {

	String r = type.toString() + "_" + clock + "_" + proc;
	return r;
    }

    @Override 
    public String getData() {

	return this.toString();
    }

}
