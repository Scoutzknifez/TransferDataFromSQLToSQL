package com.scoutzknifez.transferdatafromsqltosql.utility;

public class Constants {
    // Time
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    public static final int MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60;
    public static final int MILLIS_IN_DAY = MILLIS_IN_HOUR * 24;
    public static final int EPOCH_DAY = MILLIS_IN_DAY / 1000;

    // Driver loader
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
}
