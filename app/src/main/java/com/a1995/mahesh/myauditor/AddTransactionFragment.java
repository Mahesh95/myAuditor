package com.a1995.mahesh.myauditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by mahesh on 1/7/16.
 */
public class AddTransactionFragment extends Fragment {
    private static final String TAG = "AddTransactionFragment";
    private static final int REQUEST_DATE = 0;  //this code identifies as DatePickerFragment as reporting fragment
    private static final String DIALOG_DATE = "dialog_date";
    private static final int REQUEST_CATEGORY = 1;
    private static final String ARG_WALLET = "wallet";

    private EditText mAmountEditText;
    private TextView mCategoryTextView;
    private EditText mNoteEditText;
    private TextView mDateTextView;
    private Date mDAte;
    private String mCategory;
    private String mSubCategory;

    public static AddTransactionFragment newInstance(String wallet) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WALLET, wallet);
        AddTransactionFragment fragment = new AddTransactionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_transaction_fragment, parent, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.add_transaction_fragment_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        mAmountEditText = (EditText) view.findViewById(R.id.add_transaction_fragment_amount);
        mCategoryTextView = (TextView) view.findViewById(R.id.add_transaction_fragment_category);

        mCategoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "i called for Category list activity");
                Intent intent = CategorySelectActivity.newIntent(getActivity());
                startActivityForResult(intent, REQUEST_CATEGORY);
            }
        });

        mNoteEditText = (EditText) view.findViewById(R.id.add_transaction_fragment_note);
        mDateTextView = (TextView) view.findViewById(R.id.add_transaction_fragment_date);
        mDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date());
                fragment.setTargetFragment(AddTransactionFragment.this, REQUEST_DATE);
                fragment.show(manager, DIALOG_DATE);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_transaction_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // when save button is clicked, write transaction object to database
            case R.id.menu_item_add_transaction:
                saveTransaction();
                goToMainActivity();
                return true;

            // when cross button(discard) is clicked, go to main activity
            case R.id.menu_item_add_transaction_discard:
                goToMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else if (requestCode == REQUEST_DATE) {
            String dateFormant = "yyyy-MM-dd";
            mDAte = (Date) intent.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            String dateString = DatabaseLab.getDateInFormat(dateFormant, mDAte);
            mDateTextView.setText(dateString);
        } else if (requestCode == REQUEST_CATEGORY) {
            mCategory = (String) intent.getSerializableExtra(CategoryListFragment.EXTRA_CATEGORY);
            mSubCategory = (String) intent.getSerializableExtra(CategoryListFragment.EXTRA_SUBCATEGORY);
            mCategoryTextView.setText(mSubCategory);
        }
    }

    private void goToMainActivity() {
        Intent intent = MainActivity.newIntent(getActivity());
        startActivity(intent);
    }

    /**
     * this method extracts data from fields and creates a transaction object
     * Then the transaction object is written to the database
     * The current balance of the wallet is also changed
     */
    private void saveTransaction() {
        Float amount = Float.valueOf(mAmountEditText.getText().toString());
        String noteString = mNoteEditText.getText().toString();
        String walletName = getArguments().getSerializable(ARG_WALLET).toString();

        if (mDAte == null || amount == 0 || amount == null) {
            return;
        }
        String month = DatabaseLab.getDateInFormat("yyyy-MM", mDAte);
        Transaction transaction = new Transaction(mDAte, month, amount, mCategory, mSubCategory, walletName, noteString);
        DatabaseLab.get(getActivity()).addTransaction(transaction);

        //changing the current balance
        Wallet wallet = DatabaseLab.get(getActivity()).getWallet(walletName);
        float balance = wallet.getBalance();
        float transactionAmount;

        if (transaction.getCategory().equals("Expense") || transaction.getSubCategory().equals("Debt")) {
            transactionAmount = -1 * transaction.getAmount();
        } else {
            transactionAmount = transaction.getAmount();
        }

        balance = balance + transactionAmount;
        wallet.setBalance(balance);
        DatabaseLab.get(getActivity()).updateWallet(wallet);
    }
}
