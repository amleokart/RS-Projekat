package sample;

//fx:controller="sample.CustomerMainController"

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class CustomerMainController {

    public TableView tableCustomers;
    public TableColumn colID;
    public TableColumn colSurname;
    public TableColumn colName;
    public TableColumn colAdress;
    public TableColumn colMobile;
    public TableColumn colEmail;
    public TableColumn colCustomer;
    public Button btnBack;
    public Button btnExit;
    public Button addCustomerBtn;
    public Button editCustomerBtn;
    public Button deleteCustomerBtn;

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

    public void exitWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void addCustomer(ActionEvent actionEvent) {
    }

    public void editCustomer(ActionEvent actionEvent) {
    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }
}
