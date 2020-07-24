package sample;

public class Customer {

    private int id;
    private String name;
    private String surname;
    private String adress;
    private int mobile;
    private String email;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer(int id, String name, String surname, String adress, int mobile, String email, Car car) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.mobile = mobile;
        this.email = email;
        this.car = car;
    }

    private Car car;

    public Customer() {}

    public Customer(int id, String name, String surname, String adress, int mobile, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.mobile = mobile;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
