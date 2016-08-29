package com.a1995.mahesh.myauditor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import com.a1995.mahesh.myauditor.database.DatabaseHelper;
import com.a1995.mahesh.myauditor.database.Schema;
import com.a1995.mahesh.myauditor.database.TransactionCursorWrapper;
import com.a1995.mahesh.myauditor.database.WalletCursorWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mahesh on 24/6/16.
 * This singleton class reads the local database and has all query related methods
 */
public class DatabaseLab {

    private static final String TAG = "databaselab";
    private static DatabaseLab sDatabaseLab;
    private Context mContext;
    private List<Transaction> mTransactions; //stores all transactions
    private List<Wallet> mWallets;           //stores all wallets
    private SQLiteDatabase mDatabase;

    /**
     * As soon as the instance of DatabaseLab is created,
     * all transactions all loaded in transaction list and wallets in wallet list
     */
    private DatabaseLab(Context context) {
        mContext = context;
        mDatabase = new DatabaseHelper(mContext).getWritableDatabase();

        loadTransactions();
        loadWallets();
    }

    //this method will be used to get the instance of DatabaseLab
    public static DatabaseLab get(Context context) {
        if (sDatabaseLab == null) {
            return new DatabaseLab(context);
        } else {
            return sDatabaseLab;
        }
    }

    /**
     * this method returns a date string in required format
     */

    public static String getDateInFormat(String dateFormat, Date date) {
        return DateFormat.format(dateFormat, date).toString();
    }

    /**
     * this method loads all wallets in mWallets
     */
    private void loadWallets() {
        mWallets = new ArrayList<>();
        WalletCursorWrapper cursor = queryWalletTable(null, null);    //selects all rows
        try {
            if (cursor.getCount() == 0) {
                return;
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    mWallets.add(cursor.getWallet());
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * this method queries the wallet table
     */
    private WalletCursorWrapper queryWalletTable(String whereClause, String[] whereArg) {
        Cursor cursor = mDatabase.query(Schema.WalletTable.NAME,
                null,
                whereClause,
                whereArg,
                null,
                null,
                null);
        return new WalletCursorWrapper(cursor);
    }

    /**
     * this method loads all transactions in mTransactions
     */
    private void loadTransactions() {
        mTransactions = new ArrayList<>();
        TransactionCursorWrapper cursor = queryTransactionTable(null, null); //selects all rows

        try {
            if (cursor.getCount() == 0) {
                return;
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    mTransactions.add(cursor.getTransaction());
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * this method queries the transaction table
     * it returns a cursor based on the WhereArg provided
     */

    private TransactionCursorWrapper queryTransactionTable(String whereClause, String[] whereArg) {
        Cursor cursor = mDatabase.query(Schema.TransactionTable.NAME,
                null,
                whereClause,
                whereArg,
                null,
                null,
                null);
        return new TransactionCursorWrapper(cursor);
    }

    /**
     * @param transaction:the transaction object to be written to the database
     */
    public void addTransaction(Transaction transaction) {
        ContentValues contentValues = getTransactionContentValues(transaction);
        mDatabase.insert(Schema.TransactionTable.NAME, null, contentValues);
    }

    /**
     * transaction object is wrapped around the contentValues object
     */
    private ContentValues getTransactionContentValues(Transaction transaction) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.TransactionTable.Cols.ID, transaction.getId().toString());
        contentValues.put(Schema.TransactionTable.Cols.DATE, transaction.getDate().getTime());
        contentValues.put(Schema.TransactionTable.Cols.MONTH, transaction.getMonth());
        contentValues.put(Schema.TransactionTable.Cols.AMOUNT, transaction.getAmount());
        contentValues.put(Schema.TransactionTable.Cols.CATEGORY, transaction.getCategory());
        contentValues.put(Schema.TransactionTable.Cols.SUBCATEGORY, transaction.getSubCategory());
        contentValues.put(Schema.TransactionTable.Cols.WALLET, transaction.getWallet());
        contentValues.put(Schema.TransactionTable.Cols.NOTE, transaction.getNote());
        return contentValues;
    }

    /**
     * this method writes the wallet object to the database
     */
    public void addWallet(Wallet wallet) {
        ContentValues values = getWalletContentValues(wallet);
        mDatabase.insert(Schema.WalletTable.NAME, null, values);
    }

    /**
     * this method returns content values of a wallet object
     */

    private ContentValues getWalletContentValues(Wallet wallet) {
        ContentValues values = new ContentValues();
        values.put(Schema.WalletTable.Cols.NAME, wallet.getName());
        values.put(Schema.WalletTable.Cols.BALANCE, wallet.getBalance());

        return values;
    }

    /**
     * this method returns a list of wallet objects
     */
    public List<Wallet> getWallets() {
        return mWallets;
    }

    /**
     * this method returns a list of all transactions
     */

    public void updateWallet(Wallet wallet) {
        ContentValues values = getWalletContentValues(wallet);
        mDatabase.update(Schema.WalletTable.NAME, values, Schema.WalletTable.Cols.NAME + " =?", new String[]{wallet.getName()});
    }

    public List<Transaction> getTransactions() {
        return mTransactions;
    }

    /**
     * this method returns a List of subcategories of a transaction category
     */

    public List<String> getSubCategories(String category) {
        Cursor cursor = mDatabase.query(Schema.CategoriesTable.NAME,
                null,
                Schema.CategoriesTable.Cols.CATEGORY_NAME + "=?",
                new String[]{category},
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            String subCategoryString = cursor.getString(cursor.getColumnIndex(Schema.CategoriesTable.Cols.SUBCATEGORIES));
            List<String> subCategories = new ArrayList<>();
            String[] subCategoriesArray = subCategoryString.split(",");

            for (String subCategory : subCategoriesArray) {
                subCategories.add(subCategory);
            }

            return subCategories;
        } else
            return null;
    }

    public List<String> getMonthsList() {
        Cursor cursor = mDatabase.query(true, Schema.TransactionTable.NAME,
                new String[]{Schema.TransactionTable.Cols.MONTH},
                null,
                null,
                null,
                null,
                Schema.TransactionTable.Cols.MONTH,
                null);
        List<String> monthsList = new ArrayList<>();

        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    monthsList.add(cursor.getString(cursor.getColumnIndex(Schema.TransactionTable.Cols.MONTH)));
                    cursor.moveToNext();
                }
                return monthsList;
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    /**
     * this function returns a list of transactions in a given month
     */

    public List<Transaction> getTransactionsFromMonth(String month) {
        List<Transaction> monthlyTransactions = new ArrayList<>();
        TransactionCursorWrapper cursor = queryTransactionTable(Schema.TransactionTable.Cols.MONTH + " =?", new String[]{month});
        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    monthlyTransactions.add(cursor.getTransaction());
                    cursor.moveToNext();
                }
                return monthlyTransactions;
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    /**
     * returns a wallet object from the database
     */
    public Wallet getWallet(String walletName) {
        WalletCursorWrapper cursor = queryWalletTable(Schema.WalletTable.Cols.NAME + " =?", new String[]{walletName});
        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                Log.i(TAG, "fucccck");
                return cursor.getWallet();
            } else {
                return null;
            }
        } finally {
            cursor.close();
        }
    }
}
