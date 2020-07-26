import org.junit.jupiter.api.Test;
import sample.Car;
import sample.CarCustomerDAO;
import sample.Customer;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CarCustomerDAOTest {

    @Test
    void regenerateFile() {
        CarCustomerDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        CarCustomerDAO dao = CarCustomerDAO.getInstance();
        ArrayList<Car> cars = dao.cars();
        assertEquals("Opel", cars.get(0).getManufacturer());
    }

    @Test
    void deleteCustomer() {
        CarCustomerDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        CarCustomerDAO dao = CarCustomerDAO.getInstance();
        dao.deleteCustomer("Skoda");
        ArrayList<Car> cars = dao.cars();
        assertEquals("Opel", cars.get(0).getManufacturer());
    }

    @Test
    void deleteCustomer2() {
        CarCustomerDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        CarCustomerDAO dao = CarCustomerDAO.getInstance();
        dao.deleteCustomer("Selma");
        ArrayList<Car> cars = dao.cars();
        assertEquals(2, cars.size());
        assertEquals("Opel", cars.get(0).getManufacturer());
        assertEquals("Skoda", cars.get(1).getManufacturer());
    }

}
