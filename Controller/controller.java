package Controller;
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;
import Model.StockData;
import View.graphicBuilder;
import View.userInterface;
import View.textMonitor;
import View.graphicMonitor;

/*
 *Controller
 *Method()
 *findStock();addStock();removeStock();addobserver();selectServer();main()
 */
public class controller {

	public ArrayList<StockData> stockList;
	public ArrayList<StockData> GstockList;
	private static userInterface myUI; 
	public String ServerName;
	public controller()
	{
		stockList = new ArrayList<StockData>();
		GstockList=new ArrayList<StockData>();
	}
	
	//findStock() given a stockSymbol and Monitor type, returns stockData object in stockList/GStockList
	//if net find in the stockList return null
	private StockData findStock(String stockSymbol, String myMonType) 
	{
		if(myMonType.equals("Text"))// is the monitor type is "Text"
		{
			int i=0;
			while(i<stockList.size())
			{
				if(stockList.get(i).getStockSymbol().equalsIgnoreCase(stockSymbol))
				{
					break;
				}
				i++;
			}
			return stockList.get(i);
		}
		else if(myMonType.equals("graphic"))///if the monitor type is "graphic"
		{
			int i=0;
			while(i<GstockList.size())
			{
				if(GstockList.get(i).getStockSymbol().equalsIgnoreCase(stockSymbol))
				{
					break;
				}
				i++;
			}
			return GstockList.get(i);
		}
		return null;//// if not found
	}
	// this method is used to add monitor for a stock 
	public void addStock(String stockSymbol, String myMonType)
	{
		if(myMonType.equals("Text"))// if we need to add TextMonitor for this stock
		{
			StockData myStock = new StockData(stockSymbol,ServerName);//create a new object
			stockList.add(myStock);//add it to stockList
			addobeserve(stockSymbol,myMonType);//add observer for this stock
			StockData stock=findStock(stockSymbol,myMonType);
			int last= (stock.mymonitor.size());
			myUI.addMonitor(stock.mymonitor.get(last-1));
		}
		else if(myMonType.equals("graphic"))
		{
			StockData myStock = new StockData(stockSymbol,ServerName);// create a new stockdata object
			GstockList.add(myStock);//store reference to the newly created stockdata in Gstocklist
			addobeserve(stockSymbol,myMonType);
			StockData stock=findStock(stockSymbol,myMonType);
			int last= (stock.mygraphicMonitor.size());
			myUI.addGraphicMon(stock.mygraphicMonitor.get(last-1));//adds graphic monitor to UI
		}
	}
	//this method is used to remove a stock form stockList or GStockList
	public void removeStock(String stockSymbol,String myMonType)
	{
		if(myMonType.equals("Text"))
		{
			for(int i=0;i<stockList.size();i++)//need remove form stockList
			{
				if(stockList.get(i).getStockSymbol().equalsIgnoreCase(stockSymbol))
				{	
					stockList.get(i).detach("Text");//detach
					stockList.remove(i);
					break;
				}
			}
			myUI.removeMonitor(stockSymbol);
		}
		else if(myMonType.equals("graphic"))///need remove form GStockList
		{
			for(int i=0;i<GstockList.size();i++)
			{
				if(GstockList.get(i).getStockSymbol().equalsIgnoreCase(stockSymbol))
				{	
					GstockList.get(i).detach("graphic");//detach
					GstockList.remove(i);
					break;
				}
			}
			myUI.removeGMonitor(stockSymbol);
		}
		
	}
	// this method is used to add observer for a stock 
	public void addobeserve(String stockSymbol,String myMonType) 
	{
		StockData myStock= findStock(stockSymbol,myMonType);//find the object 
		if(myMonType.equals("Text"))//if we need to add a TextMonitor
		{
			int last= (findStock(stockSymbol,myMonType).getObserverList(myMonType).size());
			myStock.attach(myMonType);//attach
			((textMonitor) myStock.getObserverList(myMonType).get(last)).setStock(myStock);
			((textMonitor) myStock.getObserverList(myMonType).get(last)).setUI(myUI);
			((textMonitor) myStock.getObserverList(myMonType).get(last)).setMonitorState();	
		}
		else if(myMonType.equals("graphic"))//if we need to add a Graphic Monitror
		{
			myStock.attach(myMonType);//attach
			int last= (findStock(stockSymbol,myMonType).getObserverList(myMonType).size());
			graphicMonitor GM = (graphicMonitor) myStock.getObserverList(myMonType).get(last-1);
			GM.setStock(myStock);
			GM.setUI(myUI);
			GM.setMonitorState();
			//create a  graohicButider for this graphic monitor
			graphicBuilder myGB = new graphicBuilder(GM);
			GM.setGB(myGB);
			myGB.pack();
			RefineryUtilities.centerFrameOnScreen(myGB);
			myGB.setVisible(true);
		}
	}
	//select the serverName
	public void selectServer(String myServerName)
	{
		ServerName=myServerName;
	}
	//main method
	public static void main(String args[])
	{
		controller newController=new controller();
		myUI=new userInterface(newController);
	}
	
	
	
}
