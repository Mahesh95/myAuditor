package com.a1995.mahesh.myauditor.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.a1995.mahesh.myauditor.Wallet;

/**
 * Created by mahesh on 24/6/16.
 * this class wraps a wallet object around the cursor
 */
public class WalletCursorWrapper extends CursorWrapper {
    public WalletCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //this method returns a wallet object from the cursor
    public Wallet getWallet() {
        String name = getString(getColumnIndex(Schema.WalletTable.Cols.NAME));
        float balance = getFloat(getColumnIndex(Schema.WalletTable.Cols.BALANCE));

        return new Wallet(name, balance);
    }
}
