/*
 * DatabaseAccessClass.java
 *
 * Created on February 28, 2007, 10:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

import java.sql.*;
import javax.swing.*;

/**
 *This class is responsible for reading and writing to mysql.
 * @author Default
 */
public class DatabaseAccessClass
{

    /** Connection to database*/
    private Connection connection;

    /** Database name*/
    private String dbSchemaName;

    /** User Name*/
    private String dbUserName;

    /** Password*/
    private String dbPassword;

    /** Nade Client Class*/
    public  NadeClient nadeClient;

    /** Retry connection to database*/
    public  boolean bRetry;

    /** Database Login Dialog*/
    private DatabaseLoginDialog databaseLoginDialog;

    /** MySQLURL*/
    private String MySQLURL;

    /**
     * DefaultPublicName
     */
    private String DefaultPublicName;

    /** Creates a new instance of DatabaseAccessClass */
    public DatabaseAccessClass()
    {
        nadeClient          = null;
        connection          = null;
        dbSchemaName        = "webapps";
        dbUserName          = "root";
        dbPassword          = "passmysqladmin";
        databaseLoginDialog = null;
        bRetry              = true;
        MySQLURL            = "jdbc:mysql:///";
        DefaultPublicName   = "Gsladk";
    }

    /**
     * Deletes the record created by this program instance.
     */
    private void DeleteRecord()
    {
       if( connection == null )
       {
          return;
       }
       try
       {
          if( !connection.isClosed() )
          {
             String dropRecord   = "DELETE FROM `groups` WHERE publicName LIKE '" + DefaultPublicName + "';";
             Statement insertStatement = connection.createStatement();
             if( insertStatement != null )
             {
                insertStatement.executeUpdate( dropRecord   );
             }
          }
       }
       catch( SQLException e )
       {
          display( "DatabaseAccessClass.DeleteRecord - SQLException:" + e.getMessage() );
       }
       catch( Exception e )
       {
          display( "DatabaseAccessClass.DeleteRecord - Exception:" + e.getMessage() );
       }
    }

    /**Create Record to store information
     *publicName
     *groupNumber
     *contractsWon
     */
    private boolean InsertRecord()
    {
       try
       {
          DefaultPublicName = nadeClient.nadeProjectGUIInterface.getjTextFieldPublicName().getText();
          String  insertRow = "INSERT INTO groups VALUES( '" + DefaultPublicName + "', 0, 0  );";
          Statement insertStatement = connection.createStatement();
          insertStatement.executeUpdate( insertRow   );
          return true;
       }
       catch( SQLException e )
       {
          display( "SQLException:" + e.getMessage() );
          return false;
       }
    }

    /**Returns MySQLURL*/
    public String getMySQLURL()
    {
        return MySQLURL;
    }

    /**
     *Sets the database to connect to.
     *@param un New database name.
     */
    public void setdbSchemaName( String _dbSchemaName )
    {
        dbSchemaName = _dbSchemaName;
    }

    /**Returns dbSchemaName*/
    public String getdbSchemaName()
    {
        return dbSchemaName;
    }

    /**
     *Sets the Password used to connect to the database.
     *@param un New Password.
     */
    public void setdbPassword( String pw )
    {
        dbPassword = pw;
    }

    /**
     *Sets the UserName used to connect to the database.
     *@param un New User Name.
     */
    public void setdbUserName( String un )
    {
        dbUserName = un;
    }

    /**Returns dbPassword*/
    public String getdbPassword()
    {
        return dbPassword;
    }

    /**Returns dbUserName*/
    public String getdbUserName()
    {
        return dbUserName;
    }

    /** Returns the connection to the database.*/
    public java.sql.Connection getConnection()
    {
        return connection;
    }

    /**
     *Sets the public name.
     *@param name New publicName
     */
    public boolean setName( String name )
    {
        if( connection != null )
        {
           try
           {
              Statement statement = connection.createStatement();
              statement.executeUpdate( "UPDATE GROUPS SET publicName='" + name +"' WHERE publicName LIKE '" + DefaultPublicName + "';" );
              return true;
           }
           catch( SQLException e )
           {
               display( "DatabaseAccessClass.setName - SQLException: " + e.getMessage() );
               return false;
           }
        }
        return false;
    }

