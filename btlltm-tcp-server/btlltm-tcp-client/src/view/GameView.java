/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.concurrent.Callable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import run.ClientRun;
import helper.*;
import java.util.Enumeration;
import javax.swing.AbstractButton;

/**
 *
 * @author admin
 */
public class GameView extends javax.swing.JFrame {

    int currentRound = 0;
    int score = 0;
    String competitor = "";
    CountDownTimer matchTimer;
    CountDownTimer waitingClientTimer;
    int[] redAnswer = new int[4];
    boolean done = false;

    boolean answer = false;
    /**
     * Creates new form GameView
     */
    public GameView() {
        initComponents();
        
//        panel.setVisible(false); // hiện danh sách câu hỏi
        panel.setVisible(true);
        panelPlayAgain.setVisible(false);
        btnSubmit.setVisible(false);
        pbgTimer.setVisible(false); // bộ đếm thời gian
        jTextField1.setText("" + currentRound);
//        pbgTimer.setVisible(true);
        
        // close window event
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(GameView.this, "Are you sure want to leave game? You will lose?", "LEAVE GAME", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
                    ClientRun.socketHandler.leaveGame(competitor);
                    ClientRun.socketHandler.setRoomIdPresent(null);
                    dispose();
                } 
            }
        });
    }
    
    public void setWaitingRoom () {
        panel.setVisible(false);
        btnSubmit.setVisible(false);
        pbgTimer.setVisible(false);
        btnStart.setVisible(false);
        lbWaiting.setText("waiting competitor...");
        waitingReplyClient();
    }
    
    public void showAskPlayAgain (String msg) {
        panelPlayAgain.setVisible(true);
        lbResult.setText(msg);
    }
    
    public void hideAskPlayAgain () {
        panelPlayAgain.setVisible(false);
    }
    
    public void setInfoPlayer (String username) {
        competitor = username;
        infoPLayer.setText("Play game with: " + username);
    }
    
    public void setStateHostRoom () {
        answer = false;
        btnStart.setVisible(true);
        lbWaiting.setVisible(false);
    }
    
    public void setStateUserInvited () {
        answer = false;
        btnStart.setVisible(false);
        lbWaiting.setVisible(true);
    }
    
    public void afterSubmit() {
        panel.setVisible(false);
        btnSubmit.setVisible(false);
        lbWaiting.setVisible(true);
        lbWaiting.setText("Waiting result from server...");
    }
    
    public void setStartGame (int matchTimeLimit, int answer1, int answer2, int answer3) {
        answer = false;
        diem.setText(Integer.toString(score));
        redAnswer[1] = answer1;
        redAnswer[2] = answer2;
        redAnswer[3] = answer3;
//        buttonGroup1.clearSelection();
//        buttonGroup2.clearSelection();
//        buttonGroup3.clearSelection();
//        buttonGroup4.clearSelection();
        
        btnStart.setVisible(false);
        lbWaiting.setVisible(false);
        panel.setVisible(true);
        btnSubmit.setVisible(true);
        pbgTimer.setVisible(true);
        marbel1.setVisible(true);  // Hide all balls initially
        marbel2.setVisible(true);
        marbel3.setVisible(true);
        cup1.setVisible(true);
        cup2.setVisible(true);
        cup3.setVisible(true);
        
        matchTimer = new CountDownTimer(matchTimeLimit);
        matchTimer.setTimerCallBack(
                // end match callback
                null,
                // tick match callback
                (Callable) () -> {
                    pbgTimer.setValue(100 * matchTimer.getCurrentTick() / matchTimer.getTimeLimit());
                    pbgTimer.setString("" + CustumDateTimeFormatter.secondsToMinutes(matchTimer.getCurrentTick()));
                    if (pbgTimer.getString().equals("00:00")) {
                        afterSubmit();
                    }
                    return null;
                },
                // tick interval
                1
        );
        currentRound = 1;
        if (redAnswer[currentRound] == 1) {
            marbel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png"));
            marbel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
            marbel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
        } else if (redAnswer[currentRound] == 2) {
            marbel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
            marbel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png"));
            marbel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
        } else if (redAnswer[currentRound] == 3) {
            marbel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
            marbel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
            marbel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png"));
        }
    }
    
    public void waitingReplyClient () {
        waitingClientTimer = new CountDownTimer(10);
        waitingClientTimer.setTimerCallBack(
                null,
                (Callable) () -> {
                    lbWaitingTimer.setText("" + CustumDateTimeFormatter.secondsToMinutes(waitingClientTimer.getCurrentTick()));
                    if (lbWaitingTimer.getText().equals("00:00") && answer == false) {
                        hideAskPlayAgain();
                    }
                    return null;
                },
                1
        );
    }
    
    public boolean getDone(){
        return this.done;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
    
    // Xóa đoạn này
    public String getSelectedButton1() {  
        for (Enumeration<AbstractButton> buttons = buttonGroup1.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                    return button.getText();
            }
        }
        return null;
    }
    
    public String getSelectedButton2() {  
        for (Enumeration<AbstractButton> buttons = buttonGroup2.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                    return button.getText();
            }
        }
        return null;
    }
    
    public String getSelectedButton3() {  
        for (Enumeration<AbstractButton> buttons = buttonGroup3.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                    return button.getText();
            }
        }
        return null;
    }
    
    public String getSelectedButton4() {  
        for (Enumeration<AbstractButton> buttons = buttonGroup4.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                    return button.getText();
            }
        }
        return null;
    }
    
    public void pauseTime () {
        // pause timer
        matchTimer.pause();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jFrame1 = new javax.swing.JFrame();
        infoPLayer = new javax.swing.JLabel();
        btnLeaveGame = new javax.swing.JButton();
        pbgTimer = new javax.swing.JProgressBar();
        btnSubmit = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        lbWaiting = new javax.swing.JLabel();
        panelPlayAgain = new javax.swing.JPanel();
        lbWaitingTimer = new javax.swing.JLabel();
        btnYes = new javax.swing.JButton();
        btnNo = new javax.swing.JButton();
        lbResult = new javax.swing.JLabel();
        panel = new javax.swing.JLayeredPane();
        cup1 = new javax.swing.JLabel();
        marbel1 = new javax.swing.JLabel();
        cup2 = new javax.swing.JLabel();
        marbel2 = new javax.swing.JLabel();
        cup3 = new javax.swing.JLabel();
        marbel3 = new javax.swing.JLabel();
        btnNextRound = new javax.swing.JButton();
        jLabelHienDiem = new javax.swing.JLabel();
        diem = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        infoPLayer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        infoPLayer.setText("Play game with:");

        btnLeaveGame.setBackground(new java.awt.Color(255, 51, 51));
        btnLeaveGame.setForeground(new java.awt.Color(255, 255, 255));
        btnLeaveGame.setText("Leave Game");
        btnLeaveGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveGameActionPerformed(evt);
            }
        });

        pbgTimer.setStringPainted(true);

        btnSubmit.setBackground(new java.awt.Color(255, 102, 102));
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnStart.setBackground(new java.awt.Color(0, 255, 255));
        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        lbWaiting.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbWaiting.setText("Waiting host start game....");

        panelPlayAgain.setBorder(javax.swing.BorderFactory.createTitledBorder("Question?"));

        lbWaitingTimer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbWaitingTimer.setForeground(new java.awt.Color(255, 204, 51));
        lbWaitingTimer.setText("00:00");

        btnYes.setBackground(new java.awt.Color(204, 255, 204));
        btnYes.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnYes.setText("Yes");
        btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYesActionPerformed(evt);
            }
        });

        btnNo.setBackground(new java.awt.Color(255, 153, 153));
        btnNo.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNo.setText("No");
        btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoActionPerformed(evt);
            }
        });

        lbResult.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbResult.setForeground(new java.awt.Color(255, 204, 51));
        lbResult.setText("Do you want to play again? ");

        javax.swing.GroupLayout panelPlayAgainLayout = new javax.swing.GroupLayout(panelPlayAgain);
        panelPlayAgain.setLayout(panelPlayAgainLayout);
        panelPlayAgainLayout.setHorizontalGroup(
            panelPlayAgainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPlayAgainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbResult, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbWaitingTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(btnYes, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnNo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        panelPlayAgainLayout.setVerticalGroup(
            panelPlayAgainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPlayAgainLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelPlayAgainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelPlayAgainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbResult, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbWaitingTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNo, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnYes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel.setBackground(new java.awt.Color(255, 231, 255));
        panel.setBorder(new javax.swing.border.MatteBorder(null));
        panel.setForeground(new java.awt.Color(102, 102, 0));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cup1.setBackground(new java.awt.Color(255, 102, 51));
        cup1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cup1.setForeground(new java.awt.Color(102, 102, 255));
        cup1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a yellow cup (1).png")); // NOI18N
        cup1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cup1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cup1MouseClicked(evt);
            }
        });
        panel.add(cup1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 150, 160));

        marbel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png")); // NOI18N
        marbel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        panel.add(marbel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 70, 60));

        cup2.setBackground(new java.awt.Color(255, 102, 51));
        cup2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cup2.setForeground(new java.awt.Color(102, 102, 255));
        cup2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a yellow cup (1).png")); // NOI18N
        cup2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cup2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cup2MouseClicked(evt);
            }
        });
        panel.add(cup2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 150, 160));

        marbel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png")); // NOI18N
        marbel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        panel.add(marbel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 70, 60));

        cup3.setBackground(new java.awt.Color(255, 102, 51));
        cup3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cup3.setForeground(new java.awt.Color(102, 102, 255));
        cup3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a yellow cup (1).png")); // NOI18N
        cup3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cup3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cup3MouseClicked(evt);
            }
        });
        panel.add(cup3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 150, 160));

        marbel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png")); // NOI18N
        marbel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        panel.add(marbel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 70, 60));

        btnNextRound.setBackground(new java.awt.Color(255, 102, 102));
        btnNextRound.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNextRound.setText("Next round");
        btnNextRound.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNextRoundMouseClicked(evt);
            }
        });
        btnNextRound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextRoundActionPerformed(evt);
            }
        });

        jLabelHienDiem.setBackground(new java.awt.Color(255, 153, 153));
        jLabelHienDiem.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabelHienDiem.setText("Score");

        diem.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        diem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        diem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diemActionPerformed(evt);
            }
        });

        jTextField1.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pbgTimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(infoPLayer, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLeaveGame, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelPlayAgain, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(32, 32, 32))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbWaiting, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(82, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabelHienDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(diem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnNextRound)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(infoPLayer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLeaveGame, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pbgTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNextRound)
                    .addComponent(jLabelHienDiem)
                    .addComponent(diem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbWaiting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(panelPlayAgain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pbgTimer.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLeaveGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveGameActionPerformed
        if (JOptionPane.showConfirmDialog(GameView.this, "Are you sure want to leave game? You will lose?", "LEAVE GAME", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            ClientRun.socketHandler.leaveGame(competitor);
            ClientRun.socketHandler.setRoomIdPresent(null);
            dispose();
        } 
    }//GEN-LAST:event_btnLeaveGameActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        ClientRun.socketHandler.startGame(competitor);
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        System.out.println("Game with competitor: " + competitor);
        System.out.println("Chơi xong rồi");
        done = true;
        ClientRun.socketHandler.submitResult(competitor);
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoActionPerformed
        ClientRun.socketHandler.notAcceptPlayAgain();
        answer = true;
        hideAskPlayAgain();
    }//GEN-LAST:event_btnNoActionPerformed

    private void btnYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYesActionPerformed
        ClientRun.socketHandler.acceptPlayAgain();
        answer = true;
        hideAskPlayAgain();
    }//GEN-LAST:event_btnYesActionPerformed

    private void cup1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cup1MouseClicked
        // TODO add your handling code here:
        currentRound++;
        System.out.println("currentRound" + currentRound);
        cup2.setEnabled(false);
        cup3.setEnabled(false);
        if (redAnswer[currentRound-1] == 1) {
            score++;  // Nếu cốc chứa bi đỏ, tăng điểm
            diem.setText(Integer.toString(score)); // Cập nhật TextField hiện điểm
            
        }
        cup1.setVisible(false); // Ẩn cốc sau khi chọn
//        new Thread(() -> {
//            int y = cup1.getY(); // Lấy tọa độ y hiện tại của cái cốc
//            try {
//                // Di chuyển cốc lên trên 50px (thực hiện từ từ)
//                while (y > cup1.getY() - 50) {
//                    y -= 2; // Mỗi lần di chuyển lên 5px
//                    cup1.setLocation(cup1.getX(), y); // Cập nhật vị trí
//                    Thread.sleep(20); // Tạm dừng 50ms giữa mỗi lần di chuyển
//                }
//                // Sau khi di chuyển xong, ẩn cái cốc đi
//                cup1.setVisible(false);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        }).start();
    }//GEN-LAST:event_cup1MouseClicked

    private void cup2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cup2MouseClicked
        // TODO add your handling code here:
        currentRound++;
        System.out.println("currentRound" + currentRound);
        cup1.setEnabled(false);
        cup3.setEnabled(false);
        if (redAnswer[currentRound-1] == 2) {
            score++;
            diem.setText(Integer.toString(score));
            
        }
        cup2.setVisible(false);
