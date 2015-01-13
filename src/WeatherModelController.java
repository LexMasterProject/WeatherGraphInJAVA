import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;


public class WeatherModelController {
	
	private String searchURL;
	private DateModel dateModel;
	private String location;

	private WeatherNetSpider spider;
	

	private float time[];
	private float temperature[];
	private float atPressure[];
	private float windSpeed[];
	private float gustSpeed[];
	private float precipitationMm[];

	public WeatherModelController()
	{
		
		dateModel=new DateModel();
		location=null;
	}

	//generate search url
	//must invoke setLocation in advance
	private void generateURL()
	{
		 String AAAA=AirportSingleton.getInstance().getICAO(location);
		 String YYYY=Integer.toString(dateModel.getYear());
		 String MM=Integer.toString(dateModel.getMonth());
		 String DD=Integer.toString(dateModel.getDay());
		 searchURL="http://www.wunderground.com/history/airport/"+
				 	AAAA+"/"+YYYY+"/"+MM+"/"+DD+"/"+
				 "DailyHistory.html?HideSpecis=1&format=1";
	}
	
	//get info through spider
	public void getInfo()
	{
		try {
			generateURL();
			spider=new WeatherNetSpider(searchURL);
			if(spider.connect()!=0)
			{
				spider.getInfo();		
				time=transferTimeList(spider.getTime());
				temperature=arrayListStrToFloatArr(spider.getTemperature());
				atPressure=arrayListStrToFloatArr(spider.getAtPressure());
				windSpeed=arrayListStrToFloatArr(spider.getWindSpeed());
				System.out.println();
				gustSpeed=arrayListStrToFloatArr(spider.getGustSpeed());
				precipitationMm=arrayListStrToFloatArr(spider.getPrecipitationMm());
			}
			else
			{
				System.out.println("Can not access net");
			}
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//transfer timeList to minutes array
	private float[] transferTimeList(ArrayList<String>timelist)
	{
		
		float[]time=new float[timelist.size()];
		for (int i = 0; i < timelist.size(); i++) {
			
			time[i]=timeToMinutes(timelist.get(i));
		}
		
		return time;
	}
	
	//transfer time to minutes
	//e.g. 03:10 am=3*60+10
	//     03:10 pm=(3+12)*60+10
	private float timeToMinutes(String time)
	{
		final int PERIOD=1,TIME=0;
		final int HOUR=0,MINUTE=1;
		String timeInfo[]=time.split(" ");
		String hourAndMin[]=timeInfo[TIME].split(":");
		float minutes;
		if(timeInfo[PERIOD].equalsIgnoreCase("am"))
		{
			if(hourAndMin[HOUR].equals("12"))
			{
				minutes=Float.parseFloat( hourAndMin[MINUTE]);
			}
			else
			{
				minutes=Float.parseFloat(hourAndMin[HOUR])*60+Float.parseFloat(hourAndMin[MINUTE]);
			}
		}
		else
		{
			if (hourAndMin[HOUR].equals("12")) {
				minutes=Float.parseFloat(hourAndMin[HOUR])*60+Float.parseFloat(hourAndMin[MINUTE]);
			}
			else
			{
				minutes=(Float.parseFloat(hourAndMin[HOUR])+12)*60+Float.parseFloat(hourAndMin[MINUTE]);
			}
		}
		return minutes;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public DateModel getDateModel() {
		return dateModel;
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
	
	public int getTempStart(int step)
	{
		return downRound(temperature, step);
	}
	public int getTempEnd(int step)
	{
		return upRound(temperature, step);
	}
	public int getWindStart(int step)
	{
		return downRound(windSpeed, step);
	}
	public int getWindEnd(int step)
	{
		return upRound(windSpeed, step);
	}
	
	public int getatPressureStart(int step)
	{
		return downRound(atPressure, step);
	}
	public int getatPressureEnd(int step)
	{
		return upRound(atPressure, step);
	}
	
	private int upRound(float[]arr,int step)
	{
		
		 if(arr.length!=0)
		 {
			 float max=arr[0];
			
			 for(int i=0;i<arr.length;i++)
			 {
				max=Math.max(max,arr[i]); 
			 }
			
			 int imax=(int)max+1;
			 while(imax%step!=0)
			 {
				 imax++;
			 }
			
			 return imax;
		 }
		 return 0;
	}
	
	private int downRound(float[]arr,int step)
	{
		if(arr.length!=0)
		 {
			 float min=arr[0];
			
			 for(int i=0;i<arr.length;i++)
			 {
				min=Math.min(min,arr[i]); 
			 }
		
			 int imin=(int)min-1;
			 while(imin%step!=0)
			 {
				 imin--;
			 }
	
			 return imin;
		 }
		 return 0;
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

	public String getSearchURL() {
		return searchURL;
	}

	
	public static void main(String[] args)
	{
		WeatherModelController wmc=new WeatherModelController();
		wmc.setLocation("London Heathrow");
		wmc.getInfo();

		//print time
		System.out.println("time:");
		wmc.printFloatArr(wmc.getTime());
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

	public float[] getTime() {
		return time;
	}

	public void setTime(float[] time) {
		this.time = time;
	}





}
