/*
 * Contract.java
 *
 * Created on March 7, 2007, 12:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

/**
 *This class holds information about a contract.
 * @author Default
 */
public class Contract
{

   /**The name of the contract. */
   protected String name;
   
   /**Contract max profit. */
   protected int maxProfit;

   /**Contract max time. */
   protected int maxTime;

   /**Contract min resources. */
   protected int minResources;

   /**Contract bribe resource amount. */
   protected int bribeResourceAmount;

   /**Contract cost per resource unit. */
   protected int costPerUnit;

   /**Contract cost per health unit. */
   protected int costPerHealthUnit;

   /**Contract resource units. */
   protected int resourceUnits;

   /**Contract profit units. */
   protected int profitUnits;

   /**Contract ethics rate. */
   protected double ethicsRate;

   /*------- Additional information needed to helpus win ---------*/
   protected String Winner;
    /*
    *Sets the value of the winner of the contract.
    *@param winner The winner of the contract.*/
   public void setWinner( String winner )
   {
      Winner = winner;
   }
   /**@return Winner*/
   public String getWinner()
   {
      return Winner;
   }

   /**The amount we bid on the contract*/
   protected int OurBidAmount;
    /*
    *Sets the value of OurBidAmount.
    *@param _winner The amount of resources we bid on contract.*/
   public void setOurBidAmount( int _OurBidAmount )
   {
      OurBidAmount = _OurBidAmount;
   }
   /**@return OurBidAmount*/
   public int getOurBidAmount()
   {
      return OurBidAmount;
   }
    
   /** Creates a new instance of Contract */
   public Contract()
   {
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
      Winner              = "";
   }

   /*
    *Calculates the value of a given contract maxprofit/minresources.
    *@return maxprofit/minresources*/
   public double getContractValue()
   {
       if( minResources == 0 )
           return -1;
       return maxProfit/minResources;
   }
 
   /*
    *Calculates the value of a given number of resources.
    *@param resources Resources to determine the value of.
    *@return resources*CostPerUnit*/
   public double getResourceValue( int resources )
   {
       return resources*getCostPerUnit();
   }

   /**
    *@return The number of the contract. 
    * This assumes the contract name contains its number at the end.
    *e.g. Contract1 is number 1, Contract2 is number 2...
    */
   public int getNumber()
   {
      try
      {
         int number = 0;
         if( name == null || name.matches( "" ) )
         {
            return 0;
         }

         String strings[];
         try
         {
            strings = name.split( "Contract" );
         }
         catch( Exception e )
         {
            return 0;
         }

         if( strings.length == 2 )
         {
            try
            {
               number = Integer.valueOf( strings[1] );
            }
            catch( Exception e )
            {
               return 0;
            }
         }
         return number;
      }
      catch( Exception e )
      {
         return 0;
      }
   }

   /**
    * Returns the name of the Contract
    */
   public String getName()
   {
      return(name);
   }
   /**
    * Set the contract name.
    * @param Contract Name.
    */
   public void setName( String _name )
   {
      name = _name;
   }

   /**
    * Returns the maximum Profit forthe Contract.
    */
   public int getMaxProfit()
   {
      return(maxProfit);
   }
    /**
     * Set the maximum profit.
     * @param Contract Maximum Profit.
     */
   public void setMaxProfit( int _maxProfit )
   {
      maxProfit = _maxProfit;
   }

    /**
     * Returns the maximum time.
     */
   public int getMaxTime()
   {
      return(maxTime);
   }
    /**
     * Set the maximum time.
     * @param _maxtime Maximum time.
     */
   public void setMaxTime( int _maxTime )
   {
      maxTime = _maxTime;
   }
	
   /**@return minResources*/
   public int getMinResources()
   {
      return(minResources);
   }
   /*
    *Sets the value of the minimum amount of resources needed to complete the contract in time..
    *@param minResources the value of the minimum amount of resources needed to complete the contract in time.*/
   public void setMinResources( int _minResources )
   {
      minResources = _minResources;
   }

   /**@return bribeResourceAmount*/
   public int getBribeResourceAmount()
   {
      return(bribeResourceAmount);
   }
   /*
    *Sets the Amount of resources needed for a 100% successfull bribe..
    *@param bribeResourceAmount Amount of resources needed for a 100% successfull bribe.*/
   public void setBribeResourceAmount( int _bribeResourceAmount )
   {
      bribeResourceAmount = _bribeResourceAmount;
   }

   /**@return The value per resource when it is bought from or sold to NADE.*/
   public int getCostPerUnit()	
   {
      return costPerUnit;
   }
   /*
    *Sets the value per resource when it is bought from or sold to NADE.
    *@param costPerUnit The value per resource when it is bought from or sold to NADE.*/
   public void setCostPerUnit( int _costPerUnit )
   {
      costPerUnit = _costPerUnit;
   }
	
   /**@return costPerHealthUnit*/
   public int getCostPerHealthUnit()
   {
      return(costPerHealthUnit);
   }
   /*
    *Sets the value per health unit when it is bought from NADE.
    *@param costPerUnit The value per health unit when it is bought from NADE.*/
   public void setCostPerHealthUnit( int _costPerHealthUnit )
   {
      costPerHealthUnit = _costPerHealthUnit;
   }

   /**@return ethicsRate*/
   public double getEthicsRate()
   {
      return ethicsRate;
   }
   /*
    *Sets the ethics rate for this contract.
    *@param _ethicsRate The ethics rate for this contract..*/
   public void setEthicsRate( double _ethicsRate )
   {
      ethicsRate = _ethicsRate;
   }
	
   /**@return resourceUnits*/
   public int getResourceUnits()
   {
      return(resourceUnits);
   }
    /*
    *Sets the resource units injected into nade.
    *@param _resourceUnits The resource units injected into nade.*/
   public void setResourceUnits( int _resourceUnits )
   {
      resourceUnits = _resourceUnits;
   }
	
   /**@return profitUnits*/
   public int getProfitUnits() 
   {
      return(profitUnits);
   }

    /*
    *Sets the profit units injected into nade.
    *@param _profitUnits The profit units injected into nade.*/
   public void setProfitUnits( int _profitUnits ) 
   {
      profitUnits = _profitUnits;
   }
   
}
