package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import Controller.controller;

/*
 * userInterface used to create a GUI for user
 *method: StockData2String();update();addMonitor();addGraphicMonitor();removeMonitor()
 *							 remobeGmonitor(); getController()
 */

@SuppressWarnings("serial")
public class userInterface extends JFrame
{	JButton addbutton=new JButton();
	JComboBox serverSelect;
	JButton addGrephicButton=new JButton();
	JButton removebutton=new JButton();
	JButton exitbutton=new JButton();
	JTextArea myTextArea=new JTextArea("                                                                          " +
			"                                                            Monitor Detail\n ");
	ArrayList<textMonitor> monitorList; 
	ArrayList<graphicMonitor> GmonitorList;
	controller myCon;
	///constructor
	public userInterface(final controller newCon)
	{
		super();
		myCon = newCon;
		FlowLayout flow =new FlowLayout();
		flow.setAlignment(FlowLayout.CENTER);
		flow.setHgap(8);
		flow.setVgap(6);
		setLayout(flow);
		GmonitorList = new ArrayList<graphicMonitor>();
		monitorList=new ArrayList<textMonitor>();
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		// AddTextMonitor button
		
		addbutton=new JButton("AddTextMonitor");
		addbutton.setPreferredSize(new Dimension(125,30));
		addbutton.addActionListener(new ActionListener() 
		{
			//add TextMonitor action performed
            public void actionPerformed(ActionEvent evt)
            {
            	if(myCon.ServerName==null)///if not select server
            	{
            		JOptionPane.showMessageDialog(addbutton, "Please select server first");
            	}
            	else
            	{
            		addButtonActionPerformed(evt);///actionPerformed
            	}
            }
			private void addButtonActionPerformed(ActionEvent evt) 
			{
				String stockSymbol;
				stockSymbol= JOptionPane.showInputDialog("please enter the symbol of a stock which you want to add: e.g. BHP").toUpperCase()+".AX";
				boolean flag=true;
				
				//checks if the newly entered stock already be monitored
				for(int i=0;i<monitorList.size();i++)
				{
					///if already be monitored
					if(monitorList.get(i).getStock().getStockSymbol().equalsIgnoreCase(stockSymbol))
					{
						flag=false;
						JOptionPane.showMessageDialog(addbutton, stockSymbol+"  has already being monitored");
						break;
					}
				}
				///add TextMonitor for this Stock
				if(flag==true)
				{
					myCon.addStock(stockSymbol, "Text");//set the monitor type as "Text"
					String stockinfo = StockData2String(monitorList.size()-1);
					myTextArea.append(stockinfo+"\n");
				}
			}
        });
		add(addbutton);////add this button to the GUI
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		//RemoveTextMonitor button
		removebutton=new JButton("RemoveTextMonitor");
		removebutton.setPreferredSize(new Dimension(150,30));
		removebutton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent evt)
            {
            	//check if monitorList is empty
            	if(monitorList.isEmpty())
            	{
            		JOptionPane.showMessageDialog(removebutton, "There is no any stock is monitored");
            	}
            	else
            	{
            		removeButtonActionPerformed(evt);//remove action performed
            	}
            	
            }
			private void removeButtonActionPerformed(ActionEvent evt) 
			{
				String stockSymbol;
				stockSymbol= JOptionPane.showInputDialog("please enter the symble of a stock which you want to remove: e.g. BHP").toUpperCase()+".AX";
				myCon.removeStock(stockSymbol,"Text");//remove the relative TextMOnitor for this stock
				myTextArea.setText("                                                                          " +
						"                                                            Monitor Detail\n ");
				for(int a=0;a<monitorList.size();a++)
				{
					//Retrieve the stock data except the stock which has be removed
					String stockinfo = StockData2String(a);
					myTextArea.append(stockinfo+"\n");
				}
			}
        });
		add(removebutton);//add this button to GUI
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		// AddGraphicMonitor button
		
		addGrephicButton=new JButton("AddGraphicMnitor");
		addGrephicButton.setPreferredSize(new Dimension(150,30));
		addGrephicButton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent evt)
            {
            	if(myCon.ServerName==null)///select the server first 
            	{
            		JOptionPane.showMessageDialog(addGrephicButton, "Please select server first");
            	}
            	
            	else
            	{	//graphic Monitor only available for the Stock which has access to the second server
            		if(serverSelect.getSelectedItem().equals("StockQuoteServiceStage1"))
                	{
            			JOptionPane.showMessageDialog(addGrephicButton, "Please select the other server");
                	}
            		else
                	{
                		AGBbuttonActionPerformed(evt);//
                	}
            	}
            }
            private void AGBbuttonActionPerformed(ActionEvent evt) 
			{
				String stockSymbol;
				stockSymbol= JOptionPane.showInputDialog("please enter the symbol of a stock which you want to add: e.g. BHP").toUpperCase()+".AX";
				boolean flag=true;
				for(int i=0;i<GmonitorList.size();i++)////if alreade be monitored
				{
					if(GmonitorList.get(i).getStock().getStockSymbol().equalsIgnoreCase(stockSymbol))
					{
						flag=false;
						JOptionPane.showMessageDialog(addGrephicButton, stockSymbol+"  has already being monitored");
						break;
					}
				}
				if(flag==true)
				{
					myCon.addStock(stockSymbol,"graphic");////show the detail in graphic view
				}
			}
        });
		add(addGrephicButton);///add this button to the GUI
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		
		String [] items={"Pelase select Server at here","StockQuoteServiceStage1","StockQuoteTimeLapseService"};
		serverSelect=new JComboBox(items);
		serverSelect.setVisible(true);
		////select the server
		serverSelect.addActionListener(new ActionListener()
		{
			 public void actionPerformed(ActionEvent evt)
	            {
				 serverSelectActionPerformed(evt);
	            }

			private void serverSelectActionPerformed(ActionEvent evt) 
			{
				if(serverSelect.getSelectedItem()=="StockQuoteServiceStage1")
				{
					myCon.selectServer("StockQuoteServiceStage1");///select the first server
				}
				else if(serverSelect.getSelectedItem()=="StockQuoteTimeLapseService")
				{
					myCon.selectServer("StockQuoteTimeLapseService");///select he second server
				}
			}
		});
		add(serverSelect);//add this button to GUI
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Exit button
		
		exitbutton=new JButton("Exit");
		exitbutton.setPreferredSize(new Dimension(60,30));
		exitbutton.setText("Exit");
		exitbutton.addActionListener(new ActionListener() 
		{
           public void actionPerformed(ActionEvent evt)
           {
            	exitButtonActionPerformed(evt);
           }
			private void exitButtonActionPerformed(ActionEvent evt) 
			{
				dispose();///close the GUI
				System.exit(0);///exit the system
			}
        });
		add(exitbutton);//add this button to GUI
		myTextArea.setBackground(Color.black);
		myTextArea.setForeground(Color.white);
		myTextArea.setColumns(85);
		myTextArea.setRows(15);
		myTextArea.setVisible(true);
		myTextArea.setEditable(false);
		add(myTextArea);
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		setBounds(0, 0, 1000, 400);
		setVisible(true);
		setResizable(true);
		setTitle("Stock Quote Monitor");
		setIconImage(new ImageIcon(userInterface.class.getResource("image.jpg")).getImage());	
	}
	//converts stockData from monitor to string which is used to append to myTextArea
	public String StockData2String(int index)
	{
		ArrayList<String[]> text =new ArrayList<String[]>();
		text=monitorList.get(index).getMonitorState();
		String stockData = "";
		String[] fieldName = new String[10];
		String[] quoteData = new String[10];
		fieldName = text.get(0);
		quoteData = text.get(1);
		for(int i=0;i<fieldName.length;i++)
		{
			stockData+=fieldName[i]+": "+quoteData[i]+";  ";	
	    }
		return stockData;
	}
	//update() update the TextMonitor States to user interface
	public void update() 
	{
		myTextArea.setText("                                                                               " +
		"                                                          Monitor Detail\n");
		for(int a=0;a<monitorList.size();a++)
		{
			String stockinfo = StockData2String(a);
			//stockinfo+="60secs";//used for test when the server is closed
			myTextArea.append(stockinfo+"\n");
		}
	}
	//adds monitor to monitorList
	public void addMonitor(textMonitor mon)
	{
		this.monitorList.add(mon);
	}
	//adds graphicMonitor to GmonitorList
	public void addGraphicMon(graphicMonitor Gmon)
	{
		this.GmonitorList.add(Gmon);
	}
	//remove TextMonitor from monitorList
	public void removeMonitor(String stockSymbol )
	{
	   for(int i = 0; i < monitorList.size(); i++)
		{
			if(monitorList.get(i).getStock().getStockSymbol().equalsIgnoreCase(stockSymbol))
			{
				monitorList.remove(i);	
			}	
		}
	}
	//remove graphicMonitor from GmonitorList
	public void removeGMonitor(String stockSymbol )
	{
	    for(int i = 0; i < GmonitorList.size(); i++)
		{
			if(GmonitorList.get(i).getStock().getStockSymbol().equalsIgnoreCase(stockSymbol))
			{
				GmonitorList.remove(i);	
			}	
		}
	}
	//returns controller
	public controller getController()
	{
		return myCon;
	}
	
}
