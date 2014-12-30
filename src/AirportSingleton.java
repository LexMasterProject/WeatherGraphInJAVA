import java.util.HashMap;


public class AirportSingleton {
	
	private static AirportSingleton instance;
	
	private HashMap<String, String>locationICAO;
	private static final String strArrLocationICAO[][]={
			{"London Heathrow","EGLL"},
			{"Manchester Airport","EGCC"},
			{"Beijing Capital","ZBAA"},
			{"Shanghai Pudong","ZSPD"},
			{"Hong Kong International","VHHH"}
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
		
		
		//test getICAO code
		System.out.println("ICAO code:");
		String ICAOForLondonH=AirportSingleton.getInstance().getICAO("London Heathrow");
		if (ICAOForLondonH.equals("EGLL")) {
			System.out.println("London Heathrow passed");
		}
		else
		{
			System.out.println("London Heathrow failed");
		}
		
		String ICAOForBeijingC=AirportSingleton.getInstance().getICAO("Beijing Capital");
		if (ICAOForBeijingC.equals("ZBAA")) {
			System.out.println("Beijing Capital passed");
		}
		else
		{
			System.out.println("Beijing Capital failed");
		}
		
		

	}

}
