module com.example.robotarenacoursework {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.robotarenacoursework to javafx.fxml;
    exports com.example.robotarenacoursework;
}