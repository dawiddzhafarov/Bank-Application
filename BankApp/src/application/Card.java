package application;

public class Card extends DatabaseConnection {
    private String card_nr;
    private String pin;
    private double card_money;

    public Card(String _card_nr, String card_pin, double card_money) {
        this.card_nr = _card_nr;
        this.pin = card_pin;
        this.card_money = card_money;
    }

    // getters
    public String getCardNr(){ return this.card_nr; }
    public String getPin(){
        return this.pin;
    }
    public double getCardMoney(){
        return this.card_money;
    }

    // methods
    public void changePIN(String new_pin, String my_id){
        changePINDB(new_pin,my_id);
    }
    public void assignFundsCard(double newfundsCard, String my_id){
        assignCardFundsDB(newfundsCard,my_id);
        this.card_money = newfundsCard;
    }
}
