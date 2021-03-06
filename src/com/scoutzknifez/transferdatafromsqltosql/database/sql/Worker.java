package com.scoutzknifez.transferdatafromsqltosql.database.sql;

import com.scoutzknifez.transferdatafromsqltosql.structures.SQLServerDetails;
import com.scoutzknifez.transferdatafromsqltosql.utility.Constants;
import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;
import com.scoutzknifez.transferdatafromsqltosql.utility.enums.Result;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Getter
public abstract class Worker implements Runnable {
    protected Connection connection;
    protected Statement statement;
    @Setter private SQLServerDetails serverDetails;
    @Setter private Table table;

    public Worker(SQLServerDetails serverDetails, Table table) {
        setServerDetails(serverDetails);
        setTable(table);
        ready();
    }

    /**
     * Gets the MySQL connector ready to work.
     */
    private void ready() {
        if (connectToDriver() == Result.FAILURE) {
            Utils.log("Could not connect driver for MySQL.");
            return; // TODO should instead throw exceptions rather than returning
        }

        connection = connectToMySQLDatabase(getServerDetails());
        if (connection == null) {
            Utils.log("Could not connect to MySQL Database.");
            return;
        }

        if (getSQLStatementFromConnection(connection) == null) {
            Utils.log("Could not get MySQL statement.");
            return;
        }

        Utils.log("The MySQL connection to database is ready!");
    }

    protected void finish() {
        closeStatement(getStatement());
        closeConnection(getConnection());
    }

    /**
     * Gets the driver activated and fails if not present
     * @return  Fail/Success
     */
    private Result connectToDriver() {
        Utils.log("Connecting driver!");
        try {
            Class.forName(Constants.JDBC_DRIVER);
            return Result.SUCCESS;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Result.FAILURE;
    }

    /**
     * Connects directly to the MySQL database with given credentials.
     * @return  The connection to the database.
     */
    private Connection connectToMySQLDatabase(SQLServerDetails details) {
        try {
            return DriverManager.getConnection(details.getDatabaseUrl(),details.getUsername(), details.getPassword());
        } catch (Exception e) {
            Utils.log("Could not connect to MySQL database at " + details.getDatabaseUrl());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new instance of a statement for the connected database.
     * @return  A statement for the connected database.
     */
    private Statement getSQLStatementFromConnection(Connection connection) {
        try {
            return connection.createStatement();
        } catch (Exception e) {
            Utils.log("Could not create MySQL statement.");
            return null;
        }
    }

    protected Statement getSQLStatement() {
        try {
            return getSQLStatementFromConnection(connectToMySQLDatabase(serverDetails));
        } catch (Exception e) {
            Utils.log("Couldn't grab the connection and form a statement.");
            return null;
        }
    }

    /**
     * Closes the currently open statement.
     */
    protected void closeStatement(Statement statement) {
        try {
            statement.close();
        } catch (Exception e) {
            Utils.log("Could not close statement for connection.");
        }
    }

    /**
     * Closes the connection to the database safely, ending all open statements.
     */
    protected void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            Utils.log("Could not close connection.");
        }
    }
}
