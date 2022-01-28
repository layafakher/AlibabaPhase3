package com.dbms.alibabaphase3;

import Model.*;
import Repository.DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BusTicketController {

    @FXML
    private TableView<BusTripView> busTrips;
    @FXML
    private TableColumn air;
    @FXML
    private TableColumn origin;
    @FXML
    private TableColumn exdate;
    @FXML
    private TableColumn destination;
    @FXML
    private TableColumn retdate;
    @FXML
    private TableColumn price;
    @FXML
    private TableColumn capacity;
    @FXML
    private TableColumn reserve;

    public static BusTripView origins;
    public static BusTripView destinations;
    public static LocalDate exitDate;

    public void back(MouseEvent mouseEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BusTrip.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInitial(BusTripView origin, BusTripView destination, LocalDate exitDate){
        setTableView();
        this.origins = origin;
        this.destinations = destination;
        this.exitDate = exitDate;
        boolean isOneWay;

        setData(origin.getCity(),destination.getCity());
    }

    public void setTableView(){
        air.setCellValueFactory(new PropertyValueFactory<BusTripView, String>("airline_company"));
        origin.setCellValueFactory(new PropertyValueFactory<BusTripView, String>("origin"));
        destination.setCellValueFactory(new PropertyValueFactory<BusTripView, String>("destination"));
        exdate.setCellValueFactory(new PropertyValueFactory<BusTripView, String>("exitDate"));
        retdate.setCellValueFactory(new PropertyValueFactory<BusTripView, String>("returnDate"));
        price.setCellValueFactory(new PropertyValueFactory<BusTripView, String>("price"));
        capacity.setCellValueFactory(new PropertyValueFactory<BusTripView, String>("capacity"));
        reserve.setCellValueFactory(new PropertyValueFactory<BusTripView, String>("reserve"));
    }

    private void setData(String origin,String destination){

        List<BusTrip> busTrips = ((BusTripDAO) RepositoryFacade.getInstance().getDao(BusTrip.class)).findByData(origin,destination);
        List<BusCompany> trips = ((BusCompanyDAO) RepositoryFacade.getInstance().getDao(BusCompany.class)).findAll();
        List<BusTripView> busTripViews = new LinkedList<>();
        for (BusTrip busTrip : busTrips) {
            double price = Math.random()*10000 + 1;
            String airline_company = "";
                for (BusCompany busCompany:trips){
                    if (busCompany.getId()==busTrip.getBusCompanyId()){
                        airline_company = busCompany.getName();
                    }
                }
            BusTripView view = new BusTripView(busTrip.getDepartureTime()+"", busTrip.getDepartureTime()+"",String.valueOf(price),String.valueOf(busTrip.getCapacity()), busTrip.getOrigin(), busTrip.getDestination(),airline_company);
            view.setId(busTrip.getId());
            view.setPrice(price+"");
            busTripViews.add(view);
        }
        ObservableList<BusTripView> observableList = FXCollections.observableList(busTripViews);
        this.busTrips.setItems(observableList);
    }
}
