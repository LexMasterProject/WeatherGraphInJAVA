import java.util.Calendar;



public class DateModel {

	private int year,month,day;
	private int maxYear,minYear;
	private int maxDay,minDay;
	public int getMaxDay() {
		return 31;
	}

	public int getMinDay() {
		return minDay;
	}

	public int getMaxMonth() {
		return maxMonth;
	}

	public int getMinMonth() {
		return minMonth;
	}

	private int maxMonth,minMonth;
	
	
	public DateModel(){
		setDefaultDate();
		resetRange();
	
	}
	
	public DateModel(int year,int month,int day){
		setDate(year,month,day);
		resetRange();
	}
	
	private void resetRange()
	{
		Calendar calendar= Calendar.getInstance();
		maxYear=calendar.get(Calendar.YEAR);
		minYear=1950;
		maxDay=adjustMaxDay();
		minDay=1;
		maxMonth=12;
		minMonth=1;
	}
	public int getMaxYear() {
		return maxYear;
	}

	public int getMinYear() {
		return minYear;
	}

	//set today as default date
	public void setDefaultDate()
	{
		Calendar calendar= Calendar.getInstance();
		year=calendar.get(Calendar.YEAR);
		month=calendar.get(Calendar.MONTH)+1;
		day=calendar.get(Calendar.DAY_OF_MONTH);
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
	
	public void debugOutput()
	{
		System.out.println("yy:mm:dd:"+year+":"+month+":"+day);
	}

	public static void main(String[] args) {
		
		//testing getDefaultDate
		DateModel myDate=new DateModel();
		System.out.println(myDate.getYear()+"-"+myDate.getMonth()+"-"+myDate.getDay());
		
		
	}

}
