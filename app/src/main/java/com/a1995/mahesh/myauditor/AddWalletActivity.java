package com.a1995.mahesh.myauditor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by mahesh on 26/6/16.
 * this activity hosts AddWalletFragment
 */
public class AddWalletActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddWalletActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return AddWalletFragment.newInstance();
    }
}
