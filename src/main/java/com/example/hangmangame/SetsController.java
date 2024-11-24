package com.example.hangmangame;

import com.example.hangmangame.utils.RandomColors;
import com.example.hangmangame.utils.UISoundEffects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SetsController {
    private static final String CATEGORY_QUERY =
            "SELECT DISTINCT Id, name_category, color_hex FROM Categories WHERE isUser = true";
    private static final String DELETE_CATEGORY_QUERY =
            "DELETE FROM Categories WHERE Id = ?";
    private  static final String DELETE_CATEGORY_WORD=
            "DELETE FROM Words\n" +
                    "WHERE Category=?;";

    private final UISoundEffects soundEffects = new UISoundEffects();
    private final Db db = new Db();

    @FXML private VBox userCategoriesContainer;
    @FXML private Button newSetButton;
    @FXML private Button backButton;
    @FXML private Button exitButton;

    @FXML
    private void initialize() {
        setupButtonHandlers();
        loadCategories();
    }

    private void setupButtonHandlers() {
        newSetButton.setOnAction(event -> handleNewSet());
        backButton.setOnAction(event -> handleBack());
        exitButton.setOnAction(event -> handleExit());
    }

    private void handleNewSet() {
        soundEffects.clickEffect();
        Main.switchToFormsScene();
    }

    private void handleBack() {
        soundEffects.clickEffect();
        Main.loadCategoryScene();
    }

    private void handleExit() {
        soundEffects.clickEffect();
        Platform.exit();
    }

    private void loadCategories() {
        userCategoriesContainer.getChildren().clear();
        try (Connection conn = Db.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CATEGORY_QUERY);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                createCategoryButton(
                        rs.getInt("Id"),
                        rs.getString("name_category"),
                        rs.getString("color_hex")
                );
            }
        } catch (SQLException e) {
            handleDatabaseError(e);
        }
    }

    private void createCategoryButton(int id, String categoryName, String colorCode) {
        Button categoryButton = new Button(categoryName);
        Button deleteButton = new Button("×");
        HBox container = new HBox();

        styleContainer(container);
        styleButton(categoryButton, colorCode);
        styleDeleteButton(deleteButton);

        setupButtonAction(categoryButton, id, categoryName);
        setupDeleteAction(deleteButton, id, container);

        container.getChildren().addAll(categoryButton, deleteButton);
        userCategoriesContainer.getChildren().add(container);
    }

    private void styleContainer(HBox container) {
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        container.getStyleClass().add("category-button-container");
    }

    private void styleButton(Button button, String colorCode) {
        button.getStyleClass().add("category-button");
        String style = String.format("""
            -fx-background-color: %s;
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-padding: 15 30;
            -fx-border-radius: 15;
            -fx-background-radius: 15;
            -fx-font-weight: bold;
            -fx-cursor: hand;
            -fx-min-width: 250px;
            -fx-max-width: 400px;
            """, colorCode);
        button.setStyle(style);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(colorCode).darker());
        button.setOnMouseEntered(e -> button.setEffect(shadow));
        button.setOnMouseExited(e -> button.setEffect(null));
    }

    private void styleDeleteButton(Button deleteButton) {
        deleteButton.getStyleClass().add("delete-button");
    }

    private void setupButtonAction(Button button, int id, String categoryName) {
        button.setOnAction(event -> {
            soundEffects.clickEffect();
            Main.setSelectedCategory(id);
            Main.switchToGameScene();
            System.out.println("Selected category: " + categoryName);
        });
    }

    private void setupDeleteAction(Button deleteButton, int id, HBox container) {
        deleteButton.setOnAction(event -> {
            soundEffects.clickEffect();
            deleteCategory(id, container);
        });
    }

    private void deleteCategory(int id, HBox container) {
        try (Connection conn = Db.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_CATEGORY_QUERY)) {
             PreparedStatement pstmt1 = conn.prepareStatement(DELETE_CATEGORY_WORD);
            pstmt.setInt(1, id);
            pstmt1.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            int affectedRows1 = pstmt1.executeUpdate();
            if (affectedRows > 0 && affectedRows1 > 0) {
                userCategoriesContainer.getChildren().remove(container);
            }
        } catch (SQLException e) {
            handleDatabaseError(e);
        }
    }

    private void handleDatabaseError(SQLException e) {
        System.err.println("Database error occurred: " + e.getMessage());

    }
}