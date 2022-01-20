package com.dbms.alibabaphase3;

import Model.User;
import Repository.DAO.RepositoryFacade;
import Repository.DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public void onSubmitButtonClick(MouseEvent mouseEvent) {
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK);
            alert.show();
            return;
        }
        String username = usernameField.getText();
        String password = passwordField.getText();
        UserDAO userDAO = (UserDAO) RepositoryFacade.getInstance().getDao(User.class);
        User currentUser = userDAO.findIdByUserPass(username,password);
        if(currentUser != null){
            Info.getInstance().setUser(currentUser);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username or Password not found", ButtonType.OK);
            alert.show();
        }
    }

}
