package com.dbms.alibabaphase3;

import Model.Hotel;
import Model.Transaction;
import Model.User;
import Repository.DAO.HotelDAO;
import Repository.DAO.RepositoryFacade;
import Repository.DAO.TransactionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class HotelController implements Initializable {
    @FXML
    private ChoiceBox<String> hotelCity;
    @FXML
    private TextField passengerCount;
    @FXML
    private TextField roomCount;
    @FXML
    private DatePicker entryDate;
    @FXML
    private DatePicker exitDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Hotel> hotels = RepositoryFacade.getInstance().findAll(Hotel.class);
        Set<String> hotelCities = new HashSet<>();
        for (Hotel hotel:hotels) {
            hotelCities.add(hotel.getCity());
        }
        ObservableList<String> observableList = FXCollections.observableList(new ArrayList<>(hotelCities));
        hotelCity.setItems(observableList);
        hotelCity.setValue(observableList.get(0));
    }

    private List<Control> checkInputs(){
        List<Control> emptyInputs = new LinkedList<>();
        if(hotelCity.getValue() == null){
            emptyInputs.add(hotelCity);
        }
        if(passengerCount.getText().isEmpty()){
            emptyInputs.add(passengerCount);
        }
        if(roomCount.getText().isEmpty()){
            emptyInputs.add(roomCount);
        }
        if(entryDate.getValue() == null){
            emptyInputs.add(entryDate);
        }
        if(exitDate.getValue() == null){
            emptyInputs.add(exitDate);
        }
        return emptyInputs;
    }

    private void resetInputsStyle(){
        List<Control> controls = new LinkedList<>(Arrays.asList(hotelCity, passengerCount, roomCount, entryDate, exitDate));
        for (Control control : controls) {
            control.setStyle("");
        }
    }

    public void onSearchButtonClick(MouseEvent mouseEvent) {
        List<Control> emptyList = checkInputs();
        resetInputsStyle();
        if(emptyList.size() == 0){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HotelReserve.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 683, 400);
                HotelReserveController controller = fxmlLoader.getController();
                controller.setInitial(hotelCity.getValue(),Integer.parseInt(passengerCount.getText()),
                        Integer.parseInt(roomCount.getText()),entryDate.getValue(),exitDate.getValue());
                HelloApplication.primaryStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill the required fields", ButtonType.OK);
            alert.show();
            for (Control field : emptyList) {
                field.setStyle("-fx-border-color: red");
            }
        }

    }



    public void clickReturnI(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
