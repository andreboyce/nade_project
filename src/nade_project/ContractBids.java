/*
 * ContractBids.java
 *
 * Created on March 30, 2007, 12:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;
import java.util.*;
import java.math.*;

/**
 * This file keeps tracks of the contracts that have passed.
 * Who won them and how much we bid on them.
 * This information is used to adjust our current bids.
 * @author Default
 */
public class ContractBids
{

    /*This holds information about the contracts that have passed.
     *Rather than make a new class to hold information about previous bids on contracts
     *I will extend the contract class to hold the extra information.
     */
    ArrayList<Contract> contracts;

    /**
     *   0-500   ... bids
     * 500-1000  ... bids
     *1000-2000  ... bids
     *2000-3000  ... bids
     *3000-4000  ... bids
     *4000-5000  ... bids
     *5000-*     ... bids
     */
    ArrayList< ArrayList<Contract> > bids;


    /** Auto bidder class*/
    AutoBidder autoBidder;

    /** This variable keeps track of the current value of all our resources
     *That is how much money they are worth,
     *NADE gives us 1000 resources to start with
     *and I assume them to be worth $5 each initially totaling $500.
     */
    public double averageResourceCost;

    /**Keeps track of the value of the resources we have*/
    public double UpdateAverageResourceCost( int newResources, double value )
    {
       int ourResources = autoBidder.nadeClient.ourGroupStatus.getResources();
       // newResources > 0 implies that we bought resources
       // newResources < 0 implies that we sold resources
       // Selling resources does not have an effect on the resources average value
       if( newResources != 0 && ourResources != 0 && newResources > 0 )
       {
          if( ((ourResources*averageResourceCost + newResources*value)) != 0 )
          {
             averageResourceCost = (ourResources*averageResourceCost + newResources*value) /
                                   ( ourResources + newResources );
          }
       }
       autoBidder.nadeClient.autoBidderDialog.getjTextFieldAverageResourceCost().setText( String.valueOf( averageResourceCost ) );
       return averageResourceCost;
    }

    /** Creates a new instance of ContractBids */
    public ContractBids( AutoBidder _autoBidder )
    {
       contracts           = new ArrayList<Contract>();
       autoBidder          = _autoBidder;
       averageResourceCost = 5.0;
       bids                = new ArrayList< ArrayList<Contract> >( 7 );
       for( int i=0 ; i<7 ; i ++ )
       {
          bids.add( new ArrayList<Contract>() );
       }
    }
 
    /**Removes all contracts from the list.*/
    public void reset()
    {
       averageResourceCost = 5.0;
       contracts.clear();
       bids.clear();
       for( int i=0 ; i<7 ; i ++ )
       {
          bids.add( new ArrayList<Contract>() );
       }
    }

    /**Adds a contract to the list.
     *@param contract The contract to add to the list
     */
    public void AddContract( Contract contract )
    {
       //contracts.add( contract );

       try
       {
          if( contract.getMaxProfit() >= 0 && contract.getMaxProfit() < 500 )
          {
             ArrayList<Contract> ZeroTo500Bid = bids.get( 0 );
             ZeroTo500Bid.add( contract );
          }
          else if( contract.getMaxProfit() >= 500 && contract.getMaxProfit() < 1000 )
          {
             ArrayList<Contract> FiveHundredTo1000Bid =  bids.get( 1 );
             FiveHundredTo1000Bid.add( contract );
          }
          else if( contract.getMaxProfit() >= 1000 && contract.getMaxProfit() < 2000 )
          {
             ArrayList<Contract> OneThousandTo2000Bid =  bids.get( 2 );
             OneThousandTo2000Bid.add( contract );
          }
          else if( contract.getMaxProfit() >= 2000 && contract.getMaxProfit() < 3000 )
          {
             ArrayList<Contract> TwoThousandTo3000Bid =  bids.get( 3 );
             TwoThousandTo3000Bid.add( contract );
          }
          else if( contract.getMaxProfit() >= 3000 && contract.getMaxProfit() < 4000 )
          {
             ArrayList<Contract> ThreeThousandTo4000Bid =  bids.get( 4 );
             ThreeThousandTo4000Bid.add( contract );
          }
          else if( contract.getMaxProfit() >= 4000 && contract.getMaxProfit() < 5000 )
          {
             ArrayList<Contract> FourThousandTo5000Bid =  bids.get( 5 );
             FourThousandTo5000Bid.add( contract );
          }
          else if( contract.getMaxProfit() >= 5000 )
          {
             ArrayList<Contract> FiveThousandAndGreaterBid =  bids.get( 6 );
             FiveThousandAndGreaterBid.add( contract );
          }
       }
       catch( Exception e )
       {
          autoBidder.nadeClient.display( "ContractBids.AddContract - Exception: " + e.getMessage() );
       }
    }

