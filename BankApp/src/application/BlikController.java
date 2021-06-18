package application;

import application.Blik;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class BlikController implements Initializable {
    private Timer timer;

    @FXML
    private Label blikNumber;
    @FXML
    private Button generateButton;
    @FXML
    private Label timeLeft;

    @Override
    // initializing number and timer
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.blikNumber.setText(Integer.toString(new Blik().getBlikNumber()));
        timer();
    }
    // generating new code and timer
    public void generateButtonAction(ActionEvent event) {
        timer.cancel();
        this.blikNumber.setText(Integer.toString(new Blik().getBlikNumber()));
        timer();
    }
    // helper method to use in timer() method, same as generateButtonAction
    public void generateNewBlik(){
        timer.cancel();
        this.blikNumber.setText(Integer.toString(new Blik().getBlikNumber()));
        timer();
    }
    // handling timer
    public void timer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 90;
            @Override
            public void run() {
                Platform.runLater(() -> {
                    timeLeft.setText(Integer.toString(i--));
                    if (i < 0) {
                        generateNewBlik();
                    }
                });
            }
        }, 1000, 1000);
    }
}
