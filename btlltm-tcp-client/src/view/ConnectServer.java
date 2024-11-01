/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.SocketHandler;
import javax.swing.JOptionPane;
import run.ClientRun;

/**
 *
 * @author admin
 */
public class ConnectServer extends javax.swing.JFrame {

    /**
     * Creates new form ConnectServer
     */
    public ConnectServer() {
        initComponents();
    
        // Khởi tạo socketHandler nếu nó chưa được khởi tạo
        if (ClientRun.socketHandler == null) {
            ClientRun.socketHandler = new SocketHandler();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txIP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txPort = new javax.swing.JTextField();
        btnConnect = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 153, 153));
        setForeground(new java.awt.Color(153, 255, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("CONNECT TO SERVER");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 240, 34));

        txIP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txIP.setText("127.0.0.1");
        txIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txIPActionPerformed(evt);
            }
        });
        getContentPane().add(txIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 98, 240, 37));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("IP");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 98, 56, 37));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("PORT");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 153, 56, 37));

        txPort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txPort.setText("2000");
        txPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txPortActionPerformed(evt);
            }
        });
        getContentPane().add(txPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 153, 240, 37));

        btnConnect.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnConnect.setText("CONNECT");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        getContentPane().add(btnConnect, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 225, 174, 48));

        background.setBackground(new java.awt.Color(255, 204, 204));
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Red Background.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        String ip;
        int port;

        // validate input
        try {
            ip = txIP.getText();
            port = Integer.parseInt(txPort.getText());
            System.out.println("port = " + port + "; IP = " + ip);

            if (port < 0 || port > 65535) {
                JOptionPane.showMessageDialog(this, "Port phải từ 0 - 65535", "Sai port", JOptionPane.ERROR_MESSAGE);
                txPort.requestFocus();
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Port phải là số nguyên", "Sai port", JOptionPane.ERROR_MESSAGE);
            txPort.requestFocus();
            return;
        }

        // connect to server
        connect(ip, port);
    }//GEN-LAST:event_btnConnectActionPerformed

    private void txPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txPortActionPerformed
        // TODO add your handling code here:
        String txPortString = txIP.getText();
        System.out.println(txPortString);
    }//GEN-LAST:event_txPortActionPerformed

    private void txIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txIPActionPerformed

    private void connect(String ip, int port) {

        // connect to server
        new Thread(() -> {
            // call controller
            String result = ClientRun.socketHandler.connect(ip, port);

            // check result
            if (result.equals("success")) {
                onSuccess();
            } else {
                String failedMsg = result.split(";")[1];
                onFailed(failedMsg);
            }
        }).start();
    }

    private void onSuccess() {
        this.dispose();
        ClientRun.openScene(ClientRun.SceneName.LOGIN);

        System.out.println("connect to server thanh cong");
    }

    private void onFailed(String failedMsg) {
        JOptionPane.showMessageDialog(this, failedMsg, "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
    }
    
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
            java.util.logging.Logger.getLogger(ConnectServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConnectServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConnectServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConnectServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConnectServer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnConnect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txIP;
    private javax.swing.JTextField txPort;
    // End of variables declaration//GEN-END:variables
}
