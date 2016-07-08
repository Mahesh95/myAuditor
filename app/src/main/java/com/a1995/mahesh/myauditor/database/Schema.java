package com.a1995.mahesh.myauditor.database;

/**
 * Created by mahesh on 23/6/16.
 * this class is only used to define the schema
 */
public class Schema {
    public static final class TransactionTable {
        public static final String NAME = "transactions";

        public static final class Cols {
            public static final String DATE = "date";
            public static final String MONTH = "month";
            public static final String ID = "transaction_id";
            public static final String CATEGORY = "category";
            public static final String SUBCATEGORY = "subcategory";
            public static final String AMOUNT = "amount";
            public static final String WALLET = "wallet";
            public static final String NOTE = "note";
        }
    }

    public static final class WalletTable {
        public static final String NAME = "wallets";

        public static final class Cols {
            public static final String NAME = "name";
            public static final String BALANCE = "balance";
        }
    }

    public static final class CategoriesTable {
        public static final String NAME = "categories";

        public static final class Cols {
            public static final String CATEGORY_NAME = "category_name";
            public static final String SUBCATEGORIES = "subcategories";
        }
    }
}
