package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/*
 * graphicBuilder take in data from graphicMonitor object and build a graph from it
 */
@SuppressWarnings("serial")
public class graphicBuilder extends ApplicationFrame
{
	graphicMonitor myGraphicMon;
	ArrayList<String[]> dataHolder;
	JButton removeButton;
	DefaultCategoryDataset linedataset;
	//constructor
	public graphicBuilder(graphicMonitor newGraphicMon) 
	{
		super(newGraphicMon.myStock.getStockSymbol());
		myGraphicMon = newGraphicMon;
		dataHolder=new ArrayList<String[]>();
		getStockData();
		setContentPane(createDemoLine());
		removeButton=new JButton("Stop Monitor "+newGraphicMon.myStock.getStockSymbol());
		removeButton.setSize(2000, 30);
		removeButton.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent evt)
		    {
		         removeButtonActionPerformed(evt);
		    }
		    });
		add(removeButton);
	}
	//remove this graphic monitor
	private void removeButtonActionPerformed(ActionEvent evt) 
	{
		String stockSymbol = myGraphicMon.getStock().getStockSymbol();
		myGraphicMon.getUI().getController().removeStock(stockSymbol, "graphic");//remove 
		dispose();//close the graphic window
	}
	public JPanel createDemoLine() 
	{
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}
	public JFreeChart createChart(DefaultCategoryDataset linedataset)
	{
		JFreeChart chart = ChartFactory.createLineChart((" "),
														"Time",
														"Price",
														linedataset,
														PlotOrientation.VERTICAL,
														true,
														true,
														false);
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(false);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0); 
		return chart;
	}
	//this method is used to add the data to linedataset
	public DefaultCategoryDataset createDataset()
	{
		 linedataset = new DefaultCategoryDataset();
		 for(int i=0;i<dataHolder.size();i++)
		 {
			String series1 = "current Price";
//			String series2 = "dayHigh";
//			String series3 = "dayLow";
			String myStr[]=dataHolder.get(i);
			double lastTValue=Double.parseDouble(myStr[1]);
//			double dayHigh=Double.parseDouble(myStr[3]);
//			double dayLow=Double.parseDouble(myStr[4]);
			String time=myStr[2];
			linedataset.addValue(lastTValue, series1, time);
//			linedataset.addValue(dayHigh, series2, time);
//			linedataset.addValue(dayLow, series3, time);
		 }
		 
		 return linedataset;
	}
	
	// gets stock data from graphicMonitor
	public void getStockData()
	{
		String[]stockData=new String[5];
		ArrayList<String> mySD=new ArrayList<String>();
		mySD=myGraphicMon.getMonitorState();
		for(int i=0;i<mySD.size();i++)
		{
			stockData[i]=mySD.get(i);
		}
		dataHolder.add(stockData);
	}
	
	//update stock data from graphic monitor
	public void updataStockData()
	{
		getStockData();
		linedataset.clear();
		for(int i=0;i<dataHolder.size();i++)
		{
			String series1 = "Price";
//			String series2 = "dayHigh";
//			String series3 = "dayLow";
			String myStr[]=dataHolder.get(i);
			double lastTValue=Double.parseDouble(myStr[1]);
			double dayHigh=Double.parseDouble(myStr[3]);
			double dayLow=Double.parseDouble(myStr[4]);
			String time=myStr[2];
			linedataset.addValue(lastTValue, series1, time);
//			linedataset.addValue(dayHigh, series2, time);
//			linedataset.addValue(dayLow, series3, time);
		}
	}
}
