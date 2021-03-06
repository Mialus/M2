package projetAD;

//Java imports
import java.util.Queue;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

//Visidia imports
import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.Door;
import visidia.misc.property.PropertyTable;

public class Naimi extends Algorithm {
	
	File f;
	
	int voisinReponse = 0;
	int voisinReponseLeader =0;
	int father = -1;

	// All nodes data
    int procId;
    int next = -1;
    int owner;
    int leader = 0;
    // Higher speed means lower simulation speed
    int speed = 4;

    // Token 
    boolean token = false;

    // To display the state
    boolean waitForCritical = false;
    boolean inCritical = false;
    
    //creation du fichier
    
    // Correspondance table
    int nbVoisins = this.getArity();
    Map<Integer, Integer> map = new HashMap<>();
	
    // Critical section thread
    ReceptionRules rr = null;
    // State display frame
    DisplayFrame df;
    
	@Override
	public Object clone() {
		return new Naimi();
	}

	@Override
	public void init() {
		
		procId = getId();
		Random rand = new Random( procId );

		rr = new ReceptionRules(this);
		rr.start();

		// Display initial state + give time to place frames
		df = new DisplayFrame( procId );
		displayState();

	    f = new File ("traces"+procId+".txt");
	    try{
	        f.createNewFile();
	    }catch(IOException exception){}
		
		initial();
		
		while(true) {
		    // Wait for some time
		    int time = ( 3 + rand.nextInt(10)) * speed * 100;
		    System.out.println("Process " + procId + " wait for " + time);
		    try {
			Thread.sleep( time );
		    } catch( InterruptedException ie ) {}
		    
		    // Try to access critical section
		    waitForCritical = true;
		    askForCritical();

		    // Access critical
		    waitForCritical = false;
		    inCritical = true;
		    
		    displayState();

		    // Simulate critical resource use
		    time = (1 + rand.nextInt(3)) * 100;
		    System.out.println("Process " + procId + " enter SC " + time);
		    try {
			Thread.sleep( time );
		    } catch( InterruptedException ie ) {}
		    System.out.println("Process " + procId + " exit SC ");

		    // Release critical use
		    inCritical = false;
		    endCriticalUse();
		}
		
	}	
	
	//--------------------
    // Partie Gestion de fichier
    //-------------------
	
	public void printTraces(String str){
        try{
            FileWriter writer = new FileWriter(f, true); 
              // Writes the content to the file
              writer.write(str+"\n"); 
              writer.flush();
              writer.close();
        }
        catch (IOException exception){
            System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
        }
    }
	
	//--------------------
    // Partie Initialisation
    //-------------------
	
    // Rule 1 : Pi initialization
    synchronized void initial() {
		Door d = new Door();
		leader = procId;
		
		//Partie gerant que chaque processus ce connaissent
			SyncMessage sm = new SyncMessage(MsgType.START, procId);
			printTraces(procId + " envoie le message START a ses voisins");
			sendAll(sm);

	      /* try { this.wait(); } catch( InterruptedException ie) {ie.printStackTrace();}
	       voisinReponse=0;*/

		//Partie gerant l'election
			SyncMessage sm2 = new SyncMessage(MsgType.LEADER, procId);
			printTraces(procId + " envoie le message a ses voisins'le LEADER est "+procId+"'");
			sendAll(sm2);
		
        while( voisinReponseLeader < this.getArity() ) { 
            displayState();
            try { this.wait(); } catch( InterruptedException ie) {}
        }
        
        
		//Partie initialisant le leader
		
		if (leader == procId){
			token = true;
			owner = -1;
		} else {
			owner = leader;
		}
		
    }


	//--------------------
    // Partie Election
    //-------------------
	
	public synchronized void receiveLEADER (int d, int proc){
		printTraces(procId + " re�oit le message de " +d+" 'le LEADER est "+proc+"'");
    	if(proc>leader){
    		
    		leader = proc;
    		father = d;
    		voisinReponse =1;
    		
    		for(int i=0;i<this.getArity();i++){
    			// on envoi le leander � nos voisin
    				SyncMessage sm = new SyncMessage(MsgType.LEADER, proc);
    				printTraces(procId + " envoie le message a son voisin "+i+" 'le LEADER est "+proc+"'");
    				sendTo(i, sm);
    		}
    	}else if (proc == leader){
    		voisinReponse++;
    		if(voisinReponse == this.getArity()){
    			if(leader == this.procId){
    				for(int i = 0; i<this.getArity();i++){
	                    SyncMessage em = new SyncMessage(MsgType.IMLEADER, this.procId);
	                    printTraces(procId + " envoie le message IMLEADER a son voisin " + i);
	                    sendTo(i, em);
	                    voisinReponseLeader++;
    				}
    				notify();
    			}
    			
    		}
    	}
    	
    	displayState();
    }
	
