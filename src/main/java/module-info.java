module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires jbcrypt;
    requires java.xml;
    requires jlayer;
    requires java.persistence;
    requires org.hibernate.orm.core;


    opens org.example to javafx.fxml;
    opens org.example.conexion to java.xml.bind;
    opens org.example.model.domain;

    exports org.example;
    exports org.example.model.domain;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml;
}
