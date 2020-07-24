package sample;

//fx:controller="sample.ReturnController"

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.Date;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ReturnController {
    public TextField carIDFld;
    public TextField customerIDFld;
    public TextField paidFld;
    public DatePicker date;
    public TableView returnTable;
    private ReturnModel model;
    private ReturnModel returnCar;
    public TableColumn<Return, Integer> carIDColumn;
    public TableColumn<Return, Integer> customerIDColumn;
    public TableColumn<Return, Date> dateColumn;
    public TableColumn<Return, Integer> paidColumn;

    public ReturnController(ReturnModel m) {
        model=m;
    }

    private void unbindbidirectional() {
        carIDFld.textProperty().unbindBidirectional(model.getCurrentCar().carIDProperty());
        customerIDFld.textProperty().unbindBidirectional(model.getCurrentCar().customerIDProperty());
        date.valueProperty().unbindBidirectional(model.getCurrentCar().dateProperty());
        paidFld.textProperty().unbindBidirectional(model.getCurrentCar().paidProperty());
    }

    private void bindbidirectional() {
        carIDFld.textProperty().bindBidirectional(model.getCurrentCar().carIDProperty(), new NumberStringConverter("#"));
        customerIDFld.textProperty().bindBidirectional(model.getCurrentCar().customerIDProperty(), new NumberStringConverter("#"));
        date.getEditor().textProperty().bindBidirectional(model.getCurrentCar().dateProperty(), new LocalDateStringConverter());
        paidFld.textProperty().bindBidirectional(model.getCurrentCar().paidProperty(), new NumberStringConverter());
    }

    public void initialize() {
        model.setCurrentCar(model.getReturnCar().get(0));
        bindbidirectional();
        returnTable.setItems(model.getReturnCar());
        carIDColumn.setCellValueFactory(new PropertyValueFactory("CarID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory("CustomerID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory("Date"));
        paidColumn.setCellValueFactory(new PropertyValueFactory("Paid"));
        returnTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Return>() {
            @Override
            public void changed(ObservableValue<? extends Return> observableValue, Return oldReturn, Return newReturn) {
                if(oldReturn != null) {
                    unbindbidirectional();
                }
                if(newReturn == null) {
                    carIDFld.setText("");
                    customerIDFld.setText("");
                    //date.setValue(null);
                    date.getEditor().setText("");
                    paidFld.setText("");
                }
                else {
                    Return reeturn = (Return) returnTable.getSelectionModel().getSelectedItem();
                    unbindbidirectional();
                    model.setCurrentCar(reeturn);
                    bindbidirectional();
                    returnTable.refresh();
                }
                returnTable.refresh();
            }
        });
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

    //dodaje u tabelu
    public void submit(ActionEvent actionEvent) {
        model.addReturn();
        unbindbidirectional();
        model.setCurrentCar(model.getReturnCar().get(model.getReturnCar().size()-1));
        bindbidirectional();
        returnTable.refresh();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
