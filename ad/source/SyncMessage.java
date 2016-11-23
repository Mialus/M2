package projetAD;

import visidia.simulation.process.messages.Message;

public class SyncMessage extends Message {
    
    MsgType type;
    int proc;

    public SyncMessage() { }
    
    public SyncMessage( MsgType t, int p ) {
    	type = t;
    	proc = p;
    }
    
    public MsgType getMsgType() {
    	return type;
    }
    
    public int getProc() {
    	return proc;
    }
    
    @Override
    public Message clone() {
    	return new SyncMessage();
    }
    
    @Override 
    public String toString() {

	String r = type.toString() + "_" + proc;
	return r;
    }

    @Override 
    public String getData() {

	return this.toString();
    }

}
