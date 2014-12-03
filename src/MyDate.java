import java.util.Calendar;
import java.util.Date;


public class MyDate {

	private int year,month,day;
	
	public MyDate(){
		setCurrentDate();
	}
	
	public MyDate(int year,int month,int day){
		this.year=year;
		this.month=month;
		this.day=day;
	}
	
	public void setCurrentDate()
	{
		Calendar calendar= Calendar.getInstance();
		year=calendar.get(Calendar.YEAR);
		month=calendar.get(Calendar.MONTH)+1;
		day=calendar.get(Calendar.DAY_OF_MONTH);
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
		
		MyDate myDate=new MyDate();
		System.out.println(myDate.getYear()+"-"+myDate.getMonth()+"-"+myDate.getDay());
		
	}

}
