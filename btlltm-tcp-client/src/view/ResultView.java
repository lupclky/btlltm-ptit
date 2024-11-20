/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

/**
 *
 * @author nguye
 */
public class ResultView extends javax.swing.JFrame {

    /**
     * Creates new form ResultView
     */
    public ResultView() {
        initComponents();
    }
    
    public void showResult(String username, String state, String wish, String scoreHost, String scoreCompetitor){
        lblUsername.setText(username);
        lblState.setText(state);
        if(state.endsWith("WIN.")){
            lblState.setForeground(new java.awt.Color(0, 153, 51));
            this.scoreHost.setForeground(new java.awt.Color(0, 153, 51));
            this.scoreCompetitor.setForeground(new java.awt.Color(255, 0, 0));
        }
        else if(state.startsWith("DRAW")){
            lblState.setForeground(new java.awt.Color(102, 102, 0));
            this.scoreHost.setForeground(new java.awt.Color(102, 102, 0));
            this.scoreCompetitor.setForeground(new java.awt.Color(102, 102, 0));
        }
        else{
            lblState.setForeground(new java.awt.Color(255, 0, 0));
            this.scoreHost.setForeground(new java.awt.Color(255, 0, 0));
            this.scoreCompetitor.setForeground(new java.awt.Color(0, 153, 51));
        }
        lblWishWord.setText(wish);
        this.scoreHost.setText(scoreHost);
        this.scoreCompetitor.setText(scoreCompetitor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblState = new javax.swing.JLabel();
        lblWishWord = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        scoreHost = new javax.swing.JLabel();
        scoreCompetitor = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblState.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblState.setForeground(new java.awt.Color(102, 102, 0));
        lblState.setText("Your Result ");
        getContentPane().add(lblState, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, -1));

        lblWishWord.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblWishWord.setText("Congratulation!!! ");
        getContentPane().add(lblWishWord, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, -1));

        btnExit.setBackground(new java.awt.Color(153, 0, 0));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("Your Score");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 147, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("Competitor's Score");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 197, -1, -1));

        scoreHost.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        scoreHost.setText("Score1");
        getContentPane().add(scoreHost, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, -1));

        scoreCompetitor.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        scoreCompetitor.setText("Score2");
        getContentPane().add(scoreCompetitor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, -1, -1));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(0, 51, 51));
        lblUsername.setText("jLabel1");
        getContentPane().add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/light-red-background 1 (1).jpg"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 350));

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
            java.util.logging.Logger.getLogger(ResultView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResultView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResultView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResultView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResultView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblState;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblWishWord;
    private javax.swing.JLabel scoreCompetitor;
    private javax.swing.JLabel scoreHost;
    // End of variables declaration//GEN-END:variables
}
