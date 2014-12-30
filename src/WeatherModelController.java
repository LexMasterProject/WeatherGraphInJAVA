import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class WeatherModelController {

    public static final String[]airports={"EGLL"};
    public static final String searchTemplate="http://www.wunderground.com/history/"
	+ "airport/AAAA/YYYY/MM/DD/DailyHistory.html?HideSpecis=1&format=1";
    private String searchStr;
    private DateModelController mydate;
    private int airportIndex;
    private WeatherNetSpider spider;
    private ArrayList<String>time;
    private float temperature[];
    private float atPressure[];
    private float windSpeed[];
    private float gustSpeed[];
    private float precipitationMm[];
	
    public WeatherModelController()
    {
    	mydate=new DateModelController();
    	try {
    		spider=new WeatherNetSpider("http://www.wunderground.com/history/airport/EGLL/2010/11/30/DailyHistory.html?HideSpecis=1&format=1");
    	} catch (MalformedURLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }

    //get info through spider
    public void getInfo()
    {
    	try {
    		if(spider.connect()!=0)
    		{
    			spider.getInfo();		
    			time=spider.getTime();
    			temperature=arrayListStrToFloatArr(spider.getTemperature());
    			atPressure=arrayListStrToFloatArr(spider.getAtPressure());
    			windSpeed=arrayListStrToFloatArr(spider.getWindSpeed());
    			gustSpeed=arrayListStrToFloatArr(spider.getGustSpeed());
    			precipitationMm=arrayListStrToFloatArr(spider.getPrecipitationMm());
    		}
    		else
    		{
    			System.out.println("Can not access net");
    		}
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
    public float[] getAtPressure() {
		return atPressure;
	}

	public float[] getWindSpeed() {
		return windSpeed;
	}

	public float[] getGustSpeed() {
		return gustSpeed;
	}

	public float[] getPrecipitationMm() {
		return precipitationMm;
	}

	//transform from arraylist<String> to float array
	private float[]arrayListStrToFloatArr(ArrayList<String>arrlist)
	{
		float[] arr=new float[arrlist.size()];
		for (int i = 0; i < arrlist.size(); i++) {
			try{
				arr[i]=Float.parseFloat(arrlist.get(i));
			}
			catch(NumberFormatException ex)
			{
				arr[i]=0;
			}
		}
		return arr;
	}
    public void setSearchData(String location,DateModelController mydate)
    {
    	
    }
	
	public float[] getTemperature() 
	{
		return temperature;
	}
	
	/*
	 *  testing code
	 */
	public void printFloatArr(float[]arr)
	{
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
	public void printStringArr(String[]arr)
	{
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
	public void printStrArrayList(ArrayList<String>arr)
	{
		for (int i = 0; i < arr.size(); i++) {
			System.out.print(arr.get(i)+" ");
		}
		System.out.println();
	}
	
    public static void main(String[] args)
    {
		WeatherModelController wmc=new WeatherModelController();
		wmc.getInfo();
		
		//print time
		System.out.println("time:");
		wmc.printStrArrayList(wmc.getTime());
		//print temperature
		System.out.println("temperature:");
		wmc.printFloatArr(wmc.getTemperature());
		//print atPressure
		System.out.println("atPressure:");
		wmc.printFloatArr(wmc.getAtPressure());
		//print windSpeed
		System.out.println("windSpeed:");
		wmc.printFloatArr(wmc.getWindSpeed());
		//print gustSpeed
		System.out.println("gustSpeed:");
		wmc.printFloatArr(wmc.getGustSpeed());
		//print precipitationMm
		System.out.println("precipitationMm:");
		wmc.printFloatArr(wmc.getPrecipitationMm());
		
		
		
		
    }

	public ArrayList<String> getTime() {
		return time;
	}



}
