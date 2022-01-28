package com.dbms.alibabaphase3;

import Model.Airport;
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
import java.util.Arrays;
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
    @FXML
    private DatePicker exdate;
    @FXML
    private DatePicker retdate;
    @FXML
    private TextField cnt;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Airport> airports = RepositoryFacade.getInstance().findAll(Airport.class);
        List<AirportView> airportViews = new LinkedList<>();
        for (Airport airport:airports) {
            if (airport.getCountry().equals("Iran")){
                AirportView airportView = new AirportView(airport.getName() + " ("+ airport.getCity()+")",airport.getCity(),airport.getCountry(), airport.getId());
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

    private List<Control> checkInputs(){
        List<Control> emptyInputs = new LinkedList<>();
        if(raft.getValue() == null){
            emptyInputs.add(raft);
        }
        if(origin.getValue() == null){
            emptyInputs.add(origin);
        }
        if(desti.getValue() == null){
            emptyInputs.add(desti);
        }
        if(cnt.getText().isEmpty()){
            emptyInputs.add(cnt);
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
        List<Control> controls = new LinkedList<>(Arrays.asList(raft,  origin, desti, exdate,retdate,cnt));
        for (Control control : controls) {
            control.setStyle("");
        }
    }

    public void clickI(MouseEvent mouseEvent) {
        List<Control> emptyList = checkInputs();
        resetInputsStyle();
        if(emptyList.size() == 0 ){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InlandFlightTicket.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 683, 400);
                InlandFlightTicketController controller = fxmlLoader.getController();
                controller.setInitial(raft.getValue(),origin.getValue(),desti.getValue(),Integer.parseInt(cnt.getText()), retdate.getValue(),exdate.getValue());

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
}
