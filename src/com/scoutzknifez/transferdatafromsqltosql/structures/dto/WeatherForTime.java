package com.scoutzknifez.transferdatafromsqltosql.structures.dto;

import java.sql.ResultSet;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion.Databasable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.scoutzknifez.transferdatafromsqltosql.structures.Location;
import com.scoutzknifez.transferdatafromsqltosql.utility.exceptions.ParseFailureException;

@AllArgsConstructor
@Getter
public class WeatherForTime implements Databasable {
    // PK (Time, Lat, Long)
    private long time;
    private Location location;

    // Data for weather
    private double temperature;
    private double precipitationProbability;
    private double humidity;
    private int windSpeed;
    private double windBearing;


    @Override
    public Object[] fieldsToArray() {
        return new Object[]{getTime(),
                getLocation().getLatitude(), getLocation().getLongitude(),
                getTemperature(), getPrecipitationProbability(),
                getHumidity(), getWindSpeed(), getWindBearing()};
    }

    public static WeatherForTime createInstance(ResultSet set) {
        try {
            long time = set.getLong("time");
            Location location = new Location(
                    set.getDouble("latitude"), set.getDouble("longitude")
            );

            double temperature = set.getDouble("temperature");
            double precipitationProbability = set.getDouble("precipitationProbability");
            double humidity = set.getDouble("humidity");
            int windSpeed = set.getInt("windSpeed");
            double windBearing = set.getDouble("windBearing");

            return new WeatherForTime(time, location, temperature,
                    precipitationProbability, humidity, windSpeed, windBearing);
        } catch (Exception e) {
            throw new ParseFailureException(set, WeatherForTime.class);
        }
    }
}
