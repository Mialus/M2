package projetAD;

//Java imports
import java.util.Queue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

//Visidia imports
import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.Door;

public class Naimi extends Algorithm {

    //Données pour l'election
    int nae;
    int trecv = 0;
    int lrecv = 0;
    int father = 0;
    int lead = -1;
    ElectType state;
    //int[] neigh; // Ensemble des voisins de i  // Pas utile
    int nbNeighbors = 0;
    boolean init = false;
    int electednode = 0; //Noeud initiateur 
    boolean initiateur = false;
    
    //Création de la file pour naimi quelconque
    //file <Linked>;
    LinkedList<String> file = new LinkedList<String>();
    unknowNext unknowNext;

	// All nodes data
    int procId;
    int next = -1;
    int owner;
    int leader = -1;
    // Higher speed means lower simulation speed
    int speed = 4;

    // Token 
    boolean token = false;

    // To display the state
    boolean waitForCritical = false;
    boolean inCritical = false;
    
    // Correspondance table
    int nbVoisins = this.getArity();
    Map<Integer, Integer> map = new HashMap<>();

    // Critical section thread
    ReceptionRules rr = null;
    // State display frame
    DisplayFrame df;
    

	public Object clone() {
		return new Naimi();
	}

	@Override
	public void init() {
		
		procId = getId();
		Random rand = new Random( procId );
		
		//Initialisation des voisins
        nbNeighbors = getArity();

		rr = new ReceptionRules(this);
		rr.start();

		// Display initial state + give time to place frames
		df = new DisplayFrame( procId );
		displayState();
		
		// On determine les processus initateurs de l'election
		if(procId == 3 || procId == 4){
		    initiateur = true;
		}
		
		// On lance l'election
		initialisationElection();
		
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

	public void receiveSTART (int d, int proc){
    	map.put(proc,d);
    	displayState();
    }
    
	//--------------------
    // PARTIE NAIMI
    //-------------------

    // Règle 1 : Pi initialisation
    synchronized void initial() {
        //Pour commencer, on défni que c'est le leader qui a le token pour commencer
		if (leader == procId){
			token = true;
			owner = -1;
		} else {
			owner = leader;
		}
		
		Door d = new Door();
		for (int i=0; i<this.getArity(); i++) {
			SyncMessage sm = new SyncMessage(MsgType.START, procId);
			sendTo(i, sm);
		}
    }

    // Règle 2 : Pi demande Section Critique
    synchronized void askForCritical(){
    	waitForCritical = true;
    	if (this.owner != -1) {
    		SyncMessage sm = new SyncMessage(MsgType.REQ, procId);
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

    // Règle 3 : Pi recoit message REQ de k
    public synchronized void receiveREQ (int p, int d) {
    	
    	if(map.containsKey(p)/*== d*/){
    	     map.get(p);
    	}
    	else{
    	     file.offer(""+d); // ajouter element en fin de lsite si pas dans liste
    	}
    	
    	if (owner == -1) {
    		if (waitForCritical == true) {
    		    if(!map.containsKey(p)){
    		    unknowNext = new unknowNext (p,d);
    			    file.pollLast(); // enlever element à la fin, car pas besoin, sert jsute dnas la cas ou a besoin de la requetes
    			}
    			else{
    			    next = p;
    			}
    		} 
    		// cas ou j'envoie le jeton
    		else {
				token = false;
				SyncMessage sm = new SyncMessage(MsgType.TOKEN, procId);
				sendTo(map.get(p),sm);
				displayState();
				if(!map.containsKey(p)){
				 file.pollLast();
				}
    		}
    	} 
    	else {
    		SyncMessage sm = new SyncMessage(MsgType.REQ, p);
			sendTo(map.get(owner), sm);
			displayState();
    	}
    	owner = p;
    }
        // Quelconque : ajouter liste chainé linked liste, dès qu'une requete vient d'un processus qu'il ne connait pas, il la fait passé a son voisin, on ajoute dans la lsite, la porte dans laquel elle est passé -> receive requete   
    
    // Règle 4 : Pi recoit TOKEN de j
    public synchronized void receiveTOKEN (int p, int d) {
    	
    	notify();
    	if(p == procId){
    	    token = true;
    	    notifyAll();
    	}
    	else{
    	   SyncMessage sm = new SyncMessage(MsgType.TOKEN, procId);
		   sendTo(Integer.parseInt(file.poll()),sm);
		   displayState();
		}
    	//et envoie le file.poll() enmode TOKEN
    	//si token pas destiner à un de mes voisins, je lenlève de la file
    }

    
    // Règle 5 : Pi quitte Section Crique
    void endCriticalUse() {
    	waitForCritical = false; 
    	if (next != -1){
    		SyncMessage sm = new SyncMessage(MsgType.TOKEN, procId);
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
    
    
    
    // Display state
    void displayState() {	
		String state = new String("\n");
		state = state + "--------------------------------------\n";
		state = state + "nae : "+nae+"\n";
		state = state + "trecv : "+trecv+"\n";
		state = state + "lrecv : "+lrecv+"\n";
		state = state + "father: "+father+"\n";
		state = state + "lead: "+lead+"\n";
		
		state = state + "--------------------------------------\n";
		if ( inCritical ){
		    state = state + "** ACCESS CRITICAL **";
		    state = state + "\ntoken: "+token+"\n";
		}
		else if ( waitForCritical ){
		    state = state + "* WAIT FOR *";
		    state = state + "\ntoken: "+token+"\n";
		}
		else{
		    state = state + "-- SLEEPING --";
		    state = state + "\ntoken: "+token+"\n";
		}
		Iterator<Integer> i = map.keySet().iterator();
		/*while(i.hasNext()){
			int key = i.next();
			state += "\n Numéro Voisin."+ key+ " : "+ map.get(key);
		}
		*/
		df.display( state );
    }
    
     //--------------------
    // PARTIE ELECTION 
    //-------------------
    
    //Règle 1 :  Pi commence l'election
    synchronized void initialisationElection(){
        if(initiateur){
            nae = procId;
            for (int i = 0; i<nbNeighbors ; i++) {
                SyncMessage em = new SyncMessage(MsgType.ELECT, nae);
                boolean sent = sendTo(i, em);
            }
        }
        while( lrecv < nbNeighbors ) { 
            displayState();
            try { this.wait(); } catch( InterruptedException ie) {}
        }
        if(lead == procId){
            state = ElectType.LEADER;
        }
        else{
            state = ElectType.LOST;
        }
        //initial();
    }

    //Règle 2 :  Pi recoit TOKEN(v) de j
    synchronized void receiveELECT(int v, int j){
    
        //table de correspondance des voisins
        map.put(v,j);
        
        if(v> nae || nae == -1){
            nae = v;
            trecv = 1;
            father = j;
            
            if(nbNeighbors == 1){
                SyncMessage em = new SyncMessage(MsgType.ELECT, v);
                boolean sent = sendTo(j, em);
            }
            //Gestion si j'envoie a 1 voisin donc tu renvoie
            for(int i=0; i< nbNeighbors;i++) {
                // verif qu'il renvoie pas a celui qui lui a envoyé          
                if(i != j){
                    SyncMessage em = new SyncMessage(MsgType.ELECT, v);
                    boolean sent = sendTo(i, em);
                }
            }
        }
        else if(v == nae){
            trecv =trecv+1;
            if(trecv == nbNeighbors){
                if(nae==procId){
                    for(int i = 0; i< nbNeighbors;i++) {
                        SyncMessage em = new SyncMessage(MsgType.LEADER, procId);
                        boolean sent = sendTo(i, em);
                    }
                    notify();
                    
                }
                else{
                    SyncMessage em = new SyncMessage(MsgType.ELECT, nae); //peut etre mettre procID
                    boolean sent = sendTo(father, em);
                }
            }
        }
        displayState();
    }

    // Règle 3 : Pi recoit LEADER(v) de j
    synchronized void receiveLEADER(int v, int j){
      //Pour faire commencer Naimi au leader dès qu'il sait qu'il est le leader
      if(v != procId && this.state != ElectType.ASLEEP){ 
        SyncMessage sm = new SyncMessage(MsgType.LEADER, v);
        if(lrecv  == 0){
            //si nombre de voisin egale a 1, on envoie directement à son voisin unique
            if(nbNeighbors == 1){
                sendTo(j,sm);
            }
            // Sinon en envoie à tout ses voisins
            for (int i = 0; i< nbNeighbors;i++){
               //if (i != j) {
                    boolean sent = sendTo(i,sm);
                //}
            }
        }
        lead = v;
        lrecv = lrecv + 1;
        
        // On vérifie que tout les voisins est bien recu le message
        if(lrecv >= nbNeighbors){
            sendTo(father, sm);
        }
        this.notifyAll();
        displayState();

    }
  }
		
}
