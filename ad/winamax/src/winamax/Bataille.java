package winamax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Bataille {
	
	private static final String NOBODY_WINS = "PAT";
	private static final String PLAYER_1_WIN = "1";
	private static final String PLAYER_2_WIN = "2";
	
	public static class Card{
		
		private int value;

		private Card(int val) {
			this.value = val;
		}
		
		public static Card CardToValue(String val){
			String tmpVal = val.substring(0, val.length() - 1);
			switch (tmpVal) {
				case "J": return new Card(11);
				case "Q": return new Card(12);
				case "K": return new Card(13);
				case "A": return new Card(14);
				default: return new Card(Integer.parseInt(tmpVal));
			}
		}

		public int getValue() {
			return value;
		}

		public int compareTo(Card cardOpponent) {
			return Integer.compare(value, cardOpponent.getValue());
		}

			
	}
	
	public static class Player{
		private LinkedList<Card> myCard, cardsAtStake;

		public Player(LinkedList<Card> myCard) {
			this.myCard = myCard;
			this.cardsAtStake = new LinkedList<>();
		}
		
		public int getCardListSize(){
			return myCard.size();
		}
		
		public Card playCard(){
			Card c = myCard.poll();
			cardsAtStake.offer(c);
			return c;
		}
		
		public void playBattle(){
			for (int i = 0; i < 3; i++) {
				playCard();
			}
		}
		
		public void collectCards(Player j){
			while (!j.cardsAtStake.isEmpty()) {
				this.myCard.offer(j.cardsAtStake.poll());
			}
		}
		
		public void clear(){
			myCard.clear();
		}
				
	}
	
	public static class Game{
		private int rounds;
		private Player p1, p2;
		
		public Game(Player p1, Player p2) {
			this.rounds = 0;
			this.p1 = p1;
			this.p2 = p2;
		}
		
		public void play(){
			while (p1.getCardListSize() > 0 && p2.getCardListSize() > 0) {
				rounds++;
				playBattle();
			}
			
		}
		
		private void playBattle(){
			Card carteJ1 = p1.playCard();
			Card carteJ2 = p2.playCard();
			battle(carteJ1, carteJ2);
		}

		private void battle(Card carteJ1, Card carteJ2) {
			if (carteJ1.compareTo(carteJ2) > 0) {
				assignWinningsCard(p1);
			}else if(carteJ1.compareTo(carteJ2) < 0){
				assignWinningsCard(p2);
			}else{
				playWar();
			}
			
		}

		private void playWar() {
			if(p1.getCardListSize() >= 4 && p2.getCardListSize() >= 4){
				p1.playBattle();
				p2.playBattle();
				playBattle();
			}else{
				p1.clear();
				p2.clear();
			}
			
		}

		private void assignWinningsCard(Player p) {
			p.collectCards(p1);
			p.collectCards(p2);			
		}

		
		public String getWinnerAndRounds(){
			if (p1.getCardListSize() > 0) {
				return PLAYER_1_WIN + " "+ rounds;
			}else if(p2.getCardListSize() > 0){
				return PLAYER_2_WIN+ " "+ rounds;
			}else{
				return NOBODY_WINS;
			}
		}
		
	}
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player player1 = null, player2 = null;

		String filename = args[0];
		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(filename);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(filename));
			LinkedList<Card> j1 = new LinkedList<>();
			int nbCarte = Integer.parseInt(br.readLine());
			for (int i = 0; i < nbCarte; i++) {
				j1.add(Card.CardToValue(br.readLine()));
			}
			
			LinkedList<Card> j2 = new LinkedList<>();
			nbCarte = Integer.parseInt(br.readLine());
			for (int i = 0; i < nbCarte; i++) {
				j2.offer(Card.CardToValue(br.readLine()));
			}
			
			player1 = new Player(j1);
			player2 = new Player(j2);
			Game game = new Game(player1, player2);
			game.play();
			System.out.println(game.getWinnerAndRounds());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
