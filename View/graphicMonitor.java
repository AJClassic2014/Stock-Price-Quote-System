package View;


import java.util.ArrayList;
import Model.StockData;

/*
 * graphicMonitor stores the stockData states over time
 * Method:setStock(); setUI();setMonitorState();getMOnitorState();getStock();getMonTypr()updataStocK()
 * setGB();getGB();getUI()
 */
public class graphicMonitor implements observer {
	
	StockData myStock;//is empty now
	userInterface myUI;
	String monitorType;
	private ArrayList<String>monitorState;
	private graphicBuilder myGB;
	//constructor
	public graphicMonitor()
	{
		monitorType="graphic";
		monitorState =new ArrayList<String>();
	}
	//set stock
	public void setStock(StockData newStock)
	{
		myStock=newStock;
	}
	//set UI
	public void setUI(userInterface newUI)
	{
		myUI=newUI;
	}
	/*	
	 *Symbol: NAB.AX       0
	 *LastTrade: 2594.0    1
	 *Date: 22/9/2010      2
	 *Time: 15:24          3
	 *Change: 18.0         4
     *Open: 2589.0         5
	 *DayHigh: 2604.0      6
	 *DayLow: 2574.0       7
	 *Volume: 3280851      8
	*/
	///set monitorState get the stock Data
	public void setMonitorState()
	{
		ArrayList<String[]>temp=myStock.getStockData();
		String[] quotesData=temp.get(1);
		String myStockSymbol=quotesData[0];
		String LastTrade=quotesData[1];
		String time=quotesData[3];
		String dayHigh=quotesData[6];
		String dayLow=quotesData[7];
		monitorState.clear();
		monitorState.add(myStockSymbol);//0
		monitorState.add(LastTrade);//1
		monitorState.add(time);//2
		monitorState.add(dayHigh);//3
		monitorState.add(dayLow);//4
	}
	//return monitorstate
	public ArrayList<String> getMonitorState()
	{
		return monitorState;
	}
	//return stock
	public StockData getStock()
	{
		return myStock;
	}
	//return monitorType
	public String getMonType()
	{
		return monitorType;
	}
	//update the stock data to graphic builder
	public void updateStock() 
	{
		this.setMonitorState();
		myGB.updataStockData();
	}
	//set graphic builder
	public void setGB(graphicBuilder newGB)
	{
		myGB = newGB;
	}
	//return graphic builder
	public graphicBuilder getGB()
	{
		return myGB;
	}
	//return user interface
	public userInterface getUI()
	{
		return myUI;
	}
}
