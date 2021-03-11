/*
 * TheMessager.java
 *
 * Created on February 27, 2007, 11:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

import java.io.*;
import java.util.*;
import nade.client.NDConstants;

/**
 *This class parses messages sent by the NADE control.
 */
public class MessageParser implements NDConstants
{

    /**Contract name*/
   protected String name;

   /**Contract maxProfit*/
   protected int maxProfit;

   /**Contract maxTime*/
   protected int maxTime;

   /**Contract minResources*/
   protected int minResources;

   /**Contract bribeResourceAmount*/
   protected int bribeResourceAmount;

   /**Contract costPerUnit*/
   protected int costPerUnit;

   /**Contract costPerHealthUnit*/
   protected int costPerHealthUnit;

   /**Contract resourceUnits*/
   protected int resourceUnits;

   /**Contract profitUnits*/
   protected int profitUnits;

   /**Contract ethicsRate*/
   protected double ethicsRate;

   /**Action number.*/
   protected int actionNum;

   /**Dender of message.*/
   protected String sender;

   /**Receiver of message.*/
   protected String receiver;

   /**Entire Value of message*/
   protected String value;

   /**Value1 of message*/
   protected String value1;

   /**Value2 of message*/
   protected String value2;

   /**Constructor */
   public MessageParser()
   {
      /*** Contract Attributes Initialisation ***/
      name                = "";
      maxProfit           = 0;
      maxTime             = 0;
      minResources        = 0;
      bribeResourceAmount = 0;
      costPerUnit         = 0;
      costPerHealthUnit   = 0;
      ethicsRate          = 0.00;
      resourceUnits       = 0;
      profitUnits         = 0;
			
      /*** Message Attributes Initialisation ***/
      actionNum = 0;
      sender    = "";
      receiver  = "";
      value     = "";
      value1    = "";
      value2    = "";
   }//TheMessager
	
	
   /**Creats a message*/
   public String createMessage (int action, String sender, String receiver, String value)
   {
      return(""+action+":\""+sender+"\":\""+receiver+"\":\""+value+"\"");
   }

   /**Creats a message*/
   public String createMessage (int action, String sender, String receiver, String value1,  String value2)
   {
      return(""+action+":\""+sender+"\":\""+receiver+"\":\"'"+value1+"':'"+value2+"'\"");
   }	

   /**@return name*/
   public String getName()
   {
      return(name);
   }//getName
	
   /**@return MaxProfit*/
   public int getMaxProfit()
   {
      return(maxProfit);
   }//getMaxProfit
	
   /**@return MaxTime*/
   public int getMaxTime()
   {
      return(maxTime);
   }//getMaxTime
	
   /**@return MinResources*/
   public int getMinResources()
   {
      return(minResources);
   }//getMinResources
	
   /**@return BribeResourceAmount*/
   public int getBribeResourceAmount()
   {
      return(bribeResourceAmount);
   }//getBribeResourceAmount
		
   /**@return CostPerUnit*/
   public int getCostPerUnit()	
   {
      return(costPerUnit);		
   }//getCostPerUnit
	
   /**@return CostPerHealthUnit*/
   public int getCostPerHealthUnit()
   {
      return(costPerHealthUnit);
   }//getCostPerHealthUnit
		
   /**@return EthicsRate*/
   public double getEthicsRate()
   {
      return(ethicsRate);
   }//getEthicsRate
	
   /**@return ResourceUnits*/
   public int getResourceUnits()
   {
      return(resourceUnits);
   }//getResourceUnits
	
   /**@return ProfitUnits*/
   public int getProfitUnits() 
   {
      return(profitUnits);
   }
			
   /**@return ActionNum*/
   public int getActionNum()
   {
      return(actionNum);
   }//getActionNum
			
   /**@return sender*/
   public String getSender()
   {
      return(sender);
   }//getSender
		
   /**@return receiver*/
   public String getReceiver()
   {
      return(receiver);
   }//getReceiver

   /**@return value*/
   public String getValue()
   {
      return(value);
   }//getValue

   /**@return value1*/
   public String getValue1()
   {
      return(value1);
   }//getValue1
			
   /**@return value2*/
   public String getValue2()
   {
      return(value2);
   }//getValue2

