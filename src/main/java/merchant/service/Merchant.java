package merchant.service;

public class Merchant {

    private String bankAccID;
    private String id;
    private int balance;

    public Merchant(String bankAccID, String id, int balance) {
        this.bankAccID = bankAccID;
        this.id = id;
        this.balance = balance;
    }

    public Merchant() {

    }

    public String getBankAccID() {
        return bankAccID;
    }

    public void setBankAccID(String bankAccID) {
        this.bankAccID = bankAccID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
