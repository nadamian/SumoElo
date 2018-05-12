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
	
	public static void main(String[] args){
		
		try { Response response = Jsoup.connect("http://sumodb.sumogames.de/Results.aspx?b=201801&d=7").data("result", "tk_kekka", "east", "tk_east","west", "tk_west").userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.121 Safari/535.2")
                .method(Method.POST)
                .timeout(0).ignoreHttpErrors(true)
                .execute();
		
		String rawHTML = response.body();
		Document doc = Jsoup.parse(rawHTML);
		//System.out.print(doc);
		Elements temp = doc.select("td.tk_kekka");
		Element [] winLoss = temp.toArray(new Element[]{});
		System.out.println(winLoss[1]);
		temp = doc.select("td.tk_east");
		ArrayList<String> east = new ArrayList<String>();
			for(Element RikishiList:temp){
			east.add(RikishiList.getAllElements().first().text().toString());
			}
		System.out.println(east);
		temp = doc.select("td.tk_west");
		ArrayList<String> west = new ArrayList<String>();
			for(Element RikishiList:temp){
			west.add(RikishiList.getAllElements().first().text().toString());
			}
		System.out.println(west);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