//        new Thread(() -> {
//            int y = cup2.getY(); // Lấy tọa độ y hiện tại của cái cốc
//            try {
//                // Di chuyển cốc lên trên 50px (thực hiện từ từ)
//                while (y > cup2.getY() - 50) {
//                    y -= 2; // Mỗi lần di chuyển lên 5px
//                    cup2.setLocation(cup2.getX(), y); // Cập nhật vị trí
//                    Thread.sleep(20); // Tạm dừng 50ms giữa mỗi lần di chuyển
//                }
//                // Sau khi di chuyển xong, ẩn cái cốc đi
//                cup2.setVisible(false);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        }).start();
    }//GEN-LAST:event_cup2MouseClicked

    private void cup3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cup3MouseClicked
        // TODO add your handling code here:
        currentRound++;
        System.out.println("currentRound" + currentRound);
        cup2.setEnabled(false);
        cup1.setEnabled(false);
        if (redAnswer[currentRound-1] == 3) {
            score++;  // Nếu cốc chứa bi đỏ, tăng điểm
            diem.setText(Integer.toString(score)); // Cập nhật TextField hiện điểm
        }
        cup3.setVisible(false);
//        new Thread(() -> {
//            int y = cup3.getY(); // Lấy tọa độ y hiện tại của cái cốc
//            try {
//                // Di chuyển cốc lên trên 50px (thực hiện từ từ)
//                while (y > cup3.getY() - 50) {
//                    y -= 2; // Mỗi lần di chuyển lên 5px
//                    cup3.setLocation(cup3.getX(), y); // Cập nhật vị trí
//                    Thread.sleep(20); // Tạm dừng 50ms giữa mỗi lần di chuyển
//                }
//                // Sau khi di chuyển xong, ẩn cái cốc đi
//                cup3.setVisible(false);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        }).start();
    }//GEN-LAST:event_cup3MouseClicked

    private void btnNextRoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextRoundActionPerformed
        // TODO add your handling code here:
        diem.setText(Integer.toString(score));
        jTextField1.setText("" + currentRound);
        System.out.println("currentRound" + currentRound);
        if (currentRound > 3) {
            JOptionPane.showMessageDialog(this, "Bạn đã hoàn thành 3 vòng!\nĐiểm của bạn là: " + score, 
                    "Điểm số", JOptionPane.INFORMATION_MESSAGE);
//            cup1.
//            currentRound = 1; // Reset lại vòng chơi
//            score = 0; // Reset điểm số
            done = true;
            btnNextRound.setEnabled(false);
            btnNextRound.setVisible(false);
            return;
        }
        cup1.setEnabled(true);
        cup2.setEnabled(true);
        cup3.setEnabled(true);
        cup1.setVisible(true);
        cup2.setVisible(true);
        cup3.setVisible(true);

        marbel1.setVisible(true);  // Hide all balls initially
        marbel2.setVisible(true);
        marbel3.setVisible(true);
        cup1.setLocation(60, 10);
        cup2.setLocation(270, 10);
        cup3.setLocation(510, 10);
        if (redAnswer[currentRound] == 1) {
            marbel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png"));
            marbel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
            marbel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));

        } else if (redAnswer[currentRound] == 2) {
            marbel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
            marbel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png"));
            marbel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));

        } else if (redAnswer[currentRound] == 3) {
            marbel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
            marbel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a black marbel.png"));
            marbel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\nguye\\Downloads\\a red marble.png"));
        }

        // Reset any other game-specific variables as needed
