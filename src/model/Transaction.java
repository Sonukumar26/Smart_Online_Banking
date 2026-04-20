package model;

import java.sql.Timestamp;

public class Transaction {
    

    private int txnId;
    private long accNo;
    private String txnType;
    private double amount;
    private Timestamp txnTime;

    public Transaction(int int1, long int2, String string, double double1, Timestamp timestamp) {
       this.txnId = int1;
       this.accNo = int2;   
       this.txnType = string;
       this.amount = double1;   
         this.txnTime = timestamp;
    }


    public Transaction() {
        new Transaction();
    }
   

    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public long getAccNo() {
        return accNo;
    }

    public void setAccNo(long accNo) {
        this.accNo = accNo;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(Timestamp txnTime) {
        this.txnTime = txnTime;
    }
}
