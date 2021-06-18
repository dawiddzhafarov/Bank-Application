package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Button;

public class TranferInterfaceController extends UserInterfaceController {
    private DatabaseConnection database = new DatabaseConnection();
    private Account account;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button backButton;
    @FXML
    private Button sendButton;
    @FXML
    private TextField fnametxt;
    @FXML
    private TextField lnametxt;
    @FXML
    private TextField accountnrtxt;
    @FXML
    private TextField valuetxt;
    @FXML
    private Label messagetransfer;
    @FXML
    private Label moneyinfo;
    @FXML Button xbutton;

    // Handling sending transfer
    public void sendTransferButton(ActionEvent event){
        if ((fnametxt.getText().isBlank()) == false && (lnametxt.getText().isBlank()) == false && (accountnrtxt.getText().isBlank()) == false && (valuetxt.getText().isBlank() == false)
        && (this.database.checkRecipinetsData(fnametxt.getText(), lnametxt.getText(), accountnrtxt.getText(), Double.parseDouble(valuetxt.getText())) == true)) {
            this.account.makeTransferacc(this.account.getUser_id(), fnametxt.getText(), lnametxt.getText(), accountnrtxt.getText(), Double.parseDouble(valuetxt.getText()));
            this.database.connect(this.account.getUser_id());
            moneyinfo.setText("Your available funds: " + Double.toString(this.account.getMoney()) + "$");
            messagetransfer.setText("Transfer has been sent succesfully!");
            refresh();
        } else {
            messagetransfer.setText("Wrong data! Correct mistakes.");
        }
    }
    // updating information
    public void refresh(){
        this.account.setMoney(this.database.getMoney());
        moneyinfo.setText("Your available funds: " + Double.toString(this.account.getMoney()) + "$");
    }
    // assessing account information
    public void getUID2(String uid) {
        this.database.connect(uid);
        this.account = new Account(this.database.getUser_id(),this.database.getFname(), this.database.getLname(), this.database.getMoney(), this.database.getAccount_nr(), this.database.getCard_nr(),this.database.getType(),this.database.getEmail(), this.database.getCard_pin(),this.database.getCard_money(),this.database.getPassword());
        moneyinfo.setText("Your available funds: " + Double.toString(this.account.getMoney()) + "$");
    }
    // loading mainPanel
    public void goBackButton(ActionEvent event) throws IOException {
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
    // quitting app on x button
    public void xbuttonAction(ActionEvent event) {
        Stage my_stage = (Stage) xbutton.getScene().getWindow();
        my_stage.close();
    }
}
