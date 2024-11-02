package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DatabaseConnection;
import model.UserModel;

public class UserController {

    // SQL Queries
    private final String INSERT_USER = "INSERT INTO users (username, password, score, win, draw, lose, avgCompetitor, avgTime) VALUES (?, ?, 0, 0, 0, 0, 0, 0)";
    private final String CHECK_USER = "SELECT userId FROM users WHERE username = ? LIMIT 1";
    private final String LOGIN_USER = "SELECT username, password, score FROM users WHERE username=? AND password=?";
    private final String GET_INFO_USER = "SELECT username, password, score, win, draw, lose, avgCompetitor, avgTime FROM users WHERE username=?";
    private final String UPDATE_USER = "UPDATE users SET score = ?, win = ?, draw = ?, lose = ?, avgCompetitor = ?, avgTime = ? WHERE username=?";
    
    // Database connection instance
    private final Connection con;

    // Constructor to initialize the database connection
    public UserController() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    // Register a new user
    public String register(String username, String password) {
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            // Check if the user already exists
            p = con.prepareStatement(CHECK_USER);
            p.setString(1, username);
            r = p.executeQuery();
            
            if (r.next()) {
                return "failed;User Already Exists";
            } else {
                // Close the current PreparedStatement and ResultSet before next query
                r.close();
                p.close();

                // Insert new user
                p = con.prepareStatement(INSERT_USER);
                p.setString(1, username);
                p.setString(2, password);
                p.executeUpdate();
            }
            return "success;";
        } catch (SQLException e) {
            e.printStackTrace();
            return "failed;Database Error";
        } finally {
            closeResources(p, r); // Ensure resources are closed
        }
    }

    // Login user
    public String login(String username, String password) {
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            p = con.prepareStatement(LOGIN_USER);
            p.setString(1, username);
            p.setString(2, password);
            r = p.executeQuery();
            
            if (r.next()) {
                float score = r.getFloat("score");
                return "success;" + username + ";" + score;
            } else {
                return "failed;Please enter the correct account password!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "failed;Database Error";
        } finally {
            closeResources(p, r); // Ensure resources are closed
        }
    }

    // Get user information
    public String getInfoUser(String username) {
        PreparedStatement p = null;
        ResultSet r = null;
        UserModel user = new UserModel();
        try {
            p = con.prepareStatement(GET_INFO_USER);
            p.setString(1, username);
            r = p.executeQuery();
            
            if (r.next()) {
                user.setUserName(r.getString("username"));
                user.setScore(r.getFloat("score"));
                user.setWin(r.getInt("win"));
                user.setDraw(r.getInt("draw"));
                user.setLose(r.getInt("lose"));
                user.setAvgCompetitor(r.getFloat("avgCompetitor"));
                user.setAvgTime(r.getFloat("avgTime"));
            }
            return "success;" + user.getUserName() + ";" + user.getScore() + ";" + user.getWin() + ";" + user.getDraw() + ";" + user.getLose() + ";" + user.getAvgCompetitor() + ";" + user.getAvgTime();
        } catch (SQLException e) {
            e.printStackTrace();
            return "failed;Database Error";
        } finally {
            closeResources(p, r); // Ensure resources are closed
        }
    }

    // Update user's score and other related stats
    public boolean updateScore(String username, float score) {
    PreparedStatement p = null;
    try {
        // Chuẩn bị câu lệnh SQL để cập nhật điểm của người dùng
        String UPDATE_SCORE = "UPDATE users SET score = ? WHERE username = ?";
        p = con.prepareStatement(UPDATE_SCORE);
        p.setFloat(1, score); // Đặt điểm mới
        p.setString(2, username); // Đặt tên người dùng

        // Thực thi câu lệnh cập nhật và trả về kết quả
        return p.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        // Đảm bảo rằng PreparedStatement được đóng sau khi thực thi
        closeResources(p, null);
    }
}

    // Update user information
    public boolean updateUser(UserModel user) {
        PreparedStatement p = null;
        try {
            p = con.prepareStatement(UPDATE_USER);
            p.setFloat(1, user.getScore());
            p.setInt(2, user.getWin());
            p.setInt(3, user.getDraw());
            p.setInt(4, user.getLose());
            p.setFloat(5, user.getAvgCompetitor());
            p.setFloat(6, user.getAvgTime());
            p.setString(7, user.getUserName());

            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(p, null); // Ensure PreparedStatement is closed
        }
    }

    // Get user object by username
    public UserModel getUser(String username) {
        PreparedStatement p = null;
        ResultSet r = null;
        UserModel user = new UserModel();
        try {
            p = con.prepareStatement(GET_INFO_USER);
            p.setString(1, username);
            r = p.executeQuery();
            
            if (r.next()) {
                user.setUserName(r.getString("username"));
                user.setScore(r.getFloat("score"));
                user.setWin(r.getInt("win"));
                user.setDraw(r.getInt("draw"));
                user.setLose(r.getInt("lose"));
                user.setAvgCompetitor(r.getFloat("avgCompetitor"));
                user.setAvgTime(r.getFloat("avgTime"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(p, r); // Ensure resources are closed
        }
    }

    // Utility method to close PreparedStatement and ResultSet
    private void closeResources(PreparedStatement p, ResultSet r) {
        try {
            if (r != null) {
                r.close();
            }
            if (p != null) {
                p.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
