import com.scoutzknifez.transferdatafromsqltosql.database.sql.GetWorker;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.Table;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion.InsertWorker;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.support.cs3560project.Global;
import com.scoutzknifez.transferdatafromsqltosql.utility.Constants;
import com.scoutzknifez.transferdatafromsqltosql.utility.MySQLServersInfo;
import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Constants.DATABASE_NAME = "CS3560";

        // WeatherDBTransfer();
        CS3560Transfer();
    }

    public static void WeatherDBTransfer() {
        GetWorker grabAll = new GetWorker(MySQLServersInfo.fromServer, Table.WEATHER_FOR_TIME, null);
        grabAll.run();
        List<?> list = grabAll.getItems();

        Utils.log("Size: %s", list.size());
        new InsertWorker(MySQLServersInfo.toServer, Table.WEATHER_FOR_TIME, list).run();
    }

    public static void CS3560Transfer() {
        Table[] tableList = {Table.PRODUCTS, Table.PRODUCT_IMAGES, Table.PRODUCT_REVIEWS, Table.USERS, Table.INVENTORIES};

        for(Table tb : tableList) {
            if (tb == Table.INVENTORIES)
                Global.initialize();

            GetWorker grabAll = new GetWorker(MySQLServersInfo.fromServer, tb, null);
            grabAll.run();
            List<?> list = grabAll.getItems();

            Utils.log("Size: %s", list.size());
            new InsertWorker(MySQLServersInfo.toServer, tb, list).run();
        }
    }
}
