import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.IllegalComponentStateException;
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
	private JComboBox<String> yearComboBox,monthComboBox,dayComboBox;
	private DateModelController date;
	public WeatherGUI()
	{
		setTitle(WINDOW_TITLE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(dim.width/4, dim.height/4);
		setLocation(dim.width/4, dim.height/4);
		date=new DateModelController();
		
		Container c=this.getContentPane();
		JPanel p=new JPanel();
		
		p.add(new JLabel("Airport:"));
		
		//date panel 
	
	    p.add(new JLabel("Year:"));
	    yearComboBox=addDateComboBox(date.getMinYear(), date.getMaxYear(),date.getYear(), p);
		p.add(new JLabel("Month:"));
		monthComboBox=addDateComboBox(date.getMinMonth(), date.getMaxMonth(),date.getMaxMonth(), p);
		p.add(new JLabel("Day:"));
		dayComboBox=addDateComboBox(date.getMinDay(), date.getMaxDay(), date.getDay(),p);
		//add combo	

		
	
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
			  WeatherGraphGui graphGui=new WeatherGraphGui();
			  graphGui.setVisible(true);
			  graphGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		  }
		  else if(source==yearComboBox)
		  {
			  String year=(String)yearComboBox.getSelectedItem();
			  date.setYear(Integer.parseInt(year));
			  dayComboBox.setSelectedItem(Integer.toString(date.getDay()));
			  date.debugOutput();
		  }
		  else if(source==monthComboBox)
		  {
			  String month=(String)monthComboBox.getSelectedItem();
			  date.setMonth(Integer.parseInt(month));
			  dayComboBox.setSelectedItem(Integer.toString(date.getDay()));
			  date.debugOutput();
		  }
		  else if(source==dayComboBox)
		  {
			  String day=(String)dayComboBox.getSelectedItem();
			  date.setDay(Integer.parseInt(day));
			  dayComboBox.setSelectedItem(Integer.toString(date.getDay()));
			  date.debugOutput();
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
