package com.dbms.alibabaphase3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InlandFlightController implements Initializable {
    @FXML
    private ChoiceBox<String> raft;
    @FXML
    private ChoiceBox<String> origin;
    @FXML
    private ChoiceBox<String> desti;
    @FXML
    private Button search;
    @FXML
    private Button ret;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        raft.getItems().addAll("یک طرفه", "رفت و برگشت");
        raft.setValue("یک طرفه");

        origin.getItems().addAll( "دبی (امارات متحده عربی)", "امام خمینی (ایران)","ایروان (ارمنستان)");
        origin.setValue("امام خمینی (ایران)");
        desti.getItems().addAll( "آنکارا (ترکیه)", "دبی (امارات متحده عربی)","تورنتو (کانادا)","فرانکفورت (آلمان)");
        desti.setValue("آنکارا (ترکیه)");

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
        System.out.println(1);
    }
}
