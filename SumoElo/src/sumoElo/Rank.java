package sumoElo;

public class Rank {
	private String rank;
	private double avgOpElo;
	private int bouts;

	public Rank(String Rank) {
		rank = Rank;
	}
	
	public Rank(String Rank, double elo, int Bouts){
		rank = Rank;
		avgOpElo = elo;
		bouts = Bouts;
	}

	public int getBouts(){
		return bouts; 
	}
	public String getRank() {
		return rank;
	}

	public double getElo() {
		return avgOpElo;
	}

	public void setRank(String Rank) {
		rank = Rank;
	}

	public void setElo(double elo) {
		avgOpElo = elo;
	}

	public void newBout(Rikishi opponent) {
		double totalElo = avgOpElo * bouts;
		bouts++;
		if (bouts < 2) {
			totalElo = opponent.getElo();
		}

		totalElo += opponent.getElo();
		avgOpElo = totalElo / bouts + 1;
		return;
	}

}