    /**
     *Returns publicName.
     */
    public String getName()
    {
        if( connection == null )
        {
        }

        if( connection != null )
        {
           String publicName = "";
           try
           {
              Statement statement = connection.createStatement();
              ResultSet resultset = statement.executeQuery( "SELECT publicName FROM groups WHERE publicName LIKE'" + DefaultPublicName +"';" );
              resultset.next();
              publicName = resultset.getString( "publicName" );
              return publicName;
           }
           catch( SQLException e )
           {
               display( "DatabaseAccessClass.getName - SQLException: " + e.getMessage() );
               return publicName;
           }
        }
        else
        {
           if( nadeClient != null )
           {
              return nadeClient.nadeProjectGUIInterface.getjTextFieldPublicName().getText();
           }
           else
           {
              return "";
              //return "Gsladk Masters of the Unseen Cosmo";
           }
        }
    }

    /**
     *Sets groupNumber.
     *@param _groupNumber new value for groupNumber.
     */
    public boolean setGroupNumber( int _groupNumber )
    {
        if( connection != null )
        {
           try
           {
              Statement statement = connection.createStatement();
              statement.executeUpdate( "UPDATE GROUPS SET groupNumber=" + _groupNumber +" WHERE publicName LIKE '" + DefaultPublicName + "';" );
              return true;
           }
           catch( SQLException e )
           {
               display( "DatabaseAccessClass.setGroupNumber - SQLException: " + e.getMessage() );
               return false;
           }
        }
        return false;
    }

    /**
     *Returns groupNumber.
     */
    public int getGroupNumber()
    {
        if( connection == null )
        {
        }

        if( connection != null )
        {
           int groupNumber = 7;
           try
           {
              Statement statement = connection.createStatement();
              ResultSet resultset = statement.executeQuery( "SELECT groupNumber FROM groups WHERE publicName LIKE '" + DefaultPublicName + "';" );
              resultset.next();
              groupNumber = resultset.getInt( "groupNumber" );
              return groupNumber;
           }
           catch( SQLException e )
           {
               display( "DatabaseAccessClass.getGroupNumber - SQLException: " + e.getMessage() );
               return groupNumber;
           }
        }
        else
        {
           return 7;
        }
    }

    /**
     *Sets the contracts won.
     *@param contractsWon number of contracts won.
     */
    public boolean setContractsWon( int contractsWon )
    {
        if( connection != null )
        {
           try
           {
              Statement statement = connection.createStatement();
              statement.executeUpdate( "UPDATE GROUPS SET contractsWon=" + contractsWon +" WHERE publicName LIKE '" + DefaultPublicName + "';" );
              return true;
           }
           catch( SQLException e )
           {
               display( "DatabaseAccessClass.setContractsWon - SQLException: " + e.getMessage() );
               return false;
           }
        }
        return false;
    }

    /**
     *Returns contractsWon.
     */
    public int getContractsWon()
    {
        if( connection == null )
        {
        }

        if( connection != null )
        {
           int contractsWon = 0;
           try
           {
              Statement statement = connection.createStatement();
              ResultSet resultset = statement.executeQuery( "SELECT contractsWon FROM groups WHERE publicName LIKE '" + DefaultPublicName + "';" );
              resultset.next();
              contractsWon = resultset.getInt( "contractsWon" );
              return contractsWon;
           }
           catch( SQLException e )
           {
               display( "DatabaseAccessClass.getContractsWon - SQLException: " + e.getMessage() );
               return contractsWon;
           }
        }
        else
        {
           return 0;
        }
    }

   /**
     * Sets the url used to connect to mysql
     *@param url.
     */
    public void setMySQLURL( String url )
    {
        MySQLURL = url;
    }

