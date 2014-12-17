
public class WeatherModelController {

	public static final String[]airports={"EGLL"};
	public static final String searchTemplate="http://www.wunderground.com/history/"
			+ "airport/AAAA/YYYY/MM/DD/DailyHistory.html?HideSpec is=1&format=1";
	private String searchStr;
	private MyDate mydate;
	private int airportIndex;
	
	public WeatherModelController()
	{
		mydate=new MyDate();
	}
	
	public void setSearchData(String location,MyDate mydate)
	{
		
	}
	
	public static void main(String[] args) {

	}

}
