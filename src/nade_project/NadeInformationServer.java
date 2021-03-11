/*
 * NadeInformationServer.java
 *
 * Created on February 22, 2007, 11:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

import nade.client.*;
import java.io.*;
import java.nio.channels.*;
import java.net.*;
import javax.swing.*;

/**
 *This class maintains the information server.
 * @author Default
 */
public class NadeInformationServer extends Thread
{
    /**NadeClient class*/
    public  NadeClient   nadeClient;

    /**Loop that determins if the server should continue listening.*/
    private boolean      waiting;

    /**Information server socket*/
    private ServerSocket socket;

    /** Creates a new instance of NadeInformationServer */
    public NadeInformationServer()
    {
       waiting = false;
       socket  = null;
    }

    /**@return nadeClient*/
    public NadeClient getNadeClient()
    {
       return nadeClient;
    }

    /**Starts the information server.*/
    public void StartServer( NadeClient _nadeclient )
    {
        nadeClient = _nadeclient;
        start();
    }

    /**Stops the information server.*/
    void StopServer()
    {
       waiting = false;
    }

    /**Information server main loop.*/
    synchronized public void run()
    {
        if( nadeClient == null )
            return;

        waiting = true;
        try
        {
            nadeClient.databaseAccessClass.Connect( nadeClient );

            // Potential security problem
            // What happens when too many clients try to log on?

            // This uses non blocking sockets
            // instead of the blocking sockets code sample Sir used
            int serverport = 8000;
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking( false );
            socket = ssc.socket();
            InetSocketAddress isa = new InetSocketAddress( serverport );
            socket.bind( isa );
            nadeClient.nadeProjectGUIInterface.getjCheckBoxInformationServerRunning().setSelected( true );
            display( "Inforamtion Server Started" );
            // loop and detect incoming using polling
            while( waiting )
            {
               try
               {
                  Socket newSocket = null;
                  try
                  {
                     newSocket = socket.accept();
                  }
                  catch( IllegalBlockingModeException e )
                  {
                  }

                  if( newSocket != null )
                  {
                     //display( "Connection from " + newSocket );
                     NadeInformationServerThread nadeInformationServerThread = new NadeInformationServerThread( newSocket, this );
                     nadeInformationServerThread.start();
                  }

                  try
                  {
                     Thread.sleep( 100 );
                  }
                  catch( InterruptedException e )
                  {
                     display( "InterruptedException :" + e.getMessage() );
                  }
               }
               catch( IOException e )
               {
                 display( "NadeInformationServer.run - IOException: " + e.getMessage() );
                 display( "Information Server Halted." );
               }
               catch( Exception e )
               {
                   display( "NadeInformationServer.run - Exception :" + e.getMessage() );
               }
            }
            socket.close();
            nadeClient.databaseAccessClass.Disconnect();
            display( "Information Server Halted." );
            nadeClient.nadeProjectGUIInterface.getjCheckBoxInformationServerRunning().setSelected( false );
        }
        catch( IOException e )
        {
            display( "NadeInformationServer.run - IOException: " + e.getMessage() );
            display( "Information Server Halted." );
            if( nadeClient != null )
            {
               nadeClient.nadeProjectGUIInterface.getjCheckBoxInformationServerRunning().setSelected( false );
            }
        }
    }

    /*
     *Displays a message.
     *@param message Message to display.*/
    synchronized public void display( String message )
    {
        if( nadeClient != null  )
        {
           nadeClient.display( message );
        }
        else
        {
           //System.err.println( message );
           System.out.println( message );
        }
    }
}



