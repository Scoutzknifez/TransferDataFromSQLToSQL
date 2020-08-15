package com.scoutzknifez.transferdatafromsqltosql.database.sql;

import com.scoutzknifez.transferdatafromsqltosql.database.sql.conditions.Conditional;
import com.scoutzknifez.transferdatafromsqltosql.structures.SQLServerDetails;
import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateWorker extends Worker {
    private String field;
    private Object value;
    private Conditional conditions;

    public UpdateWorker(SQLServerDetails serverDetails, Table table, String field, Object value, Conditional conditions) {
        super(serverDetails, table);
        setField(field);
        setValue(value);
        setConditions(conditions);
    }

    @Override
    public void run() {
        statement = getSQLStatement();
        if (getStatement() == null)
            return;

        doUpdate();
        finish();
    }

    private void doUpdate() {
        String sqlArg = "UPDATE " + getTable().getTableCasing() +
                " SET `" + getField() + "` = " +
                (getValue() instanceof String ? "\"" + getValue() + "\"" : getValue()) +
                //(conditions == null ? "" : " WHERE " + getConditions().toString()); TODO Maybe use this (Trying to think of a case where blank is preferred)
                " WHERE " + getConditions().toString();

        try {
            getStatement().execute(sqlArg);
        } catch (Exception e) {
            Utils.log("Failed to update on table " + getTable().getTableCasing() + ")" + " updating item with conditions of " + conditions.toString());
        }
    }
}
