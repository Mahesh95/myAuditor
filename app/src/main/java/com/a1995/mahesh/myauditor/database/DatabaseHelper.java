package com.a1995.mahesh.myauditor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.a1995.mahesh.myauditor.Category;

/**
 * Created by mahesh on 23/6/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String NAME = "myAuditor.db";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creating TransactionTable

        sqLiteDatabase.execSQL("create table " +
                Schema.TransactionTable.NAME +
                " (_id integer primary key autoincrement," +
                Schema.TransactionTable.Cols.ID + "," +
                Schema.TransactionTable.Cols.DATE + "," +
                Schema.TransactionTable.Cols.MONTH + "," +
                Schema.TransactionTable.Cols.AMOUNT + "," +
                Schema.TransactionTable.Cols.CATEGORY + "," +
                Schema.TransactionTable.Cols.SUBCATEGORY + "," +
                Schema.TransactionTable.Cols.NOTE + "," +
                Schema.TransactionTable.Cols.WALLET + ")"
        );

        //creating WalletTable
        sqLiteDatabase.execSQL("create table " + Schema.WalletTable.NAME +
                "(" + Schema.WalletTable.Cols.NAME + " primary key," +
                Schema.WalletTable.Cols.BALANCE + ")"
        );

        //creating Categories table
        sqLiteDatabase.execSQL("create table " + Schema.CategoriesTable.NAME +
                "(" + Schema.CategoriesTable.Cols.CATEGORY_NAME + " primary key, " +
                Schema.CategoriesTable.Cols.SUBCATEGORIES + ")");

        //inserting persistent categories and subcategories in database
        Category[] persistentCategories = new Category[]{new Category("Debt&Loan", "Debt,Loan"),
                new Category("Expense", "Food&Beverages,Bills&Utilities,Transportation,Shopping,Health&Fitness,Education"),
                new Category("Income", "Award,Salary,PocketMoney,gifts,selling")};

        for (Category category : persistentCategories) {
            ContentValues values = getCategoryValues(category);
            sqLiteDatabase.insert(Schema.CategoriesTable.NAME, null, values);
        }
    }

    /**
     * this method binds category object to contentValues
     */
    private ContentValues getCategoryValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(Schema.CategoriesTable.Cols.CATEGORY_NAME, category.getCategoryName());
        values.put(Schema.CategoriesTable.Cols.SUBCATEGORIES, category.getSubCategories());

        return values;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
