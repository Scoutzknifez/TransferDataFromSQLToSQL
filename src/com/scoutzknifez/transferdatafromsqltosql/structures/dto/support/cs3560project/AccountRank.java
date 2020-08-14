package com.scoutzknifez.transferdatafromsqltosql.structures.dto.support.cs3560project;

public enum AccountRank {
    BASIC,  // Can Buy
    VENDOR, // Can Sell and Edit own products
    ADMIN;  // Can Edit all

    public static AccountRank getRank(int value) {
        return (value < 0 ?
                BASIC : value > AccountRank.values().length ?
                ADMIN : AccountRank.values()[value]);
    }

    public int getOrdinal() {
        return ordinal();
    }
}
