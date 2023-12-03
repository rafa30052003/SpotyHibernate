package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.model.DAO.ListDAO;
import org.example.model.domain.Playlist;

import java.sql.SQLException;

public class ControllerCreateList {
    @FXML
    private TextField nameList;
    @FXML
    private TextArea descriptionList;
    @FXML
    private Button addList;

    private static ControllerLogin HomeLogin;

    private static String loggedInUserName = HomeLogin.getLoggedInUserName();

    /**
     * funcion para crear una listas en la base de datos 
     */
    @FXML
    public void addList() {
        try {
            Playlist lists=new Playlist();
            ListDAO listDAO=new ListDAO();
            String name = nameList.getText();
            String description = descriptionList.getText();
            String Name_user = ControllerLogin.getLoggedInUserName();
            Playlist newlist = new Playlist(0,name, description,Name_user);
            listDAO.save(newlist);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("lista creada");
            alert.setHeaderText(null);
            alert.setContentText("lista creda");
            alert.showAndWait();

            // Limpia los campos de texto después de agregar el usuario

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de base de datos");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo crear la lista en la base de datos.");
            alert.showAndWait();
            e.printStackTrace();


        }
    }
}


