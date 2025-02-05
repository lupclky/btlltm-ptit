/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import run.ClientRun;

/**
 *
 * @author admin
 */
public class HomeView extends javax.swing.JFrame {
    String statusCompetitor = "";
    /**
     * Creates new form HomeView
     */
    public void getUserOnline() {
        
    }
    
    public HomeView() {
        initComponents();
        
    }

    public void setStatusCompetitor (String status) {
        statusCompetitor = status;
    }
    
    public void setListUser(Vector vdata, Vector vheader) {
        tblUser.setModel(new DefaultTableModel(vdata, vheader));
    }
    
    public void resetTblUser () {
        DefaultTableModel dtm = (DefaultTableModel) tblUser.getModel();
        dtm.setRowCount(0);
    }
    
    public void setUsername(String username) {
        infoUsername.setText("Hello: " + username);
    }
    
    public void setUserScore(float score) {
        infoUserScore.setText("Score: " + score);
    }
    
    public void showDialogAcceptInvite(String userSelected){
        switch (statusCompetitor) {
            case "ONLINE" -> ClientRun.socketHandler.inviteToPlay(userSelected);
            case "OFFLINE" -> JOptionPane.showMessageDialog(HomeView.this, "This user is offline." , "ERROR", JOptionPane.ERROR_MESSAGE);
            case "INGAME" -> JOptionPane.showMessageDialog(HomeView.this, "This user is in game." , "ERROR", JOptionPane.ERROR_MESSAGE);
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

        btnPlay = new javax.swing.JButton();
        btnMessage = new javax.swing.JButton();
        btnBXH = new javax.swing.JButton();
        btnHistory = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        infoUsername = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        infoUserScore = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnGetInfo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPlay.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        btnPlay.setText("Play");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });
        getContentPane().add(btnPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 71, 36));

        btnMessage.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        btnMessage.setText("Message");
        btnMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMessageActionPerformed(evt);
            }
        });
        getContentPane().add(btnMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, -1, 36));

        btnBXH.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        btnBXH.setText("Leaderboard");
        btnBXH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBXHActionPerformed(evt);
            }
        });
        getContentPane().add(btnBXH, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 130, 40));

        btnHistory.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        btnHistory.setText("History");
        btnHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryActionPerformed(evt);
            }
        });
        getContentPane().add(btnHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 130, 40));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("User online");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 210, 46));

        infoUsername.setBackground(new java.awt.Color(153, 255, 255));
        infoUsername.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        infoUsername.setForeground(new java.awt.Color(255, 255, 255));
        infoUsername.setText("Hello");
        infoUsername.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 51, 255)));
        getContentPane().add(infoUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 190, 26));

        tblUser.setBackground(new java.awt.Color(255, 204, 204));
        tblUser.setBorder(new javax.swing.border.MatteBorder(null));
        tblUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUser.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblUser.setRowHeight(30);
        tblUser.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblUser);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 250, 340));

        btnRefresh.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, 100, 36));

        infoUserScore.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        infoUserScore.setForeground(new java.awt.Color(255, 255, 255));
        infoUserScore.setText("Score");
        infoUserScore.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 51, 204)));
        getContentPane().add(infoUserScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 190, 26));

        btnLogout.setBackground(new java.awt.Color(153, 0, 0));
        btnLogout.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, 88, 36));

        btnGetInfo.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        btnGetInfo.setText("Info");
        btnGetInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetInfoActionPerformed(evt);
            }
        });
        getContentPane().add(btnGetInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, -1, 36));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/avatar.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 60, 60));

        btnExit.setBackground(new java.awt.Color(153, 0, 0));
        btnExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 460, 79, 34));

        jButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        jButton1.setText("Your Info");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 130, 40));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/images.png"))); // NOI18N
        background.setText("jLabel2");
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 510));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        int row = tblUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(HomeView.this, "You haven't chosen anyone yet! Please select one user." , "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String userSelected = String.valueOf(tblUser.getValueAt(row, 0));
            ClientRun.socketHandler.inviteToPlay(userSelected);
            // check user online/in game
//            ClientRun.socketHandler.checkStatusUser(userSelected);
//            showDialogAcceptInvite(userSelected);
//            switch (statusCompetitor) {
//                case "ONLINE" -> ClientRun.socketHandler.inviteToPlay(userSelected);
//                case "OFFLINE" -> JOptionPane.showMessageDialog(HomeView.this, "This user is offline." , "ERROR", JOptionPane.ERROR_MESSAGE);
//                case "INGAME" -> JOptionPane.showMessageDialog(HomeView.this, "This user is in game." , "ERROR", JOptionPane.ERROR_MESSAGE);
//            }
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMessageActionPerformed
        int row = tblUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(HomeView.this, "You haven't chosen anyone yet! Please select one user." , "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String userSelected = String.valueOf(tblUser.getValueAt(row, 0));
            System.out.println(userSelected);
            if (userSelected.equals(ClientRun.socketHandler.getLoginUser())) {
                JOptionPane.showMessageDialog(HomeView.this, "You can not chat yourself." , "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
               ClientRun.socketHandler.inviteToChat(userSelected);
            }
        }
    }//GEN-LAST:event_btnMessageActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // get UserOnline
        ClientRun.socketHandler.getListOnline();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        JFrame frame = new JFrame("Logout");
        if (JOptionPane.showConfirmDialog(frame, "Confirm if you want Logout", "Logout", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            ClientRun.socketHandler.logout();
            
        } 
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnGetInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetInfoActionPerformed
        int row = tblUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(HomeView.this, "You haven't chosen anyone yet! Please select one user." , "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String userSelected = String.valueOf(tblUser.getValueAt(row, 0));
            System.out.println("Chọn user: " + userSelected);
            if (userSelected.equals(ClientRun.socketHandler.getLoginUser())) {
                JOptionPane.showMessageDialog(HomeView.this, "You can not see yourself." , "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
               ClientRun.socketHandler.getInfoUser(userSelected);
            }
        }
    }//GEN-LAST:event_btnGetInfoActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        JFrame frame = new JFrame("EXIT");
        if (JOptionPane.showConfirmDialog(frame, "Confirm if you want exit", "EXIT", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            ClientRun.socketHandler.close();
            System.exit(0);
        } 
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnBXHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBXHActionPerformed
        // TODO add your handling code here:
        ClientRun.socketHandler.getRankList();
    }//GEN-LAST:event_btnBXHActionPerformed

    private void btnHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryActionPerformed
        // TODO add your handling code here:
        ClientRun.socketHandler.getHistoryList();
    }//GEN-LAST:event_btnHistoryActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ClientRun.socketHandler.getInfoUser(ClientRun.socketHandler.getLoginUser());
    }//GEN-LAST:event_jButton1ActionPerformed
   
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnBXH;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnGetInfo;
    private javax.swing.JButton btnHistory;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMessage;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel infoUserScore;
    private javax.swing.JLabel infoUsername;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblUser;
    // End of variables declaration//GEN-END:variables
}
