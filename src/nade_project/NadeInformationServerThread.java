/*
 * NadeInformationServerThread.java
 *
 * Created on February 23, 2007, 12:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

import nade.client.*;
import java.io.*;
import java.net.*;

/**
 *This class maintains a connection with each client connected to the information server.
 * @author Default
 */
public class NadeInformationServerThread extends Thread
{
    /**Information server connection*/
    private Socket connection;

    /**Nade Information Server class*/
    private NadeInformationServer server;

    /**Class used to send data to clients connected to the information server*/
    private ObjectOutputStream output;

    /**Class used to receive data from clients connected to the information server*/
    private ObjectInputStream input;

    /**Nade client class*/
    private NadeClient nadeClient;

    /** Creates a new instance of NadeInformationServerThread */
    public NadeInformationServerThread( Socket aSocket, NadeInformationServer aServer )
    {
        connection = aSocket;
        server     = aServer;
        nadeClient = aServer.getNadeClient();
    }

    /**Information server thread main loop.*/
    public void run()
    {
        try
        {
            String message = "";
            GroupStatus gs = nadeClient.GetGroup( connection.getInetAddress().getHostAddress() );
            String groupname = "";
            if( gs != null )
            {
            }
            display( groupname + " Client has logged in to the information server from : " + connection.getInetAddress().getHostAddress() );
            boolean bLoop = true;

                output = new ObjectOutputStream( connection.getOutputStream() );
                output.flush();
                input = new ObjectInputStream( connection.getInputStream() );
                do
                {
                    try
                    {
                       if( input == null )
                       {
                          bLoop = false;
                          break;
                       }
                       message = (String)input.readObject();
                       if( message.equals("") )
                           break;
                       if( message.equals( "name" ) )
                       {
                           //display( "Client requested name." );
                           String name = "name";
                           if( nadeClient != null )
                           {
                              name = nadeClient.databaseAccessClass.getName();
                           }
                           sendData( name );
                       }
                       else if( message.equals( "group_number" ) )
                       {
                           //display( "Client requested group_number." );
                           int group_number = 7;
                           if( nadeClient != null )
                           {
                              group_number = nadeClient.databaseAccessClass.getGroupNumber();
                           }
                           sendData( String.valueOf( group_number ) );
                       }
                       else if( message.equals( "contracts_won" ) )
                       {
                           //display( "Client requested contracts_won." );
                           int contracts_won = 0;
                           if( nadeClient != null )
                           {
                              contracts_won = nadeClient.databaseAccessClass.getContractsWon();
                           }
                           sendData( String.valueOf( contracts_won ) );
                       }
                       else if( message.equals( "disconnect" ) )
                       {
                           display( "Client requested disconnect." );
                           //sendData( "disconnecting...." );
                           bLoop = false;
                       }
                    }
                    catch( ClassNotFoundException e )
                    {
                       display( "NadeInformationServerThread.run - Unknown Object type recieved. ClassNotFoundException: " + e.getMessage() );
                    }
                    catch( Exception e )
                    {
                       display( "InformationServer connection closed. NadeInformationServerThread.run - Exception: " + e.getMessage() );
                       bLoop = false;
                    }
                } while( bLoop );
                //display( "InformationServer Thread Connection closed." );
                output.close();
                input.close();
                connection.close();
        }
        catch( IOException e )
        {
           display( "Client has logged out. IOException: " + e.getMessage() );
        }
        catch( Exception e )
        {
           display( "NadeInformationServerThread.run - Exception: " + e.getMessage() );
        }
    }

    /**Displays a message in the main loop.*/
    void display( String message )
    {
        if( server != null )
        {
           server.nadeClient.display( message );
        }
        else
        {
            System.out.println( message );
        }
    }

    /**Sends data to the connected clients.*/
    private synchronized void sendData( String message )
    {
        try
        {
            if( output != null && connection != null )
            {
               if( !connection.isClosed() )
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
}


