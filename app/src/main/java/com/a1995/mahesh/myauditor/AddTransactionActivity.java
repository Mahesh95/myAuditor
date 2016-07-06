package com.a1995.mahesh.myauditor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by mahesh on 1/7/16.
 * This activity hosts AddTransaction Fragment
 */
public class AddTransactionActivity extends SingleFragmentActivity {
    private static final String EXTRA_SELECTED_WALLET = "selectedWallet";

    /**
     * this method is used to get intent for the activity
     */
    public static Intent newIntent(Context context, String selectedWallet) {
        Intent intent = new Intent(context, AddTransactionActivity.class);
        intent.putExtra(EXTRA_SELECTED_WALLET, selectedWallet);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return AddTransactionFragment.newInstance(getIntent().getStringExtra(EXTRA_SELECTED_WALLET));
    }
}
