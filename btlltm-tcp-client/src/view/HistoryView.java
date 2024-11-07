/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nguye
 */
public class HistoryView extends javax.swing.JFrame {

    /**
     * Creates new form HistoryView
     */
    public HistoryView() {
        initComponents();
    }
    
    public void setHistoryTable(Vector vdata, Vector vheader) {
        tblHistory.setModel(new DefaultTableModel(vdata, vheader));
    }
    
    public DefaultTableModel getHistoryTableModel(){
        return (DefaultTableModel) this.tblHistory.getModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        jScrollBXH = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        lblBXH = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnExit.setBackground(new java.awt.Color(153, 0, 0));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, -1, -1));

        jScrollBXH.setBackground(new java.awt.Color(255, 204, 204));
        jScrollBXH.setBorder(null);
        jScrollBXH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Competitor", "myScore", "compScore", "status", "time"
            }
        ));
        jScrollBXH.setViewportView(tblHistory);

        getContentPane().add(jScrollBXH, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 520, 230));

        lblBXH.setBackground(new java.awt.Color(153, 0, 0));
        lblBXH.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblBXH.setText("Lịch sử thi đấu");
        getContentPane().add(lblBXH, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/light-red-background 1 (1).jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, -7, 570, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HistoryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistoryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistoryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistoryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistoryView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollBXH;
    private javax.swing.JLabel lblBXH;
    private javax.swing.JTable tblHistory;
    // End of variables declaration//GEN-END:variables
}
