package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.DAO.AlbumDAO;
import org.example.model.DAO.SongDAO;
import org.example.model.domain.Album;
import org.example.model.domain.Song;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAddSong implements Initializable {
    SongDAO sdao = new SongDAO();
    @FXML private TextField txtname;
    @FXML private TextField  txtgender;

    @FXML private TextField txtduration;
    @FXML  private  TextField txtrepro;

    @FXML private ComboBox <String> txtalbum;


    private String audioFilePath;
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AlbumDAO adao = new AlbumDAO();
        List<String> songNames = adao.findNames();
        ObservableList<String> namesObservableList = FXCollections.observableArrayList(songNames);
        txtalbum.setItems(namesObservableList);
    }
    @FXML
    private void addSong() {
        String name = txtname.getText();
        String gender = txtgender.getText();
        String duration = txtduration.getText();
        String album = txtalbum.getValue();
        int repro = Integer.parseInt(txtrepro.getText());

        try {
            Album albumObject = new Album();
            Song s = new Song();
            s.setName_song(name);
            s.setGender(gender);
            s.setDuration(duration);
            Album a = new Album();
            a.setName(album);
            s.setAlbum(a);
            s.setNrepro(repro);
            s.setArchive_song(audioFilePath);

            sdao.save(s);
            App.setRoot("HomeAdmin");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void chooseAudioFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos MP3", "*.mp3"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Guarda la ruta del archivo seleccionado en la variable audioFilePath
            audioFilePath = selectedFile.getAbsolutePath();
        }
    }

}

