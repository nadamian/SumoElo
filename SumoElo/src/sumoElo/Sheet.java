package sumoElo;

import java.util.ArrayList; 
import java.util.Scanner;
import java.io.*;

public class Sheet {
	static Scanner console = new Scanner(System.in);
	private static int numberOfStats = 4;
	public static ArrayList<Rikishi> wrestlers = new ArrayList<Rikishi>();
	//Will have use implemented eventually 
	public static ArrayList<Rank> Ranks = new ArrayList<Rank>();

	//sets up CSV for saving 
	private static String tokenizedList(ArrayList<Rikishi> wrestlers, ArrayList<Rank> ranks) {
		String tokenizedList = "";
		for (Rikishi rikishi : wrestlers) {
			tokenizedList += rikishi.getName() + ",";
			tokenizedList += rikishi.getRank() + ",";
			tokenizedList += rikishi.getCareerPeakElo() + ",";
			tokenizedList += rikishi.getElo() + ",";
			tokenizedList += rikishi.getBouts();
			tokenizedList += System.lineSeparator();
		}
		tokenizedList += "done";
		return tokenizedList;
	}
	/*Deals with user input still takes commands but most 
	 *of its old job is gone thanks to automation #topical*/
	public static String getCommand(Scanner console) {
		System.out.print("Enter a command: ");
		String command = console.nextLine();
		return command;
	}
	//does things with user input
	private static void handleCommand(String command) throws FileNotFoundException {
		// ignore empty input
		if (command == null || command.isEmpty())
			return;
		// if "edit" allow user to select a rikishi to edit and give them the
		// option to change any stat
		//probably gonna repurpose this to just check for new rikishi automatically
		if (command.equals("new")) {
			System.out.println("Enter the Rikishi's name");
			String name = console.nextLine();
			System.out.println("Enter the Rikishi's rank");
			String rank = console.nextLine();
			if (isValidName(name) && isValidRank(rank)) {
				wrestlers.add(new Rikishi(name, rank, 1000.0));
				return;
			}
		}
		//prints out list and breakdown of all wrestlers 
		if (command.equals("print")) {
			String line = "";
			for (Rikishi rikishi : wrestlers) {
				line += rikishi.getEloRank() + " " + rikishi.getName() + " " + rikishi.getElo()
						+ System.lineSeparator();
			}
			System.out.println(line);
			return;
		}
		//where most of the user input happens
		if (command.equals("bouts")) {
			ResultParser.collect();
			ArrayList<String> east = ResultParser.getEast();
			ArrayList<String> west = ResultParser.getWest();
			ArrayList<Boolean> wins = ResultParser.getWins();
			for(String name:east) {
				Boolean namethere = false;
				for(Rikishi rikishi:wrestlers) {
					if(rikishi.getName().equals(name)) {
						namethere = true; 
					}
				}
				if(!namethere) {
					Rikishi newGuy = new Rikishi(name, "M16", 1000, 1000, 1);
					wrestlers.add(newGuy);
				}
			}
			for(String name:west) {
				Boolean namethere = false;
				for(Rikishi rikishi:wrestlers) {
					if(rikishi.getName().equals(name)) {
						namethere = true; 
					}
				}
				if(!namethere) {
					Rikishi newGuy = new Rikishi(name, "M16", 1000, 1000, 1);
					wrestlers.add(newGuy);
				}
			}
			for(int i = 0; i < wins.size(); i++) {
				String e = east.get(i);
				String w = west.get(i);
				String win = "";
				String lose = "";
				if(wins.get(i)) {
					win = e;
					lose = w;
				}
				else {
					win = w;
					lose = e;
				}
				Rikishi winner = null;
				Rikishi loser = null;
				for (Rikishi rikishi : wrestlers) {
					if (rikishi.getName().equals(win)) {
					winner = rikishi;
				}
				if (rikishi.getName().equals(lose)) {
					loser = rikishi;
				}
				}
				Calculator.calcElo(winner, loser, wrestlers, Ranks);
			}
			return;
		}
		
		if (command.equals("read")) {
			readFile();
			return;
		}
		if (command.equals("sort")) {
			selectionSort();
			return;
		}
		//removes rikishi from list
		if (command.equals("retire")){
			System.out.println("Who Has Retired?");
			String name = console.nextLine();
			for(Rikishi rikishi : wrestlers){
				if (name.equals(rikishi.getName())){wrestlers.remove(rikishi);
				return;}
			}
			return;
		}
		//unimplemented
		if(command.equals("rank data")){
			for(Rank rank : Ranks){
				System.out.println(rank.getElo());
			}
			return;
		}
		
		if(command.equals("change rank")){
				System.out.println("Whos Rank Has Changed?");
				String name = console.nextLine();
				System.out.println("What is their new rank?");
				String rank = console.nextLine();
				for(Rikishi rikishi : wrestlers){
					if(name.equals(rikishi.getName())){rikishi.setRank(rank);}
				}
				return;
		}
		//for testing - accesses different file
		if(command.equals("Test Read")){
			readTestFile();
			return;
		}
		//saves to test file
		if(command.equals("Test Save")){
			String tokenizedList = tokenizedList(wrestlers, Ranks);
			File outfile = new File("test.txt");
			PrintStream output = new PrintStream(outfile);
			output.println(tokenizedList);
			System.out.println("all done");
			output.close();
			return;
		}

		System.out.println("No valid command entered. Please enter a valid command");
		command = getCommand(console);
	}
	//checks if bout syntax is valid
	private static boolean isBout(String bout){
		boolean Bout = false; 
		if(bout.indexOf(" ") < 2){ return Bout;}
		if(bout.charAt(bout.lastIndexOf(" ") - 1) == ('v')){Bout = true;}
		return Bout;
	}
	//check if rank syntax is valid
	private static boolean isValidRank(String rank) {
		boolean isRank = false;
		if (rank.charAt(0) == 'M' && Integer.parseInt(rank.charAt(1) + "") > 0)
			isRank = true;
		if (rank.length() == 1
				&& (rank.charAt(0) == 'K' || rank.charAt(0) == 'S' || rank.charAt(0) == 'O' || rank.charAt(0) == 'Y') || rank.charAt(0) == 'J') {
			isRank = true;
		}
		return isRank;
	}

