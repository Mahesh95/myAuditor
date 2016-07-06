package com.a1995.mahesh.myauditor;

/**
 * Created by mahesh on 24/6/16.
 */
public class Wallet {
    private String name;
    private Float balance;

    public Wallet(String name) {
        this.name = name;
        this.balance = null;
    }

    public Wallet(String name, Float balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
