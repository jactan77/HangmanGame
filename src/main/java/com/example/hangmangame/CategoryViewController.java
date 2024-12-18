package com.example.hangmangame;

import com.example.hangmangame.utils.RandomColors;
import com.example.hangmangame.utils.UISoundEffects;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class CategoryViewController {

    private final UISoundEffects soundEffects = new UISoundEffects();
    private final Db db = new Db();
    @FXML
    private VBox buttonContainer;
    @FXML
    private Button userSetsButton;
    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
       loadCategories();

        userSetsButton.setOnAction(event -> {
            soundEffects.clickEffect();
            Main.loadUserCategoryScene();
        });
        backButton.setOnAction(event -> {
            soundEffects.clickEffect();
            Main.switchToMenuScene();
        });


    }



    private void loadCategories() {
        try (Connection conn = Db.getInstance().getConnection()) {
            String sql = "SELECT DISTINCT Id, name_category, color_hex FROM Categories WHERE isUser = false;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String categoryName = rs.getString("name_category");
                String colorCode = rs.getString("color_hex");
                Button categoryButton = new Button(categoryName);
                categoryButton.setStyle("-fx-background-color: "+ colorCode + "; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-font-weight: bold; -fx-cursor: hand; -fx-pref-width: 200px;");
                categoryButton.setOnAction(event -> {
                    soundEffects.clickEffect();
                    Main.setSelectedCategory(id);
                    Main.switchToGameScene();
                    System.out.println("Selected category: " + categoryName);
                });
                buttonContainer.getChildren().add(categoryButton);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
