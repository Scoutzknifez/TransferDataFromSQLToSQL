package com.scoutzknifez.transferdatafromsqltosql.database.sql;

import com.scoutzknifez.transferdatafromsqltosql.database.sql.conditions.Conditional;
import com.scoutzknifez.transferdatafromsqltosql.structures.SQLServerDetails;
import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * A fetcher from a SQL database.
 */
@Getter
@Setter
public class GetWorker extends Worker {
    private List<?> items;
    private Conditional conditions;

    /**
     * Constructor with specific table and conditions
     * @param table         table to fetch from
     * @param conditions    conditions to meet when fetching from database (null = no conditions)
     */
    public GetWorker(SQLServerDetails serverDetails, Table table, Conditional conditions) {
        super(serverDetails, table);
        setConditions(conditions);
    }

    @Override
    public void run() {
        statement = getSQLStatement();
        if (statement == null)
            return;

        getFromDatabase();
        if (items == null) {
            Utils.log("Item list is null from GetWorker");
        }

        finish();
    }

    /**
     * Sends a query to the database and returns a result set.
     */
    private void getFromDatabase() {
        /* Showing how to structure a conditional for use in this section
        OrConditional or = new OrConditional("size", 5, "shape", "circle");
        AndConditional and = new AndConditional(or, new Conditional("height", 7));
        OrConditional or2 = new OrConditional(or,and);
        System.out.println(or2);

        PRINTS OUT:
        ((`size` = 5 OR `shape` = `circle`) OR ((`size` = 5 OR `shape` = `circle`) AND `height` = 7))*/

        String sqlArg = "SELECT * FROM " + getTable().getTableCasing() + (getConditions() == null ? "" : " WHERE " + getConditions().toString());
        try {
            putResultIntoList(getStatement().executeQuery(sqlArg));
        } catch (Exception e) {
            Utils.log("Failed to fetch list from database.");
        }
    }

    /**
     * Result set is turned into an object set of type specified on construction.
     * @param set   The set to turn into a list of objects
     */
    private void putResultIntoList(ResultSet set) {
        List<Object> list = new ArrayList<>();
        try {
            while (set.next()) {
                list.add(getTable().getConstructorClass().getDeclaredMethod("createInstance", ResultSet.class)
                        .invoke(getTable().getConstructorClass(), set));
            }
            items = list;
        } catch (Exception e) {
            e.printStackTrace();
            Utils.log("Could not parse returned list from " + getTable().getTableCasing() + " table.");
        }
    }
}
