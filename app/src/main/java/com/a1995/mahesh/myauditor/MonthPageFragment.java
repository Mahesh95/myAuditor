package com.a1995.mahesh.myauditor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mahesh on 24/6/16.
 * this fragment shows all the transactions in a given month
 */
public class MonthPageFragment extends Fragment {

    private static final String TAG = "MPF running";
    private static final String ARG_MONTH = "month";
    private List<Wallet> mWallets;
    private RecyclerView mTransactionsRecyclerView;
    private FloatingActionButton addTransactionButton;
    private String selectedWallet;
    private TransactionAdapter mAdapter;

    public static MonthPageFragment newInstance(String monthString) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MONTH, monthString);
        MonthPageFragment fragment = new MonthPageFragment();
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
        View view = inflater.inflate(R.layout.month_page_fragment, parent, false);
        mWallets = DatabaseLab.get(getActivity()).getWallets();
        // checking if there are no wallets, if so throwing AddWallet Activity
        if (mWallets == null || mWallets.size() == 0) {
            Intent intent = AddWalletActivity.newIntent(getActivity());
            startActivity(intent);
        }
        mTransactionsRecyclerView = (RecyclerView) view.findViewById(R.id.month_page_fragment_recycler_view);
        mTransactionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addTransactionButton = (FloatingActionButton) view.findViewById(R.id.month_page_fragment_add_transaction);
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddTransactionActivity.newIntent(getActivity(), selectedWallet);
                startActivity(intent);
            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        List<Transaction> transactions;
        String month = getArguments().getString(ARG_MONTH);
        if (month == null) {
            return;
        }
        transactions = DatabaseLab.get(getActivity()).getTransactionsFromMonth(month);
        if (mAdapter == null) {
            mAdapter = new TransactionAdapter(transactions);
            mTransactionsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTransactions(transactions);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_month_page_fragment, menu);

        MenuItem item = menu.findItem(R.id.menu_item_month_fragment_spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);


        List<String> wallets = new ArrayList<String>();
        for (Wallet wallet : mWallets) {
            wallets.add(wallet.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, wallets);

        dataAdapter.setDropDownViewResource(R.layout.wallet_drop_down);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                selectedWallet = ((TextView) adapterView.getChildAt(0)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private class TransactionHolder extends RecyclerView.ViewHolder {
        private TextView mTransactionDate;
        private TextView mTransactionAmount;
        private TextView mTransactionCategory;
        private TextView mTransactionNote;

        public TransactionHolder(View itemView) {
            super(itemView);
            mTransactionDate = (TextView) itemView.findViewById(R.id.transaction_card_date);
            mTransactionAmount = (TextView) itemView.findViewById(R.id.transaction_card_amount);
            mTransactionCategory = (TextView) itemView.findViewById(R.id.transaction_card_category);
            mTransactionNote = (TextView) itemView.findViewById(R.id.transaction_card_note);
        }

        public void bindTransaction(Transaction transaction) {
            mTransactionDate.setText(DatabaseLab.getDateInFormat("yyyy-MM-dd", transaction.getDate()));
            mTransactionCategory.setText(transaction.getSubCategory());
            mTransactionAmount.setText(transaction.getAmount().toString());
            mTransactionNote.setText(transaction.getNote());
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {
        private List<Transaction> mTransactions;

        public TransactionAdapter(List<Transaction> transactions) {
            mTransactions = transactions;
        }

        @Override
        public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.transaction_recycler_view_item, parent, false);
            return new TransactionHolder(view);
        }

        @Override
        public void onBindViewHolder(TransactionHolder holder, int position) {
            Transaction transaction = mTransactions.get(position);
            holder.bindTransaction(transaction);
        }

        @Override
        public int getItemCount() {
            return mTransactions.size();
        }

        private void setTransactions(List<Transaction> transactions) {
            mTransactions = transactions;
        }
    }
}

