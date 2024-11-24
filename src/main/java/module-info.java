module com.example.hangmangame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires org.slf4j;

    opens com.example.hangmangame to javafx.fxml;
    exports com.example.hangmangame;
}