package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CarCustomerDAO implements CarCustomerDAOBase {

    private static CarCustomerDAO instance;
    private Connection conn;
    private PreparedStatement loginStatment;


    public static CarCustomerDAO getInstance() {
        if (instance == null) instance = new CarCustomerDAO();
        return instance;
    }


    private CarCustomerDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            loginStatment = conn.prepareStatement("SELECT * FROM login WHERE username = ? and password = ?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                loginStatment = conn.prepareStatement("SELECT * FROM login WHERE username = ? and password = ?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            loginStatment = conn.prepareStatement("SELECT * FROM login WHERE username = ? and password = ?");
            /*getCarssStatment = conn.prepareStatement("SELECT * FROM car ORDER BY price DESC");
            getCarStatment = conn.prepareStatement("SELECT * FROM car");
            addCarStatment = conn.prepareStatement(" INSERT INTO car VALUES (?,?,?,?,?,?,?)");
            editCarStatment = conn.prepareStatement("UPDATE car SET manufacturer=?, model=?, description=?, fuel=?, price=? customer_id=? WHERE id=?");
            deleteCarStatment = conn.prepareStatement("DELETE FROM car WHERE id=?");

            getCustomerStatment = conn.prepareStatement("SELECT * FROM customer");
            addCustomerStatment = conn.prepareStatement(" INSERT INTO customer VALUES (?,?,?,?,?,?,?)");
            editCustomerStatment = conn.prepareStatement("UPDATE customer SET name=?, surname=?, adress=?, mobile=?, email=?, car=? WHERE id=?");
            deleteCustomerStatment = conn.prepareStatement("DELETE FROM customer WHERE id=?");

            getIdCarStatment = conn.prepareStatement("SELECT MAX(id)+1 FROM car");
            getIdCustomerStatment = conn.prepareStatement("SELECT MAX(id)+1 FROM customer");

            findCarStatment = conn.prepareStatement("SELECT * FROM car WHERE id=?");
            findCustomerStatment = conn.prepareStatement("SELECT * FROM customer WHERE id=?");

            getCarIDCustomerStatment = conn.prepareStatement("SELECT car.id, car.manufacturer, car.model, car.description, car.fuel, car.price, car.customer_id FROM car, customer WHERE car.customer_id=customer.id AND customer.id=?");*/
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
            ulaz = new Scanner(new FileInputStream("database.db.sql"));
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

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    @Override
    public boolean validate(String user, String password) throws SQLException {
        try {
            loginStatment.setString(1, user);
            loginStatment.setString(2, password);
            ResultSet resultSet = loginStatment.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

   /* public Car customer_id(String drzava) {
        try {
            Customer c = getCustomer(drzava);
            getCarIDCustomerStatment.setString(1, drzava);
            ResultSet rs = getCarIDCustomerStatment.executeQuery();
            if (!rs.next()) return null;
            return getCarFromResult(rs, c);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Car getCarFromResult(ResultSet rs, Customer c) throws SQLException {
        return new Car(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), c);
    }

    private Customer getCustomer(int id) {
        try {
            getCustomerStatment.setInt(1, id);
            ResultSet rs = getCustomerStatment.executeQuery();
            if (!rs.next()) return null;
            return getCustomerFromResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Car getCar(int id, Customer c) {
        try {
            getCarStatment.setInt(1, id);
            ResultSet rs = getCarStatment.executeQuery();
            if (!rs.next()) return null;
            return getCarFromResult(rs, c);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomerFromResult(ResultSet rs) throws SQLException {
        Customer c = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), null);
        c.setCar(getCar(rs.getInt(7), c));
        return c;
    }

    //greška neka
    public ArrayList<Car> cars() {
        ArrayList<Car> rezultat = new ArrayList();
        try {
            ResultSet rs = getCarStatment.executeQuery();
            while (rs.next()) {
                Customer d = getCustomer(rs.getInt(7));
                Car car = getCarFromResult(rs, d);
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
            ResultSet rs = getCustomerStatment.executeQuery();
            while (rs.next()) {
                Customer customer = getCustomerFromResult(rs);
                rezultat.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void addCar(Car car) {
        try {
            ResultSet rs = getIdCarStatment.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addCarStatment.setInt(1, id);
            addCarStatment.setString(2, car.getManufacturer());
            addCarStatment.setString(3, car.getModel());
            addCarStatment.setString(4,car.getDescription());
            addCarStatment.setString(5, car.getFuel());
            addCarStatment.setInt(6, car.getPrice());
            addCarStatment.setInt(7, car.getCustomer_id().getId());
            addCarStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer customer) {
        try {
            ResultSet rs = getIdCustomerStatment.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addCustomerStatment.setInt(1, id);
            addCustomerStatment.setString(2, customer.getName());
            addCustomerStatment.setString(3, customer.getSurname());
            addCustomerStatment.setString(4,customer.getAdress());
            addCustomerStatment.setInt(5, customer.getMobile());
            addCustomerStatment.setString(6, customer.getEmail());
            addCustomerStatment.setInt(7, customer.getCar().getId());
            addCustomerStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCar(Car car) {
        try {
            editCarStatment.setString(1, car.getManufacturer());
            editCarStatment.setString(2, car.getModel());
            editCarStatment.setString(3, car.getDescription());
            editCarStatment.setString(4, car.getFuel());
            editCarStatment.setInt(5, car.getPrice());
            editCarStatment.setInt(6, car.getCustomer_id().getId());
            editCarStatment.setInt(7, car.getId());
            editCarStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomer(String customer_id) {
        try {
            getCustomerStatment.setString(1, customer_id);
            ResultSet rs = findCustomerStatment.executeQuery();
            if (!rs.next()) return null;
            return getCustomerFromResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Car getCar(String car_id) {
        try {
            findCarStatment.setString(1, car_id);
            ResultSet rs = findCarStatment.executeQuery();
            if (!rs.next()) return null;
            Customer c = getCustomer(rs.getInt(7));
            return getCarFromResult(rs, c);
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

    public void deleteCustomer(String customer_id) {
        try {
            findCustomerStatment.setString(1, customer_id);
            ResultSet rs = findCustomerStatment.executeQuery();
            if (!rs.next()) return;
            Customer customer = getCustomerFromResult(rs);
            deleteCustomerStatment.setInt(1, customer.getId());
            deleteCustomerStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        try {
            editCustomerStatment.setString(1, customer.getName());
            editCustomerStatment.setString(2, customer.getSurname());
            editCustomerStatment.setString(3, customer.getAdress());
            editCustomerStatment.setInt(4, customer.getMobile());
            editCustomerStatment.setString(5, customer.getEmail());
            editCustomerStatment.setInt(6, customer.getCar().getId());
            editCustomerStatment.setInt(7, customer.getId());
            editCustomerStatment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCarId() {
        ResultSet rs;
        int id = 1;
        try {
            rs = getIdCarStatment.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getCustomerId() {
        ResultSet rs;
        int id = 1;
        try {
            rs = getIdCustomerStatment.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }*/


}

