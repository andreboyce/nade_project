/*
 * AutoBidder.java
 *
 * Created on March 7, 2007, 1:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

import java.util.Random;
import java.lang.Math.*;
import java.util.*;

/**
 * Autto Bidding class.
 * @author Default
 */
public class AutoBidder
{

    /** Variable responseable for interaction with nade */
    NadeClient nadeClient;
    
    /** Random number generator*/
    Random generator;

    /**Ho;ds information about previous contracts and bids.*/
    ContractBids contractBids;

    /**Contract time t
     * t = (contract.getMinResources()*contract.getMaxTime())/n;
     */
    double t;

    /**Bid amount.
     * n = (contract.getMinResources()*contract.getMaxTime())/t;
     */
    int    n;
    
    /**
     * Creates a new instance of AutoBidder
     */
    public AutoBidder( NadeClient _nadeClient )
    {
        t            = 10.0;
        n            = 1;
        nadeClient   = _nadeClient;
        generator    = new Random();
        contractBids = new ContractBids( this );
    }

    /**Reduces contract time if we lost*/
    void ReduceContractTimeOnLoss()
    {
       t = t*0.8;
       if( nadeClient.autoBidderDialog.getjCheckBoxReduceContractTimeIfLostBid().isSelected() )
       {
          nadeClient.autoBidderDialog.getjTextFieldCompletionTime().setText( String.valueOf( t ) );
       }
    }

    /**Increase contract time if we won*/
    void IncreaseContractTimeOnWin()
    {
       t = t*1.2;
       if( nadeClient.autoBidderDialog.getjCheckBoxIncreaseContractTimeIfWonBid().isSelected() )
       {
          nadeClient.autoBidderDialog.getjTextFieldCompletionTime().setText( String.valueOf( t ) );
       }
    }

   /**Adjusts the abid amount to finish the contract in a given time.*/
   public double AdjustBidAmountForNewCompletionTime( double newCompletionTime )
   {
      try
      {
         Contract contract = nadeClient.contract;
         double n = (contract.getMinResources()*contract.getMaxTime())/ ( ( newCompletionTime != 0 ) ? newCompletionTime : 1.0 );
         t        = (contract.getMinResources()*contract.getMaxTime())/ ( ( n != 0 ) ? n : 1 );
         return n;
      }
      catch( Exception e )
      {
         return 0.0;
      }
   }

   /**Calculates a bid based on what t is*/
   public void CalculateBid()
   {
      if( nadeClient == null )
      {
         n = 100;
         return;
      }
      Contract contract = nadeClient.contract;
      double ratio = contract.getMinResources()*contract.getMaxTime();
      n = (int)Math.ceil( (ratio/ ((t!=0)?t:1) ) );

      if( nadeClient.autoBidderDialog.getjCheckBoxUseContractValueAlgorithm().isSelected() )
      {
         n = contractBids.CalculateBid();
         t = CalculateTime( n );
      }

      if( n <= contract.getMinResources() )
      {
         n = contract.getMinResources()+10;
      }
   }

   /**Calculates a bid based on what t is*/
   public int CalculateBid( double time )
   {
      Contract contract = nadeClient.contract;
      double ratio = contract.getMinResources()*contract.getMaxTime();
      int bid = (int)(ratio/ ((time!=0)?time:1) );
      return bid;
   }

   /**Calculates a t based on the bid made
    *@param bid Bid to be made
    *@return contract_time Time the contract will be completed in.
    */
   public double CalculateTime( int bid )
   {
      Contract contract = nadeClient.contract;
      double ratio = contract.getMinResources()*contract.getMaxTime();
      double contract_time = (int)(ratio/ ((bid!=0)?bid:1) );
      return contract_time;
   }

   /**This function buys additional resources if they arent enough to make a bid.*/
   public void GatherResourcesForBid()
   {
      if( nadeClient != null )
      {
         GroupStatus ourgroupStatus = nadeClient.ourGroupStatus;
         if( ourgroupStatus.getResources() < n )
         {
            int resources_needed = n - ourgroupStatus.getResources();
            nadeClient.BuyResources( resources_needed );
         }
      }
   }

   /**This function buys additional resources if they arent enough to make a bid.*/
   public void GatherResourcesForBid( int bid )
   {
      if( nadeClient != null )
      {
         GroupStatus ourgroupStatus = nadeClient.ourGroupStatus;
         if( ourgroupStatus.getResources() < bid )
         {
            int resources_needed = bid - ourgroupStatus.getResources();
            nadeClient.BuyResources( resources_needed );
         }
      }
   }

   /**Buys and sells resources from/to NADE.*/
   public void PerformTrading()
   {
      Contract          contract = nadeClient.contract;
      GroupStatus ourgroupStatus = nadeClient.ourGroupStatus;
      int resources_to_sell = 0;
      int resources_to_buy  = 0;

      if( (contract.getCostPerUnit() == 1) && (contract.getNumber() < 80) )
      {
         nadeClient.BuyResources( (int)Math.ceil(ourgroupStatus.getMoney()*0.5) );
      }
      else if( (contract.getCostPerUnit() <= 3) && (contract.getNumber() < 80) )
      {
         // Buy 25 % of our moneys worth in resources
         double money = ourgroupStatus.getMoney();
         money *= (25/100);
         nadeClient.BuyResources( (int) money );
      }
      // 7-9
      else if( (contract.getCostPerUnit() >= 6) && (contract.getCostPerUnit() < 10) )
      {
         // Sell 25 % of our resources
         double res = ourgroupStatus.getResources();
         res *= (25/100);
         nadeClient.SellResources( (int) res );
      }
      else if( contract.getCostPerUnit() == 10 )
      {
         nadeClient.SellResources( ourgroupStatus.getResources() );
         for( int i=0; i<10 ; i++ )
         {
            nadeClient.SellResources( (int)Math.ceil(ourgroupStatus.getResources()*0.1) );
         }
      }

      if( contract.getNumber() <= 85 )
      {
         if( nadeClient.moneyPool > ourgroupStatus.getResources()*contract.getCostPerUnit() )
         {
            nadeClient.SellResources( ourgroupStatus.getResources() );
         }
         else
         {
            resources_to_sell = nadeClient.moneyPool/contract.getCostPerUnit();
            nadeClient.SellResources( resources_to_sell );
         }
      }
   }

    /**
     * Makes a bid depending on several factors such as resource selling price 
     * buying price ect...
     */
    public synchronized void MakeBid()
    {
        if( nadeClient != null )
        {
           try
           {
              Contract                      contract = nadeClient.contract;
              GroupStatus             ourgroupStatus = nadeClient.ourGroupStatus;
              ArrayList<GroupStatus> groupStatusList = nadeClient.groupStatusList;

              boolean                otherClientsConnected = ( groupStatusList.size() > 0 ) ? true : false;
              int bid = 0;

              PerformTrading();
              CalculateBid();

              if( otherClientsConnected )
              {
                 bid = n;
              }
              else
              {
                 bid = contract.getMinResources();
              }
              GatherResourcesForBid( bid );
              nadeClient.MakeBid( bid );
              nadeClient.autoBidderDialog.getjTextFieldResultingBid().setText( String.valueOf( bid ) );
           }
           catch( Exception e )
           {
              nadeClient.display( "AutoBidder.MakeBid - Exception: " + e.getMessage() );
           }
        }
    }

}
