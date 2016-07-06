package com.a1995.mahesh.myauditor;

/**
 * Created by mahesh on 3/7/16.
 * Category object denotes a Transaction Category
 */
public class Category {
    private String mCategoryName;
    private String mSubCategories;

    public Category(String categoryName, String subCategories) {
        mCategoryName = categoryName;
        mSubCategories = subCategories;
    }

    //getters
    public String getCategoryName() {
        return mCategoryName;
    }

    public String getSubCategories() {
        return mSubCategories;
    }
}
