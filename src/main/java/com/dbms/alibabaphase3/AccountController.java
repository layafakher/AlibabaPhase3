package com.dbms.alibabaphase3;

import Model.User;
import Repository.DAO.RepositoryFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class AccountController implements Initializable {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField telephoneInput;
    @FXML
    private TextField nationalCodeInput;
    @FXML
    private TextField emailInput;
    @FXML
    private DatePicker dateOfBirthInput;
    @FXML
    private TextField accountNumberInput;
    @FXML
    private TextField creditInput;

    private void setInputs(){
        User user = Info.getInstance().getUser();
        nameInput.setText(user.getName());
        lastNameInput.setText(user.getLastName());
        passwordInput.setText(user.getPassword());
        ToggleGroup genderGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        if(user.isGender()){
            maleRadioButton.fire();
        }
        else{
            femaleRadioButton.fire();
        }
        phoneInput.setText(user.getPhoneNumber());
        telephoneInput.setText(user.getTelephone());
        nationalCodeInput.setText(user.getNationalCode());
        emailInput.setText(user.getEmail());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(user.getDateOfBirth());
        dateOfBirthInput.setValue(LocalDate.parse(format));
        accountNumberInput.setText(user.getAccountNumber());
        creditInput.setText(String.valueOf(user.getCredit()));
    }

    private List<TextField> checkInputs(){
        List<TextField> emptyInputs = new LinkedList<>();
        if(nameInput.getText().isEmpty()){
            emptyInputs.add(nameInput);
        }
        if(lastNameInput.getText().isEmpty()){
            emptyInputs.add(lastNameInput);
        }
        if(passwordInput.getText().isEmpty()){
            emptyInputs.add(passwordInput);
        }
        if(phoneInput.getText().isEmpty()){
            emptyInputs.add(phoneInput);
        }
        if(telephoneInput.getText().isEmpty()){
            emptyInputs.add(telephoneInput);
        }
        if(nationalCodeInput.getText().isEmpty()){
            emptyInputs.add(nationalCodeInput);
        }
        if(emailInput.getText().isEmpty()){
            emptyInputs.add(emailInput);
        }
        return emptyInputs;
    }

    private void resetInputsStyle(){
        List<TextField> textFields = new LinkedList<>();
        textFields.addAll(Arrays.asList(nameInput,lastNameInput,passwordInput,phoneInput,telephoneInput,nationalCodeInput,
                emailInput,accountNumberInput));
        for (TextField field : textFields) {
            field.setStyle("");
        }
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
        List<TextField> emptyList = checkInputs();
        resetInputsStyle();
        if(emptyList.size() == 0){
            User user = new User();
            user.setId(Info.getInstance().getUser().getId());
            user.setName(nameInput.getText());
            user.setLastName(lastNameInput.getText());
            user.setPassword(passwordInput.getText());
            if(maleRadioButton.isSelected()){
                user.setGender(true);
            }
            else{
                user.setGender(false);
            }
            user.setPhoneNumber(phoneInput.getText());
            user.setTelephone(telephoneInput.getText());
            user.setNationalCode(nationalCodeInput.getText());
            user.setEmail(emailInput.getText());
            LocalDate localDate = dateOfBirthInput.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            user.setDateOfBirth(date);
            user.setSignUpDate(Info.getInstance().getUser().getSignUpDate());
            user.setCredit(Info.getInstance().getUser().getCredit());
            if(!accountNumberInput.getText().isEmpty()){
                user.setAccountNumber(accountNumberInput.getText());
            }
            User newUser = RepositoryFacade.getInstance().save(user,User.class);
            if(newUser != null){
                Info.getInstance().setUser(newUser);
                setInputs();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Information updated successfully", ButtonType.OK);
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Something went wrong", ButtonType.OK);
                alert.show();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill the required fields", ButtonType.OK);
            alert.show();
            for (TextField field : emptyList) {
                field.setStyle("-fx-border-color: red");
            }
        }
    }

    public void onCreditButtonClick(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Transaction.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
