package sample;

//fx:controller="sample.CarMainController"

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class CarMainController {

    public TableColumn colRegistrationCar;
    public TableColumn colManufacturerCar;
    public TableColumn colModelCar;
    public TableColumn colDescriptionCar;
    public TableColumn colFuelCar;
    public TableColumn colPriceCar;
    public TableView carTable;
    public TableColumn<Car, String> colCustomer;
    private CarCustomerDAO dao;
    private ObservableList<Car> listCars;

   /* public CarMainController() {
        dao = CarCustomerDAO.getInstance();
        listCars = FXCollections.observableArrayList(dao.cars());
    }

    @FXML
    public void initialize() {
        carTable.setItems(listCars);
        colRegistrationCar.setCellValueFactory(new PropertyValueFactory("ID"));
        colManufacturerCar.setCellValueFactory(new PropertyValueFactory("Manufacturer"));
        colModelCar.setCellValueFactory(new PropertyValueFactory("Model"));
        colDescriptionCar.setCellValueFactory(new PropertyValueFactory("Description"));
        colFuelCar.setCellValueFactory(new PropertyValueFactory("Fuel"));
        colPriceCar.setCellValueFactory(new PropertyValueFactory("Price(per day)"));
        colPriceCar.setCellValueFactory(new PropertyValueFactory("Customer"));
        //colCustomer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer_id().getId()));
    }*/

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

    public void addCar(ActionEvent actionEvent) {
        /*Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addCar.fxml"));
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
        }*/
    }

    public void editCar(ActionEvent actionEvent) {
        /*Car car = carTable.getSelectionModel().getSelectedItem();
        if (car == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addCar.fxml"));
            CarController gradController = new Car(car, dao.customers());
            loader.setController(gradController);
            root = loader.load();
            stage.setTitle("Grad");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Grad noviGrad = gradController.getGrad();
                if (noviGrad != null) {
                    dao.izmijeniGrad(noviGrad);
                    listGradovi.setAll(dao.gradovi());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void deleteCar(ActionEvent actionEvent) {

    }

    public void reset() {
        CarCustomerDAO.removeInstance();
        File dbfile = new File("database.db");
        dbfile.delete();
        dao = CarCustomerDAO.getInstance();
    }
}
