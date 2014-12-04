import java.util.Calendar;
import java.util.Date;


public class MyDate {

	private int year,month,day;
	private int maxYear,minYear;
	private int maxDay;
	
	public MyDate(){
		setDefaultDate();
		resetRange();
	
	}
	
	public MyDate(int year,int month,int day){
		setDate(year,month,day);
		resetRange();
	}
	
	private void resetRange()
	{
		maxYear=2015;
		minYear=1900;
		maxDay=adjustMaxDay();
	}
	//set today as default date
	public void setDefaultDate()
	{
		Calendar calendar= Calendar.getInstance();
		year=calendar.get(Calendar.YEAR);
		month=calendar.get(Calendar.MONTH)+1;
		day=calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	
	/*
	 *  get arrays of years/months/days
	 */
	
	public Integer[]getYears()
	{
		int n=maxYear-minYear+1;
		int tempMinYear=minYear;
		Integer[]years=new Integer[n];
		for (int i = 0; i < years.length; i++) {
			years[i]=tempMinYear++;
		}
		
		return years;
	}
	public Integer[]getMonths()
	{
		Integer[]months= new Integer[12];
		for(int i=1;i<=12;i++)
		{
			months[i-1]=i;
		}
		return months;
	}
	
	public Integer[]getDays()
	{
		Integer[]days= new Integer[maxDay];
		for (int i = 0; i < days.length; i++) {
			days[i]=i+1;
		}
		return days;
	}
	
	//setters 
	public void setDate(int year,int month,int day)
	{
		this.year=year;
		this.month=month;
		this.day=day;
		
		maxDay=adjustMaxDay();
		setDay(day);
	}
	
	public void setYear(int year) {
		this.year = year;
		maxDay=adjustMaxDay();
		setDay(day);
	}

	public void setMonth(int month) {
		this.month = month;
		maxDay=adjustMaxDay();
		setDay(day);
	}

	public void setDay(int day) {
		this.day = day;
		if(this.day>this.maxDay)
		{
			this.day=this.maxDay;
		}
	}

	
	private boolean isMonthHas31Days()
	{
		int []monthHas31Days={1,3,5,7,8,10,12};
		for (int i = 0; i < monthHas31Days.length; i++) {
			if(monthHas31Days[i]==month)
			{
				return true;
			}
		}
		return false;
	}
	
	//calculate the max day for given month&year
	private int adjustMaxDay()
	{
		if(month==2)
		{
			if(isLeapYear())
			{
				return 29;
			}
			else
				return 28;
		}
		
		if (isMonthHas31Days()) {
			return 31;
		}
		
		return 30;
		
	}
	

	public boolean isLeapYear()
	{
		if((year%4==0&&year%100!=0)||year%400==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public static void main(String[] args) {
		
		//testing getDefaultDate
		MyDate myDate=new MyDate();
		System.out.println(myDate.getYear()+"-"+myDate.getMonth()+"-"+myDate.getDay());
		
		
	}

}
