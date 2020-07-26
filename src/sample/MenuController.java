package sample;

//fx:controller="sample.MenuController"

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MenuController {

    public void carcustomerdatabase(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carcustomerdatabase.fxml"));
            CarCustomerDatabaseController carCustomerDatabaseController = new CarCustomerDatabaseController();
            loader.setController(carCustomerDatabaseController);
            root = loader.load();
            stage.setTitle("Car/Customer database");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void returnCar(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        ReturnModel model = new ReturnModel();
        model.napuni();
        ReturnController ctrl = new ReturnController(model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/return.fxml"));
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Return");
        primaryStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        primaryStage.show();
        primaryStage.setResizable(false);
        //primaryStage.toFront();
    }


    public void renting(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/renting2.fxml"));
            RentingController rentingController = new RentingController();
            loader.setController(rentingController);
            root = loader.load();
            stage.setTitle("Renting");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void previousWindow(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginform.fxml"));
            LoginController loginController = new LoginController();
            loader.setController(loginController);
            root = loader.load();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }
}
