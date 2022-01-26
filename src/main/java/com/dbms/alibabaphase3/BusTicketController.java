package com.dbms.alibabaphase3;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class BusTicketController {
    public void back(MouseEvent mouseEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BusTrip.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
