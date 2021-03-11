/*
 * IPDocument.java
 *
 * Created on March 14, 2007, 2:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

//import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import java.awt.*;
import javax.swing.text.*;

/**
 * This class when set as the document for a TextField such as JTextField
 * will only allow IP Addresses xxx.xxx.xxx to be input.
 * @author Default
 */
public class IPDocument extends PlainDocument
{
    
    /** Creates a new instance of IPDocument */
    public IPDocument()
    {
    }

    /**
     * Overidess the PlainDocument.insertString method
     *@param offset Position to add string.
     *@param string String to add.
     *@param attributes See PlainDocument.insertString
     */
    public void insertString( int offset, String string, AttributeSet attributes )
                throws BadLocationException
    {
       if( string == null )
       {
          return;
       }
       else
       {
          try
          {
             String newValue;
             int length = getLength();
             if( length == 0 )
             {
                newValue = string;
             }
             else
             {
                String currentContent = getText( 0, length );
                StringBuffer currentBuffer = new StringBuffer( currentContent );
                currentBuffer.insert( offset, string );
                newValue = currentBuffer.toString();
             }
             if( super.getLength() == 0 && string.length() == 0 )
             {
                 super.insertString( offset, "0.0.0.0", attributes );
             }
             else
             {
                super.insertString( offset, string, attributes );
             }
             /*if( newValue.matches( "(\\d)+.(\\d)+.(\\d)+" ) )
             {
                super.insertString( offset, string, attributes );
             }
             else
             {
                Toolkit.getDefaultToolkit().beep();
             }*/
          }
          catch( Exception e )
          {           
          }
       }
    }
}
 
