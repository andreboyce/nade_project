/*
 * NadeClient.java
 *
 * Created on February 22, 2007, 11:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Default
 */
package nade_project;

import nade.client.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import javax.swing.text.*;

/**
 * This is the most important class in the program.
 * It handles the connection to the NADE server. 
 * Most other classes are just help it to perform its function.
 */
public class NadeClient extends Thread implements NDConstants
{
    /** Stream for sending data. */
    private ObjectOutputStream output;

    /** Stream for receiving data. */
    private ObjectInputStream input;

    /** Connection to NADE server.*/
    private Socket client;

    /** Last message recieved from the NADE server. */
    private String serverMessage;

    /** Unique ID sent to us by the server. */
    private String ClientID;

    /** Class that parses messages. */
    private MessageParser messageParser;

    /** The main interface for the entire project*/
    public NadeProjectGUIInterface nadeProjectGUIInterface;

    /** Class used to read/write to the database*/
    public DatabaseAccessClass databaseAccessClass;

    /** Information server main class.
     * Other groups may connect to this server and send simple messages:
     * name: publicName of our group
     * group_number: number ou members in our group
     * contracts_won: the number of contracts our group has won.
     * disconnect: disconnect from our server
     */
    public NadeInformationServer nadeInformationServer;

    /** Variable used to continue reading server messages. 
      * When set to false the client will disconnect from the NADE server.
      */
    public boolean   bLoop;

    /** Message parser test dialog.*/
    public  MessageParserTestGUI messageParserTestGUI;

    /** Our group status.*/
    public GroupStatus ourGroupStatus;

    /** List of other clients group status.*/
    public ArrayList<GroupStatus> groupStatusList;

    /** Game length.*/
    private int gameLength;

    /** Resource pool.*/
    public int resourcePool;

    /** Money pool.*/
    public int moneyPool;

    /** Current contrat being offered.*/
    public Contract contract;

    /** Contract information dialog.*/
    public ContractInfoDialog contractInfoDialog;

    /** Banking dialog.*/
    private BankingDialog bankingDialog;

    /** Trading dialog.*/
    private TradingDialog tradingDialog;
    
    /** Auto bidding class.*/
    public AutoBidder autoBidder;

    /** Security breach dialog.*/
    private SecurityBreachDialog securityBreachDialog;

    /** Information server client dialog.*/
    private InformationServerClientGUI informationServerGUI;

    /** Rumor dialog.*/
    private RumourDialog rumourDialog;

    /** Chat dialog.*/
    private ChatDialog chatDialog;

    /** Bribe dialog.*/
    private BribeDialog bribeDialog;

    /** Varable to store automatically generated public name
     *Used when alother instance of this program is running.
     */
    private String AlternatePublicName;

    /** This number will be appended to the publicName if a client 
      * with that public name already exists
      */
    private int ClientInstanceNumber;

    /** Orignal title of the NADE gui.*/
    private String OrignalTitle;

    /** Class used for saving and retrieving settings such as database password.*/
    private INIFile iniFile;

    /** Class used for auto bidding.*/
    public AutoBidderDialog autoBidderDialog;

    /** StandingsDialog used to display first, second, last ect...*/
    public StandingsDialog standingsDialog;

    /**Settings File Name*/
    public String settingsFileName;

    /**Keeps track of if you can bid or not*/
    public boolean bBiddingInProgress;
    
    /** Creates a new instance of NadeClient */
    public NadeClient( NadeProjectGUIInterface _nadeProjectGUIInterface )
    {
        nadeProjectGUIInterface = _nadeProjectGUIInterface;

        bBiddingInProgress      = false;
        AlternatePublicName     = "";
        ourGroupStatus          = new GroupStatus();
        nadeProjectGUIInterface = null;
        bLoop                   = false;
        messageParser           = new MessageParser();
        databaseAccessClass     = new DatabaseAccessClass();
        nadeInformationServer   = null;
        messageParserTestGUI    = new MessageParserTestGUI( this );
        gameLength              = 0;
        resourcePool            = 0;
        moneyPool               = 0;
        autoBidder               = new AutoBidder( this );
        contract                = new Contract();
        contractInfoDialog      = new ContractInfoDialog();
        bankingDialog           = new BankingDialog( this );
        tradingDialog           = new TradingDialog( this );
        securityBreachDialog    = new SecurityBreachDialog( this );
        informationServerGUI    = new InformationServerClientGUI( this );
        client                  = null;
        groupStatusList         = new ArrayList<GroupStatus>();
        rumourDialog            = new RumourDialog( this );
        chatDialog              = new ChatDialog( this );
        bribeDialog             = new BribeDialog( this );
        autoBidderDialog        = new AutoBidderDialog( this );
        standingsDialog         = new StandingsDialog( this );
        ClientInstanceNumber    = 1;
        OrignalTitle            = "";
        if( nadeProjectGUIInterface != null )
        {
           settingsFileName        = nadeProjectGUIInterface.settingsFileName;
           iniFile                 = nadeProjectGUIInterface.iniFile;
        }
        else
        {
           settingsFileName        = "settings.ini";
           iniFile                 = null;
        }
    }

    /**@return settingsFileName*/
    public String getSettingsFileName()
    {
       return settingsFileName;
    }

    /**Set standingsDialog to visable.*/
    public void ShowStandingsDialog()
    {
        standingsDialog.setVisible( true );
    }
    /**
     * Set autoBidderDialog dialog to visible
     */
    void ShowAutoPilotOptions()
    {
        autoBidderDialog.setVisible( true );
    }
    /** Set bribeDialog to visible*/
    void ShowBribeDialog()
    {
        bribeDialog.setVisible( true );
    }
    /** Set chatDialog to visible*/
    void ShowChatDialog()
    {
        chatDialog.setVisible( true );
        int index = this.nadeProjectGUIInterface.getchoiceGroups().getSelectedIndex();
        chatDialog.getjComboBoxGroup().setSelectedIndex( index );
    }
    /** Set rumorDialog to visible*/
    void ShowRumourDialog()
    {
        rumourDialog.setVisible( true );
    }
    /** Set informationServer Dialog to visible*/
    void ShowInformationServerGUI()
    {
        informationServerGUI.setVisible( true );
    }
    /** Set securityBreachDialog to visible*/
    void ShowSecurityBreachDialog()
    {
        securityBreachDialog.setVisible( true );
    }

    /** Set bankingDialog to visible*/
    void ShowBankingDialog()
    {
        bankingDialog.setVisible( true );
    }

    /** Set tradingDialog to visible*/
    void ShowTradingDialog()
    {
        tradingDialog.setVisible( true );
    }

    /** Set messageParserTest Dialog to visible*/
    void ShowMessageTest()
    {
        messageParserTestGUI.setVisible( true );
        messageParserTestGUI.Parse();
    }

    /** Set contractInfoDialog Dialog to visible*/
    void ShowContractInfoDialog()
    {
        contractInfoDialog.setVisible( true );
    }

    /*
     *This method adds and parses a message and displays its components.
     *@param message Message to add and parse.
     */
    void AddTestMessage( String message )
    {
        messageParserTestGUI.getjTextAreaMessage().setText( "" );
        messageParserTestGUI.getjTextAreaMessage().append( message );
        messageParserTestGUI.Parse();
    }


    /**
     * NADE clinet Main loop.
     */
    public void run()
    {
        try
        {
           client = null;
           AlternatePublicName = "";
           OrignalTitle = nadeProjectGUIInterface.getTitle();
           int port = 5000;
           port = Integer.valueOf( nadeProjectGUIInterface.getjFormattedTextFieldPort().getText() );
           InetAddress ipAddress = InetAddress.getByName( nadeProjectGUIInterface.getjFormattedTextFieldIPAddress().getText() );
           display( "Connecting to: " + ipAddress.getHostAddress() + " @ " + String.valueOf( port ) );
           client = new Socket( ipAddress, port );
           output = new ObjectOutputStream( client.getOutputStream() );
           output.flush();
           input = new ObjectInputStream( client.getInputStream() );
           bLoop = true;
           nadeProjectGUIInterface.getjFormattedTextFieldLocalIPAddress().setText( client.getLocalAddress().getHostAddress() );//"127.0.0.1"
           ourGroupStatus.setIPAddress( client.getLocalAddress().getHostAddress() );
           ourGroupStatus.setInfoServerIPAddress( client.getLocalAddress().getHostAddress() );
           setButtonsToConnectedState();
           StartInformationServer();
           if( nadeProjectGUIInterface.getjCheckBoxAutoPilot().isSelected() )
           {
              //display( "AutoPilot is enabled. (Auto Bidding ON)" );
           }
           else
           {
              //display( "AutoPilot is disabled. (Auto Bidding OFF)" );
           }
           do
           {
              try
              {
                  serverMessage = (String) input.readObject();
                  serverMessage = serverMessage.replaceAll( "\b", "\\b" );
                  serverMessage = serverMessage.replaceAll( "\t", "\\t" );
                  serverMessage = serverMessage.replaceAll( "\r", "\\r" );
                  serverMessage = serverMessage.replaceAll( "\n", "\\n" );
                  serverMessage = serverMessage.replaceAll( "\f", "\\f" );
                  if( serverMessage.equals( "" ) )
                  {
                     break;
                  }
                  try
                  {
                     HandleMessage( serverMessage );
                  }
                  catch( Exception e )
                  {
                     display( "HandleMessage - Exception: " + e.getMessage() );
                  }
              }
              catch( ClassNotFoundException e )
              {
                  display( "Unknown Object Type Recieved" );
              }
           } while( bLoop );
           output.close();
           input.close();
           client.close();
           StopInformationServer();
           //databaseAccessClass.Disconnect();
           //setButtonsToDisconnectedState();
        }
        catch( EOFException e )
        {
            display( "Client terminated connection. EOFException: " + e.getMessage() );
        }
        catch( IOException e )
        {
            display( "Could not connect to server. IOException: " + e.getMessage() );
        }
        catch( Exception e )
        {
            display( "Client terminated connection. Exception: " + e.getMessage() );
        }
        nadeProjectGUIInterface.setTitle( OrignalTitle );
        setButtonsToDisconnectedState();
        bBiddingInProgress = false;
        display( "Nade Client Halted." );
    }

