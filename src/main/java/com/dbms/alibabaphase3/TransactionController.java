package com.dbms.alibabaphase3;

import Model.Transaction;
import Repository.DAO.RepositoryFacade;
import Repository.DAO.TransactionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TransactionController {

    @FXML
    private TableView<TransactionView> transactionTable;
    @FXML
    private Label usernameLbl;

    @FXML
    private TextField chargeAmountInput;

    private ObservableList<TransactionView> observableList;

    public void onReturnToMainMenuButtonClick(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAccountButtonClick(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Account.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPerformOperationButtonClick(MouseEvent mouseEvent) {
        if(chargeAmountInput.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please enter the amount",ButtonType.OK);
            alert.show();
        }
        else{
            Transaction transaction = new Transaction();
            transaction.setUserId(Info.getInstance().getUser().getId());
            transaction.setType("شارژ حساب");
            try{
                transaction.setAmount(Double.parseDouble(chargeAmountInput.getText()));
            }catch (Exception e){
                return;
            }
            transaction.setTransTime(new Date());
            RepositoryFacade.getInstance().save(transaction,Transaction.class);
            setTableView();
        }
    }

    public void setTableView(){
        TableColumn transTimeCol = new TableColumn("زمان تراکنش");
        transTimeCol.setPrefWidth(228);
        transTimeCol.setCellValueFactory(new PropertyValueFactory<TransactionView, String>("transTime"));
        TableColumn amountCol = new TableColumn("مقدار تراکنش");
        amountCol.setPrefWidth(219);
        amountCol.setCellValueFactory(new PropertyValueFactory<TransactionView, String>("amount"));
        TableColumn typeCol = new TableColumn("نوع تراکنش");
        typeCol.setPrefWidth(126);
        typeCol.setCellValueFactory(new PropertyValueFactory<TransactionView, String>("type"));
        List<Transaction> userTransactions = ((TransactionDAO) RepositoryFacade.getInstance().getDao(Transaction.class)).findByUserId(Info.getInstance().getUser().getId());
        List<TransactionView> transactionViews = new LinkedList<>();
        for (Transaction transaction : userTransactions) {
            TransactionView transactionView = new TransactionView(
                    transaction.getTransTime().toString(),String.valueOf(transaction.getAmount()),transaction.getType()
            );
            transactionViews.add(transactionView);
        }
        observableList = FXCollections.observableList(transactionViews);
        transactionTable.setItems(observableList);
        transactionTable.getColumns().addAll(transTimeCol, amountCol, typeCol);
    }

    public void setUsername(){
        this.usernameLbl.setText(Info.getInstance().getUser().getName());
    }

    @FXML
    void initialize() {
        setTableView();
        setUsername();
    }
}
