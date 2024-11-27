module com.example.robotarenacoursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.robotarenacoursework to javafx.fxml;
    exports com.example.robotarenacoursework;
}