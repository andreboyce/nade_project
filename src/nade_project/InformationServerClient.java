/*
 * InformationServerClient.java
 *
 * Created on March 6, 2007, 12:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;
import java.io.*;
import java.nio.channels.*;
import java.net.*;

/**
 *This class is capable of connection to information servers.
 * @author Default
 */
public class InformationServerClient extends Thread
{
    /***/
    InformationServerClientGUI informationServerGUI;
    /***/
    boolean bLoop;
    /***/
    ObjectOutputStream output;
    /***/
    ObjectInputStream input;
    /***/
    Socket client;
    /***/
    SocketChannel sChannel;
    /***/
    int port;

    /** Creates a new instance of InformationServerClient */
    public InformationServerClient()
    {
        bLoop = false;
        informationServerGUI = null;
        output = null;
        input  = null;
        client = null;
        port   = 8000;
    }

    void SetPort( int _port )
    {
        port = _port;
    }

    private void sendData( String message )
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
            display( "InformationServerClient.sendData - IOException: " + e.getMessage() );
        }
    }

    /**Disconnects from information server.*/
    void Disconnect()
    {
       if( client!= null )
       {
          try
          {
             bLoop = false;
             informationServerGUI.getjButtonAskContracts().setEnabled( false );
             informationServerGUI.getjButtonAskName().setEnabled( false );
             informationServerGUI.getjButtonAskNumber().setEnabled( false );
             informationServerGUI.getjButtonAskDisconnect().setEnabled( false );
             informationServerGUI.getjButtonDisconnect().setEnabled( false );
             informationServerGUI.getjButtonConnect().setEnabled( true );
             client.close();
          }
          catch( IOException e )
          {
             display( "Disconnect - IOException: " + e.getMessage() );
          }
          catch( Exception e )
          {
             display( "Disconnect - Exception: " + e.getMessage() );
          }
       }
    }
    /**
     * Connects to information server.
     * 
     * @paramInformationServerClientGUII GUI used to display status.
     */
    public boolean connect( InformationServerClientGUI _informationServerGUI )
    {
        informationServerGUI = _informationServerGUI;
        try
        {
           if( client != null )
           {
              if( !client.isClosed() )
              {
                 client.close();
                 //return false;
              }
           }
           output   = null;
           input    = null;
           client   = null;
           sChannel = SocketChannel.open();
           sChannel.configureBlocking( false );
           port = Integer.valueOf( informationServerGUI.getjTextFieldPort().getText() );
           String ip = "";
           ip = informationServerGUI.getjTextFieldIPAddress().getText();
           //ip = informationServerGUI.getjTextFieldInfoServerIP().getText();
           InetAddress ipAddress = InetAddress.getByName( ip );

           /*sChannel.connect( new InetSocketAddress( ipAddress, port ) );
           // Before the socket is usable, the connection must be completed
           // by calling finishConnect(), which is non-blocking
           while( !sChannel.finishConnect() )
           {
              // Do something else
           }
           client = sChannel.socket();
           output = new ObjectOutputStream( sChannel.socket().getOutputStream() );*/

           client = new Socket( ipAddress, port );
           output = new ObjectOutputStream( client.getOutputStream() );
           output.flush();
           input = new ObjectInputStream( client.getInputStream() );
           bLoop = true;
           start();
           display( "Connected to InformationServer @" + ipAddress.getHostAddress() );
           informationServerGUI.getjButtonAskContracts().setEnabled( true );
           informationServerGUI.getjButtonAskName().setEnabled( true );
           informationServerGUI.getjButtonAskNumber().setEnabled( true );
           informationServerGUI.getjButtonAskDisconnect().setEnabled( true );
           informationServerGUI.getjButtonDisconnect().setEnabled( true );
           informationServerGUI.getjButtonConnect().setEnabled( false );
           return true;
        }
        catch( Exception e )
        {
            display( "InformationServerClient.connect - Exception: " + e.getMessage() );
            return false;
        }        
    }
    public void run()
    {
        try
        {
           String serverMessage = null;
           do
           {
              try
              {
                  serverMessage = (String) input.readObject();
                  if( serverMessage.equals( "" ) )
                      break;
                  HandleMessage( serverMessage );
                  /*try
                  {
                     Thread.sleep( 100 );
                  }
                  catch( InterruptedException e )
                  {
                     display( "InterruptedException :" + e.getMessage() );
                  }*/
              }
              catch( ClassNotFoundException e )
              {
                 display( "Unknown Object Type Recieved. ClassNotFoundException: " + e.getMessage() );
              }
           } while( bLoop );
        }
        catch( IOException e )
        {
            display( "InformationServerClient.run - IOException: " + e.getMessage() );
        }
        catch( Exception e )
        {
            display( "InformationServerClient.run - Exception: " + e.getMessage() );
        }
        finally
        {
           bLoop = false;
           informationServerGUI.getjButtonAskContracts().setEnabled( false );
           informationServerGUI.getjButtonAskName().setEnabled( false );
           informationServerGUI.getjButtonAskNumber().setEnabled( false );
           informationServerGUI.getjButtonAskDisconnect().setEnabled( false );
           informationServerGUI.getjButtonDisconnect().setEnabled( false );
           informationServerGUI.getjButtonConnect().setEnabled( true );
           display( "Information Server Client Halted." );
        }
    }
    void HandleMessage( String serverMessage )
    {
        display( serverMessage );
    }
    public void display( String message )
    {
        if( informationServerGUI != null )
        {
            informationServerGUI.getjTextAreaMessages().append( message + "\n" );
            informationServerGUI.getjTextAreaMessages().setSelectionStart( informationServerGUI.getjTextAreaMessages().getText().length() );
        }
        else
        {
           System.out.println( message );
        }
    }
    void AskName()
    {
       //display( "Requesting name" );
       sendData( "name" );
    }
    void AskContractsWon()
    {
       //display( "Requesting ContractsWon" );
       sendData( "contracts_won" );
    }
    void AskGroupNumber()
    {
       //display( "Requesting GroupNumber" );
       sendData( "group_number" );
    }
    void AskDisconnect()
    {
       //display( "Requesting Disconnect" );
       sendData( "disconnect" );
    }
}
