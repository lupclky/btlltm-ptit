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
    
    String resultClient1;
    String resultClient2;
    
    String playAgainC1;
    String playAgainC2;
    String waitingTime = "00:00";
    int round = 0; // Số vòng hiện tại
    final int maxRounds = 3; // Tổng số vòng

    int scoreClient1 = 0; // Tổng điểm của Client1
    int scoreClient2 = 0; // Tổng điểm của Client2

    public LocalDateTime startedTime;

    public Room(String id) {
        this.id = id;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void startGame() {
        gameStarted = true;
        round = 1; // Bắt đầu vòng 1
        
<<<<<<< HEAD
        startRound(); // Bắt đầu ván đầu tiên
    }

    public void startRound() {
        if (round > maxRounds) {
            endGame(); // Kết thúc trò chơi sau 3 vòng
            return;
        }

        matchTimer = new CountDownTimer(30); // Mỗi ván kéo dài 30 giây
=======
        matchTimer = new CountDownTimer(31);
>>>>>>> parent of 37dc135 (NQ Hai Dang sửa toàn bộ)
        matchTimer.setTimerCallBack(
            null,
            (Callable) () -> {
                time = "" + CustumDateTimeFormatter.secondsToMinutes(matchTimer.getCurrentTick());
                System.out.println("Time for round " + round + ": " + time);

                if (time.equals("00:00")) {
                    sendAnswer();
                    waitForNextRound();
                }
                return null;
            },
            1
        );
    }
<<<<<<< HEAD

    public void sendAnswer() {
        // Gửi đáp án cho cả 2 client
        broadcast("SEND_ANSWER;" + client1.getLoginUser() + ";" + client2.getLoginUser() + ";" + id);

        // Cộng điểm cho mỗi client dựa trên kết quả của vòng
        if (resultClient1 != null && resultClient1.contains("correct")) {
            scoreClient1 += 10;
        }
        if (resultClient2 != null && resultClient2.contains("correct")) {
            scoreClient2 += 10;
        }
    }

    public void waitForNextRound() {
        waitingTimer = new CountDownTimer(3); // Thời gian chờ 3 giây trước khi vào vòng tiếp theo
=======
    
    public void waitingClientTimer() {
        waitingTimer = new CountDownTimer(12);
>>>>>>> parent of 37dc135 (NQ Hai Dang sửa toàn bộ)
        waitingTimer.setTimerCallBack(
            null,
            (Callable) () -> {
                waitingTime = "" + CustumDateTimeFormatter.secondsToMinutes(waitingTimer.getCurrentTick());
                System.out.println("Waiting: " + waitingTime);

                if (waitingTime.equals("00:00")) {
                    round++; // Sang vòng tiếp theo
                    startRound(); // Bắt đầu vòng mới
                }
                return null;
            },
            1
        );
    }

    public void endGame() {
        System.out.println("Game Over! Final Result.");
        
        // Xử lý kết quả cuối cùng sau 3 vòng
        String finalResult = "";
        try {
            finalResult = handleFinalResult();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Gửi kết quả cuối cùng
        broadcast("RESULT_GAME;final;" + finalResult + ";" + id);
        resetRoom(); // Đặt lại trạng thái phòng sau khi kết thúc game
    }
    public void userLeaveGame (String username) throws SQLException {
        if (client1.getLoginUser().equals(username)) {
            client2Win();
        } else if (client2.getLoginUser().equals(username)) {
            client1Win();
        }
    }
    public String handleFinalResult() throws SQLException {
        // Xác định người chiến thắng dựa trên tổng điểm sau 3 vòng
        if (scoreClient1 > scoreClient2) {
            client1Win();
            return client1.getLoginUser();
        } else if (scoreClient1 < scoreClient2) {
            client2Win();
            return client2.getLoginUser();
        } else {
            draw();
            return "DRAW";
        }
    }

    public void client1Win() throws SQLException {
        broadcast("RESULT_GAME;win;" + client1.getLoginUser() + ";" + id);
        new UserController().updateScore(client1.getLoginUser(), scoreClient1); // Cập nhật điểm cuối cùng của Client1
    }

    public void client2Win() throws SQLException {
        broadcast("RESULT_GAME;win;" + client2.getLoginUser() + ";" + id);
        new UserController().updateScore(client2.getLoginUser(), scoreClient2); // Cập nhật điểm cuối cùng của Client2
    }

    public void draw() {
        broadcast("RESULT_GAME;draw;");
        System.out.println("The game is a draw.");
    }

    public void resetRoom() {
        gameStarted = false;
        resultClient1 = null;
        resultClient2 = null;
        playAgainC1 = null;
        playAgainC2 = null;
        time = "00:00";
        waitingTime = "00:00";
        round = 0;
        scoreClient1 = 0;
        scoreClient2 = 0;
    }
<<<<<<< HEAD

    // Thêm/xóa client
=======
    
    public String handleResultClient() throws SQLException {
        int timeClient1 = 0;
        int timeClient2 = 0;
        
        if (resultClient1 != null) {
            String[] splitted1 = resultClient1.split(";");
            timeClient1 = Integer.parseInt(splitted1[13]);
        }
        if (resultClient2 != null) {
            String[] splitted2 = resultClient2.split(";");
            timeClient2 = Integer.parseInt(splitted2[13]);
        }
        
        if (resultClient1 == null & resultClient2 == null) {
            draw();
            return "DRAW";
        } else if (resultClient1 != null && resultClient2 == null) {
            if (calculateResult(resultClient1) > 0) {
                client1Win(timeClient1);
                return client1.getLoginUser();
            } else {
                draw();
                return "DRAW";
            }
        } else if (resultClient1 == null && resultClient2 != null) {
            if (calculateResult(resultClient2) > 0) {
                client2Win(timeClient2);
                return client2.getLoginUser();
            } else {
                draw();
                return "DRAW";
            }
        } else if (resultClient1 != null && resultClient2 != null) {
            int pointClient1 = calculateResult(resultClient1);
            int pointClient2 = calculateResult(resultClient2);
            
            if (pointClient1 > pointClient2) {
                client1Win(timeClient1);
                return client1.getLoginUser();
            } else if (pointClient1 < pointClient2) {
                client2Win(timeClient2);
                return client2.getLoginUser();
            } else {
                draw();
                return "DRAW";
            }
        }
        return null;
    }
    
    public int calculateResult (String received) {
        String[] splitted = received.split(";");
        
        String user1 = splitted[1];
        
        String b1 = splitted[5];
        String r1 = splitted[6];
        String b2 = splitted[8];
        String r2 = splitted[9];
        String b3 = splitted[11];
        String r3 = splitted[12];
    
        
        int i = 0;
        int c1 = Integer.parseInt(b1);
        int c2 = Integer.parseInt(b2);
        int c3 = Integer.parseInt(b3);

        
        if (c1 == Integer.parseInt(r1)) {
            i++;
        } 
        if (c2 == Integer.parseInt(r2)) {
            i++;
        } 
        if (c3 == Integer.parseInt(r3)) {
            i++;
        } 
        
        System.out.println(user1 + " : " + i + " cau dung");
        return i;
    }

    public void draw () throws SQLException {
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
        
//        newAvgCompetitor1 = Math.round(newAvgCompetitor1 * 100) / 100;
//        newAvgCompetitor2 = Math.round(newAvgCompetitor2 * 100) / 100;
        
        user1.setAvgCompetitor(newAvgCompetitor1);
        user2.setAvgCompetitor(newAvgCompetitor2);
        
        new UserController().updateUser(user1);
        new UserController().updateUser(user2);
    }
    
    public void client1Win(int time) throws SQLException {
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
        
        new UserController().updateUser(user1);
        new UserController().updateUser(user2);
    }
    
    public void client2Win(int time) throws SQLException {
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
        
        new UserController().updateUser(user1);
        new UserController().updateUser(user2);
    }
    
    public void userLeaveGame (String username) throws SQLException {
        if (client1.getLoginUser().equals(username)) {
            client2Win(0);
        } else if (client2.getLoginUser().equals(username)) {
            client1Win(0);
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
>>>>>>> parent of 37dc135 (NQ Hai Dang sửa toàn bộ)
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

    // Phát thông báo cho tất cả các client
    public void broadcast(String msg) {
        clients.forEach((c) -> {
            c.sendData(msg);
        });
    }

    public Client find(String username) {
        for (Client c : clients) {
            if (c.getLoginUser() != null && c.getLoginUser().equals(username)) {
                return c;
            }
        }
        return null;
    }

    // Getter/Setter cho các thuộc tính
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
