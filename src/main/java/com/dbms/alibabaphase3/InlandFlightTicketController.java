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

public class InlandFlightTicketController {

    @FXML
    private TableView<ForeignFlightView> inlandFlights;
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


    public static String type;
    public static AirportView origins;
    public static AirportView destinations;
    public static int passengerCount;
    public static LocalDate entryDate;
    public static LocalDate exitDate;

    public void back(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InlandFlight.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInitial(String raft, AirportView origin, AirportView destination, int passengerCount, LocalDate entryDate, LocalDate exitDate){
        setTableView();
        this.type = raft;
        this.passengerCount = passengerCount;
        this.origins = origin;
        this.destinations = destination;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        boolean isOneWay;
        if (raft =="یک طرفه"){
            isOneWay = true;
        }else{
            isOneWay = false;
        }
        setData(origin.getCity(),destination.getCity(),isOneWay);
    }

    public void setTableView(){
        air.setCellValueFactory(new PropertyValueFactory<ForeignFlightView, String>("airline_company"));
        origin.setCellValueFactory(new PropertyValueFactory<ForeignFlightView, String>("origin"));
        destination.setCellValueFactory(new PropertyValueFactory<ForeignFlightView, String>("destination"));
        exdate.setCellValueFactory(new PropertyValueFactory<ForeignFlightView, String>("exitDate"));
        retdate.setCellValueFactory(new PropertyValueFactory<ForeignFlightView, String>("returnDate"));
        price.setCellValueFactory(new PropertyValueFactory<ForeignFlightView, String>("price"));
        capacity.setCellValueFactory(new PropertyValueFactory<ForeignFlightView, String>("capacity"));
        reserve.setCellValueFactory(new PropertyValueFactory<ForeignFlightView, String>("reserve"));
    }

    private void setData(String origin,String destination,boolean type){

        List<InlandFlight> foreignFlights = ((InlandFlightDAO) RepositoryFacade.getInstance().getDao(InlandFlight.class)).findByData(origin,destination,type);
        List<TripReserve> tripReserves = ((TripReserveDAO) RepositoryFacade.getInstance().getDao(TripReserve.class)).findAll();
        List<AirlineCompany> airlineCompanies = ((AirlineCompanyDAO) RepositoryFacade.getInstance().getDao(AirlineCompany.class)).findAll();
        List<FlightAirline> flightAirlines = ((FlightAirlineDAO) RepositoryFacade.getInstance().getDao(FlightAirline.class)).findAll();
        List<ForeignFlightView> foreignFlightViews = new LinkedList<>();
        for (InlandFlight inlandFlight : foreignFlights) {
            double price = Math.random()*10000 + 1;
            String airline_company = "";
            int cnt = passengerCount;
//            for(TripReserve tripReserve:tripReserves){
//                if (tripReserve.getTripId()==foreignFlight.getId()){
//                    price = (tripReserve.getPrice()) * passengerCount ;
//                    cnt = tripReserve.getPassengerCont();
//                }
//            }
            for (AirlineCompany airlineCompany:airlineCompanies){
                for (FlightAirline flightAirline:flightAirlines){
                    if (Objects.equals(flightAirline.getId(), airlineCompany.getId()) && flightAirline.getId()== inlandFlight.getId()){
                        airline_company = airlineCompany.getAirlineName();
                    }
                }
            }
            ForeignFlightView view = new ForeignFlightView(inlandFlight.getDepartureTime()+"", inlandFlight.getDepartureTime()+"",String.valueOf(price),String.valueOf(inlandFlight.getCapacity()), inlandFlight.getOrigin(), inlandFlight.getDestination(),airline_company);
            view.setId(inlandFlight.getId());
            view.setPrice(price+"");
            view.setAmount(String.valueOf(cnt));
            foreignFlightViews.add(view);
        }
        ObservableList<ForeignFlightView> observableList = FXCollections.observableList(foreignFlightViews);
        this.inlandFlights.setItems(observableList);
    }
}
