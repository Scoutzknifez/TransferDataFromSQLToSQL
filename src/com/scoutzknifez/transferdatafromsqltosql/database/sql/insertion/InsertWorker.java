package com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion;

import com.scoutzknifez.transferdatafromsqltosql.database.sql.SQLHelper;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.Table;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.Worker;
import com.scoutzknifez.transferdatafromsqltosql.structures.SQLServerDetails;
import lombok.Getter;
import lombok.Setter;
import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;

import java.util.List;

@Setter
@Getter
public class InsertWorker extends Worker {
    private boolean listInsertionMode;
    private List<?> listToInsert;
    private String objectStringForm;

    public InsertWorker(SQLServerDetails serverDetails, Table table, Databasable databasableObject) {
        super(serverDetails, table);
        setObjectStringForm(SQLHelper.databasableToInsertionForm(databasableObject));
    }

    public InsertWorker(SQLServerDetails serverDetails, Table table, List<?> list) {
        super(serverDetails, table);
        setListInsertionMode(true);
        setListToInsert(list);
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
        if (listInsertionMode) {
            int index = 1;
            for(Object obj : listToInsert) {
                Databasable data = (Databasable) obj;
                Utils.log("Starting transfer [" + (index++) + " / " + listToInsert.size() + "]");
                String sqlArg = "INSERT INTO " + getTable().getTableCasing() + " VALUES " + SQLHelper.databasableToInsertionForm(data);
                try {
                    getStatement().execute(sqlArg);
                } catch (Exception e) {
                    Utils.log("Failed to do insertion on table: " + getTable().getTableCasing());
                    e.printStackTrace();
                }
            }
        } else {
            String sqlArg = "INSERT INTO " + getTable().getTableCasing() + " VALUES " + getObjectStringForm();
            try {
                getStatement().execute(sqlArg);
            } catch (Exception e) {
                Utils.log("Failed to do insertion on table: " + getTable().getTableCasing());
            }
        }
    }
}
