import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class WeatherGUI extends JFrame {
	
	private final String WINDOW_TITLE="weather";
	public WeatherGUI()
	{
		setTitle(WINDOW_TITLE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(dim.width/4, dim.height/4);
		setLocation(dim.width/4, dim.height/4);
		System.out.println("width:"+dim.width);
		
		Container c=this.getContentPane();
		JPanel p=new JPanel(new GridLayout(3, 1));
		
		//airport panel
		JPanel p1= new JPanel(new GridLayout(1, 2));
		p1.add(new JLabel("Airport:"));
		//add combo
		p.add(p1);
		//date panel 
	    p1= new JPanel(new GridLayout(1, 6));
	    p1.add(new JLabel("Year:"));
	    //add combo
		p1.add(new JLabel("Month:"));
		//add combo
		p1.add(new JLabel("Day:"));
		//add combo	
		p.add(p1);
		
		p1=new JPanel(new GridLayout(1,1));
		p1.add(new JButton("Submit"));
		p.add(p1);
	
		c.add(p);
	}
	
	private void addComboBox(String[]list,JPanel p)
	{
		JComboBox<String>tempComboBox=new JComboBox<String>();
		DefaultComboBoxModel<String>comboxModel=new DefaultComboBoxModel<String>(list);
		tempComboBox.setModel(comboxModel);
		p.add(tempComboBox);
	}
	public static void main(String[] args) {
		
		WeatherGUI weatherGui=new WeatherGUI();
		weatherGui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		weatherGui.setVisible(true);
	}

}
