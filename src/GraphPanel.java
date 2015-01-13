import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;

import javax.swing.JPanel;


public class GraphPanel extends JPanel {

	private int width;
	private int height;
	private float bgWidth,bgHeight,bgX,bgY;
	private float ystartNum,ystepNum,yendNum;
	private int numOfYlines;
	private static final float BG_SCALE=0.8f;
	private static final int NUM_OF_XLINES=25;//num of gray lines
	private DataTransfer dataTransfer;
	private float[]data;
	private float[]time;
	private Point2D.Float origin;
	private String header,leftUnit,rightUnit;
	public float[] getData() {
		return data;
	}

	public void setData(float[] data) {
		this.data = data;
	}

	public GraphPanel(int startnum,int endnum,int stepnum,float[]data,float []time)
	{
		
		ystartNum=startnum;
		ystepNum=stepnum;
		yendNum=endnum;
		this.data=data;
		numOfYlines=(endnum-startnum)/stepnum+1;
		this.time=time;
		header=null;
		leftUnit=null;
		rightUnit=null;
		dataTransfer=null;
	
	}
	
	//draw table and table metadata
	private void drawBackGround(Graphics g)
	{
		//initialize background paras
		width=this.getWidth();
		height=this.getHeight();
		bgWidth=BG_SCALE*width;
		bgHeight=BG_SCALE*height;
		bgX=(width-bgWidth)/2;
		bgY=(height-bgHeight)/2;
		origin=new Point2D.Float((width-bgWidth)/2, (height+bgHeight)/2);
		
		
		
		Graphics2D g2d=(Graphics2D)g;
		
		g2d.setStroke(new BasicStroke(1.5f));
		
		
		
	
		
		
		String yString,xString;
		float strlenx,strleny;
		float xstep=bgWidth/(NUM_OF_XLINES-1);
		Line2D.Double grayline;
		float x1,y1,x2,y2;
		
		//draw header
		if(header!=null)
		{	g2d.setColor(Color.red);
			g2d.drawString(header, (width-bgWidth)/2+xstep, (height-bgHeight)/2-0.8f);
			g2d.setColor(Color.black);
		}
		
		//draw left unit
		if(leftUnit!=null)
		{
			g2d.drawString(leftUnit, (width-bgWidth)/2-2*xstep, (height-bgHeight)/2-0.8f);
			
		}
		//draw right unit
		if(rightUnit!=null)
		{	
			g2d.drawString(rightUnit, (width+bgWidth)/2+xstep, (height-bgHeight)/2-0.8f);
		
		}
		//draw time lines
		for(int i=0;i<NUM_OF_XLINES;i++)
		{
			x1=bgX+xstep*i;
			y1=bgY;
			x2=x1;
			y2=bgY+bgHeight;
			g2d.setColor(new Color(200, 200, 200));
			grayline=new Line2D.Double(x1, y1, x2, y2);
			g2d.draw(grayline);
			g2d.setColor(Color.black);
			xString=Integer.toString(i);
			strlenx=(float)g2d.getFontMetrics().getStringBounds(xString, g2d).getWidth();
			strleny=(float)g2d.getFontMetrics().getStringBounds(xString, g2d).getHeight();
			g2d.drawString(xString, x1-strlenx/2, y2+strleny);
			
		}
		//draw data lines 
		float ystep=bgHeight/(numOfYlines-1);
		float leftData,rightData;
		for(int i=0;i<numOfYlines;i++)
		{
			x1=bgX;
			y1=bgY+ystep*i;
			x2=bgX+bgWidth;
			y2=y1;
			g2d.setColor(new Color(200, 200, 200));
			grayline=new Line2D.Double(x1, y1, x2, y2);
			g2d.draw(grayline);
			g2d.setColor(Color.black);
			//drawstring
			leftData=ystartNum+(numOfYlines-1-i)*ystepNum;
			yString=Integer.toString((int)leftData);
			strlenx=(float)g2d.getFontMetrics().getStringBounds(yString, g2d).getWidth();
			strleny=(float)g2d.getFontMetrics().getStringBounds(yString, g2d).getHeight();
			g2d.drawString(yString, x1-strlenx-5, y1+strleny/2);
			if(dataTransfer!=null)
			{
			rightData=dataTransfer.transfer(leftData);
			yString=Integer.toString((int)rightData);
			
			strleny=(float)g2d.getFontMetrics().getStringBounds(yString, g2d).getHeight();
			g2d.drawString(yString, x2+5, y1+strleny/2);
			}
			
		}
		
		
		//draw bg rect
		g2d.setColor(Color.black);
		Rectangle2D.Double rect=new Rectangle2D.Double(bgX, bgY, bgWidth, bgHeight);
		g2d.draw(rect);
		
	}
	
	public String getLeftUnit() {
		return leftUnit;
	}

	public void setLeftUnit(String leftUnit) {
		this.leftUnit = leftUnit;
	}

	public String getRightUnit() {
		return rightUnit;
	}

	public void setRightUnit(String rightUnit) {
		this.rightUnit = rightUnit;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	private void drawData(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.red);
		Line2D.Float line;
		for (int i = 0; i < data.length-1; i++) {
			line=new Line2D.Float(transfer(time[i]/60, data[i]), transfer(time[i+1]/60, data[i+1]));
			g2d.draw(line);
		}
	}
	private Point2D transfer(float x,float y)
	{
		Point2D p= new Point2D.Float();
		float y1=(float)origin.getY()-(y-ystartNum)/(yendNum-ystartNum)*bgHeight;
		float x1=x/(NUM_OF_XLINES-1)*bgWidth+(float)origin.getX();
		p.setLocation(x1, y1);
		return p;
	}
	public void setDataTransfer(DataTransfer dataTransfer) {
		this.dataTransfer = dataTransfer;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawBackGround(g);
		drawData(g);
		
	
	}
	
	
	public static void main(String[] args) {
		
		
	}


}
