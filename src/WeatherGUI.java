import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel("Airport:"));
		panel.add(new JLabel("Year:"));
		panel.add(new JLabel("Month:"));
		panel.add(new JLabel("Day:"));
		panel.add(new JButton("Submit"));
		c.add(panel);
		
		
		
		
	}
	public static void main(String[] args) {
		
		WeatherGUI weatherGui=new WeatherGUI();
		weatherGui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		weatherGui.setVisible(true);
	}

}
