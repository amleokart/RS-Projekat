package sample;

//fx:controller="sample.CustomerController"

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CustomerController {

    public ChoiceBox choiceCustomer;
    public TextField IDFld;
    public TextField manufacturerFld;
    public TextField modelFld;
    public TextField descriptionFld;
    public TextField fuelFld;
    public TextField nameFld;
    public Button btnSubmit;
    public Button btnCancel;
    public TextField surnameFld;
    public TextField adressFld;
    public TextField mobileFld;
    public TextField emailFld;

    public void submit(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
        Platform.exit();
    }
}
