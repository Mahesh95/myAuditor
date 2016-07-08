package com.a1995.mahesh.myauditor;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mahesh on 24/6/16.
 * A Transaction is an entity of the database
 */
public class Transaction {
    private UUID mId;
    private Date mDate;
    private String mMonth;
    private Float mAmount;
    private String mCategory;
    private String mSubCategory;
    private String mWallet;
    private String mNote;

    // this constructor is used when creating a new transaction object
    public Transaction(Date date, String month, Float amount, String category, String subCategory, String wallet, String note) {
        this(UUID.randomUUID(), date, month, amount, category, subCategory, wallet, note);
    }

    //this constructor is used when creating a transaction object from a transactionTable row
    public Transaction(UUID id, Date date, String month, Float amount, String category, String subCategory, String wallet, String note) {
        mDate = date;
        mMonth = month;
        mId = id;
        mAmount = amount;
        mCategory = category;
        mSubCategory = subCategory;
        mWallet = wallet;
        mNote = note;
    }

    //getters
    public UUID getId() {
        return mId;
    }

    public Float getAmount() {
        return mAmount;
    }

    public Date getDate() {
        return mDate;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getWallet() {
        return mWallet;
    }

    public String getNote() {
        return mNote;
    }

    public String getSubCategory() {
        return mSubCategory;
    }

    public String getMonth() {
        return mMonth;
    }
}
