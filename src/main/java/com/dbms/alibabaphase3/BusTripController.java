package com.dbms.alibabaphase3;

import Model.Airport;
import Model.BusTrip;
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

public class BusTripController implements Initializable {
    @FXML
    private ChoiceBox<BusTripView> origin;
    @FXML
    private ChoiceBox<BusTripView> desti;
    @FXML
    private DatePicker exdate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<BusTrip> busTrips = RepositoryFacade.getInstance().findAll(BusTrip.class);
        List<BusTripView> busTripViews = new LinkedList<>();
        List<BusTripView> busTripViews2 = new LinkedList<>();
        for (BusTrip busTrip:busTrips) {
            BusTripView b = new BusTripView(busTrip.getOriginTerminal(), busTrip.getId());
            busTripViews.add(b);
            BusTripView b2 = new BusTripView(busTrip.getDestination(), busTrip.getId());
            busTripViews2.add(b2);
        }
        ObservableList<BusTripView> observableList = FXCollections.observableList(busTripViews);
        ObservableList<BusTripView> observableList2 = FXCollections.observableList(busTripViews2);
        origin.setItems(observableList);
        desti.setItems(observableList2);
        desti.setValue(observableList2.get(0));
        origin.setValue(observableList.get(0));

    }
    private List<Control> checkInputs(){
        List<Control> emptyInputs = new LinkedList<>();
        if(origin.getValue() == null){
            emptyInputs.add(origin);
        }
        if(desti.getValue() == null){
            emptyInputs.add(desti);
        }

        if( exdate.getValue() == null){
            emptyInputs.add(exdate);
        }

        return emptyInputs;
    }
    private void resetInputsStyle(){
        List<Control> controls = new LinkedList<>(Arrays.asList(origin, desti, exdate));
        for (Control control : controls) {
            control.setStyle("");
        }
    }

    public void clickI(MouseEvent mouseEvent) {
        List<Control> emptyList = checkInputs();
        resetInputsStyle();
        if(emptyList.size() == 0 ){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BusTicket.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 683, 400);
                BusTicketController controller = fxmlLoader.getController();
                controller.setInitial(origin.getValue(),desti.getValue(),exdate.getValue());
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