//        currentRound++;
//        System.out.println("curen= " + currentRound);
//        updateRoundLabel();
    }//GEN-LAST:event_btnNextRoundActionPerformed

    private void btnNextRoundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextRoundMouseClicked
        // TODO add your handling code here:
        btnNextRoundActionPerformed(null); 
    }//GEN-LAST:event_btnNextRoundMouseClicked

    private void diemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diemActionPerformed
        // TODO add your handling code here:
        diem.setText(Integer.toString(score));
    }//GEN-LAST:event_diemActionPerformed

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
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameView().setVisible(true);
            }
        });
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLeaveGame;
    private javax.swing.JButton btnNextRound;
    private javax.swing.JButton btnNo;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnYes;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JLabel cup1;
    private javax.swing.JLabel cup2;
    private javax.swing.JLabel cup3;
    private javax.swing.JTextField diem;
    private javax.swing.JLabel infoPLayer;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabelHienDiem;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbResult;
    private javax.swing.JLabel lbWaiting;
    private javax.swing.JLabel lbWaitingTimer;
    private javax.swing.JLabel marbel1;
    private javax.swing.JLabel marbel2;
    private javax.swing.JLabel marbel3;
    private javax.swing.JLayeredPane panel;
    private javax.swing.JPanel panelPlayAgain;
    public static javax.swing.JProgressBar pbgTimer;
    // End of variables declaration//GEN-END:variables
}
