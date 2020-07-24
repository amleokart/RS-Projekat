package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CarController {

    public TextField registrationFld;
    public TextField manufacturerFld;
    public TextField modelFld;
    public TextField descriptionFld;
    public TextField fuelFld;
    public TextField priceFld;
    public ChoiceBox<Customer> choiceCustomer;
    public ObservableList<Customer> listCustomer;
    private Car car;

    public CarController(Car car, ArrayList<Customer> customers) {
        this.car = car;
        listCustomer = FXCollections.observableArrayList(customers);
    }

    @FXML
    public void initialize() {
        choiceCustomer.setItems(listCustomer);
        if (car != null) {
            manufacturerFld.setText(car.getManufacturer());
            modelFld.setText(car.getModel());
            descriptionFld.setText(car.getDescription());
            fuelFld.setText(car.getFuel());
            priceFld.setText(Integer.toString(car.getPrice()));
            for (Customer customer : listCustomer)
                if (customer.getId() == car.getCustomer_id().getId())
                    choiceCustomer.getSelectionModel().select(customer);
        } else {
            choiceCustomer.getSelectionModel().selectFirst();
        }
    }

    public Car getCar() {
        return car;
    }


    public void submit(ActionEvent actionEvent) {
        boolean correct = true;

        if (manufacturerFld.getText().trim().isEmpty()) {
            manufacturerFld.getStyleClass().removeAll("poljeIspravno");
            manufacturerFld.getStyleClass().add("poljeNijeIspravno");
            correct = false;
        } else {
            manufacturerFld.getStyleClass().removeAll("poljeNijeIspravno");
            manufacturerFld.getStyleClass().add("poljeIspravno");
        }

        if (modelFld.getText().trim().isEmpty()) {
            modelFld.getStyleClass().removeAll("poljeIspravno");
            modelFld.getStyleClass().add("poljeNijeIspravno");
            correct = false;
        } else {
            modelFld.getStyleClass().removeAll("poljeNijeIspravno");
            modelFld.getStyleClass().add("poljeIspravno");
        }

        if (descriptionFld.getText().trim().isEmpty()) {
            descriptionFld.getStyleClass().removeAll("poljeIspravno");
            descriptionFld.getStyleClass().add("poljeNijeIspravno");
            correct = false;
        } else {
            descriptionFld.getStyleClass().removeAll("poljeNijeIspravno");
            descriptionFld.getStyleClass().add("poljeIspravno");
        }

        if (fuelFld.getText().trim().isEmpty()) {
            fuelFld.getStyleClass().removeAll("poljeIspravno");
            fuelFld.getStyleClass().add("poljeNijeIspravno");
            correct = false;
        } else {
            fuelFld.getStyleClass().removeAll("poljeNijeIspravno");
            fuelFld.getStyleClass().add("poljeIspravno");
        }

        if (priceFld.getText().trim().isEmpty()) {
            priceFld.getStyleClass().removeAll("poljeIspravno");
            priceFld.getStyleClass().add("poljeNijeIspravno");
            correct = false;
        } else {
            priceFld.getStyleClass().removeAll("poljeNijeIspravno");
            priceFld.getStyleClass().add("poljeIspravno");
        }


        if (!correct) return;

        if (car == null) car = new Car();
        car.setManufacturer(manufacturerFld.getText());
        car.setModel(modelFld.getText());
        car.setDescription(descriptionFld.getText());
        car.setFuel(fuelFld.getText());
        car.setPrice(Integer.parseInt(priceFld.getText()));
        car.setCustomer_id(choiceCustomer.getValue());
        Stage stage = (Stage) manufacturerFld.getScene().getWindow();
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        Platform.exit();
    }
}
