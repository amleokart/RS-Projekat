package sample;

//fx:controller="sample.CarController"

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CarController {
    public TextField fieldManufacturer;
    public TextField fieldModel;
    public TextField fieldDescription;
    public TextField fieldPrice;
    public ChoiceBox<Customer> choiceCustomer;
    public ObservableList<Customer> listCustomers;
    private Car car;

    public CarController(Car car, ArrayList<Customer> drzave) {
        this.car = car;
        listCustomers = FXCollections.observableArrayList(drzave);
    }

    @FXML
    public void initialize() {
        choiceCustomer.setItems(listCustomers);
        if (car != null) {
            fieldManufacturer.setText(car.getManufacturer());
            fieldModel.setText(car.getModel());
            fieldDescription.setText(car.getDescription());
            fieldPrice.setText(Integer.toString(car.getPrice()));
            for (Customer customer : listCustomers)
                if (customer.getId() == car.getCustomer().getId())
                    choiceCustomer.getSelectionModel().select(customer);
        } else {
            choiceCustomer.getSelectionModel().selectFirst();
        }
    }

    public Car getCar() {
        return car;
    }

    public void clickCancel(ActionEvent actionEvent) {
        car = null;
        Stage stage = (Stage) fieldManufacturer.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean correct = true;

        if (fieldManufacturer.getText().trim().isEmpty()) {
            fieldManufacturer.getStyleClass().removeAll("correctly");
            fieldManufacturer.getStyleClass().add("incorrectly");
            correct = false;
        } else {
            fieldManufacturer.getStyleClass().removeAll("incorrectly");
            fieldManufacturer.getStyleClass().add("correctly");
        }

        if (fieldModel.getText().trim().isEmpty()) {
            fieldModel.getStyleClass().removeAll("correctly");
            fieldModel.getStyleClass().add("incorrectly");
            correct = false;
        } else {
            fieldModel.getStyleClass().removeAll("incorrectly");
            fieldModel.getStyleClass().add("correctly");
        }

        if (fieldDescription.getText().trim().isEmpty()) {
            fieldDescription.getStyleClass().removeAll("correctly");
            fieldDescription.getStyleClass().add("incorrectly");
            correct = false;
        } else {
            fieldDescription.getStyleClass().removeAll("incorrectly");
            fieldDescription.getStyleClass().add("correctly");
        }

        int price = 0;
        try {
            price = Integer.parseInt(fieldPrice.getText());
        } catch (NumberFormatException e) {
        }
        if (price <= 0) {
            fieldPrice.getStyleClass().removeAll("correctly");
            fieldPrice.getStyleClass().add("incorrectly");
            correct = false;
        } else {
            fieldPrice.getStyleClass().removeAll("incorrectly");
            fieldPrice.getStyleClass().add("correctly");
        }
        if (!correct) return;
        if (car == null) car = new Car();
        car.setManufacturer(fieldManufacturer.getText());
        car.setModel(fieldModel.getText());
        car.setDescription(fieldDescription.getText());
        car.setPrice(Integer.parseInt(fieldPrice.getText()));
        car.setCustomer(choiceCustomer.getValue());
        Stage stage = (Stage) fieldManufacturer.getScene().getWindow();
        stage.close();
    }
}