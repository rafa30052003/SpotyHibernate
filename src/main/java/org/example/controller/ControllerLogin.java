package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.model.DAO.UserDAO;
import org.example.model.domain.Admin;
import org.example.model.domain.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerLogin {

    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonUser;
    @FXML
    private TextField textName;
    @FXML
    private PasswordField textPassword;

    private static String loggedInUserName; // Variable para el nombre del usuario logueado
    private static String loggedInUserMail; // Variable para el correo del usuario logueado
    private static String loggedInUserPhoto; // Variable para la foto del usuario logueado
    private static String loggedInUserPassword;

    @FXML
    private void buttonUser() throws IOException {
        App.setRoot("addUser");
    }

    @FXML
    private void buttonLogin() throws IOException, SQLException {
        login();
    }


    @FXML
    private void login() throws IOException, SQLException {
        String username = textName.getText();
        String password = textPassword.getText();

        UserDAO userDAO = new UserDAO();

        // Busca al usuario en la tabla User
        User user = userDAO.findUserByName(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            if (userDAO.isAdmin(username)) {
                // La contraseña coincide y es un administrador
                loggedInUserName = user.getName();
                loggedInUserMail = user.getMail();
                loggedInUserPhoto = user.getPhoto();
                loggedInUserPassword = user.getPassword(); // Aquí puedes almacenar la contraseña hasheada si la necesitas
                App.setRoot("homeAdmin");
                return; // Sale del método para evitar la redirección a 'homeUser'
            }

            // La contraseña coincide pero no es un administrador
            loggedInUserName = user.getName();
            loggedInUserMail = user.getMail();
            loggedInUserPhoto = user.getPhoto();
            loggedInUserPassword = user.getPassword(); // Aquí puedes almacenar la contraseña hasheada si la necesitas
            App.setRoot("homeAdmin");
        } else {
            // Si no se encontró un usuario con el nombre y contraseña correctos, muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de acceso");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos");
            alert.showAndWait();
        }
    }
    // Métodos para acceder a los campos del usuario logueado desde otras clases
    public static String getLoggedInUserName() {
        return loggedInUserName;
    }

    public static String getLoggedInUserMail() {
        return loggedInUserMail;
    }

    public static String getLoggedInUserPhoto() {
        return
                loggedInUserPhoto;
    }
    // Método para configurar la contraseña del usuario logueado
    public static String getLoggedInUserPassword() {
        return loggedInUserPassword;
    }
}
