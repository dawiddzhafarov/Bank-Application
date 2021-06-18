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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class CardInterfaceController extends UserInterfaceController {
    private DatabaseConnection database = new DatabaseConnection();
    private Account account;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button backButtonCard;
    @FXML
    private Label cardInfo;
    @FXML
    private Label currentFunds;
    @FXML
    private Label currentPIN;
    @FXML
    private Button changePinButton;
    @FXML
    private TextField pinTxt;
    @FXML
    private Label pinLabel;
    @FXML
    private Button assignFundsButton;
    @FXML
    private TextField amountTxt;
    @FXML
    private Label cardFundsInfo;
    @FXML
    private Button xbutton;

    // method handling assigning funds to card
    public void assignFundsAction(ActionEvent event){
        if ((isNumeric(amountTxt.getText())) && Double.parseDouble(amountTxt.getText()) <= this.account.getMoney()) {
            this.account.assignFunds(Double.parseDouble(amountTxt.getText()), this.account.getUser_id());
            currentFunds.setText("Your currently assigned funds to this card are: " + this.account.getCardFunds() + "$");
        } else {
            cardFundsInfo.setText("Enter correct numeric value!");
        }
    }
    // method handling changing pin
    public void changePinAction(ActionEvent event){
        if ((pinTxt.getText().length() == 4) && (isNumeric(pinTxt.getText()))){
            this.account.changeCardPin(pinTxt.getText(),this.account.getUser_id());
            currentPIN.setText("Succesfully changed PIN number to: " + pinTxt.getText());
        } else {
            pinLabel.setText("Enter 4-digit PIN number");
        }
    }
    // method checking if input data is numeric
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    // assessing account information
    public void getUID3(String uid) {
        this.database.connect(uid);
        this.account = new Account(this.database.getUser_id(), this.database.getFname(), this.database.getLname(), this.database.getMoney(), this.database.getAccount_nr(), this.database.getCard_nr(), this.database.getType(), this.database.getEmail(), this.database.getCard_pin(), this.database.getCard_money(), this.database.getPassword());
        cardInfo.setText("Your card number is: " + this.account.getCard_nr());
        currentPIN.setText("Current PIN number is: " + this.account.getCardPin());
        currentFunds.setText("Your currently assigned funds to this card are: " + this.account.getCardFunds() + "$");
    }
    // going back to mainPanel
    public void goBackButtonCard(ActionEvent event) throws IOException {
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
    //quitting app by x button
    public void xbuttonAction(ActionEvent event) {
        Stage my_stage = (Stage) xbutton.getScene().getWindow();
        my_stage.close();
    }
}
