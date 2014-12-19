import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;


public class WeatherNetSpider {
	/*
	 *  Given target url and grab the specific info
	 */

	//the info kind we are interested
	public static final String[] INFO_KIND={"TimeGMT","TemperatureC","Sea Level PressurehPa","Wind SpeedKm/h","Gust SpeedKm/h"};
	//number of info kind 
	public static final int NUM_OF_INFO_KIND=INFO_KIND.length;
	private URL url; 
	private HttpURLConnection httpCon;
	private int isConnected;
	private ArrayList<String>weatherInfo; 
	private String infoHead;
	private int[]infoIndex;


	public WeatherNetSpider(String urlStr) throws MalformedURLException
	{
		url=new URL(urlStr);	
		weatherInfo=new ArrayList<String>();
		isConnected=0;
		infoIndex=new int[NUM_OF_INFO_KIND];
	}

	public void setURL(String urlStr) throws MalformedURLException
	{
		url=new URL(urlStr);	
	}

	public int connect() throws IOException
	{
		URLConnection connection = url.openConnection();
		httpCon=(HttpURLConnection)connection;
		int code=httpCon.getResponseCode();
		if(code==HttpURLConnection.HTTP_OK)
		{
			isConnected=1;
		}
		else
		{
			isConnected=0;
		}
		return isConnected;		
	}

	public ArrayList<String> getInfo() throws IOException
	{
		weatherInfo.clear();
		if(isConnected==1)
		{
			BufferedReader in=new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) 
			{
				if(inputLine.length()!=0)
				{
					infoHead=inputLine;
					System.out.println(infoHead);
					getInfoIndex();
					break;
				}
				
			}
			
			while ((inputLine = in.readLine()) != null) 
			{
				parseInfo(inputLine);
				break;
			}
			in.close();
		}

		return weatherInfo;
	}
	
	//get info index through the infoHead
	private void getInfoIndex()
	{
		if(infoHead.length()==0)
		{
			System.out.println("can not find the info header");
		}
		else
		{
			
			
			ArrayList<String>infoHeaderItems=new ArrayList<String>();
			infoHeaderItems.addAll(Arrays.asList(infoHead.split(",")));
			
			for (int i = 0; i < infoIndex.length; i++) {
				infoIndex[i]=infoHeaderItems.indexOf(INFO_KIND[i]);
			}
			
		}
	}
	
	private void parseInfo(String infoItemStr)
	{
		System.out.println("in parseInfo:");
		ArrayList<String>infoItems=new ArrayList<String>();
		infoItems.addAll(Arrays.asList(infoItemStr.split(",")));
		
		for (int i = 0; i < infoIndex.length; i++) {
			System.out.println(infoItems.get(infoIndex[i]));
		}
		
	}
	

	public static void main(String[] args) {

		try {
			WeatherNetSpider spider=new WeatherNetSpider("http://www.wunderground.com/history/airport/EGLL/2010/11/30/DailyHistory.html?HideSpecis=1&format=1");
			if(spider.connect()!=0)
			{
//				for(String str:spider.getInfo())
//				{
//					System.out.println(str.length()+" "+str);
//				}
				spider.getInfo();
				
			}
			else
			{
				System.out.println("Can not access net");
			}

		} catch (MalformedURLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

}
