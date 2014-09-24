package Model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import View.*;
import java.util.Timer;
import org.apache.axis2.AxisFault;
import stockquoteserviceStage1.StockQuoteServiceStage11Stub;
import stockquoteserviceStage1.StockQuoteServiceStage11Stub.GetQuote;
import stockquoteserviceStage2.StockQuoteTimeLapseServiceStub;
import stockquoteserviceStage2.StockQuoteTimeLapseServiceStub.GetStockQuote;
import stockquoteserviceStage2.StockQuoteTimeLapseServiceStub.GetStockQuoteResponse;

/*
 * StockData class gets the stock quote data from the server and stores it
 * Method: scheduleUpdate(); setStockData();getStockData();setStockSymblo()
 *         getobsverList();attach();detach();notifyobserver();setServer()
 *         getServerName();
 */

public class StockData implements stock{

	private String stockSymbol;
	public ArrayList<textMonitor> mymonitor;
	public ArrayList<graphicMonitor> mygraphicMonitor;
	private Timer myTimer;
	private ArrayList<String[]> stockState;
	private StockQuoteServiceStage11Stub QuoteService;
	private StockQuoteTimeLapseServiceStub service;
	public static String[] symbols=null;
	private String myServerName;
	
	public StockData(String stockSymbol, String serverName){
		setStockSymbol(stockSymbol);
		myServerName=serverName;
		stockState = new ArrayList<String[]>();
		mymonitor=new ArrayList<textMonitor>();
		mygraphicMonitor=new ArrayList<graphicMonitor>();
		myTimer=new Timer(true);
		setStockData();
		scheduleUpdate();
		
	}
	// according to which server is selected scheduleUpdate will call notifyobserver() 
	// for the first server the Stock data will be updated every 60secs
	// for the second server the stock data will be updated every 10 secs
	private void scheduleUpdate()
	{
		if(myServerName.equals("StockQuoteTimeLapseService"))
		{
			myTimer.schedule(new java.util.TimerTask()
			{
				public void run() 
				{
					notifyobserver();
				}
			}, 9000, 9000);
		}
		else if(myServerName.equals("StockQuoteServiceStage1"))
		{
			myTimer.schedule(new java.util.TimerTask()
			{
				public void run() 
				{
					notifyobserver();
				}
			}, 58000, 58000);
		}
		
	}
	// according to the server name, get the relative stock data from server
	public void setStockData() 
	{
		String[] FieldNames = null;
		String[] QuoteData=null;
		////set stock data for which use the second server
		if(myServerName.equalsIgnoreCase("StockQuoteTimeLapseService"))
		{
			try {
				service = new StockQuoteTimeLapseServiceStub();
			} catch (AxisFault e) {
				e.printStackTrace();
			}
			try {
				
				FieldNames = service.getFieldNames().get_return();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			try {
				GetStockQuote quoteRequest = new GetStockQuote();
				quoteRequest.setSymbol(this.stockSymbol);
				GetStockQuoteResponse stockQuote = service.getStockQuote(quoteRequest);
				QuoteData = stockQuote.get_return();
			}catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		//set stock data for which use the first server
		else if (myServerName.equalsIgnoreCase("StockQuoteServiceStage1"))
		{
			try {
				QuoteService = new StockQuoteServiceStage11Stub();
			} catch (AxisFault e1) {
				e1.printStackTrace();
			}
			GetQuote QuoteRequest = new GetQuote();
			QuoteRequest.setSymbol(this.stockSymbol);
			
			try {
				FieldNames=QuoteService.getFieldNames().get_return();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			};
			try {
				QuoteData=QuoteService.getQuote(QuoteRequest).get_return();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		stockState.clear();////clear the previous stock data form the stock states
		//store the new stock data to the stock State
		stockState.add(FieldNames);
		stockState.add(QuoteData);
	}
	// this method is used to return stockState
	public ArrayList<String[]> getStockData()
	{
		return stockState;
	}
	//set stockSymbol
	public void setStockSymbol(String newStockSymbol)
	{
		stockSymbol = newStockSymbol;
	}
	//return stockSymbol
	public String getStockSymbol()
	{
		return this.stockSymbol;
	}
	
	//getObserverList(String input) returns a list of monitors of stockData,
	public ArrayList<?> getObserverList(String myMonType)
	{
		if(myMonType.equals("Text"))
		{
			return mymonitor;	
		}
		else if(myMonType.equals("graphic"))
		{
			return mygraphicMonitor;
		}
		return null;
	
	}
	//create a new textMonitor to StockData if  in here we can create two kinds of monitor for StockData
	public void attach(String myMonType)
	{
		if(myMonType.equals("Text"))
		{
			textMonitor myMonitor = new textMonitor();
			mymonitor.add(myMonitor);
		}
		else if(myMonType.equals("graphic"))
		{
			graphicMonitor myGMonitor = new graphicMonitor();//creates a new graphicMonitor
			mygraphicMonitor.add(myGMonitor);//add the newly created GM to graphicMonitorList
		}
		
	}
	//remove monitor(TextMonitor or graphicMonitro) for StockData
	public void detach(String myMonType) 
	{
		if(myMonType.equals("Text"))
		{
			int i=0;
			while(i<mymonitor.size())
			{
				if(mymonitor.get(i).getStock().getStockSymbol().equalsIgnoreCase(this.stockSymbol))
				{
					mymonitor.remove(i);
				}
				i++;
			}
		}
		else if(myMonType.equals("graphic"))
		{	
			int i=0;
			while(i<mygraphicMonitor.size())
			{
				if(mygraphicMonitor.get(i).getStock().getStockSymbol().equalsIgnoreCase(this.stockSymbol))
				{
					mygraphicMonitor.remove(i);
				}
				i++;
			}
			
		}
		
	}
	//notifyobserver() updates the relevant monitor that is monitoring this stockData object
	public void notifyobserver() 
	{
		int i=0,x=0;
		while(i<mymonitor.size())
		{
			mymonitor.get(i).getStock().setStockData();
			mymonitor.get(i).updateStock();
			i++;
		}
		
		while(x<mygraphicMonitor.size())
		{
			mygraphicMonitor.get(x).getStock().setStockData();
			mygraphicMonitor.get(x).updateStock();
			x++;
		}
	}
	// set myServerName
	public void setServer(String newServer)
	{
		myServerName = newServer;
	}
	// get myServerName
	public String getServerName(){
		return myServerName;
	}
}
