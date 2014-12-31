import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.GapContent;


public class WeatherGraphGui extends JFrame {


	private final String WINDOW_TITLE="WeatherGraph";
	private GraphPanel tempGraphPanel,windGraphPanel,atPressGraphPanel;
	private WeatherModelController wmc;
	

	public WeatherModelController getWmc() {
		return wmc;
	}

	public void setWmc(WeatherModelController wmc) {
		this.wmc = wmc;
	}

	public WeatherGraphGui(WeatherModelController wmc)  {
		this.wmc=wmc;
		setTitle(WINDOW_TITLE);
		//setSize(400, 500);
		setSize(800, 550);
		
		Container c=getContentPane();
		JPanel p=new JPanel(new GridLayout(3, 1));
		System.out.println("@@@@@@@@@@@@@");
		System.out.println(wmc.getTempStart(3));
		System.out.println(wmc.getTempEnd(3));
		System.out.println(3);
		tempGraphPanel=new GraphPanel(wmc.getTempStart(3),wmc.getTempEnd(3),3);
		
		tempGraphPanel.setDataTransfer(new CelsiusToFahrenheit());
		p.add(tempGraphPanel);
//		windGraphPanel=new GraphPanel();
//		p.add(windGraphPanel);
//		atPressGraphPanel=new GraphPanel();
//		p.add(atPressGraphPanel);
		
		c.add(p);
		
	}
	
public static void main(String[] args) {
		
	
	}
}
