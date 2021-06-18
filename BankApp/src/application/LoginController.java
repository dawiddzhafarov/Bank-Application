package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController extends Main {
    private DatabaseConnection database = new DatabaseConnection();
    private double xOffset = 0;
    private double yOffset = 0;
    private static FileHandler file_handler;
    private final static Logger logger = Logger.getLogger(LoginController.class.getName());
    @FXML
    private Button loginButton;
    @FXML
    private Label loginmessage;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Button xbutton;
    @FXML BorderPane loginPane;

    // Method that sets up logger and file handler
    // I added to program arguments path to new (not default) log.properties file, that keeps old logs and appends new ones
    // previously it was java default file - logging.properties
    // new file is called log.properties
    public static void loginit() {
        try {
            file_handler = new FileHandler("C:/Users/Dawid/IdeaProjects/PROJEKT/logs.log", true);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        Logger logger = Logger.getLogger("");
        file_handler.setFormatter(new SimpleFormatter());
        logger.addHandler(file_handler);
        logger.setLevel(Level.INFO);
    }
    // Closing window using X button
    public void buttonXPressed(ActionEvent event){
        Stage my_stage = (Stage) xbutton.getScene().getWindow();
        my_stage.close();
    }
    // Actions on login button
    public void loginButtonAction(ActionEvent event) throws IOException {
        if ((usernameText.getText().isBlank() == false) && (passwordField.getText().isBlank() == false)) {
            loginmessage.setText("Trying to login...");
            validate();
        } else {
            loginmessage.setText("Enter username and password");
        }
    }
    // Validation of input data
    public void validate() throws IOException {
        LoginController.loginit();     // starting logger method
        if (this.database.check(usernameText.getText(),passwordField.getText()) == true){
            loginmessage.setText("Login succesful!");

            logger.log(Level.INFO, "User " + usernameText.getText() + " has logged in"); // log message

            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
            // Opening new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/userInterface.fxml"));
            Parent root = loader.load();
            // Getting user_id from 1st controller to 2nd one
            UserInterfaceController usercontroller = loader.getController();
            usercontroller.getUID(this.database.getUser_id());
            // Creating new stage and scene
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.setTitle("Bank with Ease");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            // methods that handle dragging window
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
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
        } else {
            loginmessage.setText("Wrong username or password!");
            logger.log(Level.INFO, "User " + usernameText.getText() + " failed to login"); // log message
        }

    }
    // loading sign up formula
    public void signUpButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/registerInterface.fxml"));
        Parent root = loader.load();
        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        loginStage.close();
        // Getting user_id from 1st controller to 2nd one
        // Creating new stage and scene
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Sign Up");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

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
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

}
