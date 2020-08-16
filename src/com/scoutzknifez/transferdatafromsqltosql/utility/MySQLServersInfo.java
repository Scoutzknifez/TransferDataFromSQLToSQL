package com.scoutzknifez.transferdatafromsqltosql.utility;

import com.scoutzknifez.transferdatafromsqltosql.structures.SQLServerDetails;

public class MySQLServersInfo {
    // From SQL Server
    public static SQLServerDetails fromServer = new SQLServerDetails("ADMIN", "adminrootpassword", "jdbc:mysql://192.168.1.134:3210/" + Constants.DATABASE_NAME + "?useSSL=false&allowPublicKeyRetrieval=true");

    // To SQL Server
    public static SQLServerDetails toServer = new SQLServerDetails("ADMIN", "ADMINROOTPASSWORD", "jdbc:mysql://192.168.1.130:3210/" + Constants.DATABASE_NAME + "?useSSL=false&allowPublicKeyRetrieval=true");
}
