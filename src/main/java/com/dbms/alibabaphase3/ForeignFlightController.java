package com.dbms.alibabaphase3;

import Model.Airport;
import Repository.DAO.RepositoryFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ForeignFlightController implements Initializable {
    @FXML
    private ChoiceBox<String> raft;
    @FXML
    private ChoiceBox<String> eco;
    @FXML
    private ChoiceBox<AirportView> origin;
    @FXML
    private ChoiceBox<AirportView> desti;
    @FXML
    private Button search;
    @FXML
    private Button ret;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Airport> airports = RepositoryFacade.getInstance().findAll(Airport.class);
        List<AirportView> airportViews = new LinkedList<>();
        for (Airport airport:airports) {
            AirportView airportView = new AirportView(airport.getName() + " ("+ airport.getCountry()+")", airport.getId());
            airportViews.add(airportView);
        }
        ObservableList<AirportView> observableList = FXCollections.observableList(airportViews);
        origin.setItems(observableList);
        raft.getItems().addAll("یک طرفه", "رفت و برگشت", "چند مسیره");
        raft.setValue("رفت و برگشت");
        eco.getItems().addAll( "اکونومی", "فرست کلاس");
        eco.setValue("اکونومی");
        desti.setItems(observableList);
        desti.setValue(observableList.get(0));
        origin.setValue(observableList.get(0));

    }

    public void click(MouseEvent mouseEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ForeignFlightsTickets.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clickReturn(MouseEvent mouseEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