    /**
     * Returns the INIFile class.
     */
    public INIFile getINIFile()
    {
        return nadeProjectGUIInterface.iniFile;
    }
    /**
     * Starts the NADE client.
     *@param NadeProjectGUIInterface The client GUI.
     */
    public synchronized void runClient( NadeProjectGUIInterface _nadeProjectGUIInterface )
    {
        nadeProjectGUIInterface = _nadeProjectGUIInterface;
        if( bLoop == false )
        {
           this.start();
        }
    }
    /**
     * Stops the main loop
     * and disconnects from the NADE server.
     */
    public synchronized void stopClient()
    {
        bLoop = false;
        disconnect();
        setButtonsToDisconnectedState();
    }

    /**
     * Parses the message sent by the server and takes the required action.
     *@param serverMessage The message sent by the NADE server.
     */
    private synchronized void HandleMessage( String serverMessage )
    {
       // prevent someone from sending HTLM to me
       serverMessage = serverMessage.replaceAll( "<", "&lt;" );
       serverMessage = serverMessage.replaceAll( ">", "&gt;" );
       
       messageParser.parseMessage( serverMessage );

       GroupStatus groupStatus = GetGroup( messageParser.getSender() );
       if( groupStatus != null )
       {
          if( groupStatus.getBlocked() )
          {
             SendChat( messageParser.getSender(), "You have been blocked." );
             return;
          }
       }

       AddTestMessage( serverMessage );
       int action = messageParser.getActionNum();

       // Untrusted messages
       if( !messageParser.getSender().equals( NDConstants.SERVER_ID ) &&
           !messageParser.getSender().equals( NDConstants.CONTROL_NAME ))
       {
          switch( action )
          {
             case PROTOCOL_MESSAGE:
             {
                 GroupStatus gs = GetGroup( messageParser.getSender() );
                 if( gs != null )
                 {
                     if( gs.getBlocked() )
                     {
                        SendChat( messageParser.getSender(), "You have been blocked." );
                        break;
                     }
                     else
                     {
                        display( "<font color='green'>" + messageParser.getSender() + ": " + messageParser.getValue1() + "</font>" );
                        gs.AddConversationMessage( "<font color='green'>" + messageParser.getSender() + ": " + messageParser.getValue1() + "</font>" );
                        chatDialog.UpdateMessages();
                        chatDialog.getjComboBoxGroup().setSelectedItem( messageParser.getSender() );
                     }
                 }
                 else
                 {
                    display( "<font color='green'>" + messageParser.getSender() + ": " + messageParser.getValue1() + "</font>" );
                    chatDialog.AddConversationMessage( "<font color='green'>" + messageParser.getSender() + ": " + messageParser.getValue1() + "</font>" );
                    chatDialog.UpdateMessages();
                    //chatDialog.getjComboBoxGroup().setSelectedItem( messageParser.getSender() );
                 }
             } break;
             case NDConstants.PROTOCOL_INFO_ADDRESS_REPLY:
             {
                //display( "Info address reply: " + messageParser.getSender() + " @ " + messageParser.getValue1() );
                GroupStatus gs = GetGroup( messageParser.getSender() );
                if( gs != null )
                {
                   gs.setInfoServerIPAddress( messageParser.getValue1() );
                   informationServerGUI.getjTextFieldInfoServerIP().setText( gs.getInfoServerIPAddress() );
                }
                UpdateGroups();
             } break;
             case NDConstants.PROTOCOL_INFO_ADDRESS_REQUEST:
             {
                //display( "PROTOCOL_INFO_ADDRESS_REQUEST" );
                SendInfoAddressReply( "", ourGroupStatus.getIPAddress() );
             } break;
             default:
             {
                //String message = messageParser.createMessage( action, ourGroupStatus.getName(), messageParser.getSender(), messageParser.getValue() );
                //sendData( message );
                //display( "Unhandled Message from: " + messageParser.getSender() + " : #" + action );
             }
          }
       }
       // Trusted messages from NADE
       else
       {

       switch( action )
       {
          case PROTOCOL_CONNECTION_SUCCESSFUL:
          {
             // I just put this here for security
             // not sure if its usefull yet
             if( !messageParser.sender.equals( NDConstants.SERVER_ID ) )
             {
                 display( "Invalid login message reply" );
                 break;
             }
	     // Logging In
             display( "Connection Successful" );

             ClientID = messageParser.getValue1();

             databaseAccessClass.setName( nadeProjectGUIInterface.getjTextFieldPublicName().getText() );
             // 1-tuple message
             // Create client response message
             String aTextMsg2 = 
                    messageParser.createMessage( PROTOCOL_PUBLIC_NAME, 
                                                 ClientID, 
                                                 SERVER_ID, 
                                                 nadeProjectGUIInterface.getjTextFieldPublicName().getText(), // //databaseAccessClass.getName(), 
                                                 nadeProjectGUIInterface.getjFormattedTextFieldLocalIPAddress().getText() );
	     sendData( aTextMsg2 );
          } break;
          case PROTOCOL_CLIENT_LOGIN:
          {
             //display( "PROTOCOL_CLIENT_LOGIN " + messageParser.getValue1() + " @ " + messageParser.getValue2() + " has logged in." );
             if( AlternatePublicName.length() > 0 )
             {
                 if( !messageParser.getValue1().matches( AlternatePublicName ) )
                 {
                    display( "<font color='purple'>" + messageParser.getValue1() + " has logged in." + "</font>" );
                    AddGroup( messageParser.getValue1(), messageParser.getValue2() );
                 }
             }
             else if( !messageParser.getValue1().matches( nadeProjectGUIInterface.getjTextFieldPublicName().getText() ) )
             {
                 //display( messageParser.getValue1() + " has logged in.");
                 display( "<font color='purple'>" + messageParser.getValue1() + " has logged in." + "</font>" );
                 AddGroup( messageParser.getValue1(), messageParser.getValue2() );
             }
             UpdateGroups();
          } break;
          case PROTOCOL_PUBLIC_NAME_ADDED:
          {
              if( AlternatePublicName.length() > 0 )
              {
                  nadeProjectGUIInterface.getjTextFieldPublicName().setText( AlternatePublicName );
                  nadeProjectGUIInterface.setTitle( OrignalTitle + " - " + AlternatePublicName );
                  ourGroupStatus.setName( AlternatePublicName );
              }
              else
              {
                  nadeProjectGUIInterface.setTitle( OrignalTitle + " - " + nadeProjectGUIInterface.getjTextFieldPublicName().getText() );
                  ourGroupStatus.setName( nadeProjectGUIInterface.getjTextFieldPublicName().getText() );
              }
              //Database not guranteed to be created at this poing
              // infact it probally isnt. Dont you love threads? =/
              //databaseAccessClass.setGroupNumber( 7 );
              UpdateGroups();
          } break;
          case PROTOCOL_LOGGED_IN:
          {
             //display( "PROTOCOL_LOGGED_IN " + messageParser.getValue1() + " @ " + messageParser.getValue2() + " has logged in." );
             if( !messageParser.getValue1().matches( nadeProjectGUIInterface.getjTextFieldPublicName().getText() ) )
             {
                 //display( messageParser.getValue1() + " has logged in.");
                 display( "<font color='purple'>" + messageParser.getValue1() + " has logged in." + "</font>" );
                 AddGroup( messageParser.getValue1(), messageParser.getValue2() );
             }
             UpdateGroups();

          } break;
          case PROTOCOL_SERVER_TERMINATE:
          {
              //display( "PROTOCOL_SERVER_TERMINATE: Connection can now be closed." );
          }break;            						
          case PROTOCOL_CLIENT_LOGOUT:
          {
              //display( messageParser.getValue1() + " has logged out.");
              display( "<font color='purple'>" + messageParser.getValue1() + " has logged out." + "</font>" );
              RemoveGroup( messageParser.getValue1() );
          } break;
          case PROTOCOL_BEGIN_GAME:
          {
             display( "<font color='green'>" + "Game Started" + "</font>" );
             nadeProjectGUIInterface.getjButtonTradeInfo().setEnabled( true );
             nadeProjectGUIInterface.getjButtonHealth().setEnabled( true );
             nadeProjectGUIInterface.getjButtonBribe().setEnabled( true );
             nadeProjectGUIInterface.getjButtonRumour().setEnabled( true );
             nadeProjectGUIInterface.getjCheckBoxAutoPilot().setEnabled( true );
             rumourDialog.getjButtonSendRomour().setEnabled( true );
             ourGroupStatus.resetToNewGame();
             autoBidder.contractBids.reset();
             ResetOtherGroupStatusToNewGame();
             //SellResources( 50 );
             //MakeBid( 50 );
             UpdateGroups();
          } break;		   
          case PROTOCOL_END_GAME:
          {
              //nadeProjectGUIInterface.getjButtonTradeInfo().setEnabled( false );
              //nadeProjectGUIInterface.getjButtonMakeBid().setEnabled( false );
              //nadeProjectGUIInterface.getjButtonHealth().setEnabled( false );
              //nadeProjectGUIInterface.getjButtonBribe().setEnabled( false );
          } break;
          case PROTOCOL_MAKE_BID:
          {
              //nadeProjectGUIInterface.getjButtonTradeInfo().setEnabled( false );
              nadeProjectGUIInterface.getjButtonMakeBid().setEnabled( true );
              //nadeProjectGUIInterface.getjButtonRumour().setEnabled( false );
              rumourDialog.getjButtonSendRomour().setEnabled( false );
              display( "Waiting for bid..." );
              if( nadeProjectGUIInterface.getjCheckBoxAutoPilot().isSelected() )
              {
                 autoBidder.MakeBid();
              }
              else
              {
                  nadeProjectGUIInterface.SelectAdditionalBidAmount();
              }
          } break;
          case PROTOCOL_USE_CONTRACT:
          {
              bBiddingInProgress = true;

              tradingDialog.getjButtonBuyResources().setEnabled( true );
              tradingDialog.getjButtonSellResources().setEnabled( true );

              //nadeProjectGUIInterface.getjButtonBribe().setEnabled( false );
              nadeProjectGUIInterface.getjButtonBankingInfo().setEnabled( true );
              nadeProjectGUIInterface.getjButtonShowContractInfo().setEnabled( true );

              contract = new Contract();
              contract.setName(                messageParser.getName()                );
              contract.setMaxTime(             messageParser.getMaxTime()             );
              contract.setMinResources(        messageParser.getMinResources()        );
              contract.setMaxProfit(           messageParser.getMaxProfit()           );
              contract.setCostPerUnit(         messageParser.getCostPerUnit()         );
              contract.setCostPerHealthUnit(   messageParser.getCostPerHealthUnit()   );
              contract.setBribeResourceAmount( messageParser.getBribeResourceAmount() );
              contract.setEthicsRate(          messageParser.getEthicsRate()          );
              contract.setResourceUnits(       messageParser.getResourceUnits()       );
              contract.setProfitUnits(         messageParser.getProfitUnits()         );

              contractInfoDialog.getjTextFieldContractName().setText(                String.valueOf( contract.getName()                ) );
              contractInfoDialog.getjTextFieldContractMaximumTime().setText(         String.valueOf( contract.getMaxTime()             ) );
              contractInfoDialog.getjTextFieldContractMinimumResourses().setText(    String.valueOf( contract.getMinResources()        ) );
              contractInfoDialog.getjTextFieldContractMaximumProfit().setText(       String.valueOf( contract.getMaxProfit()           ) );
              contractInfoDialog.getjTextFieldContractCostPerUnit().setText(         String.valueOf( contract.getCostPerUnit()         ) );              
              contractInfoDialog.getjTextFieldContractCostperHealthUnit().setText(   String.valueOf( contract.getCostPerHealthUnit()   ) );              
              contractInfoDialog.getjTextFieldContractBribeResourceAmount().setText( String.valueOf( contract.getBribeResourceAmount() ) );
              contractInfoDialog.getjTextFieldContractEthicsRate().setText(          String.valueOf( contract.getEthicsRate()          ) );
              contractInfoDialog.getjTextFieldContractResourceUnits().setText(       String.valueOf( contract.getResourceUnits()       ) );
              contractInfoDialog.getjTextFieldContractProfitUnits().setText(         String.valueOf( contract.getProfitUnits()         ) );
              contractInfoDialog.getjTextFieldContractNumber().setText(              String.valueOf( contract.getNumber()              ) );
              contractInfoDialog.SetContractValue();
              contractInfoDialog.SetTradeProfit();

              nadeProjectGUIInterface.getjTextFieldBidAmount().setText(     String.valueOf( contract.getMinResources() )      );
              nadeProjectGUIInterface.getjTextFieldRestoreHealth().setText( String.valueOf( contract.getCostPerHealthUnit() ) );

              bribeDialog.getjTextFieldBribeAmount().setText( String.valueOf( contract.getBribeResourceAmount() ) );

              display( "" );
              display(             "<h2><u>" + messageParser.getName() + "" + 
                                              " $" + 
                                  "<font >" + String.valueOf( contract.getMaxProfit() ) + "</font>" + 
                                              " with minimum bid of " + 
                                              String.valueOf( contract.getMinResources() ) + 
                                              " resources. - Value " + 
                                  "<font >" + String.valueOf( contract.getContractValue() ) + "</font></u></h2>" );

              securityBreachDialog.SendFakeContractAll();
              try
              {
                 int maximum = ourGroupStatus.getResources() - contract.getMinResources();
                 if( maximum < 0  )
                 {
                    maximum = 0;
                 }
                 nadeProjectGUIInterface.getjSliderAdditionalBidAmount().setMaximum( maximum );
              }
              catch( Exception e )
              {
              }
              UpdateGroups();
          } break;
          case PROTOCOL_CONTROL_SHUT_DOWN:
          {
          } break;
          case PROTOCOL_BRIBE_TYPE_ACCEPTED:
          {
              //display( "Bribe accepted." );
          } break;
          case PROTOCOL_BRIBE_AMOUNT_ACCEPTED:
          {
          } break;
          case PROTOCOL_NO_BRIBE_INFO:
          {
              display( "No Bribe Info: " + messageParser.getValue() );
          } break;
          case PROTOCOL_BRIBE_INFO:
          {
             display( "Bribe Info: " + messageParser.getValue() );
             switch( bribeDialog.bribeInfoRecipient )
             {
                case none:
                {
                } break;
                case contract:
                {
                } break;
                case jTextFieldBribeControlResult:
                {
                    bribeDialog.getjTextFieldBribeControlResult().setText( messageParser.getValue() );
                } break;
                case jTextFieldBribeLastContacted:
                {
                    bribeDialog.getjTextFieldBribeLastContacted().setText( messageParser.getValue() );
                } break;
                case jTextFieldBribeWasGroupContacted:
                {
                    bribeDialog.getjTextFieldBribeWasGroupContacted().setText( messageParser.getValue() );
                } break;
             }
          } break;
          case PROTOCOL_NEGOTIATE_ACCEPT:
          {
              display( "PROTOCOL_NEGOTIATE_ACCEPT" );
          } break;
          case PROTOCOL_NEGOTIATE_REJECT:
          {
              display( "PROTOCOL_NEGOTIATE_REJECT" );
          } break;
          case PROTOCOL_NEGOTIATE_REQUEST_RESOURCES:
          {
             display( messageParser.getValue2() + " requested: " + messageParser.getValue1() + " resources." );
             SendNegotiationRejectMessage();
          } break;
          case PROTOCOL_NEGOTIATE_REQUEST_PROFIT:
          {
             display( messageParser.getValue2() + " requested: " + messageParser.getValue1() + " profit." );
             SendNegotiationRejectMessage();
          } break;
          case PROTOCOL_RESOURCES_GIVEN:
          {
              display( "Gave " + messageParser.getValue() + " resources." );
          } break;
          case PROTOCOL_PROFIT_GIVEN:
          {
              display( "Gave " + messageParser.getValue() + " money." );
          } break;
          case PROTOCOL_RESOURCES_RECEIVED:
          {
              //display( "We randomly won: " + messageParser.getValue1() );
              try
              {
                 display( "Recieved : "  + messageParser.getValue2() + " resources from " + messageParser.getValue1() );
                 double res = Double.valueOf( messageParser.getValue2() );
                 if( res > 0 )
                 {
                    ourGroupStatus.setResources( ourGroupStatus.getResources() + (int)res );
                 }
                 else
                 {
                    GiveResources( messageParser.getValue1(), (int)res );
                 }
              }
              catch( Exception e )
              {
                  displayerror( "Exception: " + e.getMessage() );
              }
              UpdateGroups();
          } break;
          case PROTOCOL_PROFIT_RECEIVED:
          {
              try
              {
                 display( "Recieved : "  + messageParser.getValue2() + " money from " + messageParser.getValue1() );
                 double profit = Double.valueOf( messageParser.getValue2() );
                 if( profit > 0 )
                 {
                    ourGroupStatus.setMoney( ourGroupStatus.getMoney() + (int)profit );
                 }
                 else
                 {
                    GiveMoney( messageParser.getValue1(), (int)profit );
                 }
              }
              catch( Exception e )
              {
                  displayerror( "Exception: " + e.getMessage() );
              }
              UpdateGroups();
          } break;
          case PROTOCOL_TOTAL_RESOURCES:
          {
              //display( "Total Resources: " + messageParser.getValue1() );
              int res = Integer.valueOf( messageParser.getValue1() );
              ourGroupStatus.setResources( (int)res );
              UpdateGroups();
          } break;
          case PROTOCOL_GAME_LENGTH:
          {
              //display( "Game Length: " + messageParser.getValue1() );
              double len = Double.valueOf( messageParser.getValue1() );
              gameLength = (int)len;
              nadeProjectGUIInterface.getjTextFieldGameLength().setText( String.valueOf( gameLength ) );
          }break;
          case PROTOCOL_RESOURCE_POOL_SIZE:
          {
              //display( "Resource Pool Size: " + messageParser.getValue1() );
              double rpool = Double.valueOf( messageParser.getValue1() );;
              resourcePool = (int)rpool;
              nadeProjectGUIInterface.getjTextFieldControlResources().setText( String.valueOf( resourcePool ) );
          } break;
          case PROTOCOL_MONEY_POOL_SIZE:
          {
              //display( "Money Pool Size: " + messageParser.getValue1() );
              double mpool = Double.valueOf( messageParser.getValue1() );;
              moneyPool    = (int)mpool;
              nadeProjectGUIInterface.getjTextFieldControlMoney().setText( String.valueOf( moneyPool ) );
          } break;
          case PROTOCOL_BIDDING_CLOSED:
          {
              //display( "Bidding Closed" );
              tradingDialog.getjButtonBuyResources().setEnabled( false );
              tradingDialog.getjButtonSellResources().setEnabled( false );
              nadeProjectGUIInterface.getjButtonBribe().setEnabled( true );

              nadeProjectGUIInterface.getjButtonTradeInfo().setEnabled( true );
              nadeProjectGUIInterface.getjButtonRumour().setEnabled( true );
              rumourDialog.getjButtonSendRomour().setEnabled( true );
              //nadeProjectGUIInterface.getjButtonMakeBid().setEnabled( false );
              //nadeProjectGUIInterface.getjButtonBankingInfo().setEnabled( false );
          } break;
          case PROTOCOL_CONTRACT_WINNER:
          {
              display( "<font color='blue'>" + "Contract worth: $" + messageParser.getValue2() + " won by " + messageParser.getValue1() + "</font>" );
              double money = Double.valueOf( messageParser.getValue2() );
              if( messageParser.getValue1().equals( databaseAccessClass.getName() ) )
              {
                  ourGroupStatus.setMoney( ourGroupStatus.getMoney() + (int)money );
                  nadeProjectGUIInterface.getjTextFieldOurMoney().setText( String.valueOf( ourGroupStatus.getMoney() ) );
                  int contracts = databaseAccessClass.getContractsWon();
                  contracts += 1;
                  databaseAccessClass.setContractsWon( contracts );
                  ourGroupStatus.setContractsWon( contracts );
                  autoBidder.IncreaseContractTimeOnWin();
              }
              else
              {
                 autoBidder.ReduceContractTimeOnLoss();
              }

              contract.setWinner( messageParser.getValue1() );
              autoBidder.contractBids.AddContract( contract );
              bBiddingInProgress = false;

              GroupStatus gs = GetGroup( messageParser.getValue1() );
              if( gs != null )
              {
                 gs.setMoney( gs.getMoney() + (int)money );
                 gs.setContractsWon( gs.getContractsWon() + 1 );
              }
              UpdateGroups();
          } break;
          case PROTOCOL_TRUSTWORTHY_LEVEL:
          {
              double trustlevel = Double.valueOf( messageParser.getValue2() );
              if( messageParser.getValue1().equals( databaseAccessClass.getName() ) )
              {
                 ourGroupStatus.setTrustWorthyLevel( (int)trustlevel );
              }
              else
              {
                 GroupStatus gs = GetGroup( messageParser.getValue1() );
                 if( gs != null )
                 {
                    gs.setTrustWorthyLevel( (int)trustlevel );
                 }
                 //display( messageParser.getValue1() + " trustworthy level: " + messageParser.getValue2() );
              }
              UpdateGroups();
          } break;
          case PROTOCOL_HEALTH_AMOUNT_ACCEPTED:
          {
              display( "Health restored." );
          } break;
          case PROTOCOL_OVERALL_WINNER:
          {
              display( "<h3>" + "Overall winner :" + messageParser.getValue() + "</h3>" );
              databaseAccessClass.setContractsWon( 0 );
              autoBidder.contractBids.reset();
              bBiddingInProgress = false;

              //nadeProjectGUIInterface.getjButtonRumour().setEnabled( false );
              //nadeProjectGUIInterface.getjButtonHealth().setEnabled( false );
          } break;
          case PROTOCOL_RESOURCES_OBTAINED:
          {
             //display( "Obtained: " + messageParser.getValue1() + " resources from the control." );
             double r = Double.valueOf( messageParser.getValue1() );
             int res = ourGroupStatus.getResources();

             // m - ourGroupStatus.getMoney() = resources obtained
             // which can be either positive or negative
             autoBidder.contractBids.UpdateAverageResourceCost( res, contract.getCostPerUnit() );

             ourGroupStatus.setResources( res + (int)r );
             UpdateGroups();
          } break;
          case PROTOCOL_RESOURCES_SOLD:
          {
              //display( "Control was able to buy : "  + messageParser.getValue1() + " resources." );
              int resources = ourGroupStatus.getResources();
              double r = Double.valueOf( messageParser.getValue1() );
              resources -= (int)r;
              ourGroupStatus.setResources( resources );
              UpdateGroups();
          } break;
          case PROTOCOL_PROFIT_ACCOUNT:
          {
              double money = Double.valueOf( messageParser.getValue1() );
              ourGroupStatus.setMoney( (int)money );
              nadeProjectGUIInterface.getjTextFieldOurMoney().setText( messageParser.getValue() );
              UpdateGroups();
          } break;
          case PROTOCOL_RESOURCES_ACCOUNT:
          {
              double res = Double.valueOf( messageParser.getValue1() );
              ourGroupStatus.setResources( (int)res );
              nadeProjectGUIInterface.getjTextFieldOurResources().setText( messageParser.getValue() );
              try
              {
                 int maximum = ourGroupStatus.getResources() - contract.getMinResources();
                 if( maximum < 0  )
                 {
                    maximum = 0;
                 }
                 nadeProjectGUIInterface.getjSliderAdditionalBidAmount().setMaximum( maximum );
              }
              catch( Exception e )
              {
              }
              UpdateGroups();
          } break;
          case PROTOCOL_ZONE_TRANSFER_SUCCESS:
          {
              int zone = Integer.valueOf( messageParser.getValue1() );
              switch( zone )
              {
                  case 1:
                  {
                      //display( "Contract zone selected." );
                  } break;
                  case 2:
                  {
                      //display( "Banking zone selected." );
                  } break;
                  case 3:
                  {
                      //display( "Health zone selected." );
                  } break;
                  case 4:
                  {
                      //display( "Trade zone selected." );
                  } break;
                  case 5:
                  {
                      //display( "Sleazy zone selected." );
                  } break;
              }
          } break;
          case PROTOCOL_PROFIT_CHANGE:
          {
              //display( messageParser.getValue1() + " randomly won: " + messageParser.getValue2() );
              //display( messageParser.getValue1() + " profit value is now: " + messageParser.getValue2() );
              if( messageParser.getValue1().equals( databaseAccessClass.getName() ) )
              {
                  double money = Double.valueOf( messageParser.getValue2() );
                  int m = (int)money;
                  ourGroupStatus.setMoney( m );
              }
              else
              {
                 GroupStatus gs = GetGroup( messageParser.getValue1() );
                 if( gs != null )
                 {
                     double money = Double.valueOf( messageParser.getValue2() );
                     int m = (int)money;
                     gs.setMoney( (int)money );
                 }
              }
              UpdateGroups();
          } break;
          case PROTOCOL_RUMOUR:
          {
              display( "Rumour : " + messageParser.getValue1() );
          } break;
          case PROTOCOL_ERROR_MESSAGE:
          {
              display( "Error :" + messageParser.getValue1() );
          }break;
          case NDConstants.PROTOCOL_UNKNOWN_OBJECT: // is this an error?
          {
             display( "PROTOCOL_UNKNOWN_OBJECT" );
          } break;

          // Errors
          case NDConstants.PROTOCOL_ERROR_BIDDING_IN_PROGRESS:
          {
              display( "Error: BIDDING_IN_PROGRESS" );
          } break;
          case NDConstants.PROTOCOL_ERROR_BIDDING_NOT_STARTED:
          {
              display( "Error: BIDDING_NOT_STARTED" );
          } break;
          case NDConstants.PROTOCOL_ERROR_INVALID_PROTOCOL_BRIBE_FORMAT:
          {
              display( "Error: INVALID_PROTOCOL_BRIBE_FORMAT." );
          } break;
          case NDConstants.PROTOCOL_ERROR_INVALID_PROTOCOL_GRID_FORMAT:
          {
              display( "Error: INVALID_PROTOCOL_GRID_FORMAT." );
          } break;
          case NDConstants.PROTOCOL_ERROR_INVALID_RECEIVER_ID:
          {
              display( "Error: INVALID_RECEIVER_ID." );
          } break;
          case NDConstants.PROTOCOL_ERROR_INVALID_SENDER_ID:
          {
              display( "Error: INVALID_SENDER_ID." );
          } break;
          case NDConstants.PROTOCOL_ERROR_MISSING_BRIBE_AMOUNT:
          {
              display( "Error: MISSING_BRIBE_AMOUNT." );
          } break;
          case NDConstants.PROTOCOL_ERROR_NO_CONTRACT_AVAILABLE:
          {
              display( "Error: No Contract Avaliable." );
          } break;
          case PROTOCOL_ERROR_RUMOUR_WAS_SENT:
          {
              nadeProjectGUIInterface.getjButtonRumour().setEnabled( false );
              rumourDialog.getjButtonSendRomour().setEnabled( false );
              display( "Rumour already sent for this contract." );
          } break;
          case PROTOCOL_ERROR_NO_RESOURCES_FOR_HEALTH:
          {
              display( "Not enough resources to restore health." );
          } break;
          case PROTOCOL_ERROR_INCORRECT_ZONE:
          {
              display( "PROTOCOL_ERROR_INCORRECT_ZONE" );
          } break;
          case PROTOCOL_ERROR_INVALID_PROTOCOL_FORMAT:
          {
              //display( "PROTOCOL_ERROR_INVALID_PROTOCOL_FORMAT" );
          } break;
          case NDConstants.PROTOCOL_ERROR_NO_RESOURCES_FOR_BRIBE:
          {
              display( "Error not enough resources for bribe" );
          } break;
          case PROTOCOL_ERROR_PUBLIC_NAME_EXISTS:
          {
              ClientInstanceNumber += 1;
              String publicName = nadeProjectGUIInterface.getjTextFieldPublicName().getText() + String.valueOf( ClientInstanceNumber );
              display( "Public Name already exists. Retrying with: " + publicName );
              AlternatePublicName = publicName;
              //String publicName = databaseAccessClass.getName() + String.valueOf( ClientInstanceNumber );
              //databaseAccessClass.setName( publicName );
              //nadeProjectGUIInterface.setTitle( OrignalTitle + " - " + databaseAccessClass.getName() );
              String aTextMsg2 = 
              messageParser.createMessage( PROTOCOL_PUBLIC_NAME, 
                                           ClientID, 
                                           SERVER_ID, 
                                           publicName, 
                                           nadeProjectGUIInterface.getjFormattedTextFieldLocalIPAddress().getText() );
              sendData( aTextMsg2 );
          } break;
          default:
          { 
             display( "Unhandled Message: #" + action );
          }
          break;
        }
       }
    }

