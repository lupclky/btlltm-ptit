package service;

import controller.UserController;
import helper.CountDownTimer;
import helper.CustumDateTimeFormatter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import model.UserModel;
import run.ServerRun;

public class Room {
    String id;
    String time = "00:00";
    Client client1 = null, client2 = null;
    ArrayList<Client> clients = new ArrayList<>();
    
    boolean gameStarted = false;
    CountDownTimer matchTimer;
    CountDownTimer waitingTimer;
    
    UserController userController = new UserController();
    
    // "SUBMIT_RESULT;" + loginUser + ";" + competitor + ";" + roomIdPresent + ";" + "" + scoreUser +  ";" + time;
    String resultClient1;
    String resultClient2;
    
    String playAgainC1;
    String playAgainC2;
    String waitingTime= "00:00";

    public LocalDateTime startedTime;

    public Room(String id) {
        // room id
        this.id = id;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void startGame() {
        gameStarted = true;
        
        matchTimer = new CountDownTimer(27);
        System.out.println("Thoi gian cho phan hoi sau khi choi0");
        matchTimer.setTimerCallBack(
            null,
            (Callable) () -> {
                time = "" + CustumDateTimeFormatter.secondsToMinutes(matchTimer.getCurrentTick());
                System.out.print(time);
                if (time.equals("00:00")) {
//                    waitingClientTimer();
                    if (resultClient1 == null && resultClient2 == null) {
                        draw(0,0);
                        broadcast("RESULT_GAME;success;DRAW;" + client1.getLoginUser() + ";" + client2.getLoginUser() + ";" + id);
                    } 
                }
                return null;
            },
            1
        );
    }
    
//    public void waitingClientTimer() { // xử lý đoạn lỗi ở đây
//        waitingTimer = new CountDownTimer(31);
//        waitingTimer.setTimerCallBack(
//            null,
//            (Callable) () -> {
//                waitingTime = "" + CustumDateTimeFormatter.secondsToMinutes(waitingTimer.getCurrentTick());
//                System.out.print("waiting: " + waitingTime);
//                if (waitingTime.equals("00:00")) {
//                    if (playAgainC1 == null && playAgainC2 == null) {
//                        broadcast("ASK_PLAY_AGAIN;NO");
//                        deleteRoom();
//                    } 
//                }
//                return null;
//            },
//            1
//        );
//    }
    
    public void deleteRoom () {
        client1.setJoinedRoom(null);
        client1.setcCompetitor(null);
        client2.setJoinedRoom(null);
        client2.setcCompetitor(null);
        ServerRun.roomManager.remove(this);
    }
    
    public void resetRoom() {
        gameStarted = false;
        resultClient1 = null;
        resultClient2 = null;
        playAgainC1 = null;
        playAgainC2 = null;
        time = "00:00";
        waitingTime = "00:00";
    }
    
    public String handleResultClient() throws SQLException {
        int timeClient1 = 0; //split[5]
        int timeClient2 = 0;
        int scoreUser1 = -1;
        int scoreUser2 = -1;
        

        if(resultClient1 != null){
            String[] split = resultClient1.split(";");
            scoreUser1 = Integer.parseInt(split[4]);
            timeClient1 = Integer.parseInt(split[5]);
            
        }
        if(resultClient2 != null){
            String[] split = resultClient2.split(";");
            scoreUser2 = Integer.parseInt(split[4]);
            timeClient2 = Integer.parseInt(split[5]);
        }
        if(resultClient1 == null && resultClient2 == null){
            draw(0, 0);
            return "DRAW";
        }
        else if(resultClient1 != null && resultClient2 == null){
            if(scoreUser1 >0){
                client1Win(timeClient1, scoreUser1, scoreUser2);
                return client1.getLoginUser();
            }
            else{
                draw(0,0);
                return "DRAW";
            }
        }
        else if (resultClient1 == null && resultClient2 != null) {        
            if (scoreUser2 > 0) {
                client2Win(timeClient2, 0, scoreUser2);
                return client2.getLoginUser();
            } else {
                draw(0,0);
                return "DRAW";
            }
        }
        else if (resultClient1 != null && resultClient2 != null) {
            
            if (scoreUser1 > scoreUser2) {
                client1Win(timeClient1, scoreUser1, scoreUser2);
                return client1.getLoginUser();
            } else if (scoreUser1 < scoreUser2) {
                client2Win(timeClient2, scoreUser1, scoreUser2);
                return client2.getLoginUser();
            } else {
                draw(scoreUser1, scoreUser2);
                return "DRAW";
            }
        }
//        if(scoreUser1 == scoreUser2) return "DRAW";
//        else if(scoreUser1 > scoreUser2) return "WIN";
//        else return null;
        return null;
    }
    


    public void draw (int scoreUser1 , int scoreUser2) throws SQLException {
        UserModel user1 = new UserController().getUser(client1.getLoginUser());
        UserModel user2 = new UserController().getUser(client2.getLoginUser());
        
        user1.setDraw(user1.getDraw() + 1);
        user2.setDraw(user2.getDraw() + 1);
        
        user1.setScore(user1.getScore()+ 0.5f);
        user2.setScore(user2.getScore()+ 0.5f);
        
        int totalMatchUser1 = user1.getWin() + user1.getDraw() + user1.getLose();
        int totalMatchUser2 = user2.getWin() + user2.getDraw() + user2.getLose();
        
        float newAvgCompetitor1 = (totalMatchUser1 * user1.getAvgCompetitor() + user2.getScore()) / (totalMatchUser1 + 1);
        float newAvgCompetitor2 = (totalMatchUser2 * user1.getAvgCompetitor() + user1.getScore()) / (totalMatchUser2 + 1);
        
        user1.setAvgCompetitor(newAvgCompetitor1);
        user2.setAvgCompetitor(newAvgCompetitor2);
        
//        new UserController().updateUser(user1);
//        new UserController().updateUser(user2);
        userController.updateUser(user1);
        userController.updateUser(user2);
        userController.updateGame(client1.getLoginUser(), client2.getLoginUser(), scoreUser1, scoreUser2, "DRAW");
        userController.updateGame(client2.getLoginUser(), client1.getLoginUser(), scoreUser2, scoreUser1, "DRAW");
    }
    
    public void client1Win(int time, int scoreUser1, int scoreUser2) throws SQLException {
        UserModel user1 = new UserController().getUser(client1.getLoginUser());
        UserModel user2 = new UserController().getUser(client2.getLoginUser());
        
        user1.setWin(user1.getWin() + 1);
        user2.setLose(user2.getLose() + 1);
        
        user1.setScore(user1.getScore()+ 1);
        
        int totalMatchUser1 = user1.getWin() + user1.getDraw() + user1.getLose();
        int totalMatchUser2 = user2.getWin() + user2.getDraw() + user2.getLose();
        
        float newAvgCompetitor1 = (totalMatchUser1 * user1.getAvgCompetitor() + user2.getScore()) / (totalMatchUser1 + 1);
        float newAvgCompetitor2 = (totalMatchUser2 * user1.getAvgCompetitor() + user1.getScore()) / (totalMatchUser2 + 1);
        
        user1.setAvgCompetitor(newAvgCompetitor1);
        user2.setAvgCompetitor(newAvgCompetitor2);
        
        float newAvgTime1 = (totalMatchUser1 * user1.getAvgTime() + time) / (totalMatchUser1 + 1);
        System.out.println("newAvgTime1: " + newAvgTime1);
        user1.setAvgTime(newAvgTime1);
        
//        new UserController().updateUser(user1);
//        new UserController().updateUser(user2);
        userController.updateUser(user1);
        userController.updateUser(user2);
        userController.updateGame(client1.getLoginUser(), client2.getLoginUser(), scoreUser1, scoreUser2, "WIN");
        userController.updateGame(client2.getLoginUser(), client1.getLoginUser(), scoreUser2, scoreUser1, "LOOSE");
    }
    
    public void client2Win(int time, int scoreUser1, int scoreUser2) throws SQLException {
        UserModel user1 = new UserController().getUser(client1.getLoginUser());
        UserModel user2 = new UserController().getUser(client2.getLoginUser());
        
        user2.setWin(user2.getWin() + 1);
        user1.setLose(user1.getLose() + 1);
        
        user2.setScore(user2.getScore()+ 1);
        
        int totalMatchUser1 = user1.getWin() + user1.getDraw() + user1.getLose();
        int totalMatchUser2 = user2.getWin() + user2.getDraw() + user2.getLose();
        
        float newAvgCompetitor1 = (totalMatchUser1 * user1.getAvgCompetitor() + user2.getScore()) / (totalMatchUser1 + 1);
        float newAvgCompetitor2 = (totalMatchUser2 * user1.getAvgCompetitor() + user1.getScore()) / (totalMatchUser2 + 1);
        
        user1.setAvgCompetitor(newAvgCompetitor1);
        user2.setAvgCompetitor(newAvgCompetitor2);
        
        float newAvgTime2 = (totalMatchUser2 * user2.getAvgTime() + time) / (totalMatchUser2 + 1);
        System.out.println("newAvgTime2: " + newAvgTime2);
        user2.setAvgTime(newAvgTime2);
        
//        new UserController().updateUser(user1);
//        new UserController().updateUser(user2);
        userController.updateUser(user1);
        userController.updateUser(user2);
        userController.updateGame(client1.getLoginUser(), client2.getLoginUser(), scoreUser1, scoreUser2, "LOOSE");
        userController.updateGame(client2.getLoginUser(), client1.getLoginUser(), scoreUser2, scoreUser1, "WIN");
    }
    
    public void userLeaveGame (String username) throws SQLException {
        if (client1.getLoginUser().equals(username)) {
            client2Win(0,0,0);
        } else if (client2.getLoginUser().equals(username)) {
            client1Win(0,0,0);
        }
    }
    
    public String handlePlayAgain () {
        if (playAgainC1 == null || playAgainC2 == null) {
            return "NO";
        } else if (playAgainC1.equals("YES") && playAgainC2.equals("YES")) {
            return "YES";
        } else if (playAgainC1.equals("NO") && playAgainC2.equals("YES")) {
//            ServerRun.clientManager.sendToAClient(client2.getLoginUser(), "ASK_PLAY_AGAIN;NO");
//            deleteRoom();
            return "NO";
        } else if (playAgainC2.equals("NO") && playAgainC2.equals("YES")) {
//            ServerRun.clientManager.sendToAClient(client1.getLoginUser(), "ASK_PLAY_AGAIN;NO");
//            deleteRoom();
            return "NO";
        } else {
            return "NO";
        }
    }
    
    // add/remove client
    public boolean addClient(Client c) {
        if (!clients.contains(c)) {
            clients.add(c);
            if (client1 == null) {
                client1 = c;
            } else if (client2 == null) {
                client2 = c;
            }
            return true;
        }
        return false;
    }

    public boolean removeClient(Client c) {
        if (clients.contains(c)) {
            clients.remove(c);
            return true;
        }
        return false;
    }

    // broadcast messages
    public void broadcast(String msg) {
        clients.forEach((c) -> {
            c.sendData(msg);
            System.out.println("SENT BROADCAST: " + msg);
        });
    }
    
    public Client find(String username) {
        for (Client c : clients) {
            if (c.getLoginUser()!= null && c.getLoginUser().equals(username)) {
                return c;
            }
        }
        return null;
    }

    // gets sets
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient1() {
        return client1;
    }

    public void setClient1(Client client1) {
        this.client1 = client1;
    }

    public Client getClient2() {
        return client2;
    }

    public void setClient2(Client client2) {
        this.client2 = client2;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
    
    public int getSizeClient() {
        return clients.size();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResultClient1() {
        return resultClient1;
    }

    public void setResultClient1(String resultClient1) {
        this.resultClient1 = resultClient1;
    }

    public String getResultClient2() {
        return resultClient2;
    }

    public void setResultClient2(String resultClient2) {
        this.resultClient2 = resultClient2;
    }

    public String getPlayAgainC1() {
        return playAgainC1;
    }

    public void setPlayAgainC1(String playAgainC1) {
        this.playAgainC1 = playAgainC1;
    }

    public String getPlayAgainC2() {
        return playAgainC2;
    }

    public void setPlayAgainC2(String playAgainC2) {
        this.playAgainC2 = playAgainC2;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }
    
}