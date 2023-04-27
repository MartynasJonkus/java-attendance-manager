package com.martynas.obj_4_gradebook;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController extends Controller implements Initializable {
    @FXML
    private TextField groupNameField;
    @FXML
    private TextField studentIDField;
    @FXML
    private TextField studentNameField;
    @FXML
    private TextField studentSurnameField;
    @FXML
    private Label errorLabel;

    @FXML
    private TreeTableView<Object> treeTableView;
    @FXML
    private TreeTableColumn<Student, String> nameCol;
    @FXML
    private TreeTableColumn<Student, String> surnameCol;
    @FXML
    private TreeTableColumn<Student, Integer> IDCol;

    TreeItem<Object> root = new TreeItem<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("surname"));
        IDCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("ID"));

        nameCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        surnameCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Callback<TreeTableColumn<Student, Integer>, TreeTableCell<Student, Integer>> cellFactory =
                column -> new TextFieldTreeTableCell<>(new IntegerStringConverter());
        IDCol.setCellFactory(cellFactory);

        nameCol.setOnEditCommit(event -> {
            TreeItem<Student> editedItem = event.getTreeTablePosition().getTreeItem();
            editedItem.getValue().setName(event.getNewValue());
        });
        surnameCol.setOnEditCommit(event -> {
            TreeItem<Student> editedItem = event.getTreeTablePosition().getTreeItem();
            editedItem.getValue().setSurname(event.getNewValue());
        });
        IDCol.setOnEditCommit(event -> {
            TreeItem<Student> editedItem = event.getTreeTablePosition().getTreeItem();
            editedItem.getValue().setID(event.getNewValue());
        });


        for (Group group : groups) {
            TreeItem<Object> groupItem = new TreeItem<>(group);
            root.getChildren().add(groupItem);

            for (Student student : group.getStudents()) {
                TreeItem<Object> studentItem = new TreeItem<>(student);
                groupItem.getChildren().add(studentItem);
            }
        }

        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.setEditable(true);
    }

    @FXML
    void addGroup() {
        errorLabel.setText("");

        String groupName = groupNameField.getText();
        Group group = new Group(groupName, FXCollections.observableArrayList());
        groupData.addGroup(group);
        root.getChildren().add(new TreeItem<>(group));
    }
    @FXML
    void removeGroup() {
        errorLabel.setText("");

        TreeItem<Object> selected = treeTableView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof Group) {
            selected.getParent().getChildren().remove(selected);

            groupData.removeGroup((Group) selected.getValue());
        }else
            errorLabel.setText("No group selected");
    }

    @FXML
    void addStudent() {
        errorLabel.setText("");

        TreeItem<Object> selected = treeTableView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof Group selectedGroup) {
            String name = studentNameField.getText();
            String surname = studentSurnameField.getText();
            int id = Integer.parseInt(studentIDField.getText());

            Student student = new Student(name, surname, id);
            groupData.addStudent(selectedGroup, student);

            TreeItem<Object> studentItem = new TreeItem<>(student);
            selected.getChildren().add(studentItem);
        }else
            errorLabel.setText("No group selected");
    }
    @FXML
    void removeStudent() {
        errorLabel.setText("");

        TreeItem<Object> selected = treeTableView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof Student selectedStudent) {
            Group selectedGroup = (Group) selected.getParent().getValue();
            selected.getParent().getChildren().remove(selected);

            groupData.removeStudent(selectedGroup, selectedStudent);
        }else
            errorLabel.setText("No student selected");
    }
}
