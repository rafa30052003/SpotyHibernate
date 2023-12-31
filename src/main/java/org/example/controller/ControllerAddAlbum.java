package org.example.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.DAO.AlbumDAO;
import org.example.model.DAO.ArtistDAO;
import org.example.model.domain.Album;
import org.example.model.domain.Artist;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAddAlbum implements Initializable {

    @FXML
    private TextField buscar;
    @FXML
    private DatePicker txtDate;
    @FXML
    private ImageView imageView;

    private File selectedImageFile;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> txtArtista;

    @FXML
    private Button foto;

    private ArtistDAO artistDAO;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArtistDAO artistDAO = new ArtistDAO();
        List<String> artistNames = artistDAO.findNames();
        ObservableList<String> namesObservableList = FXCollections.observableArrayList(artistNames);
        txtArtista.setItems(namesObservableList);

    }
    @FXML
    private void addCosumer() throws IOException {
        String name = txtName.getText();
        LocalDate date = txtDate.getValue();
        String artist = txtArtista.getValue();

        if (name.isEmpty() || date == null || (artist != null && artist.isEmpty()) || imageView == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Incompletos");
            alert.setHeaderText("Por favor complete todos los campos.");
            alert.showAndWait();
            return;
        }
        File selectedFile = elegirFoto();
        FileInputStream inputStream = new FileInputStream(selectedFile);
        byte[] photo = new byte[inputStream.available()];
        inputStream.read(photo);

        Album album = new Album();
        album.setName(name);
        album.setPhoto(Arrays.toString(photo).getBytes());
        album.setPublicTime(java.sql.Date.valueOf(date));
        album.setnReproduction(0);

        Artist artistObject = new Artist();
        artistObject.setName(artist);
        album.setArtist(artistObject);

        AlbumDAO albumDAO = new AlbumDAO();
        albumDAO.save(album);

        txtName.clear();
        txtArtista.setValue(null);
        txtDate.setValue(null);
        selectedImageFile=null;
        imageView.setImage(null);

        System.out.println("Álbum guardado exitosamente.");
    }

    @FXML
    private File elegirFoto() {
        if (selectedImageFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar Foto");
            selectedImageFile = fileChooser.showOpenDialog(new Stage());

            if (selectedImageFile != null) {
                try {
                    FileInputStream inputStream = new FileInputStream(selectedImageFile);
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Por favor seleccione una imagen.");
                }
            }
        }
        return selectedImageFile;
    }
    @FXML
    void volver(ActionEvent event) throws IOException {
        App.setRoot("homeAdmin");
    }
}


