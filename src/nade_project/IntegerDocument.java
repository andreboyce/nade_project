/*
 * IntegerDocument.java
 *
 * Created on March 14, 2007, 2:34 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package nade_project;

import java.awt.*;
import javax.swing.text.*;

/**
 * This class when set as the document for a TextField such as JTextField
 * will only allow Integers to be input.
 * @author Default
 */
public class IntegerDocument extends PlainDocument
{
    
    /** Creates a new instance of IntegerDocument */
    public IntegerDocument()
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
             Integer.parseInt( newValue );
             super.insertString( offset, string, attributes );
          }
          catch( NumberFormatException exception )
          {
             Toolkit.getDefaultToolkit().beep();
          }
       }
    }    
}