    void setButtonsToDisconnectedState()
    {
        nadeProjectGUIInterface.getjButtonDisconnect().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonMessageParserTest().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonDatabaseTest().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonTradeInfo().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonBankingInfo().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonShowContractInfo().setEnabled( false );
        nadeProjectGUIInterface.getchoiceGroups().setEnabled( false );
        nadeProjectGUIInterface.getjButtonInformationServer().setEnabled( false );
        nadeProjectGUIInterface.getjButtonChat().setEnabled( false );
        //nadeProjectGUIInterface.getjCheckBoxAutoPilot().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonSecurity().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonRumour().setEnabled( false );
        nadeProjectGUIInterface.getjCheckBoxInformationServerRunning().setSelected( false );
        nadeProjectGUIInterface.getjButtonAutoPilotOptions().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonBribe().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonStandings().setEnabled( false );
        chatDialog.getjComboBoxGroup().setEnabled( false );
        bankingDialog.getjComboBoxGroupToAsk().setEnabled( false );
        bankingDialog.getjComboBoxRecipient().setEnabled( false );
        nadeProjectGUIInterface.getjCheckBoxBlock().setEnabled( false );
        tradingDialog.getjButtonBuyResources().setEnabled( false );
        tradingDialog.getjButtonSellResources().setEnabled( false );

        nadeProjectGUIInterface.getchoiceGroups().removeAll();

        chatDialog.getjComboBoxGroup().removeAllItems();
        bankingDialog.getjComboBoxRecipient().removeAllItems();
        bankingDialog.getjComboBoxGroupToAsk().removeAllItems();
        informationServerGUI.getjComboBoxGroup().removeAllItems();
        securityBreachDialog.getjComboBoxGroup().removeAllItems();
        securityBreachDialog.getjComboBoxGroup().addItem( "All" );


        nadeProjectGUIInterface.getjButtonConnect().setEnabled( true );
        nadeProjectGUIInterface.getjTextFieldPublicName().setEditable( true );
        nadeProjectGUIInterface.getjFormattedTextFieldPort().setEditable( true );
        nadeProjectGUIInterface.getjFormattedTextFieldIPAddress().setEditable( true );
    }