    /**Gets a contract from the list.
     *@param index The contract to get from the list.
     *0 returns contract 1
     *1 returns contract 2...
     */
    public Contract getContract( int index )
    {
       try
       {
          return contracts.get( index );
       }
       catch( Exception e )
       {
          return null;
       }
    }

    /**Calculates the highest bid you can make on a contract without making a loss
     *@param contract the contract to perform the calculation on
     *@return the break even bid.
     */
    public int getBreakEvenBid( Contract contract )
    {
       int bid = 0;
       double average_resource_cost = 5.0;
       /*In an ideal situation you would be able to sell all your resources
        *for 10$ each and buy all your resources for $1 each. If this were the case
        *then you could bid up to 500 resources on a contract returning $500 because 
        *you bought your resources for $500.
        *Sadly this is not an ideal scenario and the resource rate varies from 1 - 10.
        *So what I am doing is assuming all our resources are worth $5 and dividing the 
        *return on the contract by 5 to determine its value in resources.
        *This would then be a fairly good estimate of what is the most you should ever bid on a contract.
        */
       bid = (int)Math.ceil( contract.getMaxProfit()/average_resource_cost );
       return bid;
    }

    /**Calculates the highest bid you can make on a contract without making a loss
     *@param MaxProfit the contract MaxProfit to perform the calculation with
     *@return the break even bid.
     */
    public int getBreakEvenBid( int MaxProfit )
    {
       int                      bid = 0;
       double average_resource_cost = 5.0;
       /*In an ideal situation you would be able to sell all your resources
        *for 10$ each and buy all your resources for $1 each. If this were the case
        *then you could bid up to 500 resources on a contract returning $500 because 
        *you bought your resources for $500.
        *Sadly this is not an ideal scenario and the resource rate varies from 1 - 10.
        *So what I am doing is assuming all our resources are worth $5 and dividing the 
        *return on the contract by 5 to determine its value in resources.
        *This would then be a fairly good estimate of what is the most you should ever bid on a contract.
        */
       bid = (int)Math.ceil( MaxProfit/average_resource_cost );
       return bid;
    }

    /**
     *Finds the highest bid in a given list of contracts
     * @param ArrayList<Contract> contract list
     *@return bid
     */
    int getHighestBidAmount( ArrayList<Contract> contract_list )
    {
       try
       {
          int bid = 0;
          for( int i=0 ; i<contract_list.size() ; i++ )
          {
             if( contract_list.get(i).getOurBidAmount() > bid )
             {
                bid = contract_list.get(i).getOurBidAmount();
             }
          }
          return bid;
       }
       catch( Exception e )
       {
          return 0;
       }
    }

