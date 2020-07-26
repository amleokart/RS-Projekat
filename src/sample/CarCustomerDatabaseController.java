package sample;

//fx:controller="sample.CarCustomerDatabaseController"

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class CarCustomerDatabaseController {

    public TableView<Car> tableViewCars;
    public TableColumn colId;
    public TableColumn colManufacturer;
    public TableColumn colModel;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn<Car,String> colCustomer;
    private CarCustomerDAO dao;
    private ObservableList<Car> listCars;

    public CarCustomerDatabaseController() {
        dao = CarCustomerDAO.getInstance();
        listCars = FXCollections.observableArrayList(dao.cars());
    }

    @FXML
    public void initialize() {
        tableViewCars.setItems(listCars);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colManufacturer.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        colModel.setCellValueFactory(new PropertyValueFactory("model"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        //colCustomer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getName()));
    }

    public void actionAddCar(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/car.fxml"));
            CarController carController = new CarController(null, dao.customers());
            loader.setController(carController);
            root = loader.load();
            stage.setTitle("Add car");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Car car = carController.getCar();
                if (car != null) {
                    dao.addCar(car);
                    listCars.setAll(dao.cars());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionAddCustomer(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customer.fxml"));
            CustomerController customerController = new CustomerController(null, dao.cars());
            loader.setController(customerController);
            root = loader.load();
            stage.setTitle("Add customer");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Customer customer = customerController.getCustomer();
                if (customer != null) {
                    dao.addCustomer(customer);
                    listCars.setAll(dao.cars());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionEditCar(ActionEvent actionEvent) {
        Car car = tableViewCars.getSelectionModel().getSelectedItem();
        if (car == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/car.fxml"));
            CarController carController = new CarController(car, dao.customers());
            loader.setController(carController);
            root = loader.load();
            stage.setTitle("Edit car");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Car newCar = carController.getCar();
                if (newCar != null) {
                    dao.editCar(newCar);
                    listCars.setAll(dao.cars());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionEditCustomer(ActionEvent actionEvent) {
        Car car = tableViewCars.getSelectionModel().getSelectedItem();
        if (car == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customer.fxml"));
            CustomerController customerController = new CustomerController(car.getCustomer(), dao.cars());
            loader.setController(customerController);
            root = loader.load();
            stage.setTitle("Edit customer");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Customer customer = customerController.getCustomer();
                if (customer != null) {
                    dao.editCustomer(customer);
                    listCars.setAll(dao.cars());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void resetujBazu() {
        CarCustomerDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        dao = CarCustomerDAO.getInstance();
    }


    public void actionDeleteCar(ActionEvent actionEvent) {
        Car car = tableViewCars.getSelectionModel().getSelectedItem();
        if (car == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Deleting a vehicle " + car.getManufacturer());
        alert.setContentText("Are you sure you want to delete " + car.getManufacturer() + "?");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteCar(car);
            listCars.setAll(dao.cars());
        }
    }



    public void actionDeleteCustomer(ActionEvent actionEvent) {
        Car customer = tableViewCars.getSelectionModel().getSelectedItem();
        if (customer == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText("Deleting a customer " + customer.getCustomer());
        alert.setContentText("Are you sure you want to delete " + customer.getCustomer() + "?");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteCustomer(String.valueOf(customer));
            listCars.setAll(dao.cars());
        }
    }

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
}
