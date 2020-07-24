package sample;

//fx:controller="sample.RentingController"

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RentingController {
    public Button backBtn;
    public Button exitBtn;
    public Button rent1Btn;
    public Button rent2Btn;
    //drugi prozor
    public Button back2Btn;
    public Button btnSubmit;
    public TextField nameCustomerFld;
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField mailFld;

    //nazad na menu
    public void previousWindow(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            MenuController menuController = new MenuController();
            loader.setController(menuController);
            root = loader.load();
            stage.setTitle("Menu");
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

    //prvo auto
    public void rent1(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/renting.fxml"));
            RentingController rentingController = new RentingController();
            loader.setController(rentingController);
            root = loader.load();
            stage.setTitle("Rent");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //drugo auto
    public void rent2(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/renting.fxml"));
            RentingController rentingController = new RentingController();
            loader.setController(rentingController);
            root = loader.load();
            stage.setTitle("Rent");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //nazad na katalog
    public void previous2Window(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/renting2.fxml"));
            RentingController rentingController = new RentingController();
            loader.setController(rentingController);
            root = loader.load();
            stage.setTitle("Rent");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submit(ActionEvent actionEvent) {

        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        String email = mailFld.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reservation confirmation");
        alert.setHeaderText("You have booked a vehicle from " + start + " to " + end + ".");
        alert.setContentText("Are you sure you want to confirm the reservation?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Alert alertt = new Alert(Alert.AlertType.INFORMATION);
            alertt.setTitle("Reservation information");
            alertt.setHeaderText(null);
            alertt.setContentText("Your reservation was successful. You will receive the rest of the information via your e-mail " + email + ".");
            alertt.showAndWait();
        } else {
            Alert alertt = new Alert(Alert.AlertType.INFORMATION);
            alertt.setTitle("Reservation information");
            alertt.setHeaderText(null);
            alertt.setContentText("Your reservation has been canceled.");
            alertt.showAndWait();
        }
    }

    public void exit2Window(ActionEvent actionEvent) {
        Platform.exit();
    }
}
