package com.dbms.alibabaphase3;

import Model.Airport;
import Model.Hotel;
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

public class HotelController implements Initializable {
    @FXML
    private ChoiceBox<HotelView> name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Hotel> hotels = RepositoryFacade.getInstance().findAll(Hotel.class);
        List<HotelView> hotelViews = new LinkedList<>();
        for (Hotel hotel:hotels) {
            HotelView hotelView = new HotelView(hotel.getCity(),hotel.getId());
            hotelViews.add(hotelView);
        }
        ObservableList<HotelView> observableList = FXCollections.observableList(hotelViews);
        name.setItems(observableList);
        name.setValue(observableList.get(0));

    }
    public void clickI(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HotelReserve.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
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
