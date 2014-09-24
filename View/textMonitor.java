package View;

import java.util.ArrayList;
import Model.StockData;

/*
 * textMonitor stores the most recent stock state from the server
 * methods: setStock(), setUI(), setMonitorState(), getMonitorState(), getStock(), getMonType(), updateStock()
 */
public class textMonitor implements observer {
	
	StockData myStock;
	userInterface myUI;
	String monitorType;
	private ArrayList<String[]>monitorState;
	
	//constructor
	public textMonitor()
	{
		monitorType="Text";
		monitorState =new ArrayList<String[]>();
	}
	// set myStock
	public void setStock(StockData newStock)
	{
		myStock=newStock;
	}
	//set myUI
	public void setUI(userInterface newUI)
	{
		myUI=newUI;
	}
	//set monitorState get the stock Data
	public void setMonitorState()
	{
		monitorState=myStock.getStockData();
	}
	//return monitor state
	public ArrayList<String[]> getMonitorState()
	{
		return monitorState;
	}
	//return myStock object
	public StockData getStock()
	{
		return myStock;
	}
	///get the type of this monitor
	public String getMonType()
	{
		return monitorType;
		
	}
	//update the stock data to user interface
	public void updateStock() 
	{
		this.setMonitorState();///get the stock data
		myUI.update();// sent new stock data to UI
	}
	
}
