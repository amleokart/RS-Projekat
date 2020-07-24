package sample;

//fx:controller="sample.LoginController"

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {
    public PasswordField passwordFld;
    public TextField usernameFld;
    public Button btnLogin;
    public Button btnBack;
    CoverController cover;
    MenuController menu;
    private CarCustomerDAO dao;

    public LoginController() {
        dao = CarCustomerDAO.getInstance();
    }

    public void previousWindow(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cover.fxml"));
            CoverController coverController = new CoverController();
            loader.setController(coverController);
            root = loader.load();
            stage.setTitle("Welcome");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(ActionEvent actionEvent) throws SQLException {
        if (usernameFld.getText().isEmpty() || passwordFld.getText().isEmpty()) {

            if(usernameFld.getText().trim().isEmpty()) {
                usernameFld.getStyleClass().add("invalid");
            } else {
                usernameFld.getStyleClass().removeAll("invalid");
            }
            if(passwordFld.getText().trim().isEmpty()) {
                passwordFld.getStyleClass().add("invalid");
            } else {
                passwordFld.getStyleClass().removeAll("invalid");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while logging in.");
            alert.setContentText("You did not insert username or password, please try again.");
            alert.show();
            return;
        }

        String user = usernameFld.getText();
        String password = passwordFld.getText();
        boolean flag=dao.validate(user,password);

        if (!flag) {
            usernameFld.getStyleClass().add("invalid");
            passwordFld.getStyleClass().add("invalid");
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while logging in.");
            alert.setContentText("Invalid username or password. Please try again.");
            alert.show();

        } else {

            Stage stage2 = new Stage();
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
                MenuController menuController = new MenuController();
                loader.setController(menuController);
                root = loader.load();
                stage2.setTitle("Menu");
                stage2.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage2.setResizable(false);
                stage2.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}


    /*public void login(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            MenuController menuController = new MenuController();
            loader.setController(menuController);
            root = loader.load();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/




