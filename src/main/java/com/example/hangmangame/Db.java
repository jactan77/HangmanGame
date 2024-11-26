package com.example.hangmangame;

import com.example.hangmangame.utils.RandomColors;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    public boolean addWord(String word, int Category) {


        String sql = "INSERT INTO Words(Category,word) VALUES(?,?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, Category);
            pstmt.setString(2, word);


            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding word and category: {0}", e.getMessage());
            return false;
        }
    }

    public boolean deleteWord(String word, String category) {
        String sql = "DELETE FROM Words WHERE word = ? AND Category = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, word);
            pstmt.setString(2, category);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting word: {0}", e.getMessage());
            return false;
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

    public void addCategory(String category) {
            String insertCategorySQL = "INSERT INTO Categories (name_category,color_hex,isUser) VALUES (?,?,?)";

            try (PreparedStatement pstmt = getConnection().prepareStatement(insertCategorySQL)) {
                pstmt.setString(1, category);
                pstmt.setString(2, RandomColors.setRandomColor());
                pstmt.setBoolean(3,true);
                pstmt.executeUpdate();
                logger.info("New category added: " + category);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error adding category: {0}", e.getMessage());
            }

    }
    public ArrayList<String> getWords(int id){
        ArrayList<String> words = new ArrayList<>();
        String selectWordsSQL = "SELECT word FROM Words WHERE id = ?";
        try(PreparedStatement pstmt = getConnection().prepareStatement(selectWordsSQL)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                words.add(rs.getString("word"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving words: {0}", e.getMessage());

        }
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

}