import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import sample.Car;
import sample.CarController;
import sample.CarCustomerDAO;

import java.io.File;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(ApplicationExtension.class)
class CarControllerTest {
    Stage theStage;
    CarController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        CarCustomerDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        CarCustomerDAO dao = CarCustomerDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/car.fxml"));
        ctrl = new CarController(null, dao.customers());
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Car database");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testValidationCar(FxRobot robot) {
        robot.clickOn("#btnOk");
        TextField name = robot.lookup("#fieldManufacturer").queryAs(TextField.class);
        Background bg = name.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);
        robot.clickOn("#fieldManufacturer");
        robot.write("Skoda");
        robot.clickOn("#fieldModel");
        robot.write("Skoda Fabia");
        robot.clickOn("#fieldDescription");
        robot.write("5 Seats, 2 Doors, No Air Conditioning, Manual gearbox");
        robot.clickOn("#fieldPrice");
        robot.write("-15");
        robot.clickOn("#btnOk");
        name = robot.lookup("#fieldManufacturer").queryAs(TextField.class);
        bg = name.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("adff2f"))
                colorFound = true;
        assertTrue(colorFound);
        TextField brojStanovnika = robot.lookup("#fieldPrice").queryAs(TextField.class);
        bg = brojStanovnika.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);
        robot.clickOn("#fieldPrice");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("40");
        robot.clickOn("#btnOk");
        assertFalse(theStage.isShowing());
    }

    @Test
    public void testReturnCar(FxRobot robot) {
        robot.clickOn("#fieldManufacturer");
        robot.write("Skoda");
        robot.clickOn("#fieldModel");
        robot.write("Skoda Fabia");
        robot.clickOn("#fieldDescription");
        robot.write("5 Seats, 2 Doors, No Air Conditioning, Manual gearbox");
        robot.clickOn("#fieldPrice");
        robot.write("40");
        robot.clickOn("#choiceCustomer");
        robot.clickOn("Elma Trako");
        robot.clickOn("#btnOk");
        Car car = ctrl.getCar();
        assertEquals("Skoda", car.getManufacturer());
        assertEquals("Skoda Fabia", car.getModel());
        assertEquals("5 Seats, 2 Doors, No Air Conditioning, Manual gearbox", car.getDescription());
        assertEquals(40, car.getPrice());
        assertEquals("Elma Trako", car.getCustomer().getName());

    }
}