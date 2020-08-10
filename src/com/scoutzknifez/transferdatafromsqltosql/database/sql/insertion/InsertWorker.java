package com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion;

import com.scoutzknifez.transferdatafromsqltosql.database.sql.SQLHelper;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.Table;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.Worker;
import com.scoutzknifez.transferdatafromsqltosql.structures.SQLServerDetails;
import lombok.Getter;
import lombok.Setter;
import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;

@Setter
@Getter
public class InsertWorker extends Worker {
    private String objectStringForm;

    public InsertWorker(SQLServerDetails serverDetails, Table table, Databasable databasableObject) {
        super(serverDetails, table);
        setObjectStringForm(SQLHelper.databasableToInsertionForm(databasableObject));
    }

    @Override
    public void run() {
        statement = getSQLStatement();
        if (getStatement() == null)
            return;

        doInsertion();
        finish();
    }

    private void doInsertion() {
        String sqlArg = "INSERT INTO " + getTable().name() + " VALUES " + getObjectStringForm();
        try {
            getStatement().execute(sqlArg);
        } catch (Exception e) {
            Utils.log("Failed to do insertion on table: " + getTable().name());
        }
    }
}
