package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ReturnModel {

    private ObservableList<Return> returnCar = FXCollections.observableArrayList();
    private SimpleObjectProperty<Return> currentCar = new SimpleObjectProperty<>();

    public ReturnModel() {
    }

    public ObservableList<Return> getReturnCar() {
        return returnCar;
    }

    public void setReturnCar(ObservableList<Return> returnCar) {
        this.returnCar=returnCar;
    }

    public Return getCurrentCar() {
        return currentCar.get();
    }

    public SimpleObjectProperty<Return> currentCarProperty() {
        return currentCar;
    }

    //ovo
    public void setCurrentCar(Return currentCar) {
        this.currentCar.set(currentCar);
    }


    public void napuni() {
        returnCar.add(new Return(1,2, LocalDate.of(2020,5,14), 81));
        returnCar.add(new Return(2,3, LocalDate.of(2020,6,9), 190));
        //currentCar.set(null);
    }

    public void addReturn() {
        returnCar.add(new Return());
    }
}