    /**
     *Finds the highest bid in a given list of contracts
     * @param ArrayList<Contract> contract list
     *@return bid
     */
    Contract getContractWithHighestBidAmount( ArrayList<Contract> contract_list )
    {
       try
       {
          Contract contract = null;
          int bid = 0;
          for( int i=0 ; i<contract_list.size() ; i++ )
          {
             if( contract_list.get(i).getOurBidAmount() > bid )
             {
                bid = contract_list.get(i).getOurBidAmount();
                contract = contract_list.get(i);
             }
          }
          return contract;
       }
       catch( Exception e )
       {
          return null;
       }
    }

    /** Check our previous bids to see if we lost for a contract 
     * of similar return and increase bid accordingly*/
    public int getBidForGivenContractValue()
    {
       int           bid = 0;
       Contract contract = autoBidder.nadeClient.contract;

       try
       {
          if( contract.getMaxProfit() >= 0 && contract.getMaxProfit() < 500 )
          {
             ArrayList<Contract> ZeroTo500Bid =  bids.get( 0 );
             if( ZeroTo500Bid.size() > 0 )
             {
                //Contract last_contract = ZeroTo500Bid.get( ZeroTo500Bid.size()-1 );
                // If we didnt win the last contract of similar value increase bid  by 10%
                //if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                //{
                //    bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.10 );
                //}
                Contract last_contract = getContractWithHighestBidAmount( ZeroTo500Bid );
                if( last_contract == null )
                {
                   bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
                }
                else
                {
                   bid = getHighestBidAmount( ZeroTo500Bid );
                   if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                   {
                       bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.05 );
                   }
                }
             }
             else
             {
                //bid = 50;
                bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
             }
          }
          else if( contract.getMaxProfit() >= 500 && contract.getMaxProfit() < 1000 )
          {
             ArrayList<Contract> FiveHundredTo1000Bid =  bids.get( 1 );
             if( FiveHundredTo1000Bid.size() > 0 )
             {
                //Contract last_contract = FiveHundredTo1000Bid.get( FiveHundredTo1000Bid.size()-1 );
                // If we didnt win the last contract of similar value increase bid  by 10%
                //if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                //{
                //    bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.10 );
                //}
                Contract last_contract = getContractWithHighestBidAmount( FiveHundredTo1000Bid );
                if( last_contract == null )
                {
                   bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
                }
                else
                {
                   bid = getHighestBidAmount( FiveHundredTo1000Bid );
                   if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                   {
                       bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.05 );
                   }
                }
             }
             else
             {
                //bid = 100;
                bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
             }
          }
          else if( contract.getMaxProfit() >= 1000 && contract.getMaxProfit() < 2000 )
          {
             ArrayList<Contract> OneThousandTo2000Bid =  bids.get( 2 );
             if( OneThousandTo2000Bid.size() > 0 )
             {
                //Contract last_contract = OneThousandTo2000Bid.get( OneThousandTo2000Bid.size()-1 );
                // If we didnt win the last contract of similar value increase bid  by 10%
                //if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                //{
                //    bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.10 );
                //}
                Contract last_contract = getContractWithHighestBidAmount( OneThousandTo2000Bid );
                if( last_contract == null )
                {
                   bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
                }
                else
                {
                   bid = getHighestBidAmount( OneThousandTo2000Bid );
                   if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                   {
                      bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.05 );
                   }
                }
             }
             else
             {
                //bid = 200;
                bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
             }
          }
          else if( contract.getMaxProfit() >= 2000 && contract.getMaxProfit() < 3000 )
          {
             ArrayList<Contract> TwoThousandTo3000Bid =  bids.get( 3 );
             if( TwoThousandTo3000Bid.size() > 0 )
             {
                //Contract last_contract = TwoThousandTo3000Bid.get( TwoThousandTo3000Bid.size()-1 );
                // If we didnt win the last contract of similar value increase bid  by 10%
                //if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                //{
                //    bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.10 );
                //}
                Contract last_contract = getContractWithHighestBidAmount( TwoThousandTo3000Bid );
                if( last_contract == null )
                {
                   bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
                }
                else
                {
                   bid = getHighestBidAmount( TwoThousandTo3000Bid );
                   if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                   {
                      bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.05 );
                   }
                }
             }
             else
             {
                //bid = 300;
                bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
             }
          }
          else if( contract.getMaxProfit() >= 3000 && contract.getMaxProfit() < 4000 )
          {
             ArrayList<Contract> ThreeThousandTo4000Bid =  bids.get( 4 );
             if( ThreeThousandTo4000Bid.size() > 0 )
             {
                //Contract last_contract = ThreeThousandTo4000Bid.get( ThreeThousandTo4000Bid.size()-1 );
                // If we didnt win the last contract of similar value increase bid  by 10%
                //if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                //{
                //    bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.10 );
                //}
                Contract last_contract = getContractWithHighestBidAmount( ThreeThousandTo4000Bid );
                if( last_contract == null )
                {
                   bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
                }
                else
                {
                   bid = getHighestBidAmount( ThreeThousandTo4000Bid );
                   if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                   {
                      bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.05 );
                   }
                }
             }
             else
             {
                //bid = 400;
                bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
             }
          }
          else if( contract.getMaxProfit() >= 4000 && contract.getMaxProfit() < 5000 )
          {
             ArrayList<Contract> FourThousandTo5000Bid =  bids.get( 5 );
             if( FourThousandTo5000Bid.size() > 0 )
             {
                //Contract last_contract = FourThousandTo5000Bid.get( FourThousandTo5000Bid.size()-1 );
                // If we didnt win the last contract of similar value increase bid  by 10%
                //if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                //{
                //   bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.10 );
                //}
                Contract last_contract = getContractWithHighestBidAmount( FourThousandTo5000Bid );
                if( last_contract == null )
                {
                   bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
                }
                else
                {
                   bid = getHighestBidAmount( FourThousandTo5000Bid );
                   if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                   {
                      bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.05 );
                   }
                }
             }
             else
             {
                //bid = 500;
                bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
             }
          }
          else if( contract.getMaxProfit() >= 5000 )
          {
             ArrayList<Contract> FiveThousandAndGreaterBid =  bids.get( 6 );
             if( FiveThousandAndGreaterBid.size() > 0 )
             {
                //Contract last_contract = FiveThousandAndGreaterBid.get( FiveThousandAndGreaterBid.size()-1 );
                // If we didnt win the last contract of similar value increase bid  by 10%
                //if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                //{
                //    bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.10 );
                //}
                Contract last_contract = getContractWithHighestBidAmount( FiveThousandAndGreaterBid );
                if( last_contract == null )
                {
                   bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
                }
                else
                {
                   bid = getHighestBidAmount( FiveThousandAndGreaterBid );
                   if( !last_contract.getWinner().equals( autoBidder.nadeClient.ourGroupStatus.getName() ) )
                   {
                      bid = (int)Math.ceil( last_contract.getOurBidAmount()*1.05 );
                   }
                }
             }
             else
             {
                //bid = 600;
                bid = (int)Math.ceil( contract.getMaxProfit()/averageResourceCost );
             }
          }
       }
       catch( Exception e )
       {
          autoBidder.nadeClient.display( "ContractBids.getBidForGivenContractValue - Exception: " + e.getMessage() );
       }
       return bid;
    }

    /**Calculates a bid based on past contracts*/
    public int CalculateBid()
    {
       int                        bid = 100;
       double   average_resource_cost = averageResourceCost;
       Contract              contract = autoBidder.nadeClient.contract;

       bid = getBidForGivenContractValue();

       //bid = (int)Math.ceil( contract.getMaxProfit()/average_resource_cost );
       if( bid > getBreakEvenBid( contract ) )
       {
          if( !autoBidder.nadeClient.autoBidderDialog.getjCheckBoxOverrideBreakEvenBid().isSelected() )
          {
             bid = getBreakEvenBid( contract );
          }
       }
       return bid;
    }
    
}
