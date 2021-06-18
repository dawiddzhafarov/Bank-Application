package application;

import java.sql.*;

public class DatabaseConnection extends Main {
    private String user_id;
    private String fname;
    private String lname;
    private String email;
    private String account_nr;
    private String type;
    private String card_nr;
    private double money;
    private String card_pin;
    private double card_money;
    private String url = "jdbc:sqlite:/C:\\Users\\Dawid\\SQLite\\sqlite-tools-win32-x86-3340000\\accounts.db"; //connection to local database
    private boolean failedTransfer;
    private String password;

    // setters
    public void setUser_id(String id){
        this.user_id = id;
    }
    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    // Getters
    public String getPassword() { return this.password; }
    public String getFname(){
        return this.fname;
    }
    public String getLname(){
        return this.lname;
    }
    public String getUser_id() { return this.user_id; }
    public String getEmail(){
        return this.email;
    }
    public String getAccount_nr(){
        return this.account_nr;
    }
    public String getType(){
        return this.type;
    }
    public String getCard_nr(){
        return this.card_nr;
    }
    public double getMoney(){
        return this.money;
    }
    public String getCard_pin(){
        return this.card_pin;
    }
    public double getCard_money(){
        return this.card_money;
    }
    public boolean getFailedTransfer(){
        return this.failedTransfer;
    }

    // Check method in order to authenticate user
    // Return true if correct, else false
    public boolean check(String inputUsername, String inputPassword) {
        try {
            Connection connection = DriverManager.getConnection(url);   // TU
            String sql = "SELECT user_id, firstname, lastname, password FROM accounts WHERE user_id = '" + inputUsername + "'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                String username = result.getString("user_id");
                String fname = result.getString("firstname");
                String lname = result.getString("lastname");
                String passwd = result.getString("password");
                if (inputPassword.equals(passwd)) {
                    this.user_id = username;
                    this.fname = fname;
                    this.lname = lname;
                    result.close();
                    statement.close();
                    connection.close();
                    return true;
                }
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Method taking all necessary data from users row in database to create account
    public void connect(String user_id) {
        try {
            Connection connection = DriverManager.getConnection(url);
            String sql = "SELECT * FROM accounts WHERE user_id = '" + user_id + "'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                String username = result.getString("user_id");
                String fname = result.getString("firstname");
                String lname = result.getString("lastname");
                String email = result.getString("email");
                String account_nr = result.getString("account_nr");
                String type = result.getString("type");
                String card_nr = result.getString("card_nr");
                String card_pin = result.getString("card_pin");
                double money = result.getDouble("money");
                double card_money = result.getDouble("card_funds");
                String password = result.getString("password");
                this.user_id = username;
                this.fname = fname;
                this.lname = lname;
                this.email = email;
                this.account_nr = account_nr;
                this.type = type;
                this.card_nr = card_nr;
                this.money = money;
                this.card_pin = card_pin;
                this.card_money = card_money;
                this.password = password;
            }
            connection.close();
            statement.close();
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Method executing transfers
    public void makeTransfer(String my_id, String recipient_fname, String recipient_lname, String recipient_account_nr, double _value){
        try {
            // Connecting to sender's row in database
            Connection connection = DriverManager.getConnection(url);
            String sql = "SELECT money FROM accounts WHERE user_id = '" + my_id + "'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            double moooney = 0;
            while (result.next()) {
                double my_money = result.getDouble("money");
                moooney = my_money;
            }

            // Updating sender's money
            moooney -= _value;
            String query = "UPDATE accounts SET money = " +moooney+ " WHERE user_id = '" + my_id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);  // connection - connection1
            preparedStatement.executeUpdate();

            // Connecting to recipient's row in database
            Connection connection2 = DriverManager.getConnection(url);
            String sql2 = "SELECT money FROM accounts WHERE firstname = '" + recipient_fname + "' AND lastname = '" + recipient_lname + "' AND account_nr = '" + recipient_account_nr + "'";
            Statement statement2 = connection2.createStatement();
            ResultSet result2 = statement2.executeQuery(sql2);
            double recipient_money = 0;
            while (result2.next()) {
                double act_money = result2.getDouble("money");
                System.out.println(act_money);
                recipient_money = act_money;
            }

            // Updating recipient's money
            recipient_money += _value;
            Connection connection3 = DriverManager.getConnection(url);
            String query2 ="UPDATE accounts SET money = " + recipient_money + " WHERE account_nr = '" + recipient_account_nr + "'";
            PreparedStatement preparedStatement2 = connection3.prepareStatement(query2);
            preparedStatement2.executeUpdate();
            System.out.println(recipient_money);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
        // checking recipient's data before making a transfer
        public boolean checkRecipinetsData(String recipient_fname, String recipient_lname, String recipient_account_nr, double _value) {
            int result = 5;
            try {
                Connection connection2 = DriverManager.getConnection(url);
                String sql2 = "SELECT EXISTS(SELECT user_id FROM accounts WHERE firstname = '" + recipient_fname + "' AND lastname = '" + recipient_lname + "' AND account_nr = '" + recipient_account_nr + "')";
                Statement statement2 = connection2.createStatement();
                ResultSet result2 = statement2.executeQuery(sql2);
                System.out.println(result2.getInt(1));
                result = result2.getInt(1);
                result2.close();
                statement2.close();
                connection2.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(result);
            if (result == 1) return true;
            return false;
        }
    // method handling changing pin in database
    public void changePINDB(String new_pin, String my_id){
        try {
            // Connecting to database
            Connection connection = DriverManager.getConnection(url);
            String sql = "UPDATE accounts SET card_pin = '" + new_pin + "' WHERE user_id = '" + my_id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    // method handling assigning funds to card in database
    public void assignCardFundsDB(double newfunds, String my_id){
        try {
            // Connecting to database
            Connection connection = DriverManager.getConnection(url);
            String sql = "UPDATE accounts SET card_funds = " + newfunds + " WHERE user_id = '" + my_id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // method changing email in database
    public void changeEmail(String newEmail, String my_id) {
        try {
            Connection connection = DriverManager.getConnection(url);
            String sql = "UPDATE accounts SET email = '" + newEmail + "' WHERE user_id = '" + my_id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close(); //
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // method changing password in database
    public void changePassword(String newpassword, String my_id) {
        try {
            Connection connection = DriverManager.getConnection(url);
            String sql = "UPDATE accounts SET password = '" + newpassword + "' WHERE user_id = '" + my_id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close(); //

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // method changing user_id in database
    public void changeUserid(String newuserid, String my_id){
        try {
            Connection connection = DriverManager.getConnection(url);
            String sql = "UPDATE accounts SET user_id = '" + newuserid + "' WHERE user_id = '" + my_id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close(); //

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // method creating new user in database
    public boolean insert(String _user_id,String _fname,String _lname,Double _money, String _account_nr, String _card_nr,String _type, String _email, String card_pin, double card_money, String pass){
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepStmt = connection.prepareStatement(
                    "insert into accounts values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, _user_id);
            prepStmt.setString(2, _fname);
            prepStmt.setString(3, _lname);
            prepStmt.setString(4, pass);
            prepStmt.setString(5, _email);
            prepStmt.setString(6, _account_nr);
            prepStmt.setString(7, _type);
            prepStmt.setString(8, _card_nr);
            prepStmt.setDouble(9, _money);
            prepStmt.setString(10, card_pin);
            prepStmt.setDouble(11, card_money);
            prepStmt.execute();
        }
        catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}