   /*
    *Parses the contract information.
    *@param contractAttributes Value of the PROTOCOL_USE_CONTRACT message
    */
   public void parseContract( String contractAttributes )
   {
      StreamTokenizer stream = new StreamTokenizer( new StringReader( contractAttributes ) ); 
       
      stream.wordChars( '_', '_' );          // a word contains underscores 
      stream.eolIsSignificant( false );      // Ignore new line characters 
      stream.quoteChar( '\'' );

      try
      {
         stream.nextToken();				// string
         name  = stream.sval;				// contract name
         stream.nextToken(); 				// :
         stream.nextToken(); 				// number
         maxTime = (int)stream.nval;			// maximum time
         stream.nextToken(); 				// :
         stream.nextToken(); 				// number
         minResources = (int)stream.nval;		// minimum resources
         stream.nextToken(); 				// :
         stream.nextToken(); 				// number
         maxProfit = (int)stream.nval;			// maximum profit
         stream.nextToken(); 				// :
         stream.nextToken(); 				// number
         costPerUnit = (int)stream.nval;		// cost per unit
         stream.nextToken(); 				// :
         stream.nextToken(); 				// number
         costPerHealthUnit = (int)stream.nval;	// cost per health unit
         stream.nextToken(); 				// :
         stream.nextToken(); 				// number
         bribeResourceAmount = (int)stream.nval;	// bribe resource amount
         stream.nextToken();				// :
         stream.nextToken(); 				// number
         ethicsRate = stream.nval;			// ethics rate
         stream.nextToken(); 				// :
         stream.nextToken(); 				// number
         resourceUnits = (int)stream.nval;	// resources added to resource pool
         stream.nextToken(); 				// :
         stream.nextToken(); 				// number
         profitUnits = (int)stream.nval;	// money added to resource pool
      }	
      catch( IOException e )
      {
      }
   } // parseContract	

   /**
    *Parses a message of format
    *actionNum:sender:receiver:value
    *@param message string to parse
    */
   public boolean parseMessage( String message )
   {
      try
      {
         value  =  "";
         value1 =  "";
         value2 =  "";
         boolean validMessage = false;
         
         StreamTokenizer t = new StreamTokenizer( new StringReader( message ) );
         int tokenType;

         // Setup tokenizer
         t.wordChars( '_', '_' );     // a word contains underscores
         t.eolIsSignificant( false ); // Ignore new line characters
         t.quoteChar( '\'' );

         if( t.ttype != t.TT_EOF )
            validMessage = false;
         t.nextToken();            // number
         actionNum  = (int)t.nval; // action number
         if( t.ttype != t.TT_EOF )
            validMessage = false;
         t.nextToken();            // :
	 t.nextToken();            // string
         if( t.ttype != t.TT_EOF )
            validMessage = false;
   	 sender = t.sval;          // sender
         t.nextToken();            // :
         t.nextToken();            // string
         if( t.ttype != t.TT_EOF )
            validMessage = false;
         receiver = t.sval;        // receiver
         t.nextToken();            // :
         t.nextToken();            // string
         if( t.ttype != t.TT_EOF )
            validMessage = false;

         value =  t.sval;
         StreamTokenizer stream2 = new StreamTokenizer( new StringReader( value ) ); 
       
         stream2.wordChars( '_', '_' );     // a word contains underscores 
         stream2.eolIsSignificant( false ); // Ignore new line characters 
         stream2.quoteChar( '\'' );

         // matches "'':''"
         if( value.matches( "'.*':'.*'" ) )
         {
            stream2.nextToken();    // string
            value1 = stream2.sval;  // value1
            stream2.nextToken();    // :
            stream2.nextToken();    // string
            value2 = stream2.sval;  // value2
         }
         else
         {
            if( value.matches( "^'\\w+'(:\\d+(\\.\\d+)?){9}$" ) && 
                (actionNum == NadeClient.PROTOCOL_USE_CONTRACT) &&
                (sender.equals( NadeClient.CONTROL_NAME) )
              )
            {
                parseContract( value );
            }
            else
            {
               value1 = t.sval;        // value1
            }
         }
         return validMessage;
      }
      catch( Exception e )
      {
          return false;
      }
   } // parseMessage
}
