package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.Random;

public class RegisterrController {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Random rand = new Random();
    private DatabaseConnection database = new DatabaseConnection();
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField money;
    @FXML
    private TextField moneyCard;
    @FXML
    private Button backButton;
    @FXML
    private Label registermessage;

    // Loading login interface
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        Parent root = loader.load();

        Scene previousScene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(previousScene);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setX(event.getScreenX() - xOffset);
                window.setY(event.getScreenY() - yOffset);
            }
        });

    }
    // submitting new account information and creating new one
    public void submitAction(ActionEvent event) throws IOException {
        if ((!username.getText().isBlank()) && (!name.getText().isBlank())&& (!surname.getText().isBlank())&& (!money.getText().isBlank())&& (!moneyCard.getText().isBlank())&& (!email.getText().isBlank())&& (!password.getText().isBlank())
         && (email.getText().contains("@")) && (username.getText().length() >= 5)) {
            String _user_id = username.getText();
            String _fname = name.getText();
            String _lname = surname.getText();
            Double _money = Double.parseDouble(money.getText());
            String _account_nr = "";
            _account_nr += Integer.toString(new Random().nextInt(89) + 10) + " ";
            _account_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            _account_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            _account_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            _account_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            _account_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            _account_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";

            String _card_nr = "";
            _card_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            _card_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            _card_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            _card_nr += Integer.toString(new Random().nextInt(8999) + 1000) + " ";
            String _type = "private";
            String _email = email.getText();
            String card_pin = String.valueOf(rand.nextInt(9000) + 1000);
            double card_money = Double.parseDouble(moneyCard.getText());
            String pass = password.getText();
            databaseConnection.insert(_user_id, _fname, _lname, _money, _account_nr, _card_nr, _type, _email, card_pin, card_money, pass);
            registermessage.setText("You are registered");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/userInterface.fxml"));
            Parent root = loader.load();

            this.database.setUser_id(username.getText());
            UserInterfaceController usercontroller = loader.getController();
            usercontroller.getUID(this.database.getUser_id());

            Scene previousScene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(previousScene);

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.setX(event.getScreenX() - xOffset);
                    window.setY(event.getScreenY() - yOffset);
                }
            });
        } else {
            registermessage.setText("Enter all information!!!");
        }
    }
}
