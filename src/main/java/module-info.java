module com.example.hangmangame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.dlsc.formsfx;

    opens com.example.hangmangame to javafx.fxml;
    exports com.example.hangmangame;
}