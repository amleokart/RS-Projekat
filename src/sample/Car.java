package sample;

public class Car {

    private int id;
    private String manufacturer;
    private String model;
    private String description;
    private String fuel;
    private int price;

    public Customer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Customer customer_id) {
        this.customer_id = customer_id;
    }

    private Customer customer_id;

    public Car(int id, String manufacturer, String model, String description, String fuel, int price, Customer customer_id) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.description = description;
        this.fuel = fuel;
        this.price = price;
        this.customer_id = customer_id;
    }

    public Car() {}

    public Car(int id, String manufacturer, String model, String description, String fuel, int price) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.description = description;
        this.fuel = fuel;
        this.price = price;
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

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
