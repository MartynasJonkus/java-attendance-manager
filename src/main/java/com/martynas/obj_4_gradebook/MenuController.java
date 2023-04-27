package com.martynas.obj_4_gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class MenuController extends Controller{
    @FXML
    protected void manage(ActionEvent event) throws IOException {
        switchScene(event, "manage.fxml");
    }
    @FXML
    protected void mark(ActionEvent event) throws IOException {
        switchScene(event, "mark.fxml");
    }
    @FXML
    protected void importData(ActionEvent event) throws IOException {
        switchScene(event, "import.fxml");
    }
    @FXML
    protected void exportData(ActionEvent event) throws IOException {
        switchScene(event, "export.fxml");
    }
    @FXML
    protected void report(ActionEvent event) throws IOException {
        switchScene(event, "report.fxml");
    }
    @FXML
    protected void exit(ActionEvent event){
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    protected void switchScene(ActionEvent event, String filename) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(filename)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}