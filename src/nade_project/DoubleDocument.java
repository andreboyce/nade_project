/*
 * DoubleDocument.java
 *
 * Created on March 14, 2007, 3:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

import java.awt.*;
import javax.swing.text.*;
import java.text.*;

/**
 * This class when set as the document for a TextField such as JTextField
 * will only allow Doubles to be input.
 * @author Default
 */
public class DoubleDocument extends PlainDocument
{
    /**Class to set decimal format e.g. 1.0, 1.00, 001.00 etc...*/
    DecimalFormat formatter;

    /** Creates a new instance of IntegerDocument */
    public DoubleDocument()
    {
       formatter = new DecimalFormat("###.00");
       formatter.setDecimalSeparatorAlwaysShown( true );
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
          try
          {
             //Double.parseDouble( newValue );
             super.insertString( offset, string, attributes );
             //super.insertString( offset, formatter.format( string ), attributes );
          }
          catch( NumberFormatException exception )
          {
             Toolkit.getDefaultToolkit().beep();
          }
       }
    }    
}
