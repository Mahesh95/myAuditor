package com.a1995.mahesh.myauditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by mahesh on 26/6/16.
 */

public class AddWalletFragment extends Fragment {
    private EditText walletName;
    private EditText walletBalance;

    public static AddWalletFragment newInstance() {
        return new AddWalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_wallet_fragment, parent, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.add_wallet_fragment_toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        walletName = (EditText) view.findViewById(R.id.add_wallet_fragment_wallet_name_edit_text);
        walletBalance = (EditText) view.findViewById(R.id.add_wallet_fragment_money_edit_text);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_wallet, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_wallet_save:
                saveWallet();
                Intent intent = MainActivity.newIntent(getActivity());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * this method saves wallet object to database on click of Done button
     */
    private void saveWallet() {
        Wallet wallet = new Wallet(walletName.getText().toString());
        wallet.setBalance(Float.valueOf(walletBalance.getText().toString()));
        DatabaseLab.get(getActivity()).addWallet(wallet);
    }
}
