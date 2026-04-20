package model;

import java.sql.Timestamp;

public class Account {

    private long  accNo;
    private int customerId;
    private String accType;
    private double balance;
    private Timestamp createdAt;

    public Account(long  accNo, int customerId, String accType, double balance, Timestamp createdAt) {
        this.accNo = accNo;
        this.customerId = customerId;
        this.accType = accType;
        this.balance = balance;
        this.createdAt = createdAt; 
    }

   

    public Account(long long1, String string, double double1) {
        this.accNo = long1;
        this.accType = string;      
        this.balance = double1;
    }



    public long  getAccNo() {
        return accNo;
    }

    public void setAccNo(long  accNo) {
        this.accNo = accNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
}
