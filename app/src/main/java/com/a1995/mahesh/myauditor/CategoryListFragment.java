package com.a1995.mahesh.myauditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mahesh on 3/7/16.
 * CategoryListFragment consists of a recyclerView of transaction Subcategories
 * the main category will be determined by ARG_CATEGORY
 */
public class CategoryListFragment extends Fragment {
    public static final String EXTRA_CATEGORY = "extra_category";
    public static final String EXTRA_SUBCATEGORY = "extra_sub_category";
    private static final String ARG_CATEGORY = "category";
    List<String> mSubCategories;
    private RecyclerView mRecyclerView;
    private String mTransactionCategory;
    private SubCategoryAdapter mAdapter;

    public static CategoryListFragment newInstance(String category) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list_fragment, parent, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.category_list_fragment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        UpdateUI();
        return view;
    }

    /**
     * this method updates the recyclerView
     * binds the recyclerView with adapter
     */

    private void UpdateUI() {
        mTransactionCategory = (String) getArguments().getSerializable(ARG_CATEGORY);
        mSubCategories = DatabaseLab.get(getActivity()).getSubCategories(mTransactionCategory);
        mAdapter = new SubCategoryAdapter(mSubCategories);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class SubCategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mSubCategoryNameTextView;

        public SubCategoryHolder(View itemView) {
            super(itemView);
            mSubCategoryNameTextView = (TextView) itemView.findViewById(R.id.subcategory_card_text_view);
        }

        public void bindSubcategory(String subCategory) {
            mSubCategoryNameTextView.setText(subCategory);
            mSubCategoryNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //pass the category string to AddTransactionActivity
            Intent intent = new Intent();
            intent.putExtra(EXTRA_CATEGORY, mTransactionCategory);
            intent.putExtra(EXTRA_SUBCATEGORY, mSubCategoryNameTextView.getText().toString());
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }

    private class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryHolder> {
        private List<String> subCategories;

        public SubCategoryAdapter(List<String> subCategories) {
            this.subCategories = subCategories;
        }

        @Override
        public SubCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.subcategory_card_view, parent, false);
            return new SubCategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(SubCategoryHolder holder, int position) {
            String subCategory = subCategories.get(position);
            holder.bindSubcategory(subCategory);
        }

        @Override
        public int getItemCount() {
            return subCategories.size();
        }
    }

}
