package com.example.hangmangame;


import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class Db {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Db.class);
    private static Db instance;
    private Connection connection;
    private static final Logger logger = Logger.getLogger(Db.class.getName());
    private static final String DB_URL = "jdbc:sqlite:target/classes/com/example/hangmangame/database/words2.db";

    public Db() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            logger.info("Connected to database");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection failure: {0}", e.getMessage());
        }
    }

    public static Db getInstance() {
        if (instance == null) {
            instance = new Db();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                logger.info("Reconnected to database");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to reconnect to database: {0}", e.getMessage());
        }
        return connection;
    }

    public String getRandomWord(int category) {
        String word = null;
        String sql = "SELECT word FROM Words WHERE category = ? ORDER BY RANDOM() LIMIT 1";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, category);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                word = rs.getString("word");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving word: {0}", e.getMessage());
        }

        return word;
    }


    public void addWord(String word, int Category) {


        String sql = "INSERT INTO Words(Category,word) VALUES(?,?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, Category);
            pstmt.setString(2, word.toLowerCase());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding word and category: {0}", e.getMessage());

        }
    }



    public boolean categoryExists(String category) {
        String checkCategorySQL = "SELECT 1 FROM Categories WHERE name_category = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(checkCategorySQL)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error checking category: {0}", e.getMessage());
            return false;
        }
    }

    public void addCategory(String category, String colorHex) {
            String insertCategorySQL = "INSERT INTO Categories (name_category,color_hex,isUser) VALUES (?,?,?)";

            try (PreparedStatement pstmt = getConnection().prepareStatement(insertCategorySQL)) {
                pstmt.setString(1, category.toLowerCase());
                pstmt.setString(2, colorHex);
                pstmt.setBoolean(3,true);
                pstmt.executeUpdate();
                logger.info("New category added: " + category);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error adding category: {0}", e.getMessage());
            }

    }
    public List<String> getWords(int id){
        List<String> words = new ArrayList<>();
        String selectWordsSQL = "SELECT word FROM Words WHERE Category = ?";
        try(PreparedStatement pstmt = getConnection().prepareStatement(selectWordsSQL)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                words.add(rs.getString("word"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving words: {0}", e.getMessage());

        }
        System.out.println("Is work");

        return words;
    }
    public int getCategoryId(String category) {
        String sql = "SELECT Id FROM Categories WHERE name_category = ?";
        try(PreparedStatement pstmt = getConnection().prepareStatement(sql)){
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("Id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting category id: {0}", e.getMessage());
        }
        return -1;
    }
    public String getCategoryName(int id) {
        String categoryName = null;
        String sql = "SELECT name_category FROM Categories WHERE Id = ?";
        try(PreparedStatement pstmt = getConnection().prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                categoryName =  rs.getString("name_category");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting category name: {0}", e.getMessage());
        }
        return categoryName;
    }
    public void updateCategorySet(int id, ArrayList<String> words) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            String sqlDelete = "DELETE FROM Words WHERE Category = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }


            String sqlInsert = "INSERT INTO Words(Category, word) VALUES(?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                for (String word : words) {
                    pstmt.setInt(1, id);
                    pstmt.setString(2, word);
                    pstmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    logger.log(Level.SEVERE, "Error rolling back transaction", rollbackEx);
                }
            }
            logger.log(Level.SEVERE, "Error updating category set", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error resetting auto-commit", e);
                }
            }
        }
    }
    public void updateCategoryColor(String color_hex, int id){
        String sqlInsert = "UPDATE Categories SET color_hex = ? WHERE Id = ?";
        try(PreparedStatement pstmt = getConnection().prepareStatement(sqlInsert)){
            pstmt.setString(1, color_hex);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }catch(SQLException e){
            logger.log(Level.SEVERE, "Error updating category color", e);
        }
    }
    public String getCategoryColor(int id) {
        String color = null;
        String selectQuery = "SELECT color_hex from Categories WHERE Id = ?";
        try(PreparedStatement pstmt = getConnection().prepareStatement(selectQuery)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                color = rs.getString("color_hex");
            }
        }catch (SQLException e){
            logger.log(Level.SEVERE, "Error getting category color", e);
        }
        return color;
    }
    public boolean userExists() {
        String sql = "SELECT 1 FROM Users_Data WHERE id = 1";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.error("Error checking user existence", e);
            return false;
        }
    }

    public void createUser() {
        String insertQuery = "INSERT INTO Users_Data(id) VALUES (1) ;";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.executeUpdate();

        }catch (SQLException e) {
            log.error("Error creating user", e);

        }
    }

    public int getBestScore() {
        String sql = "SELECT best_score FROM Users_Data WHERE id = 1";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            return rs.next() ? rs.getInt("best_score") : -1;
        } catch (SQLException e) {
            log.error("Error getting best score", e);
            return -1;
        }
    }

    public void updateBestScore(int score) {
        String sql = "UPDATE Users_Data SET best_score = ? WHERE id = 1";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Error updating best score", e);
        }
    }


}