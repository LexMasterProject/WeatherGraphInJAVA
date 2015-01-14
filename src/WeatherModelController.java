import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class WeatherModelController {
	
	private String searchURL;
	private DateModel dateModel;
	private String location;
	private String summary;
	String YYYY,MM,DD;
	public String getSummary() {
		summary=new String();
		DecimalFormat df=new DecimalFormat("#0.0");
		String totalPrecipitationMm=df.format(getSum(precipitationMm)).toString();
		char[]spaceArr=new char[40];
		Arrays.fill(spaceArr, ' ' );
		String alias=new String(spaceArr);
	    summary+=alias;
	   
	    summary+= " "+YYYY+"/"+MM+"/"+DD;
		summary+= "   "+location;
		
		summary+= "    TotalPrecipitationMm:"+totalPrecipitationMm;
	
		
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}


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
		 YYYY=Integer.toString(dateModel.getYear());
		 MM=Integer.toString(dateModel.getMonth());
		 DD=Integer.toString(dateModel.getDay());
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
	
	
	public String getAverageTemperature()
	{   
		DecimalFormat df=new DecimalFormat("#0.00");
		String ret=df.format(getAverage(temperature)).toString();
		return ret;
	}
	public String getAveragePressure()
	{
		DecimalFormat df=new DecimalFormat("#0");
		String ret=df.format(getAverage(atPressure)).toString();
		return ret;
	}
	public String getAverageWindSp()
	{
		DecimalFormat df=new DecimalFormat("#0.0");
		String ret=df.format(getAverage(windSpeed)).toString();
		return ret;
	}
	public String getAverageGustSp()
	{
		DecimalFormat df=new DecimalFormat("#0.0");
		String ret=df.format(getAverage(gustSpeed)).toString();
		return ret;
	}
	
	private float getSum(float[]arr)
	{
		float sum=0;
		for (int i = 0; i < arr.length; i++) {
			sum+=arr[i];
		}
		return sum;
	}
	private float getAverage(float[]arr)
	{
		return getSum(arr)/arr.length;
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
		int normalWindSpEnd=upRound(windSpeed, step);
		int gustWindSpEnd=upRound(gustSpeed, step);
		return Math.max(normalWindSpEnd, gustWindSpEnd);
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
	//if counter abnormal forms: change data to 0
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
	


	

	public String getSearchURL() {
		return searchURL;
	}

	public float[] getTime() {
		return time;
	}

	public void setTime(float[] time) {
		this.time = time;
	}
	
	public float[] getTemperature() 
	{
		return temperature;
	}

	
	/*
	 *  output code for testing
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
		DateModel date=wmc.getDateModel();
		
		//2014,1,1
		//2010,3,2
		date.setDate(2014,1,1);
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
		
		//print summary
		System.out.println(wmc.getSummary());
		System.out.println(wmc.getSummary().length());




	}







}
