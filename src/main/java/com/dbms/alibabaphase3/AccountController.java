package com.dbms.alibabaphase3;

import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField passwordInput;

    private void setInputs(){
        User user = Info.getInstance().getUser();
        nameInput.setText(user.getName());
        lastNameInput.setText(user.getLastName());
        passwordInput.setText(user.getPassword());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInputs();
    }

    public void onReturnToMainMenuButtonClick(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSaveChangesButtonClick(MouseEvent mouseEvent) {
    }
}
