package sumoElo;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ResultParser {
	private static ArrayList<String> east = new ArrayList<String>();
	private static ArrayList<String> west = new ArrayList<String>();
	private static ArrayList<Boolean> eastWin = new ArrayList<Boolean>();
	
	//reads data from webpage needs link pasted in (Will fix in time)
	public static void cutDown(ArrayList<String> direction) {
		int i = 0;
		int check = 0;
		int stop = 0;
		for(String rikishi:direction) {
			if (rikishi.substring(0,1).equals("J") && stop < 1) {
				check = i;
				stop = 2;
			}
			i++;
		}
		for(int j = check; j < direction.size(); j += 0) {
			direction.remove(j);
		}
	}
	public static void collect() {
		try { 
			Response response = Jsoup.connect("http://sumodb.sumogames.de/Results.aspx?b=200001&d=1").data("result", "tk_kekka", "east", "tk_east","west", "tk_west").userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.121 Safari/535.2")
                .method(Method.POST)
                .timeout(0).ignoreHttpErrors(true)
                .execute();
		
		String rawHTML = response.body();
		Document doc = Jsoup.parse(rawHTML);
		/*This step is weird. The site only displays win-loss data as an image 
		 * (probably to make it harder to scrape), so I have to get the text 
		 * linking to the image file (either a white [for win] or black [for loss]
		 * dot and convert that text into a boolean ArrayList. 
		 */
		Elements temp = doc.select("td.tk_kekka");
		Element [] winLoss = temp.toArray(new Element[]{});
		//check for readable data
		for(int i = 0; i < winLoss.length; i+=2){
			String el = winLoss[i].toString();
			if(el.substring(41, 42).equals("s")) {
				eastWin.add(true);
			}
			else {
				eastWin.add(false);
			}
		}
		//check for correct vals in eastWin
		//getting east rikishi
		temp = doc.select("td.tk_east");
			for(Element RikishiList:temp){
			east.add(RikishiList.getAllElements().first().text().toString());
			}
			//test for correct info
		//getting west Rikishi
		temp = doc.select("td.tk_west");
			for(Element RikishiList:temp){
			west.add(RikishiList.getAllElements().first().text().toString());
			}
			cutDown(east);
			cutDown(west); 
			int shorter = east.size();
			if (west.size()<east.size()) {
				shorter = west.size();
			}
			for(int i = shorter; i < eastWin.size(); i += 0) {
				eastWin.remove(i);
			}
			//test for correct info
		//System.out.println(west);
		//System.out.println(eastWin.size());
		//System.out.println(east.size());
		//System.out.println(west.size());
		//System.out.println(east);
		//System.out.println(west);
		//System.out.println(eastWin);
			for(int i = 0; i < east.size(); i++) {
				String fixed = east.remove(0);
				east.add(fixed.substring(0, fixed.indexOf(" ", 6)));
			}
			for(int i = 0; i < west.size(); i++) {
				String fixed = west.remove(0);
				west.add(fixed.substring(0, fixed.indexOf(" ", 6)));
			}
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<Boolean> getWins(){
		return eastWin;
	}
	public static ArrayList<String> getEast(){
		return east;
	}
	public static ArrayList<String> getWest(){
		return west; 
	}
	public static void main(String[] args){
		collect();
	}
}