	public synchronized void receiveIMLEADER (int d, int proc){
		printTraces(procId + " re�oit le message IMLEADER a " + d + " (le LEADER est "+proc+")");		
		if(proc != procId){
	        SyncMessage sm = new SyncMessage(MsgType.IMLEADER, proc);
	        
	        if(voisinReponseLeader  == 0){
	            //si nombre de voisin egale a 1, on envoie directement � son voisin unique
	            if(this.getArity() == 1){
	            	printTraces(procId + " envoie le message IMLEADER a " + d + " (le LEADER est "+proc+")");
	                sendTo(d,sm);
	            }
	            // Sinon en envoie � tout ses voisins
	            for (int i = 0; i< this.getArity();i++){
	               //if (i != j) {
	            	printTraces(procId + " envoie le message IMLEADER "+proc+")");
	                    boolean sent = sendTo(i,sm);
	                //}
	            }
	        }	
			leader = proc;
			voisinReponseLeader++;
			
	       /* if(voisinReponseLeader >= this.getArity()){
	        	printTraces(procId + " envoie le message IMLEADER a " + father + " (le LEADER est "+proc+")");
	            sendTo(father, sm);
	        }*/
			
			this.notifyAll();
			//System.out.println("Je suis "+procId + " voisin reponse " + voisinReponseLeader + " nbr voisin " + this.getArity());
			displayState();
		}
	}
    
	
	
	//--------------------
    // Partie Naimi
    //-------------------
	
	//permet de se souvenir du voisin
	public void receiveSTART (int d, int proc){
		printTraces(procId + " re�oit le message START de " + d);
    	map.put(proc,d);
    	/*voisinReponse++;
    	if(voisinReponse==this.getArity()){
    	this.notifyAll();
    	}*/
    	displayState();
    }

    //Pi ask for Critical Section
    synchronized void askForCritical(){
    	waitForCritical = true;
    	if (this.owner != -1) {
    		SyncMessage sm = new SyncMessage(MsgType.REQ, procId);
    		printTraces(procId + " envoie le message REQ a " + map.get(owner));
    	    sendTo(map.get(owner), sm);
    	    owner = -1;
    	    displayState();
    	    while (token != true){
    	    	try {
    	    		wait();
    	    	} catch (Exception e) { }
    	    }
    	}
    }

    //Pi receive message REQ from k
    public synchronized void receiveREQ (int k) {
    	
    	printTraces(procId + " re�oit le message REQ de " + k);
    	
    	if (owner == -1) {
    		if (waitForCritical == true || inCritical == true) {
    			next = k;
    		} else {
				token = false;
				SyncMessage sm = new SyncMessage(MsgType.TOKEN, procId);
				printTraces(procId + " envoie le message TOKEN a " + map.get(k));
				sendTo(map.get(k),sm);
				displayState();
    		}
    	} else {
    		SyncMessage sm = new SyncMessage(MsgType.REQ, k);
    		printTraces(procId + " envoie le message REQ a " + map.get(owner) + "(transmet la demande de "+k+")");
			sendTo(map.get(owner), sm);
			displayState();
    	}
    	owner = k;
    }
    
    //Pi receive TOKEN from j
    public synchronized void receiveTOKEN () {
    	printTraces(procId + " re�oit le message TOKEN");
    	token = true;
    	notify();
    }

    
    //Pi quit SC
    void endCriticalUse() {
    	waitForCritical = false; 
    	if (next != -1){
    		SyncMessage sm = new SyncMessage(MsgType.TOKEN, procId);
    		printTraces(procId + " envoie le message TOKEN a " + map.get(next));
			sendTo(map.get(next), sm);
			token = false;
			next = -1;
			displayState();
    	}
    }
    
    public SyncMessage recoit (Door door){
    	SyncMessage sm = (SyncMessage) receive(door);
    	return sm;
    }
    
	//--------------------
    // Partie Gestion des messages
    //-------------------
    
    
    // Display state
    void displayState() {	
		String state = new String("\n");
		state = state + "--------------------------------------\n";
		if ( inCritical ) 
		    state = state + "** ACCESS CRITICAL **";
		else if ( waitForCritical )
		    state = state + "* WAIT FOR *";
		else if (voisinReponse < this.getArity())
			state = state + "* WAIT ELECTION *";
		else
		    state = state + "-- SLEEPING --";
		
		Iterator<Integer> i = map.keySet().iterator();
		while(i.hasNext()){
			int key = i.next();
			state += "\n Voisin num�ro"+ key+ " : "+ map.get(key);
		}
		
		
		
		df.display( state );
    }
	
	
}