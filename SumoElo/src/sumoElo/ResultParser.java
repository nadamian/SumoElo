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
	public static void collect() {
		try { 
			Response response = Jsoup.connect("http://sumodb.sumogames.de/Results.aspx?b=201801&d=8").data("result", "tk_kekka", "east", "tk_east","west", "tk_west").userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.121 Safari/535.2")
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
		System.out.println(winLoss[0]);
		System.out.println(winLoss[1]);
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
		System.out.println(eastWin);
		//getting east rikishi
		temp = doc.select("td.tk_east");
			for(Element RikishiList:temp){
			east.add(RikishiList.getAllElements().first().text().toString());
			}
			//test for correct info
		System.out.println(east);
		//getting west Rikishi
		temp = doc.select("td.tk_west");
			for(Element RikishiList:temp){
			west.add(RikishiList.getAllElements().first().text().toString());
			}
			//test for correct info
		System.out.println(west);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		collect();
	}
}