    void setButtonsToConnectedState()
    {
        nadeProjectGUIInterface.getjButtonConnect().setEnabled( false );
        nadeProjectGUIInterface.getjButtonStandings().setEnabled( true );
        nadeProjectGUIInterface.getjButtonAutoPilotOptions().setEnabled( true );
        nadeProjectGUIInterface.getjButtonDisconnect().setEnabled( true );        
        nadeProjectGUIInterface.getjButtonMessageParserTest().setEnabled( true );
        nadeProjectGUIInterface.getjButtonBankingInfo().setEnabled( true );
        nadeProjectGUIInterface.getjCheckBoxAutoPilot().setEnabled( true );
        nadeProjectGUIInterface.getjButtonSecurity().setEnabled( true );
        nadeProjectGUIInterface.getjTextFieldPublicName().setEditable( false );
        nadeProjectGUIInterface.getjFormattedTextFieldPort().setEditable( false );
        nadeProjectGUIInterface.getjFormattedTextFieldIPAddress().setEditable( false );

        //nadeProjectGUIInterface.getjButtonBankingInfo().setEnabled( false );
        nadeProjectGUIInterface.getjCheckBoxBlock().setEnabled( false );
        //nadeProjectGUIInterface.getjButtonBribe().setEnabled( false );

        chatDialog.getjComboBoxGroup().setEnabled( false );
        bankingDialog.getjComboBoxGroupToAsk().setEnabled( false );
        bankingDialog.getjComboBoxRecipient().setEnabled( false );
        tradingDialog.getjButtonBuyResources().setEnabled( false );
        tradingDialog.getjButtonSellResources().setEnabled( false );

        
        nadeProjectGUIInterface.getchoiceGroups().removeAll();

        chatDialog.getjComboBoxGroup().removeAllItems();
        bankingDialog.getjComboBoxRecipient().removeAllItems();
        bankingDialog.getjComboBoxGroupToAsk().removeAllItems();
        informationServerGUI.getjComboBoxGroup().removeAllItems();

        securityBreachDialog.getjComboBoxGroup().removeAllItems();
        securityBreachDialog.getjComboBoxGroup().addItem( "All" );

        //nadeProjectGUIInterface.getjTextFieldPublicName().setText( databaseAccessClass.getName() );
        nadeProjectGUIInterface.getjTextFieldControlResources().setText( "0" );
        nadeProjectGUIInterface.getjTextFieldControlMoney().setText(     "0" );
        nadeProjectGUIInterface.getjTextFieldGameLength().setText(       "0" );
    }

