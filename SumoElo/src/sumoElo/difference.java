package sumoElo;

public class difference {
	int difference;
	int higherWin; 
	int lowerWin; 
	
	public difference(int higher, int lower, int difference){
	higherWin = higher;
	lowerWin = lower;
	}
	public difference()
	{
		higherWin = 1;
		lowerWin = 1;
	}
	public void higher(){higherWin++;}

	public void lower(){lowerWin++;}
	
	public int getDifference(){return difference;}
	
	public int getHigher(){return higherWin;}
	
	public int getLower(){return lowerWin;}
}
