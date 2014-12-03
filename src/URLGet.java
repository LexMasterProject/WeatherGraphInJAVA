/** 
 * URLget.java
 * 
 * Example code from Big Java by Cay Horstmann page 906
 * This class gets html from the dcs web page and 
 * displays to console
 * 
 */

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLGet {
	public static void main(String[] args) throws IOException {
		
		String urlString;
		if(args.length == 1)
			urlString = args[0];
		else
		{
			urlString = "http://www.shef.ac.uk/dcs";
			System.out.println("Reading data from " + urlString );
		}
		
		// Open connection
		URL u = new URL(urlString);
		URLConnection connection = u.openConnection();
		
		// check to make sure the page exists
		HttpURLConnection httpConnection = (HttpURLConnection) connection;
		int code = httpConnection.getResponseCode();
		String message = httpConnection.getResponseMessage();
		System.out.println(code + " " + message);
		if (code != HttpURLConnection.HTTP_OK)
			return;
		
		// Read server response
		InputStream instream = connection.getInputStream();
		Scanner in = new Scanner(instream);
		
		// display server response to console
		while (in.hasNextLine())
		{
			String input = in.nextLine();
			System.out.println(input);
		}
		
	
	}
}