    /*
     *Displays a message in the message box.
     *@param message Message to send
     */
    public void displayerror( String message )
    {
        if( nadeProjectGUIInterface != null )
        {
           try
           {
              nadeProjectGUIInterface.display( "<font color='red'><h2>" + message + "</h2></font>" );
           }
           catch( Exception e )
           {
              System.out.println( "NadeClient.display - Exception: " + e.getMessage() );
           }
        }
        else
        {
           System.out.println( message );
        }
    }

    /*
     *Displays a message in the message box.
     *@param message Message to send
     */
    public void display( String message )
    {
        if( nadeProjectGUIInterface != null )
        {
           try
           {
              nadeProjectGUIInterface.display( message );
           }
           catch( Exception e )
           {
              System.out.println( "NadeClient.display - Exception: " + e.getMessage() );
           }
        }
        else
        {
           System.out.println( message );
        }
    }

    /**
     *Starts the information server.
     */
    public void StartInformationServer()
    {
        try
        {
           if( nadeInformationServer != null )
           {
              nadeInformationServer.StopServer();
           }
           nadeInformationServer = new NadeInformationServer();
           nadeInformationServer.StartServer( this );
        }
        catch( Exception e )
        {
            display( "Exception: " + e.getMessage() );
        }
    }

    /**Stops the information server*/
    public void StopInformationServer()
    {
       nadeInformationServer.StopServer();
    }

    /*
     *Sends a message to the server.
     *@param message Message to send 
     */
    private synchronized void sendData( String message )
    {
        try
        {
            if( output != null && client != null )
            {
               if( !client.isClosed() )
               {
                  output.writeObject( message );
                  output.flush();
               }
            }
        }
        catch( IOException e )
        {
            display( "IOException: " + e.getMessage() );
        }
    }

    /**Disconnect from NADE server.*/
    private void disconnect()
    {
        if( client != null )
        {
           if( messageParser != null && !client.isClosed() )
           {
              String Msg = messageParser.createMessage( NDConstants.PROTOCOL_CLIENT_TERMINATE, ClientID, SERVER_ID, "" );
	      sendData( Msg );
              StopInformationServer();
           }
        }
    }