	private static boolean isValidName(String name) {
		return (!name.contains(" "));
	}

	private static void selectionSort() {
		for(int j = 0; j < wrestlers.size(); j++){
			Rikishi max = wrestlers.get(j); 
			for(int i = j; i < wrestlers.size(); i++){
				if(wrestlers.get(i).getElo() > max.getElo()){
					max = wrestlers.remove(i);
					wrestlers.add(j, max); 
				}
			}
		}
		for (int i = 0; i < wrestlers.size(); i++) {
			wrestlers.get(i).setEloRank(i + 1);
		}
	}

	public static void readFile() throws FileNotFoundException {
		FileReader file = new FileReader("rikishi.txt");
		Scanner sc = new Scanner(file);
		String stats = sc.nextLine();
		while (sc.hasNextLine()) {
				String name = stats.substring(0, stats.indexOf(","));
				stats = stats.substring(stats.indexOf(",") + 1);
				String rank = stats.substring(0, stats.indexOf(","));
				stats = stats.substring(stats.indexOf(",") + 1);
				double peakElo = Double.parseDouble(stats.substring(0, stats.indexOf(",")));
				stats = stats.substring(stats.indexOf(",") + 1);
				double elo = Double.parseDouble(stats.substring(0, stats.indexOf(",")));
				stats = stats.substring(stats.indexOf(",") + 1);
				int bouts = Integer.parseInt(stats.substring(0, stats.length()));
				wrestlers.add(new Rikishi(name, rank, elo, peakElo, bouts));
				stats = sc.nextLine();
			}
		selectionSort();
		for (int i = 1; i < wrestlers.size(); i++) {
			wrestlers.get(i).setEloRank(i + 1);
		}
	}
	
	public static void readTestFile() throws FileNotFoundException {
		FileReader file = new FileReader("test.txt");
		Scanner sc = new Scanner(file);
		String stats = sc.nextLine();
		while (sc.hasNextLine()) {
				String name = stats.substring(0, stats.indexOf(","));
				stats = stats.substring(stats.indexOf(",") + 1);
				String rank = stats.substring(0, stats.indexOf(","));
				stats = stats.substring(stats.indexOf(",") + 1);
				double peakElo = Double.parseDouble(stats.substring(0, stats.indexOf(",")));
				stats = stats.substring(stats.indexOf(",") + 1);
				double elo = Double.parseDouble(stats.substring(0, stats.indexOf(",")));
				stats = stats.substring(stats.indexOf(",") + 1);
				int bouts = Integer.parseInt(stats.substring(0, stats.length()));
				wrestlers.add(new Rikishi(name, rank, elo, peakElo, bouts));
				stats = sc.nextLine();
			}
		selectionSort();
		for (int i = 1; i < wrestlers.size(); i++) {
			wrestlers.get(i).setEloRank(i + 1);
		}
	}

	public static void main(String[] args) throws IOException, FileNotFoundException {
		// create fileReader for rikishi and scanner to read
		/*
		 * traverse ArrayList once gotten all rikishi and order by elo changing
		 * eloRank using counter
		 */
		System.out.println("Welcome");
		String command = getCommand(console);
		while (!command.equals("Close")) {
			handleCommand(command);
			command = getCommand(console);
		}
		String tokenizedList = tokenizedList(wrestlers, Ranks);
		File outfile = new File("rikishi.txt");
		PrintStream output = new PrintStream(outfile);
		output.println(tokenizedList);
		System.out.println("all done");
		output.close();
	}
}
