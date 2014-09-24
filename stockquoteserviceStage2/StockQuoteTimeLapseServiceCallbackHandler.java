
/**
 * StockQuoteTimeLapseServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package stockquoteserviceStage2;

    /**
     *  StockQuoteTimeLapseServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class StockQuoteTimeLapseServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public StockQuoteTimeLapseServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public StockQuoteTimeLapseServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getSymbols method
            * override this method for handling normal response from getSymbols operation
            */
           public void receiveResultgetSymbols(
                    stockquoteserviceStage2.StockQuoteTimeLapseServiceStub.GetSymbolsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSymbols operation
           */
            public void receiveErrorgetSymbols(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFieldNames method
            * override this method for handling normal response from getFieldNames operation
            */
           public void receiveResultgetFieldNames(
                    stockquoteserviceStage2.StockQuoteTimeLapseServiceStub.GetFieldNamesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFieldNames operation
           */
            public void receiveErrorgetFieldNames(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getStockQuote method
            * override this method for handling normal response from getStockQuote operation
            */
           public void receiveResultgetStockQuote(
                    stockquoteserviceStage2.StockQuoteTimeLapseServiceStub.GetStockQuoteResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getStockQuote operation
           */
            public void receiveErrorgetStockQuote(java.lang.Exception e) {
            }
                


    }
    