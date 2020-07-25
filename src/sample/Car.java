package sample;

public class Car {

    private int id;
    private String manufacturer;
    private String model;
    private String description;
    private int price;
    private Customer customer;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car(int id, String manufacturer, String model, String description, int price, Customer customer) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.description = description;
        this.price = price;
        this.customer = customer;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() { return manufacturer; }
}
