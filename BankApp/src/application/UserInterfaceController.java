package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class UserInterfaceController extends Main {
    private DatabaseConnection database = new DatabaseConnection();
    private Account account;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Label hellomessage;
    @FXML
    private Label flname;
    @FXML
    private Label moneyinfo;
    @FXML
    private Button xbutton;
    @FXML
    private Button transferbutton;
    @FXML
    private Label accountInfo;
    @FXML
    private Pane lowerPane;
    @FXML
    private Button blikButton;
    @FXML
    private Label type;
    @FXML
    private Button settingsButton;

    // Showing greeting + info
    public void helloMess(){
        hellomessage.setText("Hello " + this.account.getUser_id() + "!");
        flname.setText(this.account.getFname() + " " + this.account.getLname());
        moneyinfo.setText("Account balance: " + Double.toString(this.account.getMoney()) + "$");
        type.setText("Type of account: " + this.account.getType());
        accountInfo.setText("Your account number is: " + this.account.getAccount_nr());
    }
    // closing window on X button
    public void buttonxAction(ActionEvent event) {
        Stage my_stage = (Stage) xbutton.getScene().getWindow();
        my_stage.close();
    }
    // Method that gets user_id from login.fxml
    public void getUID(String uid) {
        this.database.connect(uid);
        this.account = new Account(this.database.getUser_id(),this.database.getFname(), this.database.getLname(), this.database.getMoney(), this.database.getAccount_nr(), this.database.getCard_nr(),this.database.getType(),this.database.getEmail(), this.database.getCard_pin(), this.database.getCard_money(),this.database.getPassword());
        helloMess();
    }
    // handling transfer button
    public void transferButton(ActionEvent event) throws IOException {
        // creating new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/transferInterface.fxml"));
        Parent root = loader.load();
        Scene transferScene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(transferScene);
        // accessing account information
        TranferInterfaceController usercontroller = loader.getController();
        usercontroller.getUID2(this.database.getUser_id());

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
    // handling card button
    public void CardButton(ActionEvent event) throws IOException {
        // creating new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/cardInterface.fxml"));
        Parent root = loader.load();
        Scene cardScene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cardScene);
        // accessing account information
        CardInterfaceController usercontroller = loader.getController();
        usercontroller.getUID3(this.database.getUser_id());

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
    //handling blik button
    public void blikButtonAction(ActionEvent event) throws IOException {
        // creating new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/blik.fxml"));
        Parent root = loader.load();
        Scene blikScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(blikScene);
        window.setTitle("BLIK");
        window.setResizable(false);
        window.show();
    }
    public void settingsButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/settings.fxml"));
        Parent root = loader.load();
        Scene settingsScene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(settingsScene);
        SettingsController settingsController = loader.getController();
        settingsController.getUID4(this.database.getUser_id());

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
}