    /**
     * Connects to the database
     *@param NadeClient class.
     */
    public boolean Connect( NadeClient _nadeclient )
    {
       try
       {
          Disconnect();

          nadeClient = _nadeclient;

          if( nadeClient == null )
              return false;

          try
          {
             String temp = "";
             if( nadeClient.getINIFile().getStringProperty( "Database", "dbSchema"   ) != null )
             {
                dbSchemaName = nadeClient.getINIFile().getStringProperty( "Database", "dbSchema"   );
             }
             if( nadeClient.getINIFile().getStringProperty( "Database", "dbUserName" ) != null )
             {
                dbUserName   = nadeClient.getINIFile().getStringProperty( "Database", "dbUserName" );
             }
             dbPassword   = nadeClient.getINIFile().getStringProperty( "Database", "dbPassword" );
          }
          catch( Exception e )
          {
             display( "Exception: " + e.getMessage() );
          }

          Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
          
          databaseLoginDialog = new DatabaseLoginDialog( this );
          databaseLoginDialog.setModalityType( java.awt.Dialog.ModalityType.APPLICATION_MODAL );

          bRetry = true;

          while( (connection == null) && bRetry )
          {
             try
             {
                connection = DriverManager.getConnection( MySQLURL, dbUserName, dbPassword );
                Statement createDBStatement  = connection.createStatement();
                String createDatabase = "CREATE DATABASE IF NOT EXISTS " + dbSchemaName + ";";
                createDBStatement.executeUpdate(  createDatabase );
             }
             catch( Exception e )
             {
                display( e.getMessage() );
                try
                {
                   JOptionPane.showMessageDialog( nadeClient.nadeProjectGUIInterface.getFrames()[0], e.getMessage() );
                }
                catch( Exception ex )
                {
                   display( ex.getMessage() );
                }
                databaseLoginDialog.setVisible( true );
                nadeClient.getINIFile().setStringProperty( "Database", "dbSchema",   dbSchemaName, "none" );
                nadeClient.getINIFile().setStringProperty( "Database", "dbUserName", dbUserName,   "none" );
                nadeClient.getINIFile().setStringProperty( "Database", "dbPassword", dbPassword,   "none" );
             }
          }
          if( nadeClient != null )
          {
             try
             {
                if( nadeClient.getINIFile() != null )
                {
                   nadeClient.getINIFile().save();
                }
             }
             catch( Exception e )
             {
                 display( "Exception: " + e.getMessage() );
             }
          }
          if( !connection.isClosed() )
          {
             display( "Connected to database" );
             nadeClient.nadeProjectGUIInterface.getjCheckBoxConnectedToDatabase().setSelected( true );
             try
             {
                Statement       useStatement = connection.createStatement();
                Statement createTBLStatement = connection.createStatement();
                String    useDatabase = "USE " + dbSchemaName + ";";
                String    createTable = "CREATE TABLE IF NOT EXISTS `groups` ("                              +
                                        "`publicName` varchar(45) NOT NULL default 'Gsladk Masters of the Unseen Cosmo'," +
                                        "`groupNumber` int(10) unsigned NOT NULL default '0'," +
                                        "`contractsWon` int(10) unsigned NOT NULL default '0'" +
                                        ") ENGINE=InnoDB DEFAULT CHARSET=latin1;" ;
                useStatement.executeUpdate(       useDatabase    );
                createTBLStatement.executeUpdate( createTable    );
             }
             catch( SQLException e )
             {
                 display( "DatabaseAccessClass.Connect - Connection to database failed. SQLException" + e.getMessage() );
                 Disconnect();
                 return false;
             }

             if( !InsertRecord() )
             {
                Disconnect();
                return false;
             }
             nadeClient.databaseAccessClass.setGroupNumber( 7 );
             return true;
          }
          else
          {
             display( "Connection to database failed." );
             return false;
          }
       }
       catch( Exception e )
       {
          display( "Exception: " + e.getMessage() );
          display( "Connection to database failed. Invalid Password or MySQL Server not running." );
       }
       finally
       {
          if( nadeClient != null )
          {
             if( nadeClient.getINIFile() != null )
             {
                //nadeClient.getINIFile().save();
             }
          }
          return false;
       }
    }

    /**Close connection and delete record.*/
    protected void finalize()
    {
        Disconnect();
    }

    /**
     * Disconnects from the database
     */
    public boolean Disconnect()
    {
       try
       {
          if( connection != null )
          {
             if( !connection.isClosed() )
             {
                DeleteRecord();
                connection.close();
                if( nadeClient != null )
                {
                   nadeClient.nadeProjectGUIInterface.getjCheckBoxConnectedToDatabase().setSelected( false );
                }
                display( "Database connection closed." );
                connection = null;
             }
          }
          return true;
       }
       catch( SQLException e )
       {
          display( "DatabaseAccessClass.Disconnect - SQLException: " + e.getMessage() );
          return false;
       }
       catch( Exception e )
       {
          display( "DatabaseAccessClass.Disconnect - Exception: " + e.getMessage() );
          return false;
       }
    }

    /**
     * Displays a message in nadeClient.display( message )
     * if nadeClient is null it uses 
     * System.out.println( message )
     *@param message The message to be displayed
     */
    public void display( String message )
    {
        if( nadeClient != null )
        {
            nadeClient.display( message );
        }
        else
        {
            System.out.println( message );
        }
    }
}
