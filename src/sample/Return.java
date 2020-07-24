package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class Return {

    private SimpleIntegerProperty carID = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty customerID = new SimpleIntegerProperty(0);
    private SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private SimpleIntegerProperty paid = new SimpleIntegerProperty(0);

    public Return() { }

    public Return(int carID, int customerID, LocalDate date, int paid) {
        this.carID = new SimpleIntegerProperty(carID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.date = new SimpleObjectProperty<LocalDate>(date);
        this.paid = new SimpleIntegerProperty(paid);
    }

    public int getCarID() {
        return carID.get();
    }

    public SimpleIntegerProperty carIDProperty() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID.set(carID);
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public SimpleIntegerProperty customerIDProperty() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public int getPaid() {
        return paid.get();
    }

    public SimpleIntegerProperty paidProperty() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid.set(paid);
    }

    public String toString() {
        return getCarID() + " " + getCustomerID() + " " + getDate() + " " + getPaid();
    }

}
