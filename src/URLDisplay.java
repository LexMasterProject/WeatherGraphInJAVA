/** 
 * URLDisplay.java
 * 
 * This example class gets html from a web page and 
 * displays to a JFrame with scroll bar
 * 
 * Modified from http://courseweb.xu.edu.ph/courses/ics10/swing/JEditorPane.htm
 * --see this page for a simple web browser that can follow links
 * 
 * 
 */
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class URLDisplay extends JFrame {
	
	 private JEditorPane htmlPane;
		
	 public URLDisplay(String url)
	 {
		super("Simple URL display");
		JPanel topPanel = new JPanel();
	    try
	    {
	        htmlPane = new JEditorPane(url);
	        htmlPane.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(htmlPane);
	        getContentPane().add(scrollPane, BorderLayout.CENTER);
	    }
	    
	    catch(IOException i)
	    {
	    	System.out.println("Can't display page " + url);
	    }

	    Dimension screenSize = getToolkit().getScreenSize();
	    int width = screenSize.width * 4 / 10;
	    int height = screenSize.height * 6 / 10;
	    setBounds(width/4, height/6, width, height);
	    setVisible(true);
		 
	 }
	 
	 public static void main(String args[]) 
	 {  
		 if (args.length == 0)
			 new URLDisplay("http://www.shef.ac.uk/dcs");
		 else
			 new URLDisplay(args[0]);
	 }
}
