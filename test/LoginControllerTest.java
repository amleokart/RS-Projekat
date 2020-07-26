import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import sample.CarCustomerDAO;
import sample.LoginController;

import java.io.File;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {

    LoginController reset;
    @Start
    public void start(Stage primaryStage) throws Exception{
        CarCustomerDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        CarCustomerDAO dao = CarCustomerDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginform.fxml"));
        loader.setController(new LoginController());
        Parent root =loader.load();
        primaryStage.setTitle("Login");
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    boolean hasCss(TextField polje, String stil) {
        for (String s : polje.getStyleClass())
            if (s.equals(stil)) return true;
        return false;
    }

    @Test
    public void testEmpty(FxRobot robot){

        robot.clickOn("#usernameFld");
        KeyCode ctrl = KeyCode.CONTROL;
        robot.clickOn("#passwordFld");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.press(KeyCode.DELETE).release(KeyCode.DELETE);
        robot.lookup("#btnLogin");
        robot.clickOn("#btnLogin");
        TextField username = robot.lookup("#usernameFld").queryAs(TextField.class);
        TextField password = robot.lookup("#passwordFld").queryAs(TextField.class);
        robot.clickOn("OK");
        assertTrue(hasCss(username, "invalid"));
        assertTrue(hasCss(password,"invalid"));
    }

    @Test
    public void testIncorrectInformation(FxRobot robot){
        robot.clickOn("#usernameFld");
        robot.write("user123");
        robot.clickOn("#passwordFld");
        robot.write("123user");
        robot.lookup("#btnLogin");
        robot.clickOn("#btnLogin");
        TextField username = robot.lookup("#usernameFld").queryAs(TextField.class);
        TextField password = robot.lookup("#passwordFld").queryAs(TextField.class);
        robot.clickOn("OK");
        assertTrue(hasCss(username,"invalid"));
        assertTrue(hasCss(password,"invalid"));
    }

    @Test
    public void testCorrectInformation(FxRobot robot){
        robot.clickOn("#usernameFld");
        KeyCode ctrl = KeyCode.CONTROL;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.press(KeyCode.DELETE).release(KeyCode.DELETE);
        robot.write("user");
        robot.clickOn("#passwordFld");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.press(KeyCode.DELETE).release(KeyCode.DELETE);
        robot.write("pass");
        robot.lookup("#btnLogin");
        robot.clickOn("#btnLogin");
        robot.window("Menu");
    }

    @Test
    public void testEmptyPassword(FxRobot robot){
        robot.clickOn("#usernameFld");
        robot.write("user123");
        robot.clickOn("#passwordFld");
        KeyCode ctrl=KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.press(KeyCode.DELETE).release(KeyCode.DELETE);
        TextField username = robot.lookup("#usernameFld").queryAs(TextField.class);
        TextField password = robot.lookup("#passwordFld").queryAs(TextField.class);
        robot.clickOn("#btnLogin");
        robot.clickOn("OK");
        assertTrue(!hasCss(username,"invalid"));
        assertTrue(hasCss(password,"invalid"));
    }
}