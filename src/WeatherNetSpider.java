import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class is the webspider for grabing the specific weather data 
 */
public class WeatherNetSpider {


	//the info kind we are interested
	public static final String[] INFO_KIND={"TimeGMT","TemperatureC","Sea Level PressurehPa","Wind SpeedKm/h","Gust SpeedKm/h","Precipitationmm"};
	public static enum InfoKind{TimeGMT,Temperature,AtPressure,WindSp,GustSp,PrecipitationMm};
	//number of info kind 
	public static final int NUM_OF_INFO_KIND=INFO_KIND.length;
	private URL url; 
	private HttpURLConnection httpCon;
	private int isConnected;

	private String infoHead;
	private int[]infoIndex;
	private ArrayList<String>time; 
	private ArrayList<String>temperature; 
	private ArrayList<String>atPressure; 
	private ArrayList<String>windSpeed;
	private ArrayList<String>gustSpeed;
	private ArrayList<String>precipitationMm;


	public WeatherNetSpider(String urlStr) throws MalformedURLException
	{
		url=new URL(urlStr);	

		isConnected=0;
		infoIndex=new int[NUM_OF_INFO_KIND];
		time=new ArrayList<String>();
		temperature=new ArrayList<String>();
		atPressure=new ArrayList<String>();
		windSpeed=new ArrayList<String>();
		gustSpeed=new ArrayList<String>();
		precipitationMm=new ArrayList<String>();
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

	public void getInfo() throws NoDataAvailable,IOException
	{

		if(isConnected==1)
		{
			BufferedReader in=new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) 
			{
				if(inputLine.length()!=0)
				{
					infoHead=inputLine;

					getInfoIndex();
					break;
				}

			}

			while ((inputLine = in.readLine()) != null) 
			{
				parseInfo(inputLine);

			}
			in.close();
		}


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
			//calc the interested info index in the infohead we get
			ArrayList<String>infoHeaderItems=new ArrayList<String>();
			infoHeaderItems.addAll(Arrays.asList(infoHead.split(",")));
			for (int i = 0; i < infoIndex.length; i++) {
				infoIndex[i]=infoHeaderItems.indexOf(INFO_KIND[i]);
			}

		}
	}

	private void parseInfo(String infoItemStr) throws NoDataAvailable
	{
		String[]infoItems;
		infoItems=infoItemStr.split(",");
		//if two data appears at the same time:
		//discard the first, reserve the second
		try{
			if(time.size()==0||!time.get(time.size()-1).equals(infoItems[infoIndex[0]]))
			{
				time.add(infoItems[infoIndex[0]]);
				temperature.add(infoItems[infoIndex[1]]); 
				atPressure.add(infoItems[infoIndex[2]]); 
				windSpeed.add(infoItems[infoIndex[3]]);
				gustSpeed.add(infoItems[infoIndex[4]]);
				precipitationMm.add(infoItems[infoIndex[5]]);
			}
			else
			{
				temperature.set(temperature.size()-1, infoItems[infoIndex[1]]);
				atPressure.set(atPressure.size()-1, infoItems[infoIndex[2]]);
				windSpeed.set(windSpeed.size()-1, infoItems[infoIndex[3]]);
				gustSpeed.set(gustSpeed.size()-1, infoItems[infoIndex[4]]);
				precipitationMm.set(gustSpeed.size()-1, infoItems[infoIndex[5]]);

			}
		}
		catch(IndexOutOfBoundsException ex)
		{
			throw new NoDataAvailable();
		}
	}




	/*
	 *  setters & getters
	 */

	public ArrayList<String> getTime() {
		return time;
	}

	public ArrayList<String> getTemperature() {
		return temperature;
	}

	public ArrayList<String> getAtPressure() {
		return atPressure;
	}

	public ArrayList<String> getWindSpeed() {
		return windSpeed;
	}

	public ArrayList<String> getGustSpeed() {
		return gustSpeed;
	}

	public ArrayList<String> getPrecipitationMm() {
		return precipitationMm;
	}

	public void setUrl(URL url) {
		this.url = url;
	}


	//output for testing
	public void printTest()
	{

		float sum=0;
		for (String wind : windSpeed) {
			sum+=Float.parseFloat(wind);
		}

		System.out.println("average wind speed:"+sum/windSpeed.size());

		sum=0;
		for (String temper : temperature) {
			sum+=Float.parseFloat(temper);
		}

		System.out.println("average tem:"+sum/temperature.size());
		System.out.println(precipitationMm.get(precipitationMm.size()-1));

	}

	public static void main(String[] args) {

		try {
			WeatherNetSpider spider=new WeatherNetSpider("http://www.wunderground.com/history/airport/EGLL/2010/11/30/DailyHistory.html?HideSpecis=1&format=1");
			if(spider.connect()!=0)
			{

				spider.getInfo();
				spider.printTest();

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
		catch (NoDataAvailable e) {
			// TODO: handle exception
			System.out.println("no data");
		}
	}

}
