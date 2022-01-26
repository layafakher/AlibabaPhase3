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

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class InlandFlightController implements Initializable {
    @FXML
    private ChoiceBox<String> raft;
    @FXML
    private ChoiceBox<AirportView> origin;
    @FXML
    private ChoiceBox<AirportView> desti;
    @FXML
    private Button search;
    @FXML
    private Button ret;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Airport> airports = RepositoryFacade.getInstance().findAll(Airport.class);
        List<AirportView> airportViews = new LinkedList<>();
        for (Airport airport:airports) {
            if (airport.getCountry().equals("Iran")){
                AirportView airportView = new AirportView(airport.getName() + " ("+ airport.getCity()+")", airport.getId());
                airportViews.add(airportView);
            }
        }
        ObservableList<AirportView> observableList = FXCollections.observableList(airportViews);
        origin.setItems(observableList);
        desti.setItems(observableList);
        raft.getItems().addAll("یک طرفه", "رفت و برگشت");
        raft.setValue("یک طرفه");
        desti.setValue(observableList.get(0));
        origin.setValue(observableList.get(0));
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

    public void clickI(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InlandFlightTicket.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
