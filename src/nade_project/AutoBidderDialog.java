/*
 * AutoBidderDialog.java
 *
 * Created on March 11, 2007, 1:04 PM
 */

package nade_project;

/**
 *This dialog allows the user to control the way the auto bidder bids.
 * @author  Default
 */
public class AutoBidderDialog extends javax.swing.JFrame
{
    /** NADE Client class.*/
    NadeClient nadeClient;
    
    /**
     * Creates new form AutoBidderDialog
     */
    public AutoBidderDialog( NadeClient _nadeClient )
    {
        initComponents();
        nadeClient = _nadeClient;
        jTextFieldCompletionTime.setText( String.valueOf( jSliderCompletionTime.getValue() ) );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */


    /**@eturn jFormattedTextFieldMinimumToleratedBuyRate*/
    javax.swing.JTextField getjTextFieldCostPerResourceUnit()
    {
       return jTextFieldCostPerResourceUnit;
    }

    /**@return jTextFieldCompletionTime*/
    javax.swing.JTextField getjTextFieldCompletionTime()
    {
       return jTextFieldCompletionTime;
    }

    /**@return jTextFieldResultingBid*/
    javax.swing.JTextField getjTextFieldResultingBid()
    {
       return jTextFieldResultingBid;
    }

    /**@return jTextFieldMoneyFromContract*/
    javax.swing.JTextField getjTextFieldMoneyFromContract()
    {
       return jTextFieldMoneyFromContract;
    }

    /**@return jTextFieldAverageResourceCost*/
    javax.swing.JTextField getjTextFieldAverageResourceCost()
    {
       return jTextFieldAverageResourceCost;
    }

    /**@return jSliderCompletionTime*/
    javax.swing.JSlider getjSliderCompletionTime()
    {
       return jSliderCompletionTime;
    }

    /**@return jCheckBoxReduceContractTimeIfLostBid*/
    javax.swing.JCheckBox getjCheckBoxReduceContractTimeIfLostBid()
    {
       return jCheckBoxReduceContractTimeIfLostBid;
    }

    /**@return jCheckBoxIncreaseContractTimeIfWonBid*/
    javax.swing.JCheckBox getjCheckBoxIncreaseContractTimeIfWonBid()
    {
       return jCheckBoxIncreaseContractTimeIfWonBid;
    }

    /**@return jCheckBoxUseContractValueAlgorithm*/
    javax.swing.JCheckBox getjCheckBoxUseContractValueAlgorithm()
    {
       return jCheckBoxUseContractValueAlgorithm;
    }

    /**@return jCheckBoxOverrideBreakEvenBid*/
    javax.swing.JCheckBox getjCheckBoxOverrideBreakEvenBid()
    {
       return jCheckBoxOverrideBreakEvenBid;
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabelResourceCostPerResourceUnit = new javax.swing.JLabel();
        jTextFieldCostPerResourceUnit = new javax.swing.JTextField();
        jLabelCompletionTime = new javax.swing.JLabel();
        jTextFieldCompletionTime = new javax.swing.JTextField();
        jSliderCompletionTime = new javax.swing.JSlider();
        jLabelResultingBid = new javax.swing.JLabel();
        jTextFieldResultingBid = new javax.swing.JTextField();
        jCheckBoxReduceContractTimeIfLostBid = new javax.swing.JCheckBox();
        jCheckBoxIncreaseContractTimeIfWonBid = new javax.swing.JCheckBox();
        jLabelMoneyFromContract = new javax.swing.JLabel();
        jTextFieldMoneyFromContract = new javax.swing.JTextField();
        jCheckBoxUseContractValueAlgorithm = new javax.swing.JCheckBox();
        jCheckBoxOverrideBreakEvenBid = new javax.swing.JCheckBox();
        jLabelAverageResourceCost = new javax.swing.JLabel();
        jTextFieldAverageResourceCost = new javax.swing.JTextField();

        setTitle("Auto Pilot Options");
        jLabelResourceCostPerResourceUnit.setText("Resource Rate");

        jTextFieldCostPerResourceUnit.setDocument(new IntegerDocument());
        jTextFieldCostPerResourceUnit.setEditable(false);
        jTextFieldCostPerResourceUnit.setText("0");

        jLabelCompletionTime.setText("Completion Time");

        jTextFieldCompletionTime.setDocument(new DoubleDocument());
        jTextFieldCompletionTime.setText("0.0");
        jTextFieldCompletionTime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCompletionTimeKeyReleased(evt);
            }
        });

        jSliderCompletionTime.setMaximum(50);
        jSliderCompletionTime.setMinimum(1);
        jSliderCompletionTime.setValue(10);
        jSliderCompletionTime.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderCompletionTimeStateChanged(evt);
            }
        });
        jSliderCompletionTime.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSliderCompletionTimePropertyChange(evt);
            }
        });

        jLabelResultingBid.setText("Resulting Bid");

        jTextFieldResultingBid.setDocument(new IntegerDocument());
        jTextFieldResultingBid.setEditable(false);
        jTextFieldResultingBid.setText("0");

        jCheckBoxReduceContractTimeIfLostBid.setText("Reduce Contract Time If Lost Bid.");
        jCheckBoxReduceContractTimeIfLostBid.setToolTipText("Every time we lose a bid this will reduce the contract time.");
        jCheckBoxReduceContractTimeIfLostBid.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxReduceContractTimeIfLostBid.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jCheckBoxIncreaseContractTimeIfWonBid.setText("Increase Contract Time If Won Bid.");
        jCheckBoxIncreaseContractTimeIfWonBid.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxIncreaseContractTimeIfWonBid.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabelMoneyFromContract.setText("Money From Contract");

        jTextFieldMoneyFromContract.setDocument(new DoubleDocument());
        jTextFieldMoneyFromContract.setEditable(false);
        jTextFieldMoneyFromContract.setText("0");

        jCheckBoxUseContractValueAlgorithm.setSelected(true);
        jCheckBoxUseContractValueAlgorithm.setText("Use contract value algorithm");
        jCheckBoxUseContractValueAlgorithm.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxUseContractValueAlgorithm.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jCheckBoxOverrideBreakEvenBid.setText("Override Break Even Bid");
        jCheckBoxOverrideBreakEvenBid.setToolTipText("This makes it possible for the auto bidder to bid more than a contract is actually worth. Might be usefull near the end of the game when we are trying to get rid of resources.");
        jCheckBoxOverrideBreakEvenBid.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxOverrideBreakEvenBid.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabelAverageResourceCost.setText("Average Resource Cost");

        jTextFieldAverageResourceCost.setDocument(new DoubleDocument());
        jTextFieldAverageResourceCost.setEditable(false);
        jTextFieldAverageResourceCost.setText("5.0");
        jTextFieldAverageResourceCost.setToolTipText("Average cost of our resources.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxIncreaseContractTimeIfWonBid)
                    .addComponent(jCheckBoxReduceContractTimeIfLostBid)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelResourceCostPerResourceUnit)
                            .addComponent(jLabelCompletionTime)
                            .addComponent(jLabelResultingBid)
                            .addComponent(jLabelMoneyFromContract))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldResultingBid)
                            .addComponent(jTextFieldCompletionTime)
                            .addComponent(jTextFieldCostPerResourceUnit)
                            .addComponent(jTextFieldMoneyFromContract, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSliderCompletionTime, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabelAverageResourceCost)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAverageResourceCost, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jCheckBoxUseContractValueAlgorithm)
                    .addComponent(jCheckBoxOverrideBreakEvenBid))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelMoneyFromContract)
                            .addComponent(jTextFieldMoneyFromContract, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAverageResourceCost)
                            .addComponent(jTextFieldAverageResourceCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelResourceCostPerResourceUnit)
                            .addComponent(jTextFieldCostPerResourceUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCompletionTime)
                            .addComponent(jTextFieldCompletionTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelResultingBid)
                            .addComponent(jTextFieldResultingBid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSliderCompletionTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)))
                .addComponent(jCheckBoxReduceContractTimeIfLostBid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxIncreaseContractTimeIfWonBid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxUseContractValueAlgorithm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxOverrideBreakEvenBid)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCompletionTimeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCompletionTimeKeyReleased
       if( nadeClient != null )
       {
          if( evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER )
          {
             nadeClient.autoBidder.AdjustBidAmountForNewCompletionTime( Double.valueOf( jTextFieldCompletionTime.getText() ) );
          }
       }
    }//GEN-LAST:event_jTextFieldCompletionTimeKeyReleased

    private void jSliderCompletionTimeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderCompletionTimeStateChanged
        if( nadeClient != null )
        {
           nadeClient.autoBidder.AdjustBidAmountForNewCompletionTime( jSliderCompletionTime.getValue() );
           jTextFieldCompletionTime.setText( String.valueOf( jSliderCompletionTime.getValue() ) );
           jTextFieldResultingBid.setText( String.valueOf( nadeClient.autoBidder.n ) );
        }
    }//GEN-LAST:event_jSliderCompletionTimeStateChanged

    private void jSliderCompletionTimePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderCompletionTimePropertyChange
    }//GEN-LAST:event_jSliderCompletionTimePropertyChange
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxIncreaseContractTimeIfWonBid;
    private javax.swing.JCheckBox jCheckBoxOverrideBreakEvenBid;
    private javax.swing.JCheckBox jCheckBoxReduceContractTimeIfLostBid;
    private javax.swing.JCheckBox jCheckBoxUseContractValueAlgorithm;
    private javax.swing.JLabel jLabelAverageResourceCost;
    private javax.swing.JLabel jLabelCompletionTime;
    private javax.swing.JLabel jLabelMoneyFromContract;
    private javax.swing.JLabel jLabelResourceCostPerResourceUnit;
    private javax.swing.JLabel jLabelResultingBid;
    private javax.swing.JSlider jSliderCompletionTime;
    private javax.swing.JTextField jTextFieldAverageResourceCost;
    private javax.swing.JTextField jTextFieldCompletionTime;
    private javax.swing.JTextField jTextFieldCostPerResourceUnit;
    private javax.swing.JTextField jTextFieldMoneyFromContract;
    private javax.swing.JTextField jTextFieldResultingBid;
    // End of variables declaration//GEN-END:variables
    
}