/*
 * RumourDialog.java
 *
 * Created on March 8, 2007, 2:36 AM
 */

package nade_project;

/**
 *This dialog allows the user to send rumors.
 * @author  Default
 */
public class RumourDialog extends javax.swing.JFrame
{
    /** NadeClient class.*/
    NadeClient nadeClient;

    /** Creates new form RumourDialog */
    public RumourDialog( NadeClient _nadeClient )
    {
        initComponents();
        nadeClient  = _nadeClient;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabelRumour = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaRumour = new javax.swing.JTextArea();
        jButtonSendRomour = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();

        setTitle("Rumour Dialog");
        jLabelRumour.setText("Rumour");

        jTextAreaRumour.setColumns(20);
        jTextAreaRumour.setRows(5);
        jTextAreaRumour.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextAreaRumourKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextAreaRumourKeyTyped(evt);
            }
        });

        jScrollPane1.setViewportView(jTextAreaRumour);

        jButtonSendRomour.setText("Send Rumour");
        jButtonSendRomour.setToolTipText("Send a rumor to all clients.");
        jButtonSendRomour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendRomourActionPerformed(evt);
            }
        });

        jButtonClear.setText("Clear");
        jButtonClear.setToolTipText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelRumour)
                                .addGap(161, 161, 161))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(198, Short.MAX_VALUE)
                        .addComponent(jButtonClear)
                        .addGap(16, 16, 16)
                        .addComponent(jButtonSendRomour)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelRumour)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSendRomour)
                    .addComponent(jButtonClear))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**Send the rumour when enter key pressed.*/
    private void jTextAreaRumourKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextAreaRumourKeyTyped
       if( nadeClient != null )
       {
          if( evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER )
          {
             nadeClient.SendRumour();
             jTextAreaRumour.setText( "" );
             jTextAreaRumour.setSelectionStart( 0 );
          }
       }
    }//GEN-LAST:event_jTextAreaRumourKeyTyped

    /**Not used*/
    private void jTextAreaRumourKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextAreaRumourKeyReleased
    }//GEN-LAST:event_jTextAreaRumourKeyReleased

    /**Clear messages*/
    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
       jTextAreaRumour.setText( "" );
    }//GEN-LAST:event_jButtonClearActionPerformed

    /**Send rumour*/
    private void jButtonSendRomourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendRomourActionPerformed
       if( nadeClient != null )
       {
           nadeClient.SendRumour();
       }
    }//GEN-LAST:event_jButtonSendRomourActionPerformed
    
    /**@return jButtonSendRomour*/
    javax.swing.JButton getjButtonSendRomour()
    {
        return jButtonSendRomour;
    }

    /**@return jTextAreaRumour*/
    javax.swing.JTextArea getjTextAreaRumour()
    {
        return jTextAreaRumour;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonSendRomour;
    private javax.swing.JLabel jLabelRumour;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaRumour;
    // End of variables declaration//GEN-END:variables
    
}
