package application;

public class Account extends DatabaseConnection {
    private String user_id;
    private String fname;
    private String lname;
    private String email;
    private double money;
    private String account_nr;
    private String card_nr;
    private String type;
    private Card card;
    private String password;

    public Account(String _user_id,String _fname,String _lname,Double _money, String _account_nr, String _card_nr,String _type, String _email, String card_pin, double card_money, String pass) {
        this.user_id = _user_id;
        this.fname = _fname;
        this.lname = _lname;
        this.money = _money;
        this.account_nr = _account_nr;
        this.card_nr = _card_nr;
        this.type = _type;
        this.email = _email;
        this.card = new Card(_card_nr, card_pin, card_money);
        this.password = pass;
    }

    // getters
    public String getPassword() { return this.password; }
    public String getEmail(){ return this.email; }
    public String getType(){ return this.type; }
    public String getUser_id(){
        return this.user_id;
    }
    public String getCard_nr(){
        return this.card_nr;
    }
    public String getFname(){
        return this.fname;
    }
    public String getLname(){
        return this.lname;
    }
    public double getMoney(){
        return this.money;
    }
    public String getAccount_nr(){
        return this.account_nr;
    }
    public String getCardPin(){
        return this.card.getPin();
    }
    public double getCardFunds(){
        return this.card.getCardMoney();
    }

    // setters
    public void setEmail(String mail){
        this.email = mail;
    }
    public void setMoney(double newmoney){
        this.money = newmoney;
    }
    public void setUser_id(String newid) { this.user_id = newid; }

    // methods
    public void setPassword(String newpass){
        this.password = newpass;
    }
    public void assignFunds(double newfunds, String my_id){
        this.card.assignFundsCard(newfunds, my_id);
    }
    public void changeCardPin(String newPin, String my_id){
        this.card.changePIN(newPin, my_id);
    }
    public void makeTransferacc(String user_id, String fname, String lname, String account_nr, double _value){
        makeTransfer(user_id, fname, lname, account_nr, _value);
    }
    public void changeEmailacc(String newEmail,String my_id){
        changeEmail(newEmail, my_id);
        setEmail(newEmail);
    }
    public void changepasswordacc(String newpasswd, String my_id){
        setPassword(newpasswd);
        changePassword(newpasswd, my_id);
    }
    public void changeUseridacc(String newuserid, String my_id){
        changeUserid(newuserid, my_id);
        setUser_id(newuserid);
    }
}
