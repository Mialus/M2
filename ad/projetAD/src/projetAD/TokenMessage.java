package projetAD;

import visidia.simulation.process.messages.Message;

public class TokenMessage extends Message {
    
    MsgType type;
    
    public TokenMessage(MsgType t) {
	type = t;
    }

    public MsgType getMsgType() { return type; }
    
    @Override
    public Message clone() {
    	if(type==MsgType.TOKEN){
    		return new TokenMessage(MsgType.TOKEN);
    	}else{
    		return new TokenMessage(MsgType.REQ);
    	}

    }
    
    @Override 
    public String toString() {
    	String r;

    	switch (type) {
    	
    	case TOKEN: r = "TOKEN"; break;
    	case REQ: r = "REQ"; break;
    	default : r="";
    	}
	return r;
    }

    @Override 
    public String getData() {

	return this.toString();
    }

}
