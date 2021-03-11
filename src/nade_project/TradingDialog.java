/*
 * TradingDialog.java
 *
 * Created on March 7, 2007, 1:42 PM
 */

package nade_project;

/**
 *This dialog allows the user to buy and sell resources with nade.
 * @author  Default
 */
public class TradingDialog extends javax.swing.JFrame {

    /** NADE Control Client class. */
    NadeClient nadeClient;

    /** Creates new form TradingDialog */
    public TradingDialog( NadeClient _nadeClient )
    {
       initComponents();
       nadeClient = _nadeClient;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButtonBuyResources = new javax.swing.JButton();
        jLabelResourcesToBuy = new javax.swing.JLabel();
        jTextFieldResourcesToBuy = new javax.swing.JTextField();
        jButtonSellResources = new javax.swing.JButton();
        jLabelNadeControlResources = new javax.swing.JLabel();
        jTextFieldNadeControlResources = new javax.swing.JTextField();
        jTextFieldOurResources = new javax.swing.JTextField();
        jLabelOurResources = new javax.swing.JLabel();
        jLabelOurMoney = new javax.swing.JLabel();
        jTextFieldOurMoney = new javax.swing.JTextField();
        jLabelDescription = new javax.swing.JLabel();
        jTextFieldResourcesToSell = new javax.swing.JTextField();
        jLabelResourcesToSell = new javax.swing.JLabel();
        jLabelCostPerResource = new javax.swing.JLabel();
        jTextFieldCostPerResource = new javax.swing.JTextField();

        setTitle("Trading Dialog");
        jButtonBuyResources.setText("Buy Resources");
        jButtonBuyResources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuyResourcesActionPerformed(evt);
            }
        });

        jLabelResourcesToBuy.setText("Resources to Buy");

        jTextFieldResourcesToBuy.setDocument(new IntegerDocument());
        jTextFieldResourcesToBuy.setText("10");

        jButtonSellResources.setText("Sell Resources");
        jButtonSellResources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSellResourcesActionPerformed(evt);
            }
        });

        jLabelNadeControlResources.setText("Nade Control Resources");

        jTextFieldNadeControlResources.setDocument(new IntegerDocument());
        jTextFieldNadeControlResources.setEditable(false);
        jTextFieldNadeControlResources.setText("0");

        jTextFieldOurResources.setDocument(new IntegerDocument());
        jTextFieldOurResources.setEditable(false);
        jTextFieldOurResources.setText("0");

        jLabelOurResources.setText("Our Resources");

        jLabelOurMoney.setText("Our Money");

        jTextFieldOurMoney.setDocument(new IntegerDocument());
        jTextFieldOurMoney.setEditable(false);
        jTextFieldOurMoney.setText("0");

        jLabelDescription.setText("Buy and sell resources before and after bids.");

        jTextFieldResourcesToSell.setDocument(new IntegerDocument());
        jTextFieldResourcesToSell.setText("10");

        jLabelResourcesToSell.setText("Resources to Sell");

        jLabelCostPerResource.setText("Cost Per Resource");

        jTextFieldCostPerResource.setDocument(new DoubleDocument());
        jTextFieldCostPerResource.setEditable(false);
        jTextFieldCostPerResource.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDescription)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelResourcesToSell)
                            .addComponent(jLabelResourcesToBuy))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldResourcesToBuy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldResourcesToSell, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonBuyResources, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonSellResources, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelOurMoney)
                            .addComponent(jLabelNadeControlResources)
                            .addComponent(jLabelOurResources)
                            .addComponent(jLabelCostPerResource))
                        .addGap(121, 121, 121)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldOurResources, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(jTextFieldOurMoney, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(jTextFieldNadeControlResources, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .addComponent(jTextFieldCostPerResource, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNadeControlResources)
                    .addComponent(jTextFieldNadeControlResources, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOurResources)
                    .addComponent(jTextFieldOurResources, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOurMoney)
                    .addComponent(jTextFieldOurMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCostPerResource)
                    .addComponent(jTextFieldCostPerResource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabelResourcesToBuy))
                            .addComponent(jButtonBuyResources)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTextFieldResourcesToBuy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabelResourcesToSell))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextFieldResourcesToSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonSellResources))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    /** Performs the nadeClient.SellResources() action.
     */
    private void jButtonSellResourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSellResourcesActionPerformed
       if( nadeClient != null )
       {
           nadeClient.SellResources();
       }
    }//GEN-LAST:event_jButtonSellResourcesActionPerformed

    /** Performs the nadeClient.BuyResources() action.
     */
    private void jButtonBuyResourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuyResourcesActionPerformed
       if( nadeClient != null )
       {
           nadeClient.BuyResources();
       }
    }//GEN-LAST:event_jButtonBuyResourcesActionPerformed
    
    /** Returns jTextFieldNadeControlResources
     */
    javax.swing.JTextField getjTextFieldNadeControlResources()
    {
        return jTextFieldNadeControlResources;
    }
    /** Returns jTextFieldOurResources
     */
    javax.swing.JTextField getjTextFieldOurResources()
    {
        return jTextFieldOurResources;
    }
    /** Returns jTextFieldResourcesToBuy
     */
    javax.swing.JTextField getjTextFieldResourcesToBuy()
    {
        return jTextFieldResourcesToBuy;
    }
    /** Returns jTextFieldResourcesToSell
     */
    javax.swing.JTextField getjTextFieldResourcesToSell()
    {
        return jTextFieldResourcesToSell;
    }
    /** Returns jTextFieldOurMoney
     */
    javax.swing.JTextField getjTextFieldOurMoney()
    {
        return jTextFieldOurMoney;
    }
    /** Returns jButtonBuyResources
     */
    javax.swing.JButton getjButtonBuyResources()
    {
        return jButtonBuyResources;
    }
    /** Returns jButtonSellResources
     */
    javax.swing.JButton getjButtonSellResources()
    {
        return jButtonSellResources;
    }

    /**@return jTextFieldCostPerResource */
    javax.swing.JTextField getjTextFieldCostPerResource()
    {
        return jTextFieldCostPerResource;
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuyResources;
    private javax.swing.JButton jButtonSellResources;
    private javax.swing.JLabel jLabelCostPerResource;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelNadeControlResources;
    private javax.swing.JLabel jLabelOurMoney;
    private javax.swing.JLabel jLabelOurResources;
    private javax.swing.JLabel jLabelResourcesToBuy;
    private javax.swing.JLabel jLabelResourcesToSell;
    private javax.swing.JTextField jTextFieldCostPerResource;
    private javax.swing.JTextField jTextFieldNadeControlResources;
    private javax.swing.JTextField jTextFieldOurMoney;
    private javax.swing.JTextField jTextFieldOurResources;
    private javax.swing.JTextField jTextFieldResourcesToBuy;
    private javax.swing.JTextField jTextFieldResourcesToSell;
    // End of variables declaration//GEN-END:variables
    
}
