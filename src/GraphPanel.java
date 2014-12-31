import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;


public class GraphPanel extends JPanel {

	private int width;
	private int height;
	private float bgWidth,bgHeight,bgX,bgY;
	private float ystartNum,ystepNum;
	private int numOfYlines;
	private String unitLeft,unitRight;
	private static final float BG_SCALE=0.8f;
	private static final int NUM_OF_XLINES=25;//num of gray lines
	private DataTransfer dataTransfer;
	public GraphPanel(int startnum,int endnum,int stepnum)
	{
		dataTransfer=null;
		ystartNum=startnum;
		ystepNum=stepnum;
		numOfYlines=(endnum-startnum)/stepnum+1;
		System.out.println("startnum:"+ystartNum);
		System.out.println("endnum:"+endnum);
		System.out.println("num of lines:"+numOfYlines);
		System.out.println(ystepNum);
	}
	
	private void drawBackGround(Graphics g)
	{
		width=this.getWidth();
		height=this.getHeight();
		bgWidth=BG_SCALE*width;
		bgHeight=BG_SCALE*height;
		bgX=(width-bgWidth)/2;
		bgY=(height-bgHeight)/2;
		Graphics2D g2d=(Graphics2D)g;
		
		g2d.setStroke(new BasicStroke(1.5f));
	
		
		//draw time lines
		String yString,xString;
		float strlenx,strleny;
		float xstep=bgWidth/(NUM_OF_XLINES-1);
		Line2D.Double grayline;
		float x1,y1,x2,y2;
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
			System.out.println("in........");
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
			System.out.println("left data:"+leftData);
			yString=Integer.toString((int)leftData);
			System.out.println("left string:"+yString);
			strlenx=(float)g2d.getFontMetrics().getStringBounds(yString, g2d).getWidth();
			strleny=(float)g2d.getFontMetrics().getStringBounds(yString, g2d).getHeight();
			g2d.drawString(yString, x1-strlenx-5, y1+strleny/2);
			System.out.println("x:"+(x1-strlenx-5));
			System.out.println("y:"+(y1+strleny/2));
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

	public void setDataTransfer(DataTransfer dataTransfer) {
		this.dataTransfer = dataTransfer;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawBackGround(g);
	
	}
	
	
	public static void main(String[] args) {
		
		
	}


}
