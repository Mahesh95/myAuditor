package com.a1995.mahesh.myauditor.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.a1995.mahesh.myauditor.Transaction;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mahesh on 24/6/16.
 * this class wraps a Transaction object round a cursor
 */
public class TransactionCursorWrapper extends CursorWrapper {
    public TransactionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Transaction getTransaction() {
        String uuidString = getString(getColumnIndex(Schema.TransactionTable.Cols.ID));
        float amount = getFloat(getColumnIndex(Schema.TransactionTable.Cols.AMOUNT));
        long date = getLong(getColumnIndex(Schema.TransactionTable.Cols.DATE));
        String month = getString(getColumnIndex(Schema.TransactionTable.Cols.MONTH));
        String category = getString(getColumnIndex(Schema.TransactionTable.Cols.CATEGORY));
        String subCategory = getString(getColumnIndex(Schema.TransactionTable.Cols.SUBCATEGORY));
        String wallet = getString(getColumnIndex(Schema.TransactionTable.Cols.WALLET));
        String note = getString(getColumnIndex(Schema.TransactionTable.Cols.NOTE));

        return new Transaction(UUID.fromString(uuidString), new Date(date), month, amount, category, subCategory, wallet, note);
    }
}
