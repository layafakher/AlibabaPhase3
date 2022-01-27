package com.dbms.alibabaphase3;

import Model.Hotel;
import Repository.DAO.HotelDAO;
import Repository.DAO.RepositoryFacade;
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

public class HotelReserveController {

    @FXML
    private TableView<HotelView> hotels;
    @FXML
    private TableColumn hotelName;
    @FXML
    private TableColumn residenceType;
    @FXML
    private TableColumn starCount;
    @FXML
    private TableColumn location1;
    @FXML
    private TableColumn city;
    @FXML
    private TableColumn price;
    @FXML
    private TableColumn reserve;

    public static String cityName;
    public static int passengerCount;
    public static int roomCount;
    public static LocalDate entryDate;
    public static LocalDate exitDate;

    public void back(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Hotel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInitial(String cityName, int passengerCount, int roomCount, LocalDate entryDate, LocalDate exitDate){
        setTableView();
        this.cityName = cityName;
        this.passengerCount = passengerCount;
        this.roomCount = roomCount;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        setData(cityName);
    }

    private void setData(String cityName){
        List<Hotel> userHotels = ((HotelDAO) RepositoryFacade.getInstance().getDao(Hotel.class)).findByData(cityName);
        List<HotelView> hotelViews = new LinkedList<>();
        for (Hotel hotel : userHotels) {
            double price = hotel.getMinPrice() + hotel.getMaxPrice() * passengerCount / 2;
            HotelView hotelView = new HotelView(
                    hotel.getName(),hotel.getResidenceType(),String.valueOf(hotel.getRating()),hotel.getLocation(),hotel.getCity(),String.valueOf(price)
            );
            hotelView.setPriceDouble(price);
            hotelView.setId(hotel.getId());
            hotelViews.add(hotelView);
        }
        ObservableList<HotelView> observableList = FXCollections.observableList(hotelViews);
        hotels.setItems(observableList);
    }

    public void setTableView(){
        hotelName.setCellValueFactory(new PropertyValueFactory<HotelView, String>("name"));
        residenceType.setCellValueFactory(new PropertyValueFactory<HotelView, String>("residenceType"));
        starCount.setCellValueFactory(new PropertyValueFactory<HotelView, String>("rating"));
        location1.setCellValueFactory(new PropertyValueFactory<HotelView, String>("location"));
        city.setCellValueFactory(new PropertyValueFactory<HotelView, String>("hotelCity"));
        price.setCellValueFactory(new PropertyValueFactory<HotelView, String>("price"));
        reserve.setCellValueFactory(new PropertyValueFactory<HotelView, String>("reserve"));
    }
}
