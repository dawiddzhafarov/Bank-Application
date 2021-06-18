package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SettingsController extends Main {
    private DatabaseConnection database = new DatabaseConnection();
    private Account account;
    private double xOffset = 0;
    private double yOffset = 0;
    private static FileHandler file_handler;
    private final static Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    private TextField setemailtxt;
    @FXML
    private Button xbutton;
    @FXML
    private Label emailtxt;
    @FXML
    private Label warningEmailtxt;
    @FXML
    private Label currentpass;
    @FXML
    private CheckBox checkbox;
    @FXML
    private TextField setpasswordtxt;
    @FXML
    private TextField setpasswordconfirmtxt;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Label passwdWarning;
    @FXML
    private Label nicknametxt;
    @FXML
    private Button changeuserIDbutton;
    @FXML
    private TextField setnickname;
    @FXML
    private Label nicknameWarning;

    public static void loginit() {
        try {
            file_handler = new FileHandler("C:/Users/Dawid/IdeaProjects/PROJEKT/logsSettings.log", true);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        Logger logger = Logger.getLogger("");
        file_handler.setFormatter(new SimpleFormatter());
        logger.addHandler(file_handler);
        logger.setLevel(Level.INFO);
    }
    // Handling changing password
    public void changePasswordButtonAction(ActionEvent event){
        if ((setpasswordtxt.getText().equals(setpasswordconfirmtxt.getText())) && (!setpasswordtxt.getText().isBlank())) {
            SettingsController.loginit();
            this.account.changepasswordacc(setpasswordtxt.getText(), this.account.getUser_id());
            passwdWarning.setText("Password changed.");
            logger.log(Level.INFO, "User " + this.account.getUser_id() + " has changed password ");
            infoLoad();
        } else {
            passwdWarning.setText("Passwords aren't the same!");
        }
    }
    // Handling checkbox
    public void checkboxAction(ActionEvent event){
        if (checkbox.isSelected()) {
            currentpass.setText(this.account.getPassword());
        }
        else {
            currentpass.setText(starPassword());
        }
    }
    // Handling changing UserID
    public void changeIDButton(ActionEvent event) {
        if((!setnickname.getText().isBlank()) && (setnickname.getText().length() >= 5)) {
            SettingsController.loginit();
            this.account.changeUseridacc(setnickname.getText(), this.account.getUser_id());
            nicknameWarning.setText("Succesfully changed User ID!");
            logger.log(Level.INFO, "User " + this.account.getUser_id() + " has changed user_id ");
            this.database.setUser_id(setnickname.getText());
            infoLoad();
        } else {
            nicknameWarning.setText("User ID is too short!");
        }
    }
    // Loading information
    public void infoLoad(){
        emailtxt.setText("Your current email is " + this.account.getEmail());
        currentpass.setText(starPassword());
        nicknametxt.setText("Your user id is: " + this.account.getUser_id());
    }
    // Assessing account information
    public void getUID4(String uid) {
        this.database.connect(uid);
        this.account = new Account(this.database.getUser_id(), this.database.getFname(), this.database.getLname(), this.database.getMoney(), this.database.getAccount_nr(), this.database.getCard_nr(), this.database.getType(), this.database.getEmail(), this.database.getCard_pin(), this.database.getCard_money(),this.database.getPassword());
        infoLoad();
    }
    // Hiding password
    public String starPassword(){
        String password = this.account.getPassword();
        int length = password.length();
        StringBuilder staredpass = new StringBuilder("");
        for(int i = 0; i < length; i++){
            staredpass.append("*");
        }
        return staredpass.toString();
    }
    // Quitttin app on x button
    public void xbuttonAction(ActionEvent event) {
        Stage my_stage = (Stage) xbutton.getScene().getWindow();
        my_stage.close();
    }
    // Handling changing email
    public void changeEmailAction(ActionEvent event) {
        if (setemailtxt.getText().contains("@")) {
            SettingsController.loginit();
            this.account.changeEmailacc(setemailtxt.getText(), this.account.getUser_id());
            logger.log(Level.INFO, "User " + this.account.getUser_id() + " has changed email ");
            emailtxt.setText("Your current email is " + this.account.getEmail());
        } else {
            warningEmailtxt.setText("Enter correct email address!");
        }
    }
    // Loading mainPanel
    public void backButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/userInterface.fxml"));
        Parent root = loader.load();

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
    }
}
