package com.dbms.alibabaphase3;

import Model.BusTrip;
import Model.Trip;
import Repository.DAO.RepositoryFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TourController implements Initializable {

    @FXML
    private ChoiceBox<String> origin;
    @FXML
    private ChoiceBox<String> desti;
    @FXML
    private Button search;
    @FXML
    private Button ret;
    @FXML
    private DatePicker exdate;
    @FXML
    private DatePicker retdate;
    @FXML
    private TextField cnt;
    @FXML
    private TextField room;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Trip> trips = RepositoryFacade.getInstance().findAll(Trip.class);
        List<TripView> TripView = new LinkedList<>();
        Collection<String> destination = new ArrayList<>();
        Collection<String> origins = new ArrayList<>();
        for (Trip trip:trips) {
            TripView b = new TripView(trip.getOrigin(), trip.getDestination(), (Long) trip.getId());
            destination.add(trip.getDestination());
            origins.add(trip.getOrigin());
            TripView.add(b);
        }
        desti.getItems().addAll(destination);
        origin.getItems().addAll(origins);
        desti.setValue(((ArrayList<String>) destination).get(0));
        origin.setValue(((ArrayList<String>) origins).get(0));
    }
    public void clickI(MouseEvent mouseEvent) {
        List<Control> emptyList = checkInputs();
        resetInputsStyle();
        if(emptyList.size() == 0 ){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TourTicket.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 683, 400);
                HelloApplication.primaryStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println(emptyList);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill the required fields", ButtonType.OK);
            alert.show();
            for (Control field : emptyList) {
                field.setStyle("-fx-border-color: red");
            }
        }
    }

    private List<Control> checkInputs(){
        List<Control> emptyInputs = new LinkedList<>();

        if(origin.getValue() == null){
            emptyInputs.add(origin);
        }
        if(desti.getValue() == null){
            emptyInputs.add(desti);
        }
        if(cnt.getText().isEmpty()){
            emptyInputs.add(cnt);
        }
        if(room.getText().isEmpty()){
            emptyInputs.add(room);
        }
        if( exdate.getValue() == null){
            emptyInputs.add(exdate);
        }
        if( retdate.getValue()==null){
            emptyInputs.add(retdate);
        }

        return emptyInputs;
    }
    private void resetInputsStyle(){
        List<Control> controls = new LinkedList<>(Arrays.asList(origin, desti, exdate,retdate,cnt,room));
        for (Control control : controls) {
            control.setStyle("");
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
