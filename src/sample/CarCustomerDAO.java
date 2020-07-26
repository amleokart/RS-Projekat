package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CarCustomerDAO {

    private static CarCustomerDAO instance;
    private Connection conn;

    private PreparedStatement customerFromCarStatment, getCustomerStatment, deleteCustomerStatment, deleteCarsForCustomerStament, findCustomerStatment,
            getCarsStatment, addCarStatment, carIdStatment, addCustomerStatment, customerIdStatment, editCarStatment, getCarStatment,
            findCarStatment, deleteCarStatment, getCustomersStatment,editCustomerStatment, loginStatment;

    public static CarCustomerDAO getInstance() {
        if (instance == null) instance = new CarCustomerDAO();
        return instance;
    }
    private CarCustomerDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            customerFromCarStatment = conn.prepareStatement("SELECT car.id, car.manufacturer, car.model, car.description, car.price, car.customer FROM car, customer WHERE car.customer=customer.id AND customer.name=?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                customerFromCarStatment = conn.prepareStatement("SELECT car.id, car.manufacturer, car.model, car.description, car.price, car.customer FROM car, customer WHERE car.customer=customer.id AND customer.name=?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            getCustomerStatment = conn.prepareStatement("SELECT * FROM customer WHERE id=?");
            getCarStatment = conn.prepareStatement("SELECT * FROM car WHERE id=?");
            deleteCarsForCustomerStament = conn.prepareStatement("DELETE FROM car WHERE customer=?");
            deleteCustomerStatment = conn.prepareStatement("DELETE FROM customer WHERE id=?");
            deleteCarStatment = conn.prepareStatement("DELETE FROM car WHERE id=?");
            findCustomerStatment = conn.prepareStatement("SELECT * FROM customer WHERE name=?");
            findCarStatment = conn.prepareStatement("SELECT * FROM car WHERE manufacturer=?");
            getCarsStatment = conn.prepareStatement("SELECT * FROM car ORDER BY price DESC");
            getCustomersStatment = conn.prepareStatement("SELECT * FROM customer ORDER BY name");
            addCarStatment = conn.prepareStatement("INSERT INTO car VALUES(?,?,?,?,?,?)");
            carIdStatment = conn.prepareStatement("SELECT MAX(id)+1 FROM car");
            addCustomerStatment = conn.prepareStatement("INSERT INTO customer VALUES(?,?,?,?)");
            customerIdStatment = conn.prepareStatement("SELECT MAX(id)+1 FROM customer");
            editCarStatment = conn.prepareStatement("UPDATE car SET manufacturer=?, model=?, description=?, price=?, customer=? WHERE id=?");
            editCustomerStatment = conn.prepareStatement("UPDATE customer SET name=?, email=?, car=? WHERE id=?");
            loginStatment = conn.prepareStatement("SELECT * FROM login WHERE username=? and password=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Car customerForCar(String customer) {
        try {
            Customer d = findCustomer(customer);
            customerFromCarStatment.setString(1, customer);
            ResultSet rs = customerFromCarStatment.executeQuery();
            if (!rs.next()) return null;
            return getCarFromResultSet(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Car getCarFromResultSet(ResultSet rs, Customer d) throws SQLException {
        return new Car(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getInt(5), d);
    }

    private Customer getCustomer(int id) {
        try {
            getCustomerStatment.setInt(1, id);
            ResultSet rs = getCustomerStatment.executeQuery();
            if (!rs.next()) return null;
            return getCustomerFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Car getCar(int id, Customer d) {
        try {
            getCarStatment.setInt(1, id);
            ResultSet rs = getCarStatment.executeQuery();
            if (!rs.next()) return null;
            return getCarFromResultSet(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Customer getCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer d = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), null);
        d.setCar( getCar(rs.getInt(4), d ));
        return d;
    }

    public void deleteCustomer(String customerName) {
        try {
            findCustomerStatment.setString(1, customerName);
            ResultSet rs = findCustomerStatment.executeQuery();
            if (!rs.next()) return;
            Customer customer = getCustomerFromResultSet(rs);

            deleteCarsForCustomerStament.setInt(1, customer.getId());
            deleteCarsForCustomerStament.executeUpdate();

            deleteCustomerStatment.setInt(1, customer.getId());
            deleteCustomerStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Car> cars() {
        ArrayList<Car> rezultat = new ArrayList();
        try {
            ResultSet rs = getCarsStatment.executeQuery();
            while (rs.next()) {
                Customer d = getCustomer(rs.getInt(4));
                Car car = getCarFromResultSet(rs, d);
                rezultat.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Customer> customers() {
        ArrayList<Customer> rezultat = new ArrayList();
        try {
            ResultSet rs = getCustomersStatment.executeQuery();
            while (rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);
                rezultat.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void addCar(Car car) {
        try {
            ResultSet rs = carIdStatment.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addCarStatment.setInt(1, id);
            addCarStatment.setString(2, car.getManufacturer());
            addCarStatment.setString(3, car.getModel());
            addCarStatment.setString(4, car.getDescription());
            addCarStatment.setInt(5, car.getPrice());
            addCarStatment.setInt(6, car.getCustomer().getId());
            addCarStatment.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer customer) {
        try {
            ResultSet rs = customerIdStatment.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addCustomerStatment.setInt(1, id);
            addCustomerStatment.setString(2, customer.getName());
            addCustomerStatment.setString(3, customer.getEmail());
            addCustomerStatment.setInt(4, customer.getCar().getId());
            addCustomerStatment.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCar(Car car) {
        try {
            editCarStatment.setString(1, car.getManufacturer());
            editCarStatment.setString(2, car.getModel());
            editCarStatment.setString(3, car.getDescription());
            editCarStatment.setInt(4, car.getPrice());
            editCarStatment.setInt(5, car.getCustomer().getId());
            editCarStatment.setInt(6, car.getId());
            editCarStatment.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCustomer(Customer customer) {
        try {
            editCustomerStatment.setString(1, customer.getName());
            editCustomerStatment.setString(2, customer.getEmail());
            editCustomerStatment.setInt(3, customer.getCar().getId());
            editCustomerStatment.setInt(4, customer.getId());
            editCustomerStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer findCustomer(String nazivDrzave) {
        try {
            findCustomerStatment.setString(1, nazivDrzave);
            ResultSet rs = findCustomerStatment.executeQuery();
            if (!rs.next()) return null;
            return getCustomerFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Car findCar(String carName) {
        try {
            findCarStatment.setString(1, carName);
            ResultSet rs = findCarStatment.executeQuery();
            if (!rs.next()) return null;
            Customer d = getCustomer(rs.getInt(6));
            return getCarFromResultSet(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteCar(Car car) {
        try {
            deleteCarStatment.setInt(1, car.getId());
            deleteCarStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validate(String username, String password) throws SQLException {
        try {
            loginStatment.setString(1,username);
            loginStatment.setString(2,password);
            ResultSet resultSet = loginStatment.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
        }
        return false;
    }
}
