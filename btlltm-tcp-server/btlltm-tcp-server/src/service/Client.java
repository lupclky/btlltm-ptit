/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controller.UserController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import run.ServerRun;
import helper.Question;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class Client implements Runnable {
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    String loginUser;
    Client cCompetitor;
    private int score = 0; // điểm của người chơi này
    private int round = 0; // đếm số ván đã chơi
    private boolean isPlaying = false; // kiểm tra trạng thái đang chơi
    
//    ArrayList<Client> clients
    Room joinedRoom; // if == null => chua vao phong nao het

    public Client(Socket s) throws IOException {
        this.s = s;

        // obtaining input and output streams 
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {

        String received;
        boolean running = true;

        while (!ServerRun.isShutDown) {
            try {
                // receive the request from client
                received = dis.readUTF();

                System.out.println(received);
                String type = received.split(";")[0];
               
                switch (type) {
                    case "LOGIN":
                        onReceiveLogin(received);
                        break;
                    case "REGISTER":
                        onReceiveRegister(received);
                        break;
                    case "GET_LIST_ONLINE":
                        onReceiveGetListOnline();
                        break;
                    case "GET_INFO_USER":
                        onReceiveGetInfoUser(received);
                        break;
                    case "LOGOUT":
                        onReceiveLogout();
                        break;  
                    case "CLOSE":
                        onReceiveClose();
                        break; 
                    // chat
                    case "INVITE_TO_CHAT":
                        onReceiveInviteToChat(received);
                        break;
                    case "ACCEPT_MESSAGE":
                        onReceiveAcceptMessage(received);
                        break;
                    case "NOT_ACCEPT_MESSAGE":
                        onReceiveNotAcceptMessage(received);
                        break;
                    case "LEAVE_TO_CHAT":
                        onReceiveLeaveChat(received);
                        break;
                    case "CHAT_MESSAGE":
                        onReceiveChatMessage(received);
                        break;
                    // play
                    case "INVITE_TO_PLAY":
                        onReceiveInviteToPlay(received);
                        break;
                    case "ACCEPT_PLAY":
                        onReceiveAcceptPlay(received);
                        break;
                    case "NOT_ACCEPT_PLAY":
                        onReceiveNotAcceptPlay(received);
                        break;
                    case "LEAVE_TO_GAME":
                        onReceiveLeaveGame(received);
                        break;
                    case "CHECK_STATUS_USER":
                        onReceiveCheckStatusUser(received);
                        break;
                    case "START_GAME":
                        onReceiveStartGame(received);
                        break;
                    case "SUBMIT_RESULT":
                        onReceiveSubmitResult(received);
                        break;
                    case "ASK_PLAY_AGAIN":
                        onReceiveAskPlayAgain(received);
                        break;
                        
                    case "EXIT":
                        running = false;
                }

            } catch (IOException ex) {

                // leave room if needed
//                onReceiveLeaveRoom("");
                break;
            } catch (SQLException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            // closing resources 
            this.s.close();
            this.dis.close();
            this.dos.close();
            System.out.println("- Client disconnected: " + s);

            // remove from clientManager
            ServerRun.clientManager.remove(this);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // send data fucntions
    public String sendData(String data) {
        try {
            this.dos.writeUTF(data);
            return "success";
        } catch (IOException e) {
            System.err.println("Send data failed!");
            return "failed;" + e.getMessage();
        }
    }
    
    private void onReceiveLogin(String received) {
        // get email / password from data
        String[] splitted = received.split(";");
        String username = splitted[1];
        String password = splitted[2];

        // check login
        String result = new UserController().login(username, password);

        if (result.split(";")[0].equals("success")) {
            // set login user
            this.loginUser = username;
        }
        
        // send result
        sendData("LOGIN" + ";" + result);
        onReceiveGetListOnline();
    }
    
    private void onReceiveRegister(String received) {
        // get email / password from data
        String[] splitted = received.split(";");
        String username = splitted[1];
        String password = splitted[2];

        // reigster
        String result = new UserController().register(username, password);

        // send result
        sendData("REGISTER" + ";" + result);
    }
    
    private void onReceiveGetListOnline() {
        String result = ServerRun.clientManager.getListUseOnline();
        
        // send result
        String msg = "GET_LIST_ONLINE" + ";" + result;
        ServerRun.clientManager.broadcast(msg);
    }
    
    private void onReceiveGetInfoUser(String received) {
        String[] splitted = received.split(";");
        String username = splitted[1];
        // get info user
        String result = new UserController().getInfoUser(username);
        
        String status = "";
        Client c = ServerRun.clientManager.find(username);
        if (c == null) {
            status = "Offline";
        } else {
            if (c.getJoinedRoom() == null) {
                status = "Online";
            } else {
                status = "In Game";
            }
        }
                
        // send result
        sendData("GET_INFO_USER" + ";" + result + ";" + status);
    }
    
    private void onReceiveLogout() {
        this.loginUser = null;
        // send result
        sendData("LOGOUT" + ";" + "success");
        onReceiveGetListOnline();
    }
    
    private void onReceiveInviteToChat(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // send result
        String msg = "INVITE_TO_CHAT;" + "success;" + userHost + ";" + userInvited;
        ServerRun.clientManager.sendToAClient(userInvited, msg);
    }
    
    private void onReceiveAcceptMessage(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // send result
        String msg = "ACCEPT_MESSAGE;" + "success;" + userHost + ";" + userInvited;
        ServerRun.clientManager.sendToAClient(userHost, msg);
    }      
      
    private void onReceiveNotAcceptMessage(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // send result
        String msg = "NOT_ACCEPT_MESSAGE;" + "success;" + userHost + ";" + userInvited;
        ServerRun.clientManager.sendToAClient(userHost, msg);
    }      
    
    private void onReceiveLeaveChat(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // send result
        String msg = "LEAVE_TO_CHAT;" + "success;" + userHost + ";" + userInvited;
        ServerRun.clientManager.sendToAClient(userInvited, msg);
    }      
    
    private void onReceiveChatMessage(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        String message = splitted[3];
        
        // send result
        String msg = "CHAT_MESSAGE;" + "success;" + userHost + ";" + userInvited + ";" + message;
        ServerRun.clientManager.sendToAClient(userInvited, msg);
    }    
    
    private void onReceiveInviteToPlay(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // create new room
        joinedRoom = ServerRun.roomManager.createRoom();
        // add client
        Client c = ServerRun.clientManager.find(loginUser);
        joinedRoom.addClient(this);
        cCompetitor = ServerRun.clientManager.find(userInvited);
        
        // send result
        String msg = "INVITE_TO_PLAY;" + "success;" + userHost + ";" + userInvited + ";" + joinedRoom.getId();
        ServerRun.clientManager.sendToAClient(userInvited, msg);
        System.out.println("userInvited: " + userInvited);
    }
    
    private void onReceiveAcceptPlay(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        String roomId = splitted[3];
        
        Room room = ServerRun.roomManager.find(roomId);
        joinedRoom = room;
        joinedRoom.addClient(this);
        
        cCompetitor = ServerRun.clientManager.find(userHost);
        
        // send result
        String msg = "ACCEPT_PLAY;" + "success;" + userHost + ";" + userInvited + ";" + joinedRoom.getId();
        ServerRun.clientManager.sendToAClient(userHost, msg);
        
    }      
      
    private void onReceiveNotAcceptPlay(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        String roomId = splitted[3];
        
        // userHost out room
        ServerRun.clientManager.find(userHost).setJoinedRoom(null);
        // Delete competitor of userhost
        ServerRun.clientManager.find(userHost).setcCompetitor(null);
        
        // delete room
        Room room = ServerRun.roomManager.find(roomId);
        ServerRun.roomManager.remove(room);
        
        // send result
        String msg = "NOT_ACCEPT_PLAY;" + "success;" + userHost + ";" + userInvited + ";" + room.getId();
        ServerRun.clientManager.sendToAClient(userHost, msg);
    }      
    
    private void onReceiveLeaveGame(String received) throws SQLException {
        String[] splitted = received.split(";");
        String user1 = splitted[1];
        String user2 = splitted[2];
        String roomId = splitted[3];
        
        joinedRoom.userLeaveGame(user1);
        
        this.cCompetitor = null;
        this.joinedRoom = null;
        
        // delete room
        Room room = ServerRun.roomManager.find(roomId);
        ServerRun.roomManager.remove(room);
        
        // userHost out room
        Client c = ServerRun.clientManager.find(user2);
        c.setJoinedRoom(null);
        // Delete competitor of userhost
        c.setcCompetitor(null);
        
        // send result
        String msg = "LEAVE_TO_GAME;" + "success;" + user1 + ";" + user2;
        ServerRun.clientManager.sendToAClient(user2, msg);
    }      
    
    private void onReceiveCheckStatusUser(String received) {
        String[] splitted = received.split(";");
        String username = splitted[1];
        
        String status = "";
        Client c = ServerRun.clientManager.find(username);
        if (c == null) {
            status = "OFFLINE";
        } else {
            if (c.getJoinedRoom() == null) {
                status = "ONLINE";
            } else {
                status = "INGAME";
            }
        }
        // send result
//        sendData("CHECK_STATUS_USER" + ";" + username + ";" + status);
        // cần phải gửi đúng người được mời
        ServerRun.clientManager.sendToAClient(username, "CHECK_STATUS_USER" + ";" + username + ";" + status);
    }
            
private void onReceiveStartGame(String received) {
        String[] splitted = received.split(";");
        String user1 = splitted[1];
        String user2 = splitted[2];
        String roomId = splitted[3];

        // Gửi 3 câu hỏi cho 3 ván đấu
        int question1 = Question.renQuestion();
        int question2 = Question.renQuestion();
        int question3 = Question.renQuestion();

        String data = "START_GAME;success;" + roomId + ";" + Integer.toString(question1)+ ";" + Integer.toString(question2) + ";" + Integer.toString(question3);
        
        joinedRoom.resetRoom();
        joinedRoom.broadcast(data); // Gửi câu hỏi
        joinedRoom.startGame(); // Bắt đầu game
        
        isPlaying = true;
        startCountdown(); // Bắt đầu đếm ngược
    }
 
    // Xử lý kết quả tính
private void onReceiveSubmitResult(String received) throws SQLException {
        String[] splitted = received.split(";");
        String user1 = splitted[1];
        String user2 = splitted[2];
        String roomId = splitted[3];
        String scoreUser = splitted[4];
        String timeUser = splitted[5];
        System.out.print("ScoreUser = " + scoreUser);
        
        if (user1.equals(joinedRoom.getClient1().getLoginUser())) {
            joinedRoom.setResultClient1(received);
        } else if (user1.equals(joinedRoom.getClient2().getLoginUser())) {
            joinedRoom.setResultClient2(received);
        }
//        String user1 = splitted[1];
//        String result = splitted[4]; // Giả định kết quả là "correct" hoặc "incorrect"
//        
//        if (result.equals("correct")) {
//            this.score += 10; // +10 điểm cho mỗi câu đúng
//        }
//
//        this.round++; // Tăng số ván chơi lên 1
//
//        if (this.round < 3) {
//            // Nếu chưa đủ 3 ván, tiếp tục cho ván sau
//            int nextQuestion = Question.renQuestion(); 
//            String data = "START_NEXT_ROUND;" + nextQuestion;
//            joinedRoom.broadcast(data); // Gửi câu hỏi tiếp theo
//        } else {
            // Nếu đã đủ 3 ván, tính điểm chung cuộc
        if(joinedRoom.getResultClient1()!= null && joinedRoom.getResultClient2()!= null){
            String finalResult = "RESULT_GAME;success;" + scoreUser + ";" 
                             + joinedRoom.handleResultClient() + ";"
                             + joinedRoom.getClient1().getLoginUser() + ";" 
                             + joinedRoom.getClient2().getLoginUser() + ";" 
                             + joinedRoom.getId();
            joinedRoom.broadcast(finalResult); // Gửi kết quả cuối cùng
            System.out.println("Ket quar cuoi" + finalResult);
        }
    }
    private void resetGame() {
        this.score = 0; // Reset điểm
        this.round = 0; // Reset số ván chơi
        this.isPlaying = false; // Đặt trạng thái là không chơi
        this.cCompetitor = null;
        this.joinedRoom = null;
    }
    private void startCountdown() {
        new Thread(() -> {
            int countdown = 30; // 30 giây cho mỗi ván
            while (countdown > 0 && isPlaying) {
                System.out.println("Time left: " + countdown + " seconds");
                try {
                    Thread.sleep(1000); // Đếm ngược 1 giây
                    countdown--;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (countdown == 0) {
                // Nếu hết giờ mà chưa nhận được kết quả, tự động kết thúc ván
                String timeoutMessage = "TIMEOUT;success;" + this.score;
                joinedRoom.broadcast(timeoutMessage); // Thông báo hết thời gian
                this.round++;
                
                if (this.round >= 3) {
                    resetGame(); // Kết thúc game sau 3 ván
                }
            }
        }).start();
    }
    
    
    private void onReceiveAskPlayAgain(String received) throws SQLException {
        String[] splitted = received.split(";");
        String reply = splitted[1];
        String user1 = splitted[2];

        if (user1.equals(joinedRoom.getClient1().getLoginUser())) {
            joinedRoom.setPlayAgainC1(reply);
        } else if (user1.equals(joinedRoom.getClient2().getLoginUser())) {
            joinedRoom.setPlayAgainC2(reply);
        }

        while (!joinedRoom.getWaitingTime().equals("00:00")) {
            try {
                Thread.sleep(1000); // Đợi phản hồi từ đối thủ
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String result = joinedRoom.handlePlayAgain();
        if (result.equals("YES")) {
            joinedRoom.broadcast("ASK_PLAY_AGAIN;YES;" + joinedRoom.getClient1().loginUser + ";" + joinedRoom.getClient2().loginUser);
        } else {
            joinedRoom.broadcast("ASK_PLAY_AGAIN;NO;");
            Room room = ServerRun.roomManager.find(joinedRoom.getId());
            ServerRun.roomManager.remove(room); // Xóa phòng khi không chơi lại
            resetGame(); // Reset trạng thái sau khi chơi xong
        }
    }
        
    
    // Close app
    private void onReceiveClose() {
        this.loginUser = null;
        ServerRun.clientManager.remove(this);
        onReceiveGetListOnline();
    }
    
    // Get set
    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public Client getcCompetitor() {
        return cCompetitor;
    }

    public void setcCompetitor(Client cCompetitor) {
        this.cCompetitor = cCompetitor;
    }

    public Room getJoinedRoom() {
        return joinedRoom;
    }

    public void setJoinedRoom(Room joinedRoom) {
        this.joinedRoom = joinedRoom;
    }
    
    
}
