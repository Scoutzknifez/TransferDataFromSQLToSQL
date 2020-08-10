import com.scoutzknifez.transferdatafromsqltosql.database.sql.GetWorker;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.Table;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion.InsertWorker;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.WeatherForTime;
import com.scoutzknifez.transferdatafromsqltosql.utility.MySQLServersInfo;
import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        GetWorker grabAll = new GetWorker(MySQLServersInfo.fromServer, Table.WEATHER_FOR_TIME, null);
        grabAll.run();
        List<WeatherForTime> weatherList = (List<WeatherForTime>) grabAll.getItems();

        Utils.log("Size: %s", weatherList.size());

        int index = 1;
        for (WeatherForTime weatherForTime : weatherList) {
            Utils.log("Starting transfer [" + index + " / " + weatherList.size() + "]");
            new InsertWorker(MySQLServersInfo.toServer, Table.WEATHER_FOR_TIME, weatherForTime).run();
        }
    }
}
