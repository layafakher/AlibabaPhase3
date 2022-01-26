package com.dbms.alibabaphase3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

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
    private TableColumn location;
    @FXML
    private TableColumn city;
    @FXML
    private TableColumn price;
    @FXML
    private TableColumn reserve;

    public void back(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Hotel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTable(){
        setTableView();
        setData();
    }

    private void setData(){
//        List<Hotel> userHotels = ((HotelDAO) RepositoryFacade.getInstance().getDao(Transaction.class)).findByUserId(Info.getInstance().getUser().getId());
//        List<TransactionView> transactionViews = new LinkedList<>();
//        for (Transaction transaction : userTransactions) {
//            TransactionView transactionView = new TransactionView(
//                    transaction.getTransTime().toString(),String.valueOf(transaction.getAmount()),transaction.getType()
//            );
//            transactionViews.add(transactionView);
//        }
//        hotels = FXCollections.observableList(transactionViews);
//        hotels.setItems(observableList);
    }

    public void setTableView(){
        hotelName.setCellValueFactory(new PropertyValueFactory<HotelView, String>("name"));
        residenceType.setCellValueFactory(new PropertyValueFactory<HotelView, String>("residenceType"));
        starCount.setCellValueFactory(new PropertyValueFactory<HotelView, String>("rating"));
        location.setCellValueFactory(new PropertyValueFactory<HotelView, String>("location"));
        city.setCellValueFactory(new PropertyValueFactory<HotelView, String>("hotelCity"));
        price.setCellValueFactory(new PropertyValueFactory<HotelView, String>("price"));
        reserve.setCellValueFactory(new PropertyValueFactory<HotelView, String>("reserve"));
    }
}
