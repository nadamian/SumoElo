package sumoElo;

import java.util.ArrayList;

public class Calculator {
	//constants that determine max Elo gained or lost
	private static int KFactor = 15;
	private static int INFK = 50;
	
	public static void calcElo(Rikishi winner, Rikishi loser, ArrayList<Rikishi> wresters, ArrayList <Rank> ranks) {
		// calculate change in elo for winner and loser
		
		// winner and loser elo
		double winnerElo = winner.getElo();
		double loserElo = loser.getElo();
		double finalWinnerElo = winnerElo;
		double finalLoserElo = loserElo;
		double difference = winnerElo - loserElo;
		
		//Winner
		//Use inflated k factor for Rikishi with fewer than 20 bouts
		if(winner.getBouts() < 20){
			finalWinnerElo = winnerElo + INFK * (1 - adjustedDifference(winnerElo, loserElo));
		}
		else{
			finalWinnerElo = winnerElo + KFactor * (1 - adjustedDifference(winnerElo, loserElo));
		}
		winner.setElo(finalWinnerElo);
		
		//Loser
		if(loser.getBouts() < 20){
			finalLoserElo = loserElo + INFK * (0 - adjustedDifference(loserElo, winnerElo));
		}
		else{
		finalLoserElo = loserElo + KFactor * (0 - adjustedDifference(loserElo, winnerElo));
		}
		loser.setElo(finalLoserElo);
		
		//Print Changes
		System.out.println(winner.getName() + " " + winnerElo + "->" + winner.getElo());
		System.out.println(loser.getName() + " " + loserElo + "->" + loser.getElo());
		
		//This is for external use and will be removed when all data processing is coded 
		int roundedDifference;
		if(difference >= 0){
		roundedDifference = (((int)Math.round(difference)+5)/10)*10;
		}
		else{
			roundedDifference = (((int)Math.round(difference)/10))*10;
		}
		System.out.println("Difference: " + roundedDifference);
		
		//To be implemented later
		rankStats(winner, loser, ranks);
		rankStats(loser, winner, ranks);
	}
	
	//First Part of Calculation
	public static double adjustedDifference(double elo1, double elo2) {
		double exp1 = elo1/400.0;
		double exp2 = elo2/400.0;
		double num = Math.pow(10, exp1);
		double den1 = Math.pow(10, exp1);
		double den2 = Math.pow(10, exp2);
		double den = den1+den2;
		return num / den;
	}
	
	//Unimplemented
	public static void rankStats(Rikishi one, Rikishi two, ArrayList <Rank> ranks){
		for(Rank rank : ranks){
			if(one.getRank().equals(rank.getRank())){ 
				rank.newBout(two);
			}
		}
	}
	//For Testing
	public static void main(String[] args) {
	Rikishi Hakuho = new Rikishi("Hakuho", "Y", 1700);
	Rikishi Tochiozan = new Rikishi("T", "S", 1500);
	ArrayList <Rikishi> rikishi = new ArrayList <Rikishi>();
	ArrayList <Rank> ranks = new ArrayList <Rank>();
	rikishi.add(Hakuho);
	rikishi.add(Tochiozan);
	calcElo(Tochiozan, Hakuho, rikishi, ranks);
	}
	//testing not working
}
