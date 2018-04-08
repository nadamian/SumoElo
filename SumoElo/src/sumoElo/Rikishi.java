package sumoElo;

public class Rikishi {
	String name;
	double elo;
	String rank;
	double careerPeakElo;
	int eloRank;
	double prevElo = 0;
	int bouts;

	public Rikishi(String Name, String Rank, double Elo, double CareerPeakElo, int Bouts) {
		name = Name;
		rank = Rank;
		elo = Elo;
		careerPeakElo = CareerPeakElo;
		prevElo = 0;
		bouts = Bouts;
	}

	public Rikishi(String Name, String Rank, double Elo) {
		name = Name;
		rank = Rank;
		elo = Elo;
		if (elo > careerPeakElo){
			careerPeakElo = elo;}
		prevElo = 0;
		bouts = 0;
	}

	public String getName() {
		return name;
	}

	public double getElo() {
		return elo;
	}

	public String getRank() {
		return rank;
	}

	public double getCareerPeakElo() {
		return careerPeakElo;
	}

	public double getEloRank() {
		return eloRank;
	}

	public void setEloRank(int newEloRank) {
		eloRank = newEloRank;
	}

	public void setElo(double newElo) {
		prevElo = elo;
		elo = newElo;
		if(newElo > careerPeakElo){
			careerPeakElo = newElo;
		}
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setRank(String newRank) {
		rank = newRank;
	}
	public void setPrevElo(){
		elo = prevElo;
	}
	public void setBouts(int b){
		bouts = b;
	}
	public int getBouts(){
		return bouts;
	}
	public void newBout(){
		bouts++;
	}
}
