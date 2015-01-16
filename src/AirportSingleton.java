import java.util.HashMap;

/**
 * function:
 * this singleton class is for the storage of all locations and 
 * transfer from location to ICAO code
 */
public class AirportSingleton {

	private static AirportSingleton instance;

	private HashMap<String, String>locationICAO;
	private static final String strArrLocationICAO[][]={
		{"London Heathrow","EGLL"},
		{"Manchester Airport","EGCC"},
		{"Nottingham Airport","EGBN"},
		{"Leicester Airport","EGBG"},
		{"Southampton Airport","EGHI"}
	};

	private AirportSingleton()
	{
		locationICAO=new HashMap<String, String>();

		for (int i = 0; i < strArrLocationICAO.length; i++) {
			locationICAO.put(strArrLocationICAO[i][0], strArrLocationICAO[i][1]);
		}
	}
	public String[] getLocations()
	{
		int size=locationICAO.size();
		return locationICAO.keySet().toArray(new String[size]);
	}

	public String getICAO(String location)
	{
		return locationICAO.get(location);
	}
	public static AirportSingleton getInstance()
	{
		if(instance==null)
			instance=new AirportSingleton();
		return instance;
	}
	public static void main(String[] args) {
		/*
		 *  black box testing 
		 */

		//test getLocations
		String[]locations=AirportSingleton.getInstance().getLocations();
		System.out.println("Locations:");
		for (String location : locations) {
			System.out.print(location+", ");
		}
		System.out.println();
		System.out.println("-------------------------------------------");


		//test getICAO code by using location
		System.out.println("ICAO code:");
		String ICAOForLondonH=AirportSingleton.getInstance().getICAO("London Heathrow");
		if (ICAOForLondonH.equals("EGLL")) {
			System.out.println("London Heathrow passed");
		}
		else
		{
			System.out.println("London Heathrow failed");
		}

		String ICAOForBeijingC=AirportSingleton.getInstance().getICAO("Nottingham Airport");
		if (ICAOForBeijingC.equals("EGBN")) {
			System.out.println("Nottingham Airport passed");
		}
		else
		{
			System.out.println("Nottingham Airport failed");
		}



	}

}
