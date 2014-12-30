import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class WeatherGraphGui extends JFrame {


	private final String WINDOW_TITLE="WeatherGraph";
	

	public WeatherGraphGui() throws HeadlessException {
		setTitle(WINDOW_TITLE);
		setSize(400, 500);
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent windowEvent){
//	            System.exit(0);
//	         }     
//		});
		
	}
	
public static void main(String[] args) {
		
		WeatherGraphGui gui=new WeatherGraphGui();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
