import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.IllegalComponentStateException;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class WeatherGUI extends JFrame implements ActionListener {
	

	private final String WINDOW_TITLE="weather";
	private JButton submitBtn;
	private JComboBox<String> yearComboBox,monthComboBox,dayComboBox,airportComboBox;
	private WeatherModelController weatherMC;
	public WeatherGUI()
	{
		weatherMC=new WeatherModelController();
		setTitle(WINDOW_TITLE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(dim.width/4, dim.height/4);
		setLocation(dim.width/4, dim.height/4);
		
		
		Container c=this.getContentPane();
		
		//main panel 
		JPanel p=new JPanel(new ParagraphLayout());
		
		//airport panel
		p.add(new JLabel("Airport:"),ParagraphLayout.NEW_PARAGRAPH);
		airportComboBox=new JComboBox<String>(AirportSingleton.getInstance().getLocations());
		airportComboBox.setSelectedIndex(0);
		p.add(airportComboBox);
	
		
		//date panel 
	    DateModel date=weatherMC.getDateModel();
	    p.add(new JLabel("Year:"),ParagraphLayout.NEW_PARAGRAPH);
	    yearComboBox=addDateComboBox(date.getMinYear(), date.getMaxYear(),date.getYear(), p);
		p.add(new JLabel("Month:"));
		monthComboBox=addDateComboBox(date.getMinMonth(), date.getMaxMonth(),date.getMonth(), p);
		p.add(new JLabel("Day:"));
		dayComboBox=addDateComboBox(date.getMinDay(), date.getMaxDay(), date.getDay(),p);
	
		
		//submit btn
		p.add(new Label(),ParagraphLayout.NEW_PARAGRAPH);
		submitBtn=new JButton("Submit");
		submitBtn.addActionListener(this);
		p.add(submitBtn);
		

		c.add(p);
		
	}
	
  

	private JComboBox<String> addDateComboBox(int min,int max,int item,JPanel p)
	{
		String items[]=new String[max-min+1];
		for(int i=0;i<max-min+1;i++)
		{
			items[i]=Integer.toString(max-i);
		}
		
		JComboBox<String> tempComboBox=new JComboBox<String>(items);
		tempComboBox.setSelectedItem(Integer.toString(item));
		tempComboBox.addActionListener(this);

		tempComboBox.setVisible(true);
		p.add(tempComboBox);
		return tempComboBox;
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		  Object source= e.getSource();
		  if(source==submitBtn)
		  {
			  String location=(String)airportComboBox.getSelectedItem();
			  weatherMC.setLocation(location);
			  weatherMC.getInfo();
			  WeatherGraphGui graphGui=new WeatherGraphGui(weatherMC);
			  graphGui.setVisible(true);
			  graphGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
			  
			  
			  // ouput search url
			  System.out.println(weatherMC.getSearchURL());
			  
			 
		  }
		  else if(source==yearComboBox)
		  {
			  DateModel date=weatherMC.getDateModel();
			  String year=(String)yearComboBox.getSelectedItem();
			  date.setYear(Integer.parseInt(year));
			  dayComboBox.setSelectedItem(Integer.toString(date.getDay()));
		
		  }
		  else if(source==monthComboBox)
		  {
			  DateModel date=weatherMC.getDateModel();
			  String month=(String)monthComboBox.getSelectedItem();
			  System.out.println(month);
			  date.setMonth(Integer.parseInt(month));
			  dayComboBox.setSelectedItem(Integer.toString(date.getDay()));
		
		
		  }
		  else if(source==dayComboBox)
		  {
			  DateModel date=weatherMC.getDateModel();
			  String day=(String)dayComboBox.getSelectedItem();
			  date.setDay(Integer.parseInt(day));
			  dayComboBox.setSelectedItem(Integer.toString(date.getDay()));
			 
		  }
		
		
	}
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
			
				WeatherGUI weatherGui=new WeatherGUI();
				weatherGui.setDefaultCloseOperation(EXIT_ON_CLOSE);
				weatherGui.pack();
				weatherGui.setVisible(true);
				
				
				
			}
		});
		
	}

	

}
