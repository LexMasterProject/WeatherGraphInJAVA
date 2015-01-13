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
	
		setSize(800, 550);
		
		Container c=getContentPane();
		JPanel p=new JPanel(new GridLayout(3, 1));
		
		//add temperature panel
		int temperatureStep=3;
		tempGraphPanel=new GraphPanel(wmc.getTempStart(temperatureStep),wmc.getTempEnd(temperatureStep),temperatureStep,wmc.getTemperature(),wmc.getTime());
		tempGraphPanel.setDataTransfer(new CelsiusToFahrenheit());
		tempGraphPanel.setHeader("Temperature && average:"+wmc.getAverageTemperature()+"C");
		tempGraphPanel.setLeftUnit("C");
		tempGraphPanel.setRightUnit("F");
		p.add(tempGraphPanel);
		
		//add pressure panel
		int pressureStep=3;
		atPressGraphPanel=new GraphPanel(wmc.getatPressureStart(pressureStep),
				wmc.getatPressureEnd(pressureStep),
				pressureStep,wmc.getAtPressure(),wmc.getTime());
		atPressGraphPanel.setHeader("Barometric Pressure && average:"+wmc.getAveragePressure()+"hPa");
		atPressGraphPanel.setLeftUnit("hPa");
		p.add(atPressGraphPanel);
		
		//add wind panel
		int windStep=8;
		windGraphPanel=new GraphPanel(wmc.getWindStart(windStep),wmc.getWindEnd(windStep),windStep,wmc.getWindSpeed(),wmc.getTime());
		windGraphPanel.setDataTransfer(new KmhToMph());
		windGraphPanel.setDataPoints(wmc.getGustSpeed());
		windGraphPanel.setHeader("Wind Speed & Wind Gust & WindAvg:"+wmc.getAverageWindSp()+"km/h"+
		"& GustAvg:"+wmc.getAverageGustSp()+"km/h");
		windGraphPanel.setLeftUnit("km/h");
		windGraphPanel.setRightUnit("mph");
		p.add(windGraphPanel);
		
		

		
		c.add(p);
		
	}
	
public static void main(String[] args) {
		
	
	}
}
