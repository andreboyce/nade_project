/*
 * GroupStatus.java
 *
 * Created on March 7, 2007, 12:08 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

import java.lang.*;
import java.util.*;

/**
 *This class holds information about groups connected to nade such as their profit, health and contracts won.
 * @author Default
 */

public class GroupStatus implements java.lang.Comparable
{
    /**Sorts in decending order according to monet.
     *Most money first, least money last.
     */
    public int compareTo( Object o )
    {
       try
       {
           GroupStatus gs = (GroupStatus)o;
           if( this.getMoney() == gs.getMoney() )
           {
              return 0;
           }
           // This should be reversed later on when I figure out how to
           // call Collections.sort()
           // right now > should return a positive integer
           if( this.getMoney() > gs.getMoney() )
           {
              return -1;
           }
           if( this.getMoney() < gs.getMoney() )
           {
              return 1;
           }
       }
       catch( Exception e )
       {
          return 0;
       }
       return 0;
    }

    /** The publicName of the group */
    private String Name;

    /** The IP Address of the group */
    private String ipAddress;

    /** The IP Address of the groups information server */
    private String infoServeripAddress;

    /** An estimate of the resources the group has */
    private int    Resources;

    /** The Money/Profit the group has obtained */
    private int    Money;

    /** The TrustWorthyLevel of the group. I dont know if this has any use.*/
    private int    TrustWorthyLevel;

    /** The number of contracts won by the group.*/
    private int    ContractsWon;

    /** The number members in the group.*/
    private int    GroupNumber;

    /** Block messages from this group.*/
    private boolean Blocked;

    /** Did this group win the last contract?*/
    private boolean WonLastContract;

    /**A list containing all the messages sent to and revieved by this client.*/
    private ArrayList<String> Conversation;

    /**A lest containing all the contracts won by this client.*/
    private ArrayList<Contract> ContractsWonList;

    /** Creates a new instance of GroupStatus */
    public GroupStatus()
    {
       ContractsWonList = new ArrayList<Contract>();
       clear();
    }

    /**
     * Resets all values of the class to their default values 
     * except blocked, ipAddress, GroupNumber and Name.
     */
    void resetToNewGame()
    {
        Resources        = 0;
        Money            = 0;
        TrustWorthyLevel = 0;
        ContractsWon     = 0;
        WonLastContract  = false;
        ContractsWonList.clear();
    }

    /**
     * Resets all values of the class to their default values.
     */
    void clear()
    {
        Resources           = 0;
        Money               = 0;
        TrustWorthyLevel    = 0;
        ContractsWon        = 0;
        GroupNumber         = 0;
        ipAddress           = "";
        infoServeripAddress = "";
        Name                = "";
        Blocked             = false;
        WonLastContract     = false;
        Conversation        = new ArrayList<String>();
        ContractsWonList.clear();
    }

    /**@return WonLastContract*/
    public boolean getWonLastContract()
    {
        return WonLastContract;
    }

    /**@param _WonLastContract new value of WonLastContract*/
    public void setWonLastContract( boolean _WonLastContract )
    {
        WonLastContract = _WonLastContract;
    }

    /**Add a Contract to the contracts won list for this client.
     *@param Contract Contract to add.
     */
    public void AddContractToList( Contract contract )
    {
       ContractsWonList.add( contract );
    }

    /**Change the ContractsWonList for this client.
     *@param _ContractsWonList new ContractsWonList value.
     */
    public void setContractsWonList( ArrayList<Contract> _ContractsWonList )
    {
       ContractsWonList = _ContractsWonList;
    }

    /**Return the ContractsWonList for this client.
     *@return ContractsWonList
     */
    public ArrayList<Contract> getContractsWonList()
    {
       return ContractsWonList;
    }

    /**Add a message to the conversation with this client.
     *@param message message to add.
     */
    public void AddConversationMessage( String message )
    {
       Conversation.add( message );
    }

    /**Change the conversation with this client.
     *@param _Conversation new conversation value
     */
    public void setConversation( ArrayList<String> _Conversation )
    {
       Conversation = _Conversation;
    }

    /**Return the conversation with this client.
     *@return Conversation
     */
    public ArrayList<String> getConversation()
    {
       return Conversation;
    }

    /**
     * @return the ipAddress of the group's information server.
     */
    public String getInfoServerIPAddress()
    {
       return infoServeripAddress;
    }

    /**
     * Sets the ipAddress of the group's information server.
     * @param ipAddress of group in question.
     */
    public void setInfoServerIPAddress( String _infoServeripAddress )
    {
       infoServeripAddress = _infoServeripAddress;
    }

    /**
     * Returns the ipAddress of the group.
     */
    public String getIPAddress()
    {
       return ipAddress;
    }

    /**
     * Sets the ipAddress of the group.
     * @param ipAddress of group in question.
     */
    public void setIPAddress( String _ipAddress )
    {
        ipAddress = _ipAddress;
    }

    /**@return Resources*/
    public int getResources()
    {
        return Resources;
    }
    /** Sets the Resources for this client.
     *@param _Resources new Resources.
     */
    public void setResources( int _Resources )
    {
       Resources = _Resources;
    }

    /**@return Money*/
    public int getMoney()
    {
        return Money;
    }
    /** Sets the Money for this client.
     *@param _Money new Money.
     */
    public void setMoney( int _Money )
    {
       Money = _Money;
    }

    /**@return TrustWorthyLevel*/
    public int getTrustWorthyLevel()
    {
        return TrustWorthyLevel;
    }
    /** Sets the TrustWorthyLevel for this client.
     *@param _TrustWorthyLevel new TrustWorthyLevel.
     */
    public void setTrustWorthyLevel( int _TrustWorthyLevel )
    {
       TrustWorthyLevel = _TrustWorthyLevel;
    }

    /**@return Name*/
    public String getName()
    {
        return Name;
    }
    /** Sets the Name for this client.
     *@param _Name new Name.
     */
    public void setName( String _Name )
    {
       Name = _Name;
    }

    /**@return ContractsWon*/
    public int getContractsWon()
    {
        return ContractsWon;
    }
    /** Sets the ContractsWon for this client.
     *@param _ContractsWon new ContractsWon.
     */
    public void setContractsWon( int _ContractsWon )
    {
       ContractsWon = _ContractsWon;
    }

    /**@return GroupNumber*/
    public int getGroupNumber()
    {
       return GroupNumber;
    }
    /** Sets the GroupNumber for this client.
     *@param _GroupNumber new GroupNumber.
     */
    public void setGroupNumber( int _GroupNumber )
    {
        GroupNumber = _GroupNumber;
    }

    /**@return Blocked*/
    public boolean getBlocked()
    {
        return Blocked;
    }
    /** Sets the Blocked state for this client.
     *@param _Blocked new Blocked state.
     */
    public void setBlocked( boolean _Blocked )
    {
        Blocked = _Blocked;
    }

}