    /**Make a bid.*/
    public void MakeBid()
    {
        if( messageParser != null )
        {
            int bid = Integer.valueOf( nadeProjectGUIInterface.getjTextFieldBidAmount().getText() );
            bid    += Integer.valueOf( nadeProjectGUIInterface.getjTextFieldAdditionalBidAmount().getText() );
            MakeBid( bid );
        }
    }

    /**Make a bid.
     *@param bid Resource amount.
     */
    public void MakeBid( int bid )
    {
        if( messageParser != null )
        {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_CONTRACT, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );

            // Create client response message
            Msg = messageParser.createMessage( PROTOCOL_MY_BID, 
                                              ClientID, 
                                              SERVER_ID, 
                                              String.valueOf( bid ) );
            sendData( Msg );

            double completion_time = autoBidder.CalculateTime( bid );
            int resources_avainable = ourGroupStatus.getResources();
            int actual_bid = bid;
            if( resources_avainable < bid )
            {
               //actual_bid = resources_avainable;
            }
            else
            {
                //actual_bid = bid;
            }
            if( bBiddingInProgress )
            {
               contract.setOurBidAmount( actual_bid );
            }
            display( "Made a bid of: " + String.valueOf( actual_bid ) + " Completion Time: " + String.valueOf( completion_time ) );

        }
    }

    /**Ask a group for the address of their information server.
     *@param groupname the name of the group to reply to
     */
    public void SendInfoAddressReply( String groupname, String ipAddress )
    {
       if( messageParser != null )
       {
          if( groupname.equals( "" ) )
          {
             groupname = String.valueOf( informationServerGUI.getjComboBoxGroup().getSelectedItem() );
          }
          String Msg = messageParser.createMessage( PROTOCOL_INFO_ADDRESS_REPLY, 
                                                    ClientID, 
                                                    groupname,
                                                    ipAddress );
          sendData( Msg );
       }
    }

    /**Ask a group for the address of their information server.
     *@param groupname the name of the group
     */
    public void SendInfoAddressRequest( String groupname )
    {
       if( messageParser != null )
       {
          String Msg = messageParser.createMessage( PROTOCOL_INFO_ADDRESS_REQUEST, 
                                                    ClientID, 
                                                    groupname,
                                                    "" );
                                                    //ourGroupStatus.getName() );
          sendData( Msg );
       }
    }

    /**Restore health.*/
    void RestoreHealth()
    {
        if( messageParser != null )
        {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_HEALTH, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );

            // Create client response message
            Msg = messageParser.createMessage( PROTOCOL_HEALTH_TRANSFER, 
                                               ClientID, 
                                               SERVER_ID, 
                                               Integer.valueOf( nadeProjectGUIInterface.getjTextFieldRestoreHealth().getText() ).toString() );
            sendData( Msg );
        }
    }

    /**Send a message. Used with the security dialog.
     *@param zone Zone 0 dont change, 1 contract, 2 banking, 3 health, 4 trade, 5 sleazy.
     *@param actionNum Action number.
     *@param receiver Receiver
     *@param value1 Value1
     */
    public void SendMessage( int zone, int actionNum, String receiver, String value1 )
    {
        if( messageParser != null )
        {
            int ZONE = PROTOCOL_ZONE_TRANSFER_SLEAZY;
            switch( zone )
            {
                case 1:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_CONTRACT;
                } break;
                case 2:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_BANKING;
                } break;
                case 3:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_HEALTH;
                } break;
                case 4:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_TRADE;
                } break;
                case 5:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_SLEAZY;
                } break;
                default:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_CONTRACT;
                }
            }

            String Msg = "";
            if( zone != 0 )
            {
               Msg = messageParser.createMessage( ZONE, 
                                                  ClientID, 
                                                  receiver, 
                                                  "" );
            }
            sendData( Msg );
            Msg = messageParser.createMessage( actionNum, 
                                               ourGroupStatus.getName(),
                                               receiver, 
                                               value1 );
            sendData( Msg );
        }
    }


    /**Send a message. Used with the security dialog.
     *@param zone Zone 0 dont change, 1 contract, 2 banking, 3 health, 4 trade, 5 sleazy.
     *@param actionNum Action number.
     *@param receiver Receiver
     *@param value1 Value1
     *@param value2 Value2
     */
    public void SendMessage( int zone, int actionNum, String receiver, String value1, String value2 )
    {
        if( messageParser != null )
        {
            int ZONE = PROTOCOL_ZONE_TRANSFER_SLEAZY;
            switch( zone )
            {
                case 1:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_CONTRACT;
                } break;
                case 2:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_BANKING;
                } break;
                case 3:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_HEALTH;
                } break;
                case 4:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_TRADE;
                } break;
                case 5:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_SLEAZY;
                } break;
                default:
                {
                   ZONE = PROTOCOL_ZONE_TRANSFER_CONTRACT;
                }
            }

            String Msg = "";
            if( zone != 0 )
            {
               Msg = messageParser.createMessage( ZONE, 
                                                  ClientID, 
                                                  receiver, 
                                                  "" );
            }
            sendData( Msg );
            Msg = messageParser.createMessage( actionNum, 
                                               ourGroupStatus.getName(), 
                                               receiver, 
                                               value1, 
                                               value2 );
            sendData( Msg );
        }
    }

    /**Send a rumour.*/
    public void SendRumour()
    {
        if( messageParser != null )
        {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_SLEAZY, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );

            // Create client response message
            Msg = messageParser.createMessage( PROTOCOL_SEND_RUMOUR, 
                                               ClientID, 
                                               SERVER_ID, 
                                               rumourDialog.getjTextAreaRumour().getText() );
            sendData( Msg );
        }
    }

    /**
     * Amount of resources to give specified by
     * bankingDialog.getjTextFieldResourcesToGive().getText()
     * Not implemented as yet.
     */
    public void GiveResources()
    {
        if( messageParser != null )
        {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_BANKING, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            String groupname = String.valueOf( bankingDialog.getjComboBoxRecipient().getSelectedItem() );
            double amount    = Double.valueOf( bankingDialog.getjTextFieldResourcesToGive().getText()  );
            Msg = messageParser.createMessage( PROTOCOL_GIVE_RESOURCES, 
                                               ClientID, 
                                               SERVER_ID, 
                                               groupname,
                                               String.valueOf( (int)amount ) );
            sendData( Msg );
        }
    }

    /**
     * Amount of resources to give specified by
     * @param groupname
     *@param amount
     */
    public void GiveResources( String groupname, int amount )
    {
        if( messageParser != null )
        {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_BANKING, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_GIVE_RESOURCES, 
                                               ClientID, 
                                               SERVER_ID, 
                                               groupname,
                                               String.valueOf( (int)amount ) );
            sendData( Msg );
        }
    }

    /**
     * Give resource after negotiating.
     *@param amount Amount of resources to give.
     */
    public void GiveNegotiateResources( int amount )
    {
        if( messageParser != null )
        {
            String Msg ="";
            String groupname = String.valueOf( bankingDialog.getjComboBoxGroupToAsk().getSelectedItem() );
            Msg = messageParser.createMessage( PROTOCOL_NEGOTIATE_GIVE_RESOURCES, 
                                               ClientID,
                                               groupname,
                                               String.valueOf( (int)amount ) );
            sendData( Msg );
        }
    }

    /**
     * Give money after negotiating.
     */
    void GiveNegotiateMoney( int amount )
    {
            String Msg = "";
            String groupname = String.valueOf( bankingDialog.getjComboBoxGroupToAsk().getSelectedItem() );
            Msg = messageParser.createMessage( PROTOCOL_NEGOTIATE_GIVE_PROFIT, 
                                               ClientID, 
                                               groupname,
                                               String.valueOf( (int)amount ) );
            sendData( Msg );
    }

    /**
     * Amount of money to give specified by
     * bankingDialog.getjTextFieldMoneyToGive().getText()
     * Not implemented as yet.
     */
    void GiveMoney()
    {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_BANKING, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            String groupname = String.valueOf( bankingDialog.getjComboBoxRecipient().getSelectedItem() );
            double    amount = Double.valueOf( bankingDialog.getjTextFieldMoneyToGive().getText()      );
            Msg = messageParser.createMessage( PROTOCOL_GIVE_PROFIT, 
                                               ClientID, 
                                               SERVER_ID, 
                                               groupname,
                                               String.valueOf( (int)amount ) );
            sendData( Msg );
    }

    /**
     * Amount of money to give specified by
     * @param groupname
     * @param Amount
     */
    void GiveMoney( String groupname, int amount )
    {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_BANKING, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_GIVE_PROFIT, 
                                               ClientID, 
                                               SERVER_ID, 
                                               groupname,
                                               String.valueOf( (int)amount ) );
            sendData( Msg );
    }

    /**
     * Amount of money to ask for specified by
     * bankingDialog.getjTextFieldMoneyToAskFor().getText()
     */
    void AskMoney()
    {
       if( client != null )
       {
          String Msg = "";
          String groupname = String.valueOf( bankingDialog.getjComboBoxGroupToAsk().getSelectedItem() );
          double    amount = Double.valueOf( bankingDialog.getjTextFieldMoneyToAskFor().getText() );
          Msg = messageParser.createMessage( PROTOCOL_NEGOTIATE_REQUEST_PROFIT, 
                                             ClientID, 
                                             groupname,
                                             String.valueOf( (int)amount ) );
          sendData( Msg );
       }
    }

    /**
     * Amount of resources to ask for specified by
     * bankingDialog.getjTextFieldResourcesToAskFor().getText()
     */
    void AskResources()
    {
       if( client != null )
       {
          String Msg = "";
          String groupname = String.valueOf( bankingDialog.getjComboBoxGroupToAsk().getSelectedItem() );
          double    amount = Double.valueOf( bankingDialog.getjTextFieldResourcesToAskFor().getText() );
          Msg = messageParser.createMessage( PROTOCOL_NEGOTIATE_REQUEST_RESOURCES, 
                                             ClientID, 
                                             groupname,
                                             String.valueOf( (int)amount ) );
          sendData( Msg );
       }
    }

    /**
     * Buys resources from the NADE Control.
     * Transfers to the trading zone and then 
     * sends the PROTOCOL_BUY_RESOURCES message
     * with the value obtained from
     * tradingDialog.getjTextFieldResourcesToBuy().getText()
     */
    void BuyResources()
    {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_TRADE, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BUY_RESOURCES, 
                                               ClientID, 
                                               SERVER_ID, 
                                               tradingDialog.getjTextFieldResourcesToBuy().getText() );
            sendData( Msg );
    }
    /**
     * Buys resources from the NADE Control.
     * Transfers to the trading zone and then 
     * sends the PROTOCOL_BUY_RESOURCES message
     * @param resources The amount of resources to buy
     */
    void BuyResources( int resources )
    {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_TRADE, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BUY_RESOURCES, 
                                               ClientID, 
                                               SERVER_ID, 
                                               String.valueOf( resources ) );
            sendData( Msg );
    }

    /**
     * Sells resources to the NADE Control.
     * Transfers to the trading zone and then 
     * sends the PROTOCOL_SELL_RESOURCES message
     * with the value obtained from
     * tradingDialog.getjTextFieldResourcesToSell().getText()
     */
    public void SellResources()
    {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_TRADE, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_SELL_RESOURCES, 
                                               ClientID, 
                                               SERVER_ID, 
                                               tradingDialog.getjTextFieldResourcesToSell().getText() );
            sendData( Msg );
    }

    /**
     * Sells resources to the NADE Control.
     * Transfers to the trading zone and then 
     * sends the PROTOCOL_SELL_RESOURCES message
     * with the value obtained from
     * @param resources Number of resources to sell.
     */
    public void SellResources( int resources )
    {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_TRADE, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_SELL_RESOURCES, 
                                               ClientID, 
                                               SERVER_ID, 
                                               String.valueOf( resources ) );
            sendData( Msg );
    }

    /**Enable auto pilot.*/
    void EnableAutoPilot()
    {
       nadeProjectGUIInterface.getjCheckBoxAutoPilot().setEnabled( true );
    }

    /**Disable auto pilot.*/
    void DisableAutoPilot()
    {
       nadeProjectGUIInterface.getjCheckBoxAutoPilot().setEnabled( false );
    }

    /** 
     * Gets the IP Address of a group
     * @param groupName
     */
    public String GetGroupIPAddress( String groupName )
    {
        String ipAddress = "";
        for( int i=0; i<groupStatusList.size() ; i++ )
        {
            if( groupStatusList.get(i).getName().equals( groupName )
              )
            {
               return groupStatusList.get(i).getIPAddress();
            }
        }
        return ipAddress;
    }

    /** 
     * Blocks messages from this group
     *@param name Name of the group to block.
     *@param block Block if true, Unblock if false.
     */
    public void BlockGroup( String name, boolean block )
    {
        GroupStatus gs = GetGroup( name );
        if( gs != null )
        {
            gs.setBlocked( block );
        }
    }

    /** 
     * Adds a group to the groupStatusList
     * Enables the nadeProjectGUIInterface.getchoiceGroups()
     */
    private void AddGroup( String publicName, String ipAddress )
    {
        GroupStatus gs = new GroupStatus();
        gs.setName( publicName );

        // This means that a client is running on the same computer as NADE
        // switch the loopback address with the address used to connect to NADE
        if( ipAddress.matches( "127.0.0.1" ) )
        {
           ipAddress = nadeProjectGUIInterface.getjFormattedTextFieldIPAddress().getText();
        }
        gs.setIPAddress( ipAddress );
        // Check to see if the group is already on the list
        for( int i=0; i<groupStatusList.size() ; i++ )
        {
            if( groupStatusList.get(i).getIPAddress().equals( gs.getIPAddress() ) && 
                groupStatusList.get(i).getName().equals( gs.getName() )
              )
            {
                return;
            }
        }
        groupStatusList.add( gs );

        nadeProjectGUIInterface.getjCheckBoxBlock().setEnabled( true );

        nadeProjectGUIInterface.getjTextFieldTheirMoney().setText(     String.valueOf( gs.getMoney())            );
        nadeProjectGUIInterface.getjTextFieldTheirResources().setText( String.valueOf( gs.getResources())        );
        nadeProjectGUIInterface.getjTextFieldTheirContracts().setText( String.valueOf( gs.getContractsWon())     );
        nadeProjectGUIInterface.getjTextFieldTheirHealth().setText(    String.valueOf( gs.getTrustWorthyLevel()) );
        nadeProjectGUIInterface.getjTextFieldTheirIPAddress().setText( String.valueOf( gs.getIPAddress())        );

        nadeProjectGUIInterface.getchoiceGroups().add( gs.getName() );        
        nadeProjectGUIInterface.getchoiceGroups().setEnabled( true );
        nadeProjectGUIInterface.getjButtonInformationServer().setEnabled( true );
        nadeProjectGUIInterface.getjButtonChat().setEnabled( true );

        chatDialog.getjComboBoxGroup().addItem( gs.getName() );
        chatDialog.getjComboBoxGroup().setEnabled( true );
        chatDialog.getjButtonSend().setEnabled( true );

        bankingDialog.getjComboBoxRecipient().addItem( gs.getName() );
        bankingDialog.getjComboBoxRecipient().setEnabled( true );
        bankingDialog.getjComboBoxGroupToAsk().addItem( gs.getName() );
        bankingDialog.getjComboBoxGroupToAsk().setEnabled( true );

        bribeDialog.getjComboBoxClient1().addItem( gs.getName() );
        bribeDialog.getjComboBoxClient1().setEnabled( true );
        bribeDialog.getjComboBoxClient2().addItem( gs.getName() );
        bribeDialog.getjComboBoxClient2().setEnabled( true );
        bribeDialog.getjComboBoxClient3().addItem( gs.getName() );
        bribeDialog.getjComboBoxClient3().setEnabled( true );
        bribeDialog.getjComboBoxClient4().addItem( gs.getName() );
        bribeDialog.getjComboBoxClient4().setEnabled( true );
        
        informationServerGUI.getjComboBoxGroup().addItem( gs.getName() );
        //informationServerGUI.getjComboBoxGroup().setEnabled( true );

        securityBreachDialog.getjComboBoxGroup().addItem( gs.getName() );
        UpdateGroups();
    }

    /** Removes a group when they disconnect.
     *@param Name of group
     */
    void RemoveGroup( String publicName )
    {
        GroupStatus gs = new GroupStatus();
        gs.setName( publicName );
        // Check to see if the group is already on the list
        for( int i=0; i<groupStatusList.size() ; i++ )
        {
            if( groupStatusList.get(i).getName().equals( gs.getName() )
              )
            {
               nadeProjectGUIInterface.getchoiceGroups().remove(    gs.getName() );
               chatDialog.getjComboBoxGroup().removeItem(           gs.getName() );
               bankingDialog.getjComboBoxRecipient().removeItem(    gs.getName() );
               bankingDialog.getjComboBoxGroupToAsk().removeItem(   gs.getName() );
               informationServerGUI.getjComboBoxGroup().removeItem( gs.getName() );
               securityBreachDialog.getjComboBoxGroup().removeItem( gs.getName() );

               bribeDialog.getjComboBoxClient1().removeItem( gs.getName() );
               bribeDialog.getjComboBoxClient2().removeItem( gs.getName() );
               bribeDialog.getjComboBoxClient3().removeItem( gs.getName() );
               bribeDialog.getjComboBoxClient4().removeItem( gs.getName() );

               groupStatusList.remove( i );
               break;
            }
        }
        UpdateGroups();
    }

    /**
     * Send chat message.
     * @param groupname Name of the group to send it to.
     * @param message Message to send.
     */
    public void SendChat( String groupname, String message )
    {
        if( messageParser != null )
        {
            String Msg = "";
            GroupStatus gs = GetGroup( groupname );
            if( gs == null )
            {
            }

            // Create client response message
            Msg = messageParser.createMessage( PROTOCOL_SEND_MESSAGE, 
                                               ClientID, 
                                               groupname, 
                                               message );
            sendData( Msg );
        }
    }

    /**Sends a chat message.*/
    public void SendChat()
    {
        if( messageParser != null )
        {
            String Msg = "";

            // Create client response message
            Msg = messageParser.createMessage( PROTOCOL_SEND_MESSAGE, 
                                               ClientID, 
                                               String.valueOf( chatDialog.getjComboBoxGroup().getSelectedItem() ), 
                                               chatDialog.getjTextAreaMessage().getText() );
            sendData( Msg );
        }
    }

    /**
     * Send chat message to all clients.
     */
    public void SendChatAll()
    {
        if( messageParser != null )
        {
            String Msg = "";

            // Create client response message
            Msg = messageParser.createMessage( PROTOCOL_SEND_MESSAGE_ALL, 
                                               ClientID, 
                                               SERVER_ID, 
                                               chatDialog.getjTextAreaMessage().getText() );
            sendData( Msg );
        }
    }

    /**Bribe was group contacted.*/
    public void BribeWasGroupContacted()
    {
       if( client != null )
       {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_SLEAZY, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BRIBE_AMOUNT, 
                                               ClientID, 
                                               SERVER_ID, 
                                               bribeDialog.getjTextFieldBribeAmount().getText() );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BRIBE_WAS_GROUP_CONTACTED, 
                                               ClientID, 
                                               SERVER_ID, 
                                               String.valueOf( bribeDialog.getjComboBoxClient2().getSelectedItem() ),
                                               String.valueOf( bribeDialog.getjComboBoxClient3().getSelectedItem() ));
            sendData( Msg );
            bribeDialog.bribeInfoRecipient = bribeDialog.bribeInfoRecipient.jTextFieldBribeWasGroupContacted;
       }
    }

    /**Bribe bribe control.*/
    public void BribeControl()
    {
       if( client != null )
       {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_SLEAZY, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BRIBE_AMOUNT, 
                                               ClientID, 
                                               SERVER_ID, 
                                               bribeDialog.getjTextFieldBribeAmount().getText() );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BRIBE_CONTROL, 
                                               ClientID, 
                                               SERVER_ID, 
                                               String.valueOf( bribeDialog.getjComboBoxClient4().getSelectedItem() )  );
            sendData( Msg );
            bribeDialog.bribeInfoRecipient = bribeDialog.bribeInfoRecipient.jTextFieldBribeControlResult;
       }
    }

    /**Bribe last contacted.*/
    public void BribeLastContacted()
    {
       if( client != null )
       {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_SLEAZY, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BRIBE_AMOUNT, 
                                               ClientID, 
                                               SERVER_ID, 
                                               bribeDialog.getjTextFieldBribeAmount().getText() );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BRIBE_LAST_CONTACTED, 
                                               ClientID, 
                                               SERVER_ID, 
                                               String.valueOf( bribeDialog.getjComboBoxClient1().getSelectedItem() ) );
            sendData( Msg );
            bribeDialog.bribeInfoRecipient = bribeDialog.bribeInfoRecipient.jTextFieldBribeLastContacted;
       }
    }

    /**Bribe next contract.*/
    public void BribeNextContract()
    {
       if( client != null )
       {
            String Msg = messageParser.createMessage( PROTOCOL_ZONE_TRANSFER_SLEAZY, 
                                                      ClientID, 
                                                      SERVER_ID, 
                                                      "" );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BRIBE_AMOUNT, 
                                               ClientID, 
                                               SERVER_ID, 
                                               bribeDialog.getjTextFieldBribeAmount().getText() );
            sendData( Msg );
            Msg = messageParser.createMessage( PROTOCOL_BRIBE_NEXT_CONTRACT, 
                                               ClientID, 
                                               SERVER_ID, 
                                               "" );
            sendData( Msg );
            bribeDialog.bribeInfoRecipient = bribeDialog.bribeInfoRecipient.contract;
       }
    }

    /**
     *@param name Name of group
     *@return GroupStatus Null is no group by that name exists the group otherwise.
     */
    public GroupStatus GetGroup( String name )
    {
        for( int i=0; i<groupStatusList.size() ; i++ )
        {
            if( groupStatusList.get(i).getName().equals( name ) )
            {
                return groupStatusList.get(i);
            }
        }
        return null;
    }

    /**
     * Updates GUI components based on each Group's Status.
     */
    public void UpdateGroups()
    {
        autoBidderDialog.getjTextFieldCostPerResourceUnit().setText( String.valueOf( contract.getCostPerUnit() ) );
        autoBidderDialog.getjTextFieldMoneyFromContract().setText( String.valueOf( contract.getMaxProfit() ) );
                
        bankingDialog.getjTextFieldResourcesAvailable().setText( String.valueOf( ourGroupStatus.getResources() ) );
        bankingDialog.getjTextFieldMoneyAvailable().setText( String.valueOf( ourGroupStatus.getMoney() ) );
        bankingDialog.getjComboBoxRecipient();

        tradingDialog.getjTextFieldCostPerResource().setText( String.valueOf( contract.getCostPerUnit() ) );
        tradingDialog.getjTextFieldNadeControlResources().setText( String.valueOf( resourcePool ) );
        tradingDialog.getjTextFieldOurResources().setText( String.valueOf( ourGroupStatus.getResources() ) );
        tradingDialog.getjTextFieldOurMoney().setText( String.valueOf( ourGroupStatus.getMoney() ) );

        nadeProjectGUIInterface.getjTextFieldOurMoney().setText( String.valueOf( ourGroupStatus.getMoney() ) );
        nadeProjectGUIInterface.getjTextFieldOurResources().setText( String.valueOf( ourGroupStatus.getResources() ) );
        nadeProjectGUIInterface.getjTextFieldOurHealth().setText( String.valueOf( ourGroupStatus.getTrustWorthyLevel() ) );
        nadeProjectGUIInterface.getjTextFieldOurContracts().setText( String.valueOf( ourGroupStatus.getContractsWon() ) );

        standingsDialog.UpdateStandings();

        String name = nadeProjectGUIInterface.getchoiceGroups().getSelectedItem();
        if( name == null )
        {
           nadeProjectGUIInterface.getjTextFieldTheirMoney().setText(     "" );
           nadeProjectGUIInterface.getjTextFieldTheirContracts().setText( "" );
           nadeProjectGUIInterface.getjTextFieldTheirHealth().setText(    "" );
           nadeProjectGUIInterface.getjTextFieldTheirResources().setText( "" );
           nadeProjectGUIInterface.getjTextFieldTheirIPAddress().setText( "" );
        }
        else
        {
           GroupStatus gs = GetGroup( name );
           if( gs != null )
           {
              nadeProjectGUIInterface.getjTextFieldTheirMoney().setText(     String.valueOf( gs.getMoney()            ) );
              nadeProjectGUIInterface.getjTextFieldTheirContracts().setText( String.valueOf( gs.getContractsWon()     ) );
              nadeProjectGUIInterface.getjTextFieldTheirHealth().setText(    String.valueOf( gs.getTrustWorthyLevel() ) );
              nadeProjectGUIInterface.getjTextFieldTheirResources().setText( String.valueOf( gs.getResources()        ) );
              nadeProjectGUIInterface.getjTextFieldTheirIPAddress().setText( String.valueOf( gs.getIPAddress()        ) );
           }
        }
    }

    /**
     * Resets money resources ect... to default values for a new game.
     */
    public void ResetOtherGroupStatusToNewGame()
    {
        for( int i=0 ; i<groupStatusList.size() ; i++ )
        {
           groupStatusList.get(i).resetToNewGame();
        }
    }


    /**
     * Helper method to check the existance of a file.
     * @param the full path and name of the file to be checked.
     * @return true if file exists, false otherwise.
     */
    private boolean checkFile( String pstrFile )
    {
        boolean blnRet  = false;
        File    objFile = null;

        try
        {
            objFile = new File( pstrFile );
            blnRet = ( objFile.exists() && objFile.isFile() );
        }
        catch( Exception e )
        {
            blnRet = false;
        }
        finally
        {
            if( objFile != null ) objFile = null;
        }
        return blnRet;
    }

    /**
     * Helper method to create a file if it dosent exist.
     * @param the full path and name of the file to be created.
     * @return true if file was created, false otherwise.
     *Not implemented as yet.
     */
    private boolean CreateFileIfItDoesNotExist( String pstrFile )
    {
        /*boolean blnRet  = false;
        File    objFile = null;

        try
        {
            objFile = new File( pstrFile );
            blnRet = ( objFile.exists() && objFile.isFile() );
        }
        catch( Exception e )
        {
            blnRet = false;
        }
        finally
        {
            if( objFile != null ) objFile = null;
        }
        return blnRet;*/
        return false;
    }

    /**Send PROTOCOL_NEGOTIATE_REJECT message*/
    public void SendNegotiationRejectMessage()
    {
       String Msg = messageParser.createMessage( PROTOCOL_NEGOTIATE_REJECT, 
                                                 ClientID, 
                                                 SERVER_ID, 
                                                 "" );
       sendData( Msg );
    }
 
    /**Send PROTOCOL_NEGOTIATE_ACCEPT message.*/
    public void SendNegotiationAcceptMessage()
    {
       String Msg = messageParser.createMessage( PROTOCOL_NEGOTIATE_REJECT, 
                                                 ClientID, 
                                                 SERVER_ID, 
                                                 "" );
       sendData( Msg );
    }
}
